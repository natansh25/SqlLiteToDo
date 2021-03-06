package com.example.natan.sqllitetodo;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.natan.sqllitetodo.Adapter.MyAdapter;
import com.example.natan.sqllitetodo.Adapter.Todo;
import com.example.natan.sqllitetodo.Database.EditActivity;
import com.example.natan.sqllitetodo.Database.MyContract;
import com.example.natan.sqllitetodo.Database.MySqlProvider;
import com.facebook.stetho.Stetho;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab_btn;
    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    //private List<Todo> mTodos;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTimeAndDate();
        fab_btn = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.recyclerView);
        //mTodos = new ArrayList<>();
        MySqlProvider mySqlProvider = new MySqlProvider(this);
        mDb = mySqlProvider.getWritableDatabase();
        Stetho.initializeWithDefaults(this);
        Cursor cursor = getAllNotes();

        //catching the intent coming from edit activity


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //Todo todo = new Todo(title, getTimeAndDate());
        //mTodos.add(todo);
        mMyAdapter = new MyAdapter(cursor, this, new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(Todo notes) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra("object", notes);
                startActivityForResult(i, 2);
            }
        });
        mRecyclerView.setAdapter(mMyAdapter);


        Bundle i = getIntent().getExtras();

        if (i != null) {
            String title = i.getString("save");


            // setting up the recycler view

            mDb = mySqlProvider.getWritableDatabase();
            insert(title, getTimeAndDate());
            // Update the cursor in the adapter to trigger UI to display the new list
            mMyAdapter.swapCursor(getAllNotes());


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


        // itemTouchHelper to delete the items


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;


            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                long id = (long) viewHolder.itemView.getTag();
                removeList(id);
                mMyAdapter.swapCursor(getAllNotes());

            }
        }).attachToRecyclerView(mRecyclerView);


    }

    private void removeList(long id) {

        /*String[] projection = {MyContract.NotesEntry.TABLE_NAME};
        String selection = MyContract.NotesEntry._ID + "=?";
        long[] selectionArgs = {id};*/

        mDb.delete(MyContract.NotesEntry.TABLE_NAME, MyContract.NotesEntry._ID + "=" + id, null);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (data != null) {

                Bundle bundle = data.getExtras();

                String title = bundle.getString("result");
                int id = bundle.getInt("id");
                update(title, id, getTimeAndDate());
                mMyAdapter.swapCursor(getAllNotes());

                //Toast.makeText(this, data.getStringExtra("result"),data.getIntExtra("id"), Toast.LENGTH_SHORT).show();
                //Log.i("hula",data.getStringExtra("result"));
                //Log.i("hula", String.valueOf(bundle.getInt("id")));
            }
        }
    }

    private int update(String title, int id, String timeAndDate) {

        int mNumberOfRowsUpdated = -1;


        ContentValues cv = new ContentValues();
        cv.put(MyContract.NotesEntry.COLUMN_TITLE, title);
        cv.put(MyContract.NotesEntry.COLUMN_DATE_TIME, timeAndDate);

        String selection = MyContract.NotesEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};


        mNumberOfRowsUpdated = mDb.update(MyContract.NotesEntry.TABLE_NAME, cv, selection, selectionArgs);

        return mNumberOfRowsUpdated;
    }

    String getTimeAndDate() {
        Calendar c = Calendar.getInstance();
        //Toast.makeText(this, String.valueOf(c.getTime()), Toast.LENGTH_SHORT).show();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        Log.i("xyz", String.valueOf(formattedDate));

        return formattedDate;


    }
    //---------quering all the data-------------------------

    Cursor getAllNotes() {

        return mDb.query(
                MyContract.NotesEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );


    }


    // insert metod for inserting data into the databse

    private long insert(String word, String date_time) {
        long newID = 0;
        if (word != null && date_time != null) {


            ContentValues cv = new ContentValues();
            cv.put(MyContract.NotesEntry.COLUMN_TITLE, word);
            cv.put(MyContract.NotesEntry.COLUMN_DATE_TIME, date_time);
            newID = mDb.insert(MyContract.NotesEntry.TABLE_NAME, null, cv);

        }
        return newID;
    }
}
