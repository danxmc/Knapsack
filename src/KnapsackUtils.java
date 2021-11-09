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

    public static List<Integer> buildAnswerFromDPTable(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C, int[][] T) {
        // Initialize solution List with appropriate size and only zeros
        List<Integer> solution = new ArrayList<Integer>(Collections.nCopies(n, 0));

        // Step 1: Starting from i = n, j = M.
        // Step 2: Look in column j, up from bottom, you find the line i such that
        // T[i][j] > T[i – 1][j]. Mark selected item i: Select [i] = true;
        // Step 3: j = T[i][j] – M[i]. If j > 0, go to step 2, otherwise go to step 4
        // Step 4: Based on the table of options to print the selected packages.
        while (n != 0) {
            // If T[i][j] = T[i – 1][j], the current item is not selected.
            if (T[n][M] != T[n - 1][M]) {
                solution.set(n - 1, 1);
                M = M - W.get(n - 1);
            }
            n--;
        }
        return solution;
    }
}