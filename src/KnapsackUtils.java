import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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

    public static List<Integer> buildAnswerByCapacityFromDPTable(int n, int M, ArrayList<Integer> W,
            ArrayList<Integer> C, int[][] T) {
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
                M -= W.get(n - 1);
            }
            n--;
        }
        return solution;
    }

    public static void print2dArray(int[][] array) {
        System.out.println(Arrays.deepToString(array).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }

    public static int getMaxValue2d(int[][] numbers) {
        int maxValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {
            for (int i = 0; i < numbers[j].length; i++) {
                if (numbers[j][i] > maxValue && numbers[j][i] < 1000000000) {
                    maxValue = numbers[j][i];
                }
            }
        }
        return maxValue;
    }

    public static int getMaxValueMinWeightIndex(int[] arrayC, int M) {
        int maxValue = arrayC[0];
        int index = 0;
        for (int i = 0; i < arrayC.length; i++) {
            if (arrayC[i] <= M || arrayC[i] >= maxValue && arrayC[i] < 1000000000) {
                maxValue = arrayC[i];
                index = i;
            }
        }

        return index;
    }

    public static List<Integer> buildAnswerByTotalCostFromDPTableAndMemory(int n, int M, ArrayList<Integer> W,
            ArrayList<Integer> C, int[][] T, int[][] memory) {
        // Initialize solution List with appropriate size and only zeros
        List<Integer> solution = new ArrayList<Integer>(Collections.nCopies(n, 0));

        int i = getMaxValueMinWeightIndex(T[n], M);
        for (int j = n; j >= 1; j--) {
            if (memory[j][i] == 1) {
                solution.set(j - 1, 1);
                i -= C.get(j - 1);
            }
        }

        return solution;
    }

    public static List<Integer> generateLocalConstructiveSolution(int n, int M, ArrayList<Integer> W,
            ArrayList<Integer> C) {
        // List<Integer> solution = Heuristic.solve(n, M, W, C);
        List<Integer> solution = Heuristic.solveExtended(n, M, W, C);
        return solution;
    }

    public static List<Integer> generateRandomValidSolution(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        List<Integer> solution = new ArrayList<>();
        boolean isValidSol = false;
        while (!isValidSol) {
            solution = generateRandomSolution(n, M, W, C);
            int solutionWeight = 0;
            for (int i = 0; i < n; i++) {
                int currentItemWeight = W.get(i);
                solutionWeight += currentItemWeight * solution.get(i);
            }
            if (solutionWeight <= M) {
                isValidSol = true;
            }
        }
        return solution;
    }

    public static List<Integer> generateRandomSolution(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        List<Integer> solution = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // Get random num between 0 - 1
            int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
            solution.add(randomNum);
        }
        return solution;
    }

    public static int getSolutionCost(int n, ArrayList<Integer> C, List<Integer> solution) {
        int solutionCost = 0;
        for (int i = 0; i < n; i++) {
            int currentItemCost = C.get(i);
            int currentItemDecision = solution.get(i);
            solutionCost += currentItemCost * currentItemDecision;
        }
        return solutionCost;
    }

    public static int getSolutionWeight(int n, ArrayList<Integer> W, List<Integer> solution) {
        int solutionWeight = 0;
        for (int i = 0; i < n; i++) {
            int currentItemWeight = W.get(i);
            solutionWeight += currentItemWeight * solution.get(i);
        }
        return solutionWeight;
    }
}