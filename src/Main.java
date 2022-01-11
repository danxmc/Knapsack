public class Main {

    public static void main(String[] args) {
        // Decision routes
        // String fileURI = "src/KnapsackInstances/Decision/NR/NR20_inst.dat";
        // String fileURI = "src/KnapsackInstances/Decision/ZR/ZR30_inst.dat";

        // Optimization routes
        String fileURI = "src/KnapsackInstances/Optimization/NK/NK50_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/ZKC/ZKC40_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/ZKW/ZKW40_inst.dat";

        // HW03 Generated Instances
        // String strFile = "n15Perm";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/" + strFile + "_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/C500_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/W100_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/m80_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/cuni_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/gh100_inst.dat";
        // String fileURI = "src/KnapsackInstances/Optimization/EXP/gl100_inst.dat";

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
        // System.out.println("BruteForce");
        // System.out.println(strFile);
        // System.out.println("id Complexity Time(ms)");
        // getByBruteForce(fileURI);
        // System.out.println("");
        // System.out.println("BranchAndBound");
        // System.out.println(strFile);
        // System.out.println("id Rel.Error P.Guarantee Time(ms)");
        // getByBranchAndBoundOptimized(fileURI);
        // System.out.println("");
        // System.out.println("DPbyTotalCostDecomposition");
        // System.out.println(strFile);
        // System.out.println("id Rel.Error P.Guarantee Time(ms)");
        // getByDynamicProgrammingTotalCostDecompositionIterative(fileURI);
        // System.out.println("");
        // System.out.println("DPbyCapacityDecomposition");
        // System.out.println(strFile);
        // System.out.println("id Rel.Error P.Guarantee Time(ms)");
        // getByDynamicProgrammingCapacityDecompositionIterative(fileURI);
        // System.out.println("");
        // System.out.println("Heuristic");
        // System.out.println(strFile);
        // System.out.println("id Rel.Error P.Guarantee Time(ms)");
        // getByHeuristicExtended(fileURI);

        // HW 4
        System.out.println("Ti Tf Alpha	Max.Iter. Rel.E. Time(ms) TotalCost");
        for (int i = 0; i < 100; i++) {
            getBySimulatedAnnealing(fileURI);
        }
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

    private static void getBySimulatedAnnealing(String fileUri) {
        SimulatedAnnealing simulatedAnnealingSol = new SimulatedAnnealing(fileUri);
        simulatedAnnealingSol.getSolutions();
    }
}