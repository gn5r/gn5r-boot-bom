<#-- このテンプレートに対応するデータモデルのクラスは org.seasar.doma.extension.gen.DaoDesc です -->
<#import "/lib.ftl" as lib>
<#if lib.copyright??>
${lib.copyright}
</#if>
<#if packageName??>
package ${packageName};
</#if>

<#list importNames as importName>
import ${importName};
</#list>

<#-- ConfigAutowireableをインポートする -->
import org.seasar.doma.boot.ConfigAutowireable;

/**
<#if entityDesc.showDbComment && entityDesc.comment??>
 * ${entityDesc.comment}Dao
</#if>
<#if lib.author??>
 * @author ${lib.author}
</#if>
 *
 * @see ${entityDesc.simpleName}
 */
@Dao<#if configClassSimpleName??>(config = ${configClassSimpleName}.class)</#if>
<#-- Autowiredをするには@ConfigAutowireableが必要 -->
@ConfigAutowireable
public interface ${simpleName} {

<#if entityDesc.idEntityPropertyDescs?size gt 0>
  /**
<#list entityDesc.idEntityPropertyDescs as property>
   * @param ${property.name} ${property.comment}
</#list>
   * @return ${entityDesc.comment}Entity
   * @see ${entityDesc.simpleName} ${entityDesc.comment}Entity
   */
  @Select
  public ${entityDesc.simpleName} selectById(<#list entityDesc.idEntityPropertyDescs as property>${property.propertyClassSimpleName} ${property.name}<#if property_has_next>, </#if></#list>);

</#if>
<#if entityDesc.idEntityPropertyDescs?size gt 0 && entityDesc.versionEntityPropertyDesc??>
  /**
<#list entityDesc.idEntityPropertyDescs as property>
   * @param ${property.name} ${property.comment}
</#list>
   * @param ${entityDesc.versionEntityPropertyDesc.name} ${entityDesc.versionEntityPropertyDesc.comment}
   * @return ${entityDesc.comment}Entity
   * @see ${entityDesc.simpleName} ${entityDesc.comment}Entity
   */
  @Select(ensureResult = true)
  public ${entityDesc.simpleName} selectByIdAndVersion(<#list entityDesc.idEntityPropertyDescs as property>${property.propertyClassSimpleName} ${property.name}, </#list>${entityDesc.versionEntityPropertyDesc.propertyClassSimpleName} ${entityDesc.versionEntityPropertyDesc.name});

</#if>
  /**
   * @param entity ${entityDesc.comment}
   * @return affected rows
   */
  @Insert
  public int insert(${entityDesc.simpleName} entity);

  /**
   * @param entity ${entityDesc.comment}
   * @return affected rows
   */
  @Update
  public int update(${entityDesc.simpleName} entity);

  /**
   * @param entity ${entityDesc.comment}
   * @return affected rows
   */
  @Delete
  public int delete(${entityDesc.simpleName} entity);
}