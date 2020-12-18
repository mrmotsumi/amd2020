package com.bac.code.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.bac.code.model.Product;

import static com.bac.code.helper.Constants.db_name;
import static com.bac.code.helper.Constants.tb_product;


public class Product_Database  extends SQLiteOpenHelper {


    private static  final String column_id = "id";
    private static  final String column_title = "title";
    private static  final String column_price = "price";
    private static  final String column_webpage = "web_page";
    private static  final String column_location = "location";
    private static  final String column_status = "status";



    public Product_Database(@Nullable Context context) {
        super(context, db_name, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql =  "CREATE TABLE " + tb_product + " ( " +
                column_id + " integer primary key AUTOINCREMENT," +
                column_title + " text,"+
                column_price + " real," +
                column_webpage + " text," +
                column_status + " text default 'active'," +
                column_location + " text );";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + tb_product);
        onCreate(db);
    }


    public    long save_Product(Product product)
    {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("title", product.getTitle());
        contentValues.put("price", product.getPrice());
        contentValues.put("web_page", product.getWebPage());
        contentValues.put("location", product.getLocation());

        long value = db.insert(tb_product, null, contentValues);
        db.close();
        return value;


    }

    public    long update_Product(Product product)
    {


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //query to insert record into  database.

        String query = "UPDATE " + tb_product + " SET "+ column_title + " =?," + column_price + " =?, " +
                column_webpage + " = ?, " + column_location + " = ? WHERE  " + column_id + "=? ";

        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(query);

        sqLiteStatement.clearBindings();

        sqLiteStatement.bindString(1,product.getTitle());
        sqLiteStatement.bindDouble(2,product.getPrice());
        sqLiteStatement.bindString(3,product.getLocation());
        sqLiteStatement.bindString(4,product.getLocation());
        sqLiteStatement.bindString(5, String.valueOf(product.getId()));

        long update =  sqLiteStatement.executeUpdateDelete();

        if(update > 0) {

            sqLiteStatement.close();
            sqLiteDatabase.close();

        }

        return  update;

    }

    public     void delete_Product(Product product){


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //query to insert record into  database.

        String query = "DELETE FROM " + tb_product + " WHERE " + column_id + " = ?";

        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(query);

        sqLiteStatement.clearBindings();
        sqLiteStatement.bindLong(1,product.getId());

        sqLiteStatement.execute();

        sqLiteStatement.close();
    }

    public Cursor queryData(String query){


        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return  sqLiteDatabase.rawQuery(query,null);

    }

    public Cursor find_all(String query){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return  sqLiteDatabase.rawQuery(query,null);

    }


    public int markSold(Product p) {


        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        //query to insert record into  database.

        String query = "Update  " + tb_product + " set status='SOLD' WHERE " +column_id + " = ?";

        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(query);

        sqLiteStatement.clearBindings();
        sqLiteStatement.bindLong(1,p.getId());

        int i =  sqLiteStatement.executeUpdateDelete();



        sqLiteStatement.close();

        return i;
    }
}
