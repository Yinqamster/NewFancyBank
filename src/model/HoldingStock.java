/**
 *  @author Group 5
 *  @description  This class records transaction information for each stock traded
 */
package model;

import java.math.BigDecimal;

/**
 * The stock information which users bought
 */
public class HoldingStock {
    private String companyName;
    private BigDecimal buyInPirce;
    private BigDecimal number;

    public HoldingStock(String companyName, BigDecimal buyInPirce, BigDecimal number) {
        this.companyName = companyName;
        this.buyInPirce = buyInPirce;
        this.number = number;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getBuyInPirce() {
        return buyInPirce;
    }

    public void setBuyInPirce(BigDecimal buyInPirce) {
        this.buyInPirce = buyInPirce;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
