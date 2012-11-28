package com.nova;

import java.net.*;
import java.io.*;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class Register extends Activity {
	
	Spinner classes;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		//setupSpinner();
	}
	
	public void cancelSelected(View view){
		Intent loginTrans = new Intent(this, Login.class);
		this.startActivity(loginTrans);
	}
	
	public void setupSpinner(){
		classes = (Spinner) findViewById(R.id.edit_class);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.class_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    classes.setAdapter(adapter);
	    classes.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	      Toast.makeText(parent.getContext(), "The class is " +
	          parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }
	}

}
