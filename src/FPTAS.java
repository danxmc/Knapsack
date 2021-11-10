import java.util.ArrayList;
import java.util.List;

public class FPTAS {
    public Timer timer;
    public KnapsackReader kReader;
    public int[][] dpTable;

    // Deserialized instances from file
    public ArrayList<KnapsackOptimizationInstance> knapsackOptimizationInstances;
    public ArrayList<KnapsackOptimumSolutionInstance> knapsackOptimumSolutionInstances;

    public FPTAS(String fileUri) {
        timer = new Timer();
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

            solve(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(), knapsackInstance.getC());

            List<Integer> solution = KnapsackUtils.buildAnswerFromDPTable(knapsackInstance.getN(),
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

    private void solve(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
    }
}
