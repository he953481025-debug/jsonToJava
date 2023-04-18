package com.hejincai.jsontojavaproject.definition;

import java.util.*;

/**
 * 待转换成java的类型定义
 * Created by hujunzhon on 2019/2/28.
 */
public class JavaDefinition extends AbstractDefinition {

    /**
     * class类型
     */
    private String type = "class";


    /**
     * import内容
     */
    private Set<String> imports;

    /**
     * 注释
     */
    private String note;

    /**
     * 描述信息
     */
    private String desc;

    /**
     * 作者
     */
    private String author;

    /**
     * 时间
     */
    private String date;

    /**
     * 属性
     */
    private Map<String, FieldDefinition> fieldMap;

    private List<MethodArgDefinition> methodList;


    /**
     * 路径
     */
    private String path;

    public Set<String> getImports() {
        return imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public void addImport(String importStr) {
        if (importStr == null) {
            return;
        }

        if (this.imports == null) {
            this.imports = new HashSet<String>();
        }

        this.imports.add(importStr);
    }

    public Map<String, FieldDefinition> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, FieldDefinition> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public void addField(FieldDefinition field) {
        if (this.fieldMap == null) {
            this.fieldMap = new HashMap<String, FieldDefinition>();
        }

        this.fieldMap.put(field.getFieldName(), field);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static class MethodDefinition {

        private String methodName;

        private List<MethodArgDefinition> argList;

        private BaseDefinition returnType;

        private String accessControl;

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public List<MethodArgDefinition> getArgList() {
            return argList;
        }

        public void setArgList(List<MethodArgDefinition> argList) {
            this.argList = argList;
        }

        public BaseDefinition getReturnType() {
            return returnType;
        }

        public void setReturnType(BaseDefinition returnType) {
            this.returnType = returnType;
        }

        public String getAccessControl() {
            return accessControl;
        }

        public void setAccessControl(String accessControl) {
            this.accessControl = accessControl;
        }
    }

    /**
     * 属性定义
     */
    public static class FieldDefinition {
        /**
         * 字段名
         */
        private String fieldName;

        /**
         * json的属性名称
         */
        private String jsonPropertyName;
        /**
         * 注解
         */
        private Set<String> annotations;
        /**
         * 注释
         */
        private String note;
        /**
         * 类型
         */
        private AbstractDefinition type;

        /**
         * 是否数组
         */
        private boolean array;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public Set<String> getAnnotations() {
            return annotations;
        }

        public void setAnnotations(Set<String> annotations) {
            this.annotations = annotations;
        }

        public void addAnnotation(String annotation) {
            if (this.annotations == null) {
                this.annotations = new HashSet<String>();
            }

            this.annotations.add(annotation);
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public AbstractDefinition getType() {
            return type;
        }

        public void setType(AbstractDefinition type) {
            this.type = type;
        }

        public boolean isArray() {
            return array;
        }

        public void setArray(boolean array) {
            this.array = array;
        }
    }
}
