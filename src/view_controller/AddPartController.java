package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.*;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller for add part screen.
 */
public class AddPartController implements Initializable {

    /**
     * Initialize add part screen.
     * Unique Ids are generated during this.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        generatePartId();
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
    void OnActionCancelButton(ActionEvent event) {

        ControllerMethods.returnToMainScreen(event);
    }

    @FXML
    void OnActionInHouse() {
        mIdComNameLabel.setText("Machine ID");
        enterMachineID.setPromptText("Enter Machine Id");

    }

    @FXML
    void OnActionOutsourced() {
        mIdComNameLabel.setText("Company");
        enterMachineID.setPromptText("Enter Company Name");
    }

    @FXML
    void OnActionSaveChanges(ActionEvent event) {
        try {
            int id = Integer.parseInt(enterID.getText().trim());
            String q = enterName.getText().trim();
            String name = q.substring(0, 1).toUpperCase() + q.substring(1);
            int stock = Integer.parseInt(enterInv.getText().trim());
            double price = Double.parseDouble(enterPrice.getText().trim());
            int max = Integer.parseInt(enterMax.getText().trim());
            int min = Integer.parseInt(enterMin.getText().trim());
            boolean validationCheck = false;
            TextField[] variables = {enterID, enterName, enterInv, enterPrice, enterMin, enterMax, enterMachineID};

            if(inHouseButton.isSelected() || outsourcedButton.isSelected()) {
                for(TextField testField : variables){
                    boolean fieldError = ControllerMethods.checkForInvalidEntries(testField);
                    if(fieldError){
                        validationCheck = true;
                        ControllerMethods.showErrorPopup("Fill in all fields");
                        break;
                    }
                }
                if(ControllerMethods.checkForInvalidInventory(stock, min, max)){
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
                if(validationCheck){
                    return;
                }
                else if(enterMachineID.getText().isEmpty()){
                    ControllerMethods.showErrorPopup("Fill in all fields");
                    return;
                }
                else if (inHouseButton.isSelected()){
                    int machineId = Integer.parseInt(enterMachineID.getText().trim());
                    InHousePart inHouse = new InHousePart(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(inHouse);
                    ControllerMethods.returnToMainScreen(event);
                }
                else if(outsourcedButton.isSelected()){
                    String companyName = enterMachineID.getText().trim();
                    OutsourcedPart outSourced = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(outSourced);
                    ControllerMethods.returnToMainScreen(event);
                }
                else{
                    System.out.println("ERROR!");
                }

            }

        }
        catch (NumberFormatException e){
            ControllerMethods.showErrorPopup("Enter valid input: " + e.getMessage());
        }
    }

    /**
     * Used to generate unique part IDs
     */
    private void generatePartId(){
        Random random = new Random();
        int randId = random.nextInt(100);
        if(isUniqueId(randId)){
            if(randId != 0) {
                enterID.setText(Integer.toString(randId));
                enterID.setEditable(false);
            }
            else{
                generatePartId();
            }
        }
        else{
            generatePartId();
        }


    }

    /**
     * Checks if part ID is unique.
     * Used in generatePartId.
     * @param id Id to be checked
     * @return
     */
    private boolean isUniqueId(int id){
        Part u = Inventory.lookupPart(id);
        return u == null;
    }
}
