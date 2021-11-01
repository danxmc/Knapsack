import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Heuristic {
    public KnapsackReader kReader;
    public List<Integer> optimumSolution;

    // Deserialized instances from file
    public ArrayList<KnapsackOptimizationInstance> knapsackOptimizationInstances;

    public Heuristic(String fileUri) {
        knapsackOptimizationInstances = new ArrayList<>();
        kReader = new KnapsackReader(fileUri);
        kReader.deserializeKnapsackOptimizationInstances(knapsackOptimizationInstances);
    }

    // function to sort hashmap by values
    public static HashMap<Integer, Float> reverseSortHashByValue(HashMap<Integer, Float> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, Float>> list = new LinkedList<Map.Entry<Integer, Float>>(hm.entrySet());

        // Sort the list using lambda expression
        Collections.sort(list, (i1, i2) -> i2.getValue().compareTo(i1.getValue()));

        // put data from sorted list to hashmap
        HashMap<Integer, Float> temp = new LinkedHashMap<Integer, Float>();
        for (Map.Entry<Integer, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void getSolutions() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Best possible solution reset
            optimumSolution = new ArrayList<>(Collections.nCopies(knapsackInstance.getN(), 0));

            solve(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC());

            knapsackInstance.setSolution(optimumSolution);

            // System.out.println(knapsackInstance.computationInfoToString());
            System.out.println(knapsackInstance.toString());
        });
    }

    private void solve(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        HashMap<Integer, Float> ratios = new HashMap<Integer, Float>();
        // Get cost / weight ratios on a map with their original index
        for (int i = 0; i < n; i++) {
            ratios.put(i, (float) C.get(i) / W.get(i));
        }
        // Reverse order ratios Map
        HashMap<Integer, Float> ratiosOrdered = reverseSortHashByValue(ratios);
        System.out.println(ratiosOrdered);

        // Build optimizedAnswer
        int totalWeight = 0;
        for (Map.Entry<Integer, Float> ratio : ratiosOrdered.entrySet()) {
            // Check if totalWeight and indexed item weight don't surpass max weight (M)
            int indexedWeight = W.get(ratio.getKey());
            if (totalWeight + indexedWeight <= M) {
                // Add item to optimum answer (Build answer)
                optimumSolution.set(ratio.getKey(), 1);
                totalWeight += indexedWeight;
            }
        }
        System.out.println(optimumSolution);
    }

}
