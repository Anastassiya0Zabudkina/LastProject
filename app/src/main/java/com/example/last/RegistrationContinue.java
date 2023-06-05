package com.example.last;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegistrationContinue extends AppCompatActivity {

    private EditText mEditFN;
    private EditText mEditTelephone;
    private EditText mEditPassword;
    private Button mButtonSave;

    private DatabaseReference users;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_continue);

        mEditFN = findViewById(R.id.editFN);
        mEditTelephone = findViewById(R.id.editTelephone);
        mEditPassword = findViewById(R.id.editTextPassword);
        mButtonSave = findViewById(R.id.buttonSave);

        db = FirebaseDatabase.getInstance("https://endingproject2023-default-rtdb.europe-west1.firebasedatabase.app/");
        users = db.getReference("users");

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditFN.getText().toString().trim();
                String telephone = mEditTelephone.getText().toString().trim();
                String password = mEditPassword.getText().toString().trim();

                if (name.isEmpty() || telephone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegistrationContinue.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Query query = users.orderByChild("telephone").equalTo(telephone);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(RegistrationContinue.this, "User with this telephone already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(name, telephone, password);

                            String userId = users.push().getKey();
                            users.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistrationContinue.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegistrationContinue.this, Registration.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(RegistrationContinue.this, "Failed to create user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("RegistrationContinue", "Database error: " + error.getMessage());
                    }
                });
            }
        });
    }
}
