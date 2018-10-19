package com.google.quiz;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Random;

public class Cricket extends SQLiteOpenHelper {
    private static final String Database_path = "/data/data/com.google.quiz/databases/";
    private static final String Database_name = "ckt.db";
    private static Context context;
    private static final int version = 1;
    public SQLiteDatabase sqlite;

    public Cricket(Context context) {
        super(context, Database_name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void createDatabase() {
        createDB();
    }

    private void createDB() {
        boolean dbexist = DBexists();
        if (!dbexist) {
            this.getReadableDatabase();
            copyDBfromResource();
        }
    }

    private void copyDBfromResource() {
        InputStream is;
        OutputStream os;
        String filePath = Database_path + Database_name;
        try {
            is = context.getAssets().open(Database_name);//reading purpose
            os = new FileOutputStream(filePath);//writing purpose
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);//writing to file
            }
            os.flush();//flush the outputstream
            is.close();//close the inputstream
            os.close();//close the outputstream

        } catch (IOException e) {
            throw new Error("Problem copying database file:");
        }
    }

    public void openDatabase() throws SQLException {
        String myPath = Database_path + Database_name;
        sqlite = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean DBexists() {
        SQLiteDatabase db = null;
        try {
            String databasePath = Database_path + Database_name;
            db = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
            db.setLockingEnabled(true);
        } catch (SQLException e) {
            Log.e("Sqlite", "Database not found");
        }
        if (db != null)
            db.close();///close the opened file
        return db != null ? true : false;
    }

    public long getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long x = DatabaseUtils.queryNumEntries(db, "ckt");
        db.close();
        return x;
    }

    public int getSerialNumber(int n) {
        Random rand = new Random();
        return rand.nextInt(n);
    }

    public int getOption(int correctAnswerLocation, int numberRows) {
        Random rand = new Random();
        int x = rand.nextInt(numberRows);

        while (x == correctAnswerLocation) {
            x = rand.nextInt(numberRows);
        }
        return x;
    }

    public int getOption(int correctAnswerLocation, int optionA, int numberRows) {
        Random rand = new Random();
        int x = rand.nextInt(numberRows);

        while (x == correctAnswerLocation || x == optionA) {
            x = rand.nextInt(numberRows);
        }
        return x;
    }

    public String getQuestion(String s, int n) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("Select * from ckt", null);
        c.moveToFirst();

        int questionIndex = c.getColumnIndex("question");
        int answerIndex = c.getColumnIndex("answer");

        c.moveToPosition(n);

        if (s.equals("Question")) {
            return c.getString(questionIndex);
        } else if (s.equals("Answer")) {
            return c.getString(answerIndex);
        } else
            return "-1";
    }

    public String getChoice(int x, int n) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("Select * from ckt", null);
        c.moveToPosition(x);

        int choiceIndex = c.getColumnIndex("answer");

        return c.getString(choiceIndex);
    }
}
