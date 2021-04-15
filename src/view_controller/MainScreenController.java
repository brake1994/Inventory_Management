package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ControllerMethods;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Main screen
 */
public class MainScreenController implements Initializable {

        private Stage stage;
        private Parent scene;

        /**
         * Initialize main screen
         * Tables are filled during this.
         * @param url
         * @param rb
         */
        @Override
        public void initialize(URL url, ResourceBundle rb){
                partsTable.setItems(Inventory.getAllParts());
                tablePartId.setCellValueFactory(new PropertyValueFactory<>("id"));
                tablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
                tablePartInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
                tablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

                productTable.setItems(Inventory.getAllProducts());
                tableProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
                tableProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
                tableProductInventory.setCellValueFactory(new PropertyValueFactory<>("productStock"));
                tableProductPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        }

        @FXML
        private TableView<Part> partsTable;
        @FXML
        private TableColumn<Part, Integer> tablePartId;
        @FXML
        private TableColumn<Part, String> tablePartName;
        @FXML
        private TableColumn<Part, Integer> tablePartInventoryLevel;
        @FXML
        private TableColumn<Part, Double> tablePartPrice;
        @FXML
        private TextField partSearch;
        @FXML
        private TableView<Product> productTable;
        @FXML
        private TableColumn<Product, Integer> tableProductId;
        @FXML
        private TableColumn<Product, String> tableProductName;
        @FXML
        private TableColumn<Product, Integer> tableProductInventory;
        @FXML
        private TableColumn<Product, Double> tableProductPrice;
        @FXML
        private TextField productSearch;

        @FXML
        void onActionDeletePart() {
                Part q = partsTable.getSelectionModel().getSelectedItem();
                if(ControllerMethods.deleteConfirmationPopup()) {
                        Inventory.deletePart(q);
                }
        }

        @FXML
        void onActionDeleteProduct() {
                Product q = productTable.getSelectionModel().getSelectedItem();
                if(ControllerMethods.deleteConfirmationPopup()) {
                        if(!q.getAllAssociatedParts().isEmpty()){
                                ControllerMethods.showErrorPopup("Product has associated part. Can't delete!");
                        }
                        else {
                                Inventory.deleteProduct(q);
                        }
                }
        }

        @FXML
        void onActionExitProgram() {
                System.exit(0);
        }

        @FXML
        void onActionOpenAddPartScreen(ActionEvent event) throws IOException {

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view_controller/AddPart.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        @FXML
        void onActionOpenAddProduct(ActionEvent event) throws IOException {

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view_controller/AddProduct.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

        }


        //added else statement to prevent runtime error/ catch exception
        @FXML
        void onActionOpenModifyPartScreen(ActionEvent event) throws IOException {
                Part p = partsTable.getSelectionModel().getSelectedItem();
                if(p != null){
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view_controller/ModifyPart.fxml"));
                        Parent scene = loader.load();

                        //Sending data to ModifyPartController
                        ModifyPartController modifyPartController = loader.getController();
                        modifyPartController.sendPartData(p);

                        stage.setScene(new Scene(scene));
                        stage.setResizable(false);
                        stage.show();
                }
                else{
                        //open error window
                        ControllerMethods.showErrorPopup("Select a part.");
                }
        }

        @FXML
        void onActionOpenModifyProduct(ActionEvent event) throws IOException {
                Product p = productTable.getSelectionModel().getSelectedItem();
                if(p != null){
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view_controller/ModifyProduct.fxml"));
                        Parent scene = loader.load();

                        //Sending data to ModifyProductController
                        ModifyProductController modifyProductController = loader.getController();
                        modifyProductController.sendProductData(p);

                        stage.setScene(new Scene(scene));
                        stage.setResizable(false);
                        stage.show();
                }
                else{
                        //open error window
                        ControllerMethods.showErrorPopup("Select a product.");
                }
        }

        @FXML
        void OnActionSearchPart() {
                try {
                        if (!partSearch.getText().isEmpty()) {
                                boolean isInt = ControllerMethods.isNumber(partSearch.getText().trim());
                                if (!isInt) {
                                        String q = partSearch.getText().trim();
                                        String r = q.substring(0, 1).toUpperCase() + q.substring(1);  //changing first char of input to uppercase
                                        ObservableList<Part> searchResultName = Inventory.lookupPart(r);
                                        if(!searchResultName.isEmpty()) {
                                                partsTable.setItems(searchResultName);
                                        }
                                        else{
                                                ControllerMethods.showErrorPopup("Part not found!");
                                                partsTable.setItems(Inventory.getAllParts());
                                        }

                                }
                                else {
                                        Part q = Inventory.lookupPart(Integer.parseInt(partSearch.getText().trim()));
                                        ObservableList<Part> searchResultId = FXCollections.observableArrayList();
                                        searchResultId.add(q);
                                        if(q == null) {
                                                ControllerMethods.showErrorPopup("Part not found!");
                                                partsTable.setItems(Inventory.getAllParts());
                                        }
                                        else{
                                                partsTable.setItems(searchResultId);
                                        }
                                }
                        }
                        partsTable.refresh();
                }
                catch (NumberFormatException e){
                        ControllerMethods.showErrorPopup("Enter valid input: " + e.getMessage());
                }
                catch(NullPointerException e){
                        ControllerMethods.showErrorPopup("Part not found!");
                }

        }


        @FXML
        void OnActionSearchProduct(){
                try {
                        if (!productSearch.getText().isEmpty()) {
                                boolean isInt = ControllerMethods.isNumber(productSearch.getText().trim());
                                if (!isInt) {
                                        String q = productSearch.getText().trim();
                                        String r = q.substring(0, 1).toUpperCase() + q.substring(1);  //changing first char of input to uppercase
                                        ObservableList<Product> searchResultName = Inventory.lookupProduct(r);
                                        if(!searchResultName.isEmpty()) {
                                                productTable.setItems(searchResultName);
                                        }
                                        else{
                                                ControllerMethods.showErrorPopup("Product not found!");
                                                productTable.setItems(Inventory.getAllProducts());
                                        }
                                } else {
                                        Product q = Inventory.lookupProduct(Integer.parseInt(productSearch.getText().trim()));
                                        ObservableList<Product> searchResultId = FXCollections.observableArrayList();
                                        searchResultId.add(q);
                                        if(q != null) {
                                                productTable.setItems(searchResultId);
                                        }
                                        else{
                                                ControllerMethods.showErrorPopup("Product not found!");
                                                productTable.setItems(Inventory.getAllProducts());
                                        }
                                }
                        }
                        productTable.refresh();
                }
                catch (NumberFormatException e){
                        ControllerMethods.showErrorPopup("Enter valid input: " + e.getMessage());
                }
                catch(NullPointerException e){
                        ControllerMethods.showErrorPopup("Product not found!");
                }
        }


}
