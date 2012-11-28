package com.nova;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class DeployBarrel extends Activity{
	EditText sgText;
	EditText trapText;
	EditText barrelText;
	EditText spiderText;
	EditText doorText;
	EditText signpostText;
	EditText shieldsText;
	EditText messageText;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deploy_barrel_layout);	
	}
	
	public void cancelBarrelSelected(View view){
		finish();
	}
	
	public void deployBarrel(View view) {
		if (Location.currentLocation == null) {
			return;
		}
		sgText = 		(EditText)findViewById(R.id.barrel_sg_input);
		trapText = 		(EditText)findViewById(R.id.barrel_trap_input);
		barrelText = 	(EditText)findViewById(R.id.barrel_barrel_input);
		spiderText = 	(EditText)findViewById(R.id.barrel_spider_input);
		doorText = 		(EditText)findViewById(R.id.barrel_door_input);
		signpostText = 	(EditText)findViewById(R.id.barrel_signpost_input);
		shieldsText = 	(EditText)findViewById(R.id.barrel_shield_input);
		messageText = 	(EditText)findViewById(R.id.barrel_message_input);
		
		int sg 			= Integer.parseInt(sgText.getText().toString());
		int traps 		= Integer.parseInt(trapText.getText().toString());
		int barrels 	= Integer.parseInt(barrelText.getText().toString());
		int spiders 	= Integer.parseInt(spiderText.getText().toString());
		int doors 		= Integer.parseInt(doorText.getText().toString());
		int signposts 	= Integer.parseInt(signpostText.getText().toString());
		int shields 	= Integer.parseInt(shieldsText.getText().toString());
		String message 	= messageText.getText().toString();
		
		int limit = (User.currentUser.getUserClass()==Class.Giver) ? 100 : 10;
		if (((sg/10)+traps+barrels+spiders+shields+doors+signposts) > limit)
		{
			// reject barrel
		}
		AlertDialog alertDialog = new AlertDialog.Builder(DeployBarrel.this).create();
		alertDialog.setTitle("Stash Barrel");
		if(traps==0&&barrels==0&&spiders==0&&shields==0&&doors==0&&signposts==0&&sg==0)
		{
			alertDialog.setMessage("Cannot Stash Empty Barrels");
			alertDialog.show();
			return;
		}
		String params = 
				"Comment="+message+
				"&Tool0="+String.valueOf(traps)+
				"&Tool1="+String.valueOf(barrels)+
				"&Tool2="+String.valueOf(spiders)+
				"&Tool3="+String.valueOf(shields)+
				"&Tool4="+String.valueOf(doors)+
				"&Tool5="+String.valueOf(signposts)+
				"&Sg="+String.valueOf(sg);
		String barrelURL = "http://data.nova-initia.com/rf/remog/page/"+Location.currentLocation.getHashedURL()+"/"+Location.currentLocation.getHashedDomain()+"/"+ToolType.Barrel+".json";
		String json = HTTPRequestPoster.HttpPostRequest(barrelURL,params,User.currentUser.getLastKey());
		if (json==null) {
			alertDialog.setMessage("Barrel Stash received a bad response!");
			alertDialog.show();
			return;
		}
		try {
			JSONObject jsonResponse = new JSONObject(json);
			if (jsonResponse.has("pageSet")) {
				User.currentUser.setToolCount(ToolType.Trap, User.currentUser.getToolCount(ToolType.Trap) - traps);
				User.currentUser.setToolCount(ToolType.Barrel, User.currentUser.getToolCount(ToolType.Barrel) - barrels);
				User.currentUser.setToolCount(ToolType.Spider, User.currentUser.getToolCount(ToolType.Spider) - spiders);
				User.currentUser.setToolCount(ToolType.Shield, User.currentUser.getToolCount(ToolType.Shield) - shields);
				User.currentUser.setToolCount(ToolType.Door, User.currentUser.getToolCount(ToolType.Door) - doors);
				User.currentUser.setToolCount(ToolType.Sign, User.currentUser.getToolCount(ToolType.Sign) - signposts);
				User.currentUser.setSG(User.currentUser.getSG()-sg);
				alertDialog.setMessage("Barrel success");
				alertDialog.show();
				return;
			} else if (jsonResponse.has("error") && jsonResponse.getString("error").equals("low inventory")) {
				alertDialog.setMessage("Error: low inventory");
				alertDialog.show();
				return;
			} else if (jsonResponse.has("fail") && jsonResponse.getBoolean("fail") == true) {
				alertDialog.setMessage("Barrel fail");
				alertDialog.show();
				return;
			} else {
				alertDialog.setMessage("Barrel Stash received a bad response!");
				alertDialog.show();
				return;
			}
		} catch (JSONException e) {
		}
		finish();
	}
}