import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class KnapsackUtils {

    // Private constructor to prevent instantiation
    private KnapsackUtils() {
        throw new UnsupportedOperationException();
    }

    // public static methods

    public static KnapsackOptimumSolutionInstance findKnapsackOptimumSolutionInstance(
            ArrayList<KnapsackOptimumSolutionInstance> knapsackOptimumSolutionInstances, int id) {
        for (KnapsackOptimumSolutionInstance kInstance : knapsackOptimumSolutionInstances) {
            if (kInstance.getId() == id) {
                return kInstance;
            }
        }
        return null;
    }

    public static void calculateQualityMeasurements(KnapsackOptimizationInstance knapsackOptimizationInstance,
            ArrayList<KnapsackOptimumSolutionInstance> knapsackOptimumSolutionInstances) {
        int calculatedSolCost = knapsackOptimizationInstance.calculateSolutionCost();
        KnapsackOptimumSolutionInstance knapsackOptimumSolutionInstance = findKnapsackOptimumSolutionInstance(
                knapsackOptimumSolutionInstances, knapsackOptimizationInstance.getId());

        if (knapsackOptimumSolutionInstance != null) {
            knapsackOptimizationInstance.calcRelativeErrorMaximization(
                    knapsackOptimumSolutionInstance.getSolutionCost(), calculatedSolCost);
            knapsackOptimizationInstance.calcPerformanceGuaranteeMaximization(
                    knapsackOptimumSolutionInstance.getSolutionCost(), calculatedSolCost);
        }
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

    public static String getSolUri(String fileUri) {
        return fileUri.replace("_inst.dat", "_sol.dat");
    }
}