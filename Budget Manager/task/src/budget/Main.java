package budget;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Budget myBudget = new Budget();

        int userAction = -1;

        while (userAction != 0) {
            printActions();
            userAction = scanner.nextInt();
            System.out.println();

            switch (userAction) {
                case 1: {
                    myBudget.addIncome();
                    break;
                }
                case 2: {
                    chooseCategoryAdd(myBudget);
                    break;
                }
                case 3: {
                    chooseCategoryShow(myBudget);
                    break;
                }
                case 4: {
                    myBudget.printBalance();
                    break;
                }
                case 5: {
                    myBudget.saveToFile();
                    break;
                }
                case 6: {
                    myBudget.loadFromFile();
                    break;
                }
                case 7: {
                    chooseSort(myBudget);
                    break;
                }
                case 0: {
                    System.out.print("Bye!");
                    break;
                }
            }
        }
    }

    private static void printActions() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }

    private static void chooseCategoryAdd(Budget myBudget) {

        int userAction = -1;

        final int endAction = 5;

        while (userAction != endAction) {

            printCategories(false);

            userAction = scanner.nextInt();
            System.out.println();

            switch (userAction) {
                case 1: {
                    myBudget.addPurchase(Categories.FOOD);
                    break;
                }
                case 2: {
                    myBudget.addPurchase(Categories.CLOTHES);
                    break;
                }
                case 3: {
                    myBudget.addPurchase(Categories.ENTERTAINMENT);
                    break;
                }
                case 4: {
                    myBudget.addPurchase(Categories.OTHER);
                    break;
                }
                case endAction: {
                    break;
                }
            }

            if (userAction != endAction) {
                System.out.println();
            }
        }
    }

    private static void chooseCategoryShow(Budget myBudget) {

        if (!myBudget.isEmptyPurchaseList()) {

            int userAction = -1;

            final int endAction = 6;

            while (userAction != endAction) {

                printCategories(true);

                userAction = scanner.nextInt();
                System.out.println();

                switch (userAction) {
                    case 1: {
                        myBudget.showPurchases(Categories.FOOD);
                        break;
                    }
                    case 2: {
                        myBudget.showPurchases(Categories.CLOTHES);
                        break;
                    }
                    case 3: {
                        myBudget.showPurchases(Categories.ENTERTAINMENT);
                        break;
                    }
                    case 4: {
                        myBudget.showPurchases(Categories.OTHER);
                        break;
                    }
                    case 5: {
                        myBudget.showPurchases(Categories.ALL);
                        break;
                    }
                }

                if (userAction != endAction) {
                    System.out.println();
                }
            }
        } else {
            System.out.println("Purchase list is empty");
        }
    }

    private static void chooseSort(Budget myBudget) {

        int userAction = -1;

        final int endAction = 4;

        while (userAction != endAction) {

            printSorts();

            userAction = scanner.nextInt();
            System.out.println();

            ContextAlgorithm contextAlgorithm = new ContextAlgorithm();

            switch (userAction) {
                case 1: {
                    contextAlgorithm.setAlgorithm(new SortAllOrCategory());
                    contextAlgorithm.doAlgorithm(myBudget.getCategoriesPurchases(), myBudget.getSumCategoriesPurchases(), Categories.ALL);
                    break;
                }
                case 2: {
                    contextAlgorithm.setAlgorithm(new SortByType());
                    contextAlgorithm.doAlgorithm(myBudget.getCategoriesPurchases(), myBudget.getSumCategoriesPurchases(), Categories.ALL);
                    break;
                }
                case 3: {
                    printPurchasesTypes();

                    int type = scanner.nextInt();
                    System.out.println();

                    Categories key = typeSort(type, myBudget);

                    contextAlgorithm.setAlgorithm(new SortAllOrCategory());
                    contextAlgorithm.doAlgorithm(myBudget.getCategoriesPurchases(), myBudget.getSumCategoriesPurchases(), key);
                    break;
                }
                case endAction: {
                    break;
                }
            }

            if (userAction != endAction) {
                System.out.println();
            }
        }
    }

    private static Categories typeSort(int type, Budget myBudget) {
        switch (type) {
            case 1: {
                return Categories.FOOD;
            }
            case 2: {
                return Categories.CLOTHES;
            }
            case 3: {
                return Categories.ENTERTAINMENT;
            }
            default: {
                return Categories.OTHER;
            }
        }
    }

    private static void printCategories(boolean all) {
        printPurchasesTypes();
        if (all) {
            System.out.println("5) All");
            System.out.println("6) Back");
        } else {
            System.out.println("5) Back");
        }
    }

    private static void printSorts() {
        System.out.println("How do you want to sort?");
        System.out.println("1) Sort all purchases");
        System.out.println("2) Sort by type");
        System.out.println("3) Sort certain type");
        System.out.println("4) Back");
    }

    private static void printPurchasesTypes() {
        System.out.println("Choose the type of purchases:");
        System.out.println("1) Food");
        System.out.println("2) Clothes");
        System.out.println("3) Entertainment");
        System.out.println("4) Other");
    }
}
