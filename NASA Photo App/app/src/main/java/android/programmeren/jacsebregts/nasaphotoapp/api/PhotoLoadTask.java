package android.programmeren.jacsebregts.nasaphotoapp.api;

import android.os.AsyncTask;
import android.programmeren.jacsebregts.nasaphotoapp.domain.Photo;
import android.programmeren.jacsebregts.nasaphotoapp.api.interfaces.OnPhotoAvailable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class PhotoLoadTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "PhotoLoadTask";

    // Callback
    private OnPhotoAvailable listener = null;

    // Constructor, set listener
    public PhotoLoadTask(OnPhotoAvailable listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        int responseCode = -1;
        // De URL die we via de .execute() meegeleverd krijgen
        String photoUrl = params[0];
        // Het resultaat dat we gaan retourneren
        String response = "";

        Log.i(TAG, "doInBackground - " + photoUrl);
        try {
            // Maak een URL object
            URL url = new URL(photoUrl);
            // Open een connection op de URL
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                return null;
            }

            Log.i(TAG, "now initialising HTTP connection.");

            // Initialiseer een HTTP connectie
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");

            Log.i(TAG, "now initialising the request on the URL.");

            // Voer het request uit via de HTTP connectie op de URL
            httpConnection.connect();

            Log.i(TAG, "now checking the response code.");

            // Kijk of het gelukt is door de response code te checken
            responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
                Log.i(TAG, "doInBackground response = " + response);
            } else {
                Log.e(TAG, "Error, invalid response");
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }

        // Hier eindigt deze methode.
        // Het resultaat gaat naar de onPostExecute methode.
        return response;
    }

    /**
     * onPostExecute verwerkt het resultaat uit de doInBackground methode.
     *
     * @param response
     */
    protected void onPostExecute(String response) {

        Log.i(TAG, "onPostExecute " + response);

        // Check of er een response is
        if (response == null || response == "") {
            Log.e(TAG, "onPostExecute kreeg een lege response!");
            return;
        }

        // Het resultaat is in ons geval een stuk tekst in JSON formaat.
        // Daar moeten we de info die we willen tonen uit filteren (parsen).
        // Dat kan met een JSONObject.
        JSONObject jsonObject;
        try {
            // Top level json object
            jsonObject = new JSONObject(response);

            // Get all photos and start looping
            JSONArray photosArray = jsonObject.getJSONArray("photos");
            for (int idx = 0; idx < photosArray.length(); idx++) {
                // array level objects and get user
                JSONObject photos = photosArray.getJSONObject(idx);

                // Get photo details
                int id = photos.getInt("id");
                String name = photos.getJSONObject("camera").getString("name");
                String fullName = photos.getJSONObject("camera").getString("full_name");
                String earthDate = photos.getString("earth_date");

                Log.i(TAG, "Got photo " + fullName);

                // Get image url
                String imageURL = photos.getString("img_src");
                Log.i(TAG, "URL " + imageURL + " was called.");

                // Create new Photo object
                Photo photo = new Photo(id, name, fullName, imageURL, earthDate);

                Log.i(TAG, " photo was created.");
                //
                // call back with new person data
                //
                listener.onPhotoAvailable(photo);

            }
        } catch (JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }


    //
    // convert InputStream to String
    //
    private static String getStringFromInputStream(InputStream is) {
        Log.d(TAG, "getStringFromInputStream was called.");

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
