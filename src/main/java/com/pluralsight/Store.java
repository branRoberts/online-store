
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Students will complete the TODO sections to make the program work.
 */
public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: id|name|price)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    /**
     * Reads product data from a file and populates the inventory list.
     * File format (pipe-delimited):
     * id|name|price
     * <p>
     * Example line:
     * A17|Wireless Mouse|19.99
     */
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        // TODO: read each line, split on "|",
        //       create a Product object, and add it to the inventory list
        String line;
        try {

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\|");
                String id = values[0];
                String name = values[1];
                double price = Double.parseDouble(values[2]);
                inventory.add(new Product(id, name, price));

            }
            reader.close();
        }catch (Exception e) {
            System.err.println("Error loading file!");
        }

    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        // TODO: show each product (id, name, price),
        //       prompt for an id, find that product, add to cart
        boolean found = false;
        int countInventory = 0;
        while (countInventory < inventory.size()) {
            Product product = inventory.get(countInventory);
            System.out.println("Product ID: " + product.getId() + " Name: " + product.getName() + " Price: " + product.getPrice());
            countInventory++;
        }
       while (true) {
           System.out.println("Would you like to add an item to the cart? Enter C. If not enter X.");
           String choiceAddToCart = scanner.nextLine();
           if (choiceAddToCart.equalsIgnoreCase("X")) {
               return;
           }
           else if (choiceAddToCart.equalsIgnoreCase("C")) {

               System.out.print("Enter ID of product to add to cart:");
               String searchingForId = scanner.nextLine();
               for (int i = 0; i < inventory.size(); i++) {
                   if (inventory.get(i).getId().equalsIgnoreCase(searchingForId)) {
                       System.out.println("Name: " + inventory.get(i).getName() + " has been added to cart.");
                       cart.add(inventory.get(i));
                       found = true;
                   }
               }
                   if (!found)  {
                       System.out.println("Product with ID: " + searchingForId + " not found!");
                       return;
                   }

           } else {
               System.out.println("Invalid choice!");
           }

       }

    }

    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)
        System.out.println("Welcome to cart menu! \n");
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i);
            System.out.println("Product ID: " + product.getId() + " Name: " + product.getName() + " Price: " + product.getPrice());
        }
    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        // TODO: implement steps listed above
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        return null;
    }
}

