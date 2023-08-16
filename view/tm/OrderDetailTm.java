package view.tm;

public class OrderDetailTm {
    private String oId;
    private String itemCode;
    private Integer qty;
    private Double unitPrice;

    public OrderDetailTm(String oId, String itemCode, Integer qty, Double unitPrice) {
        this.oId = oId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getOId() {
        return oId;
    }

    public void setOId(String oId) {
        this.oId = oId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailTm{" +
                "oId='" + oId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
