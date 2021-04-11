package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;
import java.lang.String;
import java.util.stream.*;

public class UserMenu extends NormalUtil{
    public UserMenu(){
        super();
    }

    public static void displayUserInitMenu(List<Currency> currencies, List<Time>times){
        int selection;
        do{
            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "display the table",
                            "convert currency",
                            "show summary of conversion",
                            "back",
                    },
                    "Please enter a selection");

            switch (selection){

                case 1:
                    List<Currency> editedCurencies = getEditedCurrencies(currencies);
                    getcurrentTable(editedCurencies, times).display(4);//只用展出4个
                    displayUserInitMenu(currencies,times);
                    break;

                case 2:
                    System.out.println("Available currencies:");
                    for(int i = 0; i < currencies.size(); i+=1){
                        System.out.println(currencies.get(i).getName());
                    }
                    String cfrom = getString("enter the currency you want convert from").toUpperCase();
                    Double amount = getdouble("enter how much you want to convert");
                    String cto = getString("enter the currency you want convert to").toUpperCase();
                    String result = convert(cfrom,cto,times,amount);
                    if(result.startsWith("No such")){
                        System.out.println(result);
                    }else{
                        System.out.println(String.format("%.2f",amount) + " " + cfrom + " = " + result + " " + cto);
                    }
                    displayUserInitMenu(currencies,times);
                    break;

                case 3:
                    System.out.println("Available currencies:");
                    for(int i = 0; i < currencies.size(); i+=1){
                        System.out.println(currencies.get(i).getName());
                    }
                    System.out.println("You can see summary of conversion of 2 currencies in a certain period");
                    String cfrom2 = getString("From currency:").toUpperCase();
                    String cto2 = getString("To currency:").toUpperCase();
                    String fromd = getString("From date(in the 'yyyy-mm-dd' format eg. 2020-10-05):");
                    String tod = getString("To date(in the 'yyyy-mm-dd' format eg. 2020-10-05):");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date startd = null;
                    Date endd= null;
                    try {
                        startd = sdf.parse(fromd);
                        endd = sdf.parse(tod);
                    }catch (ParseException e){
                        System.out.println(e);
                    }
                    conversionsummary(startd,endd,cfrom2,cto2,times);
                    displayUserInitMenu(currencies,times);
                    break;

                case 4:
                    App.initMenu();
                    break;
            }
        }
        while (selection == -1);
    }

    //get the latest table
    public static Table getcurrentTable(List<Currency> currencies, List<Time>times){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date);

        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        Date yesterday = calendar.getTime();
        String yesterdayStr = sdf.format(yesterday);

        Time currentTime = null;
        Time yesterdayTime = null;

        for(int i=0;i<times.size();i+=1){
            if(dateNowStr.equals(times.get(i).getDate())){
                currentTime = times.get(i);
            }
        }

        for(int j=0;j<times.size();j+=1){
            if(yesterdayStr.equals(times.get(j).getDate())){
                yesterdayTime = times.get(j);
            }
        }

        Table currentTable = new Table(dateNowStr,currencies,currentTime);
        currentTable.setYesterdayTime(yesterdayTime);
        return currentTable;
    }

    // find the newest exchange rate and use it to convert currency
    public static String convert(String currencyfrom, String currencyto,List<Time> times, Double amount){
        if(currencyfrom.equals(currencyto)){
            return String.format("%.2f",amount);
        }
        if(times.size() > 0) { // if the time list is empty return null
            times.sort((i, j) -> i.getDate().compareTo(j.getDate())); // sort time list by its date from old to new
            for (int i = times.size()-1; i >= 0; i-=1) { // search time object from new to old
                for (int j = 0; j < times.get(i).getExchanges().size(); j += 1) { // check whether the exchange rete between these 2 currencies in this time object
                    if (times.get(i).getExchanges().get(j).getFromCurrency().getName().equals(currencyfrom) && times.get(i).getExchanges().get(j).getToCurrency().getName().equals(currencyto)) {
                        return String.format("%.2f", amount * times.get(i).getExchanges().get(j).getRate());
                    }
                }
                return "No such exchange rate between " + currencyfrom + " and " + currencyto;// if the corresponding exchange rate is not found in all time object
            }

        }
        return null;
    }
    //history, avg, median, max, min, standard diviation
    public static void conversionsummary (Date startdate, Date enddate, String currencyfrom, String currencyto, List<Time> times){
        List<Double> rates = new ArrayList<>(); // conversion rates of the 2 currencies in the period
        List<String> timesinp = new ArrayList<>(); // the date of those rates added within the period
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (times.size() != 0) {
            if (startdate.compareTo(enddate) <= 0) {
                times.sort((i, j) -> i.getDate().compareTo(j.getDate())); // sort the time arraylist according the date of time object from old to new
                try {
                    for (int i = 0; i < times.size(); i += 1) {
                        // only search time object in the period
                        if (startdate.compareTo(sdf.parse(times.get(i).getDate())) <= 0 && enddate.compareTo(sdf.parse(times.get(i).getDate())) >= 0) {
                            for (int j = 0; j < times.get(i).getExchanges().size(); j += 1) {
                                // find exchange rate between the 2 currencies and put the date of the rate added and the rate to 2 lists.
                                if (times.get(i).getExchanges().get(j).getFromCurrency().getName().equals(currencyfrom) && times.get(i).getExchanges().get(j).getToCurrency().getName().equals(currencyto)) {
                                    rates.add(times.get(i).getExchanges().get(j).getRate());
                                    timesinp.add(times.get(i).getDate());
                                }
                            }
                        }
                    }
                } catch (ParseException e) {
                    System.out.println(e);
                }
                if (rates.size()==0){ // if there is no such rate during the period
                    System.out.println("No exchange rate from " + currencyfrom + " to " + currencyto + " between " + sdf.format(startdate) + " and " + sdf.format(enddate) + ".");
                    return;
                }

                for (int i = 0; i < rates.size(); i += 1) { // print conversion history of the 2 currencies
                    System.out.println(timesinp.get(i) + " " + rates.get(i));
                }

                double mean = rates.stream().mapToDouble(i -> i).average().getAsDouble(); // calculate the mean of all exchange rate of the 2 currencies
                System.out.println("average rate: " + String.format("%.2f", mean));

                Collections.sort(rates);// sort by the rate
                if (rates.size() == 1) { // if there is only one exchange rate, this rate is the median
                    System.out.println("median rate: " + String.format("%.2f", rates.get(0)));
                } else if (rates.size() % 2 != 0) { // if the number of exchange rate is odd, the n/2th rate is median
                    System.out.println("median rate: " + String.format("%.2f", rates.get(rates.size() / 2)));
                } else { // if the number of exchange rate is even, the average of the n/2 th and n/2-1 th rate is the median
                    System.out.println("median rate: " + String.format("%.2f", (rates.get(rates.size() / 2) + rates.get(rates.size() / 2 - 1)) / 2));
                }

                System.out.println("max rate: " + String.format("%.2f", rates.stream().mapToDouble(i -> i).max().getAsDouble())); // get the max
                System.out.println("min rate: " + String.format("%.2f", rates.stream().mapToDouble(i -> i).min().getAsDouble())); // get the min

                // calculate the standard deviation
                double variance = 0.0;
                for (int i = 0; i < rates.size(); i += 1) {
                    variance += Math.pow((rates.get(i) - mean), 2);
                }
                double sd = Math.sqrt(variance / rates.size());
                System.out.println("standard deviation of rates: " + String.format("%.2f", sd));

            }else{
                System.out.println("The end date is earlier than start date, no such period exist, please enter correct period.");
            }
        }else{
            System.out.println("No data in database, please contact admin for help.");
        }

    }

    public static List<Currency> getEditedCurrencies(List<Currency> currencies){
        List<Currency> editedCurrencies = new ArrayList<>();
        int size = currencies.size();
        int index = size - 1;
        int i = 0;
        while (i < 4){
            Currency a = currencies.get(index-i);
            editedCurrencies.add(a);
            i+=1;
        }
        return editedCurrencies;
    }


}
