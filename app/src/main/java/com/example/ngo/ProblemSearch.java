package com.example.ngo;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProblemSearch extends AppCompatActivity {
    private Spinner spinner1,spinner2,spinner3;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private ArrayList<String> List=new ArrayList<>();
    private ArrayList<String> ListInd=new ArrayList<>();
    private ArrayList<String> ListUsa=new ArrayList<>();
    private ArrayList<String> ListJap=new ArrayList<>();
    private ArrayList<String> ListAus=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problemsearch);

        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);
        spinner3=findViewById(R.id.spinner3);
        showDataSpinner();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    databaseReference.child("INDIA").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ListInd.clear();
                            for (DataSnapshot item : snapshot.getChildren()) {
                                ListInd.add(item.child("name").getValue(String.class));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProblemSearch.this, R.layout.support_simple_spinner_dropdown_item, ListInd);
                            spinner2.setAdapter(arrayAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (position == 1) {
                    databaseReference.child("USA").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ListUsa.clear();
                            for (DataSnapshot item : snapshot.getChildren()) {
                                ListUsa.add(item.child("name").getValue(String.class));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProblemSearch.this, R.layout.support_simple_spinner_dropdown_item, ListUsa);
                            spinner2.setAdapter(arrayAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (position==2){
                    databaseReference.child("JAPAN").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ListJap.clear();
                            for (DataSnapshot item : snapshot.getChildren()) {
                                ListJap.add(item.child("name").getValue(String.class));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProblemSearch.this, R.layout.support_simple_spinner_dropdown_item, ListJap);
                            spinner2.setAdapter(arrayAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    databaseReference.child("AUSTRALIA").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ListAus.clear();
                            for (DataSnapshot item : snapshot.getChildren()) {
                                ListAus.add(item.child("name").getValue(String.class));
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ProblemSearch.this, R.layout.support_simple_spinner_dropdown_item, ListAus);
                            spinner2.setAdapter(arrayAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private  void showDataSpinner()
    {
        databaseReference.child("country").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                List.clear();
                for (DataSnapshot item:snapshot.getChildren())
                {
                    List.add(item.child("name").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ProblemSearch.this,R.layout.support_simple_spinner_dropdown_item,List);
                spinner1.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
