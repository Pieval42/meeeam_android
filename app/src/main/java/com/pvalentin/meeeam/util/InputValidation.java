package com.pvalentin.meeeam.util;

import static com.pvalentin.meeeam.util.Constants.CITY_REGEX;
import static com.pvalentin.meeeam.util.Constants.NAME_REGEX;
import static com.pvalentin.meeeam.util.Constants.PASSWORD_REGEX;
import static com.pvalentin.meeeam.util.Constants.POSTCODE_REGEX;
import static com.pvalentin.meeeam.util.Constants.PSEUDO_REGEX;

import android.util.Patterns;

import com.pvalentin.meeeam.data.network.request.SignUpRequest;
import com.pvalentin.meeeam.util.dateUtils.CheckDateFormat;

import java.util.regex.Pattern;

public class InputValidation {
    private static final SignUpRequest SIGN_UP_REQUEST = new SignUpRequest(
            "", "", "", "", "", "",
            "", "", 0, "I", ""
    );
    private String errorMessage;

    public static boolean validatePseudo(String pseudo) {
      return pseudo.matches(PSEUDO_REGEX);
    }

    public static boolean validateEmail(String email) {
        return Pattern.matches(Patterns.EMAIL_ADDRESS.toString(), email);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validateConfirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public static boolean validateFirstName(String firstName) {
        return firstName.matches(NAME_REGEX);
    }

    public static boolean validateLastName(String lastName) {
        return lastName.matches(NAME_REGEX);
    }

    public static boolean validateBirthDate(String birthDate) {
        return CheckDateFormat.isValidFormat(birthDate);
    }

    public static boolean validatePostCode(String postCode) {
        return postCode.matches(POSTCODE_REGEX);
    }

    public static boolean validateCity(String city) {
        return city.matches(CITY_REGEX);
    }

    public static boolean validateWebsite(String website) {
        return Pattern.matches(Patterns.WEB_URL.toString(), website);
    }

    public static class RequiredFields {
        private final String pseudo;
        private final String email;
        private final String password;
        private final String confirmPassword;
        private final String firstName;
        private final String lastName;
        private final String birthDateNotConverted;

        public RequiredFields(String pseudo, String email, String password,
                                     String confirmPassword, String firstName, String lastName,
                                     String birthDateNotConverted) {
            this.pseudo = pseudo;
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDateNotConverted = birthDateNotConverted;
        }
    }

    public static class RequiredFieldsBuilder {
        private String pseudo = "";
        private String email = "";
        private String password = "";
        private String confirmPassword = "";
        private String firstName = "";
        private String lastName = "";
        private String birthDateNotConverted = "";


        public RequiredFieldsBuilder setSignUp(String pseudo, String email, String password,
                                     String confirmPassword, String firstName, String lastName,
                                     String birthDateNotConverted) {
            this.pseudo = pseudo;
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDateNotConverted = birthDateNotConverted;
            return this;
        }

        public RequiredFieldsBuilder setLogin(String email, String password) {
            this.email = email;
            this.password = password;
            return this;
        }

        public RequiredFields build() {
            return new RequiredFields(pseudo, email, password, confirmPassword, firstName, lastName,
                birthDateNotConverted);
        }
    }
    public static boolean validateRequiredFields(RequiredFields requiredFields) {

        boolean isPseudoValid = validatePseudo(requiredFields.pseudo);
        boolean isEmailValid = validateEmail(requiredFields.email);
        boolean isPasswordValid = validatePassword(requiredFields.password);
        boolean isConfirmPasswordValid = validateConfirmPassword(requiredFields.password,
                requiredFields.confirmPassword);
        boolean isFirstNameValid = validateFirstName(requiredFields.firstName);
        boolean isLastNameValid = validateLastName(requiredFields.lastName);
        boolean isBirthDateValid = validateBirthDate(requiredFields.birthDateNotConverted);

        return isPseudoValid && isEmailValid && isPasswordValid && isConfirmPasswordValid &&
                isFirstNameValid && isLastNameValid && isBirthDateValid;
    }

    public static boolean validateNotRequiredFields(String postCode, String city, String website) {
        boolean isPostCodeValid = postCode.isEmpty() || validatePostCode(postCode);
        boolean isCityValid = city.isEmpty() || validateCity(city);
        boolean isWebsiteValid = website.isEmpty() || validateWebsite(website);

        return isPostCodeValid && isCityValid && isWebsiteValid;
    }

    public boolean validateLoginFields(String email, String password) {
        boolean isEmailValid = validateEmail(email);
        boolean isPasswordValid = validatePassword(password);

        return isEmailValid && isPasswordValid;
    }

    public boolean validateSignUpForm(String pseudo, String email, String password,
                                      String confirmPassword, String firstName, String lastName,
                                      String birthDateNotConverted, String postCode, String city,
                                      String website) {
        boolean requiredFieldsAreNotEmpty = !pseudo.isEmpty() && !password.isEmpty()
                && !firstName.isEmpty() && !lastName.isEmpty() && !birthDateNotConverted.isEmpty()
                && !email.isEmpty();

        RequiredFields requiredFields = new RequiredFieldsBuilder().setSignUp(pseudo, email,
                password, confirmPassword, firstName, lastName, birthDateNotConverted).build();

        boolean allRequiredFieldsAreValid = InputValidation.validateRequiredFields(requiredFields);
        boolean otherFieldsAreValid = InputValidation.validateNotRequiredFields(postCode, city,
                website);

        return requiredFieldsAreNotEmpty && allRequiredFieldsAreValid && otherFieldsAreValid;
    }

    public boolean validateLoginForm(String email, String password) {
        boolean fieldsAreNotEmpty = !password.isEmpty() && !email.isEmpty();

        boolean fieldsAreValid = validateLoginFields(email, password);

        return fieldsAreNotEmpty && fieldsAreValid;
    }
}
