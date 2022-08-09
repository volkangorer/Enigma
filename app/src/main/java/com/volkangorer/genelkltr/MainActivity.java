package com.volkangorer.genelkltr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.volkangorer.genelkltr.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;
    String name;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    private FirebaseAuth auth;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);






        firebaseFirestore = FirebaseFirestore.getInstance();

        sharedPreferences = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE);
        name = sharedPreferences.getString("name","null");

        editor = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE).edit();


        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        if(user != null){
            Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);
            finish();

        }


    }

    public void buttonOnClicked(View view){
        String nameGet = binding.nameInput.getText().toString();
        String password = binding.password.getText().toString();


        if(nameGet.equals("") || password.equals("")){
            Toast.makeText(this,"Enter password or email",Toast.LENGTH_LONG).show();
        }else{
            auth.signInWithEmailAndPassword(nameGet,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    firebaseFirestore.collection("Users").document(nameGet).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable  DocumentSnapshot value, @Nullable  FirebaseFirestoreException error) {
                            Map<String,Object> map = value.getData();
                            int score = value.getLong("score").intValue();
                            editor.putInt("score",score);
                            editor.putString("email",nameGet);
                            editor.putInt("degerProfil",0);

                            editor.apply();
                            Intent intent = new Intent(MainActivity.this,Home.class);
                            startActivity(intent);
                            finish();


                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    public void signUpOnClicked(View view){
        Intent intent = new Intent(MainActivity.this,CreateAccount.class);
        startActivity(intent);

    }


}