package com.example.learning.CommonBetweenThem;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.learning.Adapters.MessageRecycleAdapter;
import com.example.learning.Constant;
import com.example.learning.Moduls.MessageContent;
import com.example.learning.Moduls.SignOut;
import com.example.learning.R;
import com.example.learning.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Chat extends AppCompatActivity {
ActivityChatBinding binding;
    String idCourse;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
    MessageRecycleAdapter adapter=new MessageRecycleAdapter();
    Uri ImagePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityChatBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(Chat.this, R.color.colorPrimaryDark));
        getSupportActionBar().hide();
    setContentView(binding.getRoot());
    binding.animationView.animate();
        binding.RecMessage.setAdapter(adapter);

        idCourse=getIntent().getStringExtra("idCourse");
        addDataToList(idCourse);
        adapter.setOnclickItem(new MessageRecycleAdapter.OnclickItem() {
            @Override
            public void Onclick(MessageContent messageContent) {
                ref.child(Constant.KEY_Message).child(messageContent.getMessageID()).setValue(null);
            }
        });
        binding.sentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(iGallery,22);
            }
        });

binding.sent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String message=binding.message.getText().toString().trim();
        validation(message,idCourse);
    }
});



    }



    private void validation(String message, String idCourse) {
           if(message.isEmpty()){
           }else{
               sentMessageToRealTime(message,idCourse);
           }



    }

    private void sentMessageToRealTime(String message, String idCourse) {
  String messageId=ref.push().getKey();
    ref.child(Constant.CHAT).child(idCourse).child(messageId)
            .setValue(new MessageContent(message,auth.getUid(),messageId,"1","")).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    binding.message.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Chat.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==22 && data!=null && data.getData()!=null){
            ImagePicture=data.getData();
            binding.progress.setVisibility(View.VISIBLE);
            sentImageToStorage(ImagePicture);


        }

    }
    private void sentImageToStorage(Uri imagePicture) {
        StorageReference rf=storageReference.child("ChatImages/").child(auth.getUid()+System.currentTimeMillis());
        rf.putFile(imagePicture).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                rf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        sentDataToRealTime(uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Chat.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void sentDataToRealTime(String ImgUrl){
        String p=ref.push().getKey();
        ref.child(Constant.CHAT).child(idCourse).child(p).setValue(new MessageContent("", FirebaseAuth.getInstance().getCurrentUser().getUid(), p,"2",ImgUrl)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                binding.progress.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Chat.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addDataToList(String idCourse){
        ref.child(Constant.CHAT).child(idCourse).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MessageContent c=snapshot.getValue(MessageContent.class);
                ref.child(Constant.USERS).child(c.getSenderID()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        SignOut s=snapshot.getValue(SignOut.class);
                        adapter.addItem(c,s.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    MessageContent m = snapshot.getValue(MessageContent.class);
                    ArrayList<MessageContent> NewList = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        NewList = (ArrayList<MessageContent>) adapter.getList().stream().filter(new Predicate<MessageContent>() {
                            @Override
                            public boolean test(MessageContent messageContent) {
                                return messageContent.getMessageID().equals(m.getMessageID());
                            }
                        });
                    }
                    if (NewList.size() > -1) {

                        int pos = adapter.getList().indexOf(NewList.get(0));
                        if(pos!=-1){
                            adapter.editItem(pos,m);
                        }

                    }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    MessageContent m = snapshot.getValue(MessageContent.class);
                    ArrayList<MessageContent> NewList = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        NewList = (ArrayList<MessageContent>) adapter.getList().stream().filter(new Predicate<MessageContent>() {
                            @Override
                            public boolean test(MessageContent messageContent) {
                                return messageContent.getMessageID().equals(m.getMessageID());
                            }
                        }).collect(Collectors.toList());
                    }
                    if (NewList.size() > -1) {

                        int pos = adapter.getList().indexOf(NewList.get(0));
                        if(pos!=-1){
                            adapter.removeItem(pos);
                        }

                    }


            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}