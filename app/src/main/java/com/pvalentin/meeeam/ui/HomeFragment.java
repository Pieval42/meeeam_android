package com.pvalentin.meeeam.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public static SignUpFragment signUpFragment;
    public static LoginFragment loginFragment;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(v -> {
            binding.homeProgressBar.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            loginFragment = LoginFragment.newInstance(this);
            fragmentTransaction.add(R.id.fragment_container_view, loginFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            binding.homeProgressBar.setVisibility(View.VISIBLE);
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            signUpFragment = SignUpFragment.newInstance(this);
            fragmentTransaction.add(R.id.fragment_container_view, signUpFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }

    public void hideProgressBar() {
        binding.homeProgressBar.setVisibility(View.INVISIBLE);
    }
}