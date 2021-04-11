
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.sql.*;

// public class ConnectSQLite {
//    /**
//     * Connect to a sample database
//     */
//    public static void connect() {
//        Connection conn = null;
//        try {
//            // db parameters
//            String url = "jdbc:sqlite:./sample.db";
//            // create a connection to the database
//            conn = DriverManager.getConnection(url);

//            System.out.println("Connection to SQLite has been established.");

//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }

//    public static void createTable(String sqlQuery){
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:./sample.db");
//            System.out.println("Opened database successfully");

//            stmt = c.createStatement();
//            String sql = sqlQuery;
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Table created successfully");
//    }

//    public static void insert(String query){
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:./sample.db");
//            c.setAutoCommit(false);
//            System.out.println("Opened database successfully");

//            stmt = c.createStatement();
//            String sql = query;
//            stmt.executeUpdate(sql);

//            stmt.close();
//            c.commit();
//            c.close();
//        } catch ( Exception e ) {
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//            System.exit(0);
//        }
//        System.out.println("Records created successfully");
//    }

//    /**
//     * @param args
//     *            the command line arguments
//     */
//    public static void main(String[] args) throws ClassNotFoundException {
//        connect();

// //        ------------------------------------------
// //        query for table creation
//        String queryItemTable = "DROP TABLE IF EXISTS Item; " + "CREATE TABLE Item " +
//                "(item_id       INT AUTO_INCREMENT  PRIMARY KEY," +
//                " name    TEXT   NOT NULL, " +
//                " item_code  TEXT  NOT NULL, " +
//                " category            TEXT     NOT NULL, " +
//                " price    DOUBLE  NOU NULL, " +
//                " quantity_remain    INT    NOT NULL, " +
//                " quantity_total_sold INT              NOT NULL)";

//        String queryUserTable = "DROP TABLE IF EXISTS User; " + "CREATE TABLE User " +
//                "(user_name   TEXT   PRIMARY KEY    NOT NULL," +
//                " user_password     TEXT NOT NULL, " +
//                " card_holder_name  TEXT, " +
//                " card_number       STRING," +
//                " user_type         TEXT NOT NULL,"+
//                " user_last_5_items TEXT)";

//        String queryChangeTable = "DROP TABLE IF EXISTS Change; " + "CREATE TABLE Change " +
//                "(change_id       INT AUTO_INCREMENT  PRIMARY KEY," +
//                " change_type     DOUBLE PRECISION NOT NULL, " +
//                " change_quantity INT              NOT NULL)";

//        String queryTransactionTable = "DROP TABLE IF EXISTS Transactions; " + "CREATE TABLE Transactions " +
//                "(transaction_id INT AUTO_INCREMENT  PRIMARY KEY," +
//                " transaction_date   DATE                            NOT NULL, " +
//                " transaction_time   TIME                            NOT NULL, " +
//                " transaction_amount DOUBLE PRECISION                NOT NULL, " +
//                " transaction_items  TEXT                            NOT NULL," +
//                " transaction_change DOUBLE PRECISION                NOT NULL," +
//                " transaction_method TEXT                            NOT NULL," +
//                " user_name            TEXT REFERENCES \"user\" (user_name) NULL)";



//        String queryCancelledTransactionTable = "DROP TABLE IF EXISTS CancelledTransaction; " + "CREATE TABLE CancelledTransaction " +
//                "(cancelled_transaction_id     INT  AUTO_INCREMENT  PRIMARY KEY," +
//                " cancelled_transaction_date   DATE                            NOT NULL, " +
//                " cancelled_transaction_time   TIME                            NOT NULL, " +
//                " user_name                    TEXT REFERENCES \"user\" (user_name) NULL, " +
//                " cancelled_transaction_reason TEXT                            NOT NULL)";

//        createTable(queryItemTable);
//        createTable(queryUserTable);
//        createTable(queryChangeTable);
//        createTable(queryTransactionTable);
//        createTable(queryCancelledTransactionTable);

// //---------------------------------------------------------------
// //      insert some product as initialise
//        String owner ="insert into User(user_name,user_password,card_holder_name,card_number,user_type,user_last_5_items) values('admin','admin',null,null,'owner', null);";

//        String intial_user="insert into User(user_name,user_password,card_holder_name,card_number,user_type,user_last_5_items) values('right','right','Charles','40691','customer', 'Cola,Juice');";

//        String anon="insert into User(user_name,user_password,card_holder_name,card_number,user_type,user_last_5_items) values('anonymous','anonymous',null,null,'customer', null);";

//        String seller="insert into User(user_name,user_password,card_holder_name,card_number,user_type,user_last_5_items) values('seller','seller',null,null,'seller',null);";

//        String cashier="insert into User(user_name,user_password,card_holder_name,card_number,user_type,user_last_5_items) values('cashier','cashier',null,null,'cashier',null);";

//        String intial_user2="insert into User(user_name,user_password,card_holder_name,card_number,user_type,user_last_5_items) values('left','left',null,null,'customer', null);";

//        String dollar_100s="insert into Change(change_type,change_quantity) values(100.00,5);";

//        String dollar_50s="insert into Change(change_type,change_quantity) values(50.00,5);";

//        String dollar_20s="insert into Change(change_type,change_quantity) values(20.00,5);";

//        String dollar_10s="insert into Change(change_type,change_quantity) values(10.00,5);";

//        String dollar_5s="insert into Change(change_type,change_quantity) values(5.00,5);";

//        String dollar_2s="insert into Change(change_type,change_quantity) values(2.00,5);";

//        String dollar_1s="insert into Change(change_type,change_quantity) values(1.00,5);";

//        String dollar_0_5s="insert into Change(change_type,change_quantity) values(0.50,5);";

//        String dollar_0_2s="insert into Change(change_type,change_quantity) values(0.20,5);";

//        String dollar_0_1s="insert into Change(change_type,change_quantity) values(0.10,5);";

//        String dollar_0_05s="insert into Change(change_type,change_quantity) values(0.05,5);";

//        String mineralWater = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('MineralWater', '1001', 'Drinks', 1.5, 7, 0);";

//        String sprite = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Sprite', '1002', 'Drinks', 3.0, 7, 0);";

//        String pepsi = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Pepsi', '1003', 'Drinks', 3.0, 7, 0);";

//        String juice = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Juice', '1004', 'Drinks', 2.5, 7, 0);";

//        String mars = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Mars', '2001', 'Chocolates', 10.0, 7, 0);";

//        String mm = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('M&M', '2002', 'Chocolates', 12.0, 7, 0);";

//        String Bounty = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Bounty', '2003', 'Chocolates', 15.2, 7, 0);";

//        String Snickers = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Snickers', '2004', 'Chocolates', 13.2, 7, 0);";

//        String smiths = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Smiths', '3001', 'Chips', 8.5, 7, 0);";

//        String pringles = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Pringles', '3002', 'Chips', 9.35, 7, 0);";

//        String kettle = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Kettle', '3003', 'Chips', 5.40, 7, 0);";

//        String thins = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Thins', '3004', 'Chips', 8.55, 7, 0);";

//        String mentos = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Mentos', '4001', 'Candies', 10.30, 7, 0);";

//        String sour = "INSERT INTO Item (name, item_code, category, price, quantity_remain, quantity_total_sold) " +
//                "VALUES ('Sour', '4002', 'Candies', 8.85, 7, 0);";
//        insert(dollar_100s);
//        insert(dollar_50s);
//        insert(dollar_20s);
//        insert(dollar_10s);
//        insert(dollar_5s);
//        insert(dollar_2s);
//        insert(dollar_1s);
//        insert(dollar_0_5s);
//        insert(dollar_0_2s);
//        insert(dollar_0_1s);
//        insert(dollar_0_05s);
//        insert(intial_user);
//        insert(intial_user2);
//        insert(anon);
//        insert(owner);
//        insert(seller);
//        insert(cashier);
//        insert(mineralWater);
//        insert(sprite);
//        insert(pepsi);
//        insert(juice);
//        insert(mars);
//        insert(mm);
//        insert(Bounty);
//        insert(Snickers);
//        insert(smiths);
//        insert(pringles);
//        insert(kettle);
//        insert(thins);
//        insert(mentos);
//        insert(sour);

// //---------------------------------------------------------------


//    }
// }
