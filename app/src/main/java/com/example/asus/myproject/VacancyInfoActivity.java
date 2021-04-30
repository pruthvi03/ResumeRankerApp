package com.example.asus.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myproject.Model.VacancyCardData;
import com.example.asus.myproject.Utils.PreferenceUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VacancyInfoActivity extends AppCompatActivity {
    JsonPlaceHolderApi jsonPlaceHolderApi;

    TextView pos,loc,sk,cat,sal,ld,vac,exp,tit,comp_n,idit;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy_info);

        idit = findViewById(R.id.id);
        pos = findViewById(R.id.position);
        loc = findViewById(R.id.location);
        sk = findViewById(R.id.skills);
        cat= findViewById(R.id.jobcategory);
        sal = findViewById(R.id.salary);
        ld = findViewById(R.id.lastdate);
        vac = findViewById(R.id.vacancies);
        exp = findViewById(R.id.experience);
        tit = findViewById(R.id.title);
        comp_n = findViewById(R.id.name);

        button = findViewById(R.id.apply_btn);

        Intent intent = getIntent();
        int idv = intent.getIntExtra("id",0);
        final String comp_name = intent.getStringExtra("company");
        final int vaca = intent.getIntExtra("vacancy",0);

        if (PreferenceUtils.getUserType(VacancyInfoActivity.this).equals("false")) {
            button.setVisibility(View.GONE);
        }

        comp_n.setText(comp_name);
        vac.setText(Integer.toString(vaca));



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://myprojectapphingu.herokuapp.com/view-set/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<JsonArray> call = jsonPlaceHolderApi.setcardVacancyDetail(idv);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                Toast.makeText(VacancyInfoActivity.this,
//                        "running response"
//                        , Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    Toast.makeText(VacancyInfoActivity.this,
                            "Code: " + response.code()
                            , Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                    return;
                }

//                Toast.makeText(VacancyInfoActivity.this,
//                        "Successful!!!"
//                        , Toast.LENGTH_LONG).show();

                JsonArray jsonArray = response.body();

//                for (JsonElement jsonElement:jsonArray){

                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                    int id = jsonObject.get("id").getAsInt();
                    String title = jsonObject.get("job_title").getAsString();
                    int salary = jsonObject.get("salary").getAsInt();
                    int experience_req = jsonObject.get("experience_req").getAsInt();
                    String skills_req = jsonObject.get("skills_req").getAsString();
                    int vacancy = jsonObject.get("vacancy_count").getAsInt();
                    String l_d_a = jsonObject.get("l_d_a").getAsString();
                    String job_category = jsonObject.get("job_category").getAsString();
                    String position = jsonObject.get("position").getAsString();

                    JsonObject jsonObject1 = jsonObject.get("company").getAsJsonObject();
                    String compName = jsonObject1.get("company_name").getAsString();
                    String location = jsonObject1.get("address").getAsString();
                    int avail_jobs = jsonObject1.get("jobs").getAsInt();

                    idit.setText(Integer.toString(id));
                    tit.setText(title);
                    sal.setText(Integer.toString(salary));
                    pos.setText(position);
                    sk.setText(skills_req);
                    cat.setText(job_category);
                    ld.setText(l_d_a);
                    vac.setText(Integer.toString(vacancy));
                    exp.setText(Integer.toString(experience_req));
                    tit.setText(title);
                    comp_n.setText(compName);
                    loc.setText(location);




//                    VacancyData vacancyData = new VacancyData(id,compName,vacancy);

//                    VacancyCardData vacancyCardData = new VacancyCardData(id,position,location,skills_req,job_category,l_d_a,title,compName,salary,vacancy,experience_req);

//                    vacancyCardDataList.add(vacancyCardData);
//                }



//                adapter = new VacancyCardAdapter(SearchActivity.this, vacancyCardDataList);
//                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(VacancyInfoActivity.this,QuizActivity.class));
                button.setVisibility(View.GONE);
            }
        });
    }
}
