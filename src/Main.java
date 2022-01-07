public class Main {

    public static void main(String[] args) {
        // Decision routes
        // String fileURI = "src/KnapsackInstances/Decision/NR/NR20_inst.dat";
        // String fileURI = "src/KnapsackInstances/Decision/ZR/ZR30_inst.dat";

        // Optimization routes
        // String fileURI = "src/KnapsackInstances/Optimization/NK/NK40_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/ZKC/ZKC40_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/ZKW/ZKW40_inst.dat";

        // HW03 Generated Instances
        String fileURI = "src/KnapsackInstances/Optimization/EXP/n20.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/C500.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/W100.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/m80.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/cuni.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/gh100.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/gl100.dat";

        // HW 1
        // getByBruteForce(fileURI);
        // getByBranchAndBound(fileURI);

        // HW 2
        // getByBranchAndBoundOptimized(fileURI);
        // getByHeuristic(fileURI);
        // getByHeuristicExtended(fileURI);
        // getByDynamicProgrammingCapacityDecompositionIterative(fileURI);
        // getByDynamicProgrammingCapacityDecompositionRecursive(fileURI);
        // getByDynamicProgrammingTotalCostDecompositionIterative(fileURI);
        // getByFPTAS(fileURI);

        // HW 3
        getByBruteForce(fileURI);
        // getByBranchAndBoundOptimized(fileURI);
        // getByDynamicProgrammingCapacityDecompositionIterative(fileURI);
        // getByDynamicProgrammingTotalCostDecompositionIterative(fileURI);
        // getByHeuristicExtended(fileURI);

    }

    public static void getByBruteForce(String fileUri) {
        BruteForce bruteForceSol = new BruteForce(fileUri);
        bruteForceSol.getSolutions();
    }

    public static void getByBranchAndBound(String fileUri) {
        BranchAndBound branchAndBoundSol = new BranchAndBound(fileUri);
        branchAndBoundSol.getSolutions();
    }

    public static void getByBranchAndBoundOptimized(String fileUri) {
        BranchAndBound branchAndBoundSol = new BranchAndBound(fileUri);
        branchAndBoundSol.getOptimizedSolutions();
    }

    private static void getByHeuristic(String fileUri) {
        Heuristic heuristicSol = new Heuristic(fileUri);
        heuristicSol.getSolutions();
    }

    private static void getByHeuristicExtended(String fileUri) {
        Heuristic heuristicSol = new Heuristic(fileUri);
        heuristicSol.getSolutionsExtended();
    }

    private static void getByDynamicProgrammingCapacityDecompositionRecursive(String fileUri) {
        DynamicProgramming dynamicProgrammingSol = new DynamicProgramming(fileUri);
        dynamicProgrammingSol.getSolutionsCapacityDecompositionRecursive();
    }

    private static void getByDynamicProgrammingCapacityDecompositionIterative(String fileUri) {
        DynamicProgramming dynamicProgrammingSol = new DynamicProgramming(fileUri);
        dynamicProgrammingSol.getSolutionsCapacityDecompositionIterative();
    }

    private static void getByDynamicProgrammingTotalCostDecompositionIterative(String fileUri) {
        DynamicProgramming dynamicProgrammingSol = new DynamicProgramming(fileUri);
        dynamicProgrammingSol.getSolutionsTotalCostDecompositionIterative();
    }

    private static void getByFPTAS(String fileUri) {
        FPTAS fptasSol = new FPTAS(fileUri);
        fptasSol.getSolutions();
    }
}