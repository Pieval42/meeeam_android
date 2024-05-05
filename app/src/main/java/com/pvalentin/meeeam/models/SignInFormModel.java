package com.pvalentin.meeeam.models;

public class SignInFormModel {
    private String email;
    private String password;

    public SignInFormModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPSEUDO_REGEX() {
        return "^[a-zA-Z][0-9A-ZÁÀÂÄÃÅÇÉÈÊËÍÌÎÏÑÓÒÔÖÕÚÙÛÜÝŸÆŒa-záàâäãåçéèêëíìîïñóòôöõúùûüýÿæœ\\-_]{3,19}$";
    }

    public String getPASSWORD_REGEX() {
        return "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[&~\"#'{(\\[\\-|`_\\\\^@)\\]=°+}¨$£¤%*µ<>,?;.:/!§éèçàù]).{10,64}$";
    }
}
