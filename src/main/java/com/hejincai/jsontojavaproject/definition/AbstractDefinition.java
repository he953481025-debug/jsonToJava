package com.hejincai.jsontojavaproject.definition;

import lombok.Data;

/**
 * 类型定义
 * Created by hujunzhon on 2019/2/28.
 */
@Data
public abstract class AbstractDefinition {

    /**
     * 包名称
     */
    protected String packageName;

    /**
     * 类名称
     */
    protected String name;
}
