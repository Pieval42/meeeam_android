package com.pvalentin.meeeam.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class htmlSpecialChars {
  
  private static final Map<String, String> SPECIAL_CHARS_MAP = new HashMap<>();
  
  static {
    SPECIAL_CHARS_MAP.put("&amp;", "&");
    SPECIAL_CHARS_MAP.put("&#038;", "&");
    SPECIAL_CHARS_MAP.put("&quot;", "\"");
    SPECIAL_CHARS_MAP.put("&#039;", "'");
    SPECIAL_CHARS_MAP.put("&lt;", "<");
    SPECIAL_CHARS_MAP.put("&gt;", ">");
  }
  
  public static String decodeHtmlSpecialChars(String text) {
    Pattern pattern = Pattern.compile("&[\\w\\d#]{2,4};");
    Matcher matcher = pattern.matcher(text);
    StringBuffer result = new StringBuffer();
    
    while (matcher.find()) {
      String key = matcher.group();
      String replacement = SPECIAL_CHARS_MAP.get(key);
      if (replacement != null) {
        matcher.appendReplacement(result, replacement);
      } else {
        matcher.appendReplacement(result, key);
      }
    }
    matcher.appendTail(result);
    return result.toString();
  }
  
  public static String encodeHtmlSpecialChars(String text) {
    StringBuilder result = new StringBuilder();
    
    for (char ch : text.toCharArray()) {
      String str = String.valueOf(ch);
      String replacement = SPECIAL_CHARS_MAP.get(str);
      if (replacement != null) {
        result.append(replacement);
      } else {
        result.append(str);
      }
    }
    
    return result.toString();
  }
}
