package Model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class AdminMenu  extends NormalUtil{
     public static void helper_add_currency(String newcurrency,List<Currency> currencies){
        boolean has=false;
        for(Currency cr:currencies){
            if(cr.getName().equals(newcurrency)){
                has=true;
                break;
            }
        }
        if(has==true){
            System.out.println("sorry, the currency is in your data before");
        }
        else {
            currencies.add(Builder.buildCurrency(newcurrency));

            System.out.println(newcurrency);
            System.out.println("Hi, admin. The new currency has been inputted to your database successfully");
        }
    }
    public static boolean helper_add_exchange(String newString,List<Time> times,List<Currency> currencies){
        String[] array = newString.split(",");
        if(array.length!=3){
            System.out.println("array.size "+array.length);
            System.out.println("wrong format message.pleanse enter message like this (CNY,YEN,9)");
            System.out.println();
            return false;
        }
        double numberrate=0;
        DecimalFormat df = new DecimalFormat("#.00");//https://blog.csdn.net/zzq900503/article/details/36898963
        try{
            numberrate=Double.parseDouble(array[2]);
        }
        catch (Exception e) {
            System.out.println("please enter rate as number");
            return false;
        }
        String temp2 = df.format(numberrate);
        Exchange newEX =Builder.buildExchange(array[0],array[1],Double.parseDouble(temp2));
        double re=1/numberrate;
        String temp=df.format(re);

        double output=Double.parseDouble(temp);
        Exchange newEX_RE =Builder.buildExchange(array[1],array[0],output);
        boolean has=false;
        List<Exchange> list_exchange=(times.get(times.size()-1)).getExchanges();
        for(Exchange EX:list_exchange){
            if(EX.equals(newEX)){
                has=true;
                break;
            }
        }
        if(has==true){
            System.out.println("Hi,admin. You have inputted the same exchange before :)");
            System.out.println();
            return false;
        }
        boolean diff=true;
        if(array[0].equals(array[1])){
            diff=false;
        }
        if(diff==false){
            System.out.println("Sorry ,admin.The name of two currency should be different");
            System.out.println();
            return false;
        }
        if(has==false){
            //////////judge whether the name in currencies list,and exhcange is absulotly different
            boolean from=false;
            boolean to=false;
            for(Currency temp3: currencies){
                if(array[0].equals(temp3.getName())){
                    from=true;
                }
                if(array[1].equals(temp3.getName())){
                    to=true;
                }
            }
            if(from==false&&to==false){
                System.out.println("sorry. The fromCurrency type and toCurrency type are not in your database");
                System.out.println();
                return false;
            }
            else if(from==false){
                System.out.println("sorry. The fromCurrency type  type is not in your database");
                System.out.println();
                return false;
            }
            else if(to==false){
                System.out.println("sorry. The toCurrency type  type is not in your database");
                System.out.println();
                return false;
            }

            if(from==true&&to==true&&diff==true) {
                int index=-1;
                for(int i=0;i<list_exchange.size();i++){
                    if(list_exchange.get(i).getFromCurrency().getName().equals(array[0])&&list_exchange.get(i).getToCurrency().getName().equals(array[1])){
                        index=i;
                        break;
                    }
                }
                if(index!=-1){
                    list_exchange.remove(index);
                }
                ///reverse pattern
                index=-1;
                for(int i=0;i<list_exchange.size();i++){
                    if(list_exchange.get(i).getFromCurrency().getName().equals(array[1])&&list_exchange.get(i).getToCurrency().getName().equals(array[0])){
                        index=i;
                        break;
                    }
                }
                if(index!=-1){
                    list_exchange.remove(index);
                }
                list_exchange.add(newEX);
                list_exchange.add(newEX_RE);
                return true;
            }
        }
        return false;
    }
    public AdminMenu(){
        super();
    }

    public static void displayAdminInitMenu(List<Currency> currencies, List<Time>times){
        int selection;
        do{
            selection = displayMenu("choose a function you want to use",
                    new String[]{
                            "display the table",
                            "convert currency",
                            "show summary of conversion",
                            "back",
                            "add currency",
                            "add newechange"
                    },
                    "Please enter a selection");

            switch (selection){

                case 1:
                    List<Currency> editedCurencies = getEditedCurrencies(currencies);
                    getcurrentTable(editedCurencies, times).display(4);//只用展出4个
                    displayAdminInitMenu(currencies, times);
                    break;

                case 2:
                    System.out.println("Available currencies:");
                    for(int i = 0; i < currencies.size(); i+=1){
                        System.out.println(currencies.get(i).getName());
                    }
                    String cfrom = getString("enter the currency will be converted").toUpperCase();
                    Double amount = getdouble("enter how much you want to convert");
                    String cto = getString("enter the currency you want convert to").toUpperCase();
                    String result = convert(cfrom,cto,times,amount);
                    if(result.startsWith("No such")){
                        System.out.println(result);
                    }else{
                        System.out.println(String.format("%.2f",amount) + " " + cfrom + " = " + result + " " + cto);
                    }
                    displayAdminInitMenu(currencies,times);
                    break;

                case 3:
                    System.out.println("Available currencies:");
                    for(int i = 0; i < currencies.size(); i+=1){
                        System.out.println(currencies.get(i).getName());
                    }
                    System.out.println("You can see summary of conversion of 2 currencies in a certain period");
                    String cfrom2 = getString("From currency:").toUpperCase();
                    String cto2 = getString("To currency:").toUpperCase();
                    String fromd = getString("From date(in the 'yyyy-mm-dd' format eg. 2020-10-5):");
                    String tod = getString("To date(in the 'yyyy-mm-dd' format eg. 2020-10-5):");
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
                    displayAdminInitMenu(currencies,times);
                    break;

                case 4:
                    App.initMenu();
                    break;
                case 5:
                    String newcurrenct=getString("Please enter your new currency");
                    helper_add_currency(newcurrenct,currencies);
                    displayAdminInitMenu(currencies,times);
                case 6:
                    System.out.println("Available currencies:");
                    for(int i = 0; i < currencies.size(); i+=1){
                        System.out.println(currencies.get(i).getName());
                    }
                    String eFrom = getString("enter the fromcurrency");
                    String eTo = getString("enter the tocurrency");
                    String eRate = getString("Enter the newrate");
                    String combine=eFrom+","+eTo+","+eRate;
                    helper_add_exchange(combine,times,currencies);
                    displayAdminInitMenu(currencies,times);
            }
        }
        while (selection == -1);
    }

    //get the latest table
    public static Table getcurrentTable(List<Currency> currencies, List<Time>times){


        Time currentTime = times.get(times.size()-1);
        Time yesterdayTime = times.get(times.size()-2);


        Table currentTable = new Table(currentTime.getDate(),currencies,currentTime);
        currentTable.setYesterdayTime(yesterdayTime);
        currentTable.setTodayTime(currentTime);
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