import java.util.ArrayList;
import java.util.List;

public class KnapsackOptimizationInstance {
    private int id;
    private int n; // number of items
    private int M; // capacity of the Knapsack
    private ArrayList<Integer> W; // weight of items
    private ArrayList<Integer> C; // cost of items

    private long time;
    private int complexity;

    private int solutionCost;
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
        this.complexity = 0;
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
        this.complexity = 0;
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

    public long getTime() {
        return time;
    }

    public int getComplexity() {
        return complexity;
    }

    public int getSolutionCost() {
        return solutionCost;
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
        this.time = time;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public void setSolutionCost(int solutionCost) {
        this.solutionCost = solutionCost;
    }

    public void setRelativeError(float relativeError) {
        this.relativeError = relativeError;
    }

    public void setPerformanceGuarantee(float performanceGuarantee) {
        this.performanceGuarantee = performanceGuarantee;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }

    public String computationInfoToString() {
        return this.id + " " + this.relativeError + " " + this.performanceGuarantee + " " + this.complexity + " "
                + this.time;
    }

    // function to calculate the cost of a knapsack solution
    public int calculateSolutionCost() {
        int totalKnapsackCost = 0;
        for (int i = 0; i < this.n; i++) {
            int currentItemCost = this.C.get(i);
            int currentItemDecision = this.solution.get(i);

            totalKnapsackCost += currentItemCost * currentItemDecision;
        }
        this.solutionCost = totalKnapsackCost;
        return totalKnapsackCost;
    }

    public double calcRelativeErrorMaximization(int optC, int aprC) {
        if (optC > 0) {
            this.relativeError = (double) (optC - aprC) / optC;
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
                + this.complexity + " " + this.time + " " + this.relativeError + " " + this.performanceGuarantee + " "
                + this.solution;
    }
}
