package edu.virginia.cs.hw1;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.io.File;


import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        String filePath = "";
        int numOfReps = 435;
        var listy = new ArrayList<State>();
        int totalPopulation = 0;
        filePath = getFilePath(args, filePath);

        if(args.length == 1){
            huntingHillMethod(filePath,numOfReps,listy,totalPopulation);
        }
        if(args.length == 2){
            if(args[1].equals("--hamilton")) {
                hamiltonMethod(filePath,numOfReps,listy,totalPopulation);
            }
            else {
                numOfReps = getNumOfReps(args, numOfReps);
                huntingHillMethod(filePath,numOfReps,listy,totalPopulation);
            }

        }
        if(args.length == 3){
            numOfReps = getNumOfReps(args, numOfReps);
            if(args[2].equals("--hamilton")) {
                hamiltonMethod(filePath,numOfReps,listy,totalPopulation);
            }
            else{
                System.err.println("Error: " + args[2] + " is unrecognized.");
                System.exit(0);
            }
        }
        if(args.length > 3) {
            System.err.println("Error: Too many argument passed. Try again.");
            System.exit(0);
        }
    }

    private static int getNumOfReps(String[] args, int numOfReps) {
        try {
            numOfReps = (parseInt(args[1]));
        } catch (NumberFormatException e) {
            System.err.println("Error: The number inserted for the number of representatives is not a number");
            System.exit(0);
        }
        if(numOfReps <= 0) {
            System.err.println("Error: The number inserted for the number of representatives is negative or 0. Try again.");
            System.exit(0);
        }
        return numOfReps;
    }

    private static String getFilePath(String[] args, String filePath) {
        try {
            filePath = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: No arguments have been passed. Please pass in file.");
            System.exit(0);
        }
        return filePath;
    }

    public static void huntingHillMethod(String filePath, int numOfReps, ArrayList<State> listy, int totalPopulation) {
        addDataToListy(filePath,listy);
        if(listy.isEmpty()){
            System.err.println("There was no info in the file. No State was found.");
            System.exit(0);
        }
        for (State state: listy) {
            totalPopulation += state.getPopulation();
        }
        if(listy.size() > numOfReps) {
            System.err.println("Error: There are not enough representatives per state");
            System.exit(0);
        }
        for (State state: listy) {
            state.setRepresentatives(1);
            numOfReps--;
        }
        if(numOfReps > 0) {
            for(int i = 0; i < numOfReps; i++) {
                State maxPriorityState = listy.get(0);
                for (State state: listy) {
                    if(state.getPriority() > maxPriorityState.getPriority()) {
                        maxPriorityState = state;
                    }
                }
                maxPriorityState.setRepresentatives(maxPriorityState.getRepresentatives()+1);
            }
        }

        sortAlphabetically(listy);
        for (State state: listy) {
            System.out.println(state);
        }
    }
    private static void hamiltonMethod(String filePath, int numOfReps, ArrayList<State> listy, int totalPopulation) {
        addDataToListy(filePath, listy);
        if(listy.isEmpty()){
            System.err.println("There was no info in the file. No State was found.");
            System.exit(0);
        }
        for (State state: listy) {
            totalPopulation += state.getPopulation();
        }
        double averagePopPerRep = (double) totalPopulation / numOfReps;
        int totalRepsAllocated = 0;
        for (State state: listy) {
            int reps = (int)Math.floor(state.getPopulation()/averagePopPerRep);
            totalRepsAllocated += reps;
            state.setRepresentatives(reps);
            double remainder = (state.getPopulation()/averagePopPerRep) - reps;
            state.setRemainder(remainder);
        }

        ArrayList<State> listSorted = (ArrayList<State>) listy.clone();
        Collections.sort(listSorted);
        for (int i = 0; i < (numOfReps -totalRepsAllocated); i++) {
            listSorted.get(i).setRepresentatives(listSorted.get(i).getRepresentatives()+1);
        }
        sortAlphabetically(listy);
        for (State state: listy) {
            System.out.println(state);
        }
    }

    private static void addDataToListy(String filePath, ArrayList<State> listy) {
        File file = new File(filePath);
        if(filePath.contains(".csv")) {
            try {
                //  URL: https://www.javatpoint.com/how-to-read-csv-file-in-java
                //  Description: I didn't understand exactly how to read the file line by line. This link helped me with that using the scanner class, although I split the line differently and had to skip the first line. So Line 22 and 24 I think is all.
                Scanner theCSV = new Scanner(file);
                try {
                    theCSV.nextLine(); // used to ignore the header i.e.row 1
                } catch (NoSuchElementException e) {
                    System.out.println("Error: No info was found in the file.");
                    System.exit(0);
                }
                while(theCSV.hasNextLine()) {
                    String[] rowData = theCSV.nextLine().strip().split(",");
                    if (rowData.length == 2) {
                        try {
                            int num = parseInt(rowData[1].strip());
                            if(num > 0)
                                listy.add(new State(rowData[0].strip(), num));
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error: The File was not found. Try fixing the path.");
                System.exit(0);
            }
        } else if (filePath.contains(".xlsx")) {
            try {
                //  URL: https://www.codejava.net/coding/how-to-read-excel-files-in-java-using-apache-poi
                //  Description: I used this website to help me read the Excel files
                FileInputStream inputStream = new FileInputStream(filePath);
                Workbook theXLSX = new XSSFWorkbook(inputStream);
                Sheet firstSheet = theXLSX.getSheetAt(0);
                Iterator<Row> rowIterator = firstSheet.iterator();
                try {
                    rowIterator.next(); // used to ignore the header i.e.row 1
                } catch (NoSuchElementException e) {
                    System.out.println("Error: No info was found in the file.");
                    System.exit(0);
                }
                while(rowIterator.hasNext()) {
                    Row nextRow = rowIterator.next();
                    Iterator<Cell> cellIterator = nextRow.cellIterator();
                    String state = "";
                    int pop = 0;
                    int i = 0;
                    while (cellIterator.hasNext() && i<2) {
                        Cell cell = cellIterator.next();
                        if(i==0) {
                            try {
                                state = cell.getStringCellValue();
                            } catch (IllegalStateException e) {
                            }
                        }
                        if(i==1) {
                            try {
                                pop = (int)cell.getNumericCellValue();
                            } catch (IllegalStateException e) {
                            }
                        }
                        i++;
                    }
                    if (!(state.equals("")) && pop > 0) {
                        listy.add(new State(state, pop));
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("Error: The File was not found. Try fixing the path.");
                System.exit(0);
            } catch (IOException e) {
                System.err.println("Error: The data inside did not match Excel");
                System.exit(0);
            }
        } else {
            System.err.println("Error: The file selected is not a csv or an Excel (xlsx) file.");
            System.exit(0);
        }

    }

    private static void sortAlphabetically(ArrayList<State> listy) {
            //  URL: https://stackoverflow.com/questions/8432581/how-to-sort-a-listobject-alphabetically-using-object-name-field
            //  Description: I used the new comparator declaration to override my compareTo in the State class.
            Collections.sort(listy, new Comparator<State>() {
                @Override
                public int compare(final State state1, State state2) {
                    return state1.getName().compareTo(state2.getName());
                }
            } );
    }
}
