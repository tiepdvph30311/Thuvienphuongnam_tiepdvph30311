package com.example.duanmaufa24.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaufa24.DAO.PhieuMuonDao;
import com.example.duanmaufa24.Models.PhieuMuon;
import com.example.duanmaufa24.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {
    private Context context;
    private List<PhieuMuon> list;
    private PhieuMuonDao phieuMuonDao;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        this.phieuMuonDao = new PhieuMuonDao(context);
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phieu_muon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        holder.tvMaPM.setText("Mã PM: " + phieuMuon.getMaPM());
        holder.tvMaTT.setText("Mã TT: " + phieuMuon.getMaTT());
        holder.tvNgayMuon.setText("Ngày mượn: " + phieuMuon.getNgayMuon());
        holder.tvNgayTra.setText("Ngày trả: " + phieuMuon.getTraSach());
        holder.tvTienThue.setText("Tiền thuê: " + phieuMuon.getTienThue());
        holder.tvTrangThai.setText("Trạng thái: " + (phieuMuon.getTrangThaiMuon() == 1 ? "Đã trả" : "Đang mượn"));

        // Nút sửa
        holder.btnEdit.setOnClickListener(v -> showEditDialog(phieuMuon, position));

        // Nút thay đổi trạng thái
        holder.btnChangeStatus.setOnClickListener(v -> {
            int TrangThai = phieuMuon.getTrangThaiMuon(); // Lấy trạng thái hiện tại
            phieuMuon.setTrangThaiMuon(TrangThai == 1 ? 0 : 1); // Đổi trạng thái
            int result = phieuMuonDao.update(phieuMuon);
            if (result > 0) {
                notifyItemChanged(position);
                Toast.makeText(context, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Cập nhật trạng thái thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            int result = phieuMuonDao.delete(String.valueOf(phieuMuon.getMaPM()));
            if (result > 0) {
                list.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("MissingInflatedId")
    private void showEditDialog(PhieuMuon phieuMuon, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_update_pm, null);
        builder.setView(dialogView);

        Spinner spMaTT = dialogView.findViewById(R.id.spnMaTT);
        Spinner spMaTV = dialogView.findViewById(R.id.spnMaTV);
        Spinner spMaSach = dialogView.findViewById(R.id.spnMaSach);
        TextView tvNgayMuon = dialogView.findViewById(R.id.tvNgayMuon);
        TextView tvNgayTra = dialogView.findViewById(R.id.tvNgayTra);
        EditText edtTienThue = dialogView.findViewById(R.id.edtTienThue);

        // Thiết lập spinner
        ArrayAdapter<String> adapterTT = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, phieuMuonDao.getAllMaTT());
        adapterTT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaTT.setAdapter(adapterTT);
        spMaTT.setSelection(adapterTT.getPosition(phieuMuon.getMaTT()));

        ArrayAdapter<String> adapterTV = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, phieuMuonDao.getAllMaTV());
        adapterTV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaTV.setAdapter(adapterTV);
        spMaTV.setSelection(adapterTV.getPosition(String.valueOf(phieuMuon.getMaTV())));

        ArrayAdapter<String> adapterSach = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, phieuMuonDao.getAllMaSach());
        adapterSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaSach.setAdapter(adapterSach);
        spMaSach.setSelection(adapterSach.getPosition(String.valueOf(phieuMuon.getMaSach())));

        tvNgayMuon.setText(phieuMuon.getNgayMuon());
        tvNgayTra.setText(phieuMuon.getTraSach());
        edtTienThue.setText(String.valueOf(phieuMuon.getTienThue()));

        // Xử lý chọn ngày
        tvNgayMuon.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                tvNgayMuon.setText(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        tvNgayTra.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                tvNgayTra.setText(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btnAdd).setOnClickListener(v -> {
            String tienThueStr = edtTienThue.getText().toString();
            String ngayMuon = tvNgayMuon.getText().toString();
            String ngayTra = tvNgayTra.getText().toString();

            // Kiểm tra Tiền thuê
            if (tienThueStr.isEmpty()) {
                Toast.makeText(context, "Tiền thuê không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            int tienThue;
            try {
                tienThue = Integer.parseInt(tienThueStr);
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Tiền thuê phải là một số hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra ngày mượn và ngày trả
            if (ngayMuon.isEmpty() || ngayTra.isEmpty()) {
                Toast.makeText(context, "Ngày mượn và ngày trả không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra ngày trả phải sau ngày mượn
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateMuon = dateFormat.parse(ngayMuon);
                Date dateTra = dateFormat.parse(ngayTra);
                if (dateTra.before(dateMuon)) {
                    Toast.makeText(context, "Ngày trả không thể trước ngày mượn", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (ParseException e) {
                Toast.makeText(context, "Ngày mượn và ngày trả không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra Spinner (Mã thể loại, mã sách, mã TV)


            // Cập nhật PhieuMuon
            phieuMuon.setMaTT(spMaTT.getSelectedItem().toString());
            phieuMuon.setMaTV(Integer.parseInt(spMaTV.getSelectedItem().toString()));
            phieuMuon.setMaSach(Integer.parseInt(spMaSach.getSelectedItem().toString()));
            phieuMuon.setNgayMuon(ngayMuon);
            phieuMuon.setTraSach(ngayTra);
            phieuMuon.setTienThue(tienThue);

            int result = phieuMuonDao.update(phieuMuon);
            if (result > 0) {
                list.set(position, phieuMuon);
                notifyItemChanged(position);
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    public static class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPM, tvMaTT, tvNgayMuon, tvNgayTra, tvTienThue, tvTrangThai;
        ImageView btnEdit, btnDelete, btnChangeStatus;

        public PhieuMuonViewHolder(View itemView) {
            super(itemView);
            tvMaPM = itemView.findViewById(R.id.tvMaPM);
            tvMaTT = itemView.findViewById(R.id.tvMaTT);
            tvNgayMuon = itemView.findViewById(R.id.tvNgayMuon);
            tvNgayTra = itemView.findViewById(R.id.tvNgayTra);
            tvTienThue = itemView.findViewById(R.id.tvTienThue);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnChangeStatus = itemView.findViewById(R.id.btnChangeStatus);
        }
    }
}
