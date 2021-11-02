import java.util.ArrayList;
import java.util.List;

public class DynamicProgramming {
    public KnapsackReader kReader;

    // Deserialized instances from file
    public ArrayList<KnapsackOptimizationInstance> knapsackOptimizationInstances;

    public DynamicProgramming(String fileUri) {
        knapsackOptimizationInstances = new ArrayList<>();
        kReader = new KnapsackReader(fileUri);
        kReader.deserializeKnapsackOptimizationInstances(knapsackOptimizationInstances);
    }

    public void getSolutionsWeightDecomposition() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            List<Integer> solution = solveByCostDecomposition(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC());

            knapsackInstance.setSolution(solution);

            // System.out.println(knapsackInstance.computationInfoToString());
            System.out.println(knapsackInstance.toString());
        });
    }

    public void getSolutionsCostDecomposition() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            List<Integer> solution = solveByWeightDecomposition(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC());

            knapsackInstance.setSolution(solution);

            // System.out.println(knapsackInstance.computationInfoToString());
            System.out.println(knapsackInstance.toString());
        });
    }

    private List<Integer> solveByCostDecomposition(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        return new ArrayList<>();
    }

    private List<Integer> solveByWeightDecomposition(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        return new ArrayList<>();
    }

}
