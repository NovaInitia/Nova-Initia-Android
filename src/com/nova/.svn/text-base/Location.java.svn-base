package com.nova;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class Location {
	public static Location currentLocation;
	private String url;
	private String domain;
	private String hashedURL;
	private String hashedDomain;
	private ArrayList<Tool> locationTools = new ArrayList<Tool>();
	
	public static Location loadLoacation(String url) {
		Location newLocation = new Location();
		Pattern p = Pattern.compile("^[a-z]+:\\/\\/([a-z0-9][-a-z0-9]+(\\.[a-z0-9][-a-z0-9]+)+)[^_]($|\\/|\\?)?[^#]*");
		Matcher m = p.matcher(url);
		if (m.matches() && m.groupCount() > 1) {
			newLocation.setUrl(m.group(0));
			newLocation.setDomain(m.group(1));
			newLocation.setHashedURL(NIAlgorithm.base32md5(newLocation.getUrl()));
			newLocation.setHashedDomain(NIAlgorithm.base32md5(newLocation.getDomain()));
			String json = HTTPRequestPoster.HttpRequest("http://data.nova-initia.com/rf/remog/page/"+newLocation.getHashedURL()+"/"+newLocation.getHashedDomain()+".json", "LASTKEY="+User.currentUser.getLastKey(), User.currentUser.getLastKey());
			System.out.println(json);
		}
		return newLocation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getHashedURL() {
		return hashedURL;
	}

	public void setHashedURL(String hashedURL) {
		this.hashedURL = hashedURL;
	}

	public String getHashedDomain() {
		return hashedDomain;
	}

	public void setHashedDomain(String hashedDomain) {
		this.hashedDomain = hashedDomain;
	}
	
}
