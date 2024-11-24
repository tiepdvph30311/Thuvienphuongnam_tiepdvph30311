package com.example.duanmaufa24.Adapter;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaufa24.DAO.LoaiSachDao;
import com.example.duanmaufa24.Models.LoaiSach;
import com.example.duanmaufa24.R;

import java.util.List;



public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder>   {
    Context context;
    List<LoaiSach> listLoaiSach;
   LoaiSachDao loaiSachDao;

    public LoaiSachAdapter(Context context, List<LoaiSach> listLoaiSach) {
        this.context = context;
        this.listLoaiSach = listLoaiSach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);


        return new ViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
            LoaiSach loaiSach = listLoaiSach.get(position);
            holder.txtMaLoai.setText("Mã Loại Sách:"+loaiSach.getMaLoai());
            holder.txtTenLoai.setText("Tên Loại Sách:"+loaiSach.getTenSach());
        holder.imgEdit.setOnClickListener(view -> {
            showEditDialog(loaiSach, position);
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteDialog(loaiSach,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listLoaiSach.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
            TextView txtMaLoai,txtTenLoai;
            ImageView imgEdit,imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtId);
            txtTenLoai = itemView.findViewById(R.id.txt_tenloai);
            imgEdit = itemView.findViewById(R.id.img_edit);
            imgDelete = itemView.findViewById(R.id.img_delete);

        }
    }
    @SuppressLint("MissingInflatedId")
    private void showEditDialog(LoaiSach loaiSach, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_category, null);
        builder.setView(dialogView);

        TextView txtMaLoai = dialogView.findViewById(R.id.txt_edit_maLoai);
        TextView txtTenLoai = dialogView.findViewById(R.id.txt_edit_tenLoai);

        txtMaLoai.setText(loaiSach.getMaLoai());
        txtTenLoai.setText(loaiSach.getTenSach());

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String tenLoaiMoi = txtTenLoai.getText().toString().trim();

            if (!tenLoaiMoi.isEmpty()) {
                loaiSach.setTenSach(tenLoaiMoi);
                loaiSachDao = new LoaiSachDao(context);

                int result = loaiSachDao.update(loaiSach);
                if (result > 0) {
                    listLoaiSach.set(position, loaiSach);
                    notifyItemChanged(position);
                    Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Tên loại không được để trống!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDeleteDialog(LoaiSach loaiSach,int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa loại sách: " + loaiSach.getTenSach() + "?");
        loaiSachDao = new LoaiSachDao(context);
      int isDeleted=  loaiSachDao.delete(loaiSach.getMaLoai());
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            if (isDeleted>0) {
                Toast.makeText(context, "Xóa thành công loại sách: " + loaiSach.getTenSach() , Toast.LENGTH_SHORT).show();
                listLoaiSach.remove(i);
                notifyItemRemoved(i);
            } else {
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        // Nút Hủy
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
