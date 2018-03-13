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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnPhotoAvailable {

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

        this.setTitle("NASA Rover Photos");

        getPictures();

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
}
