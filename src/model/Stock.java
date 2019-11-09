/**
 * @author Group 5
 * @description  This is the data structure for a stock
 */

package model;

import java.math.BigDecimal;

public class Stock {
    private String company;
    private BigDecimal unitPrice;
    private int soldCount;

    public Stock(String company, BigDecimal unitPrice) {
        this.company = company;
        this.unitPrice = unitPrice;
        this.soldCount = 0;
    }

    public Stock(String company, BigDecimal unitPrice, int soldCount) {
        this.company = company;
        this.unitPrice = unitPrice;
        this.soldCount = soldCount;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public boolean equals(Object o) {
        if(o == null || !(o instanceof Stock))
            return false;
        Stock stock = (Stock)o;
        return stock.company.equals(this.company);
    }
}
