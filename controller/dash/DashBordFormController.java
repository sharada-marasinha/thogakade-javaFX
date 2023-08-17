package controller.dash;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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

        Parent load;
        try {
            load = FXMLLoader.load(resource);
            rootPane.getChildren().clear();
            rootPane.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void btnCustomerFormOnAction() throws IOException {
        URL resource = this.getClass().getResource("/view/customer-Form.fxml");

        assert resource != null;

        Parent load =  FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }

    public void btnItemFormOnAction() throws IOException {
        URL resource = this.getClass().getResource("/view/item-Form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }

    public void lblOnActionDash() {
        loadDash();
    }

    public void btnExitOnAction() {
        System.exit(0);
    }

    public void btnAboutOnAction() throws IOException {
        URL resource = this.getClass().getResource("/view/about-form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }

    public void btnPlaceOrderOnAction() throws IOException {
        URL resource = this.getClass().getResource("/view/place-order-form.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }
}
