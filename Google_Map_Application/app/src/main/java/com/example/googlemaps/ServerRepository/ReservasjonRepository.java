package com.example.googlemaps.ServerRepository;

import android.os.AsyncTask;

import com.example.googlemaps.Model.Reservasjon;
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

public class ReservasjonRepository {


    public boolean leggTilReservasjoner(String url) {
        leggTilReservasjon task = new leggTilReservasjon();
        try {
            return task.execute(new String[]{url}).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Reservasjon> hentReservasjoner() {
        getReservasjoner task = new getReservasjoner();
        try {
            return task.execute(new String[]{"http://student.cs.hioa.no/~s331361/Apputvk3/hentReservasjoner.php" }).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean slettReservasjon(String url) {
        slettReservasjon task = new slettReservasjon();
        try {
            return task.execute(new String[]{url}).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    class getReservasjoner extends AsyncTask<String, Void, ArrayList<Reservasjon>> {
        @Override
        protected ArrayList<Reservasjon> doInBackground(String... urls) {
            ArrayList<Reservasjon> romArray = new ArrayList<>();
            try {
                JSONArray husJson = JsonParser.makeJsonArrayFromServer(urls[0]);

                if (husJson != null) {
                    for (int i = 0; i < husJson.length(); i++) {

                        JSONObject jsonobject = husJson.getJSONObject(i);
                        Reservasjon res = new Reservasjon(Integer.parseInt(jsonobject.getString("id")), jsonobject.getString("dato"),
                                jsonobject.getString("tidfra"), jsonobject.getString("tidtil"),
                                Integer.parseInt(jsonobject.getString("romid")), jsonobject.getString("studentnavn"));
                        romArray.add(res);
                    }

                    return romArray;
                }
            } catch (
                    JSONException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
    }


    class leggTilReservasjon extends AsyncTask<String, Void, Boolean> {
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

    class slettReservasjon extends AsyncTask<String, Void, Boolean> {
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