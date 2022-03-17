package com.example.viaje;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Conexion_Viajes extends SQLiteOpenHelper {

    public Conexion_Viajes(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table TblRegistro(codigo text primary key," +
                "ciudad text not null ,personas text not null ," +
                "costo text not null,activo text not null default 'si')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table TblRegistro");{
            onCreate(sqLiteDatabase);
        }

    }
}
