package com.example.duanmaufa24.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaufa24.Adapter.SachAdapter;
import com.example.duanmaufa24.DAO.LoaiSachDao;
import com.example.duanmaufa24.DAO.SachDao;
import com.example.duanmaufa24.Models.Sach;
import com.example.duanmaufa24.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SachFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private SachDao sachDao;
    private SachAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSach);
        fabAdd = view.findViewById(R.id.fabAddSach);

        // Initialize DAO
        sachDao = new SachDao(getContext());
        loadData();

        // Xử lý sự kiện nhấn vào FAB
        fabAdd.setOnClickListener(v -> showAddDialog());

        return view;
    }

    private void loadData() {
        List<Sach> list = sachDao.getAll();
        adapter = new SachAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_sach, null);
        builder.setView(dialogView);

        // Lấy tham chiếu tới Spinner và EditText
        Spinner spMaLoai = dialogView.findViewById(R.id.spnMaLoai);
        EditText edtTenSach = dialogView.findViewById(R.id.edtTenSach);
        EditText edtGiaThue = dialogView.findViewById(R.id.edtGiaThue);

        Button btnAdd = dialogView.findViewById(R.id.btnAdd);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        // Lấy mã loại từ cơ sở dữ liệu
        LoaiSachDao loaiSachDao = new LoaiSachDao(getContext());
        List<String> maLoaiList = loaiSachDao.getAllMaLoai();  // Lấy mã loại từ cơ sở dữ liệu
        ArrayAdapter<String> maLoaiAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, maLoaiList);
        maLoaiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaLoai.setAdapter(maLoaiAdapter);

        AlertDialog dialog = builder.create();

        btnAdd.setOnClickListener(v -> {
            String maLoai = spMaLoai.getSelectedItem().toString();
            String tenSach = edtTenSach.getText().toString().trim();
            String giaThue = edtGiaThue.getText().toString().trim();

            if (maLoai.isEmpty() || tenSach.isEmpty() || giaThue.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Sach sach = new Sach();
                sach.setMaLoai(maLoai);
                sach.setTenSach(tenSach);
                sach.setGiaThue(Integer.parseInt(giaThue));

                SachDao sachDao = new SachDao(getContext());
                long result = sachDao.insert(sach);
                if (result > 0) {
                    Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


}
