package com.pvalentin.meeeam.ui.messages;

import static com.pvalentin.meeeam.util.dateUtils.ConvertDateFormat.convertDateFormatSqlToFr;
import static com.pvalentin.meeeam.util.htmlSpecialChars.decodeHtmlSpecialChars;

import com.pvalentin.meeeam.R;
import com.pvalentin.meeeam.data.viewModel.MessagesViewModel;
import com.pvalentin.meeeam.data.network.response.MessagesResponse;
import com.pvalentin.meeeam.databinding.FragmentMessagesBinding;
import com.pvalentin.meeeam.util.Constants;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Objects;

public class MessagesFragment extends Fragment {
  private static final String TAG = Constants.TAG + "." + MessagesFragment.class.getSimpleName();
  private MessagesViewModel messagesViewModel;
  private FragmentMessagesBinding binding;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
  }
  
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    
    binding = FragmentMessagesBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }
  
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
  
  @Override
  public void onResume() {
    super.onResume();
    binding.messagesProgressLayout.setVisibility(View.VISIBLE);
    binding.messagesMainLayout.removeAllViews();
    messagesViewModel.getMessages(this, requireContext());
  }
  
  public void displayMessages(MessagesResponse response){
    binding.messagesProgressLayout.setVisibility(View.GONE);
    Log.d(TAG, response.getStatus() + " "
        + response.getMessage() + " " + new Gson().toJson(response.getMessages())
    );
    for (MessagesResponse.Message message : response.getMessages()) {
      Log.d(TAG, message.getContenu_message());
      addMessageTextView(message);
    }
  }
  @SuppressLint("SetTextI18n")
  private void addMessageTextView(MessagesResponse.Message message) {
    try {
      String contenuMessage = decodeHtmlSpecialChars(message.getContenu_message());
      TextView textView = new TextView(getContext());
      textView.setText("Expéditeur: " + message.getPseudo_expediteur() + "\n"
          + "Destinataire: " + message.getPseudo_destinataire() + "\n"
          + "Message: " + contenuMessage + "\n"
          + "Date: " + convertDateFormatSqlToFr(message.getDate_heure_message()));
      textView.setPadding(16, 16, 16, 16);
      textView.setTextSize(16);
      textView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_shape, null));
      binding.messagesMainLayout.addView(textView);
    } catch (ParseException e) {
      Log.e(TAG, Objects.requireNonNull(e.getMessage()));
    }
  }
  
  public void displayError(MessagesResponse response) {
    binding.messagesProgressLayout.setVisibility(View.GONE);
    TextView textView = new TextView(getContext());
    textView.setText(response.getMessage());
    binding.messagesMainLayout.addView(textView);
  }
}