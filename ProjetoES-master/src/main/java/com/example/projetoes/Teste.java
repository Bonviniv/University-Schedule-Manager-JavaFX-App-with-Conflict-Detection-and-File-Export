package com.example.projetoes;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.opencsv.*;

public class Teste {
    public static void main(String[] args) throws ParseException {



        String[] dateParts = "12-02-2022".split("-");
        String formattedDate = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];
        System.out.println(formattedDate);

        /*DateFormat formatBR = new SimpleDateFormat("dd-MM-yyyy");
        String dataConvertida = formatBR.format(date);*/
        /*try {

            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader("Horario.csv");

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                for (String cell : nextRecord) {
                    String [] line  = cell.split(";");
                    System.out.println(line[0] +
                                    line[1] +
                                    line[2] +
                                    line[3] +
                                    line[4] +
                                    line[5] +
                                    line[6] +
                                    line[7] +
                                    line[8] +
                                    line[9] +
                                    line[10]
                            );
                    //System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
