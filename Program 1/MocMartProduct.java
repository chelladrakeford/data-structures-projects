// Chella Drakeford, 1314292, cdrakeford@mocs.flsouthern.edu, CSC3280 002, Program I: Moc Mart, 9/10/2026
//  “I will practice academic and personal integrity and excellence of character and expect the same from others"

public class MocMartProduct {

    // Fields: what product knows
    private int itemNum;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private int restockQuantity;
    private static int numProducts;

    // Constructor: how product is created
    public MocMartProduct(int itemNum, String itemName, double itemPrice, int quantity, int restockQuantity) {
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.restockQuantity = restockQuantity;
        numProducts++;
    }

    // Getters: how classes read its info
    public int getItemNum() {
        return itemNum;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRestockQuantity() {
        return restockQuantity;
    }

    public static int getNumProducts() {
        return numProducts;
    }

    // Setters: how classes modify its info
    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setRestockQuantity(int restockQuantity) {
        this.restockQuantity = restockQuantity;
    }

    public static void setNumProducts(int numProducts) {
        MocMartProduct.numProducts = numProducts;
    }
}
