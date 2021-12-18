package com.app.ppportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.ppportal.adapter.VisitHistoryAdapter;
import com.app.ppportal.model.PetData;

public class VisitHistoryActivity extends AppCompatActivity {


    RecyclerView rvVisitHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_history);
        rvVisitHistory = findViewById(R.id.rvVisitHistory);
        setData();
    }

    private void setData(){
        PetData petData = (PetData) getIntent().getSerializableExtra("DATA");
        VisitHistoryAdapter adapter = new VisitHistoryAdapter(petData.getPetReasonForVisit());
        rvVisitHistory.setAdapter(adapter);
    }
}