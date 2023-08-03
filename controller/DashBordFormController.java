package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBordFormController implements Initializable {
    public AnchorPane rootPane;
    public Button btnCustomer;
    public Button btnItem;
    public Button btnPlaceOrder;
    public Button btnExit;
    public Button btnAbout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDash();
    }

    public void loadDash() {
        URL resource = this.getClass().getResource("/view/dash-root-form.fxml");

        assert resource != null;

        Parent load = null;
        try {
            load = (Parent) FXMLLoader.load(resource);
            this.rootPane.getChildren().clear();
            this.rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


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


    public void lblOnActionDash(MouseEvent mouseEvent) {
        loadDash();
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
    }

    public void btnAboutOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

    }
}
