package com.example.tinkazorge.problemset3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * To do list: this class produces an app in ehich the user can add tasks to a to do list and can delete them
 * using a long click. A toast will pop up that says "Removed".
 */

public class MainActivity extends Activity {

    //declare widgets
    Button addButton;
    EditText taskText;

    //create array to store values from edittext
    ArrayList<String> task_input;

    //create adapter
    ArrayAdapter<String> arrayAdapter;

    //create context
    Context context1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find widgets by ID
        final ListView listview1 = (ListView) findViewById(R.id.list_view_shapes);
        Button addbutton = (Button) findViewById(R.id.add_button);
        final EditText task_text = (EditText) findViewById(R.id.task_text);

        context1 = MainActivity.this;
        task_input = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, task_input);

        //set data behind listview
        listview1.setAdapter(arrayAdapter);

        //when a user uses a long click
        listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //delete string at position
                task_input.remove(listview1.getItemAtPosition(position));

                //notify the adapter of data change
                arrayAdapter.notifyDataSetChanged();

                //let toast pop up with the text "removed"
                String text = getString(R.string.removed);
                Toast toast = Toast.makeText(context1, text, Toast.LENGTH_SHORT);
                toast.show();

                return true;
            }
        });

        //when the adddbutton is clicked
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get text from edittext
                String task = task_text.getText().toString();

                //add it to the list
                MainActivity.this.task_input.add(task);

                //update the list
                MainActivity.this.arrayAdapter.notifyDataSetChanged();

                //clear edittext
                task_text.setText("");
            }
        });

    }

    //if the user can clicks anywhere in the screen
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            //and the focus is in edittext
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    //clear focus
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}





