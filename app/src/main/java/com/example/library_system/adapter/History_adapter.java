package com.example.library_system.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class History_adapter extends RecyclerView.Adapter<History_adapter.Myviewholder>{


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
