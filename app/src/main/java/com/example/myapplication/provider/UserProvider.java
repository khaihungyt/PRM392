package com.example.myapplication.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import database.DatabaseHandler;

public class UserProvider extends ContentProvider {
    private DatabaseHandler databaseHandler;
    @Override
    public boolean onCreate() {
        databaseHandler=new DatabaseHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (UserProviderContract.uriMatcher.match(uri)){
            case UserProviderContract.MULTIPLE_ITEMS:
                return databaseHandler.getReadableDatabase().query(UserProviderContract.TABLE_NAME,
                        projection,null,null, null,null,sortOrder);
            case UserProviderContract.ONE_ITEM:
                return databaseHandler.getReadableDatabase().query(UserProviderContract.TABLE_NAME,
                        projection,selection, selectionArgs, null,null,sortOrder);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (UserProviderContract.uriMatcher.match(uri)){
            case UserProviderContract.MULTIPLE_ITEMS:
                return UserProviderContract.CONTENT_TYPE;
            case UserProviderContract.ONE_ITEM:
                return UserProviderContract.CONTENT_ITEM_TYPE;
            default:
                return "";
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
