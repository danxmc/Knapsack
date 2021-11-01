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

    private List<Integer> solution;

    public KnapsackOptimizationInstance(int id, int n, int M) {
        this.id = id;
        this.n = n;
        this.M = M;
        this.W = new ArrayList<Integer>(this.n);
        this.C = new ArrayList<Integer>(this.n);

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

    public List<Integer> getSolutions() {
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

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }

    public String computationInfoToString() {
        return this.id + " " + this.complexity + " " + this.time;
    }

    @Override
    public String toString() {
        return this.id + " " + this.n + " " + this.M + " " + this.W.toString() + " " + this.C.toString()
                + " " + this.complexity + " " + this.time + " " + this.solution;
    }
}
