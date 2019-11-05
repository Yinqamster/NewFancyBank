package model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SecurityAccount extends Account{
    //String: transaction; Stock stock
    private Map<String, HoldingStock> stockList;
    private BigDecimal totalAccount;

    public SecurityAccount() {
        super();
        stockList = new HashMap<>();
        totalAccount = new BigDecimal("0");
    }

    public Map<String, HoldingStock> getStockList() {
        return stockList;
    }

    public void setStockList(Map<String, HoldingStock> stockList) {
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
        HoldingStock holdingStock = new HoldingStock(stock.getCompany(), stock.getUnitPrice(), new BigDecimal(number));
        stockList.put(purchaseID, holdingStock);
        // It will subtract the price of stock
        totalAccount.add(stock.getUnitPrice().multiply(new BigDecimal(number)));
    }

    /**
     *
     * @param purchaseID The identity of a stock
     * @return
     */
    public boolean removeStock(String purchaseID) {
        if(stockList.containsKey(purchaseID)) {
            stockList.remove(purchaseID);
            return true;
        }
        return false;
    }
}
