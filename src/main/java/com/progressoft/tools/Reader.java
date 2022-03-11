package com.progressoft.tools;

import java.io.File;
import java.math.BigDecimal;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Vector;

public class Reader<Path> extends NormalizerImpl {
    Vector<String> Final_Rows;
    int location = 0;

    public Vector<BigDecimal> readFile(Path csv, String Col_Calculation) throws FileNotFoundException {

        Vector<String> Rows = new Vector<>();//Vector to store rows from csv file

        Vector<BigDecimal> Numbers = new Vector<>();//Vector to store the numbers under column(Col_Calculation)

        File myFile = new File(csv.toString());//Get path of csv file


        try (Scanner myReader = new Scanner(myFile)) {

            while (myReader.hasNextLine()) {

                Rows.add(myReader.nextLine());//Add line to Rows

            }

        } catch (FileNotFoundException e) {
            //CATCHING Exceptions
            throw new IllegalArgumentException("source file not found", e);
        }

        row(Rows);//Store Rows in Final_Rows

        //Split rows and store it in array[string] to know the index of column
        String[] First_Row = Rows.get(0).split("[,]", 0);

        String[] Second_Row = Rows.get(1).split("[,]", 0);

        boolean error = false;

        for (int i = 0; i < Second_Row.length; i++) {

            if (isNumeric(Second_Row[i]) && First_Row[i].equals(Col_Calculation))//Check if column(Col_Calculation) exists and check the data under of column(Col_Calculation) is Numeric
            {
                location = i;
                error = true;
                break;
            } else {
                error = false;
            }


        }
        if (!error) {
            throw new IllegalArgumentException("column " + Col_Calculation + " not found");//Give an Exception if the column not found
        }

        index(location);//store index of column(Col_Calculation)

        for (int i = 1; i < Rows.size(); i++) {
            String[] Line = Rows.get(i).split("[,]", 0);

            BigDecimal n = new BigDecimal(Line[location]);

            Numbers.add(n);//store all numbers under column(Col_Calculation)

        }

        return Numbers;//Return numbers to calculate


    }

    //Store Rows at Final_Rows
    public void row(Vector<String> Store_Rows) {
        this.Final_Rows = Store_Rows;

    }

    //store index of column(Col_Calculation)
    public void index(int index) {
        this.location = index;

    }

    //check the data under of column is Numeric
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);

        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
