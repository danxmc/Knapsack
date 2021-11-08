import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
        kReader.setFileUri(fileUri.replace("_inst.dat", "_sol.dat"));
        kReader.deserializeKnapsackOptimumSolutionInstance(knapsackOptimumSolutionInstances);
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

    public KnapsackOptimumSolutionInstance findKnapsackOptimumSolutionInstance(int id) {
        for (KnapsackOptimumSolutionInstance kInstance : knapsackOptimumSolutionInstances) {
            if (kInstance.getId() == id) {
                return kInstance;
            }
        }
        return null;
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
            int calculatedSolCost = knapsackInstance.calculateSolutionCost();
            KnapsackOptimumSolutionInstance knapsackOptimumSolution = findKnapsackOptimumSolutionInstance(
                    knapsackInstance.getId());
            if (knapsackOptimumSolution != null) {
                knapsackInstance.calcRelativeErrorMaximization(knapsackOptimumSolution.getSolutionCost(),
                        calculatedSolCost);
                knapsackInstance.calcPerformanceGuaranteeMaximization(knapsackOptimumSolution.getSolutionCost(),
                        calculatedSolCost);
            }

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
            int calculatedSolCost = knapsackInstance.calculateSolutionCost();
            KnapsackOptimumSolutionInstance knapsackOptimumSolution = findKnapsackOptimumSolutionInstance(
                    knapsackInstance.getId());
            if (knapsackOptimumSolution != null) {
                knapsackInstance.calcRelativeErrorMaximization(knapsackOptimumSolution.getSolutionCost(),
                        calculatedSolCost);
                knapsackInstance.calcPerformanceGuaranteeMaximization(knapsackOptimumSolution.getSolutionCost(),
                        calculatedSolCost);
            }
            
            System.out.println(knapsackInstance.computationInfoToString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    private List<Integer> solve(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        HashMap<Integer, Float> ratios = new HashMap<Integer, Float>();
        // Get cost / weight ratios on a map with their original index
        for (int i = 0; i < n; i++) {
            ratios.put(i, (float) C.get(i) / W.get(i));
        }
        // Reverse order ratios Map
        HashMap<Integer, Float> ratiosOrdered = reverseSortHashByValue(ratios);

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

    private List<Integer> solveExtended(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
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
        HashMap<Integer, Float> ratiosOrdered = reverseSortHashByValue(ratios);

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
