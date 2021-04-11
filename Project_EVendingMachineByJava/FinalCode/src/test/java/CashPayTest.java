//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class CashPayTest {
//    CashPay cashPay1;
//    CashPay cashPay2;
//    Integer payPrice1;
//    Integer payPrice2;
//    private LinkedHashMap<String, Integer> databaseChanges1;
//    private LinkedHashMap<String, Integer> databaseChanges2;
//    private LinkedHashMap<String, Integer> databaseChanges3;
//    private LinkedHashMap<String, Integer> databaseChanges4;
//
//    @BeforeEach
//    public void setup() {
//        payPrice1 = 1000;
//        cashPay1 = new CashPay(payPrice1);
//        cashPay1.setFaceQuantity("$10", 3);
//        cashPay1.setFaceQuantity("$5", 2);
//
//        payPrice2 = 2030;
//        cashPay2 = new CashPay(payPrice2);
//        cashPay2.setFaceQuantity("$10", 2);
//        cashPay2.setFaceQuantity("50c", 1);
//
//        databaseChanges4 = new LinkedHashMap<>();
//        databaseChanges4.put("$2", 100);
//
//        databaseChanges3 = new LinkedHashMap<>();
////        databaseChanges3.put("$1", 100);
//        databaseChanges3.put("50c", 100);
//
//        databaseChanges2 = new LinkedHashMap<>();
//        databaseChanges2.put("$100", 100);
//        databaseChanges2.put("$50", 100);
//        databaseChanges2.put("$20", 100);
//        databaseChanges2.put("$10", 100);
//
//        databaseChanges1 = new LinkedHashMap<>();
//        databaseChanges1.put("$100", 100);
//        databaseChanges1.put("$50", 100);
//        databaseChanges1.put("$20", 100);
//        databaseChanges1.put("$10", 100);
//        databaseChanges1.put("$5", 100);
//        databaseChanges1.put("$2", 100);
//        databaseChanges1.put("$1", 100);
//        databaseChanges1.put("50c", 100);
//        databaseChanges1.put("20c", 100);
//        databaseChanges1.put("10c", 100);
//        databaseChanges1.put("5c", 100);
//    }
//
//    @Test
//    void getPayPrice() {
//        assertEquals(payPrice1, cashPay1.getPayPrice());
//        assertEquals(payPrice2, cashPay2.getPayPrice());
//    }
//
//
//    @Test
//    void getInsertedTotal() {
//        assertEquals(4000, cashPay1.getInsertedTotal());
//        assertEquals(2050, cashPay2.getInsertedTotal());
//    }
//
//    @Test
//    void getReturnToal() {
//        assertTrue(cashPay1.getReturnEnough());
//    }
//
//    @Test
//    void getChange1() {
//        System.out.println(CashConfig.toDollars(15));
//        System.out.println(cashPay1.getChange(databaseChanges3));
//    }
//
//    @Test
//    void getChange() {
//        System.out.println(cashPay2.getPayPrice());
//        System.out.println(cashPay2.getInsertedTotal());
//        System.out.println(cashPay2.getReturnEnough());
//        System.out.println(cashPay1.getChange(databaseChanges3));
//        System.out.println(cashPay1.getChange(databaseChanges4));
//        String expect1 = "{$100=0, $50=0, $20=1, $10=1, $5=0, $2=0, $1=0, 50c=0, 20c=0, 10c=0, 5c=0}";
//        String expect2 = "{$100=0, $50=0, $20=0, $10=0, $5=0, $2=0, $1=0, 50c=0, 20c=1, 10c=0, 5c=0}";
//        String expect4 = "{$100=0, $50=0, $20=0, $10=0, $5=0, $2=15, $1=0, 50c=0, 20c=0, 10c=0, 5c=0}";
//        assertEquals(expect1, cashPay1.getChange(databaseChanges1).toString());
//        assertEquals(expect2, cashPay2.getChange(databaseChanges1).toString());
//        assertEquals(expect4, cashPay1.getChange(databaseChanges4).toString());
//
//        //inpossible change case cent too small
//        assertNull(cashPay2.getChange(databaseChanges3));
//        assertNull(cashPay1.getChange(null));
//        // not enough cash to pay back
//        assertNull(cashPay2.getChange(databaseChanges2));
//    }
//
//
//    @Test
//    void generateChangeString() {
//        String expect = "return total change amount: 30.00\n" +
//                "Changes:$100:0 $50:0 $20:1 $10:1 $5:0 $2:0 $1:0 50c:0 20c:0 10c:0 5c:0 ";
//        assertEquals(expect, cashPay1.generateChangeString(cashPay1.getChange(databaseChanges1)));
//        String expect2 = "return total change amount: 0.20\n" +
//                "Changes:$100:0 $50:0 $20:0 $10:0 $5:0 $2:0 $1:0 50c:0 20c:1 10c:0 5c:0 ";
//        assertEquals(expect2, cashPay2.generateChangeString(cashPay2.getChange(databaseChanges1)));
//
//    }
//
//    @Test
//    void getReturnEnough() {
//        assertTrue(cashPay1.getReturnEnough());
//        assertTrue(cashPay2.getReturnEnough());
//        cashPay2.setFaceQuantity("$10", 0);
//        cashPay2.setFaceQuantity("50c", 0);
//        assertFalse(cashPay2.getReturnEnough());
//    }
//
//    @Test
//    void combineChanges() {
//        cashPay2.getChange(databaseChanges1);
//        Map<String, Integer> resultChanges = cashPay2.combineChanges(databaseChanges1);
//        System.out.println(resultChanges);
//        assertEquals("{$5=100, $20=100, $10=102, 10c=100, 20c=99, 50c=101, $100=100, $1=100, $2=100, 5c=100, $50=100}",
//                resultChanges.toString());
//    }
//}
