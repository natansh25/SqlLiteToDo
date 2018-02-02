package com.example.natan.sqllitetodo.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.natan.sqllitetodo.Database.MyContract;
import com.example.natan.sqllitetodo.R;

import java.util.List;

/**
 * Created by natan on 2/1/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //List<Todo> mTodos;
    private Cursor mCursor;
    private Context mContext;
    private RecyclerViewClickListener mListener;


    public interface RecyclerViewClickListener {

        //if we want to on click the item index value
        //void onClick(View view, int position);

        //if we want the whole object to retrive the items
        void onClick(Todo notes);
    }

    public MyAdapter(Cursor cursor, Context context, RecyclerViewClickListener listener) {
        mCursor = cursor;
        mContext = context;
        mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       /* Todo todo = mTodos.get(position);
        holder.txt_Title.setText(todo.getTitle());
        holder.txt_date.setText(todo.getDate_time());*/

        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        String title = mCursor.getString(mCursor.getColumnIndex(MyContract.NotesEntry.COLUMN_TITLE));
        String data_time = mCursor.getString(mCursor.getColumnIndex(MyContract.NotesEntry.COLUMN_DATE_TIME));
        long id = mCursor.getLong(mCursor.getColumnIndex(MyContract.NotesEntry._ID));
        holder.itemView.setTag(id);

        holder.txt_Title.setText(title);
        holder.txt_date.setText(data_time);


    }

    @Override
    public int getItemCount() {
        //return mTodos.size();
        return mCursor.getCount();

    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_Title, txt_date;


        public MyViewHolder(View itemView) {
            super(itemView);
            txt_Title = itemView.findViewById(R.id.txt_title);
            txt_date = itemView.findViewById(R.id.txt_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           /* int adapterPosition = getAdapterPosition();
            Todo notesClicked = mTodos.get(adapterPosition);
            mListener.onClick(notesClicked);*/


            mCursor.moveToPosition(getAdapterPosition());
            String title = mCursor.getString(mCursor.getColumnIndex(MyContract.NotesEntry.COLUMN_TITLE));
            String data_time = mCursor.getString(mCursor.getColumnIndex(MyContract.NotesEntry.COLUMN_DATE_TIME));
            int id=mCursor.getInt(mCursor.getColumnIndex(MyContract.NotesEntry._ID));
            Todo todo = new Todo(title, data_time,id);
            mListener.onClick(todo);


        }
    }
}
