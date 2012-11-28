package com.nova;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class HTTPRequestPoster {
	public static String HttpRequest(String target, String reqpar, String laskKey) {
		String result = null;
		if (target.startsWith("http://")) {
			try {
				StringBuffer sbufferfer = new StringBuffer();
				String urlString = target;
				if (reqpar != null && reqpar.length() > 0) {
					urlString += "?" + reqpar;
				}
				URL url = new URL(urlString);
				HttpURLConnection  conn = (HttpURLConnection)url.openConnection();
				if (laskKey != null) {
					conn.setRequestProperty("Cookie", "LastKey="+laskKey+";");
			    }
				conn.connect();
				BufferedReader bufferferreader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = bufferferreader.readLine()) != null) {
					sb.append(line);
				}
				bufferferreader.close();
				result = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String HttpPostRequest(String targetURL, String urlParameters, String laskKey) 
	{
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", 
	           "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
	      if (laskKey != null) {
	    	  connection.setRequestProperty("X-NOVA-INITIA-LASTKEY", laskKey);
	    	  connection.setRequestProperty("Cookie", "LastKey="+laskKey+";");
	      }
	  
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);
	      connection.connect();

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }

	public static void postData(Reader sbufferfer, URL target, Writer output)
			throws Exception {
		HttpURLConnection httpurlcon = null;
		try {
			httpurlcon = (HttpURLConnection) target.openConnection();
			try {
				httpurlcon.setRequestMethod("POST");
			} catch (ProtocolException e) {
				throw new Exception(
						"Shouldn't happen: HttpURLConnection doesn't support POST??",
						e);
			}
			httpurlcon.setDoOutput(true);
			httpurlcon.setDoInput(true);
			httpurlcon.setUseCaches(false);
			httpurlcon.setAllowUserInteraction(false);
			httpurlcon.setRequestProperty("Content-type", "text/xml; charset="
					+ "UTF-8");
			OutputStream out = httpurlcon.getOutputStream();
			try {
				Writer writer = new OutputStreamWriter(out, "UTF-8");
				pipe(sbufferfer, writer);
				writer.close();
			} catch (IOException e) {
				throw new Exception("IOException while posting sbufferfer", e);
			} finally {
				if (out != null)
					out.close();
			}
			InputStream in = httpurlcon.getInputStream();
			try {
				Reader reader = new InputStreamReader(in);
				pipe(reader, output);
				reader.close();
			} catch (IOException e) {
				throw new Exception("IOException while reading response", e);
			} finally {
				if (in != null)
					in.close();
			}
		} catch (IOException e) {
			throw new Exception("Connection error (is server running at "
					+ target + " ?): " + e);
		} finally {
			if (httpurlcon != null)
				httpurlcon.disconnect();
		}
	}

	private static void pipe(Reader reader, Writer writer) throws IOException {
		char[] buffer = new char[1024];
		int read = 0;
		while ((read = reader.read(buffer)) >= 0) {
			writer.write(buffer, 0, read);
		}
		writer.flush();
	}
}