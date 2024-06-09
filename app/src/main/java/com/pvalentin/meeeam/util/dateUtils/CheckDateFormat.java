package com.pvalentin.meeeam.util.dateUtils;

import android.util.Log;

import com.pvalentin.meeeam.util.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CheckDateFormat {
    private static final String TAG = Constants.TAG + "." + CheckDateFormat.class.getSimpleName();
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
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }
        return date != null;
    }
}
