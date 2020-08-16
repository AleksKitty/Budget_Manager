package budget;

import java.util.*;

public class SortByType implements SortAlgorithm {
    @Override
    public void sort(HashMap<Categories, ArrayList<Purchase>> categoriesPurchases, HashMap<Categories, Double> sumCategoriesPurchases, Categories key) {

        HashMap<Categories, Double> map = new HashMap<>(sumCategoriesPurchases);
        map.remove(Categories.ALL);

        System.out.println("Types:");
        map.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> System.out.println(k.getKey().toString().toCharArray()[0] + k.getKey().toString().toLowerCase().substring(1) + " - $" + String.format("%.2f", k.getValue()).replace(',' ,'.')));

    }
}


