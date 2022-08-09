package com.volkangorer.genelkltr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.volkangorer.genelkltr.databinding.ActivityHomeBinding;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    ActivityHomeBinding binding;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    String name;
    int games = 0;
    int score = 0;
    int scoreGet = 0;
    String email;
    int degerProfil = 0;



    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        editor = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE).edit();


        sharedPreferences = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE);

        degerProfil = sharedPreferences.getInt("degerProfil",0);



        score = sharedPreferences.getInt("score",0);


        binding.score.setText("En Yüksek Skor : " + score);

        if (degerProfil == 0){
            System.out.println("we are here");
            getInfo();
            degerProfil++;
            editor.putInt("degerProfil",degerProfil);
            editor.apply();
        }else {
            System.out.println("burdaayız");
            String name2 = binding.isim.getText().toString();
            if (name2.equals("Merhaba ,******")){
                getInfo();
            }
        }

    }

    public void getInfo(){
        firebaseFirestore.collection("Users").document(user.getEmail()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable  FirebaseFirestoreException error) {
                Map<String,Object> map = value.getData();
                scoreGet = value.getLong("score").intValue();
                name = (String) map.get("name");
                String imageUrl = (String) map.get("dowloadurl");

                binding.score.setText("En Yüksek Skor : "+scoreGet);
                binding.isim.setText("Merhaba, "+name);

                if (imageUrl.equals("null")){

                }else {
                    Picasso.get().load(imageUrl).into(binding.imageView);
                }



                if(scoreGet >= score){
                    binding.score.setText("En Yüksek Skor : " + scoreGet);
                    editor.putInt("score",scoreGet);
                    editor.apply();
                }else {
                    binding.score.setText("En Yüksek Skor : " + score);
                    Map<String,Object> map2 = new HashMap<>();
                    map.put("date", FieldValue.serverTimestamp());
                    map.put("score",score);

                    firebaseFirestore.collection("Users").document(user.getEmail()).update(map2);
                }



            }
        });










    }

    public void startOnClicked(View view){


        Intent intent = new Intent(Home.this,Game2.class);
        startActivity(intent);
    }

    public void  sıralamaOnClicked(View view){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Siralama.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null){
            Intent intent = new Intent(Home.this,Siralama.class);
            startActivity(intent);
        }else {
            Toast.makeText(Home.this,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
        }
        //&& activeNetworkInfo.isConnected()



    }

    public void settingsOnClicked(View view){
        Intent intent = new Intent(Home.this,Settings.class);
        startActivity(intent);
    }

    public void howPlayOnClicked(View view){
        Intent intent = new Intent(Home.this,Help.class);
        startActivity(intent);
    }

    /*public void cikis(View view){
        auth.signOut();
        Intent intent = new Intent(Home.this,MainActivity.class);
        startActivity(intent);
        finish();

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signout){
            auth.signOut();
            editor.putInt("degerProfil",0);
            editor.apply();
            Intent intentToMain = new Intent(Home.this,MainActivity.class);
            startActivity(intentToMain);
            finish();
        }else if (item.getItemId() == R.id.home2){
            Intent intentToMain = new Intent(Home.this,Home.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}