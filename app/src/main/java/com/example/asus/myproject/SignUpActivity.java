package com.example.asus.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myproject.Model.Profile;
import com.example.asus.myproject.Utils.Constants;
import com.example.asus.myproject.Utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private Boolean userType = Boolean.TRUE;
    private EditText email, username, mpassword;
    private String str_email, str_username, str_password;
    private Button reg_btn;
    private TextView btn;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private int backButtonCount;

    @Override
    protected void onStart() {
        super.onStart();
//        String check ="H";
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
//        String withThis=prefs.getString(Constants.KEY_EMAIL, null);
//        assert check != null;
        String token = PreferenceUtils.getToken(this);
        if (token != null){
//            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, PreferenceUtils.getEmail(this), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
        }
//        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://myprojectapphingu.herokuapp.com/view-set/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        email = findViewById(R.id.emailid);
        username = findViewById(R.id.name);
        mpassword = findViewById(R.id.password);
        reg_btn = findViewById(R.id.btn_register);

        btn = findViewById(R.id.btn_signin);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_email = email.getText().toString();
                str_username = username.getText().toString();
                str_password = mpassword.getText().toString();
//                Toast.makeText(SignUpActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                SignUpActivity.this.signUpUser();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));

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

    // Sign up method
    public void signUpUser() {

        Toast.makeText(this, str_email, Toast.LENGTH_SHORT).show();

        Profile profile = new Profile(0, str_email,
                str_username, userType, str_password);
        Call<Profile> call = jsonPlaceHolderApi.signUpIt(profile);

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
//                Toast.makeText(SignUpActivity.this,
//                        "running response"
//                        , Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this,
                            "Code: " + response.code()
                            , Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                    return;
                }

//                Toast.makeText(SignUpActivity.this,
//                        "Successful!!!"
//                        , Toast.LENGTH_LONG).show();

                Profile userResponse = response.body();
                String content = "";
                content += "Code:" + response.code() + "\n";
                content += "User ID: " + userResponse.getId() + "\n";
                content += "User Type: " + userResponse.getUser_type() + "\n";
                content += "email: " + userResponse.getEmail() + "\n";
                content += "name: " + userResponse.getName() + "\n\n";


//                Toast.makeText(SignUpActivity.this, content, Toast.LENGTH_SHORT).show();

                saveData(userResponse);


                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
//                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });

    }

    public void saveData(Profile profile) {
        PreferenceUtils.saveId(Integer.toString(profile.getId()), this);
        PreferenceUtils.saveEmail(profile.getEmail(), this);
        PreferenceUtils.saveUserType(Boolean.toString(profile.getUser_type()), this);
        PreferenceUtils.saveUserName(profile.getName(), this);
        Constants.CHECK = "1";

    }
    @Override
    public void onBackPressed()
    {
//        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            this.drawerLayout.closeDrawer(GravityCompat.START);
//        }
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}