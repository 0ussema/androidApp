package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class SignIn extends AppCompatActivity {

    EditText phone , password;
    Button btnSignIn;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();

        phone = (EditText) findViewById(R.id.editPhoneee);
        password = (EditText) findViewById(R.id.editPassworddd);
        btnSignIn = (Button) findViewById(R.id.btnSignInn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("user");

                ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("please waiting !!!");
                mDialog.show();



                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if(snapshot.child(phone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            User user = snapshot.child(phone.getText().toString()).getValue(User.class);
                            if(user.getPassword().equals(password.getText().toString()))
                            {

                                Intent intent = new Intent(SignIn.this, Home.class);
                                Common.currentUser = user;
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(SignIn.this, "failed sign in !!!!!", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "user not exist !!!!!", Toast.LENGTH_SHORT).show();
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