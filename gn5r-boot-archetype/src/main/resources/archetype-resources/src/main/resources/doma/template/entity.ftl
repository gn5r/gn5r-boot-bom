<#-- このテンプレートに対応するデータモデルのクラスは org.seasar.doma.extension.gen.EntityDesc です -->

<#import "/lib.ftl" as lib>
<#if lib.copyright??>
${lib.copyright}
</#if>
<#if packageName??>
package ${packageName};
</#if>

<#list importNames as importName>
<#-- namingTypeがNONEじゃない場合はColumnをインポートしない -->
<#if namingType != "NONE" && importName == "org.seasar.doma.Column">
<#else>
import ${lib.convertImportType(importName)};
</#if>
</#list>
<#--  変数にjava.langの数値型を含む場合はBigDecimalをインポートする  -->
<#if lib.hasNumeric(ownEntityPropertyDescs)>
import java.math.BigDecimal;
</#if>

/**
<#if showDbComment && comment??>
 * ${comment}のエンティティクラス
</#if>
<#if lib.author??>
 * @author ${lib.author}
</#if>
 */
 <#if !useAccessor>
@lombok.Data
</#if>
@Entity<#if useListener || namingType != "NONE">(</#if><#if useListener>listener = ${listenerClassSimpleName}.class</#if><#if namingType != "NONE"><#if useListener>, </#if>naming = ${namingType.referenceName}</#if><#if useListener || namingType != "NONE">)</#if>
<#if showCatalogName && catalogName?? || showSchemaName && schemaName?? || showTableName && tableName??>
@Table(<#if showCatalogName && catalogName??>catalog = "${catalogName}"</#if><#if showSchemaName && schemaName??><#if showCatalogName && catalogName??>, </#if>schema = "${schemaName}"</#if><#if showTableName><#if showCatalogName && catalogName?? || showSchemaName && schemaName??>, </#if>name = "${tableName}"</#if>)
</#if>
public class ${simpleName}<#if superclassSimpleName??> extends ${superclassSimpleName}</#if> {
<#list ownEntityPropertyDescs as property>

  <#if showDbComment && property.comment??>
  /** ${property.comment} */
  <#else>
  /** */
  </#if>
  <#if property.id>
  @Id
  <#if property.generationType??>
  @GeneratedValue(strategy = ${property.generationType.referenceName})
  <#if property.generationType == "SEQUENCE">
  @SequenceGenerator(sequence = "${tableName}_${property.columnName}"<#if property.initialValue??>, initialValue = ${property.initialValue}</#if><#if property.allocationSize??>, allocationSize = ${property.allocationSize}</#if>)
  <#elseif property.generationType == "TABLE">
  @TableGenerator(pkColumnValue = "${tableName}_${property.columnName}"<#if property.initialValue??>, initialValue = ${property.initialValue}</#if><#if property.allocationSize??>, allocationSize = ${property.allocationSize}</#if>)
  </#if>
  </#if>
  </#if>
  <#if property.version>
  @Version
  </#if>
  <#-- namingTypeがNONEじゃない場合はColumnをアノテートしない -->
  <#if namingType == "NONE" && property.showColumnName && property.columnName??>
  @Column(name = "${property.columnName}")
  </#if>
  private ${lib.convertDataType(property.propertyClassSimpleName)} ${property.name};
</#list>
<#if originalStatesPropertyName??>
  /** */
  @OriginalStates
  ${simpleName} ${originalStatesPropertyName};
</#if>
<#if useAccessor>
  <#list ownEntityPropertyDescs as property>

  /**
  <#if showDbComment && property.comment??>
   * ${property.comment}を取得する
   *
   * @return ${property.comment}
  <#else>
   * Get a ${property.name}
   *
   * @return ${property.name}
  </#if>
   */
  <#--  Boolean型の場合はGetterをisXXXにする  -->
  <#if ["Boolean", "boolean"]?seq_contains(property.propertyClassSimpleName)>
  public ${lib.convertDataType(property.propertyClassSimpleName)} is${property.name?cap_first}() {
    return ${property.name};
  }
  <#else>
  public ${lib.convertDataType(property.propertyClassSimpleName)} get${property.name?cap_first}() {
    return ${property.name};
  }
  </#if>

  /**
  <#if showDbComment && property.comment??>
   * ${property.comment}をセットする
   *
   * @param ${property.name} ${property.comment}
  <#else>
   * Set a ${property.name}
   *
   * @param ${property.name} ${property.name}
  </#if>
   */
  public void set${property.name?cap_first}(${lib.convertDataType(property.propertyClassSimpleName)} ${property.name}) {
    this.${property.name} = ${property.name};
  }
  </#list>
</#if>
}