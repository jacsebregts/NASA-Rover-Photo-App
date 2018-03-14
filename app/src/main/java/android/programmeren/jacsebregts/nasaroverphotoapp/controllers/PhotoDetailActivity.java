package android.programmeren.jacsebregts.nasaroverphotoapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.programmeren.jacsebregts.nasaroverphotoapp.R;
import android.programmeren.jacsebregts.nasaroverphotoapp.domain.Photo;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {

    private static final String TAG = "PhotoDetailActivity";

    private Photo photo;
    private ImageView imageViewPhoto;
    private TextView textViewCameraName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate was called.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        setupActionBar();

        Intent extras = getIntent();
        photo = (Photo) extras.getSerializableExtra("PHOTO");

        Log.d(TAG, "extras was used.");

        imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        textViewCameraName = (TextView) findViewById(R.id.textViewCameraName);

        Picasso.with(this)
                .load(photo.getImageURL())
                .into(imageViewPhoto);

        Log.d(TAG, "Picasso was used.");

        textViewCameraName.setText(photo.getCameraName());
    }

    private void setupActionBar() {
        Log.d(TAG, "setupActionBar was called.");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Log.d(TAG, "setupActionBar was executed.");
    }
}
