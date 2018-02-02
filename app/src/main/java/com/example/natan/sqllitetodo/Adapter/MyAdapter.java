package com.example.natan.sqllitetodo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.natan.sqllitetodo.R;

import java.util.List;

/**
 * Created by natan on 2/1/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Todo> mTodos;
    private RecyclerViewClickListener mListener;


    public interface RecyclerViewClickListener {

        //if we want to on click the item index value
        //void onClick(View view, int position);

        //if we want the whole object to retrive the items
        void onClick(Todo notes);
    }

    public MyAdapter(List<Todo> todos, RecyclerViewClickListener listener) {
        mTodos = todos;
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

        Todo todo = mTodos.get(position);
        holder.txt_Title.setText(todo.getTitle());
        holder.txt_date.setText(todo.getDate_time());


    }

    @Override
    public int getItemCount() {
        return mTodos.size();
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
            int adapterPosition=getAdapterPosition();
            Todo notesClicked=mTodos.get(adapterPosition);
            mListener.onClick(notesClicked);

        }
    }
}
