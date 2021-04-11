package Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;
import java.text.DecimalFormat;

public class App {
    public static double helper(double input){
        DecimalFormat df = new DecimalFormat("#.00");//https://blog.csdn.net/zzq900503/article/details/36898963
        String temp=df.format(input);
        double output=Double.parseDouble(temp);
        return output;
    }
    private static List<Currency> currencies = new ArrayList<>();
    private static List<Exchange> exchanges = new ArrayList<>();
    private static List<Time> times = new ArrayList<>();
    private static int plus_day;
    private static Date today;
    private static Date yesterday;
    private static List<Exchange> exchangesYesterday=new ArrayList<>();
    public static void setupall(List<Exchange> exchanges,List<Exchange> exchangesYesterday){
        today = new Date();
        plus_day=1;
        //get yesterday
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        yesterday = calendar.getTime();
        Currency usd = new CurrencyImpl("USD");
        Currency aud = new CurrencyImpl("AUD");
        Currency gbp = new CurrencyImpl("GBP");
        Currency cny = new CurrencyImpl("CNY");
        Currency eur = new CurrencyImpl("EUR");
        Currency inr = new CurrencyImpl("INR");
        currencies.add(usd);
        currencies.add(aud);
        currencies.add(gbp);
        currencies.add(cny);
        currencies.add(eur);
        currencies.add(inr);
        exchanges.add(new ExchangeImpl(usd,aud,1.39228));
        exchanges.add(new ExchangeImpl(aud,usd,helper(1/1.39228)));
        exchanges.add(new ExchangeImpl(usd,gbp,0.770317));
        exchanges.add(new ExchangeImpl(gbp,usd,helper(1/0.770317)));
        exchanges.add(new ExchangeImpl(usd,cny,6.79054));
        exchanges.add(new ExchangeImpl(cny,usd,helper(1/6.79054)));
        exchanges.add(new ExchangeImpl(usd,eur,0.848659));
        exchanges.add(new ExchangeImpl(eur,usd,helper(1/0.848659)));
        exchanges.add(new ExchangeImpl(usd,inr,73.1052));
        exchanges.add(new ExchangeImpl(inr,usd,helper(1/73.1052)));
        exchanges.add(new ExchangeImpl(aud,gbp,0.553276));
        exchanges.add(new ExchangeImpl(gbp,aud,helper(1/0.553276)));
        exchanges.add(new ExchangeImpl(aud,cny,4.87727));
        exchanges.add(new ExchangeImpl(cny,aud,helper(1/4.87727)));
        exchanges.add(new ExchangeImpl(aud,eur,0.609544));
        exchanges.add(new ExchangeImpl(eur,aud,helper(1/0.609544)));
        exchanges.add(new ExchangeImpl(aud,inr,52.5074));
        exchanges.add(new ExchangeImpl(inr,aud,helper(1/52.5074)));
        exchanges.add(new ExchangeImpl(gbp,cny,8.81525));
        exchanges.add(new ExchangeImpl(cny,gbp,helper(1/8.81525)));
        exchanges.add(new ExchangeImpl(gbp,eur,1.10170));
        exchanges.add(new ExchangeImpl(eur,gbp,helper(1/1.10170)));
        exchanges.add(new ExchangeImpl(gbp,inr,94.9028));
        exchanges.add(new ExchangeImpl(inr,gbp,helper(1/94.9028)));
        exchanges.add(new ExchangeImpl(cny,eur,0.124977));
        exchanges.add(new ExchangeImpl(eur,cny,helper(1/0.124977)));
        exchanges.add(new ExchangeImpl(cny,inr,10.7657));
        exchanges.add(new ExchangeImpl(inr,cny,helper(1/10.7657)));
        exchanges.add(new ExchangeImpl(eur,inr,86.1420));
        exchanges.add(new ExchangeImpl(inr,eur,helper(1/86.1420)));



        exchangesYesterday.add(new ExchangeImpl(usd,aud,1.39228));
        exchangesYesterday.add(new ExchangeImpl(aud,usd,helper(1/1.39228)));
        exchangesYesterday.add(new ExchangeImpl(usd,gbp,0.770317));
        exchangesYesterday.add(new ExchangeImpl(gbp,usd,helper(1/0.770317)));
        exchangesYesterday.add(new ExchangeImpl(usd,cny,6.79054));
        exchangesYesterday.add(new ExchangeImpl(cny,usd,helper(1/6.79054)));
        exchangesYesterday.add(new ExchangeImpl(usd,eur,0.848659));
        exchangesYesterday.add(new ExchangeImpl(eur,usd,helper(1/0.848659)));
        exchangesYesterday.add(new ExchangeImpl(usd,inr,73.1052));
        exchangesYesterday.add(new ExchangeImpl(inr,usd,helper(1/73.1052)));
        exchangesYesterday.add(new ExchangeImpl(aud,gbp,0.553276));
        exchangesYesterday.add(new ExchangeImpl(gbp,aud,helper(1/0.553276)));
        exchangesYesterday.add(new ExchangeImpl(aud,cny,4.87727));
        exchangesYesterday.add(new ExchangeImpl(cny,aud,helper(1/4.87727)));
        exchangesYesterday.add(new ExchangeImpl(aud,eur,0.609544));
        exchangesYesterday.add(new ExchangeImpl(eur,aud,helper(1/0.609544)));
        exchangesYesterday.add(new ExchangeImpl(aud,inr,52.5074));
        exchangesYesterday.add(new ExchangeImpl(inr,aud,helper(1/52.5074)));
        exchangesYesterday.add(new ExchangeImpl(gbp,cny,8.81525));
        exchangesYesterday.add(new ExchangeImpl(cny,gbp,helper(1/8.81525)));
        exchangesYesterday.add(new ExchangeImpl(gbp,eur,1.10170));
        exchangesYesterday.add(new ExchangeImpl(eur,gbp,helper(1/1.10170)));
        exchangesYesterday.add(new ExchangeImpl(gbp,inr,94.9028));
        exchangesYesterday.add(new ExchangeImpl(inr,gbp,helper(1/94.9028)));
        exchangesYesterday.add(new ExchangeImpl(cny,eur,0.124977));
        exchangesYesterday.add(new ExchangeImpl(eur,cny,helper(1/0.124977)));
        exchangesYesterday.add(new ExchangeImpl(cny,inr,10.7657));
        exchangesYesterday.add(new ExchangeImpl(inr,cny,helper(1/10.7657)));
        exchangesYesterday.add(new ExchangeImpl(eur,inr,86.1420));
        exchangesYesterday.add(new ExchangeImpl(inr,eur,helper(1/86.1420)));
        // exchangesYesterday = new ArrayList<>(exchanges);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(today);
        String yesterdayStr = sdf.format(yesterday);

        Time timeTest = new TimeImpl(dateNowStr, exchanges);
        Time timeTestYesterday = new TimeImpl(yesterdayStr,exchangesYesterday);
        times.add(timeTestYesterday);
        times.add(timeTest);
        System.out.println("Today is "+times.get(times.size()-1).getDate());//time test
    }
    public static void main(String[] args){
        //set and test
        //Currency add test
        setupall(exchanges,exchangesYesterday);
        initMenu();
    }

    public static void initMenu(){
        int selection;
        do{
            selection = NormalUtil.displayMenu("make a selection",
                    new String[]{
                            "normal user",
                            "admin",
                            "next day",
                            "quit"
                    },
                    "Please enter a selection");

            switch (selection) {
                case 1:
                    //user mode
                    UserMenu.displayUserInitMenu(currencies,times);
                    break;
                case 2:
                    AdminMenu.displayAdminInitMenu(currencies,times);
                    break;
                case 3:
                    /////////////////////////////////////////
                    boolean makesure=false;
                    String newexchange="";
                    while(!makesure) {
                        System.out.println("Are you sure? pleanse enter y or n");
                        Scanner scanner2=new Scanner(System.in);
                        String temp2 = scanner2.next();//au ->us 2
                        if (temp2.equals("y")) {
                            makesure = true;
                        }
                        else{
                            break;
                        }
                    }
                    if(makesure==false){
                        App.initMenu();
                    }
                    else {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(today);
                        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + plus_day);
                        plus_day += 1;
                        Date temp = calendar.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateNowStr = sdf.format(temp);
                        exchanges = new ArrayList<>(exchanges);
                        Time timeTest = new TimeImpl(dateNowStr, exchanges);//2020-10-06
                        times.add(timeTest);
                        System.out.println("Today is " + times.get(times.size() - 1).getDate());//time test
                        //////////////////////////////////////////
                        App.initMenu();
                    }
                case 4:
                    System.exit(0);
                default:
                //nothing here
                    return;
            }
        }
    while(selection == -1);
    }
}
