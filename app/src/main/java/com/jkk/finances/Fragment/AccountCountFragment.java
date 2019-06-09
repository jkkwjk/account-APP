package com.jkk.finances.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkk.finances.Model.AccountInfo;
import com.jkk.finances.R;
import com.jkk.finances.Utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class AccountCountFragment extends Fragment {
    private PieChartView pieChartView_in;
    private PieChartView pieChartView_out;
    private PieChartData data;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_count,container,false);
        pieChartView_in = view.findViewById(R.id.piechart_in);
        pieChartView_out = view.findViewById(R.id.piechart_out);


        initView();
        initData();
        return view;
    }

    private void initView(){
        pieChartView_in.setChartRotation(180,true);
        pieChartView_in.setChartRotationEnabled(false);
        pieChartView_in.setCircleFillRatio(0.8F);

        pieChartView_out.setChartRotation(180,true);
        pieChartView_out.setChartRotationEnabled(false);
        pieChartView_out.setCircleFillRatio(0.8F);
    }

    private void initData(){
        Bundle bundle = getArguments();
        List<AccountInfo> accountInfos = (ArrayList<AccountInfo>)bundle.getSerializable("account");
        List<String> typeList = new ArrayList<>();
        List<SliceValue> values = new ArrayList<>();
        if (accountInfos!=null){
            for (AccountInfo accountInfo : accountInfos) {
                accountInfo.getType();
            }
        }else {

        }
        int numValues = 6;
        //初始化数据

        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ColorUtil.nextColor());
            sliceValue.setLabel("支付宝20%");
            values.add(sliceValue);
        }

        data = new PieChartData(values);
        data.setHasLabels(true);
        data.setHasLabelsOnlyForSelected(true);
        data.setHasLabelsOutside(true);
        data.setHasCenterCircle(true);
        data.setCenterText1("支出");
        data.setCenterText1FontSize(20);
        pieChartView_in.setPieChartData(data);
    }
}
