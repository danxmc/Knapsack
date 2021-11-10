import java.util.ArrayList;
import java.util.List;

public class BruteForce {
    public Timer timer;
    public int complexity;
    public KnapsackReader kReader;
    public ArrayList<KnapsackDecisionInstance> knapsackInstances; // Deserialized instances from file

    public BruteForce(String fileUri) {
        timer = new Timer();
        complexity = 0;
        knapsackInstances = new ArrayList<KnapsackDecisionInstance>();
        kReader = new KnapsackReader(fileUri);
        kReader.deserializeKnapsackDecisionInstances(knapsackInstances);
    }

    public void getSolutions() {
        knapsackInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();
            // Measure complexity
            complexity = 0; // Number of visited nodes

            List<List<Integer>> knapsackSols = new ArrayList<>();
            knapsackSols = solve(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getB(),
                    knapsackInstance.getW(), knapsackInstance.getC(), new ArrayList<>());

            // End timer
            timer.end();
            knapsackInstance.setComplexity(complexity);
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolutions(knapsackSols);

            System.out.println(knapsackInstance.computationInfoToString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    private List<List<Integer>> solve(int n, int M, int B, ArrayList<Integer> W, ArrayList<Integer> C,
            ArrayList<Integer> possibleSol) {
        this.complexity++;

        List<List<Integer>> result = new ArrayList<>();
        if (n > 0) {
            // Build possible solution array
            ArrayList<Integer> left = new ArrayList<Integer>(possibleSol);
            ArrayList<Integer> right = new ArrayList<Integer>(possibleSol);
            left.add(0);
            right.add(1);
            
            List<List<Integer>> leftPossibleRes = solve(n - 1, M, B, W, C, left);
            leftPossibleRes.forEach((possibleRes) -> result.add(possibleRes));

            List<List<Integer>> rightPossibleRes = solve(n - 1, M, B, W, C, right);
            rightPossibleRes.forEach((possibleRes) -> result.add(possibleRes));
        } else {
            // Check if it's a possible solution
            int totalWeight, totalCost;
            totalWeight = totalCost = 0;

            for (int i = 0; i < possibleSol.size(); i++) {
                totalWeight += (possibleSol.get(i) * W.get(i));
                totalCost += (possibleSol.get(i) * C.get(i));
            }

            // Knapsack not overloaded && Knapsack minimum cost is met
            if (totalWeight <= M && totalCost >= B) {
                result.add(new ArrayList<>(possibleSol));
            }
        }

        return result;
    }

}
