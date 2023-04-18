package com.hejincai.jsontojavaproject.output;

import com.hejincai.jsontojavaproject.definition.BaseDefinition;
import com.hejincai.jsontojavaproject.definition.JavaDefinition;

import java.io.File;
import java.util.HashMap;

/**
 * Created by hujunzhon on 2019/2/28.
 */
public class JavaClassBuilder {

    public static final String LINE_END = "\n";

    private JavaDefinition javaDefinition;

    public JavaClassBuilder(JavaDefinition javaDefinition) {
        this.javaDefinition = javaDefinition;
    }

    /**
     * 生成class文件内容
     *
     * @return
     */
    public HashMap.Entry<String, StringBuffer> buildJavaClass() {
        StringBuffer sb = new StringBuffer();

        buildPackage(sb);
        buildImports(sb);
        buildNote(sb);
        buildClassStart(sb);
        sb.append(LINE_END);
        buildFields(sb);
//        buildGetSets(sb);
        buildCLassEnd(sb);
        String path = javaDefinition.getPackageName().replace('.', File.separatorChar);
        String fileName = path + File.separator + javaDefinition.getName() + ".java";
        return new HashMap.SimpleEntry<>(fileName, sb);
    }

    private void buildCLassEnd(StringBuffer sb) {
        sb.append("}").append(LINE_END);
    }


    private String transKeyFirstUpper(String key) {
        return key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    private void buildFields(StringBuffer sb) {
        if (javaDefinition.getFieldMap() != null) {
            for (JavaDefinition.FieldDefinition field : javaDefinition.getFieldMap().values()) {
                if (field.getAnnotations() != null) {
                    for (String ann : field.getAnnotations()) {
                        sb.append("    ").append(ann).append(LINE_END);
                    }
                }

                String fieldType = buildFieldType(field);
                sb.append("    private ").append(fieldType).append(" ");
                sb.append(field.getFieldName()).append(";").append(LINE_END).append(LINE_END);
            }
        }
    }

    private String buildFieldType(JavaDefinition.FieldDefinition field) {
        if (field.isArray()) {
            return String.format("List<%s>", field.getType().getName());
        }

        return field.getType().getName();
    }

    private void buildClassStart(StringBuffer sb) {
        sb.append("@Data").append(LINE_END);
        sb.append("@ApiModel(description = \"\")").append(LINE_END);
        sb.append("public class ").append(javaDefinition.getName()).append(" {").append(LINE_END);
    }

    private void buildImports(StringBuffer sb) {
        if (javaDefinition.getImports() != null) {
            for (String importItem : javaDefinition.getImports()) {
                sb.append("import ").append(importItem).append(";").append(LINE_END);
            }
        }

        sb.append(LINE_END);
    }

    private void buildNote(StringBuffer sb) {
        sb.append(javaDefinition.getNote()).append(LINE_END);
    }

    private void buildPackage(StringBuffer sb) {
        sb.append("package ");
        sb.append(javaDefinition.getPackageName()).append(";").append(LINE_END);
    }

}
