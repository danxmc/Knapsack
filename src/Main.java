public class Main {

    public static void main(String[] args) {
        // Decision routes
        // String fileURI = "src/KnapsackInstances/Decision/NR/NR4_inst.dat";
        // String fileURI = "src/KnapsackInstances/Decision/ZR/ZR30_inst.dat";

        // Optimization routes
        String fileURI = "src/KnapsackInstances/Optimization/NK/NK40_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/ZKC/ZKC4_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/ZKW/ZKW4_inst.dat";

        // HW 1
        // getByBruteForce(fileURI);
        // getByBranchAndBound(fileURI);

        // HW 2
        // getByBranchAndBoundOptimized(fileURI);
        // getByHeuristic(fileURI);
        // getByHeuristicExtended(fileURI);
        getByDynamicProgrammingCostDecomposition(fileURI);
        getByDynamicProgrammingWeightDecomposition(fileURI);
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

    private static void getByDynamicProgrammingWeightDecomposition(String fileUri) {
        DynamicProgramming dynamicProgrammingSol = new DynamicProgramming(fileUri);
        dynamicProgrammingSol.getSolutionsWeightDecomposition();
    }

    private static void getByDynamicProgrammingCostDecomposition(String fileUri) {
        DynamicProgramming dynamicProgrammingSol = new DynamicProgramming(fileUri);
        dynamicProgrammingSol.getSolutionsCostDecomposition();
    }
}