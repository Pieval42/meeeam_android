package com.pvalentin.meeeam.util.dateUtils;

import static java.text.DateFormat.getDateInstance;

import android.annotation.SuppressLint;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ConvertDateFormat {
  
  @SuppressLint("SimpleDateFormat")
  static SimpleDateFormat sdf = new SimpleDateFormat();
  
  public ConvertDateFormat(String oldDateString, String newDateString, SimpleDateFormat sdf) {
    ConvertDateFormat.sdf = sdf;
  }
  
  public static String convertDateFormatFrToSql(String oldDateString) throws ParseException {
    Date d = getDateInstance(DateFormat.SHORT).parse(oldDateString);
    String NEW_FORMAT = "yyyy-MM-dd";
    sdf.applyPattern(NEW_FORMAT);
    assert d != null;
    return sdf.format(d);
  }
  
  public static String convertDateFormatSqlToFr(String oldDateString) throws ParseException {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime localDateTime = LocalDateTime.parse(oldDateString, inputFormatter);
    
    ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"))
        .withZoneSameInstant(ZoneId.of("Europe/Paris"));
    
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    return zonedDateTime.format(outputFormatter);
  }
}
