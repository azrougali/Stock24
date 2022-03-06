package com.stock24_dz.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.stock24_dz.Model.category_model;
import com.stock24_dz.R;

import java.util.ArrayList;
import java.util.List;

public class adapter_category extends RecyclerView.Adapter<adapter_category.MyViewHolder>{

    private Context mContext ;
    private List<category_model> mData ;
    private List<category_model> selected_item=new ArrayList<>();
    private boolean is_selected=false;



    public adapter_category(Context mContext, List<category_model> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public adapter_category.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_category,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_category.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(mData.get(position).getCategory());
        Glide.with(mContext).load(mData.get(position).getCategory_image()).into(holder.image);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onLongClick(View v) {
                is_selected=true;
                if (selected_item.contains(mData.get(position))){
                    holder.relativeLayout.setBackgroundColor(Color.TRANSPARENT);
                    selected_item.remove(mData.get(position));
                    holder.title.setText("not selected ");
                }else {
                    holder.itemView.setBackgroundResource(R.color.purple_500);
                    selected_item.add(mData.get(position));
                    holder.title.setText("selected");
                }
                if (selected_item.size()==0){
                    is_selected=false;
                }
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (is_selected){
                    if(selected_item.contains(mData.get(position))){
                        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                        selected_item.remove(mData.get(position));
                        holder.title.setText("no selected");
                    }else{
                        holder.itemView.setBackgroundResource(R.color.purple_500);
                        selected_item.add(mData.get(position));
                        holder.title.setText("selected");
                    }
                    if (selected_item.size()==0){
                        is_selected=false;
                    }
                }
            }
        });

        }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout relativeLayout;

       public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title) ;
            image = (ImageView) itemView.findViewById(R.id.imageview);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative);


        }
    }

    public List<category_model> get_item_selected(){
        return selected_item;
    }

}
