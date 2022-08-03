package com.github.gn5r.boot.commons.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.gn5r.boot.commons.response.ErrorResponse;

public class RestRuntimeExceptionHandler extends ResponseEntityExceptionHandler {

  static final Logger log = LoggerFactory.getLogger(RestRuntimeExceptionHandler.class);

  @ExceptionHandler({ RestRuntimeException.class })
  public final ResponseEntity<ErrorResponse> handleRestRuntimeException(RestRuntimeException e) {
    log.error(ExceptionUtils.getStackTrace(e));
    ErrorResponse response = new ErrorResponse(e.getLocalizedMessage(), e.getFieldErrors());
    return new ResponseEntity<ErrorResponse>(response, e.getStatus());
  }
}
