package com.pvalentin.meeeam.utils;

import android.util.Patterns;

import com.pvalentin.meeeam.models.SignUpFormModel;
import com.pvalentin.meeeam.utils.dateUtils.CheckDateFormat;

import java.util.regex.Pattern;

public class InputValidation {
    private static final SignUpFormModel signUpFormModel = new SignUpFormModel(
            "", "", "", "", "", "",
            "", "", 0, "I", ""
    );
    private String errorMessage;

    public static boolean validatePseudo(String pseudo) {
        String pseudoRegex = signUpFormModel.getPSEUDO_REGEX();
        return pseudo.matches(pseudoRegex);
    }

    public static boolean validateEmail(String email) {
        return Pattern.matches(Patterns.EMAIL_ADDRESS.toString(), email);
    }

    public static boolean validatePassword(String password) {
        String passwordRegex = signUpFormModel.getPASSWORD_REGEX();
        return password.matches(passwordRegex);
    }

    public static boolean validateConfirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public static boolean validateFirstName(String firstName) {
        String nameRegex = signUpFormModel.getNAME_REGEX();
        return firstName.matches(nameRegex);
    }

    public static boolean validateLastName(String lastName) {
        String nameRegex = signUpFormModel.getNAME_REGEX();
        return lastName.matches(nameRegex);
    }

    public static boolean validateBirthDate(String birthDate) {
        return CheckDateFormat.isValidFormat(birthDate);
    }

    public static boolean validatePostCode(String postCode) {
        String postCodeRegex = signUpFormModel.getPOSTCODE_REGEX();
        return postCode.matches(postCodeRegex);
    }

    public static boolean validateCity(String city) {
        String cityRegex = signUpFormModel.getCITY_REGEX();
        return city.matches(cityRegex);
    }

    public static boolean validateWebsite(String website) {
        return Pattern.matches(Patterns.WEB_URL.toString(), website);
    }

    public static boolean validateRequiredFields(String pseudo, String email,
                                                 String password, String confirmPassword, String firstName,
                                                 String lastName, String birthDateNotConverted) {

        boolean isPseudoValid = validatePseudo(pseudo);
        boolean isEmailValid = validateEmail(email);
        boolean isPasswordValid = validatePassword(password);
        boolean isConfirmPasswordValid = validateConfirmPassword(password, confirmPassword);
        boolean isFirstNameValid = validateFirstName(firstName);
        boolean isLastNameValid = validateLastName(lastName);
        boolean isBirthDateValid = validateBirthDate(birthDateNotConverted);

        return isPseudoValid && isEmailValid && isPasswordValid && isConfirmPasswordValid &&
                isFirstNameValid && isLastNameValid && isBirthDateValid;
    }

    public static boolean validateNotRequiredFields(String postCode, String city, String website) {
        boolean isPostCodeValid = postCode.isEmpty() || validatePostCode(postCode);
        boolean isCityValid = city.isEmpty() || validateCity(city);
        boolean isWebsiteValid = website.isEmpty() || validateWebsite(website);

        return isPostCodeValid && isCityValid && isWebsiteValid;
    }

    public static boolean validateSignUpForm(String pseudo, String email, String password,
                                             String confirmPassword, String firstName, String lastName,
                                             String birthDateNotConverted, String postCode, String city,
                                             String website) {
        boolean requiredFieldsAreNotEmpty = !pseudo.isEmpty() && !password.isEmpty()
                && !firstName.isEmpty() && !lastName.isEmpty() && !birthDateNotConverted.isEmpty()
                && !email.isEmpty();
        boolean allRequiredFieldsAreValid = InputValidation.validateRequiredFields(pseudo, email,
                password, confirmPassword, firstName, lastName, birthDateNotConverted);
        boolean otherFieldsAreValid = InputValidation.validateNotRequiredFields(postCode, city,
                website);

        return requiredFieldsAreNotEmpty && allRequiredFieldsAreValid && otherFieldsAreValid;
    }
}
