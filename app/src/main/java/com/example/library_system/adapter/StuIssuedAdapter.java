package com.example.library_system.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_system.CommonFunctions;
import com.example.library_system.R;
import com.example.library_system.databinding.IssuedbookChildLayoutBinding;
import com.example.library_system.modal_class.Bookmodal_Stu;

import java.util.ArrayList;

public class StuIssuedAdapter  extends RecyclerView.Adapter<StuIssuedAdapter.Myviewholder> {
     ArrayList<Bookmodal_Stu> list = new ArrayList<>();
     Context context;
    public StuIssuedAdapter(Context context , ArrayList<Bookmodal_Stu> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.issuedbook_child_layout,null,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        int size = list.size();
        if(size >=  1) {


               holder.binding.bookname.setText("Book Name:    "+list.get(size-1-position).getBookName());
                   holder.binding.date.setText("Date:  "+CommonFunctions.givedate(String.valueOf(list.get(size - position - 1).getIssueDate())));
                 holder.binding.bookid.setText("Book Id:           "+list.get(size-1-position).getBookId());
                holder.binding.empname.setText("Issuer Name:  "+list.get(size-1-position).getEmpName());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        IssuedbookChildLayoutBinding binding;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            binding= IssuedbookChildLayoutBinding.bind(itemView);
        }
    }
}
