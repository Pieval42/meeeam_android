package com.pvalentin.meeeam;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pvalentin.meeeam.data.network.NetworkClient;
import com.pvalentin.meeeam.data.network.TokenAuthenticator;
import com.pvalentin.meeeam.util.Constants;
import com.pvalentin.meeeam.util.dateUtils.AgeCalculator;

public class SubApplication extends Application {
  private final String TAG = Constants.TAG + "." + SubApplication.class.getSimpleName();
  private SubApplication instance;
  public Activity mActivity;
  

  private TokenAuthenticator tokenAuthenticator;
  private NetworkClient networkClient;
  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    networkClient = new NetworkClient(this);
    tokenAuthenticator = new TokenAuthenticator(this);
    
    AgeCalculator.init(this);
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      
      @Override
      public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
      
      }
      
      @Override
      public void onActivityStarted(@NonNull Activity activity) {
        mActivity = activity;
      }
      
      @Override
      public void onActivityResumed(@NonNull Activity activity) {
        mActivity = activity;
        Log.d(TAG, "onActivityResumed:"+activity.getLocalClassName());
      }
      
      @Override
      public void onActivityPaused(@NonNull Activity activity) {
        mActivity = null;
      }
      
      @Override
      public void onActivityStopped(@NonNull Activity activity) {
      
      }
      
      @Override
      public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
      
      }
      
      @Override
      public void onActivityDestroyed(@NonNull Activity activity) {
      
      }
    });
  }
  public SubApplication getInstance() {
    return instance;
  }
  
  public TokenAuthenticator getTokenAuthenticator() {
    return tokenAuthenticator;
  }
  
  public NetworkClient getNetworkClient() {
    return networkClient;
  }
  
  public Context getContext(){
    return instance.getApplicationContext();
  }
}
