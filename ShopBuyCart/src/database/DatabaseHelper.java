package database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "SHOPBUYCART";

	// Table Names
	private static final String TABLE_FAV = "fav";
	private static final String TABLE_SUBCAT = "subcat";

	private static final String KEY_ID = "id";
	private static final String KEY_PRODUCTID = "productid";

	// Table Create Statements
	// Todo table create statement

	private static final String CREATE_TABLE_FAV = "CREATE TABLE " + TABLE_FAV
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PRODUCTID
			+ " TEXT )";

	private static final String CREATE_TABLE_SUBCAT = "CREATE TABLE "
			+ TABLE_SUBCAT
			+ "("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY, catid TEXT, subcatid TEXT ,category TEXT,subcategory TEXT,image TEXT )";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLE_FAV);
		db.execSQL(CREATE_TABLE_SUBCAT);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBCAT);
		// create new tables
		onCreate(db);
	}

	public void clearTable(String table) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from " + table);
		closeDB();
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/**
	 * get datetime
	 */
	@SuppressWarnings("unused")
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	public int CheckFAV(String idd) {
		SQLiteDatabase db = this.getReadableDatabase();
		String fd = "SELECT  * FROM " + TABLE_FAV + "  where  " + KEY_PRODUCTID
				+ " = " + "'" + idd + "'";
		Cursor cs = db.rawQuery(fd, null);
		int ss = cs.getCount();
		closeDB();
		return ss;
	}

	public void UNFAV(String idd) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete FROM " + TABLE_FAV + "  where  " + KEY_PRODUCTID
				+ " = " + "'" + idd + "'");
		closeDB();
	}

	public void INSERT_SUB(String catid, String subcatid, String category,
			String subcategory, String image) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("catid", catid);
		values.put("subcatid", subcatid);
		values.put("category", category);
		values.put("subcategory", subcategory);
		values.put("image", image);

		db.insert(TABLE_SUBCAT, null, values);
		closeDB();
	}

	public void INSERT_FAV(String productid) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_PRODUCTID, productid);
		db.insert(TABLE_FAV, null, values);
		closeDB();
	}

	
	
	
	public ArrayList<HashMap<String, String>> getallSubcategories(String catid) {
		ArrayList<HashMap<String, String>> subcat = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM " + TABLE_SUBCAT
				+ " WHERE catid ='" + catid + "'";
		HashMap<String, String> next;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				next = new HashMap<String, String>();
				//next.put("id", c.getString((c.getColumnIndex(KEY_ID))));
				next.put("id",
						c.getString((c.getColumnIndex("subcatid"))));
				next.put("subcategory",
						c.getString((c.getColumnIndex("subcategory"))));
				next.put("image", c.getString((c.getColumnIndex("image"))));

				subcat.add(next);
			} while (c.moveToNext());
		}
		closeDB();
		return subcat;

	}

	public ArrayList<String> getSubcategories(String catid) {
		ArrayList<String> subcat = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + TABLE_SUBCAT
				+ " WHERE catid ='" + catid + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				// next.put("id", c.getString((c.getColumnIndex(KEY_ID))));
				// next.put("subcatid",
				// c.getString((c.getColumnIndex("subcatid"))));
				// next.put("subcategory",
				// c.getString((c.getColumnIndex("subcategory"))));
				// next.put("image", c.getString((c.getColumnIndex("image"))));

				subcat.add(c.getString((c.getColumnIndex("subcategory"))));
			} while (c.moveToNext());
		}
		closeDB();
		return subcat;

	}

	public  ArrayList<HashMap<String, String>>  getallcategories() {
		 ArrayList<HashMap<String, String>>  search_text = new  ArrayList<HashMap<String, String>> ();
		String selectQuery = "SELECT * FROM " + TABLE_SUBCAT
				+ " GROUP BY  catid";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {

				HashMap<String, String> next = new HashMap<String, String>();
				next.put("id", c.getString((c.getColumnIndex(KEY_ID))));
				 next.put("catid", c.getString((c.getColumnIndex("catid"))));
				 next.put("category",
				 c.getString((c.getColumnIndex("category"))));
				

				search_text.add(next);
			} while (c.moveToNext());
		}
		closeDB();
		return search_text;

	}
	public ArrayList<String> getcategories() {
		ArrayList<String> search_text = new ArrayList<String>();
		String selectQuery = "SELECT * FROM " + TABLE_SUBCAT
				+ " GROUP BY  catid";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {

				// next.put("id", c.getString((c.getColumnIndex(KEY_ID))));
				// next.put("catid", c.getString((c.getColumnIndex("catid"))));
				// next.put("category",
				// c.getString((c.getColumnIndex("category"))));
				//

				search_text.add(c.getString((c.getColumnIndex("category"))));
			} while (c.moveToNext());
		}
		closeDB();
		return search_text;

	}

	public ArrayList<HashMap<String, String>> getFAV() {
		ArrayList<HashMap<String, String>> search_text = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM " + TABLE_FAV;
		HashMap<String, String> next;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				next = new HashMap<String, String>();

				next.put("id", c.getString((c.getColumnIndex(KEY_ID))));
				next.put("productid",
						c.getString((c.getColumnIndex(KEY_PRODUCTID))));

				search_text.add(next);
			} while (c.moveToNext());
		}
		closeDB();
		return search_text;

	}

	public int CHECKSUBCAT(String subcatid) {
		// TODO Auto-generated method stub
		int ss = 0;
		SQLiteDatabase db = this.getReadableDatabase();
		String fd = "SELECT  * FROM " + TABLE_SUBCAT + "  where  subcatid ='"
				+ subcatid + "'";
		Cursor cs = db.rawQuery(fd, null);
		ss = cs.getCount();
		closeDB();
		return ss;
	}

	public String getsubcatid(String strsubcatname) {
		String ss = "";
		String selectQuery = "SELECT  * FROM " + TABLE_SUBCAT
				+ " WHERE subcategory ='" + strsubcatname + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {

				ss = c.getString((c.getColumnIndex("subcatid")));
			} while (c.moveToNext());
		}
		closeDB();
		return ss;

	}

}
