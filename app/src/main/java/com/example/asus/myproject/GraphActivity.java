package com.example.asus.myproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.asus.myproject.Model.ExamResultModel;
import com.example.asus.myproject.Model.VacancyCardData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
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

public class GraphActivity extends AppCompatActivity {
    JsonPlaceHolderApi jsonPlaceHolderApi;
    List<ExamResultModel> examResultModelList;
    BarChart mBarchart;
    ArrayList<BarEntry> entries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        mBarchart = (BarChart) findViewById(R.id.barChart);

        entries = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.92:8000/view-set/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        createGraph();



//
//        entries.add(new BarEntry(10,0));
//        entries.add(new BarEntry(5,1));
//        entries.add(new BarEntry(25,2));
//        entries.add(new BarEntry(17,3));
//        entries.add(new BarEntry(20,4));
//        entries.add(new BarEntry(8,5));
//
//
//
//
//        labels.add("Aashay");
//        labels.add("Vyom");
//        labels.add("Abs");
//        labels.add("PRUTHVI");
//        labels.add("Raj");
//        labels.add("Aakash");


//        BarData barData = new BarData(labels,barDataSet);
//        mBarchart.setData(barData);
//        mBarchart.setDescription("Student's Test Result");
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        mBarchart.animateY(5000);


    }

    private void createGraph() {
        Call<JsonArray> call = jsonPlaceHolderApi.getExamResult();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                Toast.makeText(GraphActivity.this,
//                        "running response"
//                        , Toast.LENGTH_LONG).show();
                if (!response.isSuccessful()) {
                    Toast.makeText(GraphActivity.this,
                            "Code: " + response.code()
                            , Toast.LENGTH_LONG).show();
                    Toast.makeText(GraphActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(GraphActivity.this,
                        "Successful!!!"
                        , Toast.LENGTH_LONG).show();

                JsonArray jsonArray = response.body();
                ArrayList<String> labels = new ArrayList<>();
                int i=0;
                for (JsonElement jsonElement:jsonArray){
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    int id = jsonObject.get("id").getAsInt();
                    String candidate = jsonObject.get("candidate").getAsString();
                    int marks = jsonObject.get("marks").getAsInt();

//                    ExamResultModel examResultModel = new ExamResultModel(id,candidate,marks);
//                    examResultModelList.add(examResultModel);
                    entries.add(new BarEntry(marks,i));
                    labels.add(candidate);
                    i++;

                }


                BarDataSet barDataSet = new BarDataSet(entries,"Result");
                BarData barData = new BarData(labels,barDataSet);
                mBarchart.setData(barData);
                mBarchart.setDescription("Student's Test Result");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                mBarchart.animateY(5000);

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast.makeText(GraphActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
