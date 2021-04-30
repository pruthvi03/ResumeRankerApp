package com.example.asus.myproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.myproject.Model.CompanyInfo;
import com.example.asus.myproject.R;
import com.example.asus.myproject.Utils.PreferenceUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyMoreInfo extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    EditText comp_name,addr,cata,website_link;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_more_info);

        comp_name = findViewById(R.id.company_name);
        addr = findViewById(R.id.address);
        cata = findViewById(R.id.category);
        website_link = findViewById(R.id.link);
        btn_submit = findViewById(R.id.submit_btn);

        Toast.makeText(this, PreferenceUtils.getUserType(CompanyMoreInfo.this), Toast.LENGTH_SHORT).show();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://myprojectapphingu.herokuapp.com/view-set/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                String cN = comp_name.getText().toString();
                String aD = addr.getText().toString();
                String cat = cata.getText().toString();
                String wL = website_link.getText().toString();

                String tok = "Token "+ PreferenceUtils.getToken(CompanyMoreInfo.this);
                Toast.makeText(CompanyMoreInfo.this, tok, Toast.LENGTH_SHORT).show();

//                String tok = PreferenceUtils.getToken(CompanyMoreInfo.this);


                CompanyInfo companyInfo = new CompanyInfo(cN,aD,cat,wL);

                Call<JsonObject> call = jsonPlaceHolderApi.setCompanyInfo(tok,companyInfo);

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Toast.makeText(CompanyMoreInfo.this,
                                "running response"
                                , Toast.LENGTH_LONG).show();
                        if (!response.isSuccessful()) {
                            Toast.makeText(CompanyMoreInfo.this,
                                    "Code: " + response.code()
                                    , Toast.LENGTH_LONG).show();

                            return;
                        }

                        Toast.makeText(CompanyMoreInfo.this,
                                "Successful!!!"
                                , Toast.LENGTH_LONG).show();

                        JsonObject jsonObject = response.body();



                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(CompanyMoreInfo.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
