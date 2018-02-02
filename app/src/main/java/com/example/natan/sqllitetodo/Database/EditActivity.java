package com.example.natan.sqllitetodo.Database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natan.sqllitetodo.Adapter.Todo;
import com.example.natan.sqllitetodo.MainActivity;
import com.example.natan.sqllitetodo.R;

public class EditActivity extends AppCompatActivity {
    private EditText edt;
    private Todo todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        edt=findViewById(R.id.txt_edit);

         todo=getIntent().getParcelableExtra("object");
        if (todo !=null) {
            //Toast.makeText(this, String.valueOf(todo.getTitle()), Toast.LENGTH_SHORT).show();
            edt.setText(todo.getTitle());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {

            if (todo==null) {
                //Toast.makeText(this, "saved !!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditActivity.this, MainActivity.class);
                i.putExtra("save", edt.getText().toString());
                startActivity(i);
                return true;
            }
            else
            {
                Intent intent=new Intent(EditActivity.this,MainActivity.class);
                intent.putExtra("result",edt.getText().toString());
                setResult(2,intent);
                finish();

            }
        }
        return super.onOptionsItemSelected(item);
    }
}
