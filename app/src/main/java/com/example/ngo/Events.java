package com.example.ngo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngo.RecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Events extends AppCompatActivity {
    //Widgets
    RecyclerView recyclerView;
    //Instantiate Our Firebase
    private DatabaseReference myRef;
    //Variables
    private ArrayList<Messages> messagesArrayList;
    private RecyclerAdapter recyclerAdapter;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resycler_view);
        recyclerView=findViewById(R.id.recyclerView);
        Context context;
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        //Firebase
        myRef= FirebaseDatabase.getInstance().getReference();
        //Code to get the data from the reference
        messagesArrayList=new ArrayList<>();
        //Clear Array List
        ClearAll();
        //Get data method
        GetDataFromFirebase();
    }

    public void GetDataFromFirebase() {
        Query query=myRef.child("message");
        query.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    Messages messages=new Messages();
                    messages.setImageURL(Objects.requireNonNull(snapshot1.child("image").getValue()).toString());
                    messages.setName(Objects.requireNonNull(snapshot1.child("name").getValue()).toString());
                    messagesArrayList.add(messages);
                }
                recyclerAdapter=new RecyclerAdapter(getApplicationContext(),messagesArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //This method will ensure that the list is empty and then it will fetch the data from firebase
    public void ClearAll(){
        if(messagesArrayList!=null)
        {
            messagesArrayList.clear();

            if (recyclerAdapter!=null)
            {
                recyclerAdapter.notifyDataSetChanged();
            }

        }
        messagesArrayList=new ArrayList<>();
    }
}

