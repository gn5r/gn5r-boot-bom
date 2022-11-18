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
<#--  エンティティの変数にjava.langの数値型を含む場合はBigDecimalをインポートする  -->
<#if lib.hasNumeric(entityDesc.ownEntityPropertyDescs)>
import java.math.BigDecimal;
</#if>

<#-- ConfigAutowireableをインポートする -->
import org.seasar.doma.boot.ConfigAutowireable;

/**
<#if entityDesc.showDbComment && entityDesc.comment??>
 * ${entityDesc.comment}のDaoインターフェイス
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
   * @return ${entityDesc.comment}
   * @see ${entityDesc.simpleName} ${entityDesc.comment}
   */
  @Select
  public ${entityDesc.simpleName} selectById(<#list entityDesc.idEntityPropertyDescs as property>${lib.convertDataType(property.propertyClassSimpleName)} ${property.name}<#if property_has_next>, </#if></#list>);

</#if>
<#if entityDesc.idEntityPropertyDescs?size gt 0 && entityDesc.versionEntityPropertyDesc??>
  /**
<#list entityDesc.idEntityPropertyDescs as property>
   * @param ${property.name} ${property.comment}
</#list>
   * @param ${entityDesc.versionEntityPropertyDesc.name} ${entityDesc.versionEntityPropertyDesc.comment}
   * @return ${entityDesc.comment}
   * @see ${entityDesc.simpleName} ${entityDesc.comment}
   */
  @Select(ensureResult = true)
  public ${entityDesc.simpleName} selectByIdAndVersion(<#list entityDesc.idEntityPropertyDescs as property>${lib.convertDataType(property.propertyClassSimpleName)} ${property.name}, </#list>${lib.convertDataType(entityDesc.versionEntityPropertyDesc.propertyClassSimpleName)} ${entityDesc.versionEntityPropertyDesc.name});

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