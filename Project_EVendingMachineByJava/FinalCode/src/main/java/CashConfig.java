import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CashConfig {
    /**
     * @return map copntaion face with face value
     */
    static Map<String, Integer> getFaceNameValueMap() {
        Map<String, Integer> faceNameValueMap;
        faceNameValueMap = new LinkedHashMap<>();
//        faceNameValueMap.put("$100", 100.0);
//        faceNameValueMap.put("$50", 50.0);
//        faceNameValueMap.put("$20", 20.0);
//        faceNameValueMap.put("$10", 10.0);
//        faceNameValueMap.put("$5", 5.0);
//        faceNameValueMap.put("$2", 2.0);
//        faceNameValueMap.put("$1", 1.0);
//        faceNameValueMap.put("50c", 0.5);
//        faceNameValueMap.put("20c", 0.2);
//        faceNameValueMap.put("10c", 0.1);
//        faceNameValueMap.put("5c", 0.05);

        faceNameValueMap.put("100.00", 10000);
        faceNameValueMap.put("50.00", 5000);
        faceNameValueMap.put("20.00", 2000);
        faceNameValueMap.put("10.00", 1000);
        faceNameValueMap.put("5.00", 500);
        faceNameValueMap.put("2.00", 200);
        faceNameValueMap.put("1.00", 100);
        faceNameValueMap.put("0.50", 50);
        faceNameValueMap.put("0.20", 20);
        faceNameValueMap.put("0.10", 10);
        faceNameValueMap.put("0.05", 5);
        return faceNameValueMap;
    }

    /**
     * @return map contain faces with each count as 0.
     */
    static Map<String, Integer> getFaceNameQuantityMap() {
        Map<String, Integer> faceNameQuantityMap = new LinkedHashMap<>();
        Connection c = sqliteFunction.connect();

        for (String key : getFaceNameValueMap().keySet()) {
            try {
                String query = String.format("SELECT change_quantity FROM Change WHERE change_type = %s", String.valueOf(key));
                ResultSet rs = sqliteFunction.select(query,c);
                while (rs.next()) {
                    int quantity = rs.getInt("change_quantity");
                    faceNameQuantityMap.put(key,quantity);
                    }
                }catch (Exception e){
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    System.exit(0);
                }
        }
        try {
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return faceNameQuantityMap;
    }

    static Double toDollars(Integer cents) {
        return cents / 100.00;
    }

    static Integer toCents(Double dollars) {
        Double a = dollars * 100;
        return a.intValue();
    }
}

