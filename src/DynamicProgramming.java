import java.util.ArrayList;
import java.util.List;

public class DynamicProgramming {
    public Timer timer;
    public KnapsackReader kReader;
    public int[][] dpTable;

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

            solveByCapacityDecompositionRecursive(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(),
                    knapsackInstance.getC());

            List<Integer> solution = KnapsackUtils.buildAnswerFromDPTable(knapsackInstance.getN(), knapsackInstance.getM(),
                    knapsackInstance.getW(), knapsackInstance.getC(), dpTable);

            // System.out.println(Arrays.deepToString(dpTable));
            // System.out.println(solution.toString());
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

    public void getSolutionsCapacityDecompositionIterative() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();

            solveByCapacityDecompositionIterative(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(),
                    knapsackInstance.getC());

            List<Integer> solution = KnapsackUtils.buildAnswerFromDPTable(knapsackInstance.getN(), knapsackInstance.getM(),
                    knapsackInstance.getW(), knapsackInstance.getC(), dpTable);

            // System.out.println(Arrays.deepToString(dpTable));
            // System.out.println(solution.toString());
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

    public void getSolutionsCostDecomposition() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();

            List<Integer> solution = solveByWeightDecomposition(knapsackInstance.getN(), knapsackInstance.getM(),
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
        dpTable[n][M] = valueWithItem > valueWithoutItem ? valueWithItem : valueWithoutItem;

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
                dpTable[i][j] = valueWithItem > valueWithoutItem ? valueWithItem : valueWithoutItem;
            }
        }

        // for (int i = 1; i <= n; i++) {
        //     for (int j = 1; j <= M; j++) {
        //         if (W.get(i - 1) > j) {
        //             dpTable[i][j] = dpTable[i - 1][j];
        //         } else {
        //             if (C.get(i - 1) + dpTable[i - 1][j - W.get(i - 1)] > dpTable[i - 1][j]) {
        //                 dpTable[i][j] = C.get(i - 1) + dpTable[i - 1][j - W.get(i - 1)];
        //             } else {
        //                 dpTable[i][j] = dpTable[i - 1][j];
        //             }
        //         }
        //     }
        // }

        return dpTable[n][M];
    }

    private List<Integer> solveByWeightDecomposition(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        return new ArrayList<>();
    }

}
