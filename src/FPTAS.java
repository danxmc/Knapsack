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
        // Epsilon has to be >= 1
        epsilon = .1;
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

            List<Integer> solution = solve(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC());

            // List<Integer> solution = KnapsackUtils.buildAnswerByTotalCostFromDPTableAndMemory(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(), cScaled,
            //         DynamicProgramming.dpTable);

            // End timer
            timer.end();
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolution(solution);

            // Calc Ea (Relative error) & Ra (Performance guarantee)
            KnapsackUtils.calculateQualityMeasurements(knapsackInstance,
            knapsackOptimumSolutionInstances);

            
            // System.out.println(knapsackInstance.getM() + " " + knapsackInstance.getC()+ " " + knapsackInstance.getW() );
            System.out.println(knapsackInstance.computationInfoToString()+ " " + solution.toString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    private List<Integer> solve(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        int maxCost = Collections.max(C);
        // Set scaling factor
        double K = (epsilon * maxCost) / n;

        // New scaled cost values
        cScaled = new ArrayList<>();

        for (Integer cost : C) {
            Integer newCost = (int) (Math.floor(cost / K));
            cScaled.add(newCost);
        }
        List<Integer> solution = DynamicProgramming.solveByTotalCostDecompositionIterative(n, M, W, cScaled);
        return solution;
    }
}
