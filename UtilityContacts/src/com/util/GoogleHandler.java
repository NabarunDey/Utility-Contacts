package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import userRegistration.appService.inputBeans.UserRegistrationAppServiceIB;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GoogleHandler {
	
	public static UserRegistrationAppServiceIB getGoogleData(String code,String server)
	{
        UserRegistrationAppServiceIB userRegistrationAppServiceIB = new UserRegistrationAppServiceIB();

		  String urlParameters = "code="
                  + code
                  + "&client_id=968847956660-7cs0n3ke5m69hj96hp9sfmqql34gsd4s.apps.googleusercontent.com"
                  + "&client_secret=p1i-Hhz-ZIoyuVWLE1Ht5w_7"
                  + "&redirect_uri=http://"+server+"/loginFunctionGoogle"
                  + "&grant_type=authorization_code";
		try{
		 URL url = new URL("https://accounts.google.com/o/oauth2/token");
         URLConnection urlConn = url.openConnection();
         urlConn.setDoOutput(true);
         OutputStreamWriter writer = new OutputStreamWriter(
                 urlConn.getOutputStream());
         writer.write(urlParameters);
         writer.flush();
         
         //get output in outputString 
         String line, outputString = "";
         BufferedReader reader = new BufferedReader(new InputStreamReader(
                 urlConn.getInputStream()));
         while ((line = reader.readLine()) != null) {
             outputString += line;
         }
         System.out.println(outputString);
         
         //get Access Token 
         JsonObject json = (JsonObject)new JsonParser().parse(outputString);
         String access_token = json.get("access_token").getAsString();

         //get User Info 
         url = new URL(
                 "https://www.googleapis.com/oauth2/v1/userinfo?access_token="
                         + access_token);
         urlConn = url.openConnection();
         outputString = "";
         reader = new BufferedReader(new InputStreamReader(
                 urlConn.getInputStream()));
         while ((line = reader.readLine()) != null) {
             outputString += line;
         }
         JsonObject json2 = (JsonObject)new JsonParser().parse(outputString);
         userRegistrationAppServiceIB.setUsername(json2.get("id").getAsString());
         userRegistrationAppServiceIB.setFirstname(json2.get("given_name").getAsString());
         userRegistrationAppServiceIB.setLastname(json2.get("family_name").getAsString());

     } catch (Exception e) {
         e.printStackTrace();
     }
		return userRegistrationAppServiceIB;
 }

}
