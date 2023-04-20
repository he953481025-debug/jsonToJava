package com.hejincai.jsontojavaproject.parser;

import com.hejincai.jsontojavaproject.definition.JavaDefinition;

import java.util.Arrays;
import java.util.List;

public interface IParser {


    JavaDefinition parse(String str);

    JavaDefinition parse(Object obj);

    default List<JavaDefinition> parseStrToList(String str) {
        return Arrays.asList(parse(str));
    }

    ;

    default List<JavaDefinition> parseObjToList(Object obj) {
        return Arrays.asList(parse(obj));
    }

    ;
}
