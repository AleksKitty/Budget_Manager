package budget;

import java.util.ArrayList;
import java.util.HashMap;

class ContextAlgorithm {
    private SortAlgorithm algorithm;

    void setAlgorithm(SortAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    void doAlgorithm(HashMap<Categories, ArrayList<Purchase>> categoriesPurchases, HashMap<Categories, Double> sumCategoriesPurchases, Categories key) {
        this.algorithm.sort(categoriesPurchases, sumCategoriesPurchases, key);
    }
}
