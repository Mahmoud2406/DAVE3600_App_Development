package com.example.googlemaps.util;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetLocationTask extends AsyncTask<Void, Void, String> {

    JSONObject jsonObject;
    String address;
    String lokasjon;
    String latlon;

    public GetLocationTask(String a) {
        this.address = a;
    }

    public String getLocationString() {
        return latlon;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String s = "";
        String output = "";

        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + address.replaceAll(" ", "%20")
                + "&key=AIzaSyCn3t7vicEyXQkuPHpwzA3RoxdN0-D1tAk";

        try {
            URL urlen = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlen.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new
                    InputStreamReader((conn.getInputStream())));
            while ((s = br.readLine()) != null) {
                output = output + s;

            }


            jsonObject = new JSONObject(output);

            if (jsonObject.get("status").equals("OK")) {
                conn.disconnect();
                Double lon;
                Double lat;
                lon =
                        ((JSONArray)
                                jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                lat = ((JSONArray)
                        jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                lokasjon = lat + " : " + lon;
                return lokasjon;
            } else {
                lokasjon = "Addresse Not Found!!";
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return lokasjon;
    }

    @Override
    protected void onPostExecute(String resultat) {
        latlon = resultat;
    }
}