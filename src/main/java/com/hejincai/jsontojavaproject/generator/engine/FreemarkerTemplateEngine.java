package com.hejincai.jsontojavaproject.generator.engine;

import com.hejincai.jsontojavaproject.JsonTojavaProjectApplication;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

/**
 * Freemarker 模板引擎实现文件输出
 *
 * @author nieqiurong
 * @since 2018-01-11
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);

    private static Configuration cfg = new Configuration(new Version("2.3.23"));

    {
        ClassTemplateLoader loader = new ClassTemplateLoader(JsonTojavaProjectApplication.class, "/templates");
        cfg.setTemplateLoader(loader);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }


    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        Template template = cfg.getTemplate(templatePath);
        File file = new File(outputFile);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
            file.setWritable(true);
        }
        try (OutputStream fileOutputStream = Files.newOutputStream(file.toPath())) {
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));

        }
        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }

    protected boolean isCreate(String filePath) {
        // 全局判断【默认】
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            file.getParentFile().mkdirs();
        }
        return !exist;
    }


    @Override
    public String templateFilePath(String filePath) {
        return filePath + ".ftl";
    }
}
