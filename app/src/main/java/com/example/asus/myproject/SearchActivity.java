package com.example.asus.myproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asus.myproject.Adapter.VacancyAdapter;
import com.example.asus.myproject.Model.VacancyCardData;
import com.example.asus.myproject.Model.VacancyData;
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


public class SearchActivity extends AppCompatActivity {
    List<VacancyCardData> vacancyCardDataList= new ArrayList<>();;
    RecyclerView recyclerView;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    VacancyCardAdapter adapter;
//    private List<VacancyCardData> vacancyDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        setTitle("Search");







        recyclerView=(RecyclerView)findViewById(R.id.recycler_vac_card);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        vacancyCardDataList.add(new VacancyCardData(0,"abc","abad","ce","cd","2200-22-22","omg","djdj",123,20,3));
        SearchActivity.this.VacancyDataPrepare();





    }

    private void VacancyDataPrepare() {
        final ProgressDialog progressDialog=new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading Data......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://myprojectapphingu.herokuapp.com/view-set/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<JsonArray> call = jsonPlaceHolderApi.setCardVacancy();

        call.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                progressDialog.dismiss();
//                Toast.makeText(SearchActivity.this,
//                        "running response"
//                        , Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    Toast.makeText(SearchActivity.this,
                            "Code: " + response.code()
                            , Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                    return;
                }

//                Toast.makeText(SearchActivity.this,
//                        "Successful!!!"
//                        , Toast.LENGTH_LONG).show();

                JsonArray jsonArray = response.body();

                for (JsonElement jsonElement:jsonArray){
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

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

//                    VacancyData vacancyData = new VacancyData(id,compName,vacancy);

                    VacancyCardData vacancyCardData = new VacancyCardData(id,position,location,skills_req,job_category,l_d_a,title,compName,salary,vacancy,experience_req);

                    vacancyCardDataList.add(vacancyCardData);
                }



                adapter = new VacancyCardAdapter(SearchActivity.this, vacancyCardDataList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
