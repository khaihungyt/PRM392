package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.bean.UserBean;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USER_TABLE_NAME = "Users";

    private static final String ID_FIELD="id";
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String FIRSTNAME_FIELD = "first_name";
    private static final String LASTNAME_FIELD = "last_name";
    private static final String EMAIL_FIELD = "email";
    private static final String PHONE_FIELD = "phone";
    private static final String ADDRESS_FIELD = "address";
    private static final String ROLE_FIELD = "role";
    private static final String CAMPUS_FIELD = "campus";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USERNAME_FIELD + " TEXT, " +
                    PASSWORD_FIELD + " TEXT, " +
                    FIRSTNAME_FIELD + " TEXT, " +
                    LASTNAME_FIELD + " TEXT, " +
                    EMAIL_FIELD + " TEXT, " +
                    PHONE_FIELD + " TEXT, " +
                    ADDRESS_FIELD + " TEXT, " +
                    ROLE_FIELD + " TEXT, " +
                    CAMPUS_FIELD + " TEXT" +
                    ");";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }
    public void insertUser(UserBean user){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(USERNAME_FIELD, user.getUsername());
        values.put(PASSWORD_FIELD, user.getPassword());
        values.put(FIRSTNAME_FIELD, user.getFirstName());
        values.put(LASTNAME_FIELD, user.getLastName());
        values.put(EMAIL_FIELD, user.getEmail());
        values.put(PHONE_FIELD, user.getPhone());
        values.put(ADDRESS_FIELD, user.getAddress());
        values.put(ROLE_FIELD, user.getRole());
        values.put(CAMPUS_FIELD, user.getCampus());
        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }
    public UserBean getUserByUserName(String username){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE_NAME + " WHERE " + USERNAME_FIELD + "=?";
        String[] args ={username};
        Cursor cursor = db.rawQuery(query, args);
        UserBean user=null;
        if (cursor.moveToFirst()) {
            user = new UserBean();
            int idIndex = cursor.getColumnIndexOrThrow(ID_FIELD);
            user.setId(cursor.getInt(idIndex));
            int usernameIndex = cursor.getColumnIndexOrThrow(USERNAME_FIELD);
            user.setUsername(cursor.getString(usernameIndex));
            int passwordIndex = cursor.getColumnIndexOrThrow(PASSWORD_FIELD);
            user.setPassword(cursor.getString(passwordIndex));
            int firstNameIndex = cursor.getColumnIndexOrThrow(FIRSTNAME_FIELD);
            user.setFirstName(cursor.getString(firstNameIndex));
            int lastNameIndex = cursor.getColumnIndexOrThrow(LASTNAME_FIELD);
            user.setLastName(cursor.getString(lastNameIndex));
            int emailIndex = cursor.getColumnIndexOrThrow(EMAIL_FIELD);
            user.setEmail(cursor.getString(emailIndex));
            int phoneIndex = cursor.getColumnIndexOrThrow(PHONE_FIELD);
            user.setPhone(cursor.getString(phoneIndex));
            int addressIndex = cursor.getColumnIndexOrThrow(ADDRESS_FIELD);
            user.setAddress(cursor.getString(addressIndex));
            int roleIndex = cursor.getColumnIndexOrThrow(ROLE_FIELD);
            user.setRole(cursor.getString(roleIndex));
            int campusIndex = cursor.getColumnIndexOrThrow(CAMPUS_FIELD);
            user.setCampus(cursor.getString(campusIndex));
        }
        return user;
    }
}
