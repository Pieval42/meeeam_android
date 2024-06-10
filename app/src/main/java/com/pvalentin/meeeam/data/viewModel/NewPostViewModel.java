package com.pvalentin.meeeam.data.viewModel;

import static com.pvalentin.meeeam.util.Constants.TAG;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.loader.content.CursorLoader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.network.response.NewPostResponse;
import com.pvalentin.meeeam.data.repository.NewPostRepository;
import com.pvalentin.meeeam.ui.home.HomeFragment;
import com.pvalentin.meeeam.ui.profile.NewPostFragment;

import java.io.File;
import java.io.InputStream;

public class NewPostViewModel extends ViewModel {
  
  public NewPostViewModel() {
  
  }
  
  public void submitNewPost(NewPostFragment newPostFragment, Context context, String text, Uri imageUri) {
    String extension = null;
    try {
      byte[] imageData;
      InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
      String mimeType = context.getContentResolver().getType(imageUri);
      
      if (mimeType != null) {
        extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
      } else {
        String path = imageUri.getPath();
        if (path != null) {
          int dotIndex = path.lastIndexOf('.');
          if (dotIndex > 0) {
            extension = path.substring(dotIndex + 1);
          }
        }
      }
      if (inputStream != null) {
        imageData = new byte[inputStream.available()];
        inputStream.read(imageData);
        inputStream.close();
      } else {
        throw new Exception("InputStream is null");
      }
      
      
      NewPostRepository.callNewPostService(
          context, text, imageData, extension, new NewPostRepository.NewPostCallback() {
            
            @Override
            public void onSuccess(NewPostResponse response) {
              newPostFragment.displayNewPostResponse(response);
              Handler handler = new Handler();
              int delayMillis = 3000;
              handler.postDelayed(newPostFragment::navigateToProfile, delayMillis);
              
            }
            
            @Override
            public void onError(NewPostResponse response) {
              newPostFragment.displayNewPostResponse(response);
            }
          });
    } catch (Exception e) {
      Log.d(TAG, "submitNewPost: " + e.getMessage());
    }
    
    
    
  }
  
  private String getRealPathFromURI(Uri contentUri, Context context) {
    String[] proj = {MediaStore.Images.Media.DATA};
    CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
    Cursor cursor = loader.loadInBackground();
    if (cursor != null) {
      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      cursor.moveToFirst();
      String result = cursor.getString(column_index);
      cursor.close();
      return result;
    } else {
      return null;
    }
  }
}


