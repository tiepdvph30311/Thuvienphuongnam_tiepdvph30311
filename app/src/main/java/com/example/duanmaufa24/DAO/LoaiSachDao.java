package com.example.duanmaufa24.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaufa24.Models.LoaiSach;
import com.example.duanmaufa24.database.DbHelper;

import java.util.ArrayList;
import java.util.List;



public class LoaiSachDao {
    private SQLiteDatabase db;

    public LoaiSachDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("TenLoai", loaiSach.getTenSach());
        return db.insert("loaisach", null, values);
    }

    public int update(LoaiSach loaiSach) {
        ContentValues values = new ContentValues();
        values.put("TenLoai", loaiSach.getTenSach());
        return db.update("loaisach", values, "MaLoai=?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }


    public int delete(String maLoai) {
        return db.delete("loaisach", "MaLoai=?", new String[]{maLoai});
    }
    public List<String> getAllMaLoai() {
        List<String> maLoaiList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT MaLoai FROM loaisach", null);
        if (cursor.moveToFirst()) {
            do {
                maLoaiList.add(cursor.getString(0)); // lấy mã loại
            } while (cursor.moveToNext());
        }
        cursor.close();
        return maLoaiList;
    }

    @SuppressLint("Range")
    public List<LoaiSach> getAll() {
        List<LoaiSach> list = new ArrayList<>();
        Cursor cursor = db.query("loaisach", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setMaLoai(cursor.getString(cursor.getColumnIndex("MaLoai")));
            loaiSach.setTenSach(cursor.getString(cursor.getColumnIndex("TenLoai"))); // Thay "TenSach" bằng "TenLoai"
            list.add(loaiSach);
        }
        cursor.close();
        return list;
    }



}
