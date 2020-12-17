package com.example.googlemaps.ServerRepository;

import android.os.AsyncTask;

import com.example.googlemaps.Model.Rom;
import com.example.googlemaps.util.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RomRepository {


    public boolean leggTilRom(String url) {
        leggTilRom task = new leggTilRom();
        try {
            return task.execute(new String[]{url}).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Rom> hentRom(String url) {
        getAlleRoom task = new getAlleRoom();
        try {
            return task.execute(new String[]{url}).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean slettRom(String url) {
        slettFromRom task = new slettFromRom();
        try {
            return task.execute(new String[]{url}).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    class leggTilRom extends AsyncTask<String, Void, Boolean> {
        JSONObject jsonObject;

        @Override
        protected Boolean doInBackground(String... urls) {
            String s = "";
            String output = "";
            for (String url : urls) {
                try {
                    URL urlen = new URL(urls[0]);
                    HttpURLConnection conn = (HttpURLConnection)
                            urlen.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept",
                            "application/json");
                    return conn.getResponseCode() == 200;
                } catch (Exception e) {
                    System.out.println("Rom er ikke lagret");
                    return false;
                }
            }
            return false;
        }


    }


    class getAlleRoom extends AsyncTask<String, Void, ArrayList<Rom>> {
        @Override
        protected ArrayList<Rom> doInBackground(String... urls) {
            ArrayList<Rom> romArray = new ArrayList<>();
            try {
                JSONArray husJson = JsonParser.makeJsonArrayFromServer(urls[0]);

                if (husJson != null) {
                    for (int i = 0; i < husJson.length(); i++) {

                        JSONObject jsonobject = husJson.getJSONObject(i);
                        Rom etRom = new Rom(Integer.parseInt(jsonobject.getString("id")), Integer.parseInt(jsonobject.getString("etasjenr")),
                                jsonobject.getString("romnummer"), jsonobject.getString("kapasitet"),
                                jsonobject.getString("beskrivelse"), Integer.parseInt(jsonobject.getString("husid")));

                        romArray.add(etRom);
                    }

                    return romArray;
                }
            } catch (
                    JSONException e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
    }


    class slettFromRom extends AsyncTask<String, Void, Boolean> {
        HttpURLConnection urlConnection;
        URL url;

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {
                    return true;
                } else if (statusCode == 404) {
                    return false;
                }
                return false;
            } catch (ProtocolException protocolException) {
                protocolException.printStackTrace();
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }

    }


}
