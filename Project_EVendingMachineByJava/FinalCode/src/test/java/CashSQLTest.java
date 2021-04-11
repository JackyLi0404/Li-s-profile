//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.LinkedHashMap;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class CashSQLTest {
//
//    CashSQL cashSQL;
//
//    @BeforeEach
//    public void setup() throws SQLException, IOException {
//        File test = java.io.File.createTempFile("test", ".db");
//        test.deleteOnExit();
//        String testFile = test.toString();
//        System.out.println(testFile);
//        Connection connnection = sqliteFunction.connectFile(testFile);
//        Statement stmt = connnection.createStatement();
//
//        stmt.execute("DROP  TABLE IF EXISTS Change");
//        stmt.execute(
//                "create table Change( " +
//                        "change_id  INT AUTO_INCREMENT  PRIMARY KEY ," +
//                        "change_type     DOUBLE PRECISION NOT NULL," +
//                        "change_quantity INT              NOT NULL" +
//                        ")"
//
//        );
//        stmt.execute(
//                "insert into Change(change_id, change_type, change_quantity)" +
//                        "values (1, '$100', 100)," +
//                        "(2, '$50', 100)," +
//                        "(3, '$20', 100)," +
//                        "(4, '$10', 100)," +
//                        "(5, '$5', 100)," +
//                        "(6, '$2', 100)," +
//                        "(7, '$1', 100)," +
//                        "(8, '50c', 100)," +
//                        "(9, '20c', 100)," +
//                        "(10, '10c', 100)," +
//                        "(11, '5c', 100);"
//        );
//
//        cashSQL = new CashSQL(connnection);
//        LinkedHashMap<String, Integer> dataBaseChanges1 = new LinkedHashMap<>();
//        dataBaseChanges1.put("$100", 90);
//        dataBaseChanges1.put("$50", 100);
//        dataBaseChanges1.put("$20", 100);
//        dataBaseChanges1.put("$10", 100);
//        dataBaseChanges1.put("$5", 100);
//        dataBaseChanges1.put("$2", 100);
//        dataBaseChanges1.put("$1", 100);
//        dataBaseChanges1.put("50c", 100);
//        dataBaseChanges1.put("20c", 100);
//        dataBaseChanges1.put("10c", 100);
//        dataBaseChanges1.put("5c", 100);
//    }
//
//    @Test
//    void getDatabaseChanges() throws SQLException {
//        String expect1 = "{$100=100, $50=100, $20=100, $10=100, $5=100, $2=100, $1=100, 50c=100, 20c=100, 10c=100, 5c=100}";
//        assertEquals(expect1, cashSQL.getDatabaseChanges().toString());
//    }
//
//    @Test
//    void writeDatabaseChanges() throws SQLException {
//        LinkedHashMap<String, Integer> dataBaseChanges1 = new LinkedHashMap<>();
//        dataBaseChanges1.put("$100", 90);
//        dataBaseChanges1.put("$50", 100);
//        dataBaseChanges1.put("$20", 100);
//        dataBaseChanges1.put("$10", 100);
//        dataBaseChanges1.put("$5", 100);
//        dataBaseChanges1.put("$2", 100);
//        dataBaseChanges1.put("$1", 100);
//        dataBaseChanges1.put("50c", 100);
//        dataBaseChanges1.put("20c", 100);
//        dataBaseChanges1.put("10c", 100);
//        dataBaseChanges1.put("5c", 100);
//        cashSQL.writeDatabaseChanges(dataBaseChanges1);
//        System.out.println(cashSQL.getDatabaseChanges().toString());
//        String expect = "{$100=90, $50=100, $20=100, $10=100, $5=100, $2=100, $1=100, 50c=100, 20c=100, 10c=100, 5c=100}";
//        assertEquals(expect, cashSQL.getDatabaseChanges().toString());
//    }
//}