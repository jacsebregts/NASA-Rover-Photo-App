package android.programmeren.jacsebregts.nasaroverphotoapp.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.programmeren.jacsebregts.nasaroverphotoapp.R;
import android.programmeren.jacsebregts.nasaroverphotoapp.domain.Photo;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {

    private Photo photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        setupActionBar();

        Intent extras = getIntent();
        Photo photo = (Photo) extras.getSerializableExtra("PHOTO");

        ImageView imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
        TextView textViewImageID = (TextView) findViewById(R.id.textViewImageID);

        Picasso.with(this)
                .load(photo.getImageURL())
                .into(imageViewPhoto);

        textViewImageID.setText(photo.getCameraName());
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
