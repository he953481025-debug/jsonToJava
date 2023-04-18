package ${cfg.packageName};


<#list definition.imports as importStr>
import  ${importStr};
</#list>

/**
* ${definition.note!}
* @author ${author}
* @since ${date}
*/
<#if swagger2>
    @ApiModel(description="${cfg.note!}")
</#if>
public class ${cfg.rootClass} {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list definition.fieldMap as field>

    <#if field.note!?length gt 0>
        <#if swagger2>
            @ApiModelProperty(value = "${field.note}")
        </#if>
    </#if>
    <#list field.annotations as annotation>
        ${annotation}
    </#list>
    <#if field.array>
        private List<${field.type.name}> ${field.fieldName};
    <#else>
        private ${field.type.name} ${field.fieldName};
    </#if>

</#list>
<#------------  END 字段循环遍历  ---------->
}
