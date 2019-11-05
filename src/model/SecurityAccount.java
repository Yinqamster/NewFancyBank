package model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SecurityAccount extends Account{
    //String: transaction; Stock stock
    private Map<String, Map<Stock, Integer>> stockList;
    private BigDecimal totalAccount;

    public SecurityAccount() {
        super();
        stockList = new HashMap<>();
        totalAccount = new BigDecimal("0");
    }

    public Map<String, Map<Stock, Integer>> getStockList() {
        return stockList;
    }

    public void setStockList(Map<String, Map<Stock, Integer>> stockList) {
        this.stockList = stockList;
    }

    public BigDecimal getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(BigDecimal totalAccount) {
        this.totalAccount = totalAccount;
    }

    //The method below should be written in controller
    /**
     *
     * @param purchaseID It will be generated before creating a stock object
     * @param stock The stock user wants to buy
     */
    public void addStock(String purchaseID, Stock stock, int number) {
        Map<Stock, Integer> stockMap = new HashMap<>();
        stockMap.put(stock, number);
        stockList.put(purchaseID, stockMap);
        // It will subtract the price of stock
        totalAccount.add(stock.getUnitPrice().multiply(new BigDecimal(String.valueOf(number))));
    }

    /**
     *
     * @param purchaseID The identity of a stock
     * @return
     */
    public boolean removeStock(String purchaseID) {
        if(stockList.containsKey(purchaseID)) {
            Map<Stock, Integer> stockMap = stockList.remove(purchaseID);
            // It will add the price of stock
            for(Map.Entry<Stock, Integer> entry : stockMap.entrySet()) {
                if(entry != null) {
                    BigDecimal unitPrice = entry.getKey().getUnitPrice();
                    BigDecimal number = new BigDecimal(String.valueOf(entry.getValue()));
                    totalAccount.subtract(unitPrice.multiply(number));
                    return true;
                }
            }
        }
        return false;
    }
}
