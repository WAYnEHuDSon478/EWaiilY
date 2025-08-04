// 代码生成时间: 2025-08-04 18:54:11
package com.example.analytics;

import io.micronaut.core.annotation.Introspected;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Introspected
public class StatisticalDataAnalyzer {

    // 构造函数
    public StatisticalDataAnalyzer() {
    }

    // 统计数据列表
    private List<StatisticData> data = new ArrayList<>();

    // 添加数据
    public void addData(StatisticData data) {
        this.data.add(data);
    }

    // 获取数据列表
    public List<StatisticData> getData() {
        return new ArrayList<>(data); // 返回数据副本以保护内部数据
    }

    // 计算平均值
    public double calculateMean() throws IllegalArgumentException {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("No data available to calculate mean.");
        }

        double sum = 0;
        for (StatisticData datum : data) {
            sum += datum.getValue();
        }
        return sum / data.size();
    }

    // 计算中位数
    public double calculateMedian() throws IllegalArgumentException {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("No data available to calculate median.");
        }

        List<StatisticData> sortedData = new ArrayList<>(data);
        sortedData.sort(StatisticData::compareTo);
        int size = sortedData.size();
        if (size % 2 == 0) {
            // 偶数个元素，取中间两个的平均值
            return (sortedData.get(size / 2 - 1).getValue() + sortedData.get(size / 2).getValue()) / 2.0;
        } else {
            // 奇数个元素，取中间的值
            return sortedData.get(size / 2).getValue();
        }
    }

    // 计算标准差
    public double calculateStandardDeviation() throws IllegalArgumentException {
        if (data.isEmpty()) {
            throw new IllegalArgumentException("No data available to calculate standard deviation.");
        }

        double mean = calculateMean();
        double squareSum = 0;
        for (StatisticData datum : data) {
            squareSum += Math.pow(datum.getValue() - mean, 2);
        }
        return Math.sqrt(squareSum / data.size());
    }

    // 统计数据类
    @Introspected
    public static class StatisticData implements Comparable<StatisticData> {

        private double value;

        public StatisticData(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public int compareTo(StatisticData other) {
            return Double.compare(this.value, other.value);
        }
    }
}
