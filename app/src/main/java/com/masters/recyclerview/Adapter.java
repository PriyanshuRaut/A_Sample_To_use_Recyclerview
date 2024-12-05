package com.masters.recyclerview;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    public List<model> modelList;
    public onClick clickListener;


    public void setClickListener(onClick onClick){

        this.clickListener = onClick;
    }






    public Adapter(List<model> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_view,
                parent,
                false
        );

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final model item = modelList.get(position);
        holder.text.setText(item.getText());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text;

        public viewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textView1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener!=null){
                clickListener.onClick(v,getAdapterPosition());
            }
        }
    }

}
