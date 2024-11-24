package com.example.duanmaufa24.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaufa24.Models.ThanhVien;
import com.example.duanmaufa24.database.DbHelper;

import java.util.ArrayList;
import java.util.List;



public class ThanhVienDao {
    private SQLiteDatabase db;

    public ThanhVienDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("HoTen", thanhVien.getHoTen());
        values.put("NamSinh", thanhVien.getNamSinh());
        return db.insert("thanhvien", null, values);
    }

    public int update(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put("HoTen", thanhVien.getHoTen());
        values.put("NamSinh", thanhVien.getNamSinh());
        return db.update("thanhvien", values, "MaTV=?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }
    public List<String> getAllMaTV() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT MaTV FROM thanhvien", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public int delete(String maTV) {
        return db.delete("thanhvien", "MaTV=?", new String[]{maTV});
    }

    @SuppressLint("Range")
    public List<ThanhVien> getAll() {
        List<ThanhVien> list = new ArrayList<>();
        Cursor cursor = db.query("thanhvien", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setMaTV(cursor.getInt(cursor.getColumnIndex("MaTV")));
            thanhVien.setHoTen(cursor.getString(cursor.getColumnIndex("HoTen")));
            thanhVien.setNamSinh(cursor.getInt(cursor.getColumnIndex("NamSinh")));
            list.add(thanhVien);
        }
        cursor.close();
        return list;
    }
}
