package com.example.library_system.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_system.CommonFunctions;
import com.example.library_system.R;
import com.example.library_system.databinding.ChildHistoryLayoutBinding;
import com.example.library_system.modal_class.BookModal;

import java.util.ArrayList;

public class BookHistoryAdapter extends RecyclerView.Adapter<BookHistoryAdapter.Myviewholder>{
    Context context;
    ArrayList<BookModal> bookModals;

    public BookHistoryAdapter(Context context, ArrayList<BookModal> bookModals) {
        this.context = context;
        this.bookModals = bookModals;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view  = LayoutInflater.from(context).inflate(R.layout.child_history_layout,null,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        int size = bookModals.size();
        if(size >=  1) {


            holder.binding.bookname.setText(bookModals.get(size - 1 - position).getBook_name());
            holder.binding.stuemail.setText(bookModals.get(size - 1 - position).getStu_email());
            holder.binding.stuname.setText(bookModals.get(size - 1 - position).getStu_name());
            holder.binding.date.setText(CommonFunctions.givedate(String.valueOf(bookModals.get(size - position - 1).getIssue_date())));
        }
    }

    @Override
    public int getItemCount() {
        return bookModals.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder {
        ChildHistoryLayoutBinding binding;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
             binding = ChildHistoryLayoutBinding.bind(itemView);
        }
    }
}
