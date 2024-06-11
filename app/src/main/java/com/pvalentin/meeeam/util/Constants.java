package com.pvalentin.meeeam.util;

public class Constants {
    public static final String TAG = "MEEEAM";
    public static final String HOST = "http://192.168.1.66";
    
//    public static final String HOST = "https://www.pvalentin.fr";
    public static final String API_BASE_DIRECTORY = HOST + "/meeeam/backend/";
    public static final String BASE_URL = API_BASE_DIRECTORY + "index.php/";
    public static final String PSEUDO_REGEX = "^[a-zA-Z][0-9A-ZÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒa-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœ\\-_]{3,19}$";
    public static final String NAME_REGEX = "^[A-ZÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒa-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœ'\\-\\s]{1,50}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&~\"#'{(\\[\\-|`_\\\\^@)\\]=°+}¨$£¤%*µ<>,?;.:/!§éèçàù]).{10,64}$";
    public static final String POSTCODE_REGEX = "^[a-zA-Z0-9\\-]{2,10}$";
    public static final String CITY_REGEX = "^[A-ZÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒa-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœ'\\-\\s]{1,50}$";
    public static final String AUTH_PREFS_FILE = "com.pvalentin.meeeam.AUTH_PREFS";
}
