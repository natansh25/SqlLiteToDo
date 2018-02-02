package com.example.natan.sqllitetodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.natan.sqllitetodo.Adapter.MyAdapter;
import com.example.natan.sqllitetodo.Adapter.Todo;
import com.example.natan.sqllitetodo.Database.EditActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab_btn;
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private List<Todo> mTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTimeAndDate();
        fab_btn = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recyclerView);
        mTodos = new ArrayList<>();

        //catching the intent coming from edit activity


        Bundle i = getIntent().getExtras();

        if (i != null) {
            String title = i.getString("save");


            // setting up the recycler view

            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            Todo todo = new Todo(title, getTimeAndDate());
            mTodos.add(todo);
            mMyAdapter = new MyAdapter(mTodos, new MyAdapter.RecyclerViewClickListener() {
                @Override
                public void onClick(Todo notes) {
                    Intent i=new Intent(MainActivity.this,EditActivity.class);
                    i.putExtra("object",notes);
                    startActivityForResult(i,2);
                }
            });
            mRecyclerView.setAdapter(mMyAdapter);
        }


        //----FloatingAction button onClick-------

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                startActivity(i);
                //Toast.makeText(MainActivity.this, "huaaallaalla", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==2)
        {
            if (data !=null)
            {
                Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    String getTimeAndDate() {
        Calendar c = Calendar.getInstance();
        //Toast.makeText(this, String.valueOf(c.getTime()), Toast.LENGTH_SHORT).show();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        Log.i("xyz", String.valueOf(formattedDate));

        return formattedDate;


    }
}
