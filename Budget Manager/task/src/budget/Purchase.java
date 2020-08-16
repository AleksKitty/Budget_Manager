package budget;

import org.jetbrains.annotations.NotNull;

class Purchase implements Comparable<Purchase> {
    private final String purchaseName;
    private final double purchasePrice;

    public Purchase(String purchaseName, double purchasePrice) {
        this.purchaseName = purchaseName;
        this.purchasePrice = purchasePrice;
    }

    public String getName() {
        return purchaseName;
    }

    public double getPrice() {
        return purchasePrice;
    }


    @Override
    public int compareTo(@NotNull Purchase o) {
        return Double.compare(o.getPrice(), this.getPrice());
    }
}
