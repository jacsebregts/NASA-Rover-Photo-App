package android.programmeren.jacsebregts.nasaroverphotoapp.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.programmeren.jacsebregts.nasaroverphotoapp.R;
import android.programmeren.jacsebregts.nasaroverphotoapp.api.interfaces.OnPhotoAvailable;
import android.programmeren.jacsebregts.nasaroverphotoapp.domain.Photo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.util.Objects;

public class PhotoSearchTask extends AsyncTask<String, Void, String> {

    private static final String TAG = "PhotoSearchTask";

    private Context context;
    private OnPhotoAvailable listener = null;
    private ProgressDialog progressDialog;

    public PhotoSearchTask(OnPhotoAvailable listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Log.i(TAG, "onPreExecute was called.");

        progressDialog = ProgressDialog.show(context, "Searching Data", "Searching data from the server.",  true);
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground was called.");

        InputStream inputStream = null;
        int responseCode = -1;
        String movieUrl = strings[0];
        String response = "";

        try {

            URL url = new URL(movieUrl);
            URLConnection connection = url.openConnection();

            if (!(connection instanceof HttpURLConnection)) {
                return null;
            }

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Log.i(TAG, "onPostExecute " + response);

        if (response == null || Objects.equals(response, "")) {
            return;
        }

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(response);
            JSONArray photosArray = jsonObject.getJSONArray("photos");

            for (int i = 0; i < photosArray.length(); i++) {
                JSONObject photoObject = photosArray.getJSONObject(i);

                int photoID = photoObject.getInt("id");
                int photoSol = photoObject.getInt("sol");
                JSONObject photoCamera = photoObject.getJSONObject("camera");
                String photoCameraName = photoCamera.getString("full_name");
                String photoURL = photoObject.getString("img_src");
                Date earthDate = Date.valueOf(photoObject.getString("earth_date"));

                Photo photo = new Photo(photoID, photoSol, photoCameraName, photoURL, earthDate);

                listener.onPhotoAvailable(photo);
            }

        } catch (JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());

        }
        progressDialog.dismiss();
    }

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
