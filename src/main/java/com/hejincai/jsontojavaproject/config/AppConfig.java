package com.hejincai.jsontojavaproject.config;

import lombok.Data;

import java.io.File;

/**
 * @author hejincai
 * @since 2023/4/18 15:21
 **/
@Data
public class AppConfig {


    private String outPath = "";

    private String packageName ="com.hjz.json.pojo";


    private String rootClass = "root";

    private String frame = "jackson";
}
