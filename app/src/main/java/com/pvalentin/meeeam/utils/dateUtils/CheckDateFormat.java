package com.pvalentin.meeeam.utils.dateUtils;

import android.util.Log;

import com.pvalentin.meeeam.config.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckDateFormat {
    private static final String TAG = Constants.TAG;
    public static boolean isValidFormat(String value) {
        String format = "dd/MM/yyyy";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
            date = sdf.parse(value);
            assert date != null;
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException e) {
            Log.d(TAG, e.getMessage());
        }
        return date != null;
    }
}
