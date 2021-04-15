package model;

/**
 * Provided Part class.
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor used by InHousePart and OutsourcedPart
     * @param id Part ID
     * @param name part name
     * @param price part price
     * @param stock part inventory
     * @param min part min inventory
     * @param max part max inventory
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Get part id
     * @return part id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set Part id
     * @param id part id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get part name
     * @return part name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set part name
     * @param name part name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get part price
     * @return part price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Set part price
     * @param price part price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get part inventory on hand
     * @return stock
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Set part inventory on hard
     * @param stock stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get min part inventory
     * @return min inventory value
     */
    public int getMin() {
        return this.min;
    }

    /**
     * Set min part inventory
     * @param min min inventory value
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get part max inventory
     * @return max inventory value
     */
    public int getMax() {
        return this.max;
    }

    /**
     * Set part max inventory
     * @param max max inventory value
     */
    public void setMax(int max) {
        this.max = max;
    }
}
