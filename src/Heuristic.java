import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Heuristic {
    public Timer timer;
    public KnapsackReader kReader;

    // Deserialized instances from file
    public ArrayList<KnapsackOptimizationInstance> knapsackOptimizationInstances;
    public ArrayList<KnapsackOptimumSolutionInstance> knapsackOptimumSolutionInstances;

    public Heuristic(String fileUri) {
        timer = new Timer();
        knapsackOptimizationInstances = new ArrayList<>();
        knapsackOptimumSolutionInstances = new ArrayList<>();

        kReader = new KnapsackReader(fileUri);
        kReader.deserializeKnapsackOptimizationInstances(knapsackOptimizationInstances);
        kReader.setFileUri(KnapsackUtils.getSolUri(fileUri));
        kReader.deserializeKnapsackOptimumSolutionInstance(knapsackOptimumSolutionInstances);
    }

    public void getSolutions() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();

            List<Integer> solution = solve(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(),
                    knapsackInstance.getC());

            // End timer
            timer.end();
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolution(solution);

            // Calc Ea (Relative error) & Ra (Performance guarantee)
            KnapsackUtils.calculateQualityMeasurements(knapsackInstance, knapsackOptimumSolutionInstances);

            System.out.println(knapsackInstance.computationInfoToString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    public void getSolutionsExtended() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();

            List<Integer> solution = solveExtended(knapsackInstance.getN(), knapsackInstance.getM(),
                    knapsackInstance.getW(), knapsackInstance.getC());

            // End timer
            timer.end();
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolution(solution);

            // Calc Ea (Relative error) & Ra (Performance guarantee)
            KnapsackUtils.calculateQualityMeasurements(knapsackInstance, knapsackOptimumSolutionInstances);

            System.out.println(knapsackInstance.computationInfoToString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    public static List<Integer> solve(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        HashMap<Integer, Float> ratios = new HashMap<Integer, Float>();
        // Get cost / weight ratios on a map with their original index
        for (int i = 0; i < n; i++) {
            ratios.put(i, (float) C.get(i) / W.get(i));
        }
        // Reverse order ratios Map
        HashMap<Integer, Float> ratiosOrdered = KnapsackUtils.reverseSortHashByValue(ratios);

        // Build optimizedAnswer
        int totalWeight = 0;
        List<Integer> optimumSolution = new ArrayList<>(Collections.nCopies(n, 0));
        for (Map.Entry<Integer, Float> ratio : ratiosOrdered.entrySet()) {
            // Check if totalWeight and indexed item weight don't surpass max weight (M)
            int indexedWeight = W.get(ratio.getKey());
            if (totalWeight + indexedWeight <= M) {
                // Add item to optimum answer (Build answer)
                optimumSolution.set(ratio.getKey(), 1);
                totalWeight += indexedWeight;
            }
        }
        return optimumSolution;
    }

    public static List<Integer> solveExtended(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        int possibleSolutionCost = 0;
        Integer possibleSolutionIndex = null;
        List<Integer> possibleSolution = new ArrayList<>(Collections.nCopies(n, 0));
        HashMap<Integer, Float> ratios = new HashMap<Integer, Float>();

        // Get cost / weight ratios on a map with their original index
        // Get item index with best Cost, not exceeding maxWeight (M)
        for (int i = 0; i < n; i++) {
            int currentItemCost = C.get(i);
            int currentItemWeight = W.get(i);

            ratios.put(i, (float) currentItemCost / currentItemWeight);

            if (currentItemCost > possibleSolutionCost && currentItemWeight <= M) {
                possibleSolutionIndex = i;
                possibleSolutionCost = currentItemCost;
            }
        }

        // Build possibleSolution with one item
        if (possibleSolutionIndex != null) {
            possibleSolution.set(possibleSolutionIndex, 1);
        }

        // Reverse order ratios Map
        HashMap<Integer, Float> ratiosOrdered = KnapsackUtils.reverseSortHashByValue(ratios);

        // Build optimizedAnswer
        int totalWeight, totalCost;
        totalWeight = totalCost = 0;
        List<Integer> optimumSolution = new ArrayList<>(Collections.nCopies(n, 0));
        for (Map.Entry<Integer, Float> ratio : ratiosOrdered.entrySet()) {
            // Check if totalWeight and indexed item weight don't surpass max weight (M)
            int indexedWeight = W.get(ratio.getKey());
            int indexedCost = C.get(ratio.getKey());
            if (totalWeight + indexedWeight <= M) {
                // Add item to optimum answer (Build answer)
                optimumSolution.set(ratio.getKey(), 1);
                totalWeight += indexedWeight;
                totalCost += indexedCost;
            }
        }

        // Compare possibleAnswer with built optimizedAnswer
        // return the answer with best cost
        return (totalCost > possibleSolutionCost ? optimumSolution : possibleSolution);
    }

}
