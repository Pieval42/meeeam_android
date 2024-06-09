package com.pvalentin.meeeam.util.dateUtils;

import static java.text.DateFormat.getDateInstance;

import android.annotation.SuppressLint;

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
    DateFormat frFormat = getDateInstance(DateFormat.SHORT);
    SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    Date date = sqlDateFormat.parse(oldDateString);
    return frFormat.format(date);
  }
}
