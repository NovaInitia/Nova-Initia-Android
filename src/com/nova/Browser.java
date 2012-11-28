package com.nova;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import android.webkit.*;

public class Browser extends Activity {
	
	WebView mWebView;
	EditText urlTextInput;
	Button load;
	String currentUrl;
	LinearLayout notificationBar;
	Button toolbar;
	Intent mIntent;
	final int BARREL_DIALOG = 0;
	final int DOOR_DIALOG = 1;
	final int SIGNPOST_DIALOG = 2;
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
	    
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.browser);
	    
	    mWebView = (WebView) findViewById(R.id.webview);
	    urlTextInput = (EditText)findViewById(R.id.editText1);
	    load = (Button)findViewById(R.id.load_button);
	    
	    //sets up all of the settings for the webview
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.getSettings().setBuiltInZoomControls(true);
	    mWebView.setWebViewClient(new HelloWebViewClient());
	    mWebView.setInitialScale(75);
	    mWebView.setOnTouchListener(new View.OnTouchListener() { 
	    	@Override
	    	public boolean onTouch(View v, MotionEvent event) {
	    	           switch (event.getAction()) { 
	    	               case MotionEvent.ACTION_DOWN: 
	    	               case MotionEvent.ACTION_UP: 
	    	                   if (!v.hasFocus()) { 
	    	                       v.requestFocus(); 
	    	                   } 
	    	                   break; 
	    	           } 
	    	           return false; 
	    	        }
	    	});

	    
	    setLoadButtonListener();
	    setToolbarButtonListener();
	    
	    setupcurrentUrl();
	    
	    notificationBar = (LinearLayout) findViewById(R.id.notification_bar);
	    notificationBar.setVisibility(View.GONE);
	}

	
	/*
	 * called in onCreate to setup the current location's URL to be 
	 * loaded into the webview
	 */
	private void setupcurrentUrl() {
	    if(Location.currentLocation != null)
	    	currentUrl = Location.currentLocation.getUrl();
	    else
	    	currentUrl = "http://www.nova-initia.com/remog";
	    mWebView.loadUrl(currentUrl);
    	urlTextInput.setText(currentUrl);
	}
	
	
	/*
	 * 	  when load is clicked, url is loaded into WebView and current URL is set to
	 * 	  the newly loaded one. currentLocation is updated 
	 */
	private void setLoadButtonListener(){
		load.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				currentUrl=urlTextInput.getText().toString();
				currentUrl = correctURL(currentUrl);
				mWebView.loadUrl(currentUrl);
				urlTextInput.setText(currentUrl);
				Location.currentLocation = Location.loadLoacation(currentUrl);
			}
		});
	}
	
	/*
	 * sets the listener for the toolbar button, starts the toolbar activity
	 */
	private void setToolbarButtonListener(){
		toolbar = (Button) findViewById(R.id.toolbar_button);
		toolbar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), Toolbar.class);
				startActivityForResult(myIntent, 0);
			}
		});
	}
	
	/*
	 * method to correct improper URLs into WebView: Not Working Quite Right
	 */
	private String correctURL(String url){
		String temp1 = "";
		String temp2 = "";
		String finalUrl;
		if(url.length() > 10){
			for(int i = 0; i <= 10; i++){
				if(i <= 3)
					temp1+=url.charAt(i);
				temp2+=url.charAt(i);
			}
		}
		if(temp2.equals("http://www."))
			finalUrl = url;
		else if(temp1.equals("www."))
			finalUrl = "http://"+url;
		else
			finalUrl = "http://www."+url;
		return finalUrl;
	}
	
	private class HelloWebViewClient extends WebViewClient {
	    @Override
		/*
		 * called whenever link is followed on page
		 */
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        currentUrl = url;
			urlTextInput.setText(currentUrl);
			Location.currentLocation = Location.loadLoacation(currentUrl);
			if(Location.currentLocation != null)
				notificationBar.setVisibility(View.VISIBLE);
			else
				notificationBar.setVisibility(View.GONE);
			return true;
	    }
	}
	
	/*
	 * sets up the menu for the activity
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.browser_menu, menu);
	    return true;
	}
	
	
	/*
	 * all of the actions associated with selecting something
	 * in the menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.pref_view:
	        	Intent prefIntent = new Intent(this, Preferences.class);
	    		this.startActivity(prefIntent);
	    		return true;
	        case R.id.logout:
	        	Location.currentLocation = null;
	    		User.currentUser = null;
	        	Intent loginIntent = new Intent(this, Login.class);
	    		this.startActivity(loginIntent);
	        	return true;
	        case R.id.send_message:
	        	Intent messageIntent = new Intent(this, SendMessage.class);
	        	this.startActivity(messageIntent);
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	

}
