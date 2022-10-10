package com.github.gn5r.boot.autoconfigure.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

import springfox.documentation.builders.RequestHandlerSelectors;

@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

  /**
   * Swaggerが読み込むベースパッケージ
   * 
   * @see RequestHandlerSelectors
   */
  private String basePackage;

  /**
   * swagger-uiの設定
   */
  private SwaggerUI ui = new SwaggerUI();

  /**
   * @return the basePackage
   */
  public String getBasePackage() {
    return basePackage;
  }

  /**
   * @param basePackage the basePackage to set
   */
  public void setBasePackage(String basePackage) {
    this.basePackage = basePackage;
  }

  /**
   * @return the ui
   */
  public SwaggerUI getUi() {
    return ui;
  }

  /**
   * @param ui the ui to set
   */
  public void setUi(SwaggerUI ui) {
    this.ui = ui;
  }

  public class SwaggerUI {
    /**
     * swagger-uiに表示するプロジェクトタイトル
     */
    private String title;

    /**
     * swagger-uiに表示するプロジェクトの説明
     */
    private String description;

    /**
     * @return the title
     */
    public String getTitle() {
      return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
      return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
      this.description = description;
    }
  }
}
