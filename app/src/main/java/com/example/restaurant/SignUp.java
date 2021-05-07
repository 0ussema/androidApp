package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText name , phone, password;
    Button btnSignUp;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        name = (EditText) findViewById(R.id.editName);
        phone = (EditText) findViewById(R.id.editPhone);
        password = (EditText) findViewById(R.id.editPassword);

        btnSignUp = (Button) findViewById(R.id.btnSignUpp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("user");

                ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("please waiting !!!");
                mDialog.show();


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child(phone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "phone number already register", Toast.LENGTH_SHORT).show();
                        }else {
                            mDialog.dismiss();
                            String name1 = name.getText().toString();
                            String phone1 = phone.getText().toString();
                            String password1 = password.getText().toString();

                            User user = new User(name1,phone1,password1);
                            reference.child(phone1).setValue(user);

                            Toast.makeText(SignUp.this, "sign up successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }
}