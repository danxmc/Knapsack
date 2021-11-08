import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class KnapsackReader {
    private String fileUri;

    public KnapsackReader(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public void getFileInfo() {
        File myObj = new File(this.fileUri);
        if (myObj.exists()) {
            System.out.println("File name: " + myObj.getName());
            System.out.println("Absolute path: " + myObj.getAbsolutePath());
            System.out.println("Writeable: " + myObj.canWrite());
            System.out.println("Readable " + myObj.canRead());
            System.out.println("File size in bytes " + myObj.length());
        } else {
            System.out.println("The file does not exist.");
        }
    }

    public void readFile() {
        try {
            File myObj = new File(this.fileUri);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void deserializeKnapsackOptimumSolutionInstance(
            ArrayList<KnapsackOptimumSolutionInstance> knapsackSolutions) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileUri));

            String currentLine;
            try {
                while ((currentLine = br.readLine()) != null) {
                    // Parse to Obj
                    String[] strArgs = currentLine.split(" ");

                    KnapsackOptimumSolutionInstance knapsackInstance = new KnapsackOptimumSolutionInstance(strArgs[0],
                            strArgs[1], strArgs[2]);
                    for (int j = 3; j < strArgs.length; j++) {
                        // Assign solution arrays
                        knapsackInstance.getSolution().add(Integer.parseInt(strArgs[j]));
                    }
                    knapsackSolutions.add(knapsackInstance);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deserializeKnapsackOptimizationInstances(ArrayList<KnapsackOptimizationInstance> knapsackInstances) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileUri));

            String currentLine;
            try {
                while ((currentLine = br.readLine()) != null) {
                    // Parse to Obj
                    String[] strArgs = currentLine.split(" ");

                    KnapsackOptimizationInstance knapsackInstance = new KnapsackOptimizationInstance(strArgs[0],
                            strArgs[1], strArgs[2]);
                    for (int j = 3; j < strArgs.length; j++) {
                        // Assign weights and cost arrays
                        if (j % 2 == 0) {
                            // Cost
                            knapsackInstance.getC().add(Integer.parseInt(strArgs[j]));
                        } else {
                            // Weight
                            knapsackInstance.getW().add(Integer.parseInt(strArgs[j]));
                        }
                    }
                    knapsackInstances.add(knapsackInstance);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deserializeKnapsackDecisionInstances(ArrayList<KnapsackDecisionInstance> knapsackInstances) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileUri));
            String currentLine;
            try {
                while ((currentLine = br.readLine()) != null) {
                    // Parse to Obj
                    String[] strArgs = currentLine.split(" ");

                    KnapsackDecisionInstance knapsackInstance = new KnapsackDecisionInstance(strArgs[0], strArgs[1],
                            strArgs[2], strArgs[3]);
                    for (int j = 4; j < strArgs.length; j++) {
                        // Assign weights and cost arrays
                        if (j % 2 == 0) {
                            // Weight
                            knapsackInstance.getW().add(Integer.parseInt(strArgs[j]));
                        } else {
                            // Cost
                            knapsackInstance.getC().add(Integer.parseInt(strArgs[j]));
                        }
                    }
                    knapsackInstances.add(knapsackInstance);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
