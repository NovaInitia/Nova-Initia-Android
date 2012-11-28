package com.nova;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.view.Menu;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Toolbar extends Activity {

	// Button web = new Button();
	private String currentUrl;
	private LinearLayout barrelPanel;
	private LinearLayout doorPanel;
	private LinearLayout signpostPanel;
	private Button doorButton;
	private Button barrelButton;
	private Button signpostButton;
	private Button trapButton;
	LinearLayout currentPanel;
	private List<Tool> pageTools = new ArrayList<Tool>();
	private int currentIndex = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toolbar);
		syncToolbar();

		// sets up the interaction panels
		barrelPanel = (LinearLayout) findViewById(R.id.barrel_found_layout);
		doorPanel = (LinearLayout) findViewById(R.id.door_found_layout);
		signpostPanel = (LinearLayout) findViewById(R.id.signpost_found_layout);

		// sets up the buttons
		barrelButton = (Button) findViewById(R.id.deploy_barrel);
		doorButton = (Button) findViewById(R.id.deploy_door);
		signpostButton = (Button) findViewById(R.id.deploy_singpost);
		trapButton = (Button) findViewById(R.id.deploy_trap);

		currentPanel = barrelPanel;
		doorPanel.setVisibility(View.GONE);
		signpostPanel.setVisibility(View.GONE);

		setBarrelButtonListener();
		setDoorButtonListener();
		setSignpostButtonListener();
		setTrapDeployListener();
	}

	public void onStart (){
		super.onStart();
		syncToolbar();
	}
	
	public void syncToolbar() {
		User.syncUser();
		((TextView) findViewById(R.id.trapCnt)).setText(Integer.valueOf(
				User.currentUser.getToolCount(ToolType.Trap)).toString());
		((TextView) findViewById(R.id.barrelCnt)).setText(Integer.valueOf(
				User.currentUser.getToolCount(ToolType.Barrel)).toString());
		((TextView) findViewById(R.id.signpostCnt)).setText(Integer.valueOf(
				User.currentUser.getToolCount(ToolType.Sign)).toString());
		((TextView) findViewById(R.id.doorwayCnt)).setText(Integer.valueOf(
				User.currentUser.getToolCount(ToolType.Door)).toString());
		((TextView) findViewById(R.id.spiderCnt)).setText(Integer.valueOf(
				User.currentUser.getToolCount(ToolType.Spider)).toString());
		((TextView) findViewById(R.id.shieldCnt)).setText(Integer.valueOf(
				User.currentUser.getToolCount(ToolType.Shield)).toString());
		((TextView) findViewById(R.id.sg)).setText(String.valueOf(User.currentUser.getSG()));
		((TextView) findViewById(R.id.shield_counter)).setText(String.valueOf(User.currentUser.getShieldCount()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.toolbar_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.web_view:
			Intent browserIntent = new Intent(this, Browser.class);
			this.startActivity(browserIntent);
			return true;
		case R.id.pref_view:
			Intent prefIntent = new Intent(this, Preferences.class);
			this.startActivity(prefIntent);
			return true;
		case R.id.logout:
			Intent loginIntent = new Intent(this, Login.class);
			this.startActivity(loginIntent);
			return true;
		case R.id.send_message:
			Intent messageIntent = new Intent(this, SendMessage.class);
			this.startActivity(messageIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setBarrelButtonListener() {
		barrelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), DeployBarrel.class);
				startActivityForResult(myIntent, 0);
			}
		});
	}

	private void setDoorButtonListener() {
		doorButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), DeployDoor.class);
				startActivityForResult(myIntent, 0);
			}
		});
	}

	private void setSignpostButtonListener() {
		signpostButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(),
						DeploySignpost.class);
				startActivityForResult(myIntent, 0);
			}
		});
	}

	public void nextTool(View view) {
		if (currentIndex != pageTools.size() - 1)
			currentIndex++;
		refreshPanelView();
	}

	public void prevTool(View view) {
		if (currentIndex != 0)
			currentIndex--;
		refreshPanelView();
	}
	
	public void deploySpider(View view) {
		String spiderUrl = "http://data.nova-initia.com/rf/remog/page/"
				+ Location.currentLocation.getHashedURL() + "/"
				+ Location.currentLocation.getHashedDomain() + "/"
				+ ToolType.Spider + ".json";
		String json = HTTPRequestPoster.HttpPostRequest(spiderUrl,
				"LASTKEY=" + User.currentUser.getLastKey(),
				User.currentUser.getLastKey());
		try {
			JSONObject jsonResponse = new JSONObject(json);
			AlertDialog alertDialog = new AlertDialog.Builder(
				Toolbar.this).create();
			alertDialog.setTitle("Place Spider");
			if (jsonResponse.has("pageSet")) { // Success
				User.currentUser.setToolCount(ToolType.Spider,
						User.currentUser
								.getToolCount(ToolType.Spider) - 1);
				alertDialog.setMessage("Spider successfully placed.");
				alertDialog.show();
			}
			if (jsonResponse.has("result")) { // Problem
				if (jsonResponse.getString("result") == "Page Full") {
					alertDialog.setMessage("Page Full.");
					alertDialog.show();
				} 
				else // Spider blocked it
				{
					User.currentUser.setToolCount(ToolType.Spider,
							User.currentUser
									.getToolCount(ToolType.Spider) - 1);
					alertDialog.setMessage("Spider set off trap.");
					alertDialog.show();
				}
			}
			if (jsonResponse.has("fail")) { // Failure
				User.currentUser.setToolCount(ToolType.Spider,
						User.currentUser
								.getToolCount(ToolType.Spider) - 1);
				alertDialog.setMessage("Spider failed.");
				alertDialog.show();
			}
			((TextView) findViewById(R.id.spiderCnt)).setText(Integer
					.valueOf(User.currentUser.getToolCount(ToolType.Spider)).toString());
		} catch (JSONException e) {
		}
	}

	private void setTrapDeployListener() {
		trapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (Location.currentLocation == null) {
					return;
				}
				String trapURL = "http://data.nova-initia.com/rf/remog/page/"
						+ Location.currentLocation.getHashedURL() + "/"
						+ Location.currentLocation.getHashedDomain() + "/"
						+ ToolType.Trap + ".json";
				String json = HTTPRequestPoster.HttpPostRequest(trapURL,
						"LASTKEY=" + User.currentUser.getLastKey(),
						User.currentUser.getLastKey());
				try {
					JSONObject jsonResponse = new JSONObject(json);
					AlertDialog alertDialog = new AlertDialog.Builder(
							Toolbar.this).create();
					alertDialog.setTitle("Place Trap");
					if (jsonResponse.has("pageSet")) { // Success
						User.currentUser
								.setToolCount(ToolType.Trap, User.currentUser
										.getToolCount(ToolType.Trap) - 1);
						alertDialog.setMessage("Trap successfully placed.");
						alertDialog.show();
					}
					if (jsonResponse.has("result")) { // Problem
						if (jsonResponse.getString("result") == "Page Full") {
							alertDialog.setMessage("Page Full.");
							alertDialog.show();
						} else // Spider blocked it
						{
							User.currentUser.setToolCount(ToolType.Trap,
									User.currentUser
											.getToolCount(ToolType.Trap) - 1);
							alertDialog.setMessage("Trap blocked by spider.");
							alertDialog.show();
						}
					}
					if (jsonResponse.has("fail")) { // Failure
						User.currentUser.setToolCount(ToolType.Trap, User.currentUser.getToolCount(ToolType.Trap) - 1);
						alertDialog.setMessage("Trap failed.");
						alertDialog.show();
					}
					((TextView) findViewById(R.id.trapCnt)).setText(Integer
							.valueOf(User.currentUser.getToolCount(ToolType.Trap)).toString());
				} catch (Exception e) {
					return;
				}
			}
		});
	}
	
	public void toggleShield(View view) {
		HTTPRequestPoster.HttpPostRequest("http://data.nova-initia.com/rf/remog/user/shield.json","LASTKEY="+User.currentUser.getLastKey(),User.currentUser.getLastKey());
		syncToolbar();
	}

	private void refreshPanelView() {
		// Tool currentTool = pageTools.get(currentIndex);
		// if(Tool.Door == Tool.Door)
	}

}
