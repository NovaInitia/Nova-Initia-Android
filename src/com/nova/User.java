package com.nova;

import org.json.JSONArray;
import org.json.JSONObject;

public class User {
	public static User currentUser = null;
	
	private int ID = 0;
	private String username = "";
	private String lastKey = "";
	private int[] toolCount = new int[6]; // trap, barrel, spider, shield, door, sign
	private int sg = 0;
	private int shieldCount = 0;
	private int userClass = 0;
	private int[] experience = new int[4]; // refugee, giver, guardian, guide
	private int[] level = new int[4];
	private boolean rememberMe;
	
	public static User buildUserFromJSON(String json) {
		User newUser = new User();
		try {
			JSONObject jsonUser = new JSONObject(json).getJSONObject("user");
			newUser.setID(jsonUser.getInt("ID"));
			newUser.setUsername(jsonUser.getString("UserName"));
			newUser.setLastKey(jsonUser.getString("LastKey"));
			newUser.setToolCount(ToolType.Trap, jsonUser.getInt("Tool0"));
			newUser.setToolCount(ToolType.Barrel, jsonUser.getInt("Tool1"));
			newUser.setToolCount(ToolType.Spider, jsonUser.getInt("Tool2"));
			newUser.setToolCount(ToolType.Shield, jsonUser.getInt("Tool3"));
			newUser.setToolCount(ToolType.Door, jsonUser.getInt("Tool4"));
			newUser.setToolCount(ToolType.Sign, jsonUser.getInt("Tool5"));
			newUser.setSG(jsonUser.getInt("Sg"));
			newUser.setShieldCount(jsonUser.getInt("isShielded"));
			newUser.setUserClass(jsonUser.getInt("Class"));
			newUser.setExperience(Class.Giver, jsonUser.getInt("Experience1"));
			newUser.setExperience(Class.Guardian, jsonUser.getInt("Experience2"));
			newUser.setExperience(Class.Guide, jsonUser.getInt("Experience3"));
			newUser.setLevel(Class.Giver, jsonUser.getInt("LevelClass1"));
			newUser.setLevel(Class.Guardian, jsonUser.getInt("LevelClass2"));
			newUser.setLevel(Class.Guide, jsonUser.getInt("LevelClass3"));
		} catch(Exception e) {
			return null;
		}
		return newUser;
	}
	
	public static void syncUser() {
		String userURL = "http://data.nova-initia.com/rf/remog/user/"+User.currentUser.getID()+".json";
		String json = HTTPRequestPoster.HttpRequest(userURL,"LASTKEY="+User.currentUser.getLastKey(),User.currentUser.getLastKey());
		if (json!=null) {
			User.currentUser = buildUserFromJSON(json);
		}
	}
	
	public User setID(int id) {
		this.ID = id;
		return this;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public User setUsername(String name) {
		username = name;
		return this;
	}

	public User setToolCount(int toolID, int count) {
		toolCount[toolID] = count;
		return this;
	}
	
	public User setSG(int count) {
		sg = count;
		return this;
	}
	
	public int getSG() {
		return sg;
	}
	
	public User setExperience(int classID, int exp) {
		experience[classID] = exp;
		return this;
	}
	
	public User setLevel(int classID, int lvl) {
		level[classID] = lvl;
		return this;
	}

	public int getToolCount(int toolID) {
		return toolCount[toolID];
	}

	public String getLastKey() {
		return lastKey;
	}

	public void setLastKey(String lastKey) {
		this.lastKey = lastKey;
	}
	
	public void setRemember(boolean rem){
		rememberMe = rem;
	}

	public int getUserClass() {
		return userClass;
	}

	public void setUserClass(int userClass) {
		this.userClass = userClass;
	}

	public int getShieldCount() {
		return shieldCount;
	}

	public void setShieldCount(int shieldCount) {
		this.shieldCount = shieldCount;
	}
	
}