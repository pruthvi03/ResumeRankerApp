package com.example.asus.myproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myproject.Adapter.VacancyAdapter;
import com.example.asus.myproject.Model.VacancyData;
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

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;
    private VacancyAdapter vacancyAdapter;

    private List<VacancyData> vacancyDataList = new ArrayList<>();

    private FloatingActionButton myFab;

    private TextView nav_full_name,nav_username;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private int backButtonCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation view
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_id);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        nav_username = (TextView)headerView.findViewById(R.id.nav_user_name);
        nav_username.setText(PreferenceUtils.getUserName(MainActivity.this));




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_camera:
//                        Toast.makeText(MainActivity.this, "my_profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,MyProfileActivity.class));
                        return true;
//
//
                    case R.id.nav_add_info:
//                        Toast.makeText(MainActivity.this, "Add More Info", Toast.LENGTH_SHORT).show();
                        if (PreferenceUtils.getUserType(MainActivity.this).equals("false")) {
                            startActivity(new Intent(MainActivity.this, CompanyMoreInfo.class));
                        }
                        else {
                            startActivity(new Intent(MainActivity.this, CandidateMoreInfo.class));
                        }
                        return true;

//                    case R.id.nav_rec:
////                        Toast.makeText(MainActivity.this, "Rec", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(MainActivity.this,RecActivity.class));
//                        return true;

                    case R.id.nav_log_out:
//                        Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        PreferenceUtils.saveToken(null,navigationView.getContext());
                        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                        return true;

                    case R.id.nav_marks:
//                        if (PreferenceUtils.getUserType(MainActivity.this).equals("false")) {
                            startActivity(new Intent(MainActivity.this, GraphActivity.class));
//                        }
//                        else {
//                            startActivity(new Intent(MainActivity.this, CandMarkActivity.class));
//                        }
                        return true;

                    case R.id.nav_rank_detail:
                        startActivity(new Intent(MainActivity.this,CandMarkActivity.class));
                    default:
                        return true;
                }

            }
        });
        // Navigation view ---->

        // floating but
        myFab = (FloatingActionButton)findViewById(R.id.fab);



        // Recycler view 
        recyclerView = findViewById(R.id.recycler_view1);
        recyclerView1 = findViewById(R.id.recycler_view2);
        recyclerView2 = findViewById(R.id.recycler_view3);

        MainActivity.this.StudentDataPrepare();
//        vacancyAdapter = new VacancyAdapter(vacancyDataList,getApplicationContext());

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, PreferenceUtils.getToken(MainActivity.this), Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "Going to search activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);

            }
        });


    } //End of On create method



    // Recycler view
    public void StudentDataPrepare() {
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
//                Toast.makeText(MainActivity.this,
//                        "running response"
//                        , Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this,
                            "Code: " + response.code()
                            , Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                    return;
                }

//                Toast.makeText(MainActivity.this,
//                        "Successful!!!"
//                        , Toast.LENGTH_LONG).show();

                JsonArray jsonArray = response.body();

                for (JsonElement jsonElement:jsonArray){
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    int id = jsonObject.get("id").getAsInt();
                    int vacancy = jsonObject.get("vacancy_count").getAsInt();
                    JsonObject jsonObject1 = jsonObject.get("company").getAsJsonObject();
                    String compName = jsonObject1.get("company_name").getAsString();
                    VacancyData vacancyData = new VacancyData(id,compName,vacancy);
                    vacancyDataList.add(vacancyData);
                }

//                Toast.makeText(MainActivity.this, vacancyDataList.get(0).getCompanyName(), Toast.LENGTH_SHORT).show();

//                vacancyAdapter = new VacancyAdapter(MainActivity.this,vacancyDataList);
                vacancyAdapter = new VacancyAdapter(MainActivity.this,vacancyDataList);

                RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, true);
                recyclerView.setLayoutManager(manager);
                RecyclerView.LayoutManager manager1 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, true);
                recyclerView1.setLayoutManager(manager1);
                RecyclerView.LayoutManager manager2 = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, true);
                recyclerView2.setLayoutManager(manager2);

                recyclerView.setAdapter(vacancyAdapter);
                recyclerView1.setAdapter(vacancyAdapter);
                recyclerView2.setAdapter(vacancyAdapter);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

//        Toast.makeText(this, vacancyDataList.get(0).getCompanyName(), Toast.LENGTH_SHORT).show();
    }
    // Recycler view ------>

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) ||super.onOptionsItemSelected(item);
    }

    // Floating button

    @Override
    public void onBackPressed()
    {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(backButtonCount >= 1)
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
