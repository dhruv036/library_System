package com.example.library_system.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library_system.CommonFunctions;
import com.example.library_system.R;
import com.example.library_system.databinding.ChildBooklistBinding;
import com.example.library_system.modal_class.Bookinfo;

import java.util.ArrayList;

public class Book_list_Adapter extends  RecyclerView.Adapter<Book_list_Adapter.Myviewholder> {
    ArrayList<Bookinfo> arrayList = new ArrayList<>();
    Context  context;

    public Book_list_Adapter(ArrayList<Bookinfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.child_booklist,null,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
      holder.binding.bookname.setText(arrayList.get(position).getBookname());
      //holder.binding.date.setText(CommonFunctions.givedate(String.valueOf(arrayList.get(posit)));
        holder.binding.bookid.setText(arrayList.get(position).getBookid());
        holder.binding.bookaut.setText(arrayList.get(position).getBookAuthor());
      holder.binding.moreinfo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              holder.binding.show.setVisibility(View.VISIBLE);
              holder.binding.stuemail2.setText(arrayList.get(position).getStuemail());
          }
      });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Myviewholder  extends RecyclerView.ViewHolder {
        ChildBooklistBinding binding;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            binding = ChildBooklistBinding.bind(itemView);
        }
    }
}
