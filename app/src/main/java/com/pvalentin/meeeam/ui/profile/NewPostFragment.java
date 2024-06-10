package com.pvalentin.meeeam.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.network.response.NewPostResponse;
import com.pvalentin.meeeam.data.viewModel.NewPostViewModel;
import com.pvalentin.meeeam.databinding.FragmentNewPostBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPostFragment extends Fragment {
  
  private NewPostViewModel newPostViewModel;
  private FragmentNewPostBinding binding;
  private ActivityResultLauncher<Intent> selectImageLauncher;
  private ActivityResultLauncher<String> requestPermissionLauncher;
  private TextInputEditText newPostTextInput;
  private ImageView newPostImageView;
  private Uri imageUri;
  
  
  
  public NewPostFragment() {
  }
  
  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment NewPostFragment.
   */
  public static NewPostFragment newInstance() {
    NewPostFragment fragment = new NewPostFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    newPostViewModel = new ViewModelProvider(this).get(NewPostViewModel.class);
    
    binding = FragmentNewPostBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    
    newPostImageView = binding.newPostImageView;
    newPostTextInput = binding.newPostTextInput;
    
    newPostTextInput.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      
      }
      
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      
      }
      
      @Override
      public void afterTextChanged(Editable s) {
      
      }
    });
    
    // Initialiser l'ActivityResultLauncher pour la sélection d'image
    selectImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
      if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
        imageUri = result.getData().getData();
        newPostImageView.setImageURI(imageUri);
        newPostImageView.setVisibility(View.VISIBLE);
      }
    });
    
    binding.newPostUploadFileBtn.setOnClickListener(v -> {
      if (checkPermission()) {
        selectImage();
      } else {
        requestPermission();
      }
    });
    
    binding.newPostBackBtn.setOnClickListener(v -> {
      navigateToProfile();
    });
    
    binding.newPostSubmitBtn.setOnClickListener(v -> {
      String text = Objects.requireNonNull(newPostTextInput.getText()).toString();
      if (text.isEmpty()) {
        Toast.makeText(getContext(), "Veuillez écrire quelque chose.", Toast.LENGTH_SHORT).show();
        return;
      }
      binding.newPostProgressLayout.setVisibility(View.VISIBLE);
      
      newPostViewModel.submitNewPost(this, requireContext(), text, imageUri);
    });
    
    return root;
  }
  
  private void selectImage() {
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("image/*");
    selectImageLauncher.launch(intent);
  }
  
  @Override
  public void onResume() {
    super.onResume();
  }
  
  public void displayNewPostResponse(NewPostResponse response) {
    binding.newPostProgressLayout.setVisibility(View.GONE);
    
    switch (response.getStatus()) {
      case "success":
        binding.newPostSuccessMessage.setText(response.getMessage());
        binding.newPostSuccessMessage.setVisibility(View.VISIBLE);
        binding.newPostTextInput.setText("");
        newPostImageView.setVisibility(View.GONE);
        break;
      case "error":
      case "failed":
        binding.newPostErrorMessage.setVisibility(View.VISIBLE);
        binding.newPostErrorMessage.setText(response.getMessage());
        break;
      
      default:
        binding.newPostErrorMessage.setVisibility(View.VISIBLE);
        binding.newPostErrorMessage.setText(getString(R.string.unknown_error));
    }
  }
  
  private boolean checkPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
    } else {
      return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
  }
  
  private void requestPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
    } else {
      requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }
  }
  
  public void navigateToProfile() {
    NavController navController = Navigation.findNavController(
        requireActivity(), R.id.nav_host_fragment_content_main_logged_in);
    navController.navigate(R.id.nav_profile);
  }
}