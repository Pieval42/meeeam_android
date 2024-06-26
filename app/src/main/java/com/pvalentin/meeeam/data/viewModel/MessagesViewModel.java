package com.pvalentin.meeeam.data.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pvalentin.meeeam.ui.messages.MessagesFragment;
import com.pvalentin.meeeam.data.repository.MessagesRepository;
import com.pvalentin.meeeam.data.network.response.MessagesResponse;

public class MessagesViewModel extends ViewModel {
  
  private final MutableLiveData<String> mText;
  
  public MessagesViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("Messages");
  }
  
  public void getMessages(MessagesFragment messagesFragment, Context context) {
    MessagesRepository.callMessagesService(
        context, new MessagesRepository.MessagesCallback() {
          
          @Override
          public void onSuccess(MessagesResponse response) {
            messagesFragment.displayMessages(response);
          }
          
          @Override
          public void onError(MessagesResponse response) {
            messagesFragment.displayError(response);
          }
        });
  }
  
  public LiveData<String> getText() {
    return mText;
  }
}