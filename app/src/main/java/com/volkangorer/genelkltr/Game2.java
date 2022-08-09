package com.volkangorer.genelkltr;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.volkangorer.genelkltr.databinding.ActivityGame2Binding;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game2 extends AppCompatActivity {
    ActivityGame2Binding binding;
    SharedPreferences.Editor editor;
    int score = 0;
    SharedPreferences sharedPreferences;
    FirebaseFirestore firebaseFirestore;
    String name2;
    FirebaseUser user;
    FirebaseAuth auth;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetworkInfo;



    int colorCorrect;
    int black;
    int white;
    ArrayList name;
    ArrayList number2;
    ArrayList wrong;
    Map<String,Object> map;
    CountDownTimer countDownTimer;
    ArrayList<String> number;
    int myPoint = 0;
    int deger = 0;
    int deger2 = 0 ;


    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGame2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        editor = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE).edit();
        sharedPreferences = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE);
        score = sharedPreferences.getInt("score",0);
        name2 = sharedPreferences.getString("name","null");

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        connectivityManager = (ConnectivityManager) getSystemService(Siralama.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();




        wrong = new ArrayList();

        colorCorrect = 0xFF66EC14;
        black = 0xFF6C0000;
        white = 0xFFFFFFFF;

        map = new HashMap();
        name = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h"));
        number2 = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8"));
        startGame0();
        //startGame1();
        function();

        countDownTimer = new CountDownTimer(120000,1000){
            @Override
            public void onTick(long l) {
                binding.time.setText("Kalan Süre : " + l/1000);

            }

            @Override
            public void onFinish() {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game2.this);
                alert.setTitle("Oyun Bitti");
                alert.setMessage("Puanınız : "+myPoint);
                alert.setPositiveButton("Geri Dön", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Game2.this,Home.class);
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("Yeniden Oyna", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Game2.this,Game2.class);
                        startActivity(intent);
                    }
                });
                alert.show();

            }
        }.start();

        //random1 = new Random().nextInt(list.size());


    }

    public void buttonPress(View v){
        switch (v.getId()) {


            case R.id.a1:
                binding.a1.setEnabled(false);
                if(wrong.contains("a1")){
                    binding.a1.setBackgroundColor(black);


                    verifyFalse();
                }else {
                    binding.a1.setBackgroundColor(colorCorrect);
                    verifyCorrect();

                }
                break;

            case R.id.a2:
                binding.a2.setEnabled(false);
                if(wrong.contains("a2")){
                    binding.a2.setBackgroundColor(black);

                    verifyFalse();}else {
                    binding.a2.setBackgroundColor(colorCorrect);
                    verifyCorrect();
                }
                break;
            case R.id.a3:
                binding.a3.setEnabled(false);
                if(wrong.contains("a3")){
                    binding.a3.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.a3.setBackgroundColor(colorCorrect);
                    verifyCorrect();
                }

                break;
            case R.id.a4:
                binding.a4.setEnabled(false);
                if(wrong.contains("a4")){
                    binding.a4.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.a4.setBackgroundColor(colorCorrect);
                    verifyCorrect();
                }

                break;
            case R.id.a5:
                binding.a5.setEnabled(false);
                if(wrong.contains("a5")){
                    binding.a5.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.a5.setBackgroundColor(colorCorrect);
                    verifyCorrect();
                }

                break;

            case R.id.a6:
                binding.a6.setEnabled(false);
                if(wrong.contains("a6")){
                    binding.a6.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.a6.setBackgroundColor(colorCorrect);
                    verifyCorrect();
                }
                break;
            case R.id.a7:
                binding.a7.setEnabled(false);
                if(wrong.contains("a7")){
                    binding.a7.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.a7.setBackgroundColor(colorCorrect);

                    verifyCorrect();}
                break;
            case R.id.a8:
                binding.a8.setEnabled(false);
                if(wrong.contains("a8")){
                    binding.a8.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.a8.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.b1:
                binding.b1.setEnabled(false);
                if(wrong.contains("b1")){
                    binding.b1.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.b1.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.b2:
                binding.b2.setEnabled(false);
                if(wrong.contains("b2")){
                    binding.b2.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.b2.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.b3:
                binding.b3.setEnabled(false);
                if(wrong.contains("b3")){
                    binding.b3.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.b3.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.b4:
                binding.b4.setEnabled(false);
                if(wrong.contains("b4")){
                    binding.b4.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.b4.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.b5:
                binding.b5.setEnabled(false);
                if(wrong.contains("b5")){
                    binding.b5.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.b5.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.b6:
                binding.b6.setEnabled(false);
                if(wrong.contains("b6")){
                    binding.b6.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.b6.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.b7:
                binding.b7.setEnabled(false);
                if(wrong.contains("b7")){
                    binding.b7.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.b7.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.b8:
                binding.b8.setEnabled(false);
                if(wrong.contains("b8")){
                    binding.b8.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.b8.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.c1:
                binding.c1.setEnabled(false);
                if(wrong.contains("c1")){
                    binding.c1.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.c1.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.c2:
                binding.c2.setEnabled(false);
                if(wrong.contains("c2")){
                    binding.c2.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.c2.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.c3:
                binding.c3.setEnabled(false);
                if(wrong.contains("c3")){
                    binding.c3.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.c3.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.c4:
                binding.c4.setEnabled(false);
                if(wrong.contains("c4")){
                    binding.c4.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.c4.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.c5:
                binding.c5.setEnabled(false);
                if(wrong.contains("c5")){
                    binding.c5.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.c5.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.c6:
                binding.c6.setEnabled(false);
                if(wrong.contains("c6")){
                    binding.c6.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.c6.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.c7:
                binding.c7.setEnabled(false);
                if(wrong.contains("c7")){
                    binding.c7.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.c7.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.c8:
                binding.c8.setEnabled(false);
                if(wrong.contains("c8")){
                    binding.c8.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.c8.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.d1:
                binding.d1.setEnabled(false);
                if(wrong.contains("d1")){
                    binding.d1.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.d1.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.d2:
                binding.d2.setEnabled(false);
                if(wrong.contains("d2")){
                    binding.d2.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.d2.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.d3:
                binding.d3.setEnabled(false);
                if(wrong.contains("d3")){
                    binding.d3.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.d3.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.d4:
                binding.d4.setEnabled(false);
                if(wrong.contains("d4")){
                    binding.d4.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.d4.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.d5:
                binding.d5.setEnabled(false);
                if(wrong.contains("d5")){
                    binding.d5.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.d5.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.d6:
                binding.d6.setEnabled(false);
                if(wrong.contains("d6")){
                    binding.d6.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.d6.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.d7:
                binding.d7.setEnabled(false);
                if(wrong.contains("d7")){
                    binding.d7.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.d7.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.d8:
                binding.d8.setEnabled(false);
                if(wrong.contains("d8")){
                    binding.d8.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.d8.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.e1:
                binding.e1.setEnabled(false);
                if(wrong.contains("e1")){
                    binding.e1.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.e1.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.e2:
                binding.e2.setEnabled(false);
                if(wrong.contains("e2")){
                    binding.e2.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.e2.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.e3:
                binding.e3.setEnabled(false);
                if(wrong.contains("e3")){
                    binding.e3.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.e3.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.e4:
                binding.e4.setEnabled(false);
                if(wrong.contains("e4")){
                    binding.e4.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.e4.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.e5:
                binding.e5.setEnabled(false);
                if(wrong.contains("e5")){
                    binding.e5.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.e5.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.e6:
                binding.e6.setEnabled(false);
                if(wrong.contains("e6")){
                    binding.e6.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.e6.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.e7:
                binding.e7.setEnabled(false);
                if(wrong.contains("e7")){
                    binding.e7.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.e7.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.e8:
                binding.e8.setEnabled(false);
                if(wrong.contains("e8")){
                    binding.e8.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.e8.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.f1:
                binding.f1.setEnabled(false);
                if(wrong.contains("f1")){
                    binding.f1.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.f1.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.f2:
                binding.f2.setEnabled(false);
                if(wrong.contains("f2")){
                    binding.f2.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.f2.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.f3:
                binding.f3.setEnabled(false);
                if(wrong.contains("f3")){
                    binding.f3.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.f3.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.f4:
                binding.f4.setEnabled(false);
                if(wrong.contains("f4")){
                    binding.f4.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.f4.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.f5:
                binding.f5.setEnabled(false);
                if(wrong.contains("f5")){
                    binding.f5.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.f5.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.f6:
                binding.f6.setEnabled(false);
                if(wrong.contains("f6")){
                    binding.f6.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.f6.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.f7:
                binding.f7.setEnabled(false);
                if(wrong.contains("f7")){
                    binding.f7.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.f7.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.f8:
                binding.f8.setEnabled(false);
                if(wrong.contains("f8")){
                    binding.f8.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.f8.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;

            case R.id.g1:
                binding.g1.setEnabled(false);
                if(wrong.contains("g1")){
                    binding.g1.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.g1.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.g2:
                binding.g2.setEnabled(false);
                if(wrong.contains("g2")){
                    binding.g2.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.g2.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.g3:
                binding.g3.setEnabled(false);
                if(wrong.contains("g3")){
                    binding.g3.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.g3.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.g4:
                binding.g4.setEnabled(false);
                if(wrong.contains("g4")){
                    binding.g4.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.g4.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.g5:
                binding.g5.setEnabled(false);
                if(wrong.contains("g5")){
                    binding.g5.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.g5.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.g6:
                binding.g6.setEnabled(false);
                if(wrong.contains("g6")){
                    binding.g6.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.g6.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.g7:
                binding.g7.setEnabled(false);
                if(wrong.contains("g7")){
                    binding.g7.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.g7.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.g8:
                binding.g8.setEnabled(false);
                if(wrong.contains("g8")){
                    binding.g8.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.g8.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;

            case R.id.h1:
                binding.h1.setEnabled(false);
                if(wrong.contains("h1")){
                    binding.h1.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.h1.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.h2:
                binding.h2.setEnabled(false);
                if(wrong.contains("h2")){
                    binding.h2.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.h2.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.h3:
                binding.h3.setEnabled(false);
                if(wrong.contains("h3")){
                    binding.h3.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.h3.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.h4:
                binding.h4.setEnabled(false);
                if(wrong.contains("h4")){
                    binding.h4.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.h4.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.h5:
                binding.h5.setEnabled(false);
                if(wrong.contains("h5")){
                    binding.h5.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.h5.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.h6:
                binding.h6.setEnabled(false);
                if(wrong.contains("h6")){
                    binding.h6.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.h6.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.h7:
                binding.h7.setEnabled(false);
                if(wrong.contains("h7")){
                    binding.h7.setBackgroundColor(black);
                    verifyFalse();

                }else {
                    binding.h7.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;
            case R.id.h8:
                binding.h8.setEnabled(false);
                if(wrong.contains("h8")){
                    binding.h8.setBackgroundColor(black);
                    verifyFalse();
                }else {
                    binding.h8.setBackgroundColor(colorCorrect);
                    verifyCorrect();}
                break;

        }
    }

    public void startGame0(){
        map.put("a",0);
        map.put("b",0);
        map.put("c",0);
        map.put("d",0);
        map.put("e",0);
        map.put("f",0);
        map.put("g",0);
        map.put("h",0);
        map.put("1",0);
        map.put("2",0);
        map.put("3",0);
        map.put("4",0);
        map.put("5",0);
        map.put("6",0);
        map.put("7",0);
        map.put("8",0);

    }




    public void function(){

        for ( Object a : name){

            number = new ArrayList<>(number2);

            if (number2.size()<2){
                number2 = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8"));
                map.put("1",0);
                map.put("2",0);
                map.put("3",0);
                map.put("4",0);
                map.put("5",0);
                map.put("6",0);
                map.put("7",0);
                map.put("8",0);
                wrong.clear();
                function();
            }



            if (number.size()==0){

            }else {

                int random1 = new Random().nextInt(number.size());
                String firstNumber = number.get(random1);
                String word = a + firstNumber;
                wrong.add(word);
                number.remove(random1);
                int value1 = (int) map.get(firstNumber);
                value1++;
                map.put(firstNumber, value1);


                int random2 = new Random().nextInt(number.size());
                String secondNumber = number.get(random2);
                String word2 = a + secondNumber;
                wrong.add(word2);
                number.remove(random2);
                int value2 = (int) map.get(secondNumber);
                value2++;
                map.put(secondNumber, value2);


                /*int random3 = new Random().nextInt(number.size());
                String thirdNumber = number.get(random3);
                String word3 = a + thirdNumber;
                wrong.add(word3);
                number.remove(random3);
                int value3 = (int) map.get(thirdNumber);
                value3++;
                map.put(thirdNumber, value3);*/





                if (value1 == 2) {
                    number2.remove(firstNumber);

                }
                if (value2 == 2) {
                    number2.remove(secondNumber);

                }

                /*
                if (value3 == 3) {
                    number2.remove(thirdNumber);
                    System.out.println("&&&&&&&&&&&");
                    System.out.println(number2);
                    System.out.println("&&&&&&&&&&&");
                }*/


            }



        }








    }

    public void verifyCorrect(){
        if (deger < 6){
            deger++;
            myPoint++;
            binding.point.setText("Puan : " + myPoint);
        }else if (deger>=6 && deger<12){
            deger++;
            myPoint+=2;
            binding.point.setText("Puan : " + myPoint);
        }else if (deger>=12 && deger<18){
            deger++;
            myPoint+=3;
            binding.point.setText("Puan : " + myPoint);
        }else if (deger>=18 && deger<24){
            deger++;
            myPoint+=4;
            binding.point.setText("Puan : " + myPoint);
        }else if (deger>=24 && deger<30){
            deger++;
            myPoint+=5;
            binding.point.setText("Puan : " + myPoint);
        }else if (deger>=30 && deger<36){
            deger++;
            myPoint+=6;
            binding.point.setText("Puan : " + myPoint);
        }else if (deger>=36 && deger<42){
            deger++;
            myPoint+=7;
            binding.point.setText("Puan : " + myPoint);
        }else if (deger>=42 && deger<47){
            deger++;
            myPoint+=8;
            binding.point.setText("Puan : " + myPoint);

        }else if (deger==47){

            if (deger2>3){
                deger++;
                myPoint+=9;
                binding.point.setText("Puan : " + myPoint);
            }else {
                deger++;
                myPoint+=9;
                binding.point.setText("Puan : " + myPoint);
                countDownTimer.cancel();
                AlertDialog.Builder alert2 = new AlertDialog.Builder(Game2.this);
                alert2.setTitle("Oyun Bitti");
                alert2.setMessage("Puanınız : "+myPoint);
                alert2.setPositiveButton("Geri Dön", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Game2.this,Home.class);
                        startActivity(intent);

                    }
                });
                alert2.setNegativeButton("Liderlik Tablosu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Game2.this,Siralama.class);
                        startActivity(intent);
                    }
                });
                alert2.show();
            }

        }
    }

    public void verifyFalse(){
        System.out.println(score);
        System.out.println(myPoint);
        deger2++;
        if (deger2 == 4){
            if (myPoint>score){
                editor.putInt("score",myPoint);
                editor.apply();

                Map<String,Object> map =  new HashMap<>();
                map.put("score",myPoint);
                map.put("date", FieldValue.serverTimestamp());


                if (activeNetworkInfo != null){
                    firebaseFirestore.collection("Users").document(user.getEmail()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Game2.this,"En Yüksek Skor : " + myPoint ,Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            Toast.makeText(Game2.this, e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                    countDownTimer.cancel();
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(Game2.this);
                    alert2.setTitle("Oyun Bitti");
                    alert2.setMessage("Puanınız : "+myPoint);
                    alert2.setPositiveButton("Geri Dön", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Game2.this,Home.class);
                            startActivity(intent);
                            finish();

                        }
                    });
                    alert2.setNegativeButton("Yeniden Oyna", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(Game2.this,Game2.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert2.show();
                }else {

                    countDownTimer.cancel();
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(Game2.this);
                    alert2.setTitle("Oyun Bitti");
                    alert2.setMessage("Puanınız : "+myPoint);
                    alert2.setPositiveButton("Geri Dön", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Game2.this,Home.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert2.setNegativeButton("Yeniden Oyna", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(Game2.this,Game2.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert2.show();
                    //Toast.makeText(Home.this,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
                }


            }else{
                countDownTimer.cancel();
                AlertDialog.Builder alert2 = new AlertDialog.Builder(Game2.this);
                alert2.setTitle("Oyun Bitti");
                alert2.setMessage("Puanınız : "+myPoint);
                alert2.setPositiveButton("Geri Dön", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Game2.this,Home.class);
                        startActivity(intent);
                        finish();

                    }
                });
                alert2.setNegativeButton("Yeniden Oyna", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Game2.this,Game2.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alert2.show();
                //Toast.makeText(Home.this,"İnternet Bağlantınızı Kontrol Ediniz",Toast.LENGTH_LONG).show();
            }


        }
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

            Intent intentToMain = new Intent(Game2.this,MainActivity.class);
            startActivity(intentToMain);
            finish();
        }else if (item.getItemId() == R.id.home2){
            Intent intentToMain = new Intent(Game2.this,Home.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

