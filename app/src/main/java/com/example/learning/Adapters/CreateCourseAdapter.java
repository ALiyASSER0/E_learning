package com.example.learning.Adapters;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.Moduls.CreateCourse;
import com.example.learning.databinding.HolderCreateCourseBinding;

import java.util.List;

public class CreateCourseAdapter extends RecyclerView.Adapter<CreateCourseAdapter.Holder> {
private List<CreateCourse> list;
public OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setList(List<CreateCourse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       HolderCreateCourseBinding binding=HolderCreateCourseBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Holder(binding);
    }



    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

    HolderCreateCourseBinding binding;

    public Holder(HolderCreateCourseBinding binding) {
        super(binding.getRoot());
    this.binding=binding;
    binding.getRoot().setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickItem.Onclick(list.get(getLayoutPosition()));
        }
    });
    }

public void bind(CreateCourse createCourse){
    Log.e("erw",createCourse.getCourseName());
        binding.textName.setText(createCourse.getCourseName());
}

    }




public interface OnClickItem{
        public void Onclick(CreateCourse createCourse);
}
}
