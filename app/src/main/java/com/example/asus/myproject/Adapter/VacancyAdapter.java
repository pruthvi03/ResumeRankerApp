package com.example.asus.myproject.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asus.myproject.Model.VacancyData;
import com.example.asus.myproject.R;
import com.example.asus.myproject.Model.StudentData;
import com.example.asus.myproject.VacancyInfoActivity;

import java.util.List;

public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.MyViewHolder> {

    List<VacancyData> vacancyDataList;
    View itemView;
    Context context;
    /*
        public interface OnItemClickListener {
            void onItemClick(ContentItem item);
        }
        */
    public VacancyAdapter(Context context,List<VacancyData> vacancyDatas ) {
        this.vacancyDataList=vacancyDatas;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_list_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final VacancyData data=(VacancyData)vacancyDataList.get(i);


        //Random rnd = new Random();
        int currentColor = Color.argb(255, 255, 255,255);
        myViewHolder.parent.setBackgroundColor(currentColor);
        myViewHolder.name.setText(data.getCompanyName());
        myViewHolder.name.setTextColor(0xFF00B0FF);
        myViewHolder.vacancy.setTextColor(0xFF000000);
        myViewHolder.vacancy.setText(String.valueOf(data.getVacancyCount()));
        myViewHolder.vid.setText(String.valueOf(data.getId()));
        // creating a CardView and assigning a value.

        myViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context, String.valueOf(data.getId()), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,VacancyInfoActivity.class);
                intent.putExtra("id",data.getId());
                intent.putExtra("company",data.getCompanyName());
                intent.putExtra("vacancy",data.getVacancyCount());
                context.startActivity(intent);



            }
            //Toast.makeText(context,"YOu Clicked",Toast.LENGTH_SHORT).show();

        });




    }


    @Override
    public int getItemCount() {
        return vacancyDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,vacancy,vid;
        LinearLayout parent;

        public MyViewHolder(View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.name);
            vacancy = itemView.findViewById(R.id.vacancy_count);
            vid = itemView.findViewById(R.id.v_id);
        }
    }
}

