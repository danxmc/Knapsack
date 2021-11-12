public class Main {

    public static void main(String[] args) {
        // Decision routes
        // String fileURI = "src/KnapsackInstances/Decision/NR/NR4_inst.dat";
        // String fileURI = "src/KnapsackInstances/Decision/ZR/ZR30_inst.dat";

        // Optimization routes
        String fileURI = "src/KnapsackInstances/Optimization/NK/NK4_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/ZKC/ZKC40_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/ZKW/ZKW40_inst.dat";

        // HW 1
        // getByBruteForce(fileURI);
        // getByBranchAndBound(fileURI);

        // HW 2
        // getByBranchAndBoundOptimized(fileURI);
        // getByHeuristic(fileURI);
        // getByHeuristicExtended(fileURI);
        // getByDynamicProgrammingCapacityDecompositionRecursive(fileURI);
        // getByDynamicProgrammingCapacityDecompositionIterative(fileURI);
        getByDynamicProgrammingTotalCostDecompositionIterative(fileURI);
        getByFPTAS(fileURI);
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