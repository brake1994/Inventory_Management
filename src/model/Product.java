package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class used to create product objects
 */
public class Product {
    /**
     * List of associated Parts of the product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Product ID
     */
    private int productId;
    /**
     * Product Name
     */
    private String productName;
    /**
     * Product Price
     */
    private double productPrice;
    /**
     * Product Inventory/stock on hand
     */
    private int productStock;
    /**
     * Product minimum inventory
     */
    private int productMin;
    /**
     * Product maximum inventory
     */
    private int productMax;

    /**
     * Constructor for product class.
     * Used to create product objects
     * @param id product ID
     * @param name product Name
     * @param price product Price
     * @param stock Product Inventory
     * @param min Product Min inventory
     * @param max Product max inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.productId = id;
        this.productName = name;
        this.productPrice = price;
        this.productStock = stock;
        this.productMin = min;
        this.productMax = max;
    }

    /**
     * Get product Id
     * @return ID
     */
    public int getProductId() {
        return this.productId;
    }

    /**
     * Set product Id
     * @param id ID
     */
    public void setProductId(int id) {
        this.productId = id;
    }

    /**
     * Get product name
     * @return name
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * Set product name
     * @param name Name
     */
    public void setProductName(String name) {
        this.productName = name;
    }

    /**
     * Get product price
     * @return price
     */
    public double getProductPrice() {
        return this.productPrice;
    }

    /**
     * Set product price
     * @param price price
     */
    public void setProductPrice(double price) {
        this.productPrice = price;
    }

    /**
     * Get inventory/stock of product
     * @return inventory value
     */
    public int getProductStock() {
        return this.productStock;
    }

    /**
     * Set inventory/stock of product
     * @param stock Inventory/stock
     */
    public void setProductStock(int stock) {
        this.productStock = stock;
    }

    /**
     * Getter for min inventory of product
     * @return Min inventory value
     */
    public int getProductMin() {
        return this.productMin;
    }

    /**
     * Set min inventory for product
     * @param min Min inventory value
     */
    public void setProductMin(int min) {
        this.productMin = min;
    }

    /**
     * Getter for product max inventory
     * @return Max inventory value
     */
    public int getProductMax() {
        return this.productMax;
    }

    /**
     * Set max inventory of product
     * @param max Max inventory value
     */
    public void setProductMax(int max) {
        this.productMax = max;
    }

    /**
     * Adds an associated part to a list.
     * @param part Part to be added.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Deleted an associated part from the list
     * @param selectedAssociatedPart Part to be deleted.
     * @return Boolean value. True if deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){

        for( int i = 0; i < associatedParts.size(); i++){
           if(associatedParts.get(i) == selectedAssociatedPart){
               associatedParts.remove(i);
               return true;
           }
        }

        return false;
    }

    /**
     * Returns all associated parts the product has.
     * @return associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts(){

        return associatedParts;
    }

    /**
     * Empty constructor used in add product controller.
     */
    public Product(){

    }
}
