package com.pvalentin.meeeam.views;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.config.Constants;
import com.pvalentin.meeeam.databinding.FragmentSignUpBinding;
import com.pvalentin.meeeam.models.CountryModel;
import com.pvalentin.meeeam.models.SignUpFormModel;
import com.pvalentin.meeeam.network.MeeeamApiService;
import com.pvalentin.meeeam.repositories.SignUpRepository;
import com.pvalentin.meeeam.request.ApiService;
import com.pvalentin.meeeam.response.CountryListResponse;
import com.pvalentin.meeeam.response.RetrofitResponse;
import com.pvalentin.meeeam.utils.InputValidation;
import com.pvalentin.meeeam.utils.dateUtils.ConvertDateFormat;
import com.pvalentin.meeeam.utils.dateUtils.DatePicker;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private static final String TAG = Constants.TAG;
    public static TextInputEditText dateInput;
    private final WelcomeFragment welcomeFragment;
    private final SignUpFormModel signUpForm;
    private final InputValidation inputValidation;
    private FragmentSignUpBinding binding;
    private List<CountryModel> countries;
    private ArrayList<String> countriesName = new ArrayList<>();
    private String birthDateNotConverted;
    private String confirmPassword;


    public SignUpFragment(WelcomeFragment welcomeFragment) {
        this.welcomeFragment = welcomeFragment;
        signUpForm = new SignUpFormModel(
                "", "", "", "", "", "",
                "", "", 0, "I", ""
        );
        inputValidation = new InputValidation();
    }

    public static SignUpFragment newInstance(WelcomeFragment welcomeFragment) {
        return new SignUpFragment(welcomeFragment);
    }

    private void enableButton() {
        try {
            String pseudo = signUpForm.getPseudo();
            String email = signUpForm.getEmail();
            String password = signUpForm.getPassword();
            String firstName = signUpForm.getFirstName();
            String lastName = signUpForm.getLastName();
            String postCode = signUpForm.getCode_postal();
            String city = signUpForm.getNom_ville();
            String website = signUpForm.getSite_web();

            boolean isFormValid = inputValidation.validateSignUpForm(pseudo, email, password,
                    confirmPassword, firstName, lastName, birthDateNotConverted, postCode,
                    city, website);
            binding.confirmSignUp.setEnabled(isFormValid);
        } catch (Exception e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void disableButton() {
        try {
            binding.confirmSignUp.setEnabled(false);
        } catch (Exception e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void resetErrorView() {
        binding.signUpErrorMessage.setVisibility(View.GONE);
        binding.signUpErrorMessage.setText("");
    }

    // TODO: Améliorer UX en chargeant la liste de pays dans onViewCreated
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countriesName = new ArrayList<>();
        getCountryList();
    }

    private void getCountryList() {
        MeeeamApiService apiService = ApiService.getMeeeamApiService();

        Call<CountryListResponse> responseCall = apiService.getCountries();
        assert responseCall != null;
        responseCall.enqueue(new Callback<CountryListResponse>() {
            @Override
            public void onResponse(@NonNull Call<CountryListResponse> call,
                                   @NonNull Response<CountryListResponse> response) {
                if (response.code() == 200) {
                    try {
                        assert response.body() != null;
                        Log.d(TAG, response.body().toString());
                        countries = new ArrayList<>(response.body().getCountries());
                        countriesName.add("");
                        for (CountryModel country : countries) {
                            countriesName.add(country.getNameFr());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                requireContext(),
                                R.layout.dropdown_menu_popup_item,
                                countriesName
                        );
                        AutoCompleteTextView signUpDropdownCountries = binding.signUpDropdownCountries;
                        signUpDropdownCountries.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        Log.d(TAG, response.errorBody().toString());
                    } catch (Exception e) {
                        Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CountryListResponse> call, @NonNull Throwable throwable) {
                try {
                    throw throwable;
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        welcomeFragment.hideProgressBar();

        binding.signUpInputPseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String pseudo = s.toString();
                    signUpForm.setPseudo(pseudo);
                    TextInputLayout signUpLayoutPseudo = binding.signUpLayoutPseudo;
                    if (!pseudo.isEmpty() && !InputValidation.validatePseudo(pseudo)) {
                        signUpLayoutPseudo.setError(getString(
                                R.string.error_message_nickname_not_valid));
                        disableButton();
                    } else if (signUpLayoutPseudo.isErrorEnabled()) {
                        signUpLayoutPseudo.setError("");
                        signUpLayoutPseudo.setErrorEnabled(false);
                    }
                    enableButton();
                    resetErrorView();
                } catch (Exception e) {
                    Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                }
            }
        });
        binding.signUpInputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString();
                signUpForm.setEmail(email);
                TextInputLayout signUpLayoutEmail = binding.signUpLayoutEmail;
                if (!email.isEmpty() && !InputValidation.validateEmail(email)) {
                    signUpLayoutEmail.setError(getString(
                            R.string.error_message_email_not_valid));
                    disableButton();
                } else if (signUpLayoutEmail.isErrorEnabled()) {
                    signUpLayoutEmail.setError("");
                    signUpLayoutEmail.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });
        binding.signUpInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                signUpForm.setPassword(password);
                TextInputLayout signUpLayoutPassword = binding.signUpLayoutPassword;
                if (!password.isEmpty() && !InputValidation.validatePassword(password)) {
                    signUpLayoutPassword.setError(getString(
                            R.string.error_message_password_not_valid));
                    disableButton();
                } else if (signUpLayoutPassword.isErrorEnabled()) {
                    signUpLayoutPassword.setError("");
                    signUpLayoutPassword.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });
        binding.signUpInputConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = signUpForm.getPassword();
                confirmPassword = s.toString();
                TextInputLayout signUpLayoutConfirmPassword = binding.signUpLayoutConfirmPassword;
                if (!confirmPassword.isEmpty() && !InputValidation.validateConfirmPassword(password, confirmPassword)) {
                    signUpLayoutConfirmPassword.setError(getString(
                            R.string.error_message_passwords_dont_match));
                    disableButton();
                } else if (signUpLayoutConfirmPassword.isErrorEnabled()) {
                    signUpLayoutConfirmPassword.setError("");
                    signUpLayoutConfirmPassword.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });
        binding.signUpInputFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String firstName = s.toString();
                signUpForm.setFirstName(firstName);
                TextInputLayout signUpLayoutFirstName = binding.signUpLayoutFirstName;
                if (!firstName.isEmpty() && !InputValidation.validateFirstName(firstName)) {
                    signUpLayoutFirstName.setError(getString(
                            R.string.error_message_first_name_not_valid));
                    disableButton();
                } else if (signUpLayoutFirstName.isErrorEnabled()) {
                    signUpLayoutFirstName.setError("");
                    signUpLayoutFirstName.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });
        binding.signUpInputLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String lastName = s.toString();
                signUpForm.setLastName(lastName);
                TextInputLayout signUpLayoutLastName = binding.signUpLayoutLastName;
                if (!lastName.isEmpty() && !InputValidation.validateLastName(lastName)) {
                    signUpLayoutLastName.setError(getString(
                            R.string.error_message_first_name_not_valid));
                    disableButton();
                } else if (signUpLayoutLastName.isErrorEnabled()) {
                    signUpLayoutLastName.setError("");
                    signUpLayoutLastName.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });
        binding.signUpLayoutBirthdate.setEndIconOnClickListener(v -> {
            DatePicker datePickerDialogFragment;
            datePickerDialogFragment = new DatePicker();
            datePickerDialogFragment.show(getChildFragmentManager(), "DATE PICK");
        });
        dateInput = binding.signUpInputBirthdate;
        dateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    birthDateNotConverted = s.toString();
                    TextInputLayout signUpLayoutBirthdate = binding.signUpLayoutBirthdate;
                    if (!birthDateNotConverted.isEmpty() && !InputValidation.validateBirthDate(birthDateNotConverted)) {
                        signUpLayoutBirthdate.setError(getString(
                                R.string.error_message_date_not_valid));
                        disableButton();
                    } else if (signUpLayoutBirthdate.isErrorEnabled()) {
                        signUpLayoutBirthdate.setError("");
                        signUpLayoutBirthdate.setErrorEnabled(false);
                    }
                    enableButton();
                    resetErrorView();
                } catch (Exception e) {
                    Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                }
            }
        });

        binding.signUpInputPostCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String postCode = s.toString();
                signUpForm.setCode_postal(postCode);
                TextInputLayout signUpLayoutPostCode = binding.signUpLayoutPostCode;
                if (!postCode.isEmpty() && !InputValidation.validatePostCode(postCode)) {
                    signUpLayoutPostCode.setError(getString(
                            R.string.error_message_postcode_not_valid));
                    disableButton();
                } else if (signUpLayoutPostCode.isErrorEnabled()) {
                    signUpLayoutPostCode.setError("");
                    signUpLayoutPostCode.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });
        binding.signUpInputCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String city = s.toString();
                signUpForm.setNom_ville(city);
                TextInputLayout signUpLayoutCity = binding.signUpLayoutCity;
                if (!city.isEmpty() && !InputValidation.validateCity(city)) {
                    signUpLayoutCity.setError(getString(
                            R.string.error_message_city_not_valid));
                    disableButton();
                } else if (signUpLayoutCity.isErrorEnabled()) {
                    signUpLayoutCity.setError("");
                    signUpLayoutCity.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });

        binding.signUpRadioMan.setOnCheckedChangeListener((buttonView, isChecked) -> signUpForm.setId_genre("H"));
        binding.signUpRadioWoman.setOnCheckedChangeListener((buttonView, isChecked) -> signUpForm.setId_genre("F"));
        binding.signUpRadioNonBinary.setOnCheckedChangeListener((buttonView, isChecked) -> signUpForm.setId_genre("N"));

        binding.signUpInputWebsite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String website = s.toString();
                signUpForm.setSite_web(website);
                TextInputLayout signUpLayoutWebsite = binding.signUpLayoutWebsite;
                if (!website.isEmpty() && !InputValidation.validateWebsite(website)) {
                    signUpLayoutWebsite.setError(getString(
                            R.string.error_message_website_not_valid));
                    disableButton();
                } else if (signUpLayoutWebsite.isErrorEnabled()) {
                    signUpLayoutWebsite.setError("");
                    signUpLayoutWebsite.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });

        disableButton();

        binding.confirmSignUp.setOnClickListener(v -> submitSignUpForm());
    }

    private void clearAllFields() {
        signUpForm.setPseudo("");
        binding.signUpInputPseudo.setText("");
        signUpForm.setEmail("");
        binding.signUpInputEmail.setText("");
        signUpForm.setPassword("");
        binding.signUpInputPassword.setText("");
        confirmPassword = "";
        binding.signUpInputConfirmPassword.setText("");
        signUpForm.setFirstName("");
        binding.signUpInputFirstName.setText("");
        signUpForm.setLastName("");
        binding.signUpInputLastName.setText("");
        signUpForm.setBirthDate("");
        binding.signUpInputBirthdate.setText("");
        signUpForm.setCode_postal("");
        binding.signUpInputPostCode.setText("");
        signUpForm.setNom_ville("");
        binding.signUpInputCity.setText("");
        signUpForm.setId_pays(0);
        binding.signUpDropdownCountries.setText(null);
        signUpForm.setId_genre("I");
        binding.signUpRadioGroupGender.clearCheck();
        signUpForm.setSite_web("");
        binding.signUpInputWebsite.setText("");
    }

    private void submitSignUpForm() {
        try {
            resetErrorView();
            signUpForm.setBirthDate(ConvertDateFormat.convertDateFormat(birthDateNotConverted));
            String countryName = String.valueOf(binding.signUpDropdownCountries.getText());
            int idPays = countriesName.indexOf(countryName);
            signUpForm.setId_pays(idPays);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        SignUpRepository.callSignUpService(this, signUpForm, requireContext());

        binding.sendingSignUpForm.setVisibility(View.VISIBLE);
    }

    public void displayServiceResponse(RetrofitResponse response) {
        binding.sendingSignUpForm.setVisibility(View.GONE);
        switch (response.getStatus()) {
            case "success":
                Snackbar.make(requireContext(), binding.signUpCoordinatorLayout,
                                response.getMessage(),
                                Snackbar.LENGTH_LONG)
                        .show();
                clearAllFields();
                Handler handler = new Handler();
                int delayMillis = 3000;
                handler.postDelayed(() -> {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager
                            .beginTransaction();
                    fragmentTransaction.remove(WelcomeFragment.signUpFragment);
                    fragmentTransaction.commit();
                }, delayMillis);
                break;
            case "error":
            case "failed":
                binding.signUpErrorMessage.setVisibility(View.VISIBLE);
                binding.signUpErrorMessage.setText(response.getMessage());
                break;

            default:
                binding.signUpErrorMessage.setVisibility(View.VISIBLE);
                binding.signUpErrorMessage.setText(getString(R.string.unknown_error));
        }
    }
}