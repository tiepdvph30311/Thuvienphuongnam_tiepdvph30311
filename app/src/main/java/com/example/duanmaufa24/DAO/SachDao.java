package com.example.duanmaufa24.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaufa24.Models.Sach;
import com.example.duanmaufa24.database.DbHelper;

import java.util.ArrayList;
import java.util.List;


public class SachDao {
    private SQLiteDatabase db;

    public SachDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
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




    public long insert(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("MaSach", sach.getMaSach());
        values.put("MaLoai", sach.getMaLoai());
        values.put("TenSach", sach.getTenSach());
        values.put("GiaThue", sach.getGiaThue());
        return db.insert("sach", null, values);
    }
    public List<String> getAllMaSach() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT MaSach FROM sach", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public int update(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("MaLoai", sach.getMaLoai());
        values.put("TenSach", sach.getTenSach());
        values.put("GiaThue", sach.getGiaThue());
        return db.update("sach", values, "MaSach=?", new String[]{sach.getMaSach()});
    }

    public int delete(String maSach) {
        return db.delete("sach", "MaSach=?", new String[]{maSach});
    }

    public List<Sach> getAll() {
        List<Sach> list = new ArrayList<>();
        Cursor cursor = db.query("sach", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Sach sach = new Sach();
            sach.setMaSach(cursor.getString(cursor.getColumnIndex("MaSach")));
            sach.setMaLoai(cursor.getString(cursor.getColumnIndex("MaLoai")));
            sach.setTenSach(cursor.getString(cursor.getColumnIndex("TenSach")));
            sach.setGiaThue(cursor.getInt(cursor.getColumnIndex("GiaThue")));
            list.add(sach);
        }
        cursor.close();
        return list;
    }
}
