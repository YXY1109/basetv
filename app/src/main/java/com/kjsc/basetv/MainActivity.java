package com.kjsc.basetv;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.kjsc.basetv.databinding.ActivityMainBinding;
import com.kjsc.basetv.mpandroid.MyMarkerView;
import com.kjsc.basetv.mpandroid.MyMarkerView2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    //折线图-温度数据
    private int[] mLableYHeartRate_wendu = new int[]{11, 13, 15, 18, 35, 40, 26, 23, 19, 34, 24, 3};
    //折线图-湿度数据
    private int[] mLableYHeartRate_shidu = new int[]{42, 46, 53, 58, 60, 76, 80, 94, 99, 73, 57, 12};
    //折线图-X轴数据
    private String[] mLableXHeartRate = new String[]{"00:00", "02:00", "04:00", "06:00", "08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initLineChart(binding.chartShidu, 0);//湿度
        initLineChart(binding.chartWendu, 1);//温度
    }

    //设置温度和湿度的折线图
    private void initLineChart(LineChart chart, int chart_type) {
        //设置线状图不显示描述
        chart.setDescription(null);

        if (chart_type == 0) {//湿度
            MyMarkerView2 mv = new MyMarkerView2(MainActivity.this, R.layout.custom_marker_view2);
            mv.setChartView(chart);
            chart.setMarker(mv);
        } else {//温度
            MyMarkerView mv = new MyMarkerView(MainActivity.this, R.layout.custom_marker_view);
            mv.setChartView(chart);
            chart.setMarker(mv);
        }

        //获取柱状图的X轴
        XAxis xAxis = chart.getXAxis();
        //下面两个是获取Y轴  包括左右
        YAxis axisLeft = chart.getAxisLeft();
        YAxis axisRight = chart.getAxisRight();
        //设置XY轴
        setAXis(chart, xAxis, axisLeft, axisRight, chart_type);
    }

    //折线图-XY轴
    public void setAXis(LineChart chart, XAxis xaxis, YAxis yAxisLeft, YAxis yAxisRight, int chart_type) {
        //设置X轴在图底部显示
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置X轴的宽度
        //xaxis.setAxisLineWidth(1);
        xaxis.setAxisLineColor(Color.WHITE);
        //起始0坐标开始
        xaxis.setAxisMinimum(0);
        //设置X轴显示轴线
        xaxis.setDrawAxisLine(false);
        //x的表格线不显示
        xaxis.setDrawGridLines(false);
        //设置X轴显示
        xaxis.setEnabled(true);
        //设置X轴文字的显示数量
        xaxis.setLabelCount(mLableXHeartRate.length, true);
        //设置X轴文字的颜色
        xaxis.setTextColor(getResources().getColor(R.color.white_40));
        //x轴显示字符串
        xaxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return mLableXHeartRate[(int) value];
            }
        });
        //y轴显示字符串
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (chart_type == 0) {
                    return (int) value + "%";
                } else {
                    return (int) value + "℃";
                }
            }
        });

        //y轴最小刻度值
        if (chart_type == 0) {
            yAxisLeft.setAxisMinimum(0);
        } else {
            yAxisLeft.setAxisMinimum(-10);
        }
        //不画网格线
        yAxisLeft.setDrawGridLines(true);
        yAxisLeft.setAxisLineColor(Color.WHITE);
        //显示Y轴轴线
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setTextColor(getResources().getColor(R.color.white_40));

        //设置虚线
        yAxisLeft.enableGridDashedLine(7f, 3f, 0f);

        //设置y轴间隔的增量
        if (chart_type == 0) {
            yAxisLeft.setAxisMaximum(150);
            yAxisLeft.setAxisMinimum(0);
            yAxisLeft.setLabelCount(6);
        }
        //不显示右Y轴
        yAxisRight.setEnabled(false);

        //左下角的图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        setLineData(chart, chart_type);
    }

    //折线图-设置数据
    public void setLineData(LineChart chart, int chart_type) {
        List<Entry> mListEnryMin = new ArrayList<>();

        for (int i = 0; i < mLableXHeartRate.length; i++) {
            //添加x,y坐标的值
            if (chart_type == 0) {
                mListEnryMin.add(new Entry(i, mLableYHeartRate_shidu[i]));
            } else {
                mListEnryMin.add(new Entry(i, mLableYHeartRate_wendu[i]));
            }
        }
        LineDataSet lineDataSet = new LineDataSet(mListEnryMin, "");
        //线条宽度
        lineDataSet.setLineWidth(0.5f);
        //圆点false=实心还是true=空心
        lineDataSet.setDrawCircleHole(true);
        //圆点半径
        lineDataSet.setCircleRadius(1f);
        //两点之间线条的模式
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        if (chart_type == 0) {
            //设置线条颜色
            lineDataSet.setColors(Color.parseColor("#25adc7"));
            //设置折线图转择点的值的大小
            //lineDataSet.setValueTextSize(12);
            //lineDataSet.setValueTextColor(Color.parseColor("#2cca8f"));
        } else {
            //设置线条颜色
            lineDataSet.setColors(Color.parseColor("#2cca8f"));
            //设置折线图转择点的值的大小
            //lineDataSet.setValueTextSize(12);
            //lineDataSet.setValueTextColor(Color.parseColor("#25adc7"));
        }

        //设置转折点是否显示值
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        chart.setData(lineData);

        lineDataSet.setHighlightEnabled(true);//设置是否显示十字线
        //将十字线颜色值透明
        lineDataSet.setHighLightColor(Color.parseColor("#00ffffff"));
        if (chart_type == 2) {
            //设置多个标记
            Highlight[] highlights = {new Highlight(0, 0, 0), new Highlight(1, 0, 0),
                    new Highlight(2, 0, 0), new Highlight(3, 0, 0)};
            chart.highlightValues(highlights);
        } else {
            //设置单个标记
            chart.highlightValue(1, 0);
        }

    }
}