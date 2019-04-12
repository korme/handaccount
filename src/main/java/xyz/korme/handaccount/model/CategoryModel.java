package xyz.korme.handaccount.model;

public class CategoryModel {
    private Integer cateId;
    private String cateName;
    private Integer uniqueNumber;
    private String balance;
    private String ceateTime;
    private String cateTotal;
    private Integer cateTotalTrans;
    private Integer balanceTrans;
    private String accountId;

    public CategoryModel() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setCateTotal(String cateTotal) {
        this.cateTotal = cateTotal;
    }

    public String getCateTotal() {
        return cateTotal;
    }

    public Integer getCateTotalTrans() {
        return cateTotalTrans;
    }

    public void setCateTotalTrans(Integer cateTotalTrans) {
        this.cateTotalTrans = cateTotalTrans;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(Integer uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Integer getBalanceTrans() {
        return balanceTrans;
    }

    public void setBalanceTrans(Integer balanceTrans) {
        this.balanceTrans = balanceTrans;
    }

    public String getCeateTime() {
        return ceateTime;
    }

    public void setCeateTime(String ceateTime) {
        this.ceateTime = ceateTime;
    }
}
