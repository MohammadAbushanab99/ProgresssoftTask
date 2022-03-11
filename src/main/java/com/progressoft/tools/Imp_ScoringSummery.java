package com.progressoft.tools;

import java.math.BigDecimal;

public class Imp_ScoringSummery implements ScoringSummary{
    BigDecimal  Stand_div,Mean,Variance,Median,Min,Max;

    public void setMean(BigDecimal mean) {
        this.Mean = mean;
    }

    public void setStand_div(BigDecimal stand_div){this.Stand_div= stand_div;}

    public void setVariance(BigDecimal variance) {
        this.Variance = variance;
    }

    public void setMedian(BigDecimal median) {
        this.Median = median;
    }

    public void setMin(BigDecimal min) {
        this.Min = min;
    }

    public void setMax(BigDecimal max) {
        this.Max = max;
    }

    @Override
    public BigDecimal mean() {
        return this.Mean;
    }

    @Override
    public BigDecimal standardDeviation() {
        return this.Stand_div;
    }

    @Override
    public BigDecimal variance() {
        return this.Variance;
    }

    @Override
    public BigDecimal median() {
        return this.Median ;
    }

    @Override
    public BigDecimal min() {
        return this.Min;
    }

    @Override
    public BigDecimal max() {
        return this.Max;
    }
}
