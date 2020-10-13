package com.bvu.assistant.view.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.ActivityLoginBinding;
import com.bvu.assistant.receiver.NetworkStatusChecker;
import com.bvu.assistant.receiver.NetworkStatusReceiver;
import com.bvu.assistant.viewmodel.retrofit.login.LoginAPI;
import com.bvu.assistant.viewmodel.retrofit.login.LoginRequest;
import com.bvu.assistant.viewmodel.retrofit.login.LoginResponse;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivityTAG";
    private static final String SHARED_PREFERENCES_KEY = "LOGIN_ACTIVITY";
    private static final String CHECK_REMEMBER_PREF_KEY = "REMEMBER_CREDENTIALS";
    private static final String LAST_USERNAME_KEY = "LAST_USERNAME";
    private static final String LAST_PASSWORD_KEY = "LAST_PASSWORD";


    ActivityLoginBinding B;
    SharedPreferences pref;
    NetworkStatusReceiver networkReceiver;


    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences("MAIN_ACTIVITY", Context.MODE_PRIVATE);
        boolean isDark = preferences.getBoolean("darkmode", false);
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_login);


        //  Check the checkbox's state (checked - or not --- from SharedPreferences)
        checkRememberState();


        assignEvents();


        networkReceiver = new NetworkStatusReceiver();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        B.btnLogin.dispose();
        unregisterReceiver(networkReceiver);
    }



    @SuppressLint("ClickableViewAccessibility")
    private void assignEvents() {
        B.edtUsername.setOnEditorActionListener((v, actionId, event) -> {
            if (B.edtUsername.getText() == null || B.edtUsername.getText().length() == 0) {
                B.edtUsername.setError("Vui lòng điền vào trường này");
                B.edtUsername.requestFocus();
                return true;
            }

            B.edtUsername.setError(null);
            return false;
        });


        //  Login when perform Password EditText action (the action button on keyboard)
        B.edtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (B.edtPassword.getText() == null || B.edtPassword.getText().length() == 0) {
                B.edtPassword.setError("Vui lòng điền vào trường này");
                B.edtPassword.requestFocus();
                return true;
            }

            B.edtPassword.setError(null);
            B.btnLogin.performClick();
            return false;
        });


        B.checkRemember.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            pref.edit().putBoolean(CHECK_REMEMBER_PREF_KEY, isChecked).apply();
            saveCredentials(isChecked);
        });


        B.btnLogin.setOnClickListener(view -> {
            if (!checkEditTextsState()) return;

            hideKeyboard(LoginActivity.this);
            saveCredentials(B.checkRemember.isChecked());

            if (hasNetwork()) {
                B.btnLogin.startAnimation();    //  start the Morph anim

                login(new LoginRequest(
                        B.edtUsername.getText() == null? "": B.edtUsername.getText().toString(),
                        B.edtPassword.getText() == null? "": B.edtPassword.getText().toString(),
                        getSSID())
                );
            }
            else {
                Snackbar snackbar = Snackbar.make(B.getRoot(), "No internet connection", 3000);
                snackbar.show();
            }
        });


        //  Hide keyboard on touch outside (on the bounder layout)
        B.bounder.setOnTouchListener((v, event) -> {
            hideKeyboard(LoginActivity.this);
            return false;
        });
    }


    private boolean checkEditTextsState() {
        if (B.edtUsername.getText() == null || B.edtUsername.getText().length() == 0) {
            B.edtUsername.requestFocus();
            B.edtUsername.setError("Vui lòng điền vào trường này");
            return false;
        }

        B.edtUsername.setError(null);
        if (B.edtPassword.getText() == null || B.edtPassword.getText().length() == 0) {
            B.edtPassword.requestFocus();
            B.edtPassword.setError("Vui lòng điền vào trường này");
            return false;
        }


        B.edtPassword.setError(null);
        return true;
    }


    private void checkRememberState() {
        pref = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        boolean isKeyExits = pref.contains(CHECK_REMEMBER_PREF_KEY);

        if (!isKeyExits) {
            Log.w(TAG, "onCreate: REMEMBER_KEY doesn't exists");
            pref.edit().putBoolean(CHECK_REMEMBER_PREF_KEY, false).apply();
        }


        boolean isRemembered = pref.getBoolean(CHECK_REMEMBER_PREF_KEY, false);
        B.checkRemember.setChecked(isRemembered);


        //  Fill out the edtUsername & edtPassword if here is REMEMBERED
        fillCredentials();
    }

    private void fillCredentials() {
        String lastUsername = pref.getString(LAST_USERNAME_KEY, "");
        String lastPassword = pref.getString(LAST_PASSWORD_KEY, "");

        B.edtUsername.setText(lastUsername);
        B.edtPassword.setText(lastPassword);
    }

    private void saveCredentials(boolean isRemember) {
        String username = "";
        String password = "";

        if (isRemember) {
            username = B.edtUsername.getText() == null? "": B.edtUsername.getText().toString();
            password = B.edtPassword.getText() == null? "": B.edtPassword.getText().toString();
        }

        pref.edit().putString(LAST_USERNAME_KEY, username).apply();
        pref.edit().putString(LAST_PASSWORD_KEY, password).apply();
    }

    private boolean hasNetwork() {
        switch (NetworkStatusChecker.checkNetworkStatus(LoginActivity.this)) {
            case "Wifi enabled": case "Mobile enabled": return true;
            default: return false;
        }
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();

        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private String getSSID() {
        String result = "";

        if (pref.contains("SSID")) {
            result = pref.getString("SSID", "");
        }
        else {
            pref.edit().putString("SSID", "").apply();
        }

        return result;
    }


    private void login(LoginRequest request) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.login_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI loginAPI = retrofit.create(LoginAPI.class);


        loginAPI.login(request)
            .enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    B.btnLogin.revertAnimation();

                    if (response.body() != null && response.isSuccessful()) {
                        LoginResponse result = response.body();

                        //  Update the SSID
                        pref.edit().putString("SSID", result.getSessionId()).apply();


                        if (result.getLoginSuccess()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("ssid", pref.getString("SSID", ""));
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        else {
                            Snackbar.make(B.getRoot(), "Login failed, please check your credentials", 5500).show();
                        }
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Snackbar.make(B.getRoot(), "Login failed, caused by Programmer", 4000).show();
                    Log.e(TAG, "onFailure: ", t.getCause());

                    B.btnLogin.revertAnimation();
                }
            });
    }

}