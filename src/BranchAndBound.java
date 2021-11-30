import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BranchAndBound {
    public Timer timer;
    public int complexity;
    public KnapsackReader kReader;
    public ArrayList<Integer> optimizedSolution;
    public int upperBound;

    // Deserialized instances from file
    public ArrayList<KnapsackDecisionInstance> knapsackDecisionInstances;
    public ArrayList<KnapsackOptimizationInstance> knapsackOptimizationInstances;
    public ArrayList<KnapsackOptimumSolutionInstance> knapsackOptimumSolutionInstances;

    public BranchAndBound(String fileUri) {
        timer = new Timer();
        complexity = 0;
        upperBound = 0;
        knapsackDecisionInstances = new ArrayList<>();
        knapsackOptimizationInstances = new ArrayList<>();
        knapsackOptimumSolutionInstances = new ArrayList<>();
        kReader = new KnapsackReader(fileUri);
        if (fileUri.contains("Decision")) {
            kReader.deserializeKnapsackDecisionInstances(knapsackDecisionInstances);
        } else {
            kReader.deserializeKnapsackOptimizationInstances(knapsackOptimizationInstances);
            kReader.setFileUri(KnapsackUtils.getSolUri(fileUri));
            kReader.deserializeKnapsackOptimumSolutionInstance(knapsackOptimumSolutionInstances);
        }

    }

    public void getSolutions() {
        knapsackDecisionInstances.forEach((knapsackInstance) -> {
            // Measure complexity
            complexity = 0; // Number of visited nodes
            // Measure CPU time
            timer.start();
            // Upper bound reset
            upperBound = 0;

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

    public List<List<Integer>> solve(int n, int M, int B, ArrayList<Integer> W, ArrayList<Integer> C,
            ArrayList<Integer> possibleSol) {
        this.complexity++;
        List<List<Integer>> result = new ArrayList<>();

        // Calc accumulatedWeight of added/notAdded items
        // Calc accumulatedValue of added/notAdded items
        // Calc potentialGain of undecided items as 'added'
        int accumulatedWeight, accumulatedValue, potentialGain;
        accumulatedWeight = accumulatedValue = potentialGain = 0;
        for (int i = 0; i < C.size(); i++) {
            if (possibleSol.size() > 0 && i < possibleSol.size()) {
                // Add items already added/notAdded to the accumulatedValue
                accumulatedValue += C.get(i) * possibleSol.get(i);
                // Add items already added/notAdded to the accumulatedWeight
                accumulatedWeight += W.get(i) * possibleSol.get(i);
            } else {
                // Undecided items will be added to the potentialGain
                potentialGain += C.get(i) * 1;
            }
        }

        // If the accumulatedValue is bigger than the upperBound
        // we reassign upperBound
        if (accumulatedValue > upperBound) {
            upperBound = accumulatedValue;
        }

        // If the minimal cost (B) can be met
        // and the accumulatedWeight is less than the maximum Weight (M)
        if (accumulatedValue + potentialGain >= B && accumulatedWeight <= M) {

            // If it's a full length possible answer, then we can add it to the results
            if (n == 0) {
                result.add(new ArrayList<>(possibleSol));
            }

            // If we can obtain a better upperBound,
            // by taking into account the immediate costs and potential costs
            // we continue iterating
            if (accumulatedValue + potentialGain > upperBound) {
                // Build possible solution array
                ArrayList<Integer> left = new ArrayList<Integer>(possibleSol);
                ArrayList<Integer> right = new ArrayList<Integer>(possibleSol);
                left.add(0);
                right.add(1);

                List<List<Integer>> leftPossibleRes = solve(n - 1, M, B, W, C, left);
                leftPossibleRes.forEach((possibleRes) -> result.add(possibleRes));

                List<List<Integer>> rightPossibleRes = solve(n - 1, M, B, W, C, right);
                rightPossibleRes.forEach((possibleRes) -> result.add(possibleRes));
            }
        }

        return result;
    }

    public void getOptimizedSolutions() {
        knapsackOptimizationInstances.forEach((knapsackInstance) -> {
            // Measure CPU time
            timer.start();
            // Upper bound reset
            upperBound = 0;
            // Best possible solution reset
            optimizedSolution = new ArrayList<>(Collections.nCopies(knapsackInstance.getN(), 0));

            solveOptimized(knapsackInstance.getN(), knapsackInstance.getM(),
                    knapsackInstance.getW(),
                    knapsackInstance.getC(), new ArrayList<>());

            // End timer
            timer.end();
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolution(optimizedSolution);

            // Calc Ea (Relative error) & Ra (Performance guarantee)
            KnapsackUtils.calculateQualityMeasurements(knapsackInstance, knapsackOptimumSolutionInstances);

            System.out.println(knapsackInstance.computationInfoToString());
            // System.out.println(knapsackInstance.toString());
        });
    }

    private List<List<Integer>> solveOptimized(int n, int M, ArrayList<Integer> W, ArrayList<Integer> C,
            ArrayList<Integer> possibleSol) {
        List<List<Integer>> result = new ArrayList<>();

        // Calc accumulatedWeight of added/notAdded items
        // Calc accumulatedValue of added/notAdded items
        // Calc potentialGain of undecided items as 'added'
        int accumulatedWeight, accumulatedValue, potentialGain, bestOptimizedVal;
        accumulatedWeight = accumulatedValue = potentialGain = bestOptimizedVal = 0;
        for (int i = 0; i < C.size(); i++) {
            if (possibleSol.size() > 0 && i < possibleSol.size()) {
                // Add items already added/notAdded to the accumulatedValue
                accumulatedValue += C.get(i) * possibleSol.get(i);
                // Add items already added/notAdded to the accumulatedWeight
                accumulatedWeight += W.get(i) * possibleSol.get(i);
            } else {
                // Undecided items will be added to the potentialGain
                potentialGain += C.get(i) * 1;
            }
            bestOptimizedVal += C.get(i) * optimizedSolution.get(i);
        }

        // If the accumulatedValue is bigger than the upperBound
        // we reassign upperBound
        if (accumulatedValue > upperBound) {
            upperBound = accumulatedValue;
        }

        // If the accumulatedWeight is less than the maximum Weight (M)
        if (accumulatedWeight <= M) {

            // If it's a full length possible answer, then we can add it to the results
            // and the current value has a better cost, we replace our optimizedSolution
            if (n == 0 && bestOptimizedVal < accumulatedValue) {
                // result.add(new ArrayList<>(possibleSol));
                optimizedSolution = new ArrayList<>(possibleSol);
            }

            // If we can obtain a better upperBound,
            // by taking into account the immediate costs and potential costs
            // we continue iterating
            if (accumulatedValue + potentialGain > bestOptimizedVal) {
                // Build possible solution array
                ArrayList<Integer> left = new ArrayList<Integer>(possibleSol);
                ArrayList<Integer> right = new ArrayList<Integer>(possibleSol);
                left.add(0);
                right.add(1);

                List<List<Integer>> leftPossibleRes = solveOptimized(n - 1, M, W, C, left);
                leftPossibleRes.forEach((possibleRes) -> result.add(possibleRes));

                List<List<Integer>> rightPossibleRes = solveOptimized(n - 1, M, W, C, right);
                rightPossibleRes.forEach((possibleRes) -> result.add(possibleRes));
            }
        }

        return result;

    }
}
