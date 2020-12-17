package com.example.googlemaps.ServerRepository;

import android.os.AsyncTask;

import com.example.googlemaps.Model.Hus;
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

public class HusRepository {


    public void leggTilHus(String url) {
        leggTilHus task = new leggTilHus();
        try {
            task.execute(new String[]{url}).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Hus> hentAlleHus() {
        getAlleHus task = new getAlleHus();
        try {
            return task.execute(new String[]{"http://student.cs.hioa.no/~s331361/Apputvk3/hentAlleHus.php" }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean slettHus(String url) {
        slettHus task = new slettHus();
        try {
            return task.execute(new String[]{url}).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    class leggTilHus extends AsyncTask<String, Void, Void> {
        JSONObject jsonObject;

        @Override
        protected Void doInBackground(String... urls) {
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
                    if (conn.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + conn.getResponseCode());
                    }
                } catch (Exception e) {
                    System.out.println("hus er not saved");
                    return null;
                }
            }
            return null;
        }
    }


    class getAlleHus extends AsyncTask<String, Void, ArrayList<Hus>> {
        JSONObject jsonObject;

        @Override
        protected ArrayList<Hus> doInBackground(String... urls) {
            ArrayList<Hus> hus = new ArrayList<>();
            try {
                JSONArray husJson = JsonParser.makeJsonArrayFromServer(urls[0]);

                if (husJson != null) {
                    for (int i = 0; i < husJson.length(); i++) {

                        JSONObject jsonobject = husJson.getJSONObject(i);

                        String name = jsonobject.getString("gateadresse");

                        Hus etHus = new Hus(Integer.parseInt(jsonobject.getString("id")), jsonobject.getString("beskrivelse"),
                                jsonobject.getString("gateadresse"), jsonobject.getString("gps"), Integer.parseInt(jsonobject.getString("etasjer")));

                        hus.add(etHus);
                    }

                }
                return hus;

            } catch (
                    JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

    }


    class slettHus extends AsyncTask<String, Void, Boolean> {
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
