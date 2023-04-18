package com.hejincai.jsontojavaproject.domain;

import lombok.Data;

/**
 * @author hejincai
 * @since 2023/4/17 17:49
 **/
@Data
public class JsonToJavaParam {

    private Object inputJson;

    private String pathPrefix;

    private String packageName;

    private String className;

    private String author;
}
