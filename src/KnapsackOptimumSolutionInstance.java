import java.util.ArrayList;
import java.util.List;

public class KnapsackOptimumSolutionInstance {
    private int id;
    private int n; // number of items
    private int solutionCost;
    private List<Integer> solution;

    public KnapsackOptimumSolutionInstance(int id, int n, int solutionCost) {
        this.id = id;
        this.n = n;
        this.solutionCost = solutionCost;
        this.solution = new ArrayList<Integer>(this.n);
    }

    public KnapsackOptimumSolutionInstance(String id, String n, String solutionCost) {
        this.id = Integer.parseInt(id);
        this.n = Integer.parseInt(n);
        this.solutionCost = Integer.parseInt(solutionCost);
        this.solution = new ArrayList<Integer>(this.n);
    }

    public int getId() {
        return id;
    }

    public int getN() {
        return n;
    }

    public int getSolutionCost() {
        return solutionCost;
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

    public void setSolutionCost(int solutionCost) {
        this.solutionCost = solutionCost;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }
}