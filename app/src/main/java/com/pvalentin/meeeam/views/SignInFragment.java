package com.pvalentin.meeeam.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.config.Constants;
import com.pvalentin.meeeam.databinding.FragmentSignInBinding;
import com.pvalentin.meeeam.models.SignInFormModel;
import com.pvalentin.meeeam.repositories.SignInRepository;
import com.pvalentin.meeeam.repositories.SignUpRepository;
import com.pvalentin.meeeam.response.RetrofitResponse;
import com.pvalentin.meeeam.utils.InputValidation;
import com.pvalentin.meeeam.utils.dateUtils.ConvertDateFormat;

import java.text.ParseException;
import java.util.Objects;

public class SignInFragment extends Fragment {

    private final String TAG = Constants.TAG;
    private final WelcomeFragment welcomeFragment;
    private final SignInFormModel signInForm;
    private final InputValidation inputValidation;

    private FragmentSignInBinding binding;

    public SignInFragment(WelcomeFragment welcomeFragment) {
        this.welcomeFragment = welcomeFragment;
        this.signInForm = new SignInFormModel("", "");
        inputValidation = new InputValidation();
    }

    public static SignInFragment newInstance(WelcomeFragment welcomeFragment) {
        return new SignInFragment(welcomeFragment);
    }

    private void enableButton() {
        try {
            String email = signInForm.getEmail();
            String password = signInForm.getPassword();

            boolean isFormValid = inputValidation.validateSignInForm(email, password);
            binding.confirmSignIn.setEnabled(isFormValid);
        } catch (Exception e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void disableButton() {
        try {
            binding.confirmSignIn.setEnabled(false);
        } catch (Exception e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void resetErrorView() {
        binding.signInErrorMessage.setVisibility(View.GONE);
        binding.signInErrorMessage.setText("");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        welcomeFragment.hideProgressBar();

        binding.signInInputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString();
                signInForm.setEmail(email);
                TextInputLayout signInLayoutEmail = binding.signInLayoutEmail;
                if (!email.isEmpty() && !InputValidation.validateEmail(email)) {
                    signInLayoutEmail.setError(getString(
                            R.string.error_message_email_not_valid));
                    disableButton();
                } else if (signInLayoutEmail.isErrorEnabled()) {
                    signInLayoutEmail.setError("");
                    signInLayoutEmail.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });
        binding.signInInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                signInForm.setPassword(password);
                TextInputLayout signInLayoutPassword = binding.signInLayoutPassword;
                if (!password.isEmpty() && !InputValidation.validatePassword(password)) {
                    signInLayoutPassword.setError(getString(
                            R.string.error_message_password_not_valid));
                    disableButton();
                } else if (signInLayoutPassword.isErrorEnabled()) {
                    signInLayoutPassword.setError("");
                    signInLayoutPassword.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });

        disableButton();

        binding.confirmSignIn.setOnClickListener(v -> submitSignInForm());
    }

    private void clearAllFields() {
        signInForm.setEmail("");
        binding.signInInputEmail.setText("");
        signInForm.setPassword("");
        binding.signInInputPassword.setText("");
    }

    private void submitSignInForm() {
        resetErrorView();

        SignInRepository.callSignUpService(this, signInForm, requireContext());

        binding.sendingSignInForm.setVisibility(View.VISIBLE);
    }

    public void displayServiceResponse(RetrofitResponse response) {
        binding.sendingSignInForm.setVisibility(View.GONE);
        switch (response.getStatus()) {
            case "success":
                Snackbar.make(requireContext(), binding.signInCoordinatorLayout,
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
                    fragmentTransaction.remove(WelcomeFragment.signInFragment);
                    fragmentTransaction.commit();
                }, delayMillis);
                break;
            case "error":
            case "failed":
                binding.signInErrorMessage.setVisibility(View.VISIBLE);
                binding.signInErrorMessage.setText(response.getMessage());
                break;

            default:
                binding.signInErrorMessage.setVisibility(View.VISIBLE);
                binding.signInErrorMessage.setText(getString(R.string.unknown_error));
        }
    }
}