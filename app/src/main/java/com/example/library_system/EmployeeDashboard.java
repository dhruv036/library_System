package com.example.library_system;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.library_system.databinding.ActivityEmployeeDashboardBinding;
import com.example.library_system.modal_class.BookModal;
import com.example.library_system.modal_class.Bookinfo;
import com.example.library_system.modal_class.UserModal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Calendar;
import java.util.HashMap;


public class EmployeeDashboard extends AppCompatActivity {
    ActivityEmployeeDashboardBinding binding;

    String scnresult;
    String stu_name,stu_email;
    FirebaseDatabase database;
    String bookname;
    int scantype = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        binding.teachName.setText(getIntent().getStringExtra("Empname"));

//        try {
//            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//            Bitmap bitmap = barcodeEncoder.encodeBitmap(code, BarcodeFormat.QR_CODE, 400, 400);
//            ImageView imageViewQrCode = findViewById(R.id.qrCode);
//            imageViewQrCode.setImageBitmap(bitmap);
//        } catch(Exception e) {
//        }

        //? TO SET NO OF BOOKS(TODAY)

        database.getReference().child("Issued_Book").child(FeatureController.getController().getEmp_name()).orderByChild("issue_date").limitToLast(100).addValueEventListener(new ValueEventListener() {
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

//        database.getReference().child("Issued_Book").child(FeatureController.getController().getEmp_name()).addValueEventListener(new ValueEventListener() {
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

        final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
                result -> {
                    if (scantype == 0) {
                        if (result.getContents() == null) {
                            Toast.makeText(EmployeeDashboard.this, "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("TAG", result.getContents());
                            scnresult = result.getContents();
                            checkuser(scnresult);
//                        Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (scantype == 1) {
                        if (result.getContents() == null) {
                            Toast.makeText(EmployeeDashboard.this, "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("TAG", result.getContents());
                            scnresult = result.getContents();
                            checkbook(scnresult);
//                        Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }
                    if (scantype == 2) {
                        if (result.getContents() == null) {
                            Toast.makeText(EmployeeDashboard.this, "Cancelled", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e("TAG", result.getContents());
                            scnresult = result.getContents();
                            depositebook(scnresult);
//                        Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
        //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
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

        binding.subbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookname != null && !(bookname.equals(""))) {
                    Calendar calendar = Calendar.getInstance();
                    BookModal bookModal = new BookModal(stu_name,stu_email, bookname,scnresult, calendar.getTimeInMillis());
                    database.getReference().child("Issued_Book").child(FeatureController.getController().getEmp_name())
                            .child(String.valueOf(calendar.getTimeInMillis()))
                            .setValue(bookModal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EmployeeDashboard.this, "Booked Issued Successfully", Toast.LENGTH_SHORT).show();
                            binding.stuName.setText("");
                            binding.bookName.setText("");
                            binding.card.setVisibility(View.GONE);
                        }
                    });

                }
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
                        Bookinfo bookinfo = new Bookinfo(id,name);
                        database.getReference().child("Book_table").child(id).setValue(bookinfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(EmployeeDashboard.this, "Book Submited", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
//                v.findViewById(R.id.subb).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                    }
//                });

                alert.show();

//                Customdialog customdialog = new Customdialog();
//                customdialog.showdialog(EmployeeDashboard.this);
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
                       if(bookid.equals(id))
                       {
                           Bookinfo info =  snapshot.getValue(Bookinfo.class);
                          bookname = info.getBookname();
                          binding.bookName.setText(bookname);
                          binding.subbook.setVisibility(View.VISIBLE);
                           Toast.makeText(EmployeeDashboard.this, ""+bookname, Toast.LENGTH_SHORT).show();

                       }

                    }else{
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
                            stu_email =  user.getUser_email();
                            binding.stuName.setText(stu_name);
                            binding.scanbook.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(EmployeeDashboard.this, "User not present", Toast.LENGTH_SHORT).show();
                        }
                    }else {
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

    public  void depositebook(String id)
    {
        database.getReference().child("Issued_Book").child(FeatureController.getController().getEmp_name()).orderByChild("book_id").equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot snapshot1 : snapshot.getChildren())
                    {
                        BookModal bookModal =  snapshot1.getValue(BookModal.class);
                        if(bookModal.getBook_id().equals(id))
                        {
                            database.getReference().child("Issued_Book").child(FeatureController.getController().getEmp_name()).child(snapshot1.getKey()).removeValue();
                            Toast.makeText(EmployeeDashboard.this, "Book Deposited Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        scantype = 0;
    }
}