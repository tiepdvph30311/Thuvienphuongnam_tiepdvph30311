package com.example.duanmaufa24.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.duanmaufa24.R;
import com.example.duanmaufa24.database.DbHelper;

import java.util.Calendar;

//public class ThongKeDoanhThuFragment extends Fragment {
//
//    private TextView tvFromDate, tvToDate, tvRevenue;
//    private Button btnRevenue;
//    private DbHelper dbHelper;
//    private int year, month, day;
//
//    public ThongKeDoanhThuFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_thong_ke_doanh_t_hu, container, false);
//
//        // Khởi tạo DbHelper
//        dbHelper = new DbHelper(getActivity());
//
//        // Lấy các phần tử UI
//        tvFromDate = rootView.findViewById(R.id.tvFromDate);
//        tvToDate = rootView.findViewById(R.id.tvToDate);
//        tvRevenue = rootView.findViewById(R.id.tvRevenue);
//        btnRevenue = rootView.findViewById(R.id.btnRevenue);
//
//        // Khởi tạo ngày hiện tại
//        Calendar calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        // Sự kiện chọn ngày bắt đầu
//        tvFromDate.setOnClickListener(v -> showDatePickerDialog(tvFromDate));
//
//        // Sự kiện chọn ngày kết thúc
//        tvToDate.setOnClickListener(v -> showDatePickerDialog(tvToDate));
//
//        // Sự kiện tính tổng tiền
//        btnRevenue.setOnClickListener(v -> {
//            String startDate = tvFromDate.getText().toString();
//            String endDate = tvToDate.getText().toString();
//
//            // Kiểm tra nếu người dùng đã chọn đủ ngày
//            if (!startDate.equals("Chưa chọn") && !endDate.equals("Chưa chọn") &&
//                    !startDate.isEmpty() && !endDate.isEmpty()) {
//                double totalRevenue = dbHelper.getTongTienThuTrongKhoangThoiGian(startDate, endDate);
//                tvRevenue.setText("Doanh Thu: " + totalRevenue);
//            } else {
//                tvRevenue.setText("Vui lòng chọn thời gian.");
//            }
//        });
//
//
//        return rootView;
//    }
//
//    // Hàm hiển thị DatePicker để chọn ngày
//    private void showDatePickerDialog(final TextView textView) {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                getActivity(),
//                (view, year, monthOfYear, dayOfMonth) -> {
//                    // Định dạng lại ngày theo chuẩn yyyy-MM-dd
//                    String formattedDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
//                    textView.setText(formattedDate);
//                },
//                year, month, day);
//        datePickerDialog.show();
//    }
//
//}
public class ThongKeDoanhThuFragment extends Fragment {

    private TextView tvFromDate, tvToDate, tvRevenue;
    private Button btnRevenue;
    private DbHelper dbHelper;
    private int year, month, day;

    public ThongKeDoanhThuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_thong_ke_doanh_t_hu, container, false);

        // Khởi tạo DbHelper
        dbHelper = new DbHelper(getActivity());

        // Lấy các phần tử UI
        tvFromDate = rootView.findViewById(R.id.tvFromDate);
        tvToDate = rootView.findViewById(R.id.tvToDate);
        tvRevenue = rootView.findViewById(R.id.tvRevenue);
        btnRevenue = rootView.findViewById(R.id.btnRevenue);

        // Đặt giá trị mặc định cho các TextView ngày
        tvFromDate.setText("Chưa chọn");
        tvToDate.setText("Chưa chọn");

        // Khởi tạo ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Sự kiện chọn ngày bắt đầu
        tvFromDate.setOnClickListener(v -> showDatePickerDialog(tvFromDate));

        // Sự kiện chọn ngày kết thúc
        tvToDate.setOnClickListener(v -> showDatePickerDialog(tvToDate));

        // Sự kiện tính tổng tiền
        btnRevenue.setOnClickListener(v -> {
            String startDate = tvFromDate.getText().toString();
            String endDate = tvToDate.getText().toString();

            // Kiểm tra nếu người dùng đã chọn đủ ngày
            if (!startDate.equals("Chưa chọn") && !endDate.equals("Chưa chọn") &&
                    !startDate.isEmpty() && !endDate.isEmpty()) {
                double totalRevenue = dbHelper.getTongTienThuTrongKhoangThoiGian(startDate, endDate);
                tvRevenue.setText("Doanh Thu: " + totalRevenue);
            } else {
                tvRevenue.setText("Vui lòng chọn thời gian.");
            }
        });


        return rootView;
    }

    // Hàm hiển thị DatePicker để chọn ngày
    private void showDatePickerDialog(final TextView textView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Định dạng lại ngày theo chuẩn yyyy-MM-dd
                    String formattedDate = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                    textView.setText(formattedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }
}

