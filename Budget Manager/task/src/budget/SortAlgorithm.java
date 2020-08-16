package budget;

import java.util.ArrayList;
import java.util.HashMap;

interface SortAlgorithm {
    void sort(HashMap<Categories, ArrayList<Purchase>> categoriesPurchases, HashMap<Categories, Double> sumCategoriesPurchases, Categories key);
}
