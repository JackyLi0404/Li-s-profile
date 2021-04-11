import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CashSQL {
    private final Connection connnection;

    public CashSQL(Connection connnection) {
        this.connnection = connnection;
    }


    public Map<String, Integer> getDatabaseChanges() throws SQLException {
        Map<String, Integer> map = CashConfig.getFaceNameQuantityMap();
        ResultSet output = sqliteFunction.select("select change_type, change_quantity from Change", connnection);
        while (output.next()) {
            String change_type = output.getString("change_type");
            int change_quantity = output.getInt("change_quantity");
            map.put(change_type, change_quantity);
        }
        return map;
    }

    public void writeDatabaseChanges(Map<String, Integer> map) {
        for (var entry : map.entrySet()) {
//            System.out.println(entry);
//            System.out.println("--------------");
            String name = entry.getKey();
            Integer quantity = entry.getValue();
            if(name.equals("100.00") || name.equals("50.00") || name.equals("20.00") || name.equals("10.00") || name.equals("5.00") || name.equals("2.00") || name.equals("1.00") || name.equals("0.50") || name.equals("0.20") || name.equals("0.10") || name.equals("0.05")) {
                String sql =
                        "update Change " +
                                "set change_quantity=%d " +
                                "where change_type= %s;";
                sql = String.format(sql, quantity, name);
//            System.out.println(sql);
                sqliteFunction.update(sql, connnection);
            }
        }
    }
}
