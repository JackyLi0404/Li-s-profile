package Model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import java.awt.*;

//every time the App open, system will check if the today table was created.
// if not, it will be created automatically in App .
// question: if we need to design to display previous table rather than just current table?
public class Table extends JFrame{
    private String date;
    private Time currentTime;
    private Time yesterdayTime;
    private List<Currency> currencies;
    private JFrame jf;
    private JPanel panel;
    private List<List<Object>> allRowData;
    public Table(String date, List<Currency> currencies, Time currentTime){
        super();

        this.date = date;
        this.currencies = currencies;
        this.currentTime = currentTime;
        this.yesterdayTime = null;

        jf = new JFrame("title here");
        panel = new JPanel(new BorderLayout());
    }


//    compare current rates to yesterday. return 'rate up' if higher,'rate down' if lower
    public String compare(double currentRate, double yesterdayRate){
        if(currentRate > yesterdayRate){
            return String.format("%.02f ↑",currentRate);
        }
        else if(currentRate < yesterdayRate){
            return String.format("%.02f ↓",currentRate);
        }
        else {
            return String.format("%.02f",currentRate);
        }
    }

    public String getDate(){
        return date;
    }

    // transfer the exchanges to array type used in JTable
    public Object[][] getAllRowData(List<Exchange> exchanges){
        int num = currencies.size();
        Object[][] allRowData = new Object[num][num+1];

        // set the first column
        int i = 0;
        while (i<num){
            allRowData[i][0] = currencies.get(i).getName();
            i+=1;
        }

        // set rates for each column
        int j = 0;
        while (j<exchanges.size()){
            String from = exchanges.get(j).getFromCurrency().getName();
            String to = exchanges.get(j).getToCurrency().getName();
            double rate = exchanges.get(j).getRate();
            int indexfrom = 0;
            while (indexfrom < num){


                if (from.equals(currencies.get(indexfrom).getName())){
                    int indexto = 0;
                    while (indexto < num){
                        if (to.equals(currencies.get(indexto).getName())){
                            allRowData[indexfrom][indexto+1] = rate;
                        }
                        indexto += 1;
                    }
                }
                indexfrom += 1;
            }
            j+=1;
        }

        return allRowData;
    }

    public Object[]getAllColumnNames(List<Currency> currencies){
        int num = currencies.size();
        Object[] allColumnNames = new Object[num+1];
        allColumnNames[0] = "From/To";
        int i = 0;
        while(i<currencies.size()){
            allColumnNames[i+1] = currencies.get(i).getName();
            i+=1;
        }
        return allColumnNames;
    }

    //    display the table
    public void display(int size){


//        Example
//        Object[] allColumnNames = {"from/to","a","b","c","d","e"};
//        Object[][] allRowData = {
//                {"f", 80, 80, 80, 240, 20},
//                {"g", 70, 80, 90, 240, 20},
//                {"h", 70, 70, 70, 210, 20},
//                {"o", 80, 70, 60, 210, 20},
//                {"J", 80, 70, 60, 210, 20}
//        };

//        Object[] columnNames = Arrays.copyOfRange(allColumnNames,0,size+1);
//        Object[][] rowData = Arrays.copyOfRange(allRowData,0,size);
//      example


        Object[] columnNames = Arrays.copyOfRange(getAllColumnNames(currencies),0,size+1);
//        Object[][] rowData = Arrays.copyOfRange(getAllRowData(currentTime.getExchanges()),0,size);
        Object[][] rowData = Arrays.copyOfRange(getAllComparedData(),0,size);


        JTable table = new JTable(rowData,columnNames);

        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
        jf.setContentPane(panel);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);


//        jf.setDefaultCloseOperation(EXIT_ON_CLOSE); //// maybe need for exit?
    }

    public void setYesterdayTime(Time time){
        yesterdayTime = time;
    }
    public void setTodayTime(Time time){
        currentTime=time;
    }

    public Time getYesterdayTime(){
        return yesterdayTime;
    }

    public Object[][] getAllComparedData() {
        Object[][] currentData = getAllRowData(currentTime.getExchanges());
        
        if (yesterdayTime == null) {
            return currentData;
        } else {
            Object[][] yesterdayData = getAllRowData(yesterdayTime.getExchanges());

            int i = 0;
            while (i<yesterdayData.length){
                int j = 1;
                while(j < yesterdayData[i].length){
                    if (currentData[i][j] == null || yesterdayData[i][j] == null){
                        j += 1;
                    }
                    else {
                        double currentRate = Double.valueOf(currentData[i][j].toString());
                        double yesterdayRate = Double.valueOf(yesterdayData[i][j].toString());
                        String result = compare(currentRate, yesterdayRate);
                        currentData[i][j] = result;
                        j += 1;
                    }
                }
                i+=1;
            }
            return currentData;
        }

    }


}
