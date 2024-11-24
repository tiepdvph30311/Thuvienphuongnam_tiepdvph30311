package com.example.duanmaufa24.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaufa24.Adapter.PhieuMuonAdapter;
import com.example.duanmaufa24.DAO.PhieuMuonDao;
import com.example.duanmaufa24.DAO.SachDao;
import com.example.duanmaufa24.DAO.ThanhVienDao;
import com.example.duanmaufa24.DAO.ThuThuDao;
import com.example.duanmaufa24.Models.PhieuMuon;
import com.example.duanmaufa24.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

public class PhieuMuonFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private PhieuMuonDao phieuMuonDao;
    private PhieuMuonAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPhieuMuon);
        fabAdd = view.findViewById(R.id.fabAddPhieuMuon);

        // Initialize DAO
        phieuMuonDao = new PhieuMuonDao(getContext());
        loadData();

        // Xử lý sự kiện nhấn vào FAB
        fabAdd.setOnClickListener(v -> showAddDialog());

        return view;
    }

    private void loadData() {
        List<PhieuMuon> list = phieuMuonDao.getAll();
        adapter = new PhieuMuonAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void showAddDialog() {
        // Hiển thị dialog thêm mới
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_phieu_muon, null);
        builder.setView(dialogView);

        Spinner spnMaTT = dialogView.findViewById(R.id.spnMaTT);
        Spinner spnMaTV = dialogView.findViewById(R.id.spnMaTV);
        Spinner spnMaSach = dialogView.findViewById(R.id.spnMaSach);
        TextView tvNgayMuon = dialogView.findViewById(R.id.tvNgayMuon);
        TextView tvNgayTra = dialogView.findViewById(R.id.tvNgayTra);
        EditText edtTienThue = dialogView.findViewById(R.id.edtTienThue);

        Button btnAdd = dialogView.findViewById(R.id.btnAdd);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();

        // Tải dữ liệu vào Spinner
        ThuThuDao thuThuDao = new ThuThuDao(getContext());
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        SachDao sachDao = new SachDao(getContext());

        ArrayAdapter<String> adapterMaTT = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, thuThuDao.getAllMaTT());
        adapterMaTT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaTT.setAdapter(adapterMaTT);

        ArrayAdapter<String> adapterMaTV = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, thanhVienDao.getAllMaTV());
        adapterMaTV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaTV.setAdapter(adapterMaTV);

        ArrayAdapter<String> adapterMaSach = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, sachDao.getAllMaSach());
        adapterMaSach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMaSach.setAdapter(adapterMaSach);

        // Xử lý chọn ngày
        tvNgayMuon.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year1, month1, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        tvNgayMuon.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        tvNgayTra.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view, year1, month1, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        tvNgayTra.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        btnAdd.setOnClickListener(v -> {
            String maTT = spnMaTT.getSelectedItem().toString();
            String maTV = spnMaTV.getSelectedItem().toString();
            String maSach = spnMaSach.getSelectedItem().toString();
            String ngayMuon = tvNgayMuon.getText().toString().trim();
            String ngayTra = tvNgayTra.getText().toString().trim();
            String tienThue = edtTienThue.getText().toString().trim();

            if (ngayMuon.isEmpty() || tienThue.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                // Tạo đối tượng Phiếu Mượn và thêm vào CSDL
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaTT(maTT);
                phieuMuon.setMaTV(Integer.parseInt(maTV));
                phieuMuon.setMaSach(Integer.parseInt(maSach));
                phieuMuon.setNgayMuon(ngayMuon);
                phieuMuon.setTraSach(ngayTra);
                phieuMuon.setTrangThaiMuon(0); // Chưa trả sách
                phieuMuon.setTienThue(Integer.parseInt(tienThue));

                long result = phieuMuonDao.insert(phieuMuon);
                if (result > 0) {
                    Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                    loadData(); // Tải lại danh sách
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


}
