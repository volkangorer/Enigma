package com.volkangorer.genelkltr;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.volkangorer.genelkltr.databinding.ActivitySettingsBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Settings extends AppCompatActivity {
    Spinner spinner;
    Spinner spinnerGender;
    Spinner spinnerCity;
    ActivitySettingsBinding binding;
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter2;
    ArrayAdapter<CharSequence> adapter3;
    TextView textView;
    TextView textView2;
    TextView textView3;

    SharedPreferences.Editor editor;
    FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLaunher;
    Uri imageData;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetworkInfo;

    SharedPreferences sharedPreferences;
    String name;

    String age;
    String city;
    String gender;

    int black;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        registerLauncher();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        editor = getSharedPreferences("com.volkangorer.genelkltr", MODE_PRIVATE).edit();

        black = 0xFF6C0000;

        binding.editTextTextPersonName.setEnabled(false);

        spinner = binding.spinner;
        //spinnerGender = binding.spinnerGender;
        spinnerCity = binding.spinnerCity;

        adapter = ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                age = adapterView.getItemAtPosition(i).toString();
                textView = (TextView) adapterView.getChildAt(0);
                textView.setTextSize(20);
                textView.setTextColor(black);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*
        adapter2 = ArrayAdapter.createFromResource(this,R.array.gender, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter2);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = adapterView.getItemAtPosition(i).toString();
                textView2 = (TextView) adapterView.getChildAt(0);
                textView2.setTextSize(20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        adapter3 = ArrayAdapter.createFromResource(this,R.array.city, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter3);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = adapterView.getItemAtPosition(i).toString();
                textView3 = (TextView) adapterView.getChildAt(0);
                textView3.setTextSize(20);
                textView3.setTextColor(black);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getInfo();
    }

    public void getInfo(){
        firebaseFirestore.collection("Users").document(user.getEmail()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable  FirebaseFirestoreException error) {
                Map<String,Object> map = value.getData();

                String name = (String) map.get("name");
                String imageUrl = (String) map.get("dowloadurl");
                String age = (String) map.get("age");
                String city = (String) map.get("city");
                if (imageUrl.equals("null")){
                    String uri = "@drawable/profile";
                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    Drawable res = getResources().getDrawable(imageResource);
                    binding.imageView3.setImageDrawable(res);
                }else{
                    Picasso.get().load(imageUrl).into(binding.imageView3);
                }

                binding.editTextTextPersonName.setText(name);
                binding.age.setText("Yaş : "+age);
                binding.city.setText("Şehir : "+ city);
            }
        });
    }

    public void selectImageClicked(View view){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"Permission is needed  for gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        permissionLaunher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                }).show();
            }else{
                permissionLaunher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intentToGallery);
        }

    }

    private void registerLauncher(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult != null){
                        imageData = intentFromResult.getData();
                        binding.imageView3.setImageURI(imageData);

                        /*
                        try {
                            if(Build.VERSION.SDK_INT >= 28){
                                ImageDecoder.Source source = ImageDecoder.createSource(UploadActivity.this.getContentResolver(),imageData);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                                binding.imageView.setImageBitmap(selectedImage);
                            }else{
                                selectedImage = MediaStore.Images.Media.getBitmap(UploadActivity.this.getContentResolver(),imageData);
                                binding.imageView.setImageBitmap(selectedImage);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }*/
                    }
                }
            }
        });

        permissionLaunher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }else{
                    Toast.makeText(Settings.this,"Permission Needed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateOnClicked(View view){
        connectivityManager = (ConnectivityManager) getSystemService(Siralama.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null){
            if(imageData != null){


                storageReference.child(user.getEmail()).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        StorageReference newReference = firebaseStorage.getReference(user.getEmail());

                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String dowloadUrl = uri.toString();

                                HashMap<String , Object> postData = new HashMap<>();
                                postData.put("dowloadurl",dowloadUrl);
                                postData.put("city",city);

                                postData.put("age",age);

                                firebaseFirestore.collection("Users").document(user.getEmail()).update(postData);

                                editor.putInt("degerProfil",0);
                                editor.apply();

                                Intent intent = new Intent(Settings.this,Home.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(Settings.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }else {
                HashMap<String , Object> postData = new HashMap<>();

                postData.put("city",city);

                postData.put("age",age);

                firebaseFirestore.collection("Users").document(user.getEmail()).update(postData);

                Intent intent = new Intent(Settings.this,Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }else{
            Toast.makeText(Settings.this,"Interet bağlantınızı kontrol ediniz",Toast.LENGTH_LONG).show();
        }

    }

    public void removeOnClicked(View view){
        String uri = "@drawable/profile";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        binding.imageView3.setImageDrawable(res);

        imageData = Uri.parse("android.resource://com.volkangorer.genelkltr/drawable/profile");
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

            Intent intentToMain = new Intent(Settings.this,MainActivity.class);
            startActivity(intentToMain);
            finish();
        }else if (item.getItemId() == R.id.home2){
            Intent intentToMain = new Intent(Settings.this,Home.class);
            startActivity(intentToMain);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }




}