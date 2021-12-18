package com.app.ppportal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.app.ppportal.adapter.PatientListAdapter;
import com.app.ppportal.model.PetData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ShowExistingPatientsActivity extends AppCompatActivity {



    RecyclerView rvPatients;
    ProgressBar progressBar;
    EditText etSearch;
    private List<PetData> petDataList =  new ArrayList<>();
    private PatientListAdapter patientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_existing_patients);
        rvPatients = findViewById(R.id.rvPatients);
        progressBar =findViewById(R.id.progressBar);
        etSearch = findViewById(R.id.etSearch);
        getData();
        searchListener();
    }

    private void searchListener(){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<PetData> filteredList = petDataList.stream().filter(new Predicate<PetData>() {
                    @Override
                    public boolean test(PetData petData) {
                        boolean result1 = petData.getPetName().toLowerCase().contains(s.toString().toLowerCase());
                        boolean result2 = petData.getPetCategory().toLowerCase().contains(s.toString().toLowerCase());
                        boolean result3 =  petData.getPetDOB().toLowerCase().contains(s.toString().toLowerCase());

                        return  result1||result2||result3;
                    }
                }).collect(Collectors.toList());
                patientListAdapter.updateList(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void getData(){

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("petProfiles")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    petDataList = task.getResult().toObjects(PetData.class);
                    patientListAdapter = new PatientListAdapter(petDataList);
                    rvPatients.setAdapter(patientListAdapter);
                }
            }
        });
    }
}