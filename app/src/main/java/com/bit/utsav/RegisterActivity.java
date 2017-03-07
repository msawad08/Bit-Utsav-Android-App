package com.bit.utsav;

import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    int namecount[]={2, 3, 6,5, 6, 2, 3, 2, 1,3, 1, 5, 1, 8, 4, 1, 8, 1, 1, 1, 2, 2, 1, 5,1, 6, 8, 1, 4};
    EditText nameEdit[] =new EditText[8];
    EditText leaderName,sem,branch;
    LinearLayout nameEditHolder;
    LayoutInflater inflater;
    ProgressDialog pg;
    int selectPos=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inflater=getLayoutInflater();
        nameEditHolder = (LinearLayout) findViewById(R.id.name_holder);
        leaderName = (EditText) findViewById(R.id.name1);
        sem =(EditText) findViewById(R.id.sem);
        branch = (EditText) findViewById(R.id.branch);
        spinner = (Spinner) findViewById(R.id.eventchooser);
        List<String> spinnerArray=  Arrays.asList(getResources().getStringArray(R.array.event_name));
        Collections.sort(spinnerArray);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);;
        spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectPos = i;
        nameEditHolder.removeAllViews();
        if(namecount[i]==1)
            leaderName.setHint("Name");
        for (int j=0;j<namecount[i]-1;j++) {
            View v = inflater.inflate(R.layout.name_edit_layout, nameEditHolder, false);
            nameEditHolder.addView(v);
            nameEdit[j] = (EditText) v.findViewById(R.id.memname);
            nameEdit[j].setHint("Team Member "+ (j+2));
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void register(final View view) {
        pg = new ProgressDialog(this);
        pg.setTitle("Register");
        pg.setMessage("Connecting");
        pg.show();
        int count =namecount[selectPos]-1;
        ParseObject obj =new ParseObject("registrations");
        obj.put("event",spinner.getSelectedItem().toString());
        obj.put("leaderName",leaderName.getText().toString());
        obj.put("sem",sem.getText().toString());
        obj.put("branch",branch.getText().toString());

       for(int i=0;i<count;i++) obj.put("Member" + (i + 2), nameEdit[i].getText().toString());
        saveData(obj,view);


    }
    private void saveData(final ParseObject obj, final View view)
    {
        obj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                pg.hide();
                if(e==null) {

                    Snackbar.make(view, "Register Successfully", Snackbar.LENGTH_LONG).show();
                    leaderName.setText("");
                    sem.setText("");
                    branch.setText("");
                    for(int i=0;i<namecount[selectPos]-1;i++)
                        nameEdit[i].setText("");
                }
                else
                    Snackbar.make(view,"Error connecting to internet",Snackbar.LENGTH_LONG).setAction("Retry",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view1) {
                                    pg.show();
                                    saveData(obj,view);
                                }
                            }).show();
            }
        });
    }
}
