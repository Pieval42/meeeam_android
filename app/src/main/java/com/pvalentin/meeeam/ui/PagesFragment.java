package com.pvalentin.meeeam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pvalentin.meeeam.databinding.FragmentPagesBinding;
import com.pvalentin.meeeam.data.viewModel.PagesViewModel;

public class PagesFragment extends Fragment {
  
  private FragmentPagesBinding binding;
  
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    PagesViewModel pagesViewModel =
        new ViewModelProvider(this).get(PagesViewModel.class);
    
    binding = FragmentPagesBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    
    final TextView textView = binding.textPages;
    pagesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    return root;
  }
  
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}