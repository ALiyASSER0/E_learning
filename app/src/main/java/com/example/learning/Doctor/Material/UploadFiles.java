package com.example.learning.Doctor.Material;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learning.Constant;
import com.example.learning.databinding.ActivityUploadFilesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadFiles extends AppCompatActivity {
ActivityUploadFilesBinding binding;
Uri uri ;
String idCourse;
private StorageReference storage= FirebaseStorage.getInstance().getReference();
DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
static int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityUploadFilesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         idCourse=getIntent().getStringExtra("idCourse");
        openPdf();
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(uri,idCourse);
            }
        });

    }
public void openPdf(){
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.setType("application/pdf");
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    startActivityForResult(intent , 0 );
}
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( data != null && data.getData() != null && requestCode == 0 ){

            uri  = data.getData();

            binding.pdfView.fromUri(uri)
                    .enableSwipe(true)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .scrollHandle(null)
                    .enableDoubletap(true)
                    .load();
        }else{
            Intent intent=new Intent(UploadFiles.this, ShowMaterial.class);
            intent.putExtra("idCourse",idCourse);
            startActivity(intent);
            finish();
        }

    }
    public void uploadFile(Uri filePath, String courseId) {
        binding.progeressloadingregister.setVisibility(View.VISIBLE);
        if (filePath != null) {
            StorageReference sRef = storage.child("file/" + courseId +"/" +System.currentTimeMillis());
            sRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    ref.child(Constant.MATERIAL).child(courseId).child("material:"+(i++)).setValue(uri+"").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            binding.progeressloadingregister.setVisibility(View.GONE);
                                            Intent intent=new Intent(UploadFiles.this, ShowMaterial.class);
                                            intent.putExtra("idCourse",courseId);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            binding.progeressloadingregister.setVisibility(View.GONE);
                                            Toast.makeText(UploadFiles.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });





                                }
                            });
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadFiles.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null ;
    }








}