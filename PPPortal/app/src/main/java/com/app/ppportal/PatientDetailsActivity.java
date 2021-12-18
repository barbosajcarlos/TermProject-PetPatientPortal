package com.app.ppportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ppportal.model.PetData;
import com.app.ppportal.model.VisitHistoryData;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PatientDetailsActivity extends AppCompatActivity {
    TextView etCategory,etName, etPhoneNumber, etEmergencyContact,etHomeAddress,etHeight,etWeight,
            etTemerature,etKnownAllergies, etImmunizationHistory,etReasonForVisit,tvDOB;
    ImageView imgPet;
    EditText etReasonForVisitNew;
    Button btnRecordVisit, btnVisitHistory;
    private PetData petData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        imgPet = findViewById(R.id.imgPet);
        etCategory = findViewById(R.id.etCategory);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNo);
        etEmergencyContact = findViewById(R.id.etEmergencyCont);
        etHomeAddress = findViewById(R.id.etHomeAddr);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        etTemerature = findViewById(R.id.etTemperature);
        etKnownAllergies = findViewById(R.id.etKnownAllergies);
        etImmunizationHistory = findViewById(R.id.etImmunizationHistory);
        etReasonForVisit = findViewById(R.id.etReasonForVisit);
        tvDOB = findViewById(R.id.tvDOB);
        btnRecordVisit = findViewById(R.id.btnRecordVisit);
        btnVisitHistory = findViewById(R.id.btnHistory);
        etReasonForVisitNew = findViewById(R.id.etVisitReasonNew);

        setData();
        setOnClickListener();
    }

    private void setOnClickListener(){
        btnRecordVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()) {
                    recordPetVisit();
                }
            }
        });

        btnVisitHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDetailsActivity.this,VisitHistoryActivity.class);
                intent.putExtra("DATA", petData);
                startActivity(intent);

            }
        });
    }

    private boolean validateData(){
        if(TextUtils.isEmpty(etReasonForVisitNew.getText())){
            Toast.makeText(this, "Please enter reason for visit", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void recordPetVisit(){
        ArrayList<VisitHistoryData> historyData = petData.getPetReasonForVisit();
        historyData.add(new VisitHistoryData(etReasonForVisitNew.getText().toString(), new Date().getTime()));
        petData.setPetReasonForVisit(historyData);
        HashMap<String, Object> map = new HashMap();
        map.put("petReasonForVisit", historyData);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("petProfiles").document(petData.getId())
                .update(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(PatientDetailsActivity.this, "Pet visit recorded.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setData(){
        petData = (PetData) getIntent().getSerializableExtra("DATA");
        Glide.with(imgPet).load(petData.getPetImage()).placeholder(R.drawable.download).into(imgPet);

        etCategory.setText(petData.getPetCategory());
        etName.setText(petData.getPetName());
        etPhoneNumber.setText(petData.getPetPhoneNumber());
        etEmergencyContact.setText(petData.getPetEmergencyContact());
        etHomeAddress.setText(petData.getPetHomeAddress());
        etHeight.setText(petData.getPetHeight());
        etWeight.setText(petData.getPetWeight());
        etTemerature.setText(petData.getPetTemperature());
        etKnownAllergies.setText(petData.getPetKnownAllergies());
        etImmunizationHistory.setText(petData.getPetImmunizationHistory());
        etReasonForVisit.setText(petData.getPetReasonForVisit().get(petData.getPetReasonForVisit().size()-1).getReason());
        tvDOB.setText(petData.getPetDOB());
    }
}