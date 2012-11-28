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

public class DeploySignpost extends Activity{
	EditText titleText;
	EditText commentText;
	CheckBox nsfwChk;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deploy_signpost_layout);
	}
	
	public void cancelSignpostSelected(View view){
		finish();
	}
	
	public void deploySignpost(View view) {
		if (Location.currentLocation == null) {
			return;
		}
		titleText = 		(EditText)findViewById(R.id.signpost_title_input);
		commentText = 		(EditText)findViewById(R.id.signpost_comment_input);
		nsfwChk = 			(CheckBox)findViewById(R.id.nsfw_check);
		
		String title	= titleText.getText().toString();
		String comment 	= commentText.getText().toString();
		boolean nsfw 	= nsfwChk.isChecked();
		
		String params = 
				"Title="+title+
				"&Comment="+comment+
				"&Url="+Location.currentLocation.getUrl()+
				"&NSFW="+String.valueOf(nsfw);
		
		String signURL = "http://data.nova-initia.com/rf/remog/page/"+Location.currentLocation.getHashedURL()+"/"+Location.currentLocation.getHashedDomain()+"/"+ToolType.Sign+".json";
		String json = HTTPRequestPoster.HttpPostRequest(signURL,params,User.currentUser.getLastKey());
		AlertDialog alertDialog = new AlertDialog.Builder(DeploySignpost.this).create();
		alertDialog.setTitle("Place Signpost");
		if (json==null) {
			alertDialog.setMessage("Signpost received a bad response!");
			alertDialog.show();
			return;
		}
		try {
			JSONObject jsonResponse = new JSONObject(json);
			if (jsonResponse.has("pageSet")) {
				User.currentUser.setToolCount(ToolType.Sign, User.currentUser.getToolCount(ToolType.Sign) - 1);
				alertDialog.setMessage("Signpost success!");
				alertDialog.show();
				return;
			} else if (jsonResponse.has("error")) {
				alertDialog.setMessage("Error placing signpost");
				alertDialog.show();
				return;
			} else if (jsonResponse.has("fail") && jsonResponse.getBoolean("fail") == true) {
				User.currentUser.setToolCount(ToolType.Sign, User.currentUser.getToolCount(ToolType.Sign) - 1);
				alertDialog.setMessage("Signpost failed");
				alertDialog.show();
				return;
			} else if (jsonResponse.has("result")) {
				if (jsonResponse.getString("result").equals("Page Full")) {
					alertDialog.setMessage("Page full, please try again later");
					alertDialog.show();
				}
				else
				{
					User.currentUser.setToolCount(ToolType.Sign, User.currentUser.getToolCount(ToolType.Sign) - 1);
					alertDialog.setMessage("Signpost Blocked");
					alertDialog.show();
				}
				return;
			} else {
				alertDialog.setMessage("Signpost received a bad response!");
				alertDialog.show();
				return;
			}
		} catch (JSONException e) {
		}
		finish();
	}
}