package com.progressoft.tools;


import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Vector;



public class Min_Z_Score implements Normalizer{


    Imp_ScoringSummery scoringSummary = new Imp_ScoringSummery();//object from Imp_ScoringSummery

    public MathContext mc= new MathContext(7);// Global variable give you 7 decimals


    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize)  {

        String Equation_type="z";//Give you the type of equation

        Reader<Path> reader = new Reader<>();//object form Reader

        Vector<BigDecimal> Results;// The result will be the numbers under colToStandardize

        try {

            Results = reader.Read_File(csvPath,colToStandardize);//It sends path and column to Reader class

        }catch (FileNotFoundException exception){

            throw new IllegalArgumentException(exception);//Give an Exception if the path is wrong

        }

        Vector<String> Lines= reader.Final_Rows;//Get Rows from Reader class

        int index_of_col=reader.location;//Get index of column from Reader class

        BigDecimal Size= new BigDecimal(Results.size());//the number of people with their information


        Calculate_Mean(Results,Size);//Send result and number of people to calculate mean

        Calculate_Variance(Results,Size);//Send result and number of people to calculate variance

        Calculate_Stand_div();//calculate variance

        Calculate_Median(Results);//Send result to calculate median

        Calculate_min_max(Results);//Send result to calculate min and max


        BigDecimal Standard_Dev1=scoringSummary.standardDeviation();//Get standard deviation from Imp_ScoringSummery

        BigDecimal Mean=scoringSummary.mean();//Get mean from Imp_ScoringSummery

        for(int i=0;i<Results.size();i++)
        {

            BigDecimal Z_score= (Results.get(i).subtract(Mean)).divide(Standard_Dev1,mc);//Calculate z-score for each person

            Z_score = Z_score.setScale(2, RoundingMode.HALF_EVEN);

            Results.set(i, Z_score);

        }





        Writer<Object> writer= new Writer<>();//object from Writer class

        writer.Write_to_file(Results,Lines,index_of_col,Equation_type,destPath);//Send Results,rows,index of (colToStandardize),equation type and destination path to Writer class





        return scoringSummary;//object mn al scoring summery

    }



    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize)  {

        String Equation_type="mm";//Give you the type of equation

        Reader<Path> reader = new Reader<>();//object form Reader

        Vector<BigDecimal> Results;// The result will be the numbers under colToStandardize

        try {

            Results = reader.Read_File(csvPath,colToNormalize);//It sends path and column to Reader class

        }catch (FileNotFoundException exception){

            throw new IllegalArgumentException(exception);//Give an Exception if the path is wrong

        }

        Vector<String> Lines= reader.Final_Rows;//Get Rows from Reader class

        int index_of_col=reader.location;//Get index of column from Reader class

        BigDecimal Size= new BigDecimal(Results.size());//the number of people with their information


        Calculate_Mean(Results,Size);//Send result and number of people to calculate mean

        Calculate_Variance(Results,Size);//Send result and number of people to calculate variance

        Calculate_Stand_div();//calculate variance

        Calculate_Median(Results);//Send result to calculate median

        Calculate_min_max(Results);//Send result to calculate min and max



        BigDecimal Numerator,Denominator,Normalize;


        BigDecimal Min=scoringSummary.min();//Get minimum value from Imp_ScoringSummery

        BigDecimal Max=scoringSummary.max();//Get maximum value from Imp_ScoringSummery


        for(int i=0;i<Results.size();i++)
        {

            Numerator= Results.get(i).subtract(Min);

            Denominator=Max.subtract(Min);

            Normalize=Numerator.divide(Denominator,mc);//Calculate minMaxScaling for each person

            Normalize = Normalize.setScale(2, RoundingMode.HALF_EVEN);

            Results.set(i, Normalize);

        }



        Writer<Object> writer= new Writer<>();//object from Writer class

        writer.Write_to_file(Results,Lines,index_of_col,Equation_type,destPath);//Send Results,rows,index of (colToNormalize),equation type and destination path to Writer class






        return scoringSummary;//object mn al scoring summery
    }


// Function for calculate mean
    public  void Calculate_Mean(Vector<BigDecimal> Numbers, BigDecimal Size) {
        BigDecimal Calc_Mean;


        BigDecimal Sum = new BigDecimal("0");

        for (BigDecimal number : Numbers) {

            Sum = Sum.add(number);

        }

        Calc_Mean = Sum.divide(Size,mc);

        Calc_Mean = Calc_Mean.setScale(0, RoundingMode.HALF_EVEN);

        Calc_Mean = Calc_Mean.setScale(2);

        scoringSummary.setMean(Calc_Mean);

    }

    // Function for calculate variance
    public void Calculate_Variance(Vector<BigDecimal> Numbers,BigDecimal Size) {
        BigDecimal Mean=scoringSummary.mean();//Get mean value from Imp_ScoringSummery

        BigDecimal Variance = new BigDecimal("0");

        for (BigDecimal number : Numbers) {

            Variance = Variance.add((number.subtract(Mean)).pow(2));

        }

        Variance=Variance.divide(Size,mc);

        Variance = Variance.setScale(0, RoundingMode.HALF_EVEN);

        Variance = Variance.setScale(2);

        scoringSummary.setVariance(Variance);

    }

    // Function for calculate standard deviation
    public void Calculate_Stand_div() {
        BigDecimal Variance=scoringSummary.variance();//Get variance value from Imp_ScoringSummery

        double Standard_Dev= Math.sqrt(Variance.doubleValue());

        BigDecimal Standard_Dev1= new BigDecimal(Standard_Dev);

        Standard_Dev1 = Standard_Dev1.setScale(2,RoundingMode.HALF_EVEN);

        scoringSummary.setStand_div(Standard_Dev1);

    }

    // Function for calculate median
    public void Calculate_Median(Vector<BigDecimal> Numbers) {

        Vector<BigDecimal>Sort_Numbers=new Vector<>(Numbers);
        Collections.sort(Sort_Numbers);



        BigDecimal Calc_MEDIAN=new BigDecimal("0");



        int Second_Index= Sort_Numbers.size()/2;

        if(Sort_Numbers.size()%2==0){

            int First_Index=(Sort_Numbers.size()/2)-1;

            BigDecimal Division = new BigDecimal("2");

            Calc_MEDIAN =  Calc_MEDIAN.add(Sort_Numbers.get(First_Index));

            Calc_MEDIAN =  Calc_MEDIAN.add(Sort_Numbers.get(Second_Index));

            Calc_MEDIAN =  Calc_MEDIAN.divide(Division,mc);

            Calc_MEDIAN =  Calc_MEDIAN.setScale(0, RoundingMode.HALF_EVEN);

            Calc_MEDIAN =  Calc_MEDIAN.setScale(2);



        }
        else{

            Calc_MEDIAN = Sort_Numbers.get(Second_Index);

            Calc_MEDIAN = Calc_MEDIAN.setScale(2);

        }

        scoringSummary.setMedian(Calc_MEDIAN);

    }

    // Function for calculate min and max
    public void  Calculate_min_max(Vector<BigDecimal> Numbers) {

        Vector<BigDecimal>Sort_Numbers=new Vector<>(Numbers);

        Collections.sort(Sort_Numbers);


        BigDecimal min=Sort_Numbers.get(0);
        min = min.setScale(2);

        scoringSummary.setMin(min);
        BigDecimal max=Sort_Numbers.get(Sort_Numbers.size()-1);
        max = max.setScale(2);

        scoringSummary.setMax(max);

    }

}
