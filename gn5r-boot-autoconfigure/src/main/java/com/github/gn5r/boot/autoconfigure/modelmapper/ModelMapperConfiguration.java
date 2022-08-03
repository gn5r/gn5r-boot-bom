package com.github.gn5r.boot.autoconfigure.modelmapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class ModelMapperConfiguration {

  private static final Logger log = LoggerFactory.getLogger(ModelMapperConfiguration.class);

  /**
   * yyyy-MM-dd
   */
  private static final DateTimeFormatter YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /**
   * yyyy-MM-dd HH:mm:ss
   */
  private static final DateTimeFormatter YMDHMS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final Converter<String, LocalDate> stringToLocalDateConverter = new AbstractConverter<String, LocalDate>() {
    @Override
    protected LocalDate convert(String source) {
      if (StringUtils.isEmpty(source)) {
        return null;
      }
      try {
        return LocalDate.parse(source, YMD);
      } catch (DateTimeParseException e) {
        log.warn(ExceptionUtils.getStackTrace(e));
        return null;
      }
    }
  };

  private static final Converter<LocalDate, String> localDateToStringConverter = new AbstractConverter<LocalDate, String>() {
    @Override
    protected String convert(LocalDate source) {
      if (source == null) {
        return "";
      }
      try {
        return source.format(YMD);
      } catch (DateTimeParseException e) {
        log.warn(ExceptionUtils.getStackTrace(e));
        return "";
      }
    }
  };

  private static final Converter<String, LocalDateTime> stringToLocalDateTimeConverter = new AbstractConverter<String, LocalDateTime>() {
    @Override
    protected LocalDateTime convert(String source) {
      if (StringUtils.isEmpty(source)) {
        return null;
      }
      try {
        return LocalDateTime.parse(source, YMDHMS);
      } catch (DateTimeParseException e) {
        log.warn(ExceptionUtils.getStackTrace(e));
        return null;
      }
    }
  };

  private static final Converter<LocalDateTime, String> localDateTimeToStringConverter = new AbstractConverter<LocalDateTime, String>() {
    @Override
    protected String convert(LocalDateTime source) {
      if (source == null) {
        return "";
      }
      try {
        return source.format(YMDHMS);
      } catch (DateTimeParseException e) {
        log.warn(ExceptionUtils.getStackTrace(e));
        return "";
      }
    }
  };

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.addConverter(stringToLocalDateConverter);
    modelMapper.addConverter(localDateToStringConverter);
    modelMapper.addConverter(stringToLocalDateTimeConverter);
    modelMapper.addConverter(localDateTimeToStringConverter);
    return modelMapper;
  }
}
