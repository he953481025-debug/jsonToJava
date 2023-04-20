package com.hejincai.jsontojavaproject.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author hejincai
 * @since 2023/4/17 17:49
 **/
@Data
public class JsonToJavaParam {

    private Object inputJson;

    private String packageName;

    private String outPath = "";

    private String rootClass = "root";

    private String engine = "freemarker";

    private Boolean swagger = Boolean.TRUE;

    private Boolean lombok = Boolean.TRUE;

    private String inputType = "json";

    private String note = "";

    private String author = "hejincai";

    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));

}
