package com.example.duanmaufa24.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaufa24.Models.ThuThu;
import com.example.duanmaufa24.database.DbHelper;

import java.util.ArrayList;
import java.util.List;



public class ThuThuDao {
    private SQLiteDatabase db;
    DbHelper dbHelper;
    public ThuThuDao(Context context) {
         dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("MaTT", thuThu.getMaTT());
        values.put("HoTen", thuThu.getHoTen());
        values.put("MatKhau", thuThu.getMatKhau());
        return db.insert("thuthu", null, values);
    }
    public List<String> getAllMaTT() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT MaTT FROM thuthu", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public int update(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("HoTen", thuThu.getHoTen());
        values.put("MatKhau", thuThu.getMatKhau());
        return db.update("thuthu", values, "MaTT=?", new String[]{thuThu.getMaTT()});
    }

    public int delete(String maTT) {
        return db.delete("thuthu", "MaTT=?", new String[]{maTT});
    }
    public Boolean KiemTraDangNhap(String username,String password){
        Cursor cursor = db.rawQuery("SELECT * FROM thuthu WHERE MaTT = ? AND MatKhau = ?",new String[]{username,password});
        return cursor.getCount()>0;

    }

    @SuppressLint("Range")
    public List<ThuThu> getAll() {
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = db.query("thuthu", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ThuThu thuThu = new ThuThu();
            thuThu.setMaTT(cursor.getString(cursor.getColumnIndex("MaTT")));
            thuThu.setHoTen(cursor.getString(cursor.getColumnIndex("HoTen")));
            thuThu.setMatKhau(cursor.getString(cursor.getColumnIndex("MatKhau")));
            list.add(thuThu);
        }
        cursor.close();
        return list;
    }

    public int setMatKhau(String maTT, String newMatKhau) {
        ContentValues values = new ContentValues();
        values.put("MatKhau", newMatKhau);
        return db.update("thuthu", values, "MaTT=?", new String[]{maTT});
    }
}
