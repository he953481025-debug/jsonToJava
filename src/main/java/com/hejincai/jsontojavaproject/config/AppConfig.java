package com.hejincai.jsontojavaproject.config;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author hejincai
 * @since 2023/4/18 15:21
 **/
@Data
public class AppConfig {

    private String outPath = "";

    private String packageName ="com.hjz.json.pojo";

    private String rootClass = "root";

    private String engine = "freemarker";

    private Boolean swagger = Boolean.TRUE;

    private Boolean lombok = Boolean.TRUE;

    private String inputType = "json";

    private String note = "";

    private String author = "";

    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
}
