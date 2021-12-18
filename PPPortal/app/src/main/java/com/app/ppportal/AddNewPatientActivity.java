package com.app.ppportal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ppportal.model.PetData;
import com.app.ppportal.model.VisitHistoryData;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddNewPatientActivity extends AppCompatActivity {

    EditText etCategory,etName, etPhoneNumber, etEmergencyContact,etHomeAddress,etHeight,etWeight, etTemerature,etKnownAllergies, etImmunizationHistory,etReasonForVisit;
    TextView tvDOB;
    Button btnCreate;
    ImageView imgPet;
    private Uri uri;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_patient);

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
        progressBar = findViewById(R.id.progressBar);

        tvDOB = findViewById(R.id.tvDOB);
        btnCreate = findViewById(R.id.btnCreate);
        setOnClickListener();
    }

    private void setOnClickListener(){
        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    progressBar.setVisibility(View.VISIBLE);
                    if (uri == null) {
                        saveData(getPetData());
                    } else {
                        uploadPictureAndSaveData();
                    }
                }
            }
        });

        imgPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(AddNewPatientActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
    }

    private void openCalendar(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                tvDOB.setText(""+dayOfMonth+"/"+month+"/"+year);
            }
        },calendar.get(Calendar.YEAR),1,1);
        datePickerDialog.show();
    }

    private boolean validateData(){

        if(TextUtils.isEmpty(etCategory.getText())){
            Toast.makeText(this, "Please enter pet's category",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(etName.getText())){
            Toast.makeText(this, "Please enter Pet Name",Toast.LENGTH_LONG).show();
            return false;
        }

        if(TextUtils.isEmpty(tvDOB.getText())){
            Toast.makeText(this, "Please enter date of birth",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etPhoneNumber.getText())){
            Toast.makeText(this, "Please enter phone number",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etEmergencyContact.getText())){
            Toast.makeText(this, "Please enter Emergency Contact",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etHomeAddress.getText())){
            Toast.makeText(this, "Please enter home address",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etHeight.getText())){
            Toast.makeText(this, "Please enter Pet height",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etWeight.getText())){
            Toast.makeText(this, "Please enter Pet weight",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etTemerature.getText())){
            Toast.makeText(this, "Please enter Pet Temperature",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etKnownAllergies.getText())){
            Toast.makeText(this, "Please enter Pet known allergies",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etImmunizationHistory.getText())){
            Toast.makeText(this, "Please enter Pet Immunization History",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etReasonForVisit.getText())){
            Toast.makeText(this, "Please enter Pet reason for visit",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void saveData(PetData petData){

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference document = firebaseFirestore.collection("petProfiles").document();
        petData.setId(document.getId());

        document.set(petData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddNewPatientActivity.this, "Pet profile saved successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    @NonNull
    private PetData getPetData() {
        PetData petData = new PetData();
        petData.setPetCategory(etCategory.getText().toString());
        petData.setPetName(etName.getText().toString());
        petData.setPetDOB(tvDOB.getText().toString());
        petData.setPetPhoneNumber(etPhoneNumber.getText().toString());
        petData.setPetEmergencyContact(etEmergencyContact.getText().toString());
        petData.setPetHomeAddress(etHomeAddress.getText().toString());
        petData.setPetHeight(etHeight.getText().toString());
        petData.setPetWeight(etWeight.getText().toString());
        petData.setPetTemperature(etTemerature.getText().toString());
        petData.setPetKnownAllergies(etKnownAllergies.getText().toString());
        petData.setPetImmunizationHistory(etImmunizationHistory.getText().toString());
        ArrayList<VisitHistoryData> visitHistoryData = new ArrayList();
        visitHistoryData.add(new VisitHistoryData(etReasonForVisit.getText().toString(), new Date().getTime()));
        petData.setPetReasonForVisit(visitHistoryData);
        return petData;
    }


    private void uploadPictureAndSaveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference childReference = storageReference.child(new Date().getTime()+".png");

        childReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()) {
                    childReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            PetData petData = getPetData();
                            petData.setPetImage(task.getResult().toString());
                            saveData(petData);
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Erro", e.getLocalizedMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        imgPet.setImageURI(data.getData());
    }
}