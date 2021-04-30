package com.example.asus.myproject;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.myproject.Model.Result;
import com.example.asus.myproject.Utils.PreferenceUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizActivity extends AppCompatActivity {
    List list = new ArrayList();
    List listcorrect = new ArrayList();
    List shuffel = new ArrayList();
    static int j = 0, i = 0;
    static int resultcount = 0;

    int counter = 30,valid_selection=0;
    private TextView textViewResult, timer, question_num;
//    private TextView marksview;
    private Button buttonnext,buttona,buttonb,buttonc,buttond;
//    private CardView card_option;
    private Vibrator vibe;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    MediaPlayer bell_sound,knock_sound,result_sound,clock_tic_sound;

    int k = -1;
    ProgressBar mProgressBar, mProgressBar1;

    private TextView textViewShowTime;
    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;


    @Override
    protected void onStart() {
        super.onStart();

        textViewResult = findViewById(R.id.text_view_result);
        buttonnext = findViewById(R.id.buttonnext);
        buttona=findViewById(R.id.option_a);
        buttonb=findViewById(R.id.option_b);
        buttonc=findViewById(R.id.option_c);
        buttond=findViewById(R.id.option_d);
        timer = findViewById(R.id.time);
        question_num = findViewById(R.id.question_number);

        textViewShowTime = (TextView)findViewById(R.id.textView_timerview_time);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_timerview);
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressbar1_timerview);


        textViewResult.setText("Welcome\n Are you ready for Fun!!!!\n");
        buttona.setVisibility(View.GONE);
        buttonb.setVisibility(View.GONE);
        buttonc.setVisibility(View.GONE);
        buttond.setVisibility(View.GONE);
        question_num.setVisibility(View.GONE);

        textViewShowTime.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
//        mProgressBar1.setVisibility(View.GONE);



        buttonnext.setText("Start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bell_sound = MediaPlayer.create(this,R.raw.bell);
        knock_sound = MediaPlayer.create(this,R.raw.knock_knock);
        result_sound= MediaPlayer.create(this,R.raw.jingle_win);
        clock_tic_sound= MediaPlayer.create(this,R.raw.clock_ticking);
        clock_tic_sound.setLooping(true);




//        card_option = findViewById(R.id.card_view_option);

//        marksview = findViewById(R.id.marks);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        vibe = (Vibrator) getSystemService(QuizActivity.VIBRATOR_SERVICE);

//        jsonPlaceHolderApi2 = retrofit2.create(JsonPlaceHolderApi.class);

        QuizActivity.this.getQuizData();

//        Toast.makeText(this, Integer.toString(resultcount) , Toast.LENGTH_SHORT).show();




    }

    private void setTimer(){
        int time = 30;
        totalTimeCountInMilliseconds =  time * 1000;
        mProgressBar1.setMax( time * 1000);
    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 1) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                mProgressBar1.setProgress((int) (leftTimeInMilliseconds));

                textViewShowTime.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));
            }

            @Override
            public void onFinish() {
                textViewShowTime.setText("00:00");
                textViewShowTime.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar1.setVisibility(View.GONE);


                clock_tic_sound.stop();
                buttonnext.setText("Submit");
                j=50;

            }
        }.start();
    }



    private void getQuizData() {
        Call<JsonObject> call = jsonPlaceHolderApi.getQuiz(10,19,"medium","multiple");
        call.enqueue(new Callback<JsonObject>() {

            String content="";

            int rc;
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    Toast.makeText(QuizActivity.this,
                            "But gives error!!!"
                            , Toast.LENGTH_LONG).show();
                    return;
                }

                JsonObject jsonObject = response.body();
                JsonArray jsonArray = jsonObject.get("results").getAsJsonArray();
                String content = "";
                final List list =new ArrayList();
                for(int i=0;i<10;i++) {
                    JsonObject jsonObject1 = jsonArray.get(i).getAsJsonObject();

                    list.add(jsonObject1.get("question").getAsString());
                    list.add(jsonObject1.get("correct_answer").getAsString());
                    listcorrect.add(jsonObject1.get("correct_answer").getAsString());
                    JsonArray jsonArray1 = jsonObject1.get("incorrect_answers").getAsJsonArray();
                    list.add(jsonArray1.get(0).getAsString());
                    list.add(jsonArray1.get(1).getAsString());
                    list.add(jsonArray1.get(2).getAsString());
                }

//                textViewResult.setText("Welcome\n Are you ready for Fun!!!!\n");
//
//                buttona.setVisibility(View.GONE);
//                buttonb.setVisibility(View.GONE);
//                buttonc.setVisibility(View.GONE);
//                buttond.setVisibility(View.GONE);
//                card_option.setVisibility(View.GONE);
//                buttonnext.setText("Start");

                final int total_question = jsonArray.size();

                i=-1;


                buttonnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        valid_selection=0;
//                        buttona.setBackgroundColor(-16776961);
//                        buttonb.setBackgroundColor(-16776961);
//                        buttonc.setBackgroundColor(-16776961);
//                        buttond.setBackgroundColor(-16776961);
                        buttona.setBackground(getResources().getDrawable(R.drawable.btn_circular_bg));
                        buttonb.setBackground(getResources().getDrawable(R.drawable.btn_circular_bg));
                        buttonc.setBackground(getResources().getDrawable(R.drawable.btn_circular_bg));
                        buttond.setBackground(getResources().getDrawable(R.drawable.btn_circular_bg));


                        if(j==0)
                        {
//                            new CountDownTimer(30000,1000){
//
//                                public void onTick(long millisUntilFinished){
//                                    timer.setText("00:"+String.valueOf(counter));
//                                    counter--;
//                                }
//
//                                public void onFinish()
//                                {
//
//                                    timer.setText("Time Expired!!!");
//                                    clock_tic_sound.stop();
//                                    buttonnext.setText("Submit");
//                                    j=50;
//                                }
//                            }.start();

                            //



                            setTimer();
                            mProgressBar.setVisibility(View.INVISIBLE);

                            startTimer();
                            mProgressBar1.setVisibility(View.VISIBLE);

                            //
                        }
                        if (j<50) {

                            clock_tic_sound.start();

                            int q_num = j/5  + 1;
                            String que = Integer.toString(q_num) + " / " + Integer.toString(total_question);
                            question_num.setText(que);

                            if(j==45)
                            {
                                buttonnext.setText("Submit");
                            }
                            else
                            {
                                buttonnext.setText("Next");
                            }

//                            card_option.setVisibility(View.VISIBLE);
                            buttona.setVisibility(View.VISIBLE);
                            buttonb.setVisibility(View.VISIBLE);
                            buttonc.setVisibility(View.VISIBLE);
                            buttond.setVisibility(View.VISIBLE);
                            question_num.setVisibility(View.VISIBLE);

                            textViewShowTime.setVisibility(View.VISIBLE);
//                            mProgressBar.setVisibility(View.VISIBLE);
//                            mProgressBar1.setVisibility(View.VISIBLE);



                            textViewResult.setText(list.get(j).toString());

                            String a = list.get(++j).toString();
                            String b = list.get(++j).toString();
                            String c = list.get(++j).toString();
                            String d = list.get(++j).toString();

                            shuffel.add(a);
                            shuffel.add(b);
                            shuffel.add(c);
                            shuffel.add(d);


                            Collections.shuffle(shuffel);

                            buttona.setText(shuffel.get(0).toString());
                            buttonb.setText(shuffel.get(1).toString());
                            buttonc.setText(shuffel.get(2).toString());
                            buttond.setText(shuffel.get(3).toString());

                            shuffel.clear();

                            buttona.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(valid_selection==0) {
                                        if (listcorrect.get(i).equals(buttona.getText().toString())) {
//                                            buttona.setBackgroundColor(-16711936);
                                            buttona.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                            bell_sound.start();
                                            resultcount++;
                                        }
                                        else
                                        {
//                                            buttona.setBackgroundColor(-65536);
                                            buttona.setBackground(getResources().getDrawable(R.drawable.btn_wrong));
                                            knock_sound.start();
                                            vibe.vibrate(100);

                                            //Display right answer
                                            for (int k=0;k<=3;k++){
                                                if (listcorrect.get(i).equals(buttonb.getText().toString())){
                                                    buttonb.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                                else if (listcorrect.get(i).equals(buttonc.getText().toString())){
                                                    buttonc.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                                else{
                                                    buttond.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                            }

                                        }
                                        valid_selection=1;
                                    }

                                }
                            });

                            buttonb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(valid_selection==0) {
                                        if (listcorrect.get(i).equals(buttonb.getText().toString())) {
//                                            buttonb.setBackgroundColor(-16711936);
                                            buttonb.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                            bell_sound.start();
                                            resultcount++;
                                        }
                                        else
                                        {
//                                            buttonb.setBackgroundColor(-65536);
                                            buttonb.setBackground(getResources().getDrawable(R.drawable.btn_wrong));
                                            knock_sound.start();
                                            vibe.vibrate(100);
                                            //Display right answer
                                            for (int k=0;k<=3;k++){
                                                if (listcorrect.get(i).equals(buttona.getText().toString())){
                                                    buttona.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                                else if (listcorrect.get(i).equals(buttonc.getText().toString())){
                                                    buttonc.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                                else{
                                                    buttond.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                            }
                                        }
                                        valid_selection=1;
                                    }

                                }

                            });
                            buttonc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(valid_selection==0) {
                                        if (listcorrect.get(i).equals(buttonc.getText().toString())) {
//                                            buttonc.setBackgroundColor(-16711936);
                                            buttonc.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                            bell_sound.start();
                                            resultcount++;
                                        }
                                        else
                                        {
//                                            buttonc.setBackgroundColor(-65536);
                                            buttonc.setBackground(getResources().getDrawable(R.drawable.btn_wrong));
                                            knock_sound.start();
                                            vibe.vibrate(100);
                                            //Display right answer
                                            for (int k=0;k<=3;k++){
                                                if (listcorrect.get(i).equals(buttona.getText().toString())){
                                                    buttona.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                                else if (listcorrect.get(i).equals(buttonb.getText().toString())){
                                                    buttonb.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                                else{
                                                    buttond.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                            }
                                        }
                                        valid_selection=1;
                                    }

                                }
                            });
                            buttond.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(valid_selection==0) {
                                        if (listcorrect.get(i).equals(buttond.getText().toString())) {
//                                            buttond.setBackgroundColor(-16711936);
                                            buttond.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                            bell_sound.start();
                                            resultcount++;
                                        }
                                        else
                                        {
//                                            buttond.setBackgroundColor(-65536);
                                            buttond.setBackground(getResources().getDrawable(R.drawable.btn_wrong));
                                            knock_sound.start();
                                            vibe.vibrate(100);
                                            //Display right answer
                                            for (int k=0;k<=3;k++){
                                                if (listcorrect.get(i).equals(buttonb.getText().toString())){
                                                    buttonb.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                                else if (listcorrect.get(i).equals(buttonc.getText().toString())){
                                                    buttonc.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                                else{
                                                    buttona.setBackground(getResources().getDrawable(R.drawable.btn_right));
                                                }
                                            }
                                        }
                                        valid_selection=1;
                                    }

                                }
                            });


                            j++;
                            i++;



                        }

                        else if(j==50)
                        {
                            clock_tic_sound.stop();
                            result_sound.start();
                            textViewResult.setText("Finished\n"+"Your Score:"+resultcount);
                            rc = resultcount;

                            buttona.setVisibility(View.GONE);
                            buttonb.setVisibility(View.GONE);
                            buttonc.setVisibility(View.GONE);
                            buttond.setVisibility(View.GONE);
                            buttonnext.setVisibility(View.GONE);
                            timer.setVisibility(View.GONE);
                            question_num.setVisibility(View.GONE);


                            //

                            countDownTimer.cancel();
                            countDownTimer.onFinish();
//                            mProgressBar1.setVisibility(View.GONE);
//                            mProgressBar.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                            textViewShowTime.setVisibility(View.GONE);
                            //



                            //

                            JsonPlaceHolderApi jsonPlaceHolderApi2;
                            Retrofit retrofit2 = new Retrofit.Builder()
                                    .baseUrl("http://192.168.1.92:8000/view-set/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            jsonPlaceHolderApi2 = retrofit2.create(JsonPlaceHolderApi.class);

                            Result result = new Result(PreferenceUtils.getUserName(QuizActivity.this),resultcount);
                            Call<JsonObject> call2 = jsonPlaceHolderApi2.setResultScore(result);
                            call2.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if (!response.isSuccessful()){
//                    textViewResult.setText("Code: "+response.code());
                                        Toast.makeText(QuizActivity.this,
                                                "But gives error!!!"
                                                , Toast.LENGTH_LONG).show();
                                        return;
                                    }

//                                    Toast.makeText(QuizActivity.this, "successful", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {

                                }
                            });


                            //
                        }



                    }
                });


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(QuizActivity.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }


        });




    } //end of get quiz data




}
