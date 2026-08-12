import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Admin> admins = new ArrayList<>();
    static ArrayList<Product> products = new ArrayList<>();

    static User loggedInUser = null;
    static Admin loggedInAdmin = null;

    public static void main(String[] args) {

        initializeProducts();

        // Default admin account
        admins.add(new Admin("admin", "admin123"));

        while (true) {

            System.out.println("\n======================================");
            System.out.println("        WELCOME TO PARAMESHWARI MART");
            System.out.println("======================================");
            System.out.println("1. User Registration");
            System.out.println("2. User Login");
            System.out.println("3. Admin Registration");
            System.out.println("4. Admin Login");
            System.out.println("5. Exit");
            System.out.println("======================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> userRegistration();
                case 2 -> userLogin();
                case 3 -> adminRegistration();
                case 4 -> adminLogin();
                case 5 -> {
                    System.out.println(
                        "\nThank you for visiting Parameshwari Mart!"
                    );
                    scanner.close();
                    return;
                }
                default -> System.out.println(
                    "Invalid choice. Please try again."
                );
            }
        }
    }

    static void initializeProducts() {
        products.add(new Product(1, "Rice 5kg", 350));
        products.add(new Product(2, "Wheat Flour 1kg", 60));
        products.add(new Product(3, "Sugar 1kg", 50));
        products.add(new Product(4, "Cooking Oil 1L", 150));
        products.add(new Product(5, "Milk 1L", 60));
        products.add(new Product(6, "Tea Powder 250g", 120));
        products.add(new Product(7, "Biscuits", 40));
        products.add(new Product(8, "Soap", 45));
        products.add(new Product(9, "Shampoo", 120));
        products.add(new Product(10, "Toothpaste", 90));
    }

    static void userRegistration() {

        System.out.println("\n========== USER REGISTRATION ==========");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }

        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }

        users.add(new User(username, password));

        System.out.println("Registration successful!");
    }

    static void userLogin() {

        System.out.println("\n============== USER LOGIN ==============");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(username)
                    && user.checkPassword(password)) {

                loggedInUser = user;

                System.out.println("\nLogin successful!");
                System.out.println(
                    "Welcome, " + user.getUsername() + "!"
                );

                userMenu();
                return;
            }
        }

        System.out.println("Invalid username or password.");
    }

    static void adminRegistration() {

        System.out.println("\n========== ADMIN REGISTRATION ==========");

        System.out.print("Enter admin username: ");
        String username = scanner.nextLine().trim();

        for (Admin admin : admins) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Admin username already exists.");
                return;
            }
        }

        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }

        admins.add(new Admin(username, password));

        System.out.println("Admin registration successful!");
    }

    static void adminLogin() {

        System.out.println("\n============== ADMIN LOGIN ==============");

        System.out.print("Enter admin username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        for (Admin admin : admins) {

            if (admin.getUsername().equalsIgnoreCase(username)
                    && admin.checkPassword(password)) {

                loggedInAdmin = admin;

                System.out.println("\nAdmin login successful!");
                System.out.println(
                    "Welcome Admin, " + admin.getUsername() + "!"
                );

                adminMenu();
                return;
            }
        }

        System.out.println("Invalid admin username or password.");
    }

    static void userMenu() {

        ArrayList<CartItem> cart = new ArrayList<>();

        while (true) {

            System.out.println("\n======================================");
            System.out.println("             USER MENU");
            System.out.println("======================================");
            System.out.println("1. Browse Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. Place Order");
            System.out.println("6. Logout");
            System.out.println("======================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> displayProducts();
                case 2 -> addToCart(cart);
                case 3 -> viewCart(cart);
                case 4 -> removeFromCart(cart);
                case 5 -> placeOrder(cart);
                case 6 -> {
                    loggedInUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void displayProducts() {

        System.out.println(
            "\n============== PRODUCT CATALOG =============="
        );

        System.out.printf(
            "%-5s %-25s %s%n",
            "ID", "PRODUCT", "PRICE"
        );

        System.out.println("----------------------------------------------");

        for (Product product : products) {
            product.displayProduct();
        }
    }

    static void addToCart(ArrayList<CartItem> cart) {

        displayProducts();

        int productId = readInt("\nEnter product ID: ");
        Product selectedProduct = findProduct(productId);

        if (selectedProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        int quantity = readInt("Enter quantity: ");

        if (quantity <= 0) {
            System.out.println(
                "Quantity must be greater than zero."
            );
            return;
        }

        for (CartItem item : cart) {

            if (item.getProduct().getId() == productId) {

                item.increaseQuantity(quantity);

                System.out.println(
                    "Product quantity updated in cart."
                );

                return;
            }
        }

        cart.add(new CartItem(selectedProduct, quantity));

        System.out.println(
            "Product added to cart successfully!"
        );
    }

    static void viewCart(ArrayList<CartItem> cart) {

        System.out.println(
            "\n================ YOUR CART ================"
        );

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.printf(
            "%-5s %-25s %-10s %s%n",
            "ID", "PRODUCT", "QUANTITY", "TOTAL"
        );

        System.out.println("--------------------------------------------");

        double grandTotal = 0;

        for (CartItem item : cart) {
            item.displayCartItem();
            grandTotal += item.getTotal();
        }

        System.out.println("--------------------------------------------");
        System.out.printf(
            "Grand Total: ₹%.2f%n",
            grandTotal
        );
    }

    static void removeFromCart(ArrayList<CartItem> cart) {

        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        viewCart(cart);

        int productId = readInt(
            "\nEnter product ID to remove: "
        );

        for (int i = 0; i < cart.size(); i++) {

            if (cart.get(i).getProduct().getId() == productId) {

                cart.remove(i);

                System.out.println(
                    "Product removed from cart successfully."
                );

                return;
            }
        }

        System.out.println("Product not found in cart.");
    }

    static void placeOrder(ArrayList<CartItem> cart) {

        if (cart.isEmpty()) {
            System.out.println(
                "Cannot place order. Your cart is empty."
            );
            return;
        }

        System.out.println(
            "\n============== ORDER SUMMARY =============="
        );

        double grandTotal = 0;

        for (CartItem item : cart) {
            item.displayCartItem();
            grandTotal += item.getTotal();
        }

        System.out.println("--------------------------------------------");

        System.out.printf(
            "Total Amount: ₹%.2f%n",
            grandTotal
        );

        System.out.print(
            "\nDo you want to confirm the order? (yes/no): "
        );

        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {

            System.out.println(
                "\n========== ORDER CONFIRMED! =========="
            );

            System.out.println(
                "Customer: " + loggedInUser.getUsername()
            );

            System.out.printf(
                "Order Total: ₹%.2f%n",
                grandTotal
            );

            System.out.println(
                "Thank you for shopping with us!"
            );

            cart.clear();

        } else {
            System.out.println("Order cancelled.");
        }
    }

    static void adminMenu() {

        while (true) {

            System.out.println("\n======================================");
            System.out.println("             ADMIN MENU");
            System.out.println("======================================");
            System.out.println("1. View Products");
            System.out.println("2. Add Product");
            System.out.println("3. Logout");
            System.out.println("======================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> displayProducts();
                case 2 -> addProduct();
                case 3 -> {
                    loggedInAdmin = null;
                    System.out.println(
                        "Admin logged out successfully."
                    );
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addProduct() {

        System.out.println("\n========== ADD PRODUCT ==========");

        int id = readInt("Enter product ID: ");

        if (findProduct(id) != null) {
            System.out.println("Product ID already exists.");
            return;
        }

        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println(
                "Product name cannot be empty."
            );
            return;
        }

        double price = readDouble("Enter product price: ");

        if (price <= 0) {
            System.out.println(
                "Price must be greater than zero."
            );
            return;
        }

        products.add(new Product(id, name, price));

        System.out.println("Product added successfully!");
    }

    static Product findProduct(int productId) {

        for (Product product : products) {

            if (product.getId() == productId) {
                return product;
            }
        }

        return null;
    }

    static int readInt(String message) {

        while (true) {

            try {
                System.out.print(message);

                return Integer.parseInt(
                    scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Please enter a valid number."
                );
            }
        }
    }

    static double readDouble(String message) {

        while (true) {

            try {
                System.out.print(message);

                return Double.parseDouble(
                    scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Please enter a valid price."
                );
            }
        }
    }
}