import java.util.*;

class Item {
    private String name;
    private int code;
    private double price;

    public Item(String name, int code, double price) {
        this.name = name;
        this.code = code;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }
}

public class OnlineShoppingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Creating electronic gadgets items
        List<Item> items = new ArrayList<>();
        items.add(new Item("Laptop", 101, 1000.0));
        items.add(new Item("Smartphone", 102, 500.0));
        items.add(new Item("Headphones", 103, 50.0));
        items.add(new Item("Tablet", 104, 300.0));
        items.add(new Item("Smartwatch", 105, 200.0));
        items.add(new Item("Wireless Mouse", 106, 30.0));
        items.add(new Item("Bluetooth Speaker", 107, 80.0));
        items.add(new Item("Gaming Console", 108, 400.0));
        items.add(new Item("E-Reader", 109, 150.0));
        items.add(new Item("Fitness Tracker", 110, 100.0));
        items.add(new Item("Digital Camera", 111, 250.0));
        items.add(new Item("Drone", 112, 600.0));
        items.add(new Item("VR Headset", 113, 300.0));
        items.add(new Item("External Hard Drive", 114, 120.0));
        items.add(new Item("Power Bank", 115, 40.0));
        items.add(new Item("Wireless Earbuds", 116, 70.0));
        items.add(new Item("Mini Projector", 117, 150.0));
        items.add(new Item("Smart Home Hub", 118, 200.0));
        items.add(new Item("Digital Photo Frame", 119, 80.0));
        items.add(new Item("Compact Binoculars", 120, 50.0));
        items.add(new Item("USB-C Docking Station", 121, 100.0));
        items.add(new Item("Solar Power Bank", 122, 60.0));
        items.add(new Item("Smart Doorbell", 123, 150.0));
        items.add(new Item("Portable Bluetooth Keyboard", 124, 50.0));
        items.add(new Item("Car Dash Cam", 125, 80.0));
        items.add(new Item("Wireless Charging Pad", 126, 30.0));
        items.add(new Item("Compact Digital Scale", 127, 20.0));
        items.add(new Item("Smart Plugs", 128, 25.0));
        items.add(new Item("Raspberry Pi Starter Kit", 129, 70.0));
        items.add(new Item("Wireless Presenter Remote", 130, 40.0));
        items.add(new Item("LED Desk Lamp", 131, 35.0));

        List<Item> cart = new ArrayList<>();

        System.out.println("Welcome to Online Shopping System!");
        System.out.println("Items available:");

        // Display available items
        for (Item item : items) {
            System.out.println(item.getCode() + ". " + item.getName() + " - $" + item.getPrice());
        }

        boolean continueShopping = true;
        while (continueShopping) {
            System.out.println("\n1. Buy item");
            System.out.println("2. Add item to cart");
            System.out.println("3. View cart");
            System.out.println("4. Confirm order");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter item code to buy: ");
                    int buyCode = scanner.nextInt();
                    for (Item item : items) {
                        if (item.getCode() == buyCode) {
                            System.out.println("You bought: " + item.getName() + " - $" + item.getPrice());
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter item code to add to cart: ");
                    int cartCode = scanner.nextInt();
                    for (Item item : items) {
                        if (item.getCode() == cartCode) {
                            cart.add(item);
                            System.out.println(item.getName() + " added to cart.");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Items in cart:");
                    double total = 0;
                    for (Item item : cart) {
                        System.out.println(item.getName() + " - $" + item.getPrice());
                        total += item.getPrice();
                    }
                    System.out.println("Total price: $" + total);
                    break;
                case 4:
                    System.out.println("Order confirmed!");
                    continueShopping = false;
                    break;
                case 5:
                    System.out.println("Exiting...");
                    continueShopping = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
}