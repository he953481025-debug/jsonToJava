package com.hejincai.jsontojavaproject.generator.engine;

import com.hejincai.jsontojavaproject.definition.JavaDefinition;

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

    public void writer(JavaDefinition javaDefinition, String templatePath) {

    }

    public String templateFilePath(String filePath);
}
