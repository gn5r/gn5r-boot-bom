package com.github.gn5r.boot.commons.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

public class RestRuntimeException extends RuntimeException {
  private HttpStatus status;
  private List<FieldError> fieldErrors;

  public RestRuntimeException(String message) {
    super(message);
    this.fieldErrors = new ArrayList<FieldError>();
  }

  public RestRuntimeException(String message, HttpStatus status) {
    super(message);
    this.status = status;
    this.fieldErrors = new ArrayList<FieldError>();
  }

  public RestRuntimeException(String message, HttpStatus status, List<FieldError> fieldErrors) {
    super(message);
    this.status = status;
    this.fieldErrors = fieldErrors;
  }

  public HttpStatus getStatus() {
    return this.status;
  }

  public List<FieldError> getFieldErrors() {
    return this.fieldErrors;
  }
}
