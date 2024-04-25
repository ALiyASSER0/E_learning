package com.example.learning.Student.Manage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.learning.Adapters.CreateCourseAdapter;
import com.example.learning.Constant;
import com.example.learning.Moduls.CreateCourse;
import com.example.learning.R;
import com.example.learning.Splash.WelcomeScreen;
import com.example.learning.databinding.ActivityHomeStudentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HomeStudent extends AppCompatActivity implements View.OnClickListener {
ActivityHomeStudentBinding binding;
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
FirebaseAuth auth=FirebaseAuth.getInstance();
CreateCourseAdapter adapter=new CreateCourseAdapter();
List<CreateCourse> list=new ArrayList<>();
       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeStudentBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(HomeStudent.this, R.color.purple_500));
        getSupportActionBar().hide();
        setContentView(binding.getRoot());
binding.btnAddMember.setOnClickListener(this::onClick);

ref.child(Constant.COURSES_OF_STUDENT).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        list.clear();
        for(DataSnapshot snapshot1:snapshot.getChildren()){
             for(DataSnapshot snapshot2:snapshot1.getChildren()){
                 if(snapshot2.getKey().equals(auth.getUid())){
                     list.add(snapshot2.getValue(CreateCourse.class));
                 }
             }

            }
adapter.setList(list);
binding.recyclerViewHomeStudent.setAdapter(adapter);


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});



binding.btnLogout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(HomeStudent.this, WelcomeScreen.class));
        finish();
    }
});

    adapter.setOnClickItem(new CreateCourseAdapter.OnClickItem() {
       @Override
        public void Onclick(CreateCourse createCourse) {
            Intent intent=new Intent(HomeStudent.this,PageStudent.class);
            intent.putExtra("ObjectCourse",createCourse);
            startActivity(intent);
        }
    });



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==binding.btnAddMember.getId()){
             String courseId=binding.editEmail.getText().toString().trim();
             validation(courseId);
        }
    }

    private void validation(String courseId) {
      if(courseId.isEmpty()){
          binding.editEmail.setError(getString(R.string.req));
      }else{
          getData(courseId);
      }

    }

    private void getData(String courseId) {
        ref.child(Constant.COURSE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              list.clear();
                if(snapshot.hasChild(courseId)){
                    CreateCourse m=snapshot.child(courseId).getValue(CreateCourse.class);
                    sentDataToRealTime(m);
                    list.add(m);
                }else{
                    Toast.makeText(HomeStudent.this, "Course not found", Toast.LENGTH_SHORT).show();
                }

                adapter.setList(list);
            binding.recyclerViewHomeStudent.setAdapter(adapter);
            binding.editEmail.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

public void sentDataToRealTime(CreateCourse createCourse){

               ref.child(Constant.COURSES_OF_STUDENT).child(createCourse.getCourseId()).child(auth.getCurrentUser().getUid())
                       .setValue(new CreateCourse(createCourse.getCourseName(),"0","0","0",createCourse.getCourseId(),createCourse.getDoctorId(),null));

}



}