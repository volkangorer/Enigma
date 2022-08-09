package com.volkangorer.genelkltr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.volkangorer.genelkltr.databinding.ActivityCreateAccountBinding;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    ActivityCreateAccountBinding binding;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    Uri imageData;
    SharedPreferences.Editor editor;
    int deger = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        editor = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE).edit();






    }

    public void func(){
        Map<String,Object> map2 = new HashMap<>();
        firebaseFirestore.collection("Totally").document("totalPlayerNumber").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ;
                deger = documentSnapshot.getLong("number").intValue();
                deger++;
                map2.put("number",deger);
            }
        });

        firebaseFirestore.collection("Totally").document("totalPlayerNumber").update(map2);


    }

    public  void registerOnClicked(View view){

        String email = binding.email.getText().toString();
        String username = binding.username.getText().toString();
        String password = binding.password1.getText().toString();
        String password2 = binding.password2.getText().toString();



        Map<String,Object> map = new HashMap<>();

        map.put("name",username);

        map.put("score",0);
        map.put("age","10");
        map.put("city","adana");
        map.put("dowloadurl","null");
        map.put("date", FieldValue.serverTimestamp());




        if (password.equals(password2)){
            if(email.equals("") || password.equals("")){
                Toast.makeText(this,"Enter your e mail or password",Toast.LENGTH_LONG).show();
            }else{
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        firebaseFirestore.collection("Users").document(email).set(map);
                        editor.putInt("score",0);

                        editor.apply();

                        func();

                        Intent intent = new Intent(CreateAccount.this,Home.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateAccount.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        }else {
            Toast.makeText(this,"You have to use same password",Toast.LENGTH_LONG).show();
        }
    }
}