package com.hejincai.jsontojavaproject.output;


import com.hejincai.jsontojavaproject.definition.JavaDefinition;

import java.util.List;

/**
 * Created by hujunzhon on 2019/2/28.
 */
public interface JavaOutput {
    List<String> print(JavaDefinition root) throws Exception;
}
