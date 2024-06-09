package com.pvalentin.meeeam.data.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PagesViewModel extends ViewModel {
  
  private final MutableLiveData<String> mText;
  
  public PagesViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("Pages publiques");
  }
  
  public LiveData<String> getText() {
    return mText;
  }
}