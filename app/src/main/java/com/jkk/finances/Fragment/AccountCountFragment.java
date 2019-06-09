package com.jkk.finances.Fragment;

import android.app.slice.Slice;
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
import com.jkk.finances.Utils.StampDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class AccountCountFragment extends Fragment {
    private PieChartView pieChartView_in;
    private PieChartView pieChartView_out;
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
        pieChartView_in.setChartRotation(90,true);
        pieChartView_in.setChartRotationEnabled(false);
        pieChartView_in.setCircleFillRatio(0.8F);

        pieChartView_out.setChartRotation(90,true);
        pieChartView_out.setChartRotationEnabled(false);
        pieChartView_out.setCircleFillRatio(0.8F);


    }

    private void initData(){
        Bundle bundle = getArguments();
        List<AccountInfo> accountInfos = (ArrayList<AccountInfo>)bundle.getSerializable("account");

        Map<String,List<SliceValue>> map = getDataByMonth(accountInfos,Calendar.getInstance().get(Calendar.MONTH)+1);

        PieChartData data = new PieChartData(map.get("支出"));
        data.setHasLabels(true);

        data.setHasLabelsOutside(true);
        data.setHasCenterCircle(true);
        data.setCenterText1("支出");
        data.setCenterText1FontSize(20);
        pieChartView_out.setPieChartData(data);

        data = new PieChartData(map.get("收入"));
        data.setHasLabels(true);

        data.setHasLabelsOutside(true);
        data.setHasCenterCircle(true);
        data.setCenterText1("收入");
        data.setCenterText1FontSize(20);
        pieChartView_in.setPieChartData(data);
    }


    private Map<String,List<SliceValue>> getDataByDay(List<AccountInfo> accountInfos,int day){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),day-1);
        String before = String.valueOf(cal.getTimeInMillis()/1000);
        cal.add(Calendar.DAY_OF_MONTH,1);
        String after = String.valueOf(cal.getTimeInMillis()/1000);

        return getDataBetweenStamp(accountInfos, before,after);
    }

    private Map<String,List<SliceValue>> getDataByMonth(List<AccountInfo> accountInfos,int month){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),month-1,0);
        String before = String.valueOf(cal.getTimeInMillis()/1000);
        cal.add(Calendar.MONTH,1);
        String after = String.valueOf(cal.getTimeInMillis()/1000);

        return getDataBetweenStamp(accountInfos, before,after);
    }

    private Map<String,List<SliceValue>> getDataByYear(List<AccountInfo> accountInfos,int year){
        Calendar cal = Calendar.getInstance();
        cal.set(year,0,0);
        String before = String.valueOf(cal.getTimeInMillis()/1000);
        cal.add(Calendar.YEAR,1);
        String after = String.valueOf(cal.getTimeInMillis()/1000);

       return getDataBetweenStamp(accountInfos, before,after);
    }

    private Map<String,List<SliceValue>> getDataBetweenStamp(List<AccountInfo> accountInfos,String persion,String after){
        List<SliceValue> value_in = new ArrayList<>();
        List<SliceValue> value_out = new ArrayList<>();

        Float all_in = 0F;
        Float all_out = 0F;
        Map<String,Float> rateMap_in = new HashMap<>();
        Map<String,Float> rateMap_out = new HashMap<>();

        List<AccountInfo> useful = new ArrayList<>();
        // 按时间删选
        for (AccountInfo accountInfo : accountInfos) {
            if (Long.valueOf(persion) < Long.valueOf(accountInfo.getTime()) && Long.valueOf(accountInfo.getTime()) < Long.valueOf(after)){
                useful.add(accountInfo);
            }
        }

        if (useful.size()==0){
            Map<String,List<SliceValue>> ret = new HashMap<>();
            List<SliceValue> value_null = new ArrayList<>();
            SliceValue sliceValue = new SliceValue(1);
            sliceValue.setLabel("没有数据");
            sliceValue.setColor(ChartUtils.DEFAULT_COLOR);
            value_null.add(sliceValue);
            ret.put("收入",value_null);
            ret.put("支出",value_null);
            return ret;
        }
        for (AccountInfo accountInfo : useful) {
            String type = accountInfo.getType();
            if (accountInfo.getMoney() < 0){
                // 支出 out
                if (rateMap_out.containsKey(type)){
                    rateMap_out.put(type,rateMap_out.get(type) + accountInfo.getMoney());
                }else {
                    rateMap_out.put(type,accountInfo.getMoney());
                }
                all_out += accountInfo.getMoney();
            }else if (accountInfo.getMoney() > 0){
                // 收入 in
                if (rateMap_in.containsKey(type)){
                    rateMap_in.put(type,rateMap_in.get(type) + accountInfo.getMoney());
                }else {
                    rateMap_in.put(type,accountInfo.getMoney());
                }
                all_in += accountInfo.getMoney();
            }
        }
        for (String s : rateMap_out.keySet()) {
            SliceValue sliceValue = new SliceValue(rateMap_out.get(s)/all_out, ColorUtil.nextColor());
            sliceValue.setLabel(s + String.format("%.2f%%",rateMap_out.get(s)/all_out*100));
            value_out.add(sliceValue);
        }

        for (String s : rateMap_in.keySet()) {
            SliceValue sliceValue = new SliceValue(rateMap_in.get(s)/all_in, ColorUtil.nextColor());
            sliceValue.setLabel(s + String.format("%.2f%%",rateMap_in.get(s)/all_in*100));
            value_in.add(sliceValue);
        }

        Map<String,List<SliceValue>> ret = new HashMap<>();
        ret.put("收入",value_in);
        ret.put("支出",value_out);
        return ret;
    }
}
