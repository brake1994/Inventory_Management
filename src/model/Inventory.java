package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

/**
 * Inventory Class used to store part/product objects and manipulate objects
 */
public class Inventory {
    /**
     * List of all parts in inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * List of all products in inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds part to inventory
     * @param part Part to be added
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Adds product to inventory
     * @param product Product to be added
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Search for parts by the part IDs
     * @param partId Part searched
     * @return Part found or null
     */
    public static Part lookupPart(int partId) {
        Iterator var1 = getAllParts().iterator();

        Part q;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            q = (Part)var1.next();
        } while(q.getId() != partId);

        return q;
    }

    /**
     * Search for products by product IDs
     * @param productId Product searched
     * @return product found or null
     */
    public static Product lookupProduct(int productId) {
        Iterator var1 = getAllProducts().iterator();

        Product q;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            q = (Product)var1.next();
        } while(q.getProductId() != productId);

        return q;
    }

    /**
     * Search for parts by part names
     * @param partName Part searched
     * @return List of parts found
     */
    public static ObservableList<Part> lookupPart(String partName){

        if(!allParts.isEmpty()){
            ObservableList<Part> searchPartList = FXCollections.observableArrayList();

            for(Part q: getAllParts()){
                if(q.getName().equals(partName)){
                    searchPartList.add(q);
                }
                else{
                    if(q.getName().contains(partName)){
                        if(!searchPartList.contains(q)) {
                            searchPartList.add(q);
                        }
                    }
                }
            }
            return searchPartList;
        }
        return null;
    }

    /**
     * Search for products by product names
     * @param productName Product search
     * @return List of products found
     */
    public static ObservableList<Product> lookupProduct(String productName){

        if(!allProducts.isEmpty()){
            ObservableList<Product> searchProductList = FXCollections.observableArrayList();

            for(Product q: getAllProducts()){
                if(q.getProductName().equals(productName)){
                    searchProductList.add(q);
                }
                else{
                    if(q.getProductName().contains(productName)){
                        if(!searchProductList.contains(q)) {
                            searchProductList.add(q);
                        }
                    }
                }

            }
            return searchProductList;
        }

        return null;
    }

    /**
     * Updates a part in inventory.
     * The method deletes the old part and then adds the new part
     * @param index Index of old part
     * @param selectedPart New part
     */
    public static void updatePart(int index, Part selectedPart){
        deletePart(allParts.get(index));
        addPart(selectedPart);

    }

    /**
     * Updates a product in inventory.
     * The method deletes the old product and then adds the new product.
     * @param index Index of old product
     * @param newProduct New product
     */
    public static void updateProduct(int index, Product newProduct){
        deleteProduct(allProducts.get(index));
        addProduct(newProduct);
    }

    /**
     * Deletes selected part.
     * @param selectedPart Part
     */
    public static void deletePart(Part selectedPart){
            if (selectedPart != null){
               allParts.remove(selectedPart);
            }
    }

    /**
     * Deletes selected product.
     * @param selectedProduct Product
     */
    public static void deleteProduct(Product selectedProduct){
            if(selectedProduct != null && selectedProduct.getAllAssociatedParts().isEmpty()){
                allProducts.remove(selectedProduct);
            }
    }

    /**
     * Method used to return all parts in the inventory.
     * @return All parts in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Method used to return all products in the inventory.
     * @return All products in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
