package android.programmeren.jacsebregts.nasaroverphotoapp.controllers;

import android.programmeren.jacsebregts.nasaroverphotoapp.R;
import android.programmeren.jacsebregts.nasaroverphotoapp.api.PhotoSearchTask;
import android.programmeren.jacsebregts.nasaroverphotoapp.api.interfaces.OnPhotoAvailable;
import android.programmeren.jacsebregts.nasaroverphotoapp.domain.Photo;
import android.programmeren.jacsebregts.nasaroverphotoapp.util.PhotoAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnPhotoAvailable, AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";

    private PhotoAdapter photoAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private ArrayList<Photo> photoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate was called.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "content was loaded.");

        this.setTitle("NASA Mars Rover Photos");

        getPictures();
        fillTheSpinner();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        photoAdapter = new PhotoAdapter(photoList);

        recyclerView.setAdapter(photoAdapter);
    }

    @Override
    public void onPhotoAvailable(Photo photo) {
        Log.d(TAG, "onPhotoAvailable: " + photo.getImageURL());

        photoList.add(photo);
        photoAdapter.notifyDataSetChanged();
    }

    public void getPictures() {
        Log.d(TAG, "getPictures was called.");

        String[] urls = new String[]
                {"https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&api_key=NO9JaYBrPOhgwVsv08W4EE9CHkTn3ZyIxY9M96sp"};
        PhotoSearchTask findPhotos = new PhotoSearchTask(this, this);
        findPhotos.execute(urls);
    }

    public void getPicturesByCameraView(String cameraView) {
        Log.d(TAG, "getPicturesByCameraView was called.");

        if (!cameraView.equals("ALL CAMERAS")) {
            String[] urls = new String[]
                    {"https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&api_key=hkOe3Z4NdnkxYI8FlnnDMCc1o4Xuu8GRiClCnwFt&camera=" +
                            cameraView};
            System.out.println(urls[0]);
            PhotoSearchTask findPhotos = new PhotoSearchTask(this, this);
            findPhotos.execute(urls);
        }
        getPictures();
    }

    public void fillTheSpinner() {
        Log.d(TAG, "fillTheSpinner was called.");

        Spinner spinnerCameraViews = findViewById(R.id.spinnerCameraViews);
        spinnerCameraViews.setOnItemSelectedListener(this);

        ArrayList<String> cameraViews = new ArrayList<>();
        cameraViews.add("ALL CAMERAS");
        cameraViews.add("FHAZ");
        cameraViews.add("NAVCAM");
        cameraViews.add("MAST");
        cameraViews.add("CHEMCAM");
        cameraViews.add("MAHLI");
        cameraViews.add("MARDI");
        cameraViews.add("RHAZ");

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cameraViews);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCameraViews.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected was called.");

        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        getPicturesByCameraView(item);

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d(TAG, "onNothingSelected was called.");

    }
}
