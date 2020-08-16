package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Budget {
    private static final Scanner scanner = new Scanner(System.in);


    private final HashMap<Categories, ArrayList<Purchase>> categoriesPurchases = new HashMap<>();

    // + incomes; - purchases
    private double balance = 0;

    // + purchases
    private final HashMap<Categories, Double> sumCategoriesPurchases = new HashMap<>();

    {
        categoriesPurchases.put(Categories.FOOD, new ArrayList<>());
        categoriesPurchases.put(Categories.CLOTHES, new ArrayList<>());
        categoriesPurchases.put(Categories.ENTERTAINMENT, new ArrayList<>());
        categoriesPurchases.put(Categories.OTHER, new ArrayList<>());
        categoriesPurchases.put(Categories.ALL, new ArrayList<>());

        sumCategoriesPurchases.put(Categories.FOOD, 0.0);
        sumCategoriesPurchases.put(Categories.CLOTHES, 0.0);
        sumCategoriesPurchases.put(Categories.ENTERTAINMENT, 0.0);
        sumCategoriesPurchases.put(Categories.OTHER, 0.0);
        sumCategoriesPurchases.put(Categories.ALL, 0.0);
    }

    public HashMap<Categories, ArrayList<Purchase>> getCategoriesPurchases() {
        return categoriesPurchases;
    }

    public HashMap<Categories, Double> getSumCategoriesPurchases() {
        return sumCategoriesPurchases;
    }

    public boolean isEmptyPurchaseList() {
        return categoriesPurchases.get(Categories.ALL).size() == 0;
    }

    protected void printBalance() {
        System.out.println("Balance: $" + String.format("%.2f", balance));
        System.out.println();
    }

    protected void showPurchases(Categories key) {
        ArrayList<Purchase> purchases = categoriesPurchases.get(key);

        System.out.println(key + ":");
        if (purchases.size() != 0) {

            for (Purchase purchase : purchases) {
                System.out.println(purchase.getName());
            }

            System.out.println("Total sum: $" + String.format("%.2f",sumCategoriesPurchases.get(key)).replace(',', '.'));

        } else {
            System.out.println("Purchase list is empty");
        }
    }

    protected void addIncome() {
        System.out.println("Enter income:");
        double income = Double.parseDouble(scanner.nextLine());

        if (income > 0) {
            balance += income;
            System.out.println("Income was added!");
        } else {
            System.out.println("Income can't be less than zero!");
        }

        System.out.println();
    }

    protected void addPurchase(Categories value) {
        System.out.println("Enter purchase name:");
        String purchaseName = scanner.nextLine();

        System.out.println("Enter its price:");
        double purchasePrice = Double.parseDouble(scanner.nextLine());

        balance -= purchasePrice;
        checkBalance();


        String purchaseString = purchaseName + " $" + String.format("%.2f", purchasePrice).replace(',', '.');

        addPurchaseToCategory(value, purchaseString, purchasePrice);

        System.out.println("Purchase was added!");
    }

    protected void addPurchaseToCategory(Categories key, String purchaseString, double purchasePrice) {
        // to key
        ArrayList<Purchase> purchasesCategory = categoriesPurchases.get(key);
        purchasesCategory.add(new Purchase(purchaseString, purchasePrice));
        categoriesPurchases.replace(key, purchasesCategory);

        // to ALL
        ArrayList<Purchase> purchasesAll = categoriesPurchases.get(Categories.ALL);
        purchasesAll.add(new Purchase(purchaseString, purchasePrice));
        categoriesPurchases.replace(Categories.ALL, purchasesAll);

        sumCategoriesPurchases.replace(key, sumCategoriesPurchases.get(key) + purchasePrice);
        sumCategoriesPurchases.replace(Categories.ALL, sumCategoriesPurchases.get(Categories.ALL) + purchasePrice);
    }

    private void checkBalance() {
        if (balance < 0) {
            balance = 0;
        }
    }


    protected void saveToFile() {
        String pathToFile = "purchases.txt";

        File file = new File(pathToFile);

        try {
            boolean createdNew  = file.createNewFile();

            if (!createdNew) {
                System.out.println("The file already exists.");
            }
        } catch (IOException e) {
            System.out.println("Cannot create the file: " + file.getPath());
        }


        ArrayList<Purchase> purchases = categoriesPurchases.get(Categories.ALL);
        if (purchases.size() != 0) {

            try (PrintWriter printWriter = new PrintWriter(file)) {

                for (Categories key : Categories.values()) {

                    purchases = categoriesPurchases.get(key);

                    if (key != Categories.ALL && purchases.size() != 0) {
                        printWriter.println(key + ":");
                        for (Purchase purchase : purchases) {
                            printWriter.println(purchase.getName());
                        }
                    }
                }

                printWriter.println("Balance: $" + String.format("%.2f", balance).replace(',', '.'));

                System.out.println("Purchases were saved!\n");

            } catch (IOException e) {
                System.out.printf("An exception occurs %s", e.getMessage());
            }
        } else {
            System.out.println("Purchase list is empty\n");
        }

    }

    protected void loadFromFile() {
        String pathToFile = "purchases.txt";

        File file = new File(pathToFile);

        try (Scanner scanner = new Scanner(file)) {

            String line;

            Categories key = null;

            // to Category
            ArrayList<Purchase> purchasesCategory = null;

            // to ALL
            ArrayList<Purchase> purchasesAll = categoriesPurchases.get(Categories.ALL);


            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                Categories tmp;
                if ((tmp = startCategory(line)) != null) {
                    key = tmp;
                    purchasesCategory = categoriesPurchases.get(key);

                } else if (key != null && purchasesCategory != null) {

                    double purchasePrice = Double.parseDouble(line.substring(line.lastIndexOf('$') + 1));

                    if (!line.contains("Balance")) {
                        // to Category
                        purchasesCategory.add(new Purchase(line, purchasePrice));
                        categoriesPurchases.replace(key, purchasesCategory);

                        // to All
                        purchasesAll.add(new Purchase(line, purchasePrice));
                        categoriesPurchases.replace(Categories.ALL, purchasesAll);


                        sumCategoriesPurchases.replace(key, sumCategoriesPurchases.get(key) + purchasePrice);
                        sumCategoriesPurchases.replace(Categories.ALL, sumCategoriesPurchases.get(Categories.ALL) + purchasePrice);
                    } else {

                        balance = purchasePrice;
                    }
                }

            }

            System.out.println("Purchases were loaded!\n");


        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + pathToFile);
        }
    }

    private Categories startCategory(String line) {
        Categories result = null;

        for (Categories key : Categories.values()) {
            if (line.contains(key + ":") && key != Categories.ALL) {
                result = key;
                break;
            }
        }

        return result;
    }
}
