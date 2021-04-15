package model;

/**
 * Class used to create OutsourcedPart objects
 * Inherits from Part class
 */
public class OutsourcedPart extends Part {
    /**
     * Name of company part is purchased from
     */
    private String companyName;

    /**
     * Constructor for OutsourcedPart
     * @param id Part id
     * @param name part name
     * @param price part price
     * @param stock part inventory/stock
     * @param min part min inventory
     * @param max part max inventory
     * @param companyName Name of company part is purchased from
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for company name
     * @return companyName
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Setter for company name
     * @param companyName Name to be set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}