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
}
