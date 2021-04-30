package com.example.asus.myproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myproject.Model.ExamResultModel;
import com.example.asus.myproject.Utils.PreferenceUtils;
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

public class CandMarkActivity extends AppCompatActivity {
    JsonPlaceHolderApi jsonPlaceHolderApi;
//    List<ExamResultModel> examResultModelList;
//    BarChart mBarchart;
//    ArrayList<BarEntry> entries;
    TextView quiz_result,rank_result;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cand_mark);


        quiz_result = findViewById(R.id.quiz_result);
        rank_result = findViewById(R.id.rank_result);
        button = findViewById(R.id.fetch_btn);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.92:8000/view-set/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

//        createData();
//

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<JsonArray> call = jsonPlaceHolderApi.getCanExamResult("jay");
                call.enqueue(new Callback<JsonArray>() {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                Toast.makeText(GraphActivity.this,
//                        "running response"
//                        , Toast.LENGTH_LONG).show();
                        if (!response.isSuccessful()) {
                            Toast.makeText(CandMarkActivity.this,
                                    "Code: " + response.code()
                                    , Toast.LENGTH_LONG).show();
                            Toast.makeText(CandMarkActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(CandMarkActivity.this,
                                "Successful!!!"
                                , Toast.LENGTH_LONG).show();

                        JsonArray jsonArray = response.body();
//                ArrayList<String> labels = new ArrayList<>();
//                int i=0;
//                for (JsonElement jsonElement:jsonArray){

                        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                        int id = jsonObject.get("id").getAsInt();
                        String candidate = jsonObject.get("candidate").getAsString();
                        int marks = jsonObject.get("marks").getAsInt();

                        quiz_result.setText(marks);

//                    ExamResultModel examResultModel = new ExamResultModel(id,candidate,marks);
//                    examResultModelList.add(examResultModel);
//                    entries.add(new BarEntry(marks,i));
//                    labels.add(candidate);
//                    i++;

//                }


//                BarDataSet barDataSet = new BarDataSet(entries,"Result");
//                BarData barData = new BarData(labels,barDataSet);
//                mBarchart.setData(barData);
//                mBarchart.setDescription("Student's Test Result");
//                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//                mBarchart.animateY(5000);

                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t) {
                        Toast.makeText(CandMarkActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
    private void createData() {
//        Call<JsonArray> call = jsonPlaceHolderApi.getCanExamResult("jay");
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
////                Toast.makeText(GraphActivity.this,
////                        "running response"
////                        , Toast.LENGTH_LONG).show();
//                if (!response.isSuccessful()) {
//                    Toast.makeText(CandMarkActivity.this,
//                            "Code: " + response.code()
//                            , Toast.LENGTH_LONG).show();
//                    Toast.makeText(CandMarkActivity.this, "Server error", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Toast.makeText(CandMarkActivity.this,
//                        "Successful!!!"
//                        , Toast.LENGTH_LONG).show();
//
//                JsonArray jsonArray = response.body();
////                ArrayList<String> labels = new ArrayList<>();
////                int i=0;
////                for (JsonElement jsonElement:jsonArray){
//
//                    JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
//
//                    int id = jsonObject.get("id").getAsInt();
//                    String candidate = jsonObject.get("candidate").getAsString();
//                    int marks = jsonObject.get("marks").getAsInt();
//
//                    quiz_result.setText(marks);
//
////                    ExamResultModel examResultModel = new ExamResultModel(id,candidate,marks);
////                    examResultModelList.add(examResultModel);
////                    entries.add(new BarEntry(marks,i));
////                    labels.add(candidate);
////                    i++;
//
////                }
//
//
////                BarDataSet barDataSet = new BarDataSet(entries,"Result");
////                BarData barData = new BarData(labels,barDataSet);
////                mBarchart.setData(barData);
////                mBarchart.setDescription("Student's Test Result");
////                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
////                mBarchart.animateY(5000);
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonArray> call, Throwable t) {
//                Toast.makeText(CandMarkActivity.this, "Error", Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }
}
