package com.example.learning.Student.Quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Adapters.AdapterAllRecycle;
import com.example.learning.Constant;
import com.example.learning.Moduls.CreateCourse;
import com.example.learning.R;
import com.example.learning.databinding.ActivityAllQuizesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecycleAllQuizes extends AppCompatActivity {
ActivityAllQuizesBinding binding;
AdapterAllRecycle adapterAllRecycle=new AdapterAllRecycle();
ArrayList<String>list=new ArrayList<>();
FirebaseAuth auth=FirebaseAuth.getInstance();
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityAllQuizesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(RecycleAllQuizes.this, R.color.purple_500));
        getSupportActionBar().hide();
        CreateCourse ObjCourse = (CreateCourse) getIntent().getSerializableExtra("ObjectCourse");
        String type=getIntent().getStringExtra("type");

if(type.equals("0")){
    binding.animat.setVisibility(View.GONE);
    ref.child(Constant.QUIZ).child(ObjCourse.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
           if(snapshot.hasChildren()){
               for(DataSnapshot snapshot1:snapshot.getChildren()){
                   list.add(snapshot1.getKey());
               }
               adapterAllRecycle.setType(1);
               adapterAllRecycle.setList(list);
               binding.Rec.setAdapter(adapterAllRecycle);
           }else{
               binding.animat.setVisibility(View.VISIBLE);
               binding.animat.animate();
           }



        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}else if(type.equals("1")){
    binding.animat.setVisibility(View.GONE);
    ref.child(Constant.QUIZ_ANSWER).child(ObjCourse.getCourseId()).addListenerForSingleValueEvent(new ValueEventListener() {
       boolean flage=false;
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
           binding.animat.setVisibility(View.GONE);
            for(DataSnapshot snapshot1:snapshot.getChildren()){
                 if(snapshot1.hasChild(auth.getUid())){
                     list.add(snapshot1.getKey());
                     flage=true;
                 }

            }
            if(!flage){
                binding.animat.setVisibility(View.VISIBLE);
                binding.animat.animate();
                Toast.makeText(RecycleAllQuizes.this, "solve the quiz first", Toast.LENGTH_SHORT).show();
            }
            adapterAllRecycle.setType(1);
            adapterAllRecycle.setList(list);
            binding.Rec.setAdapter(adapterAllRecycle);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
}


        adapterAllRecycle.setOnclickItem(new AdapterAllRecycle.OnclickItem() {
            @Override
            public void Onclick(String s) {
                if(type.equals("0")){
                    ref.child(Constant.QUIZ_ANSWER).child(ObjCourse.getCourseId()).child(s).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.hasChild(auth.getUid())){
                                goToQuiz(ObjCourse,s,type);
                            }else{
                                Toast.makeText(RecycleAllQuizes.this, "you solved the "+s, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(type.equals("1")){
                    goToQuiz(ObjCourse,s,type);
                }
            }
        });



    }

private void goToQuiz(CreateCourse ObjCourse, String s, String type){
  if(type.equals("0")){
      Intent intent=new Intent(RecycleAllQuizes.this, SplashGotoQuiz.class);
      intent.putExtra("ObjectCourse",ObjCourse);
      intent.putExtra("position",s);
      intent.putExtra("type",type);
      startActivity(intent);
      finish();
  }else{
      Intent intent=new Intent(RecycleAllQuizes.this, Quiz.class);
      intent.putExtra("ObjectCourse",ObjCourse);
      intent.putExtra("position",s);
      intent.putExtra("type",type);
      startActivity(intent);
      finish();
  }

}
}