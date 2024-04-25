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

public class AdapterQuizOnly extends RecyclerView.Adapter<AdapterQuizOnly.Holder> {
private ArrayList<ModelAnswerQuiz>list;
private ArrayList<SignOut>list2;
private ArrayList<ShowAllGrades>list3;
    public void setLists(ArrayList<SignOut> list2,ArrayList<ModelAnswerQuiz> list) {
        Log.e("here","Here3");
        this.list2 = list2;
        this.list=list;
    }

    public void setList3(ArrayList<ShowAllGrades> list3, ArrayList<SignOut> list2) {
        Log.e("here","Here2");
        this.list3 = list3;
        this.list2 = list2;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("here","Here4");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_quiz_only,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.e("here","Here5");
        if(list3==null){
         holder.bind(list.get(position),list2.get(position));
     }else{
         holder.bind2(list3.get(position),list2.get(position));
     }



    }

    @Override
    public int getItemCount() {
      return list2==null?0:list2.size();



    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textEmail,textName,textGrade1,textGrade2;
        com.airbnb.lottie.LottieAnimationView an;
    public Holder(@NonNull View itemView) {
        super(itemView);
        textEmail=itemView.findViewById(R.id.text_email);
        textName=itemView.findViewById(R.id.text_namee);
        textGrade1=itemView.findViewById(R.id.text_quiz);
        textGrade2=itemView.findViewById(R.id.text_quiz3);
        an=itemView.findViewById(R.id.amemations);
    }
   public void bind(ModelAnswerQuiz modelAnswerQuiz,SignOut signOut){
    an.animate();
        textGrade1.setText(modelAnswerQuiz.getQuizGrideCorrect());
    textGrade2.setText(modelAnswerQuiz.getTotalGrades());
    textEmail.setText(signOut.getEmail());
    textName.setText(signOut.getName());
    }
  public void bind2(ShowAllGrades grade, SignOut signOut){
            textGrade1.setText(grade.getMyGrade());
            textGrade2.setText(grade.getTotalGrade());
            textEmail.setText(signOut.getEmail());
            textName.setText(signOut.getName());
        }

    }




}
