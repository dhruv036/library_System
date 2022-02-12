package com.example.library_system;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.library_system.databinding.ActivityEmployeeDashboardBinding;
import com.example.library_system.modal_class.BookModal;
import com.example.library_system.modal_class.Bookinfo;
import com.example.library_system.modal_class.Bookmodal_Stu;
import com.example.library_system.modal_class.UserModal;
import com.example.library_system.adapter.BookHistoryAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.Calendar;



public class EmployeeDashboard extends AppCompatActivity {

    ActivityEmployeeDashboardBinding binding;
    String scnresult;
    String stu_name, stu_email;
    ArrayList<BookModal> bookinfo = new ArrayList<>();
    FirebaseDatabase database;
    String stremail = "";
    SharedPreferences preferences;
    SharedPreferences.Editor preferenceseditor;
    String bookname;
    BookHistoryAdapter adapter;

    int curitem, scrout, total;
    int scantype = 0;
    boolean isscrolling = false;
    BookModal bookissuedinfo = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            preferenceseditor.clear().commit();
            Toast.makeText(EmployeeDashboard.this, "Teacher Logout", Toast.LENGTH_SHORT).show();
            finishAffinity();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        binding.teachName.setText(getIntent().getStringExtra("username"));
        preferences = getSharedPreferences("User_details", Context.MODE_PRIVATE);
        preferenceseditor =preferences.edit();
//        try {
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap = barcodeEncoder.encodeBitmap(code, BarcodeFormat.QR_CODE, 400, 400);
//            ImageView imageViewQrCode = findViewById(R.id.qrCode);
//            imageViewQrCode.setImageBitmap(bitmap);
//        } catch(Exception e) {
//        }



        //? TO SET NO OF BOOKS(TODAY)
        String email = FeatureController.getController().getEmp_email();
        int index = email.indexOf("@");
        stremail = email.substring(0, index);
        count_book();

//        ArrayList<BookModal> arrayList= new ArrayList<>();
//        for(int i =0 ; i<=20 ;i++)
//        {
//            BookModal modal= new BookModal("fgdg","fgdfg","dgfggd","12",1644666001145l+i);
//            database.getReference().child("Issued_Book_Emp").child(stremail)
//                    .child(String.valueOf(1644666001145l+i)).setValue(modal);
//        }




        //------------------------------------------------- -----------------------------------------------//

        //? SHOW HISTORY
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        binding.bookhistory.setLayoutManager(manager);

        binding.showHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.nested.setVisibility(View.VISIBLE);
                binding.loading.setVisibility(View.VISIBLE);
                database.getReference().child("Issued_Book_Emp").child(stremail).orderByKey().limitToLast(10).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                bookissuedinfo = snapshot1.getValue(BookModal.class);
                                bookinfo.add(bookissuedinfo);
                                //       Collections.reverse(bookinfo);
                            }
                            binding.loading.setVisibility(View.GONE);
                            adapter = new BookHistoryAdapter(getApplicationContext(), bookinfo);
                            binding.bookhistory.setAdapter(adapter);
                        } else {
                            Toast.makeText(EmployeeDashboard.this, " No book issued ", Toast.LENGTH_SHORT).show();
                            binding.nested.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


//        binding.bookhistory.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isscrolling = true;
//                }
//            }
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                curitem = manager.getChildCount();
//                total = manager.getItemCount();
//                scrout = manager.findFirstVisibleItemPosition();
//                if (isscrolling && (total == curitem + scrout)) {
//                    isscrolling = false;
//                    paginatehistory(bookinfo.get(total).getIssue_date());
//                }
//            }
//        });

        //-------------------------------------------------  -----------------------------------------------//

//        database.getReference().child("Issued_Book_Emp").child(FeatureController.getController().getEmp_name()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists())
//                {
//                    for(DataSnapshot snapshot1 : snapshot.getChildren())
//                    {
//
//                    }
//                }
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        })


        //-------------------------------------------------  -----------------------------------------------//


        final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if (scantype == 0) {   //? RETURN EMAIL FOR CHECK USER
                        if (result.getContents() == null) {
                            Toast.makeText(EmployeeDashboard.this, "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("TAG", result.getContents());
                            scnresult = result.getContents();
                            checkuser(scnresult);
//                        Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (scantype == 1) {   //? RETURN BOOKID FOR CHECK BOOK
                        if (result.getContents() == null) {
                            Toast.makeText(EmployeeDashboard.this, "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("TAG", result.getContents());
                            scnresult = result.getContents();
                            checkbook(scnresult);
//                        Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (scantype == 2) { //? RETURN BOOKID FOR DEPOSITE BOOK
                        if (result.getContents() == null) {
                            Toast.makeText(EmployeeDashboard.this, "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("TAG", result.getContents());
                            scnresult = result.getContents();
                            Toast.makeText(EmployeeDashboard.this, "Scanned book: " + result.getContents(), Toast.LENGTH_LONG).show();
                            depositebookfun(scnresult);

                        }
                    }

                });
        //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

        //? SCAN FOR BOOK DEPOSITE
        binding.depositebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scantype = 2; // scan book id
                ScanOptions options = new ScanOptions();
                options.setTorchEnabled(true);
                options.setPrompt("Scaning QR");
                options.setTimeout(5000);
                barcodeLauncher.launch(new ScanOptions());
            }
        });

        //? SUBMIT OR ISSUE BOOK
        binding.subbookbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               issuebookfun();
            }
        });

        binding.scanbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scantype = 1; // scan book id
                ScanOptions options = new ScanOptions();
                options.setTorchEnabled(true);
                options.setPrompt("Scaning QR");
                options.setTimeout(5000);
                barcodeLauncher.launch(new ScanOptions());
            }
        });

        binding.issuebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scantype = 0;  //scan user
                ScanOptions options = new ScanOptions();
                options.setTorchEnabled(true);
                options.setPrompt("Scaning QR");
                options.setTimeout(5000);
                barcodeLauncher.launch(new ScanOptions());
            }
        });
// TO ADD A BOOK TO DATABASE
        binding.addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(EmployeeDashboard.this);

//                LinearLayout layout = new LinearLayout(EmployeeDashboard.this);
//                layout.setOrientation(LinearLayout.VERTICAL);
//                EditText bookname, bookid;
//               bookid =  new EditText(getApplicationContext());
//                bookname =  new EditText(getApplicationContext());
//                bookname.setHint("Enter Book name");
//                bookid.setHint("Enter Book id");

//              layout.addView(bookid);
//              layout.addView(bookname);
                View v = getLayoutInflater().inflate(R.layout.dialoglayout, null);
                alert.setView(v);

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText bid, bname;
                        bid = v.findViewById(R.id.subbookid);
                        bname = v.findViewById(R.id.subbookname);
                        String id = bid.getText().toString();
                        String name = bname.getText().toString();
                        Bookinfo bookinfo = new Bookinfo(id, name, false);
                        database.getReference().child("Book_table").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    Toast.makeText(EmployeeDashboard.this, "Book Already Added", Toast.LENGTH_SHORT).show();
                                } else {
                                    database.getReference().child("Book_table").child(id).setValue(bookinfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(EmployeeDashboard.this, "Book Submited", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                alert.show();
            }
        });
    }

    public void checkbook(String bookid) {
        if (bookid != null && !(bookid.equals(""))) {
            database.getReference().child("Book_table").child(bookid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String id = snapshot.getKey().toString();

                        if (bookid.equals(id)) {
                            Bookinfo info = snapshot.getValue(Bookinfo.class);
                            if (info.getBookstatus() == false) {
                                bookname = info.getBookname();
                                binding.bookName.setText(bookname);
                                binding.subbookbt.setVisibility(View.VISIBLE);
                                scantype =0;
                                Toast.makeText(EmployeeDashboard.this, "" + bookname, Toast.LENGTH_SHORT).show();
                            } else {
                                binding.subbookbt.setVisibility(View.GONE);
                                Toast.makeText(EmployeeDashboard.this, info.getBookname() + " already issued", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(EmployeeDashboard.this, "Book not present", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void checkuser(String userr) {
        int index = userr.indexOf("@");
        if (index != -1) {
            String strtemail = userr.substring(0, index);
            database.getReference().child("Student_info").child(strtemail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        UserModal user = snapshot.getValue(UserModal.class);
                        if (userr.equals(user.getUser_email())) {
                            binding.card.setVisibility(View.VISIBLE);
                            stu_name = user.getUser_name();
                            stu_email = user.getUser_email();
                            binding.stuName.setText(stu_name);
                            binding.scanbook.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(EmployeeDashboard.this, "User not present", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EmployeeDashboard.this, "User not Found", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Toast.makeText(EmployeeDashboard.this, "User not FOUND!", Toast.LENGTH_SHORT).show();
        }

    }

    public void depositebookfun(String id) {
        database.getReference().child("Issued_Book_Emp").child(stremail).orderByChild("book_id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        BookModal bookModal = snapshot1.getValue(BookModal.class);
                        if (bookModal.getBook_id().equals(id)) {
                            int in= bookModal.getStu_email().indexOf("@");
                            String trimname  = bookModal.getStu_email().substring(0,in);
                            database.getReference().child("Issued_Book_Emp").child(stremail).child(snapshot1.getKey()).removeValue();
                            Bookinfo bookinfo = new Bookinfo(id, bookModal.getBook_name(), false);
                            database.getReference().child("Book_table").child(id).setValue(bookinfo);

                            database.getReference().child("Issued_Book_Stu").child(trimname).child("Books").child(snapshot1.getKey()).removeValue();                           count_book();
                            database.getReference().child("Issued_Book_Stu").child(trimname).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists())
                                    {
                                        int ii = snapshot.child("noofbook").getValue(Integer.class);
                                        database.getReference().child("Issued_Book_Stu").child(trimname).child("noofbook").setValue(ii-1);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Toast.makeText(EmployeeDashboard.this, "Book Deposite Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void count_book(){
        database.getReference().child("Issued_Book_Emp").child(stremail).orderByChild("issue_date").limitToLast(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = 0;
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        BookModal date = snap.getValue(BookModal.class);
                        if (CommonFunctions.givedate(String.valueOf(date.getIssue_date())).equals("Today")) {
                            counter++;
                        } else {
                            continue;
                        }
                    }
                    binding.noOfBook.setText(String.valueOf(counter));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void issuebookfun()
    {
        if (bookname != null && !(bookname.equals(""))) {
            Calendar calendar = Calendar.getInstance();
            Long time = calendar.getTimeInMillis();
            BookModal bookModal = new BookModal(stu_name, stu_email, bookname, scnresult, time);
            int index  =stu_email.indexOf("@");
            String stuem = stu_email.substring(0,index);
            database.getReference().child("Issued_Book_Stu").child(stuem).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        int ii = snapshot.child("noofbook").getValue(Integer.class);
                        if(ii<3)
                        {
                            database.getReference().child("Issued_Book_Emp").child(stremail)
                                    .child(String.valueOf(calendar.getTimeInMillis()))
                                    .setValue(bookModal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Bookinfo bookinfo = new Bookinfo(scnresult, bookname, true);
                                    database.getReference().child("Book_table").child(scnresult).setValue(bookinfo);
                                    database.getReference().child("Issued_Book_Stu").child(stuem).child("Books").child(String.valueOf(time)).setValue(new Bookmodal_Stu(getIntent().getStringExtra("username"),bookname, scnresult,time));
                                    database.getReference().child("Issued_Book_Stu").child(stuem).child("noofbook").setValue(ii+1);
                                    Toast.makeText(EmployeeDashboard.this, "Booked Issued Successfully", Toast.LENGTH_SHORT).show();
                                    binding.stuName.setText("");
                                    binding.bookName.setText("");
                                    binding.card.setVisibility(View.GONE);
                                }
                            });
                        }else {
                            Toast.makeText(EmployeeDashboard.this, "Limit exceed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    public void paginatehistory(Long index)
    {
        binding.loading.setVisibility(View.VISIBLE);
//        database.getReference().child("Issued_Book_Emp").child(stremail).orderByKey().endAt(index).limitToLast(2).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//
//                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                        bookissuedinfo = snapshot1.getValue(BookModal.class);
//                        BookModal modal= new BookModal("fgdg","fgdfg","dgfggd","12",1644666001145l);
//                        bookinfo.add(modal);
//                        //       Collections.reverse(bookinfo);
//                    }
//                    binding.loading.setVisibility(View.GONE);
//                   adapter.notifyDataSetChanged();
//                 //   binding.bookhistory.setAdapter(new BookHistoryAdapter(getApplicationContext(), bookinfo));
//                } else {
//                    Toast.makeText(EmployeeDashboard.this, " No book issued ", Toast.LENGTH_SHORT).show();
//                    binding.nested.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        BookModal modal= new BookModal("fg6666dg","fgdfg","dgfggd","12",1644666001145l);
        bookinfo.add(modal);
        adapter.notifyDataSetChanged();
    }
}