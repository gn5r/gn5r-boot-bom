package com.github.gn5r.boot.autoconfigure.modelmapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({ ModelMapperAutoConfiguration.class })
public class ModelMapperTest {

  private static final Logger log = LoggerFactory.getLogger(ModelMapperTest.class);

  @Autowired
  private ModelMapper modelMapper;

  @Test
  public void stringToLocalDate() {
    try {
      LocalDate localDate = modelMapper.map("2022 11-13", LocalDate.class);
      assertEquals(LocalDate.of(2022, 11, 13), localDate);
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Test
  public void localDateToString() {
    try {
      String localDate = modelMapper.map(LocalDate.of(2022, 11, 13), String.class);
      assertEquals("2022-11-13", localDate);
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Test
  public void stringToLocalDateTime() {
    try {
      LocalDateTime localDateTime = modelMapper.map("2022 11-13 12:30:45", LocalDateTime.class);
      assertEquals(LocalDateTime.of(2022, 11, 13, 12, 30, 45), localDateTime);
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }

  @Test
  public void localDateTimeToString() {
    try {
      String localDateTime = modelMapper.map(LocalDateTime.of(2022, 11, 13, 12, 30), String.class);
      assertEquals("2022-11-13 12:30:45", localDateTime);
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
      fail(e);
    }
  }
}
