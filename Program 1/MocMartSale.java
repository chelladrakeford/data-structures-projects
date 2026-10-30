// Chella Drakeford, 1314292, cdrakeford@mocs.flsouthern.edu, CSC3280 002, Program I: Moc Mart, 9/10/2026

public class MocMartSale {
    // Fields
    private String firstName;
    private String lastName;
    private int numItemsOnList;
    private int[] itemsPurchased;
    private static int numSales;

    // Constructor
    public MocMartSale(String firstName, String lastName, int numItemsOnList, int[] itemsPurchased) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numItemsOnList = numItemsOnList;
        this.itemsPurchased = itemsPurchased;
        numSales++;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getNumItemsOnList() {
        return numItemsOnList;
    }

    public int[] getItemsPurchased() {
        return itemsPurchased;
    }

    public static int getNumSales() {
        return numSales;
    }

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumItemsOnList(int numItemsOnList) {
        this.numItemsOnList = numItemsOnList;
    }

    public void setItemsPurchased(int[] itemsPurchased) {
        this.itemsPurchased = itemsPurchased;
    }

    public static void setNumSales(int numSales) {
        MocMartSale.numSales = numSales;
    }
}
