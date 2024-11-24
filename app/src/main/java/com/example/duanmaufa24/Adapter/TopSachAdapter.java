package com.example.duanmaufa24.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duanmaufa24.Models.Sach;
import com.example.duanmaufa24.R;

import java.util.List;

public class TopSachAdapter extends BaseAdapter {

    private Context context;
    private List<Sach> sachList;

    public TopSachAdapter(Context context, List<Sach> sachList) {
        this.context = context;
        this.sachList = sachList;
    }

    @Override
    public int getCount() {
        return sachList.size();
    }

    @Override
    public Object getItem(int position) {
        return sachList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_top_sach, parent, false);
        }

        Sach sach = sachList.get(position);

        TextView txtTenSach = convertView.findViewById(R.id.txtTenSach);
        TextView txtSoLuongMuon = convertView.findViewById(R.id.txtSoLuongMuon);

        txtTenSach.setText(sach.getTenSach());
        txtSoLuongMuon.setText("Số lần mượn: " + sach.getSoLuongMuon());

        return convertView;
    }
}
