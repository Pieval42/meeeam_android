package com.pvalentin.meeeam.utils.dateUtils;

import static java.text.DateFormat.getDateInstance;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDateFormat {

    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat();

    public ConvertDateFormat(String oldDateString, String newDateString, SimpleDateFormat sdf) {
        ConvertDateFormat.sdf = sdf;
    }

    public static String convertDateFormat(String oldDateString) throws ParseException {
        Date d = getDateInstance(DateFormat.SHORT).parse(oldDateString);
        String NEW_FORMAT = "yyyy-MM-dd";
        sdf.applyPattern(NEW_FORMAT);
        assert d != null;
        return sdf.format(d);
    }
}
