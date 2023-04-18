package com.hejincai.jsontojavaproject.parser;

import com.hejincai.jsontojavaproject.definition.JavaDefinition;

public interface IJsonParser {


    JavaDefinition parse(String jsonStr);

    JavaDefinition parse(Object obj);
}
