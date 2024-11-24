package com.example.duanmaufa24.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duanmaufa24.Adapter.ThuThuAdapter;
import com.example.duanmaufa24.DAO.ThuThuDao;
import com.example.duanmaufa24.Models.ThuThu;
import com.example.duanmaufa24.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ThuThuFragment extends Fragment {

    private ListView lvThuThu;
    private ThuThuDao thuThuDao;
    private ThuThuAdapter thuThuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_thuthu, container, false);

        // Initialize components
        lvThuThu = rootView.findViewById(R.id.lvThuThu);
        thuThuDao = new ThuThuDao(getContext());

        // Set up the list view with the adapter
        loadThuThuData();

        // Handle Add button click
        FloatingActionButton btnAdd = rootView.findViewById(R.id.btnAddThuThu);
        btnAdd.setOnClickListener(view -> showAddDialog());

        return rootView;
    }

    private void loadThuThuData() {
        List<ThuThu> thuThuList = thuThuDao.getAll();
        thuThuAdapter = new ThuThuAdapter(getContext(), thuThuList);
        lvThuThu.setAdapter(thuThuAdapter);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm Thủ Thư");

        // Inflate dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_thuthu, null);
        EditText edtMaTT = dialogView.findViewById(R.id.edtMaTT);
        EditText edtHoTen = dialogView.findViewById(R.id.edtHoTen);
        EditText edtMatKhau = dialogView.findViewById(R.id.edtMatKhau);

        builder.setView(dialogView);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String maTT = edtMaTT.getText().toString().trim();
                String hoTen = edtHoTen.getText().toString().trim();
                String matKhau = edtMatKhau.getText().toString().trim();

                if (maTT.isEmpty() || hoTen.isEmpty() || matKhau.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    ThuThu thuThu = new ThuThu(maTT, hoTen, matKhau);
                    long result = thuThuDao.insert(thuThu);
                    if (result != -1) {
                        Toast.makeText(getContext(), "Thêm thủ thư thành công", Toast.LENGTH_SHORT).show();
                        loadThuThuData(); // Reload list
                    } else {
                        Toast.makeText(getContext(), "Thêm thủ thư thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton("Hủy", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.show();
    }
}
