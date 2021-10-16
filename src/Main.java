public class Main {

    public static void main(String[] args) {
        String fileURI = "src/KnapsackInstances/Decision/NR/NR4_inst.dat";
        // String fileURI = "src/KnapsackInstances/Decision/ZR/ZR30_inst.dat";
        
        // HW 1
        // getByBruteForce(fileURI);
        // getByBranchAndBound(fileURI);

        // HW 2
    }

    public static void getByBruteForce(String fileUri) {
        BruteForce bruteForceSol = new BruteForce(fileUri);
        bruteForceSol.getSolutions();
    }

    public static void getByBranchAndBound(String fileUri) {
        BranchAndBound branchAndBoundSol = new BranchAndBound(fileUri);
        branchAndBoundSol.getSolutions();
    }
}