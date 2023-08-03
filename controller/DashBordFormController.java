package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;

public class DashBordFormController {
    public AnchorPane rootPane;

    public void btnCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/customer-Form.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().add(load);
    }

    public void btnItemFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/item-Form.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().add(load);
    }
}
