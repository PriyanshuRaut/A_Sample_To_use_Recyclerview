package com.masters.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.masters.recyclerview.database.model.modelDatabase;

import java.util.List;

public class Adapter_db extends RecyclerView.Adapter<Adapter_db.viewHolder> {

    public List<modelDatabase> modelList;
    public onClick clickListener;


    public void setClickListener(onClick onClick){

        this.clickListener = onClick;
    }


    public Adapter_db(List<modelDatabase> modelList) {
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

        final modelDatabase item = modelList.get(position);
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
