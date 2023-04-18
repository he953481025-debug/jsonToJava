package com.hejincai.jsontojavaproject.controller;

import com.hejincai.jsontojavaproject.config.AppConfig;
import com.hejincai.jsontojavaproject.definition.JavaDefinition;
import com.hejincai.jsontojavaproject.domain.JsonToJavaParam;
import com.hejincai.jsontojavaproject.output.JavaFileOutput;
import com.hejincai.jsontojavaproject.output.JavaOutput;
import com.hejincai.jsontojavaproject.parser.IJsonParser;
import com.hejincai.jsontojavaproject.parser.JacksonParser;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        AppConfig appConfig = new AppConfig();
        appConfig.setOutPath(javaParam.getPathPrefix());
        appConfig.setPackageName(javaParam.getPackageName());
        appConfig.setRootClass(javaParam.getClassName());
        IJsonParser jsonParser = new JacksonParser(javaParam);
        JavaDefinition root = jsonParser.parse(javaParam.getInputJson());
        JavaOutput output = new JavaFileOutput(appConfig);
        return output.print(root);
    }


}
