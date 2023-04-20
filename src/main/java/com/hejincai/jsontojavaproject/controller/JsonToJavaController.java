package com.hejincai.jsontojavaproject.controller;

import cn.hutool.core.bean.BeanUtil;
import com.hejincai.jsontojavaproject.config.AppConfig;
import com.hejincai.jsontojavaproject.definition.JavaDefinition;
import com.hejincai.jsontojavaproject.domain.JsonToJavaParam;
import com.hejincai.jsontojavaproject.generator.engine.AbstractTemplateEngine;
import com.hejincai.jsontojavaproject.generator.engine.FreemarkerTemplateEngine;
import com.hejincai.jsontojavaproject.parser.IParser;
import com.hejincai.jsontojavaproject.parser.JacksonParser;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hejincai
 * @since 2023/4/17 17:50
 **/
@RestController
@RequestMapping("/api/jsonToJava")
public class JsonToJavaController {

    @SneakyThrows
    @PostMapping()
    public List<String> jsonToJava(@RequestBody JsonToJavaParam javaParam) {
        AppConfig appConfig = BeanUtil.toBean(javaParam, AppConfig.class);
        IParser jsonParser = new JacksonParser(appConfig);
        List<JavaDefinition> javaDefinitionList = jsonParser.parseObjToList(javaParam.getInputJson());
        AbstractTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        return templateEngine.batchWriter(javaDefinitionList, "pojo.ftl");
    }


}
