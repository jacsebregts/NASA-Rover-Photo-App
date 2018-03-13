package android.programmeren.jacsebregts.nasaphotoapp.controllers;

import android.os.Bundle;
import android.programmeren.jacsebregts.nasaphotoapp.R;
import android.programmeren.jacsebregts.nasaphotoapp.domain.Photo;
import android.programmeren.jacsebregts.nasaphotoapp.util.Tags;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {

    private static final String TAG = "PhotoDetailActivity";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate was called.");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        setupActionBar();

        Bundle extras = getIntent().getExtras();

        Photo product = (Photo) extras.getSerializable(Tags.PHOTO_ID);

        ImageView imageViewPhoto = findViewById(R.id.imageViewPhoto);
        TextView textViewPhotoDetail = findViewById(R.id.textViewPhotoDetail);

        // ImageView uit scherm koppelen
        this.setTitle(product.getFullName());

        textViewPhotoDetail.setText(product.getFullName());

        Picasso.with(this)
                .load(product.getImageURL())
                .into(imageViewPhoto);
    }

    private void setupActionBar() {
        Log.d(TAG, "setupActionBar was called.");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
