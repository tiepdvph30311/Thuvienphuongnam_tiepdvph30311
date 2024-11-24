package com.example.duanmaufa24;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaufa24.Adapter.LoaiSachAdapter;
import com.example.duanmaufa24.DAO.LoaiSachDao;
import com.example.duanmaufa24.Models.LoaiSach;

import java.util.List;


public class CategoryActivity extends AppCompatActivity {
   LoaiSachDao loaiSachDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerView= findViewById(R.id.rc_category);

        loaiSachDao = new LoaiSachDao(this);
        List<LoaiSach> listloaisach = loaiSachDao.getAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(this,listloaisach);
        recyclerView.setAdapter(loaiSachAdapter);

    }
}