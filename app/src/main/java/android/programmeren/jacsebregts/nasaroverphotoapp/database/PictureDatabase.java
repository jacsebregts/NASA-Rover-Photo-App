package android.programmeren.jacsebregts.nasaroverphotoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.programmeren.jacsebregts.nasaroverphotoapp.domain.Photo;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class PictureDatabase extends SQLiteOpenHelper {

    private static final String TAG = "PictureDatabase";
    private static final String DB_NAME = "Person.db";
    private static final int DB_VERSION = 1;

    private final static String COLUMN_ID = "id";
    private final static String COLUMN_SOL = "sol";
    private final static String COLUMN_CAMERA = "cameraName";
    private final static String COLUMN_IMAGEURL = "imageUrl";
    private final static String COLUMN_EARTHDATE = "earthDate";

    private final static String TABLE_NAME = "Picture";

    public PictureDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate was called.");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_SOL + " INTEGER," +
                COLUMN_CAMERA + " TEXT NOT NULL," +
                COLUMN_IMAGEURL + " TEXT NOT NULL UNIQUE," +
                COLUMN_EARTHDATE + " TEXT NOT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade was called.");

        String query = "DROP TABLE " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }

//    public boolean hasContent (){
//        Log.d(TAG, "hasContent was called");
//
//        String query = "SELECT COUNT(*) FROM " + TABLE_NAME + ";";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query,null);
//
//        cursor.moveToFirst();
//        int id = 0;
//
//        Log.d(TAG, "About to start looping the items in the cursor");
//        while(!cursor.isAfterLast()) {
//            id = cursor.getInt(0);
//            cursor.moveToNext();
//        }
//
//        if (id > 500){
//            return true;
//        } else {
//            return false;
//        }
//    }

    public void addPicture(Photo photo) {
        Log.d(TAG, "addPicture was called.");

        //Attributen van person gaan we in de tabel zetten
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, photo.getId());
        values.put(COLUMN_SOL, photo.getSol());
        values.put(COLUMN_CAMERA, photo.getCameraName());
        values.put(COLUMN_IMAGEURL, photo.getImageURL());
        values.put(COLUMN_EARTHDATE, photo.getEarthDate().toString());

        SQLiteDatabase db = this.getWritableDatabase();
        Long id = db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Photo> getCameraName(Photo photo) {
        Log.d(TAG, "getCameraName was called.");

        String query = "SELECT " + COLUMN_CAMERA + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Photo> photosByCamera = new ArrayList<>();

        cursor.moveToFirst();
        Log.d(TAG, "About to start looping the items in the cursor");
        while (!cursor.isAfterLast()){
            String cameraName = cursor.getString(cursor.getColumnIndex(COLUMN_CAMERA));

            Photo camera = new Photo(photo.getId(), photo.getSol(), cameraName, photo.getImageURL(), photo.getEarthDate());
            photosByCamera.add(camera);
        }

        return photosByCamera;
    }

    public ArrayList<Photo> getAllPictures() {
        Log.d(TAG, "getAllPersons aangeroepen.");

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Photo> photos = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            int sol = cursor.getInt(cursor.getColumnIndex(COLUMN_SOL));
            String cameraName = cursor.getString(cursor.getColumnIndex(COLUMN_CAMERA));
            String imageURL = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGEURL));
            Date earthDate = java.sql.Date.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_EARTHDATE)));

            Photo newPicture = new Photo(id, sol, cameraName, imageURL, (java.sql.Date.valueOf(String.valueOf(earthDate))));
            photos.add(newPicture);

            cursor.moveToNext();
        }
        db.close();
        cursor.close();

        return photos;
    }
}
