package com.example.johnl.recyclerviewwithsqlitedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    //Cursor and context will be provided when instantiated this adapter
    private Context mContext;
    private Cursor mCursor;


    public ItemAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView itemTV;
        private TextView amountTV;
        //ViewHolder constructor gets passed the View(recycler_List_item.xml)
        public ItemViewHolder(View itemView) {
            super(itemView);

            itemTV = itemView.findViewById(R.id.name_textView);
            amountTV = itemView.findViewById(R.id.amount_textView);
        }
    }
    //Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item
    //The new ViewHolder will be used to display items of the adapter using onBindViewHolder(ViewHolder, int, List)
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.recycler_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    //Called by RecyclerView to display the data at the specified position
    //  -holder is the View that represents each of the item
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if(mCursor.moveToPosition(position)){
            String name = mCursor.getString(mCursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME));
            int amount = mCursor.getInt(mCursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_AMOUNT));

            holder.itemTV.setText(name);
            holder.amountTV.setText(String.valueOf(amount));//.setText(String text) requires a String
        }

        //Cursor.moveToPosition(position), Cursor.getString(int i), Cursor.getInt(int i)Cursor.getColumnIndex(String s)
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }


}
