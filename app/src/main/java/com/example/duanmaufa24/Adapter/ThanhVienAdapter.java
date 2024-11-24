package com.example.duanmaufa24.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaufa24.DAO.ThanhVienDao;
import com.example.duanmaufa24.Models.ThanhVien;
import com.example.duanmaufa24.R;

import java.util.List;



public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    private Context context;
    private List<ThanhVien> thanhVienList;

    public ThanhVienAdapter(Context context, List<ThanhVien> thanhVienList) {
        this.context = context;
        this.thanhVienList = thanhVienList;
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.thanhvien_item, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ThanhVien thanhVien = thanhVienList.get(position);
        ThanhVienDao thanhvienDao = new ThanhVienDao(context);
        holder.tvMaTV.setText(String.valueOf(thanhVien.getMaTV()));
        holder.tvTenTV.setText(thanhVien.getHoTen());
        holder.tvNamSinh.setText(String.valueOf(thanhVien.getNamSinh()));
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa " + thanhVien.getHoTen() + " không?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int kq = thanhvienDao.delete(String.valueOf(thanhVien.getMaTV()));
                                if (kq > 0) {
                                    Toast.makeText(context, "Xóa thành công " + thanhVien.getHoTen(), Toast.LENGTH_SHORT).show();
                                    thanhVienList.remove(position);
                                    notifyItemRemoved(position);
                                } else {
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Hủy", null) // Dismisses the dialog when "Hủy" is clicked
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return thanhVienList.size();
    }

    public static class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaTV, tvTenTV, tvNamSinh;
        ImageView imgDelete;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTV = itemView.findViewById(R.id.tvMaTV);
            tvTenTV = itemView.findViewById(R.id.tvTenTV);
            tvNamSinh = itemView.findViewById(R.id.tvNamSinh);
            imgDelete = itemView.findViewById(R.id.imgDeleteLS);
        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(ThanhVien thanhVien);
    }
}
