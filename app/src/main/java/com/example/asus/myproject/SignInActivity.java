package com.example.asus.myproject;

import android.content.Intent;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myproject.Utils.Constants;
import com.example.asus.myproject.Utils.PreferenceUtils;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {
    //    Boolean userType;
    private Boolean userType = Boolean.TRUE;
    private EditText email,mpassword;
    private String str_email,str_password;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private TextView txt_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://myprojectapphingu.herokuapp.com/view-set/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        email = findViewById(R.id.log_in_email_id);
        mpassword= findViewById(R.id.log_in_password);
        Button singin_btn = (Button) findViewById(R.id.btn_signin);
        txt_signup = findViewById(R.id.txt_clk_signup);



        singin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_email = email.getText().toString();
                str_password = mpassword.getText().toString();
//                Toast.makeText(SignInActivity.this, "clicked", Toast.LENGTH_SHORT).show();
//                if (!PreferenceUtils.getToken(SignInActivity.this).isEmpty()) {
//                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
//                }
                SignInActivity.this.signInUser();
            }


        });

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });


    }


    //Sign In

    public void signInUser() {

        Toast.makeText(this, str_email, Toast.LENGTH_SHORT).show();

        Call<JsonObject> call = jsonPlaceHolderApi.signInIt(str_email,str_password);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                Toast.makeText(SignInActivity.this,
//                        "running response"
//                        , Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()){
                    Toast.makeText(SignInActivity.this,
                            response.code()
                            , Toast.LENGTH_LONG).show();
                    return;
                }

//                Toast.makeText(SignInActivity.this,
//                        "Successful!!!"
//                        , Toast.LENGTH_LONG).show();

                JsonObject token = response.body();

                String content = token.get("token").getAsString();

//                Toast.makeText(SignInActivity.this, content, Toast.LENGTH_SHORT).show();

//                saveToken(content);
                PreferenceUtils.saveToken(content,SignInActivity.this);
//                PreferenceUtils.saveToken(email.getText().toString(),SignInActivity.this);
                PreferenceUtils.saveUserType(Boolean.toString(userType),SignInActivity.this);

                startActivity(new Intent(SignInActivity.this,MainActivity.class));

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_comp:
                if (checked)
                    // User is company
                    userType = Boolean.FALSE;
//                Toast.makeText(this, "Company " + userType, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_cand:
                if (checked)
                    // User is candidate
                    userType = Boolean.TRUE;
//                Toast.makeText(this, "Candidate " + userType, Toast.LENGTH_SHORT).show();
                break;
        }
    }

//    private void saveToken(String content) {
//        PreferenceUtils.saveToken(content,this);
//    }

    //




}