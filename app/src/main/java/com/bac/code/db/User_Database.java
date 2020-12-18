package com.bac.code.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.bac.code.model.User;

import static com.bac.code.helper.Constants.db_name;
import static com.bac.code.helper.Constants.tb_product;
import static com.bac.code.helper.Constants.tb_users;

public class User_Database  extends SQLiteOpenHelper {


    private static  final String column_1 = "id";
    private static  final String column_2 = "Username";
    private static  final String column_3 = "Email";
    private static  final String column_4 = "Password";

    private static  final String column_id = "id";
    private static  final String column_title = "title";
    private static  final String column_price = "price";
    private static  final String column_webpage = "web_page";
    private static  final String column_location = "location";
    private static  final String column_status = "status";







    public User_Database(@Nullable Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql2 = "CREATE TABLE " + tb_product + " ( " +
                column_id + " integer primary key AUTOINCREMENT," +
                column_title + " text,"+
                column_price + " real," +
                column_webpage + " text," +
                column_status + " text default 'active'," +
                column_location + " text );";

        db.execSQL(sql2);


        String sql = "CREATE TABLE " + tb_users + " ( " +
                column_1 + " integer primary key AUTOINCREMENT," +
                column_2 + " text,"+
                column_3 + " text," +
                column_4 + " text);";

        db.execSQL(sql);

        System.out.println("*************************************");
        System.out.println(sql);
        System.out.println("*************************************");

        System.out.println("*************************************");
        System.out.println(sql2);
        System.out.println("*************************************");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + tb_users);
        onCreate(db);

    }

    public  long sign_up(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("Email", user.getEmail());
        contentValues.put("Username", user.getUsername());
        contentValues.put("Password", user.getPassword());
        long value = db.insert(tb_users, null, contentValues);
        db.close();
        return value;
    }

    public    boolean sign_in( User user)
    {

        boolean exist = false;

        String[] columns = {column_1};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = column_3 + "=?" + " and " + column_4 + "=?";
        String[] selectionArgs = {user.getUsername(), user.getPassword()};
        Cursor cursor = db.query(tb_users,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count > 0)
        {
            exist = true;
        }



        return exist;

    }
}
