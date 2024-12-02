package com.example.duanmaufa24.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.duanmaufa24.Models.Sach;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Library.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng sach
        db.execSQL("CREATE TABLE sach ("
                + "MaSach INTEGER PRIMARY KEY autoincrement, "
                + "MaLoai TEXT, "
                + "TenSach TEXT, "
                + "GiaThue REAL, "
                + "FOREIGN KEY (MaLoai) REFERENCES loaisach(MaLoai));");

        // Tạo bảng loaisach
        db.execSQL("CREATE TABLE loaisach ("
                + "MaLoai INTEGER PRIMARY KEY autoincrement, "
                + "TenLoai TEXT);");

        // Tạo bảng phieumuon với trường TrangThaiMuon
        db.execSQL("CREATE TABLE phieumuon ("
                + "MaPM INTEGER PRIMARY KEY autoincrement, "
                + "MaTT TEXT, "
                + "MaTV INTEGER, "
                + "MaSach INTEGER, "
                + "NgayMuon TEXT, "
                + "TraSach TEXT, "  // Bạn có thể giữ hoặc thay đổi kiểu dữ liệu này tùy nhu cầu
                + "TienThue INTEGER, "
                + "TrangThaiMuon INTEGER DEFAULT 0, "  // Trường mới cho trạng thái mượn sách
                + "FOREIGN KEY (MaTT) REFERENCES thuthu(MaTT), "
                + "FOREIGN KEY (MaTV) REFERENCES thanhvien(MaTV), "
                + "FOREIGN KEY (MaSach) REFERENCES sach(MaSach));");

        // Tạo các bảng khác như thuthu, thanhvien...
        db.execSQL("CREATE TABLE thuthu ("
                + "MaTT TEXT PRIMARY KEY  , "
                + "HoTen TEXT, "
                + "MatKhau TEXT);");

        db.execSQL("CREATE TABLE thanhvien ("
                + "MaTV INTEGER PRIMARY KEY autoincrement, "
                + "HoTen TEXT, "
                + "NamSinh INTEGER);");

        // Dữ liệu mẫu
        db.execSQL("INSERT INTO loaisach (TenLoai) VALUES ('Sách Khoa Học');");
        db.execSQL("INSERT INTO loaisach (TenLoai) VALUES ('Sách Văn Học');");
        db.execSQL("INSERT INTO sach (MaLoai, TenSach, GiaThue) VALUES (1, 'Lịch Sử Thế Giới', 5000);");
        db.execSQL("INSERT INTO sach (MaLoai, TenSach, GiaThue) VALUES (2, 'Truyện Kiều', 3000);");
        db.execSQL("INSERT INTO thuthu (MaTT, HoTen, MatKhau) VALUES ('Thuthu001','Nguyễn Văn A', '12345');");
        db.execSQL("INSERT INTO thuthu (MaTT, HoTen, MatKhau) VALUES ('Thuthu002','Trần Thị B', '67890');");
        db.execSQL("INSERT INTO thuthu (MaTT, HoTen, MatKhau) VALUES ('Admin','admin', '1111');");
        db.execSQL("INSERT INTO thanhvien (HoTen, NamSinh) VALUES ('Lê Văn C', 1990);");
        db.execSQL("INSERT INTO thanhvien (HoTen, NamSinh) VALUES ('Phạm Thị D', 1995);");
        db.execSQL("INSERT INTO phieumuon (MaTT, MaTV, MaSach, NgayMuon, TraSach, TienThue, TrangThaiMuon) VALUES (1, 1, 1, '2024-10-01', '2024-10-15', 5000, 0);");
        db.execSQL("INSERT INTO phieumuon (MaTT, MaTV, MaSach, NgayMuon, TraSach, TienThue, TrangThaiMuon) VALUES (2, 2, 2, '2024-10-05', '2024-10-20', 3000, 1);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE phieumuon ADD COLUMN TrangThaiMuon INTEGER DEFAULT 0;");
        }
        // Tiến hành xóa và tái tạo cơ sở dữ liệu nếu cần thiết
        db.execSQL("DROP TABLE IF EXISTS sach;");
        db.execSQL("DROP TABLE IF EXISTS loaisach;");
        db.execSQL("DROP TABLE IF EXISTS phieumuon;");
        db.execSQL("DROP TABLE IF EXISTS thuthu;");
        db.execSQL("DROP TABLE IF EXISTS thanhvien;");
        onCreate(db);
    }
    @SuppressLint("Range")
    public List<Sach> getTop10SachMuonNhieuNhat() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Sach> sachList = new ArrayList<>();

        // Query để lấy top 10 sách mượn nhiều nhất
        String query = "SELECT s.MaSach, s.TenSach, COUNT(pm.MaSach) AS SoLuongMuon " +
                "FROM sach s " +
                "JOIN phieumuon pm ON s.MaSach = pm.MaSach " +
                "WHERE pm.TrangThaiMuon = 1 " +
                "GROUP BY s.MaSach " +
                "ORDER BY SoLuongMuon DESC " +
                "LIMIT 10";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Sach sach = new Sach();
                sach.setMaSach(String.valueOf(cursor.getInt(cursor.getColumnIndex("MaSach"))));
                sach.setTenSach(cursor.getString(cursor.getColumnIndex("TenSach")));
                sach.setSoLuongMuon(cursor.getInt(cursor.getColumnIndex("SoLuongMuon")));
                sachList.add(sach);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return sachList;
    }
    public double getTongTienThuTrongKhoangThoiGian(String ngayBatDau, String ngayKetThuc) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Câu truy vấn mặc định
        String query = "SELECT SUM(TienThue) FROM phieumuon WHERE TrangThaiMuon = 1";

        // Nếu có điều kiện thời gian, thêm vào câu truy vấn
        if (ngayBatDau != null && !ngayBatDau.isEmpty() && ngayKetThuc != null && !ngayKetThuc.isEmpty()) {
            query += " AND NgayMuon BETWEEN ? AND ?";
        }

        // Thực thi câu lệnh truy vấn
        Cursor cursor = db.rawQuery(query, new String[]{ngayBatDau, ngayKetThuc});

        double totalRevenue = 0;
        if (cursor != null && cursor.moveToFirst()) {
            totalRevenue = cursor.getDouble(0);  // Lấy giá trị tổng tiền
            cursor.close();
        }

        db.close();
        return totalRevenue;
    }







}

