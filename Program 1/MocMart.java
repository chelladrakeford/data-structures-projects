// Chella Drakeford, 1314292, cdrakeford@mocs.flsouthern.edu, CSC3280 002, Program I: Moc Mart, 9/10/2026
//  “I will practice academic and personal integrity and excellence of character and expect the same from others"
import java.util.*;

public class MocMart {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int maxProducts = input.nextInt();
        int maxSales = input.nextInt();
        MocMartProduct[] products = new MocMartProduct[maxProducts];
        MocMartSale[] sales = new MocMartSale[maxSales];
        int numProducts = 0;
        int numSales = 0;
        String command = input.next();

        while (!command.equals("QUIT")) {
            // Inventory
            if (command.equals("INVENTORY")) {
                System.out.println("INVENTORY:");
                if (numProducts == 0) {
                    System.out.println("\tContains no items.");
                } else {
                    System.out.println("\tContains the following items:");
                    for (int i = 0; i < numProducts; i++) {
                        System.out.printf("\t| Item %6d | %-20s | $%7.2f | %4d unit(s) |%n", products[i].getItemNum(),
                                products[i].getItemName(), products[i].getItemPrice(), products[i].getQuantity());
                    }
                }
            }

            // Restock
            else if (command.equals("RESTOCK")) {
                System.out.println("RESTOCK:");
                boolean restock = false;
                for (int i = 0; i < numProducts; i++) {
                    if (products[i].getQuantity() == 0) {
                        products[i].setQuantity(products[i].getRestockQuantity());
                        restock = true;
                        System.out.printf("\tItem %d, %s, restocked to a quantity of %d.%n", products[i].getItemNum(),
                                products[i].getItemName(), products[i].getQuantity());
                    }
                }
                if (!restock) {
                    System.out.printf("\tThere are no items to restock.%n");
                }
            }

            // Find item
            else if (command.equals("FINDITEM")) {
                System.out.println("FINDITEM:");
                int itemNumber = input.nextInt();

                if (numProducts == 0) {
                    System.out.println("\tCannot perform command; there are no items in the product database.");
                } else {
                    int left = 0;
                    int right = numProducts - 1;
                    int foundIndex = -1;

                    System.out.print("\tPerforming Binary Search...(Indices viewed: ");
                    while (left <= right) {
                        int mid = (left + right) / 2;
                        System.out.print(mid + " ");

                        if (itemNumber > products[mid].getItemNum()) {
                            left = mid + 1;
                        } else if (itemNumber < products[mid].getItemNum()) {
                            right = mid - 1;
                        } else if (itemNumber == products[mid].getItemNum()) {
                            foundIndex = mid;
                            break;
                        }
                    }
                    System.out.println(")");
                    if (foundIndex == -1) {
                        System.out.printf("\tItem #%d was not found in the product database.%n", itemNumber);
                    } else {
                        System.out.printf("\tItem #%d (%s)%n", products[foundIndex].getItemNum(),
                                products[foundIndex].getItemName());
                        System.out.printf("\tPrice            :  $%.2f%n", products[foundIndex].getItemPrice());
                        System.out.printf("\tCurrent Quantity :  %d%n", products[foundIndex].getQuantity());
                        // how many units sold
                        int unitsSold = 0;
                        for (int i = 0; i < numSales; i++) {
                            for (int j = 0; j < sales[i].getNumItemsOnList(); j += 2) {
                                int soldItemNumber = sales[i].getItemsPurchased()[j];
                                int soldQuantity = sales[i].getItemsPurchased()[j + 1];
                                if (soldItemNumber == itemNumber) {
                                    unitsSold += soldQuantity;
                                }
                            }
                        }
                        double totalAmount = unitsSold * products[foundIndex].getItemPrice();
                        System.out.printf("\tUnits Sold       :  %d%n", unitsSold);
                        System.out.printf("\tTotal Amount     :  $%.2f%n", totalAmount);
                    }
                }
            }

            // Add item
            if (command.equals("ADDITEM")) {
                System.out.println("ADDITEM:");
                int itemNumber = input.nextInt();
                String itemName = input.next();
                double itemPrice = input.nextDouble();
                int quantity = input.nextInt();
                int restockQuantity = input.nextInt();
                MocMartProduct product = new MocMartProduct(itemNumber, itemName, itemPrice, quantity, restockQuantity);

                int insertIndex = 0;
                while (insertIndex < numProducts && products[insertIndex].getItemNum() < product.getItemNum()) {
                    insertIndex++;
                }
                for (int i = numProducts; i > insertIndex; i--) {
                    products[i] = products[i - 1];
                }
                products[insertIndex] = product;
                numProducts++;

                System.out.printf(
                        "\tItem %d, %s, with a cost of $%.2f and initial stock of %d, has been added to the product database.%n",
                        product.getItemNum(), product.getItemName(), product.getItemPrice(), product.getQuantity());
            }

            // Customer
            else if (command.equals("CUSTOMER")) {
                System.out.println("CUSTOMER:");
                String firstName = input.next();
                String lastName = input.next();
                int numItemsOnList = input.nextInt();
                int[] itemsPurchased = new int[numItemsOnList];

                for (int i = 0; i < numItemsOnList; i++) {
                    itemsPurchased[i] = input.nextInt();
                }
                boolean madePurchase = false;
                for (int i = 0; i < numItemsOnList; i += 2) {
                    int itemNumber = itemsPurchased[i];
                    int wanted = itemsPurchased[i + 1];
                    int foundIndex = -1;
                    int left = 0;
                    int right = numProducts - 1;

                    while (left <= right) {
                        int mid = (left + right) / 2;
                        if (itemNumber == products[mid].getItemNum()) {
                            foundIndex = mid;
                            break;
                        } else if (itemNumber < products[mid].getItemNum()) {
                            right = mid - 1;
                        } else {
                            left = mid + 1;
                        }
                    }
                    if (foundIndex != -1 && products[foundIndex].getQuantity() > 0) {
                        int purchased = wanted;
                        if (products[foundIndex].getQuantity() < wanted) {
                            purchased = products[foundIndex].getQuantity();
                        }
                        products[foundIndex].setQuantity(products[foundIndex].getQuantity() - purchased);
                        itemsPurchased[i + 1] = purchased;
                        madePurchase = true;
                    } else {
                        itemsPurchased[i + 1] = 0;
                    }
                }
                if (madePurchase) {
                    MocMartSale sale = new MocMartSale(firstName, lastName, numItemsOnList, itemsPurchased);
                    sales[numSales] = sale;
                    numSales++;
                    System.out.printf("\tCustomer %s %s came and made some purchases.%n", firstName, lastName);
                } else {
                    System.out.printf("\tCustomer %s %s came and made no purchases.%n", firstName, lastName);
                }
            }

            // Print sales summary
            else if (command.equals("PRINTSALESSUMMARY")) {
                System.out.println("PRINTSALESSUMMARY:");
                System.out.println("Moc Mart Sales Report:");
                double grandTotal = 0.0;
                for (int i = 0; i < numSales; i++) {
                    int totalItems = 0;
                    double saleTotal = 0.0;
                    for (int j = 0; j < sales[i].getNumItemsOnList(); j += 2) {
                        totalItems += sales[i].getItemsPurchased()[j + 1];
                    }
                    System.out.printf("\tSale #%d, %s %s purchased %d item(s):%n", i + 1, sales[i].getFirstName(),
                            sales[i].getLastName(), totalItems);
                    for (int j = 0; j < sales[i].getNumItemsOnList(); j += 2) {
                        if (sales[i].getItemsPurchased()[j + 1] > 0) {
                            int itemNumber = sales[i].getItemsPurchased()[j];
                            int left = 0;
                            int right = numProducts - 1;
                            int foundIndex = -1;

                            while (left <= right) {
                                int mid = (left + right) / 2;
                                if (itemNumber == products[mid].getItemNum()) {
                                    foundIndex = mid;
                                    break;
                                } else if (itemNumber < products[mid].getItemNum()) {
                                    right = mid - 1;
                                } else {
                                    left = mid + 1;
                                }
                            }
                            if (foundIndex != -1) {
                                saleTotal += products[foundIndex].getItemPrice() * sales[i].getItemsPurchased()[j + 1];
                                System.out.printf("\t\t| Item %6d | %-20s | $%7.2f (x%4d) |%n",
                                        products[foundIndex].getItemNum(), products[foundIndex].getItemName(),
                                        products[foundIndex].getItemPrice(), sales[i].getItemsPurchased()[j + 1]);
                            }
                        }
                    }
                    System.out.printf("\t\tTotal: $%.2f%n", saleTotal);
                    grandTotal += saleTotal;
                }
                System.out.printf("\tGrand Total: $%.2f%n", grandTotal);
            }
            command = input.next();
        }
        System.out.println("Goodbye.");
        input.close();
    }
}
