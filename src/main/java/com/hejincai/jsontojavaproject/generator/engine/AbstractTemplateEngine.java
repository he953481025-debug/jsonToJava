package com.hejincai.jsontojavaproject.generator.engine;

import com.hejincai.jsontojavaproject.definition.JavaDefinition;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hejincai
 * @since 2023/4/18 17:52
 **/
public abstract class AbstractTemplateEngine {

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;

    /**
     * 将模板渲染成为java文件
     */
    @SneakyThrows
    public String writer(JavaDefinition javaDefinition, String templatePath) {
        Map<String, Object> definitionObjectMap = new HashMap<>(4);
        definitionObjectMap.put("cfg", javaDefinition);
        this.writer(definitionObjectMap, templatePath, javaDefinition.getFileAbsPathName());
        return javaDefinition.getFileAbsPathName();
    }

    public List<String> batchWriter(List<JavaDefinition> javaDefinitionList, String templatePath) {
        List<String> result = new ArrayList<>(javaDefinitionList.size());
        for (JavaDefinition javaDefinition : javaDefinitionList) {
            result.add(writer(javaDefinition, templatePath));
        }
        return result;
    }

    public abstract String templateFilePath(String filePath);
}
