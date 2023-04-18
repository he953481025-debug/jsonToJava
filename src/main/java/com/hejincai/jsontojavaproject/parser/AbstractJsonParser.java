package com.hejincai.jsontojavaproject.parser;

import cn.hutool.core.util.StrUtil;
import com.hejincai.jsontojavaproject.definition.JavaDefinition;
import com.hejincai.jsontojavaproject.domain.JsonToJavaParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by hujunzhon on 2019/2/28.
 */
public abstract class AbstractJsonParser implements IJsonParser {

    private static Map<String, String> fieldMap = new HashMap<>();

    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    {
        fieldMap.put("long", "lon");
        fieldMap.put("package", "pack");
    }

    protected JsonToJavaParam jsonToJavaParam;

    public AbstractJsonParser(JsonToJavaParam javaParam) {
        this.jsonToJavaParam = javaParam;
    }

    protected abstract void doParse(JavaDefinition rootDefinition, String json);

    protected String getDftPackage() {
        return jsonToJavaParam.getPackageName();
    }

    protected String buildDftNote() {

        return String.format("/**\n" +
                "  * @since " + LocalDateTime.now().format(timeFormatter) + "\n" +
                "  * @author " + jsonToJavaParam.getAuthor() + "\n" +
                "  */", new Date());
    }

    /**
     * javabean规范格式化field
     *
     * @param fieldName
     * @return
     */
    protected String formatFieldName(String fieldName) {

        if (fieldName.matches("[A-Z]*")) {//都是大写
            return fieldName.toLowerCase();
        }
        if (fieldMap.containsKey(fieldName)) {
            return fieldMap.get(fieldName);
        }
        return StrUtil.toCamelCase(fieldName);
//        } else if (fieldName.length() > 1 && fieldName.substring(0, 2).matches("[A-Z]*")) {
//            //首两个字母要么大小要么小写
//            return fieldName.substring(0, 2).toLowerCase() + fieldName.substring(2);
//        } else if ('A' <= fieldName.charAt(0) && 'Z' >= fieldName.charAt(0)) {
//            return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
//        }

//        return fieldName;
    }

    /**
     * 格式化类名
     *
     * @param fieldName
     * @return
     */
    protected String formatClassNameByField(String fieldName) {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    /**
     * 复数变单数
     *
     * @param name
     * @return
     */
    protected String pluralToSingular(String name) {
        if (name.endsWith("ies")) {
            return name.substring(0, name.length() - 3) + "y";
        } else if (name.endsWith("ses")) {
            return name.substring(0, name.length() - 2);
        } else if (name.endsWith("s")) {
            return name.substring(0, name.length() - 1);
        }

        return name;
    }


    @Override
    public JavaDefinition parse(String jsonStr) {
        JavaDefinition rootDefinition = new JavaDefinition();
        rootDefinition.setPackageName(getDftPackage());
        rootDefinition.setName(jsonToJavaParam.getClassName());
        rootDefinition.setNote(buildDftNote());
        doParse(rootDefinition, jsonStr);
        return rootDefinition;
    }

}
