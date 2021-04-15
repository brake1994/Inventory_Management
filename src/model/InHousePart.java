package model;

/**
 * This class is used to create InHousePart objects.
 * Inherits from Part class.
 */
public class InHousePart extends Part {
    /**
     * ID number of machine used to manufacture part in-house
     */
    private int machineId;

    /**
     * Constructor of InHousePart
     * All values except machineId inherited from Part class
     * @param id Part ID
     * @param name Part name
     * @param price Part price
     * @param stock Part inventory/stock on hand
     * @param min Part min inventory
     * @param max Part max inventory
     * @param machineId ID number of machine
     */
    public InHousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter for machine id
     * @return machineId
     */
    public int getMachineId() {
        return this.machineId;
    }

    /**
     * Setter for machine id
     * @param machineId machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
