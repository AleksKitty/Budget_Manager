package budget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class SortAllOrCategory implements SortAlgorithm{
    @Override
    public void sort(HashMap<Categories, ArrayList<Purchase>> categoriesPurchases, HashMap<Categories, Double> sumCategoriesPurchases, Categories key) {
        ArrayList<Purchase> allList = categoriesPurchases.get(key);

        if (allList.size() != 0) {
            Collections.sort(allList);

            for (Purchase purchase : allList) {
                System.out.println(purchase.getName());
            }
        } else {
            System.out.println("Purchase list is empty!");
        }
    }
}
