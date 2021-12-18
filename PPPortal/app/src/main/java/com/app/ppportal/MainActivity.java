package com.app.ppportal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ppportal.model.PetData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    Button btnAddNewPatient, btnShowExistingPatients,btnSearch;
    EditText etName;
    TextView tvDob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddNewPatient = findViewById(R.id.btnAddNewPatient);
        btnShowExistingPatients = findViewById(R.id.btnShowExistingPatients);
        etName =  findViewById(R.id.etName);
        tvDob = findViewById(R.id.tvDOB);
        btnSearch = findViewById(R.id.btnSearch);
        setOnClickListeners();
    }

    private void setOnClickListeners(){
        btnAddNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewPatientActivity.class);
                startActivity(intent);
            }
        });

        btnShowExistingPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowExistingPatientsActivity.class);
                startActivity(intent);
            }
        });

        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()) {
                    searchPatient();
                }
            }
        });
    }

    private void searchPatient(){
        String name = etName.getText().toString().trim();
        String dob = tvDob.getText().toString();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("petProfiles")
                .whereIn("petDOB",new ArrayList(Collections.singleton(dob)))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<PetData> list = task.getResult().toObjects(PetData.class);
                    List<PetData> filteredList = list.stream().filter(new Predicate<PetData>() {
                        @Override
                        public boolean test(PetData petData) {
                            return petData.getPetName().equalsIgnoreCase(name);
                        }
                    }).collect(Collectors.toList());

                    if(filteredList.isEmpty()){
                        Toast.makeText(MainActivity.this, "No patient found. Please add new patient", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, AddNewPatientActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "patient found!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, PatientDetailsActivity.class);
                        intent.putExtra("DATA", filteredList.get(0));
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private void openCalendar(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                tvDob.setText(""+dayOfMonth+"/"+month+"/"+year);
            }
        },calendar.get(Calendar.YEAR),1,1);
        datePickerDialog.show();
    }


    private boolean validateData(){
        if(TextUtils.isEmpty(etName.getText())){
            Toast toast = Toast.makeText(this, "Please enter Pet name.", Toast.LENGTH_SHORT) ;
            toast.setGravity(Gravity.TOP,0,0);
            toast.show();
            return  false;
        }

        if(TextUtils.isEmpty(tvDob.getText())){
            Toast.makeText(this, "Please enter Dob.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}