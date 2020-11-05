package com.bvu.assistant.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Login;
import com.bvu.assistant.data.repository.retrofit.login.LoginAPI;
import com.bvu.assistant.databinding.ActivityLoginBinding;
import com.bvu.assistant.ui.base.BaseActivity;
import com.bvu.assistant.ui.main.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginActivityViewModel> {
    private static final String TAG = "LoginActivityTAG";
    private static final String SHARED_PREFERENCES_KEY = "LOGIN_ACTIVITY";
    private static final String CHECK_REMEMBER_PREF_KEY = "REMEMBER_CREDENTIALS";
    private static final String LAST_USERNAME_KEY = "LAST_USERNAME";
    private static final String LAST_PASSWORD_KEY = "LAST_PASSWORD";

    SharedPreferences pref;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = getViewDataBinding();
        VM = ViewModelProviders.of(this).get(LoginActivityViewModel.class);
        B.setViewModel(VM);

        pref = getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE);

        //  Check the checkbox's state (checked - or not --- from SharedPreferences)
        checkRememberState();
        assignEvents();
        observeFields();


        /*BlurView blurView = findViewById(R.id.blurUsernameView);
        float radius = 5f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(new RenderScriptBlur(this))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true);*/
    }

    private void observeFields() {
        VM.StudentId.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                saveCredentials(VM.Remembered.getValue() != null ? VM.Remembered.getValue(): false);
            }
        });

        VM.Password.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                saveCredentials(VM.Remembered.getValue() != null ? VM.Remembered.getValue(): false);
            }
        });

        VM.Remembered.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                saveCredentials(aBoolean);
            }
        });

        VM.Credentials.observe(this, new Observer<LoginCredentials>() {
            @Override
            public void onChanged(LoginCredentials loginCredentials) {
                if (checkEditTextsState()) {
                    if (hasNetwork()) {
                        login(new Login.Request(
                                loginCredentials.getStudentId(),
                                loginCredentials.getPassword(), getSSID()));
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Thông tin chưa hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        if (isRemembered)
            fillCredentials();
    }


    private void fillCredentials() {
        String lastUsername = pref.getString(LAST_USERNAME_KEY, "");
        String lastPassword = pref.getString(LAST_PASSWORD_KEY, "");

        VM.StudentId.setValue(lastUsername);
        VM.Password.setValue(lastPassword);
    }

    private void saveCredentials(boolean isRemember) {
        String username = "";
        String password = "";

        if (isRemember) {
            username = VM.StudentId.getValue() == null? "": VM.StudentId.getValue();
            password = VM.Password.getValue() == null? "": VM.Password.getValue();
        }

        pref.edit().putString(LAST_USERNAME_KEY, username).apply();
        pref.edit().putString(LAST_PASSWORD_KEY, password).apply();
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



    private void login(Login.Request request) {
        B.btnLogin.startAnimation();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.login_api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginAPI loginAPI = retrofit.create(LoginAPI.class);

        loginAPI.login(request)
                .enqueue(new Callback<Login.Response>() {
                    @Override
                    public void onResponse(Call<Login.Response> call, Response<Login.Response> response) {
                        B.btnLogin.revertAnimation();

                        if (response.body() != null && response.isSuccessful()) {
                            Login.Response result = response.body();

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
                    public void onFailure(Call<Login.Response> call, Throwable t) {
                        Snackbar.make(B.getRoot(), "Login failed, caused by Programmer", 4000).show();
                        Log.e(TAG, "onFailure: ", t.getCause());

                        B.btnLogin.revertAnimation();
                    }
                });
    }



    @Override
    public Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        B.btnLogin.dispose();
    }

}