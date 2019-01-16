package com.example.johnl.recyclerviewwithsqlitedemo;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity {

    private ItemAdapter mItemAdapter;
    private RecyclerView mRecyclerView;
    private Cursor mCursor;

    private EditText itemNameET;
    private EditText itemNumberET;
    private Button addBtn;

    //a reference to our database
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //create a database
        ItemDBHelper itemDBHelper = new ItemDBHelper(this);
        //get a reference to the database you just created to write to it
        sqLiteDatabase = itemDBHelper.getWritableDatabase();
        mCursor = getAllItemsCursor();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mItemAdapter = new ItemAdapter(this, mCursor);
        mRecyclerView.setAdapter(mItemAdapter);

        itemNameET = findViewById(R.id.item_name_editText);
        itemNumberET = findViewById(R.id.item_num_editText);
        addBtn = findViewById(R.id.add_item_button);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

        //add a ItemTouchHelper to our recyclerview
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            //onSwiped- when dragging the view from left to right, or right to left
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());//remove the view using the view's tag
            }
        }).attachToRecyclerView(mRecyclerView);//attached the ItemTouchHelper to a RecyclerView
    }
    //remove an item when swiping it left or right
    private void removeItem(long id){
        //find in the table with the given name TABLE_NAME & look for the _ID matching given id
        //   and delete it
        sqLiteDatabase.delete(ItemContract.ItemEntry.TABLE_NAME,
                ItemContract.ItemEntry._ID + "=" + id,
                null);
        //..........................................
        mItemAdapter.swapCursor(getAllItemsCursor());//update the cursor in the adapter
    }

    //add an entry to our data base using insert()
    private void addItem(){
        String item = itemNameET.getText().toString();
        Integer amount = itemNumberET.getText().toString().equals("") ? 0 : Integer.parseInt(itemNumberET.getText().toString());

        if(item.trim().length() != 0 && amount != 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ItemContract.ItemEntry.COLUMN_NAME, item);
            contentValues.put(ItemContract.ItemEntry.COLUMN_AMOUNT, amount);

            sqLiteDatabase.insert(ItemContract.ItemEntry.TABLE_NAME,
                    null,
                    contentValues);
            mItemAdapter.swapCursor(getAllItemsCursor());
            //-after inserting new values into the database, call swapCursor() to give the ItemAdapter
            //  the most recent/updated database values

            //clear input fields for new data
            itemNameET.getText().clear();
            itemNumberET.getText().clear();
        }
    }

    private Cursor getAllItemsCursor(){
        //get all the items in the database & order it by most recent added item appears top of list
        //-query & return from the table TABLE_NAME, with no conditions and order by COLUMN_TIMESTAMP in DESC order
        return sqLiteDatabase.query(//.query() returns a cursor obj
                ItemContract.ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ItemContract.ItemEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}
//-getAllItemCursor() - querys the database and returns all items in the db in descending order(highest appears top)
//      --basically get a updated cursor to our database
//-A LayoutManager is responsible for measuring and positioning item views within a RecyclerView
// as well as determining the policy for when to recycle item views that are no longer visible to the user
//-ItemTouchHelper-This is a utility class to add swipe to dismiss and drag & drop support to RecyclerView
//  swipeDirs: args tell us which direction do we want to handle

