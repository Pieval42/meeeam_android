package com.pvalentin.meeeam.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

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
import com.pvalentin.meeeam.util.Constants;
import com.pvalentin.meeeam.data.viewModel.LoginViewModel;
import com.pvalentin.meeeam.databinding.FragmentLoginBinding;
import com.pvalentin.meeeam.data.network.request.LoginRequest;
import com.pvalentin.meeeam.data.network.response.ApiResponse;
import com.pvalentin.meeeam.util.InputValidation;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private final String TAG = Constants.TAG;
    private final HomeFragment homeFragment;
    private final LoginRequest loginRequest;
    private final InputValidation inputValidation;
    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;

    public LoginFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        this.loginRequest = new LoginRequest("", "");
        inputValidation = new InputValidation();
    }

    public static LoginFragment newInstance(HomeFragment homeFragment) {
        return new LoginFragment(homeFragment);
    }

    private void enableButton() {
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            boolean isFormValid = inputValidation.validateLoginForm(email, password);
            binding.confirmLogin.setEnabled(isFormValid);
        } catch (Exception e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void disableButton() {
        try {
            binding.confirmLogin.setEnabled(false);
        } catch (Exception e) {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    private void resetErrorView() {
        binding.loginErrorMessage.setVisibility(View.GONE);
        binding.loginErrorMessage.setText("");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.isAuthenticated().observe(this, isAuthenticated -> {
            if (isAuthenticated != null && isAuthenticated) {
                // L'utilisateur est authentifié, passer à l'activité principale
                // TODO: redirection vers fragment profil
            } else {
                // TODO : Afficher un message d'erreur

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeFragment.hideProgressBar();

        binding.loginInputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString();
                loginRequest.setEmail(email);
                TextInputLayout loginLayoutEmail = binding.loginLayoutEmail;
                if (!email.isEmpty() && !InputValidation.validateEmail(email)) {
                    loginLayoutEmail.setError(getString(
                            R.string.error_message_email_not_valid));
                    disableButton();
                } else if (loginLayoutEmail.isErrorEnabled()) {
                    loginLayoutEmail.setError("");
                    loginLayoutEmail.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });
        binding.loginInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = s.toString();
                loginRequest.setPassword(password);
                TextInputLayout loginLayoutPassword = binding.loginLayoutPassword;
                if (!password.isEmpty() && !InputValidation.validatePassword(password)) {
                    loginLayoutPassword.setError(getString(
                            R.string.error_message_password_not_valid));
                    disableButton();
                } else if (loginLayoutPassword.isErrorEnabled()) {
                    loginLayoutPassword.setError("");
                    loginLayoutPassword.setErrorEnabled(false);
                }
                enableButton();
                resetErrorView();
            }
        });

        disableButton();

        binding.confirmLogin.setOnClickListener(v -> submitLoginForm());
    }

    private void clearAllFields() {
        loginRequest.setEmail("");
        binding.loginInputEmail.setText("");
        loginRequest.setPassword("");
        binding.loginInputPassword.setText("");
    }

    private void submitLoginForm() {
        resetErrorView();

        loginViewModel.login(this, loginRequest, requireContext());

        binding.sendingLoginForm.setVisibility(View.VISIBLE);
    }

    public void displayServiceResponse(ApiResponse response) {
        binding.sendingLoginForm.setVisibility(View.GONE);
        switch (response.getStatus()) {
            case "success":
                Snackbar.make(requireContext(), binding.loginCoordinatorLayout,
                                response.getMessage(),
                                Snackbar.LENGTH_LONG)
                        .show();
                clearAllFields();
                break;
            case "error":
            case "failed":
                binding.loginErrorMessage.setVisibility(View.VISIBLE);
                binding.loginErrorMessage.setText(response.getMessage());
                break;

            default:
                binding.loginErrorMessage.setVisibility(View.VISIBLE);
                binding.loginErrorMessage.setText(getString(R.string.unknown_error));
        }
    }
    
    public void navigateToMainLoggedInFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
            .beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.fragment_container_view, MainLoggedInFragment.class, null);
        fragmentTransaction.commit();
    }
    
    public void navigateToMainLoggedInActivity() {
        Intent intent = new Intent(getActivity(), MainLoggedInActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}