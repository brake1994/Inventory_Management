package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for modify part screen
 */
public class ModifyPartController implements Initializable {
    /**
     * Part object used to store sent data
     */
    private Part p;

    /**
     * Initialize modify part screen.
     * Confirmation message is displayed.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Modify Part Loaded");
    }

    @FXML
    private RadioButton inHouseButton;
    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private Label mIdComNameLabel;
    @FXML
    private TextField enterID;
    @FXML
    private TextField enterName;
    @FXML
    private TextField enterInv;
    @FXML
    private TextField enterPrice;
    @FXML
    private TextField enterMax;
    @FXML
    private TextField enterMachineID;
    @FXML
    private TextField enterMin;

    @FXML
    void OnActionCancelButton(ActionEvent event){
        ControllerMethods.returnToMainScreen(event);
    }

    @FXML
    void OnActionInHouse() {
        mIdComNameLabel.setText("Machine ID");
    }

    @FXML
    void OnActionOutsourced() {
        mIdComNameLabel.setText("Company");
    }

    @FXML
    void OnActionSaveChanges(ActionEvent event) {
        try {
            int oldPartIndex = Inventory.getAllParts().indexOf(p);
            int id = Integer.parseInt(enterID.getText());
            String q = enterName.getText();
            String name = q.substring(0, 1).toUpperCase() + q.substring(1);
            int stock = Integer.parseInt(enterInv.getText());
            double price = Double.parseDouble(enterPrice.getText());
            int max = Integer.parseInt(enterMax.getText());
            int min = Integer.parseInt(enterMin.getText());
            boolean validationCheck = false;
            TextField[] variables = {enterID, enterName, enterInv, enterPrice, enterMin, enterMax, enterMachineID};

            if (inHouseButton.isSelected() || outsourcedButton.isSelected()) {
                for (TextField testField : variables) {
                    boolean fieldError = ControllerMethods.checkForInvalidEntries(testField);
                    if (fieldError) {
                        validationCheck = true;
                        ControllerMethods.showErrorPopup("Fill in all fields");
                        break;
                    }
                }
                if (ControllerMethods.checkForInvalidInventory(stock, min, max)) {
                    if(min > max){
                        ControllerMethods.showErrorPopup("Invalid inventory entry. Min > max!");
                        return;
                    }
                    if(stock < 0 || min < 0 || max < 0){
                        ControllerMethods.showErrorPopup("Error! All inventory values must be non-negative.");
                        return;
                    }
                    if(!(min <= stock && stock <= max)){
                        ControllerMethods.showErrorPopup("Error! Inventory not within min-max range.");
                        return;
                    }
                }
                if (validationCheck) {
                    return;
                }
                else if (enterMachineID.getText().isEmpty()) {
                    ControllerMethods.showErrorPopup("Fill in all fields");
                    return;
                }
                else if (inHouseButton.isSelected()) {
                    int machineId = Integer.parseInt(enterMachineID.getText().trim());
                    InHousePart inHouse = new InHousePart(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(oldPartIndex, inHouse);
                    ControllerMethods.returnToMainScreen(event);
                }
                else if (outsourcedButton.isSelected()) {
                    String companyName = enterMachineID.getText().trim();
                    OutsourcedPart outSourced = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(oldPartIndex, outSourced);
                    ControllerMethods.returnToMainScreen(event);
                }
                else {
                    System.out.println("ERROR!");
                }
            }
        }
        catch (NumberFormatException e) {
            ControllerMethods.showErrorPopup("Enter valid input: " + e.getMessage());
        }
    }

    /**
     * Method used to send part data from MainScreenController to this controller.
     * A new part is initialized and the data is parsed then stored in the new Part.
     * @param p Old part that data is copied from.
     */
    public void sendPartData(Part p){
        System.out.println("Data sent.");
        this.p = p;

        if(p instanceof InHousePart) {
            InHousePart part = (InHousePart) p;
            mIdComNameLabel.setText("Machine ID");
            inHouseButton.setSelected(true);
            enterID.setText(Integer.toString(part.getId()));
            enterID.setEditable(false);
            enterName.setText(part.getName());
            enterInv.setText(Integer.toString(part.getStock()));
            enterPrice.setText(Double.toString(part.getPrice()));
            enterMax.setText(Integer.toString(part.getMax()));
            enterMin.setText(Integer.toString(part.getMin()));
            enterMachineID.setText(Integer.toString(part.getMachineId()));
        }
        if(p instanceof OutsourcedPart){
            OutsourcedPart part2 = (OutsourcedPart) p;
            outsourcedButton.setSelected(true);
            mIdComNameLabel.setText("Company");
            enterID.setText(Integer.toString(part2.getId()));
            enterID.setEditable(false);
            enterName.setText(part2.getName());
            enterInv.setText(Integer.toString(part2.getStock()));
            enterPrice.setText(Double.toString(part2.getPrice()));
            enterMax.setText(Integer.toString(part2.getMax()));
            enterMin.setText(Integer.toString(part2.getMin()));
            enterMachineID.setText(part2.getCompanyName());
        }
    }


}
