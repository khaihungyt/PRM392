package com.example.myapplication.provider;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;

public class UserProviderContract {
    public static final String AUTHORITY="com.example.myapplication.provider";
    public static final String TABLE_NAME= "users";
    public static class TableColumns {
        private static final String ID_FIELD = "id";
        private static final String USERNAME_FIELD = "username";
        private static final String PASSWORD_FIELD = "password";
        private static final String FIRSTNAME_FIELD = "first_name";
        private static final String LASTNAME_FIELD = "last_name";
        private static final String EMAIL_FIELD = "email";
        private static final String PHONE_FIELD = "phone";
        private static final String ADDRESS_FIELD = "address";
        private static final String ROLE_FIELD = "role";
        private static final String CAMPUS_FIELD = "campus";
    }
    public static final String CONTENT_PATH=TABLE_NAME;
    public static final Uri uri = Uri.parse("content://"+AUTHORITY+"/"+CONTENT_PATH);
    // MIME types
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + CONTENT_PATH;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + TABLE_NAME;
    public final static int ONE_ITEM =1;
    public final static int MULTIPLE_ITEMS=2;
   public static UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static {

        uriMatcher.addURI(AUTHORITY,TABLE_NAME,MULTIPLE_ITEMS);
        uriMatcher.addURI(AUTHORITY,TABLE_NAME+"/#",ONE_ITEM);
    }
}
