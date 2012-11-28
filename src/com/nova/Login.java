package com.nova;


import java.net.*;
import java.io.*;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class Login extends Activity{
	
	private CheckBox rememberMe;
	EditText username;
	EditText password;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		StrictMode.ThreadPolicy policy = new StrictMode.
		ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		//sets the EditTexts to the their respective ones in their layout
		username = (EditText)findViewById(R.id.username_input);
		password = (EditText)findViewById(R.id.password_input);
		
		//clears the EditText fields upon creation
		username.setText("");
		password.setText("");
		
		//sets up remember me box for remembering login information
		rememberMe = (CheckBox)findViewById(R.id.remember_me_box);
	}
	
	public void loginSelected(View view) {

		String sUsername = username.getText().toString();
		String sPassword = password.getText().toString();
		
		String key = HTTPRequestPoster.HttpPostRequest("http://data.nova-initia.com/getKey.php", "login=1&uname=" + sUsername, null);
		sPassword = NIAlgorithm.sha256(sPassword);
		sPassword += key;
		sPassword = NIAlgorithm.sha256(sPassword);

		String userInfo = HTTPRequestPoster.HttpPostRequest("http://data.nova-initia.com/login2.php", "login=1&pwd="+sPassword+"&uname="+sUsername+"&LastKey="+key, null);
		User.currentUser = User.buildUserFromJSON(userInfo);
		if (User.currentUser != null) {
			Intent prefIntent = new Intent(this,Browser.class);  
		    this.startActivity(prefIntent);
		}
		//shows an alert box for invalid login information
		else{
			AlertDialog.Builder altDialog= new AlertDialog.Builder(this);
			altDialog.setMessage("Invalid Login: Try Again"); 
			altDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int which) {
			    // does nothing when OK is clicked
			   }
			  });
			altDialog.show();
		}
	}
	
	//if register is selected, starts the register intent
	public void registerSelected(View view){
		Intent dasIntent = new Intent(this, Register.class);
		this.startActivity(dasIntent);
	}
	
}