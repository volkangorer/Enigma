package com.volkangorer.genelkltr;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.volkangorer.genelkltr.databinding.RecyclerRowBinding;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Adapter extends RecyclerView.Adapter<Adapter.PostHolder> {

    private ArrayList<SıralamaForm> postArrayList;


    public Adapter(ArrayList<SıralamaForm> postArrayList) {
        this.postArrayList = postArrayList;

    }
    @NonNull

    @Override
    public PostHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {

        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.PostHolder holder, int position) {

        holder.recyclerRowBinding.name.setText(postArrayList.get(position).name);

        holder.recyclerRowBinding.point.setText("Skor: " + postArrayList.get(position).score);

        holder.recyclerRowBinding.date.setText("Tarih: " + postArrayList.get(position).date);
        if (postArrayList.get(position).downloadUrl.equals("null")){

        }else {
            Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.imageView2);
        }


    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    class PostHolder extends  RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;
        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
