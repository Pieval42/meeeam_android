package com.pvalentin.meeeam.data.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pvalentin.meeeam.data.network.response.MessagesResponse;
import com.pvalentin.meeeam.data.network.response.PostResponse;
import com.pvalentin.meeeam.data.repository.MessagesRepository;
import com.pvalentin.meeeam.data.repository.PostRepository;
import com.pvalentin.meeeam.ui.MessagesFragment;
import com.pvalentin.meeeam.ui.ProfileFragment;

public class ProfileViewModel extends ViewModel {
  
  private final MutableLiveData<String> mText;
  
  public ProfileViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("Profil");
  }
  
  public void getPosts(ProfileFragment profileFragment, Context context) {
    PostRepository.callPostService(
        context, new PostRepository.PostCallback() {
          
          @Override
          public void onSuccess(PostResponse response) {
            profileFragment.displayPosts(response);
          }
          
          @Override
          public void onError(PostResponse response) {
            profileFragment.displayPosts(response);
          }
        });
  }
  
  public LiveData<String> getText() {
    return mText;
  }
}