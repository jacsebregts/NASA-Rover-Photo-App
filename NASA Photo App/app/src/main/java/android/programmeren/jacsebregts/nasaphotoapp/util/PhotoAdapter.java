package android.programmeren.jacsebregts.nasaphotoapp.util;

import android.content.Context;
import android.content.Intent;
import android.programmeren.jacsebregts.nasaphotoapp.domain.Photo;
import android.programmeren.jacsebregts.nasaphotoapp.controllers.PhotoDetailActivity;
import android.programmeren.jacsebregts.nasaphotoapp.R;
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

    private ArrayList<Photo> dataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // The view (list_row_item) that contains the view items
        public View view;

        // The view items that our view displays
        public TextView textViewPhotoDetail;
        public TextView textViewEarthDate;
        public ImageView imageViewPhoto;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.view.setOnClickListener(this);

            textViewPhotoDetail = (TextView) view.findViewById(R.id.textViewPhotoDetail);
            textViewEarthDate = (TextView) view.findViewById(R.id.textViewEarthDate);
            imageViewPhoto = (ImageView) view.findViewById(R.id.imageViewPhoto);
        }

        @Override
        public void onClick(View view) {
            Log.i(TAG, "onClick was called.");

            int position = getAdapterPosition();
            Photo photos = dataset.get(position);

            Intent photoDetailIntent = new Intent(view.getContext().getApplicationContext(), PhotoDetailActivity.class);

            photoDetailIntent.putExtra(Tags.PHOTO_ID, photos);

            view.getContext().startActivity(photoDetailIntent);
        }
    }

    // Provide a suitable constructor (depending on the kind of dataset)
    public PhotoAdapter(Context context, ArrayList<Photo> myDataset) {
        this.context = context;
        dataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder was called.");

        // Create a new view.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_row_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder was called.");

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Photo photo = dataset.get(position);
        holder.textViewPhotoDetail.setText(photo.getFullName());
        holder.textViewEarthDate.setText(photo.getEarthDate());

        Picasso.with(context)
                .load(photo.getImageURL())
                .into(holder.imageViewPhoto);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount was called.");

        return dataset.size();
    }
}

