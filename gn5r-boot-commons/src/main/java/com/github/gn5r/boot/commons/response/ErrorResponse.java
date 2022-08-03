package com.github.gn5r.boot.commons.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "エラーレスポンス")
public class ErrorResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(name = "エラーメッセージ")
  private String message;

  @ApiModelProperty(name = "バリデーションエラーリスト")
  private List<FieldError> fieldErrors;

  public ErrorResponse() {
    this.fieldErrors = new ArrayList<FieldError>();
  }

  public ErrorResponse(String message) {
    this();
    this.message = message;
  }

  public ErrorResponse(String message, List<FieldError> fieldErrors) {
    this.message = message;
    this.fieldErrors = fieldErrors;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setFieldErrors(List<FieldError> fieldErrors) {
    this.fieldErrors = fieldErrors;
  }

  public String getMessage() {
    return this.message;
  }

  public List<FieldError> getFieldErrors() {
    return this.fieldErrors;
  }
}
