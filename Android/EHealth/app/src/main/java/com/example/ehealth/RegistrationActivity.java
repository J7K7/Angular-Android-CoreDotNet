package com.example.ehealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ehealth.entity.Users;
import com.example.ehealth.retrofit.EndPoint;
import com.example.ehealth.retrofit.RetrofitEndPoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText etFirstName = findViewById(R.id.etRegistrationActivityFirstName);
        EditText etLastName = findViewById(R.id.etRegistrationActivityLastName);
        EditText etEmail = findViewById(R.id.etRegistrationActivityEmail);
        EditText etMobile = findViewById(R.id.etRegistrationActivityMobile);
        EditText etUsername = findViewById(R.id.etRegistrationActivityUsername);
        EditText etPassword = findViewById(R.id.etRegistrationActivityPassword);

        RadioGroup rgGender = findViewById(R.id.rgRegistrationActivityGender);

        findViewById(R.id.btnRegistrationActivitySignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnRegistrationActivitySignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etFirstName.getText().toString().isEmpty() && !etLastName.getText().toString().isEmpty() &&
                        !etEmail.getText().toString().isEmpty() && !etMobile.getText().toString().isEmpty() &&
                        !etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {

                    Users users = new Users();

                    users.setFirstName(etFirstName.getText().toString());
                    users.setLastName(etLastName.getText().toString());
                    users.setEmail(etEmail.getText().toString());
                    users.setMobileNo(etMobile.getText().toString());
                    users.setUserName(etUsername.getText().toString());
                    users.setPassword(etPassword.getText().toString());

                    switch (rgGender.getCheckedRadioButtonId()) {
                        case R.id.rbRegistrationActivityMale:
                            users.setGender(Byte.parseByte("1"));
                            break;

                        case R.id.rbRegistrationActivityFemale:
                            users.setGender(Byte.parseByte("2"));
                            break;

                        case R.id.rbRegistrationActivityOther:
                            users.setGender(Byte.parseByte("3"));
                            break;
                    }

                    EndPoint endPoint = RetrofitEndPoint.RetrofitEndPoint(getString(R.string.api_url));

                    Call<String> registerPatient = endPoint.RegisterPatient(users);

                    registerPatient.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful() && response.code() == 200) {
                                if (response.body().equals("Register Successfully...!!!")) {
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(RegistrationActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(RegistrationActivity.this, "Please try again later...!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}