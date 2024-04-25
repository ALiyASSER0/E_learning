package com.example.learning.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.Moduls.ModelAnswerQuiz;
import com.example.learning.Moduls.ShowAllGrades;
import com.example.learning.Moduls.SignOut;
import com.example.learning.R;

import java.util.ArrayList;

public class AdapterAttendenceOnly extends RecyclerView.Adapter<AdapterAttendenceOnly.Holder> {
private ArrayList<SignOut>list2;
    public void setLists(ArrayList<SignOut> list2) {
        this.list2 = list2;
    }




    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_attendence_only,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

         holder.bind(list2.get(position));




    }

    @Override
    public int getItemCount() {
      return list2==null?0:list2.size();



    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textGrade1,textGrade2;
    public Holder(@NonNull View itemView) {
        super(itemView);
        textGrade1=itemView.findViewById(R.id.text_emaill);
        textGrade2=itemView.findViewById(R.id.text_namees);
    }
   public void bind(SignOut signOut){
        textGrade1.setText(signOut.getEmail());
    textGrade2.setText(signOut.getName());
    }


    }




}
