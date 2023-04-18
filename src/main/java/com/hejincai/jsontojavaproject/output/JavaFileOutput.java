package com.hejincai.jsontojavaproject.output;

import com.hejincai.jsontojavaproject.config.AppConfig;
import com.hejincai.jsontojavaproject.definition.AbstractDefinition;
import com.hejincai.jsontojavaproject.definition.JavaDefinition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hujunzhon on 2019/2/28.
 */
public class JavaFileOutput implements JavaOutput {

    AppConfig config;

    public JavaFileOutput(AppConfig config) {
        this.config = config;
    }

    public List<String> print(JavaDefinition root) throws Exception {
        Map<String, StringBuffer> javaClassContent = new HashMap<>();
        buildClasses(root, javaClassContent);
        List<String> filePathList = new ArrayList<>();
        for (String path : javaClassContent.keySet()) {
            String fileAbsPath = getAbsPath(path);
            File file = new File(fileAbsPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
                file.setWritable(true);
            }
            filePathList.add(fileAbsPath);
            writeClassToFile(file, javaClassContent.get(path));
            System.out.println(file.getAbsolutePath());
        }
        return filePathList;
    }

    private void writeClassToFile(File file, StringBuffer classContent) throws Exception {
        OutputStream os = Files.newOutputStream(file.toPath());
        os.write(classContent.toString().getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private String getAbsPath(String path) {
        String ppath = null;
        if (config.getOutPath() == null) {
            ppath = "";
        } else {
            ppath = config.getOutPath();
        }

        if (!ppath.endsWith(File.separator)) {
            ppath = ppath + File.separator;
        }

        return ppath + path;
    }

    protected void buildClasses(JavaDefinition javaDefinition, Map<String, StringBuffer> javaClassContent) {
        JavaClassBuilder builder = new JavaClassBuilder(javaDefinition);
        Map.Entry<String, StringBuffer> entry = builder.buildJavaClass();
        javaClassContent.put(entry.getKey(), entry.getValue());
        Map<String, JavaDefinition.FieldDefinition> fields = javaDefinition.getFieldMap();
        for (JavaDefinition.FieldDefinition definition : fields.values()) {
            AbstractDefinition fieldType = definition.getType();
            if (fieldType instanceof JavaDefinition) {
                buildClasses((JavaDefinition) fieldType, javaClassContent);
            }
        }
    }
}
