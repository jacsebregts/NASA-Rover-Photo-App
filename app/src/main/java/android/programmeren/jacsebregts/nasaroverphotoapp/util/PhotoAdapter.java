package android.programmeren.jacsebregts.nasaroverphotoapp.util;

import android.content.Intent;
import android.programmeren.jacsebregts.nasaroverphotoapp.R;
import android.programmeren.jacsebregts.nasaroverphotoapp.controllers.PhotoDetailActivity;
import android.programmeren.jacsebregts.nasaroverphotoapp.domain.Photo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private static final String TAG = "PhotoAdapter";

    private ArrayList photos;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View view;
        private ImageView imageViewPhoto;
        private TextView textViewImageID;

        public ViewHolder(View v) {
            super(v);
            this.view = v;
            this.view.setOnClickListener(this);

            imageViewPhoto = (ImageView) v.findViewById(R.id.imageViewPhoto);
            textViewImageID = (TextView) v.findViewById(R.id.textViewImageID);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick was called.");

            int position = getAdapterPosition();
            Photo p = (Photo) photos.get(position);

            Intent photoDetailIntent = new Intent(view.getContext().getApplicationContext(), PhotoDetailActivity.class);
            photoDetailIntent.putExtra("PHOTO", p);

            view.getContext().startActivity(photoDetailIntent);

            Log.d(TAG, "onClick was executed and finalised.");
        }
    }

    public PhotoAdapter(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder was called.");

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.view_row_item, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder was called.");

        Photo photo = (Photo) this.photos.get(position);

        Picasso.with(holder.view.getContext())
                .load(photo.getImageURL())
                .into(holder.imageViewPhoto);

        holder.textViewImageID.setText("Image ID: " + String.valueOf(photo.getId()));
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount was called.");

        return photos.size();
    }
}
