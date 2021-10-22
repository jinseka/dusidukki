package com.and.dusi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class CreatBBSOpenHelper extends SQLiteOpenHelper {

    Context context;

    public CreatBBSOpenHelper(Context context) {
        super(context, "dusi", null, 1);
        this.context = context;
    }
//email .pwd 글자수 제한 공백불가 ,PK 설정
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "create table bbs (" +
                    "title varchar(100) not null," +
                    "content varchar(100) not null" +
                    ");";
            db.execSQL(sql);
            Toast.makeText(context, " 테이블 생성", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

