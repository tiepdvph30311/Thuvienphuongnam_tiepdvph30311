package com.example.duanmaufa24.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duanmaufa24.Adapter.TopSachAdapter;
import com.example.duanmaufa24.Models.Sach;
import com.example.duanmaufa24.R;
import com.example.duanmaufa24.database.DbHelper;

import java.util.List;


public class Top10DanhSachFragment extends Fragment {



        private ListView lvTopSach;
        private DbHelper dbHelper;
        private TopSachAdapter topSachAdapter;
        private List<Sach> topSachs;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_top10_danh_sach, container, false);

            lvTopSach = rootView.findViewById(R.id.lvTopSach);
            dbHelper = new DbHelper(getContext());

            // Lấy dữ liệu top 10 sách mượn nhiều nhất
            topSachs = dbHelper.getTop10SachMuonNhieuNhat();

            // Tạo adapter và gắn dữ liệu vào ListView
            topSachAdapter = new TopSachAdapter(getContext(), topSachs);
            lvTopSach.setAdapter(topSachAdapter);

            return rootView;
        }
    }
