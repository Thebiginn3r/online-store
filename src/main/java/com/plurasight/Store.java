package com.plurasight;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {
    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("products.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    inventory.add(new Product(id, name, price));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // This method should read a CSV file with product information and
        // populate the inventory ArrayList with com.pluralsight.Product objects. Each line
        // of the CSV file contains product information in the following format:
        //
        // id,name,price
        //
        // where id is a unique string identifier, name is the product name,
        // price is a double value representing the price of the product
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {
        if (inventory == null || inventory.isEmpty()) {
            System.out.println("No products to display.");
            return;
        }
        System.out.printf("%-8s %-34s %-10s%n", "ID", "Name", "Price");
        System.out.println("-------------------------------------------------------------------------------------------------------");

        for (Product product : inventory) {
            System.out.printf("%-8s %-34s %-10s%n", product.getId(), product.getName(), product.getPrice());
            //scanner.nextLine();
        }
        System.out.println();
        String addCartId = "";

        System.out.println("Would you like to add an item to your cart?(y/n)");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("y")) {
            System.out.println("Enter the id of the product you would like to add to the cart: ");
            addCartId = scanner.nextLine();

            boolean found = false;
            for (Product product : inventory) {
                if (product.getId().equalsIgnoreCase(addCartId)){
                    cart.add(product);
                    System.out.println("Product added to cart: " + product.getName());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("No product matches ID " + addCartId);
            }
        } else {
            System.out.println("No product added to cart");
            return;
        }

    }

        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.


    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        double totalCost = 0;
        boolean removed = false;

        if (cart == null || cart.isEmpty()) {
            System.out.println("No products to display.");
            return;
        }
        System.out.printf("%-8s %-34s %-10s%n", "ID", "Name", "Price");
        System.out.println("-------------------------------------------------------------------------------------------------------");

        for (Product product : cart) {
            System.out.printf("%-8s %-34s %-10s%n", product.getId(), product.getName(), product.getPrice());
            totalCost += product.getPrice();
        }
        System.out.printf("Your car total is: %.2f\n" , totalCost);

        System.out.println("Would you like to remove an item from your cart?(y/n)");
        String remove = scanner.nextLine();

        if(remove.equalsIgnoreCase("y")) {
            System.out.print("Enter the ID of the product you would like to remove: ");
            String idToRemove = scanner.nextLine();

            for (int i = 0; i < cart.size(); i++) {
                Product product = cart.get(i);
                if (product.getId().equalsIgnoreCase(idToRemove)) {
                    cart.remove(i);
                    removed = true;
                    System.out.println("Item removed");
                    break;
                }
            }
            if(!removed) {
                System.out.println("Item not found");
            }
        }

        System.out.println("Would you like to check out? (y/n)");
        String proceed = scanner.nextLine();
        if (proceed.equalsIgnoreCase("y")) {
            checkOut(cart,totalCost);
        }
        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {
        Scanner scanner = new Scanner(System.in);
        double totalCost = 0;
        for (Product product : cart) {
            System.out.printf("%-8s %-34s %-10s%n", product.getId(), product.getName(), product.getPrice());
            totalCost += product.getPrice();
        }
        System.out.printf("Your car total is: %.2f\n" , totalCost);
        for (int i = 0; i < cart.size(); i++) {
            if (cart.size() == 1) {
                System.out.println("You have 1 item in your cart for a total of " + totalCost );
            }else{
                System.out.println("You have " + cart.size() + "items in your cart for a total of " + totalCost);
            }
        }
        System.out.println("Are these the right items and correct total? (y/n)");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("y")) {
            System.out.println("Your order has been confirmed");
            cart.clear();
            return;
        }
        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and calculate change and clear the cart
        // if they confirm.
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        boolean found;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the product ID you would like to search for: ");
        id = scanner.nextLine();


        for (Product product : inventory) {
            if (product.getId().equalsIgnoreCase(id)) {
                System.out.print("Product found: ");
                System.out.printf("ID: %s\nName: %s\nPrice: %.2f\n", product.getId(), product.getName(), product.getPrice());
                return product;
            }
        }
        System.out.println("Product not found.");
        return null;
    }

        //This method should search the inventory ArrayList for a product with
        // the specified ID, and return the corresponding com.pluralsight.Product object. If
        // no product with the specified ID is found, the method should return
        // null.
}




