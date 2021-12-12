import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FPTAS {
    public Timer timer;
    public KnapsackReader kReader;
    public double epsilon;
    public int[][] dpTable;
    public ArrayList<Integer> cScaled;

    // Deserialized instances from file
    public ArrayList<KnapsackOptimizationInstance> knapsackOptimizationInstances;
    public ArrayList<KnapsackOptimumSolutionInstance> knapsackOptimumSolutionInstances;

    public FPTAS(String fileUri) {
        timer = new Timer();
        // Modify epsilon for accuracy on answers
        // Epsilon has to be <= 1
        epsilon = .5;
        knapsackOptimizationInstances = new ArrayList<>();
        knapsackOptimumSolutionInstances = new ArrayList<>();

        kReader = new KnapsackReader(fileUri);
        kReader.deserializeKnapsackOptimizationInstances(knapsackOptimizationInstances);
        kReader.setFileUri(KnapsackUtils.getSolUri(fileUri));
        kReader.deserializeKnapsackOptimumSolutionInstance(knapsackOptimumSolutionInstances);
    }

    public void getSolutions() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU Time
            timer.start();

            List<Integer> solution = solve(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(),
                    knapsackInstance.getC());

            // List<Integer> solution =
            // KnapsackUtils.buildAnswerByTotalCostFromDPTableAndMemory(knapsackInstance.getN(),
            // knapsackInstance.getM(), knapsackInstance.getW(), cScaled,
            // DynamicProgramming.dpTable);

            // End timer
            timer.end();
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolution(solution);

            // Calc Ea (Relative error) & Ra (Performance guarantee)
            KnapsackUtils.calculateQualityMeasurements(knapsackInstance,
                    knapsackOptimumSolutionInstances);

            // System.out.println(knapsackInstance.getM() + " " + knapsackInstance.getC()+ "
            // " + knapsackInstance.getW() );
            System.out.println(knapsackInstance.computationInfoToString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    private List<Integer> solve(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        // Remove items where weight is over the max. Capacity
        List<List<Integer>> removedItemsList = new ArrayList<>();
        for (int i = 0; i < W.size(); i++) {
            if (W.get(i) > M) {
                n--;
                Integer wToRemove = W.remove(i);
                Integer cToRemove = C.remove(i);
                List<Integer> removedItem = new ArrayList<Integer>();
                removedItem.add(i);
                removedItem.add(wToRemove);
                removedItem.add(cToRemove);
                removedItemsList.add(removedItem);
                i--;
            }
        }

        int maxCost = 0;
        if (C.size() > 0) {
            maxCost = Collections.max(C);
        }
        // Set scaling factor
        double K = (epsilon * maxCost) / n;

        // New scaled cost values
        cScaled = new ArrayList<>();

        for (Integer cost : C) {
            Integer newCost = (int) (Math.floor(cost / K));
            cScaled.add(newCost);
        }
        List<Integer> solution = DynamicProgramming.solveByTotalCostDecompositionIterative(n, M, W, cScaled);
        // 'Add' the removed elements at the beginning as not added in the solution
        for (List<Integer> removedItem : removedItemsList) {
            n++;
            solution.add(removedItem.get(0), 0);
            W.add(removedItem.get(0), removedItem.get(1));
            C.add(removedItem.get(0), removedItem.get(2));
        }
        return solution;
    }
}
