package com.example.asus.myproject;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.myproject.Model.VacancyCardData;

import java.util.List;
import java.util.Locale;

public class VacancyCardAdapter extends RecyclerView.Adapter<VacancyCardAdapter.VacancyCardViewHolder> {

    private Context mCtx;
    private List<VacancyCardData> vacancyCardDataList;

    public VacancyCardAdapter(Context mCtx, List<VacancyCardData> vacancyCardDataList) {
        this.mCtx = mCtx;
        this.vacancyCardDataList = vacancyCardDataList;
    }

    @Override
    public VacancyCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        LayoutInflater inflater = LayoutInflater.from(mCtx);
//        View view = inflater.inflate(R.layout.vacancy_item,viewGroup,false);
//        VacancyCardViewHolder holder = new VacancyCardViewHolder(view);

        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vacancy_item,viewGroup,false);

        return new VacancyCardViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VacancyCardViewHolder vacancyCardViewHolder, int i) {
        final VacancyCardData vacancyCardData = vacancyCardDataList.get(i);
        vacancyCardViewHolder.idit.setText(Integer.toString(vacancyCardData.getId()));
        vacancyCardViewHolder.tit.setText(vacancyCardData.getStr_title());
        vacancyCardViewHolder.comp_n.setText(vacancyCardData.getStr_comp_name());
        vacancyCardViewHolder.exp.setText(Integer.toString(vacancyCardData.getInt_exp()));
        vacancyCardViewHolder.vac.setText(Integer.toString(vacancyCardData.getInt_vaca()));
        vacancyCardViewHolder.sal.setText(Integer.toString(vacancyCardData.getInt_salary()));
        vacancyCardViewHolder.cat.setText(vacancyCardData.getStr_cata());
        vacancyCardViewHolder.loc.setText(vacancyCardData.getStr_location());
        vacancyCardViewHolder.sk.setText(vacancyCardData.getStr_skills());
        vacancyCardViewHolder.pos.setText(vacancyCardData.getStr_position());
        vacancyCardViewHolder.ld.setText(vacancyCardData.getStr_lda());

        vacancyCardViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCtx.startActivity(new Intent(mCtx,QuizActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return vacancyCardDataList.size();
    }



    public class VacancyCardViewHolder extends RecyclerView.ViewHolder{

        TextView pos,loc,sk,cat,sal,ld,vac,exp,tit,comp_n,idit;
        Button button;

        public VacancyCardViewHolder(@NonNull View itemView) {
            super(itemView);
            idit = itemView.findViewById(R.id.id);
            tit = itemView.findViewById(R.id.title);
            comp_n = itemView.findViewById(R.id.name);
            pos = itemView.findViewById(R.id.position);
            loc = itemView.findViewById(R.id.location);
            sk = itemView.findViewById(R.id.skills);
            cat= itemView.findViewById(R.id.jobcategory);
            sal = itemView.findViewById(R.id.salary);
            ld = itemView.findViewById(R.id.lastdate);
            vac = itemView.findViewById(R.id.vacancies);
            exp = itemView.findViewById(R.id.experience);
            button = itemView.findViewById(R.id.apply_btn);

        }
    }
}
