package com.volkangorer.genelkltr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.volkangorer.genelkltr.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Game extends AppCompatActivity {

    ActivityGameBinding binding;
    FirebaseFirestore firebaseFirestore;
    ArrayList<QuestionForm> questionsFormArrayList;
    ArrayList<String> list;
    ArrayList<String> list2;
    ArrayList<String> list3;
    CountDownTimer countDownTimer;
    SharedPreferences sharedPreferences;
    String name;
    String name2;
    int score;
    int random1;
    int soru;
    String soruDeger;
    String answer;

    int colorCorrect;
    int black;
    int white;

    int deger;

    int myPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();
        questionsFormArrayList = new ArrayList<>();

        sharedPreferences = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE);
        name = sharedPreferences.getString("name","null");

        list = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        colorCorrect = 0xFF66EC14;
        black = 0xFF000000;
        white = 0xFFFFFFFF;

        getQuestions();
        makeFalse();
    }

    public void makeFalse(){
        binding.cevapA.setEnabled(false);
        binding.cevapB.setEnabled(false);
        binding.cevapC.setEnabled(false);
        binding.cevapD.setEnabled(false);
    }

    public void getQuestions(){
        firebaseFirestore.collection("Questions").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                int i = 1;
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    Map<String,Object> map = snapshot.getData();
                    String id = snapshot.getId();
                    String question = (String) map.get("question");
                    String correct = (String) map.get("correct");
                    String false1 = (String) map.get("false1");
                    String false2 = (String) map.get("false2");
                    String false3 = (String) map.get("false3");
                    int pointQuesiton = snapshot.getLong("puan").intValue();

                    QuestionForm questionsForm = new QuestionForm(id,question,correct,false1,false2,false3,pointQuesiton);
                    questionsFormArrayList.add(questionsForm);

                    list.add(id);
                    i++;

                }
                zaman0();

            }
        });
    }
    public void zaman0(){
        new CountDownTimer(6000,1000){
            @Override
            public void onTick(long l) {
                binding.timeText.setText("Oyun Başlıyor " + l/1000);
            }

            @Override
            public void onFinish() {
                createQuestion();
                placedQuestions();
                zaman();

            }
        }.start();


    }
    public void zaman(){
        countDownTimer = new CountDownTimer(20000,1000){

            @Override
            public void onTick(long l) {
                binding.timeText.setText("Kalan Süre : " + l/1000);

            }

            @Override
            public void onFinish() {
                theEnd();

            }
        }.start();
    }

    public void createQuestion(){

            random1 = new Random().nextInt(list.size());

            soruDeger = list.get(random1);

            list.remove(soruDeger);


            soru = Integer.parseInt(soruDeger)-1;


            list2.add(questionsFormArrayList.get(soru).correct);
            list2.add(questionsFormArrayList.get(soru).false1);
            list2.add(questionsFormArrayList.get(soru).false2);
            list2.add(questionsFormArrayList.get(soru).false3);

            for (int i = 0; i<4; i++){
                int random2 = new Random().nextInt(list2.size());
                String cevapDeger = list2.get(random2);
                list3.add(cevapDeger);
                list2.remove(cevapDeger);

            }

            Map<String,Object> map = new HashMap<>();
            map.put("question",soru);


    }

    public void placedQuestions(){


        binding.question.setText(questionsFormArrayList.get(soru).quesiton);
        binding.cevapA.setText(list3.get(0));
        binding.cevapB.setText(list3.get(1));
        binding.cevapC.setText(list3.get(2));
        binding.cevapD.setText(list3.get(3));

        makeEnabled();

    }
    public void verify(){

        if (answer.equals(questionsFormArrayList.get(soru).correct)){
            myPoint += questionsFormArrayList.get(soru).pointQuesiton;


        }
    }

    public void answerAonClicked(View view){
        answer = binding.cevapA.getText().toString();
        binding.cevapA.setTextColor(black);
        makeUnEnabled();
        general();
    }
    public void answerBonClicked(View view){
        answer = binding.cevapB.getText().toString();
        binding.cevapB.setTextColor(black);
        makeUnEnabled();
        general();
    }
    public void answerConClicked(View view){
        answer = binding.cevapC.getText().toString();
        binding.cevapC.setTextColor(black);
        makeUnEnabled();
        general();
    }
    public void answerDonClicked(View view){
        answer = binding.cevapD.getText().toString();
        binding.cevapD.setTextColor(black);
        makeUnEnabled();
        general();
    }

    public void makeUnEnabled(){
        binding.cevapA.setEnabled(false);
        binding.cevapB.setEnabled(false);
        binding.cevapC.setEnabled(false);
        binding.cevapD.setEnabled(false);
    }

    public void makeEnabled(){
        binding.cevapA.setEnabled(true);
        binding.cevapB.setEnabled(true);
        binding.cevapC.setEnabled(true);
        binding.cevapD.setEnabled(true);

        binding.cevapA.setTextColor(white);
        binding.cevapB.setTextColor(white);
        binding.cevapC.setTextColor(white);
        binding.cevapD.setTextColor(white);

    }

    public void showCorrect(){
        if (binding.cevapA.getText().toString().equals(questionsFormArrayList.get(soru).correct)){
            binding.cevapA.setTextColor(colorCorrect);
        }else if (binding.cevapB.getText().toString().equals(questionsFormArrayList.get(soru).correct)){
            binding.cevapB.setTextColor(colorCorrect);
        }else if (binding.cevapC.getText().toString().equals(questionsFormArrayList.get(soru).correct)){
            binding.cevapC.setTextColor(colorCorrect);
        }else if (binding.cevapD.getText().toString().equals(questionsFormArrayList.get(soru).correct)){
            binding.cevapD.setTextColor(colorCorrect);
        }
    }

    public void general(){
        showCorrect();
        verify();
        deger++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (deger<questionsFormArrayList.size()){
                    list3.clear();
                    createQuestion();
                    placedQuestions();


                }else {
                    theEnd();
                }
            }
        },2000);





    }

    public void theEnd(){
        firebaseFirestore.collection("Users").document(name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                score = Integer.parseInt(documentSnapshot.getData().get("score").toString());
            }
        });

        if (myPoint>score){
            Map<String,Object> map = new HashMap<>();
            map.put("score",myPoint);
            map.put("date", FieldValue.serverTimestamp());
            firebaseFirestore.collection("Users").document(name).update(map);
        }

        countDownTimer.cancel();
        AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
        alert.setTitle("Oyun Bitti");
        alert.setMessage("Puanınız : "+myPoint);
        alert.setPositiveButton("Geri Dön", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Game.this,Home.class);
                startActivity(intent);
            }
        });
        alert.show();


    }
}