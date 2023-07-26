package com.hejincai.jsontojavaproject.parser;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hejincai.jsontojavaproject.config.AppConfig;
import com.hejincai.jsontojavaproject.definition.AbstractDefinition;
import com.hejincai.jsontojavaproject.definition.BaseDefinition;
import com.hejincai.jsontojavaproject.definition.JavaDefinition;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author hejincai
 * @since 2023/4/17 18:16
 **/
public class JacksonParser extends AbstractParser {

    private static ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
    }

    public JacksonParser(AppConfig appConfig) {
        super(appConfig);
    }

    @SneakyThrows
    @Override
    protected void doParse(JavaDefinition rootDefinition, String str) {
        JsonNode jsonNode = objectMapper.readTree(str);
        rootDefinition.setName(appConfig.getRootClass());
        parseParent(rootDefinition, jsonNode);
    }

    private void parseParent(JavaDefinition pDefinition, JsonNode parentJsonNode) {
        pDefinition.setPath(appConfig.getOutPath());
        pDefinition.setDesc(appConfig.getNote());
        pDefinition.setAuthor(appConfig.getAuthor());
        pDefinition.setSwagger(appConfig.getSwagger());
        pDefinition.setDate(appConfig.getDate());
        pDefinition.setLombok(appConfig.getLombok());
        pDefinition.setPackageName(appConfig.getPackageName());
        pDefinition.setNote(appConfig.getNote());
        Iterator<Map.Entry<String, JsonNode>> fields = parentJsonNode.fields();
        if (appConfig.getSwagger()) {
            pDefinition.addImport("io.swagger.annotations.ApiModel");
            pDefinition.addImport("io.swagger.annotations.ApiModelProperty");
            pDefinition.setSwagger(appConfig.getSwagger());
        }
        if (appConfig.getLombok()) {
            pDefinition.addImport("lombok.Data");
        }
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            String field = next.getKey();
            JsonNode jsonNode = next.getValue();
            JavaDefinition.FieldDefinition fieldDefinition = new JavaDefinition.FieldDefinition();
            String fieldName = formatFieldName(field);
            fieldDefinition.setFieldName(fieldName);
            if (appConfig.getSwagger()) {
                fieldDefinition.addAnnotation("@ApiModelProperty(\"\")");
            }
            fieldDefinition.addAnnotation(getJsonFieldAnnotation(field));
            pDefinition.addImport("com.fasterxml.jackson.annotation.JsonProperty");
            if (jsonNode.isArray()) {
                fieldName = pluralToSingular(fieldName);
                fieldDefinition.setArray(true);
                pDefinition.addImport("java.util.List");
                if (jsonNode.size() == 0) {
                    jsonNode = null;
                } else {
                    jsonNode = jsonNode.get(0);
                }
            }
            AbstractDefinition abstractDefinition = getFieldType(fieldName, jsonNode);
            if (abstractDefinition.getPackageName() != null && !"".equals(abstractDefinition.getPackageName()) && !pDefinition.getPackageName().equals(abstractDefinition.getPackageName())) {
                pDefinition.addImport(abstractDefinition.getPackageName() + "." + abstractDefinition.getName());
            }
            fieldDefinition.setType(abstractDefinition);
            pDefinition.addField(fieldDefinition);
        }
    }

    private AbstractDefinition getFieldType(String fieldName, JsonNode jsonNode) {
        if (jsonNode == null) {
            return new BaseDefinition("Object", "");
        } else if (jsonNode.isObject() || jsonNode.isPojo()) {
            JavaDefinition sDefinition = new JavaDefinition();
            sDefinition.setName(formatClassNameByField(fieldName));
            parseParent(sDefinition, jsonNode);
            return sDefinition;
        } else if (jsonNode.isBoolean()) {
            return new BaseDefinition("Boolean", "");
        } else if (jsonNode.isBigDecimal()) {
            return new BaseDefinition("BigDecimal", "java.math");
        } else if (jsonNode.isLong()) {
            return new BaseDefinition("Long", "");
        } else if (jsonNode.isInt()) {
            return new BaseDefinition("Integer", "");
        } else if (jsonNode.isTextual()) {
            return new BaseDefinition("String", "");
        } else if (jsonNode.isFloat() || jsonNode.isDouble()) {
            return new BaseDefinition("Double", "");
        }
        return new BaseDefinition("Object", "");
    }

    private String getJsonFieldAnnotation(String key) {
        return "@JsonProperty(\"" + key + "\")";
    }


    @SneakyThrows
    @Override
    public JavaDefinition parse(Object obj) {
        return parse(objectMapper.writeValueAsString(obj));
    }

    @Override
    public List<JavaDefinition> parseStrToList(String str) {
        JavaDefinition root = parse(str);
        List<JavaDefinition> javaDefinitionList = new ArrayList<>();
        recursionExtractJavaDefinition(root, javaDefinitionList);
        return javaDefinitionList;
    }

    private static void recursionExtractJavaDefinition(JavaDefinition root, List<JavaDefinition> javaDefinitionList) {
        javaDefinitionList.add(root);
        List<JavaDefinition.FieldDefinition> fieldList = root.getFieldList();
        if (CollUtil.isEmpty(fieldList)) {
            root.setFieldList(new ArrayList<>());
            return;
        }
        for (JavaDefinition.FieldDefinition fieldDefinition : fieldList) {
            AbstractDefinition type = fieldDefinition.getType();
            if (type instanceof JavaDefinition) {
                recursionExtractJavaDefinition((JavaDefinition) type, javaDefinitionList);
            }
        }
    }

    @SneakyThrows
    @Override
    public List<JavaDefinition> parseObjToList(Object obj) {
        return this.parseStrToList(objectMapper.writeValueAsString(obj));
    }
}
