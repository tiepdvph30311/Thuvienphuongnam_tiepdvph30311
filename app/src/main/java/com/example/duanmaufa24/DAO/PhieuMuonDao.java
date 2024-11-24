package com.example.duanmaufa24.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmaufa24.Models.PhieuMuon;
import com.example.duanmaufa24.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDao {
    private SQLiteDatabase db;

    public PhieuMuonDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Thêm phiếu mượn
    public long insert(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("MaTT", phieuMuon.getMaTT());
        values.put("MaTV", phieuMuon.getMaTV());
        values.put("MaSach", phieuMuon.getMaSach());
        values.put("NgayMuon", phieuMuon.getNgayMuon());
        values.put("TraSach", phieuMuon.getTraSach());
        values.put("TienThue", phieuMuon.getTienThue());
        values.put("TrangThaiMuon", phieuMuon.getTrangThaiMuon());

        try {
            long result = db.insert("phieumuon", null, values);
            Log.d("PhieuMuonDao", "Insert result: " + result);
            return result;
        } catch (Exception e) {
            Log.e("PhieuMuonDao", "Insert failed", e);
            return -1;
        }
    }

    // Cập nhật phiếu mượn
    public int update(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put("MaTT", phieuMuon.getMaTT());
        values.put("MaTV", phieuMuon.getMaTV());
        values.put("MaSach", phieuMuon.getMaSach());
        values.put("NgayMuon", phieuMuon.getNgayMuon());
        values.put("TraSach", phieuMuon.getTraSach());
        values.put("TienThue", phieuMuon.getTienThue());
        values.put("TrangThaiMuon", phieuMuon.getTrangThaiMuon());  // Cập nhật trạng thái mượn sách
        return db.update("phieumuon", values, "MaPM=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    // Xóa phiếu mượn
    public int delete(String maPM) {
        return db.delete("phieumuon", "MaPM=?", new String[]{maPM});
    }

    // Lấy tất cả phiếu mượn
    @SuppressLint("Range")
    public List<PhieuMuon> getAll() {
        List<PhieuMuon> list = new ArrayList<>();
        Cursor cursor = db.query("phieumuon", null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPM(cursor.getInt(cursor.getColumnIndex("MaPM")));
                phieuMuon.setMaTT(cursor.getString(cursor.getColumnIndex("MaTT")));
                phieuMuon.setMaTV(cursor.getInt(cursor.getColumnIndex("MaTV")));
                phieuMuon.setMaSach(cursor.getInt(cursor.getColumnIndex("MaSach")));
                phieuMuon.setNgayMuon(cursor.getString(cursor.getColumnIndex("NgayMuon")));
                phieuMuon.setTraSach(cursor.getString(cursor.getColumnIndex("TraSach")));
                phieuMuon.setTienThue(cursor.getInt(cursor.getColumnIndex("TienThue")));
                phieuMuon.setTrangThaiMuon(cursor.getInt(cursor.getColumnIndex("TrangThaiMuon")));  // Đọc giá trị trạng thái mượn sách

                list.add(phieuMuon);
            }
            cursor.close();
        }
        return list;
    }

    // Lấy danh sách tất cả mã thủ thư (MaTT)
    @SuppressLint("Range")
    public List<String> getAllMaTT() {
        List<String> maTTList = new ArrayList<>();
        Cursor cursor = db.query("phieumuon", new String[]{"MaTT"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                maTTList.add(cursor.getString(cursor.getColumnIndex("MaTT")));
            }
            cursor.close();
        }
        return maTTList;
    }

    // Lấy danh sách tất cả mã thành viên (MaTV)
    @SuppressLint("Range")
    public List<String> getAllMaTV() {
        List<String> maTVList = new ArrayList<>();
        Cursor cursor = db.query("phieumuon", new String[]{"MaTV"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                maTVList.add(cursor.getString(cursor.getColumnIndex("MaTV")));
            }
            cursor.close();
        }
        return maTVList;
    }

    // Lấy danh sách tất cả mã sách (MaSach)
    @SuppressLint("Range")
    public List<String> getAllMaSach() {
        List<String> maSachList = new ArrayList<>();
        Cursor cursor = db.query("phieumuon", new String[]{"MaSach"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                maSachList.add(cursor.getString(cursor.getColumnIndex("MaSach")));
            }
            cursor.close();
        }
        return maSachList;
    }

    // Tìm phiếu mượn theo mã phiếu
    @SuppressLint("Range")
    public PhieuMuon getPhieuMuonById(int maPM) {
        PhieuMuon phieuMuon = null;
        Cursor cursor = db.query("phieumuon", null, "MaPM=?", new String[]{String.valueOf(maPM)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            phieuMuon = new PhieuMuon();
            phieuMuon.setMaPM(cursor.getInt(cursor.getColumnIndex("MaPM")));
            phieuMuon.setMaTT(cursor.getString(cursor.getColumnIndex("MaTT")));
            phieuMuon.setMaTV(cursor.getInt(cursor.getColumnIndex("MaTV")));
            phieuMuon.setMaSach(cursor.getInt(cursor.getColumnIndex("MaSach")));
            phieuMuon.setNgayMuon(cursor.getString(cursor.getColumnIndex("NgayMuon")));
            phieuMuon.setTraSach(cursor.getString(cursor.getColumnIndex("TraSach")));
            phieuMuon.setTienThue(cursor.getInt(cursor.getColumnIndex("TienThue")));
            phieuMuon.setTrangThaiMuon(cursor.getInt(cursor.getColumnIndex("TrangThaiMuon")));
            cursor.close();
        }
        return phieuMuon;
    }
}
