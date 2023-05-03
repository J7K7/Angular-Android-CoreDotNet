package com.example.ehealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ehealth.entity.Users;
import com.example.ehealth.retrofit.EndPoint;
import com.example.ehealth.retrofit.LoginResponse;
import com.example.ehealth.retrofit.RetrofitEndPoint;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
        googleSignInClient.signOut();

        findViewById(R.id.ivLoginActivityGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, 1000);
            }
        });

        findViewById(R.id.btnLoginActivitySignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnLoginActivitySignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etUsername = findViewById(R.id.etLoginActivityUsername);
                EditText etPassword = findViewById(R.id.etLoginActivityPassword);

                if (etUsername.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username is Empty", Toast.LENGTH_SHORT).show();
                } else if (etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    users.setUserName(etUsername.getText().toString());
                    users.setPassword(etPassword.getText().toString());

                    LoginAPICall(users);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> googleSignInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = googleSignInAccountTask.getResult(ApiException.class);

                users.setActive(true);
                users.setGoogle(true);
                users.setUserType(Byte.parseByte("4"));
                users.setEmail(account.getEmail());
                users.setFirstName(account.getGivenName());
                users.setLastName(account.getFamilyName());
                users.setUserName(account.getDisplayName());

                LoginAPICall(users);

            } catch (Exception ex) {
                Toast.makeText(this, "Google login fail...!!!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void LoginAPICall(Users user) {
        EndPoint endPoint = RetrofitEndPoint.RetrofitEndPoint(getString(R.string.api_url));

        Call<LoginResponse> patientLogin = endPoint.PatientLogin(users);

        patientLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body().getItem1().equals("Patient")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("Patient", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putInt("PatientId", response.body().getItem2().getId());
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (response.body().getItem1().equals("Admin") || response.body().getItem1().equals("Hospital")
                                || response.body().getItem1().equals("Doctor")) {
                            Toast.makeText(LoginActivity.this, "User not found...!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, response.body().getItem1(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Fail...!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}