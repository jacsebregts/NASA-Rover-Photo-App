package android.programmeren.jacsebregts.nasaroverphotoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PictureDatabase extends SQLiteOpenHelper {

    private static final String TAG = "PictureDatabase";
    private static final String DB_NAME = "Person.db";
    private static final int DB_VERSION = 1;

    public PictureDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
