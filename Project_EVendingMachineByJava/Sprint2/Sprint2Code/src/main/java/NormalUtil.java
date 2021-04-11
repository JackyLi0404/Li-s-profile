import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class NormalUtil {

    // when we want to create a menu, ues this function
    static int displayMenu(String header, String[] options, String prompt) {
        System.out.println("\n" + header);

        for (int i = 0; i < options.length; i++) {
            System.out.println((i+1) + ". " + options[i]);
        }

        while (true) {
            int selection = getInt(prompt);

            if (selection > 0 && selection <= options.length) {
                return selection;
            } else {
                System.out.println("Invalid menu selection");
            }
        }
    }

    static String getString(String prompt) throws IllegalStateException {

        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time out");
                if (App.transaction != null){
                    App.transaction.cancelbytimeout();
                }
                System.exit(0);
            }
        };

        Timer timer = new Timer();

        timer.schedule(task, 10 * 1000);

        Scanner s = new Scanner(System.in);

        String response = null;
        do{
            System.out.println(prompt);
            response = s.nextLine();

            if ("".equals(response)) {
                response = null;
                System.out.println("Blank entry is not allowed.");
            }
        }while (response == null);

        timer.cancel();
        return response;
    }

    static int getInt(String prompt){

        int response;
        do {
            String str = getString(prompt);
            try {
                response = Integer.parseInt(str);
                return response;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input - number required");
            }

        } while (true);


    }

    static Double getdouble(String prompt) {


        Double response;
        do {
            String str = getString(prompt);
            try {
                response = Double.parseDouble(str);

                return response;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input - number required");
            }

        } while (true);
    }

}
