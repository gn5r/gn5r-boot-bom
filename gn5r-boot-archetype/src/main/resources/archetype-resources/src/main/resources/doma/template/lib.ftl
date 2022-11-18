<#--  数値型  -->
<#assign numericClassNames = ["Integer", "Long", "Short", "Double", "Float"]>

<#--  指定したデータタイプを変換する  -->
<#function convertDataType dataType>
  <#local result = dataType?replace("Date", "LocalDate")>
  <#local result = dataType?replace("Timestamp", "LocalDateTime")>
  <#if numericClassNames?seq_contains(dataType)>
    <#local result = dataType?replace(dataType, "BigDecimal")>
  </#if>
  <#return result>
</#function>

<#--  指定したパッケージ名を変換する  -->
<#function convertImportType packageName>
  <#local result = packageName?replace("java.sql.Date", "java.time.LocalDate")>
  <#local result = packageName?replace("java.sql.Timestamp", "java.time.LocalDateTime")>
  <#return result>
</#function>

<#--  java.lang.*の数値型が変数にあるか判定する  -->
<#function hasNumeric propertyDescs>
  <#list numericClassNames as className>
    <#list propertyDescs as property>
      <#if property.propertyClassSimpleName?contains(className)>
        <#return true>
      </#if>
    </#list>
  </#list>
  <#return false>
</#function>