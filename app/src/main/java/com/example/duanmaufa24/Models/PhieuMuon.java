package com.example.duanmaufa24.Models;

public class PhieuMuon {
    private int maPM;
    private String maTT;
    private int maTV;
    private int maSach;
    private String ngayMuon;
    private String traSach;
    private int tienThue;
    private int trangThaiMuon;

    // Constructor
    public PhieuMuon(int maPM, String maTT, int maTV, int maSach, String ngayMuon, String traSach, int tienThue) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
        this.tienThue = tienThue;
    }

    public PhieuMuon(int maPM, String maTT, int maTV, int maSach, String ngayMuon, String traSach, int tienThue, int trangThaiMuon) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
        this.tienThue = tienThue;
        this.trangThaiMuon = trangThaiMuon;
    }

    // Getter và Setter cho từng thuộc tính
    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }


    public int getTrangThaiMuon() {
        return trangThaiMuon;
    }

    public void setTrangThaiMuon(int trangThaiMuon) {
        this.trangThaiMuon = trangThaiMuon;
    }


    public String getTraSach() {
        return traSach;
    }

    public void setTraSach(String traSach) {
        this.traSach = traSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public PhieuMuon() {
    }

    @Override
    public String toString() {
        return "PhieuMuon{" +
                "maPM=" + maPM +
                ", maTT='" + maTT + '\'' +
                ", maTV=" + maTV +
                ", maSach=" + maSach +
                ", ngayMuon='" + ngayMuon + '\'' +
                ", traSach=" + traSach +
                ", tienThue=" + tienThue +
                '}';
    }
}
