package com.pvalentin.meeeam.ui;

import static com.pvalentin.meeeam.util.Constants.AUTH_PREFS_FILE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.gson.Gson;
import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.network.NetworkClient;
import com.pvalentin.meeeam.data.network.TokenAuthenticator;
import com.pvalentin.meeeam.data.network.response.LoginResponse;
import com.pvalentin.meeeam.databinding.ActivityMainLoggedInBinding;

public class MainLoggedInActivity extends MainActivity {
  
  private AppBarConfiguration mAppBarConfiguration;
  private ActivityMainLoggedInBinding binding;
  public NetworkClient networkClient;
  public TokenAuthenticator tokenAuthenticator;
  private SharedPreferences authPrefs;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    this.networkClient = new NetworkClient(this);
    this.tokenAuthenticator = new TokenAuthenticator(this);
    this.authPrefs = this.getSharedPreferences(
        AUTH_PREFS_FILE, Context.MODE_PRIVATE
    );
    
    binding = ActivityMainLoggedInBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    
    setSupportActionBar(binding.appBarMainLoggedIn.toolbar);
    
    DrawerLayout drawer = binding.drawerLayout;
    NavigationView navigationView = binding.navView;
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.nav_messages, R.id.nav_profile, R.id.nav_pages)
        .setOpenableLayout(drawer)
        .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_logged_in);
    NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
    NavigationUI.setupWithNavController(navigationView, navController);
    
    // Personnalisation de la status bar et de la navigation bar
    TypedValue toolbarTypedValue = new TypedValue();
    TypedValue navigationTypedValue = new TypedValue();
    getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnBackground, toolbarTypedValue, true);
    getTheme().resolveAttribute(com.google.android.material.R.attr.backgroundColor, navigationTypedValue, true);
    int toolbarColor = toolbarTypedValue.data;
    int navigationColor = navigationTypedValue.data;
    
    Window window = getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(toolbarColor);
    window.setNavigationBarColor(navigationColor);
    
    

  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main_logged_in, menu);
    TextView headerSideMenuUserName = findViewById(R.id.header_user_name);
    LoginResponse.UserDetails userDetails = new Gson().fromJson(authPrefs.getString(
        "meeeam_user_details", null), LoginResponse.UserDetails.class);
    String userName = userDetails.getUserPseudo();
    headerSideMenuUserName.setText(userName);
    return true;
  }
  
  @Override
  public boolean onSupportNavigateUp() {
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_logged_in);
    return NavigationUI.navigateUp(navController, mAppBarConfiguration)
        || super.onSupportNavigateUp();
  }
}