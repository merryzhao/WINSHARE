<#--CSV的模板文件-->
<#macro csvLine items>
<#list items as item><#if item_index != 0>,</#if>"${item?replace('\"','\"\"')}"</#list>
</#macro>
<@csvLine items=table.header/>
<#list table.body as line>
<@csvLine items=line/>
</#list>