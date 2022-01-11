import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimulatedAnnealing {
    public Timer timer;
    public KnapsackReader kReader;
    private static double ALPHA = 0.95;
    private static int MAX_ITER = 3;
    private static final double INITIAL_TEMPERATURE = 10000;
    private static final double FINAL_TEMPERATURE = 0.00001;
    private List<Integer> bestFoundSol = new ArrayList<>();

    // Deserialized instances from file
    public ArrayList<KnapsackOptimizationInstance> knapsackOptimizationInstances;
    public ArrayList<KnapsackOptimumSolutionInstance> knapsackOptimumSolutionInstances;

    public SimulatedAnnealing(String fileUri) {
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
            bestFoundSol = Collections.nCopies(knapsackInstance.getN(), 0);
            // Measure CPU Time
            timer.start();

            List<Integer> solution = solve(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getW(),
                    knapsackInstance.getC());

            // End timer
            timer.end();
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolution(solution);

            // Calc Ea (Relative error) & Ra (Performance guarantee)
            KnapsackUtils.calculateQualityMeasurements(knapsackInstance,
                    knapsackOptimumSolutionInstances);

            System.out.println(INITIAL_TEMPERATURE + " " + FINAL_TEMPERATURE + " " + ALPHA + " " + MAX_ITER + " " + knapsackInstance.getRelativeError() + " " + knapsackInstance.getTime() + " " + knapsackInstance.getSolutionCost());
            // System.out.println(knapsackInstance.computationInfoToString());
            // System.out.println(knapsackInstance.solutionInfoToString());
            // System.out.println(
            //         knapsackInstance.solutionInfoToString() + " Rel.E: " +
            //                 knapsackInstance.getRelativeError()
            //                 + " P.Gu: " + knapsackInstance.getPerformanceGuarantee()
            //                 + " M " + knapsackInstance.getM() + " Sol. Weight: " +
            //                 knapsackInstance.getSolutionWeight()
            //                 + " Sol. Cost: "
            //                 + knapsackInstance.getSolutionCost());
            // System.out.println(knapsackInstance.toString());
        });
    }

    private List<Integer> solve(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C) {
        double t = INITIAL_TEMPERATURE;
        List<Integer> state = KnapsackUtils.generateLocalConstructiveSolution(n, M, W, C);

        int maxIter = MAX_ITER * n;
        while (t > FINAL_TEMPERATURE) {
            int innerIter = 0;
            while (innerIter <= maxIter) {
                innerIter++;
                List<Integer> newState = randomNeighbor(state);
                int neighborWeight = KnapsackUtils.getSolutionWeight(n, W, newState);
                int neighborCost = KnapsackUtils.getSolutionCost(n, C, newState);
                int stateCost = KnapsackUtils.getSolutionCost(n, C, state);

                if (neighborWeight <= M) {
                    int deltaC = neighborCost - stateCost;

                    // Accept solution if better
                    if (deltaC > 0) {
                        state = newState;
                    } else if (accept(deltaC, t)) { // Accept probabilistically even if solution is worse
                        state = newState;
                    }

                    int bestFoundCost = KnapsackUtils.getSolutionCost(n, C, bestFoundSol);
                    if (stateCost > bestFoundCost) {
                        bestFoundSol = new ArrayList<>(state);
                    }
                }

            }

            t = cool(t);
        }
        return bestFoundSol;
    }

    private List<Integer> randomNeighbor(List<Integer> state) {
        int n = state.size();
        List<Integer> result = new ArrayList<>(state);
        // Get random num between 0 to n - 1
        int i = ThreadLocalRandom.current().nextInt(0, n);

        if (result.get(i) == 0) {
            result.set(i, 1);
        } else if (result.get(i) == 1) {
            result.set(i, 0);
        }

        return result;
    }

    private double cool(double t) {
        // Geometric cooling
        return t *= ALPHA;
    }

    // When deltaC increases, e^(-deltaC/t) decreases
    // the higher the cost difference, the lower the acceptance probability
    // When t decreases, e^(-deltaC/t) decreases
    // the lower the temperature, the lower the acceptance probability
    private boolean accept(int deltaC, double t) {
        double acceptP = Math.pow(Math.E, 1 * deltaC / t);
        double p = Math.random();
        return p < acceptP;
    }
}
