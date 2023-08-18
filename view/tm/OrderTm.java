package view.tm;

import javafx.scene.control.Button;

public class OrderTm {
    private String id;
    private String date;
    private String customerId;
    private Button cancelButton;

    public OrderTm(String id, String date, String customerId, Button cancelButton) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.cancelButton = cancelButton;
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

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    @Override
    public String toString() {
        return "OrderTm{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cancelButton=" + cancelButton +
                '}';
    }
}
