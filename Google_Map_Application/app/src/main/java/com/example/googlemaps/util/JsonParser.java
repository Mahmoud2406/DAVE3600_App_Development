package com.example.googlemaps.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonParser {
    private static final String TAG = "JParser";

    private JsonParser() {
    }


    public static JSONArray makeJsonArrayFromServer(String url) {
        JSONArray ja = null;
        try {
            ja = new JSONArray(getJsonString(url));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ja;
    }


    private static String getJsonString(String webservice) {

        String jline;
        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(webservice);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "Application/Json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Couldn't contact server");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((jline = br.readLine()) != null) {
                sb.append(jline);
            }

            conn.disconnect();

            return sb.toString();

        } catch (Exception e) {

            Log.d(TAG, "Feil in: " + e.getMessage());
        }

        return "Couldn't get Json String from Server";

    }
}
