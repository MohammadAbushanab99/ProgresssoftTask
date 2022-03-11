package com.progressoft.tools;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Vector;

public class Writer <Path> extends Min_Z_Score{

    public void Write_to_file(Vector<BigDecimal> Final_Resluts, Vector<String> Rows_to_Write, int index, String column_name,Path des){

        try{

            File myFile = new File(des.toString());//Get path of destination file

            PrintWriter write = new PrintWriter(myFile);




            String[] Row;

            Row =Rows_to_Write.get(0).split("[,]", 0);//Split rows and store it in array [string] to insert new column

            StringBuilder Final_row= new StringBuilder();

            //insert column after index of column_name
            for(int i=0;i<Row.length;i++)
            {

                if(i==index && i!=Row.length-1) {

                    Final_row.append(Row[i]).append(",");

                    Final_row.append(Row[i]).append("_").append(column_name).append(",");

                }else if(i==index && i==Row.length-1) {

                    Final_row.append(Row[i]).append(",");

                    Final_row.append(Row[i]).append("_").append(column_name);

                }else if(i==Row.length-1) {

                    Final_row.append(Row[i]);

                }else {

                    Final_row.append(Row[i]).append(",");

                }

            }

            write.println(Final_row);

            //insert results after index of column(column_name)
            for(int i=0;i<Final_Resluts.size();i++)
            {

                Row =Rows_to_Write.get(i+1).split("[,]", 0);

                Final_row = new StringBuilder();

                for(int j=0;j<Row.length;j++) {

                    if(j==index && j!=Row.length-1) {

                        Final_row.append(Row[j]).append(",");

                        Final_row.append(Final_Resluts.get(i).toString()).append(",");

                    }else if(j==index && j==Row.length-1) {

                        Final_row.append(Row[j]).append(",");

                        Final_row.append(Final_Resluts.get(i).toString());

                    }else if(j==Row.length-1) {

                        Final_row.append(Row[j]);

                    }else {

                        Final_row.append(Row[j]).append(",");
                    }


                }

                Rows_to_Write.set(i+1, Final_row.toString());

                write.println(Rows_to_Write.get(i+1));

            }


            write.close();
        }
        catch (FileNotFoundException e) {

            e.printStackTrace();

        }
    }

    }



