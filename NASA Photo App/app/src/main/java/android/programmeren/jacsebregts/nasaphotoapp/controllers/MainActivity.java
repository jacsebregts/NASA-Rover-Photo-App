package android.programmeren.jacsebregts.nasaphotoapp.controllers;

import android.programmeren.jacsebregts.nasaphotoapp.R;
import android.programmeren.jacsebregts.nasaphotoapp.api.PhotoLoadTask;
import android.programmeren.jacsebregts.nasaphotoapp.api.interfaces.OnPhotoAvailable;
import android.programmeren.jacsebregts.nasaphotoapp.domain.Photo;
import android.programmeren.jacsebregts.nasaphotoapp.util.PhotoAdapter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnPhotoAvailable {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Photo> photoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate was called.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("NASA Photo App");
        this.setTitleColor(30);

        // Executing the api
        String[] params = { "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&camera=mast&api_key=NO9JaYBrPOhgwVsv08W4EE9CHkTn3ZyIxY9M96sp" };
        PhotoLoadTask photoTask = new PhotoLoadTask(this);
        photoTask.execute(params);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new PhotoAdapter(this, photoList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onPhotoAvailable(Photo photo) {
        Log.d(TAG, "onPhotoAvailable: " + photo.getID());

        photoList.add(photo);
        adapter.notifyDataSetChanged();
    }
}
