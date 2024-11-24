package com.example.duanmaufa24.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import com.example.duanmaufa24.DAO.SachDao;
import com.example.duanmaufa24.Models.Sach;
import com.example.duanmaufa24.R;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {

    private Context context;
    private List<Sach> list;
    private SachDao sachDao;

    public SachAdapter(Context context, List<Sach> list) {
        this.context = context;
        this.list = list;
        sachDao = new SachDao(context);
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach sach = list.get(position);
        holder.tvMaLoai.setText("Mã loại: " + sach.getMaLoai());
        holder.tvTenSach.setText("Tên sách: " + sach.getTenSach());
        holder.tvGiaThue.setText("Giá thuê: " + sach.getGiaThue());

        // Sự kiện sửa
        holder.btnEdit.setOnClickListener(v -> showEditDialog(sach, position));

        // Sự kiện xóa
        holder.btnDelete.setOnClickListener(v -> showDeleteDialog(sach, position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void showEditDialog(Sach sach, int position) {
        // Tạo dialog sửa
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sua_sach, null);
        builder.setView(dialogView);

        // Lấy các EditText trong dialog
        EditText edtTenSach = dialogView.findViewById(R.id.edtTenSach);
        EditText edtGiaThue = dialogView.findViewById(R.id.edtGiaThue);
        Spinner spinnerMaLoai = dialogView.findViewById(R.id.spinnerMaLoai);

        // Lấy danh sách Mã loại từ cơ sở dữ liệu
        List<String> maLoaiList = sachDao.getAllMaLoai();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, maLoaiList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaLoai.setAdapter(adapter);

        // Cập nhật EditText và Spinner với giá trị hiện tại
        edtTenSach.setText(sach.getTenSach());
        edtGiaThue.setText(String.valueOf(sach.getGiaThue()));

        // Tìm vị trí mã loại hiện tại trong spinner
        int pos = maLoaiList.indexOf(sach.getMaLoai());
        if (pos >= 0) {
            spinnerMaLoai.setSelection(pos);
        }

        builder.setTitle("Sửa thông tin sách");

        builder.setPositiveButton("Xác nhận sửa", (dialog, which) -> {
            // Lấy giá trị từ EditText và Spinner
            String newTenSach = edtTenSach.getText().toString();
            double newGiaThue = Double.parseDouble(edtGiaThue.getText().toString());
            String newMaLoai = spinnerMaLoai.getSelectedItem().toString();

            sach.setTenSach(newTenSach);
            sach.setGiaThue((int) newGiaThue);
            sach.setMaLoai(newMaLoai);

            sachDao.update(sach);  // Cập nhật cơ sở dữ liệu
            list.set(position, sach);  // Cập nhật danh sách trong RecyclerView
            notifyItemChanged(position);  // Cập nhật item trong RecyclerView
            Toast.makeText(context, "Sửa sách thành công", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void showDeleteDialog(Sach sach, int position) {
        // Tạo dialog xóa
        new AlertDialog.Builder(context)
                .setTitle("Xóa sách")
                .setMessage("Bạn có chắc chắn muốn xóa sách này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    sachDao.delete(sach.getMaSach());  // Xóa sách khỏi cơ sở dữ liệu
                    list.remove(position);  // Xóa item khỏi danh sách
                    notifyItemRemoved(position);  // Cập nhật lại RecyclerView
                    Toast.makeText(context, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .create().show();
    }

    public static class SachViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaLoai, tvTenSach, tvGiaThue;
        ImageView btnEdit, btnDelete;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaLoai = itemView.findViewById(R.id.tvMaLoai);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
