
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Store {

    public static void main(String[] args) {


        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();


        loadInventory("products.csv", inventory);


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
                scanner.nextLine();
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {

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

    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {

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
               Product foundProduct = findProductById(searchingForId, inventory);
               if (foundProduct == null) {
                   System.out.println("Product with ID: " + searchingForId + " not found!");
                   return;
               } else {
                   cart.add(foundProduct);
                   System.out.println("Product: "+ foundProduct.getName() + " has been added!");
               }
           } else {
               System.out.println("Invalid choice!");
           }

       }

    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        double totalAmount = 0;
        System.out.println("Welcome to cart menu! \n");
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i);
            System.out.println("Product ID: " + product.getId() + " Name: " + product.getName() + " Price: " + product.getPrice());
            totalAmount += product.getPrice();
        }
        System.out.println("Total price: " + totalAmount);

        while (true) {
            System.out.println("Would you like to check out? If yes enter C. If not enter X.");
            String choiceCheckOut = scanner.nextLine();
            if (choiceCheckOut.equalsIgnoreCase("X")) {
                return;
            }
            else if (choiceCheckOut.equalsIgnoreCase("C")) {
                checkOut(cart,totalAmount,scanner);
                return;
            }
            else {
                System.out.println("Invalid choice!");
            }
        }
    }
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        // TODO: implement steps listed above
        System.out.println("Are you sure you want to check out this cart? C for yes. X for no. ");
        String choiceCheckOutConfirm = scanner.nextLine();
        if (choiceCheckOutConfirm.equalsIgnoreCase("X")) {
            cart.clear();
            System.out.println("Cart cleared!\n");
            System.out.println("Thank you for shopping with us!");
        } else if (choiceCheckOutConfirm.equalsIgnoreCase("C")) {
            while (true) {
                System.out.println("Enter your amount:");
                double customerAmount = scanner.nextDouble();
                if (customerAmount <= 0 ) {
                    System.out.println("Invalid amount!");
                    continue;
                }
                totalAmount -= customerAmount;
                totalAmount = Math.round(totalAmount * 100.0) / 100.0;
                    if (totalAmount <= 0) {
                        double change = Math.abs(totalAmount);
                        System.out.println("Here's your Receipt!\n");
                        for (Product product : cart) {
                            System.out.println(" Name: " + product.getName() + " Price: " + product.getPrice());
                        }
                        if (change > 0){
                            System.out.println("-------------------------------------------");
                            System.out.printf("Your change is $%.2f!%n", change);
                        }
                        System.out.println("------------------------------------------------");
                        System.out.println("Thank you for shopping with us!");
                        cart.clear();
                        return;
                    } else  {
                        System.out.printf("You have $%.2f left.%n", totalAmount);
                    }
            }
        }
    }
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getId().equalsIgnoreCase(id)) {
                return inventory.get(i);
            }
        }
        System.out.println("Product with ID: " + id + " not found!");
        return null;
    }
}

