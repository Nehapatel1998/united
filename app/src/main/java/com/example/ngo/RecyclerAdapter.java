package com.example.ngo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String Tag="RecyclerView";
    private Context mContext;
    private ArrayList<Messages> messagesArrayList;

    public RecyclerAdapter(Context mContext, ArrayList<Messages> messagesArrayList) {
        this.mContext = mContext;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Getting the text
        holder.textView.setText(messagesArrayList.get(position).getName());
        //Getting the Image
        Glide.with(mContext).load(messagesArrayList.get(position).getImageURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Widgets
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.message_image);
            textView=itemView.findViewById(R.id.message_text);
        }
    }

}
