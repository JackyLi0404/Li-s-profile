import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CashPay {
    private final Map<String, Integer> faceNameValueMap;
    private final Map<String, Integer> faceNameQuantityMap;
    private final Integer payPrice;

    public CashPay(Integer payPriceCents) {
        this.payPrice = payPriceCents;
        faceNameValueMap = CashConfig.getFaceNameValueMap();
        faceNameQuantityMap = CashConfig.getFaceNameQuantityMap();
    }


    public Integer getPayPrice() {
        return payPrice;
    }

    public String[] getFaceValueNames() {
        return faceNameValueMap.keySet().toArray(new String[0]);
    }


    public void setFaceQuantity(String face, int quantity) {
        faceNameQuantityMap.put(face, quantity);
    }

    public Integer getInsertedTotal() {
        int total = 0;
        for (String key : faceNameValueMap.keySet()) {
            total += faceNameQuantityMap.get(key) * faceNameValueMap.get(key);
        }
        return total;
    }

    public boolean getReturnEnough() {
        return getInsertedTotal() >= payPrice;
    }

    public Map<String, Integer> getChange(Map<String, Integer> databaseChanges) {
        if (databaseChanges == null) {
            return null;
        }
        Map<String, Integer> changeMap = new LinkedHashMap<>();
        for (String key : faceNameValueMap.keySet()) {
            changeMap.put(key, 0);
        }

        int acc = 0;
        // indicating last try there is a change
        boolean flag = true;
        while (acc + getPayPrice() < getInsertedTotal() && flag) {
            flag = false;
            for (String key : faceNameValueMap.keySet()) {
                int value = faceNameValueMap.get(key);
                int databaseQuantity = databaseChanges.getOrDefault(key, 0);
                if (databaseQuantity > 0 && acc + value + getPayPrice() <= getInsertedTotal()) {
                    //decrease quantity if add to map
                    databaseChanges.put(key, databaseQuantity - 1);
                    flag = true;
                    acc += value;
                    changeMap.put(key, 1 + changeMap.get(key));
                    break;// continue next round of find max face
                }
            }
        }
        // not enough to return fail to pay back
        if (acc + getPayPrice() != getInsertedTotal()) {
            return null;
        }
        return changeMap;
    }


    public Map<String, Integer> combineChanges(Map<String, Integer> databaseChanges) {
        Map<String, Integer> map = new HashMap<>();
        for (var k : databaseChanges.keySet()) {
            map.put(k, databaseChanges.get(k));
        }

        //append to result map
        for (String k : faceNameQuantityMap.keySet()) {
            map.put(k, faceNameQuantityMap.get(k) + map.getOrDefault(k, 0));
        }
        return map;
    }


    /**
     * @return string if change is isvalid, null if change could not be done
     */
    public String generateChangeString(Map<String, Integer> change) {
        int total = 0;
        Set<Map.Entry<String, Integer>> entries = change.entrySet();
        StringBuilder ans = new StringBuilder("Changes:");
        for (Map.Entry<String, Integer> x : entries) {
            ans.append(x.getKey());
            ans.append(":");
            ans.append(x.getValue());
            ans.append(" ");
            total += faceNameValueMap.get(x.getKey()) * x.getValue();
        }
        DecimalFormat f = new DecimalFormat("0.00");
        return "return total change amount: " + f.format(CashConfig.toDollars(total)) + "\n" + ans;
    }

    public String getTotalChanges(Map<String, Integer> change) {
        int total = 0;
        Set<Map.Entry<String, Integer>> entries = change.entrySet();
        StringBuilder ans = new StringBuilder("Changes:");
        for (Map.Entry<String, Integer> x : entries) {
            ans.append(x.getKey());
            ans.append(":");
            ans.append(x.getValue());
            ans.append(" ");
            total += faceNameValueMap.get(x.getKey()) * x.getValue();
        }
        DecimalFormat f = new DecimalFormat("0.00");
        return f.format(CashConfig.toDollars(total));
    }
}

