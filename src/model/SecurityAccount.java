package model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SecurityAccount extends Account{
    //String: transaction; Stock stock
    private Map<String, HoldingStock> stockList;
    // private BigDecimal totalAccount;

    public SecurityAccount() {
        super();
        stockList = new HashMap<>();
    }

    public SecurityAccount(String accountNumber, Map<String, HoldingStock> stockList){
        super(accountNumber);
        this.stockList = stockList;
    }

    public Map<String, HoldingStock> getStockList() {
        return stockList;
    }

    public void setStockList(Map<String, HoldingStock> stockList) {
        this.stockList = stockList;
    }

}
