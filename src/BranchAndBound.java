import java.util.ArrayList;
import java.util.List;

public class BranchAndBound {
    public Timer timer;
    public int complexity;
    public KnapsackReader kReader;
    public ArrayList<KnapsackDecisionInstance> knapsackInstances; // Deserialized instances from file
    public int upperBound;

    public BranchAndBound(String fileUri) {
        timer = new Timer();
        complexity = 0;
        upperBound = 0;
        knapsackInstances = new ArrayList<KnapsackDecisionInstance>();
        kReader = new KnapsackReader(fileUri);
        kReader.deserializeKnapsackInstances(knapsackInstances);
    }

    public void getSolutions() {
        knapsackInstances.forEach((knapsackInstance) -> {
            // Measure complexity
            complexity = 0; // Number of visited nodes
            // Measure CPU time
            timer.start();
            // Upper bound reset
            upperBound = 0;

            List<List<Integer>> knapsackSols = new ArrayList<>();
            knapsackSols = solve2(knapsackInstance.getN(), knapsackInstance.getM(), knapsackInstance.getB(),
                    knapsackInstance.getW(), knapsackInstance.getC(), new ArrayList<>());

            // End timer
            timer.end();
            knapsackInstance.setComplexity(complexity);
            knapsackInstance.setTime(timer.getTotalTime());
            knapsackInstance.setSolutions(knapsackSols);

            // System.out.println(knapsackInstance.computationInfoToString());
            System.out.println(knapsackInstance.toString());
        });
    }

    public List<List<Integer>> solve(int n, int M, int B, ArrayList<Integer> W, ArrayList<Integer> C,
            ArrayList<Integer> possibleSol) {
        this.complexity++;
        List<List<Integer>> result = new ArrayList<>();

        System.out.println(n + " " + possibleSol);
        if (n > 0) {
            // Calc accumulatedWeight of added/notAdded items
            // Calc accumulatedValue of added/notAdded items
            // Calc potentialGain of undecided items as 'added'
            int accumulatedWeight, accumulatedValue, potentialGain, j;
            accumulatedWeight = accumulatedValue = potentialGain = j = 0;
            for (int i = C.size() - 1; i >= 0; i--) {
                if (possibleSol.size() > 0 && j <= possibleSol.size() - 1) {
                    // Add items already added/notAdded to the accumulatedValue
                    accumulatedValue += C.get(i) * possibleSol.get(j);
                    // Add items already added/notAdded to the accumulatedWeight
                    accumulatedWeight += W.get(i) * possibleSol.get(j);
                } else {
                    // Undecided items will be added to the potentialGain
                    potentialGain += C.get(i) * 1;
                }
                j++;
            }

            // If the accumulatedValue is bigger than the upperBound
            // we reassign upperBound
            if (accumulatedValue > upperBound) {
                upperBound = accumulatedValue;
            }

            // If we can obtain a better upperBound
            // and the accumulatedWight is less than weight
            // && accumulatedWeight <= M
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

    public List<List<Integer>> solve2(int n, int M, int B, ArrayList<Integer> W, ArrayList<Integer> C,
            ArrayList<Integer> possibleSol) {
        this.complexity++;
        List<List<Integer>> result = new ArrayList<>();

        // Calc accumulatedWeight of added/notAdded items
        // Calc accumulatedValue of added/notAdded items
        // Calc potentialGain of undecided items as 'added'
        int accumulatedWeight, accumulatedValue, potentialGain, j;
        accumulatedWeight = accumulatedValue = potentialGain = j = 0;
        // for (int i = C.size() - 1; i >= 0; i--) {
        //     if (possibleSol.size() > 0 && j <= possibleSol.size() - 1) {
        //         // Add items already added/notAdded to the accumulatedValue
        //         accumulatedValue += C.get(i) * possibleSol.get(j);
        //         // Add items already added/notAdded to the accumulatedWeight
        //         accumulatedWeight += W.get(i) * possibleSol.get(j);
        //     } else {
        //         // Undecided items will be added to the potentialGain
        //         potentialGain += C.get(i) * 1;
        //     }
        //     j++;
        // }

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

        // If we can obtain a better upperBound
        // and the minimal cost can be met
        // and the accumulatedWight is less than weight
        // we continue iterating
        if (accumulatedValue + potentialGain > upperBound 
                && accumulatedValue + potentialGain >= B
                && accumulatedWeight <= M
                ) {
            // Build possible solution array
            ArrayList<Integer> left = new ArrayList<Integer>(possibleSol);
            ArrayList<Integer> right = new ArrayList<Integer>(possibleSol);
            left.add(0);
            right.add(1);

            List<List<Integer>> leftPossibleRes = solve2(n - 1, M, B, W, C, left);
            leftPossibleRes.forEach((possibleRes) -> result.add(possibleRes));

            List<List<Integer>> rightPossibleRes = solve2(n - 1, M, B, W, C, right);
            rightPossibleRes.forEach((possibleRes) -> result.add(possibleRes));

            if (n == 0 
            && accumulatedWeight <= M 
            && accumulatedValue >= B
            ) {
                result.add(new ArrayList<>(possibleSol));
            }
        }

        // If we can obtain a better upperBound
        // and the minimal cost can be met
        // and the accumulatedWight is less than weight
        // we continue iterating
        if (accumulatedValue + potentialGain >= B
            && accumulatedWeight <= M) {
            
            if (n == 0) {
                result.add(new ArrayList<>(possibleSol));
            }

            if (accumulatedValue + potentialGain > upperBound ) {
                // Build possible solution array
            ArrayList<Integer> left = new ArrayList<Integer>(possibleSol);
            ArrayList<Integer> right = new ArrayList<Integer>(possibleSol);
            left.add(0);
            right.add(1);

            List<List<Integer>> leftPossibleRes = solve2(n - 1, M, B, W, C, left);
            leftPossibleRes.forEach((possibleRes) -> result.add(possibleRes));

            List<List<Integer>> rightPossibleRes = solve2(n - 1, M, B, W, C, right);
            rightPossibleRes.forEach((possibleRes) -> result.add(possibleRes));
            }
        }

        return result;
    }
}
