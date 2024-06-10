package com.pvalentin.meeeam.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.network.response.LoginResponse;
import com.pvalentin.meeeam.data.network.response.PostResponse;
import com.pvalentin.meeeam.data.viewModel.ProfileViewModel;
import com.pvalentin.meeeam.databinding.FragmentProfileBinding;
import com.pvalentin.meeeam.util.Constants;
import com.pvalentin.meeeam.util.dateUtils.AgeCalculator;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Objects;

public class ProfileFragment extends Fragment {
  private static final String TAG = Constants.TAG + "." + ProfileFragment.class.getSimpleName();
  
  private FragmentProfileBinding binding;
  
  private ProfileViewModel profileViewModel;
  
  // Convertir dp en pixels
  private int dpToPx(int dp) {
    float density = getResources().getDisplayMetrics().density;
    return Math.round(dp * density);
  }
  
  // Méthode pour récupérer une ColorStateList à partir d'un attribut
  private ColorStateList getColorStateListFromAttr(int attr) {
    TypedArray typedArray = requireContext().obtainStyledAttributes(new int[]{attr});
    try {
      return typedArray.getColorStateList(0);
    } finally {
      typedArray.recycle();
    }
  }
  
  @SuppressLint("SetTextI18n")
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    
    binding = FragmentProfileBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    
    SharedPreferences authPrefs = requireContext().getSharedPreferences(Constants.AUTH_PREFS_FILE,
        Context.MODE_PRIVATE);
    LoginResponse.UserDetails userDetails = new Gson().fromJson(authPrefs.getString(
        "meeeam_user_details", null), LoginResponse.UserDetails.class);
    
    binding.profileUserName.setText(userDetails.getUserFirstName() + " " + userDetails.getUserLastName());
    
    if(userDetails.getGender() != null) {
      binding.profileUserGender.setVisibility(View.VISIBLE);
      binding.genderSeparator.setVisibility(View.VISIBLE);
      binding.profileUserGender.setText(userDetails.getGender());
    }
    
    int userAge = AgeCalculator.calculateAge(userDetails.getBirthDate());
    binding.profileUserAge.setText(userAge + " ans");
    
    if(userDetails.getVille() != null && !Objects.equals(userDetails.getVille(), "")){
      binding.profileUserLocationLayout.setVisibility(View.VISIBLE);
      binding.profileUserCity.setVisibility(View.VISIBLE);
      binding.locationSeparator.setVisibility(View.VISIBLE);
      binding.profileUserCity.setText(userDetails.getVille());
    }
    
    if(userDetails.getCountry() != null && !Objects.equals(userDetails.getCountry(), "")){
      binding.profileUserLocationLayout.setVisibility(View.VISIBLE);
      binding.profileUserCountry.setVisibility(View.VISIBLE);
      binding.locationSeparator.setVisibility(View.VISIBLE);
      binding.profileUserCountry.setText(userDetails.getCountry());
    }
    
    if(!userDetails.getWebsites().isEmpty()) {
      try {
        binding.profileUserLocationLayout.setVisibility(View.VISIBLE);
        binding.profileUserWebsite.setVisibility(View.VISIBLE);
        String website = userDetails.getWebsites().get(0).getUrlWebsite();
        URI websiteUri = new URI(website);
        String websiteName = websiteUri.getHost();
        binding.profileUserWebsite.setText(websiteName);
        
        SpannableString spannableString = getSpannableString(websiteName, website);
        binding.profileUserWebsite.setText(spannableString);
        binding.profileUserWebsite.setMovementMethod(LinkMovementMethod.getInstance());
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
    }
    
    binding.btnNewPublication.setOnClickListener(v -> {
      NavController navController = Navigation.findNavController(
          requireActivity(), R.id.nav_host_fragment_content_main_logged_in);
      navController.navigate(R.id.nav_new_post);
    });
    
    return root;
  }
  
  @NonNull
  private SpannableString getSpannableString(String websiteName, String website) {
    SpannableString spannableString = new SpannableString(websiteName);
    
    ClickableSpan clickableSpan = new ClickableSpan() {
      @Override
      public void onClick(@NonNull View widget) {
        
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(website));
        startActivity(intent);
      }
    };
    
    spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return spannableString;
  }
  
  @Override
  public void onResume() {
    super.onResume();
    binding.profileProgressLayout.setVisibility(View.VISIBLE);
    binding.profilePostsLayout.removeAllViews();
    profileViewModel.getPosts(this, requireContext());
  }
  
  public void displayPosts(PostResponse response) {
    binding.profileProgressLayout.setVisibility(View.GONE);
    for (PostResponse.Post post: response.getPosts()) {
      addPostToProfileView(post);
    }
  }
  
  private void addPostToProfileView(PostResponse.Post post) {
    // Créer le ConstraintLayout parent
    ConstraintLayout constraintLayout = new ConstraintLayout(requireContext());
    constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.MATCH_PARENT,
        ConstraintLayout.LayoutParams.MATCH_PARENT
    ));
    ConstraintLayout.LayoutParams constraintLayoutParams =
        (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();
    constraintLayoutParams.setMargins(0, dpToPx(10), 0, dpToPx(10));
    
    // Créer le LinearLayout principal
    LinearLayout mainLinearLayout = new LinearLayout(requireContext());
    mainLinearLayout.setId(View.generateViewId());
    mainLinearLayout.setOrientation(LinearLayout.VERTICAL);
    mainLinearLayout.setGravity(Gravity.CENTER);
    mainLinearLayout.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
    mainLinearLayout.setBackground(ContextCompat.getDrawable(requireContext(),
        R.drawable.rounded_rectangle_shape));
    mainLinearLayout.setBackgroundTintList(getColorStateListFromAttr(
        com.google.android.material.R.attr.colorOnBackground));
    mainLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ));
    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainLinearLayout.getLayoutParams();
    params.setMargins(0, dpToPx(10), 0, dpToPx(10));
    
    // Créer le TextView pour afficher la date
    TextView dateTextView = new TextView(requireContext());
    dateTextView.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ));
    LinearLayout.LayoutParams dateLayoutParams = (LinearLayout.LayoutParams) dateTextView.getLayoutParams();
    dateLayoutParams.setMargins(0, dpToPx(10), 0, 0);
    dateTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
    try {
      dateTextView.setText(post.getDateTimePost());
    } catch (ParseException e) {
      Log.d(TAG + ".addPostToProfileView", Objects.requireNonNull(e.getMessage()));
    }
    
    // Ajouter le TextView au LinearLayout principal
    mainLinearLayout.addView(dateTextView);
    
    // Créer le LinearLayout secondaire
    LinearLayout innerLinearLayout = new LinearLayout(requireContext());
    innerLinearLayout.setOrientation(LinearLayout.VERTICAL);
    innerLinearLayout.setGravity(Gravity.CENTER);
    innerLinearLayout.setPadding(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
    innerLinearLayout.setBackground(ContextCompat.getDrawable(requireContext(),
        R.drawable.rounded_rectangle_shape));
    TypedValue secondLayoutTypedValue = new TypedValue();
    requireContext().getTheme().resolveAttribute(com.google.android.material.R.attr.backgroundColor,
        secondLayoutTypedValue, true);
    innerLinearLayout.setBackgroundTintList(getColorStateListFromAttr(
        com.google.android.material.R.attr.backgroundColor));
    innerLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ));
    LinearLayout.LayoutParams innerLayoutParams = (LinearLayout.LayoutParams) innerLinearLayout.getLayoutParams();
    innerLayoutParams.setMargins(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
    
    
    // Créer l'ImageView
    try {
      if (post.getUrlPostFile() != null) {
        ImageView imageView = new ImageView(requireContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout.LayoutParams imageLayoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        imageLayoutParams.setMargins(0, dpToPx(10), 0, 0);
        
        int size = dpToPx(300);
        Glide.with(this)
            .load(post.getUrlPostFile())
            .override(size)
            .into(imageView);
        
        // Ajouter l'ImageView au LinearLayout secondaire
        innerLinearLayout.addView(imageView);
      }
    } catch (URISyntaxException e) {
      Log.d(TAG, "onCreateView: " + e.getMessage());
    }
    
    // Créer le TextView de la publication
    TextView postTextView = new TextView(requireContext());
    postTextView.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ));
    postTextView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
    postTextView.setText(post.getPostContent());
    LinearLayout.LayoutParams postLayoutParams = (LinearLayout.LayoutParams) postTextView.getLayoutParams();
    postLayoutParams.setMargins(0, dpToPx(10), 0, dpToPx(10));
    
    // Ajouter le TextView de la publication au LinearLayout secondaire
    innerLinearLayout.addView(postTextView);
    
    // Ajouter le LinearLayout secondaire au LinearLayout principal
    mainLinearLayout.addView(innerLinearLayout);
    
    // Ajouter le LinearLayout principal au ConstraintLayout
    constraintLayout.addView(mainLinearLayout);
    
    // Définir les contraintes pour le LinearLayout principal
    ConstraintSet constraintSet = new ConstraintSet();
    constraintSet.clone(constraintLayout);
    constraintSet.connect(
        mainLinearLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
    constraintSet.connect(
        mainLinearLayout.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
    constraintSet.connect(
        mainLinearLayout.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
    constraintSet.connect(
        mainLinearLayout.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
    constraintSet.setVerticalBias(mainLinearLayout.getId(), 0.0f);
    constraintSet.applyTo(constraintLayout);
    
    // Définir le layout principal comme conteneur de la vue
    binding.profilePostsLayout.addView(constraintLayout);
  }
  
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
  
}