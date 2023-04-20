package ${cfg.packageName};


<#list cfg.imports as importStr>
import  ${importStr};
</#list>

/**
* ${cfg.desc!}
* @author ${cfg.author}
* @since ${cfg.date}
*/
<#if cfg.swagger>
@ApiModel(description="${cfg.desc!}")
</#if>
<#if cfg.lombok>
@Data
</#if>
public class ${cfg.name} {

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list cfg.fieldList as field>
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
