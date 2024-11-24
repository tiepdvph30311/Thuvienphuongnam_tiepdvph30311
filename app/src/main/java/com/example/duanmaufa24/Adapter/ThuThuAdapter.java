package com.example.duanmaufa24.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duanmaufa24.Models.ThuThu;
import com.example.duanmaufa24.R;
import com.example.duanmaufa24.DAO.ThuThuDao;

import java.util.List;

public class ThuThuAdapter extends BaseAdapter {

    private Context context;
    private List<ThuThu> thuThuList;

    public ThuThuAdapter(Context context, List<ThuThu> thuThuList) {
        this.context = context;
        this.thuThuList = thuThuList;
    }

    @Override
    public int getCount() {
        return thuThuList.size();
    }

    @Override
    public Object getItem(int position) {
        return thuThuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_thuthu, parent, false);
        }

        ThuThu thuThu = thuThuList.get(position);

        TextView txtMaTT = convertView.findViewById(R.id.txtMaTT);
        TextView txtHoTen = convertView.findViewById(R.id.txtHoTen);
        ImageView btnEdit = convertView.findViewById(R.id.btnEdit);
        ImageView btnDelete = convertView.findViewById(R.id.btnDelete);

        txtMaTT.setText(thuThu.getMaTT());
        txtHoTen.setText(thuThu.getHoTen());

        // Sự kiện nút Sửa
        btnEdit.setOnClickListener(v -> {
            // Tạo một dialog sửa thông tin thủ thư
            showEditDialog(thuThu);
        });

        // Sự kiện nút Xóa
        btnDelete.setOnClickListener(v -> {
            // Xóa thủ thư khỏi cơ sở dữ liệu
            ThuThuDao thuThuDao = new ThuThuDao(context);
            int result = thuThuDao.delete(thuThu.getMaTT());
            if (result > 0) {
                thuThuList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thủ thư thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Lỗi khi xóa thủ thư", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
    @SuppressLint("MissingInflatedId")
    // Hàm hiển thị Dialog sửa thông tin thủ thư
    private void showEditDialog(ThuThu thuThu) {
        // Tạo một dialog sửa thủ thư với thông tin hiện tại
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_sua_thuthu, null);

       final TextView edtMaTT = dialogView.findViewById(R.id.edtMaTT);
        final TextView edtHoTen = dialogView.findViewById(R.id.edtHoTen);
        final TextView edtMatKhau = dialogView.findViewById(R.id.edtMatKhau);

        edtMaTT.setText(thuThu.getMaTT());
        edtHoTen.setText(thuThu.getHoTen());
        edtMatKhau.setText(thuThu.getMatKhau());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setTitle("Sửa thông tin thủ thư")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String hoTen = edtHoTen.getText().toString();
                    String matKhau = edtMatKhau.getText().toString();

                    if (hoTen.isEmpty() || matKhau.isEmpty()) {
                        Toast.makeText(context, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        thuThu.setHoTen(hoTen);
                        thuThu.setMatKhau(matKhau);

                        // Cập nhật thông tin vào cơ sở dữ liệu
                        ThuThuDao thuThuDao = new ThuThuDao(context);
                        int result = thuThuDao.update(thuThu);
                        if (result > 0) {
                            notifyDataSetChanged(); // Refresh list
                            Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Lỗi khi cập nhật thông tin thủ thư", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Hủy", null)
                .create()
                .show();
    }
}
