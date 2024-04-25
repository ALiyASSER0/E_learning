package com.example.learning.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.R;
import java.util.ArrayList;

public class AdapterAllRecycle extends RecyclerView.Adapter<AdapterAllRecycle.Holder>  {
    private ArrayList<String> list;
    private OnclickItem onclickItem;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    private static final int VIEW_TYPE_ATTENDENCE = 0;
    private static final int VIEW_TYPE_CREATE_COURSE = 1;
    private static final int VIEW_TYPE_MATERIAL = 2;
    public void setOnclickItem(OnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }
    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if(viewType==VIEW_TYPE_ATTENDENCE){
          View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_attendence,parent,false);
             return new Holder(view);
      }else if(viewType==VIEW_TYPE_CREATE_COURSE){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_create_course,parent,false);
            return new Holder(view);
        }else if(viewType==VIEW_TYPE_MATERIAL){
          View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_material,parent,false);
          return new Holder(view);
      }else{
          return null;
      }
    }

    @Override
    public int getItemViewType(int position) {
        if(type==0){
            return VIEW_TYPE_ATTENDENCE;
        }else if(type==1){
            return VIEW_TYPE_CREATE_COURSE;
        }else if(type==2){
            return VIEW_TYPE_MATERIAL;
        }else{
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(holder.getItemViewType()==VIEW_TYPE_ATTENDENCE){
           int Resource=holder.itemView.getContext().getResources().getIdentifier("lecture","drawable",holder.itemView.getContext().getPackageName());
            holder.bind(list.get(position),Resource);
        }else if(holder.getItemViewType()==VIEW_TYPE_CREATE_COURSE){
            int Resource=holder.itemView.getContext().getResources().getIdentifier("ic_splash_hero","drawable",holder.itemView.getContext().getPackageName());
            holder.bind(list.get(position),Resource);
        }else if(holder.getItemViewType()==VIEW_TYPE_MATERIAL){
            int Resource=holder.itemView.getContext().getResources().getIdentifier("icons8_material_ui","drawable",holder.itemView.getContext().getPackageName());
            holder.bind(list.get(position),Resource);
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }


    class Holder extends RecyclerView.ViewHolder{
        TextView textView;
        de.hdodenhof.circleimageview.CircleImageView img;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_name);
            img=itemView.findViewById((R.id.Img));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickItem.Onclick(list.get(getLayoutPosition()));
                }
            });

        }
        public void bind(String name,int Resource){
            Log.e("Here3","Here3");
            img.setImageResource(Resource);
            textView.setText(name);
        }


    }







    public interface OnclickItem{
        public void Onclick(String s);
    }


}
