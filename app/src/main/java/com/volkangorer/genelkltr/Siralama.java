package com.volkangorer.genelkltr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.volkangorer.genelkltr.databinding.ActivitySiralamaBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.sax.SAXSource;

public class Siralama extends AppCompatActivity {
    ActivitySiralamaBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    FirebaseAuth auth;

    ArrayList<SıralamaForm> postArrayList;
    Adapter adapter;
    DateFormat dateFormat;
    SharedPreferences.Editor editor;

    int i ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySiralamaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        postArrayList = new ArrayList<>();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        editor = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE).edit();


        getInfo();


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(postArrayList);
        binding.recyclerView.setAdapter(adapter);

        i = 1;
    }





    public void getInfo(){

        firebaseFirestore.collection("Users").orderBy("score", Query.Direction.DESCENDING).limit(10).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {

                if(error != null){
                    Toast.makeText(Siralama.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
                if(value != null){
                    postArrayList.clear();
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String,Object> map = snapshot.getData();
                        String name = (String) map.get("name");
                        System.out.println(name);

                        String city = (String) map.get("city");
                        String age = (String) map.get("age");
                        String downloadUrl = (String) map.get("dowloadurl");


                        int score = snapshot.getLong("score").intValue();

                        Timestamp timestamp = (Timestamp) map.get("date");

                        Date date = timestamp.toDate();
                        String strDate = dateFormat.format(date);
                        System.out.println(strDate);


                        SıralamaForm sıralamaForm = new SıralamaForm(i,name,score,strDate,city,age,downloadUrl);
                        postArrayList.add(sıralamaForm);
                        i++;

                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }


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

            Intent intentToMain = new Intent(Siralama.this,MainActivity.class);
            startActivity(intentToMain);
            finish();
        }else if (item.getItemId() == R.id.home2){
            Intent intentToMain = new Intent(Siralama.this,Home.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}