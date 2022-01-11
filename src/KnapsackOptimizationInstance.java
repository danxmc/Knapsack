import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KnapsackOptimizationInstance {
    private int id;
    private int n; // number of items
    private int M; // capacity of the Knapsack
    private ArrayList<Integer> W; // weight of items
    private ArrayList<Integer> C; // cost of items

    private float time;

    private int solutionCost;
    private int solutionWeight;
    private double relativeError;
    private double performanceGuarantee;

    private List<Integer> solution;

    public KnapsackOptimizationInstance(int id, int n, int M) {
        this.id = id;
        this.n = n;
        this.M = M;
        this.W = new ArrayList<Integer>(this.n);
        this.C = new ArrayList<Integer>(this.n);

        this.solutionCost = 0;
        this.relativeError = 0;
        this.performanceGuarantee = 1;
        this.time = 0;
        this.solution = new ArrayList<>();
    }

    public KnapsackOptimizationInstance(String id, String n, String M) {
        this.id = Integer.parseInt(id);
        this.n = Integer.parseInt(n);
        this.M = Integer.parseInt(M);
        this.W = new ArrayList<Integer>(this.n);
        this.C = new ArrayList<Integer>(this.n);

        this.solutionCost = 0;
        this.relativeError = 0;
        this.performanceGuarantee = 1;
        this.time = 0;
        this.solution = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return M;
    }

    public ArrayList<Integer> getW() {
        return W;
    }

    public ArrayList<Integer> getC() {
        return C;
    }

    public float getTime() {
        return time;
    }

    public int getSolutionCost() {
        return solutionCost;
    }

    public int getSolutionWeight() {
        return solutionWeight;
    }

    public double getRelativeError() {
        return relativeError;
    }

    public double getPerformanceGuarantee() {
        return performanceGuarantee;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setM(int m) {
        M = m;
    }

    public void setW(ArrayList<Integer> w) {
        W = w;
    }

    public void setC(ArrayList<Integer> c) {
        C = c;
    }

    public void setTime(long time) {
        float timeMs = time / 1000000.0f;
        this.time = timeMs;
    }

    public void setSolutionCost(int solutionCost) {
        this.solutionCost = solutionCost;
    }

    public void setSolutionWeight(int solutionWeight) {
        this.solutionWeight = solutionWeight;
    }

    public void setRelativeError(float relativeError) {
        this.relativeError = relativeError;
    }

    public void setPerformanceGuarantee(float performanceGuarantee) {
        this.performanceGuarantee = performanceGuarantee;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
        this.calculateSolutionCost();
        this.calculateSolutionWeight();
    }

    public String computationInfoToString() {
        return this.id + " " + this.relativeError + " " + this.performanceGuarantee + " " + this.time;
    }

    public String solutionInfoToString() {
        String solutionString = this.solution.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(" ", "", ""));
        return this.id + " " + this.n + " " + this.solutionCost + " " + solutionString;
    }

    // function to calculate the cost of a knapsack solution
    public int calculateSolutionCost() {
        this.solutionCost = KnapsackUtils.getSolutionCost(this.n, this.C, this.solution);
        return this.solutionCost;
    }

    // function to calculate the weight of a knapsack solution
    public int calculateSolutionWeight() {
        this.solutionWeight = KnapsackUtils.getSolutionWeight(this.n, this.W, this.solution);
        return this.solutionWeight;
    }

    public double calcRelativeErrorMaximization(int optC, int aprC) {
        if (optC > 0) {
            this.relativeError = (double) (optC - aprC) / optC;
        } else {
            this.relativeError = 1 - (double) 1 / this.performanceGuarantee;
        }
        return this.relativeError;
    }

    public double calcRelativeErrorMinimization(int optC, int aprC) {
        if (aprC > 0) {
            this.relativeError = (double) (aprC - optC) / aprC;
        }
        return this.relativeError;
    }

    public double calcPerformanceGuaranteeMaximization(int optC, int aprC) {
        if (aprC > 0) {
            this.performanceGuarantee = (double) optC / aprC;
        } else {
            this.performanceGuarantee = (double) 1 / (1 - this.relativeError);
        }
        return this.performanceGuarantee;
    }

    public double calcPerformanceGuaranteeMinimization(int optC, int aprC) {
        if (optC > 0) {
            this.performanceGuarantee = (double) aprC / optC;
        }
        return this.performanceGuarantee;
    }

    @Override
    public String toString() {
        return this.id + " " + this.n + " " + this.M + " " + this.W.toString() + " " + this.C.toString() + " "
                + this.time + " " + this.relativeError + " " + this.performanceGuarantee + " " + this.solution;
    }
}
