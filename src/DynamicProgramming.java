import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicProgramming {
    public Timer timer;
    public KnapsackReader kReader;
    public static int[][] dpTable;

    // Deserialized instances from file
    public ArrayList<KnapsackOptimizationInstance> knapsackOptimizationInstances;
    public ArrayList<KnapsackOptimumSolutionInstance> knapsackOptimumSolutionInstances;

    public DynamicProgramming(String fileUri) {
        timer = new Timer();
        knapsackOptimizationInstances = new ArrayList<>();
        knapsackOptimumSolutionInstances = new ArrayList<>();

        kReader = new KnapsackReader(fileUri);
        kReader.deserializeKnapsackOptimizationInstances(knapsackOptimizationInstances);
        kReader.setFileUri(KnapsackUtils.getSolUri(fileUri));
        kReader.deserializeKnapsackOptimumSolutionInstance(knapsackOptimumSolutionInstances);
    }

    public void getSolutionsCapacityDecompositionRecursive() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();

            // Reset dpTable
            dpTable = new int[knapsackInstance.getN() + 1][knapsackInstance.getM() + 1];

            solveByCapacityDecompositionRecursive(knapsackInstance.getN(), knapsackInstance.getM(),
                    knapsackInstance.getW(), knapsackInstance.getC());

            List<Integer> solution = KnapsackUtils.buildAnswerByCapacityFromDPTable(knapsackInstance.getN(),
                    knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC(), dpTable);

            // End timer
            timer.end();
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolution(solution);

            // Calc Ea (Relative error) & Ra (Performance guarantee)
            KnapsackUtils.calculateQualityMeasurements(knapsackInstance, knapsackOptimumSolutionInstances);

            System.out.println(knapsackInstance.computationInfoToString());
            // System.out.println(knapsackInstance.solutionInfoToString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    public void getSolutionsCapacityDecompositionIterative() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();

            solveByCapacityDecompositionIterative(knapsackInstance.getN(), knapsackInstance.getM(),
                    knapsackInstance.getW(), knapsackInstance.getC());

            List<Integer> solution = KnapsackUtils.buildAnswerByCapacityFromDPTable(knapsackInstance.getN(),
                    knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC(), dpTable);

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

    public void getSolutionsTotalCostDecompositionIterative() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();

            List<Integer> solution = solveByTotalCostDecompositionIterative(knapsackInstance.getN(),
                    knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC());

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

    public void getSolutionsTotalCostDecompositionRecursive() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();

            List<Integer> solution = solveByTotalCostDecompositionIterative(knapsackInstance.getN(),
                    knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC());

            // End timer
            timer.end();
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolution(solution);

            // Calc Ea (Relative error) & Ra (Performance guarantee)
            KnapsackUtils.calculateQualityMeasurements(knapsackInstance, knapsackOptimumSolutionInstances);

            System.out.println(knapsackInstance.computationInfoToString() + " " + solution.toString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    private int solveByCapacityDecompositionRecursive(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        // If it's a trivial solution
        if (n <= 0 || M <= 0) {
            return 0;
        }

        // If we have already computed the answer
        if (dpTable[n][M] != 0) {
            return dpTable[n][M];
        }

        // Recursively take/not take items

        // Take item n if it's less than MaxWeight
        int valueWithItem = 0;
        if (W.get(n - 1) <= M) {
            // Calc current item value + possible next item
            // Subtract current item counter & item Weight from MaxWeight
            valueWithItem = C.get(n - 1) + solveByCapacityDecompositionRecursive(n - 1, M - W.get(n - 1), W, C);
        }

        // Don't take item
        int valueWithoutItem = solveByCapacityDecompositionRecursive(n - 1, M, W, C);

        // Store maxValue solution on the global table
        dpTable[n][M] = Math.max(valueWithItem, valueWithoutItem);

        return dpTable[n][M];
    }

    private int solveByCapacityDecompositionIterative(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        // Create array containing zeros
        dpTable = new int[n + 1][M + 1];

        // Roam the array from left-right, top-bottom
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= M; j++) {
                // Take the current item if it's less than currentWeight
                int valueWithItem = 0;
                if (W.get(i - 1) <= j) {
                    valueWithItem = C.get(i - 1) + dpTable[i - 1][j - W.get(i - 1)];
                }

                // Don't take item
                int valueWithoutItem = dpTable[i - 1][j];

                // Store the more valuable solution on the global table
                dpTable[i][j] = Math.max(valueWithItem, valueWithoutItem);
            }
        }

        return dpTable[n][M];
    }

    public static List<Integer> solveByTotalCostDecompositionIterative(int n, int M, ArrayList<Integer> W,
            ArrayList<Integer> C) {
        // Get the sum of costs
        int sumC = C.stream().mapToInt(Integer::intValue).sum();

        // Setup array
        dpTable = new int[n + 1][sumC + 1];
        Arrays.fill(dpTable[0], 1000000000);
        dpTable[0][0] = 0;
        int[][] memS = new int[n + 1][sumC + 1];

        for (int i = 1; i <= n; i++) {
            // Fill next row with 'infinites' or items not taken
            for (int j = 0; j <= sumC; j++) {
                dpTable[i][j] = dpTable[i - 1][j];
                memS[i][j] = 0;
            }
            for (int j = C.get(i - 1); j <= sumC; j++) {
                int minWeight = Math.min(M, dpTable[i - 1][j]);
                // Store value if it's less than the minWeight
                if (dpTable[i - 1][j - C.get(i - 1)] + W.get(i - 1) <= minWeight) {
                    // Take item
                    dpTable[i][j] = dpTable[i - 1][j - C.get(i - 1)] + W.get(i - 1);
                    memS[i][j] = 1;
                }
            }
        }

        List<Integer> S = KnapsackUtils.buildAnswerByTotalCostFromDPTableAndMemory(n, M, W, C, dpTable, memS);
        return S;
    }

}
