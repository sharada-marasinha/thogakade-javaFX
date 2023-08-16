package view.tm;

public class OrderTm {
    private String id;
    private String date;
    private String customerId;

    public OrderTm(String id, String date, String customerId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "OrderTm{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }
}
