package com.nova;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class DeployDoor extends Activity{
	EditText urlText;
	EditText hintText;
	EditText commentText;
	CheckBox nsfwChk;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deploy_door_layout);
		
	}
	
	public void cancelDoorSelected(View view){
		finish();
	}
	
	public void deployDoor(View view) {
		if (Location.currentLocation == null) {
			return;
		}
		urlText = 		(EditText)findViewById(R.id.door_url_input);
		hintText = 		(EditText)findViewById(R.id.door_hint_input);
		commentText = 	(EditText)findViewById(R.id.door_comment_input);
		nsfwChk = 		(CheckBox)findViewById(R.id.nsfw_check);
		
		String url 		= urlText.getText().toString();
		String hint 	= hintText.getText().toString();
		String comment 	= commentText.getText().toString();
		boolean nsfw 	= nsfwChk.isChecked();
		
		String params = 
				"Url="+url+
				"&Hint="+hint+
				"&Comment="+comment+
				"&Home="+Location.currentLocation.getUrl()+
				"&NSFW="+String.valueOf(nsfw);
		String doorURL = "http://data.nova-initia.com/rf/remog/page/"+Location.currentLocation.getHashedURL()+"/"+Location.currentLocation.getHashedDomain()+"/"+ToolType.Door+".json";
		String json = HTTPRequestPoster.HttpPostRequest(doorURL,params,User.currentUser.getLastKey());

		AlertDialog alertDialog = new AlertDialog.Builder(DeployDoor.this).create();
		alertDialog.setTitle("Place Door");
		if (json==null) {
			alertDialog.setMessage("Doorway Open received a bad response!");
			alertDialog.show();
			return;
		}
		try {
			JSONObject jsonResponse = new JSONObject(json);
			if (jsonResponse.has("pageSet")) {
				User.currentUser.setToolCount(ToolType.Door, User.currentUser.getToolCount(ToolType.Door) - 1);
				alertDialog.setMessage("Doorway Opened!");
				alertDialog.show();
				return;
			} else if (jsonResponse.has("error")) {
				alertDialog.setMessage("Error placing door");
				alertDialog.show();
				return;
			} else if (jsonResponse.has("fail") && jsonResponse.getBoolean("fail") == true) {
				alertDialog.setMessage("Doorway failed");
				alertDialog.show();
				return;
			} else if (jsonResponse.has("result") && jsonResponse.getString("result").equals("Page Full")) {
				alertDialog.setMessage("Page full, please try again later");
				alertDialog.show();
				return;
			} else {
				alertDialog.setMessage("Doorway Open received a bad response!");
				alertDialog.show();
				return;
			}
		} catch (JSONException e) {
		}
		finish();
	}
}
