import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Sudoku {
    public static String printLetter(int i) {
        return Character.toString((char) (i + 64));
    }

    public static boolean wrongNum(int number, int start) {
        return (number < start) || (number > 9);
    }

    public static boolean done (HashMap<Integer, SudokuCell> grid) {
        int numUsed = 0;
        
        for (int i = 1; i <= 9; ++i) {
            for (int j = 1; j <= 9; ++j) {
                if (grid.get(i * 100 + j).getNum() != 0) {
                    ++numUsed;
                }

                if (numUsed == 81) {
                    break;
                }
            }

            if (numUsed == 81) {
                break;
            }
        }
        
        return numUsed == 81;
    }

    public static boolean sameRow(HashMap<Integer, SudokuCell> grid, int row, int column, int storedValue) {
        int sameRow = 0;

        for (int i = 1; i <= 9; ++i) {
            if ((storedValue == grid.get(row * 100 + i).getNum()) && storedValue != 0) {
                sameRow = i;
                System.out.println("Error: " + storedValue + " is already in row " + row);
                break;
            }
        }
        
        return (sameRow != 0);
    }

    public static boolean sameColumn(HashMap<Integer, SudokuCell> grid, int row, int column, int storedValue) {
        int sameColumn = 0;

        for (int i = 1; i <= 9; ++i) {
            if ((storedValue == grid.get(i * 100 + column).getNum()) && storedValue != 0) {
                sameColumn = i;
                System.out.println("Error: " + storedValue + " is already in column " + column);
                break;
            }
        }
        
        return (sameColumn != 0);
    }

    public static boolean sameSquare(HashMap<Integer, SudokuCell> grid, int row, int column, int storedValue) {
        boolean found = false;
        String colSquare, rowSquare;
        int loRow, hiRow, loCol, hiCol;

        if (row <= 3) {
            loRow = 1;
            hiRow = 3;
            rowSquare = "top";
        }
        else if (row <= 6) {
            loRow = 4;
            hiRow = 6;
            rowSquare = "middle";
        }
        else {
            loRow = 7;
            hiRow = 9;
            rowSquare = "bottom";
        }

        if (column <= 3) {
            loCol = 1;
            hiCol = 3;
            colSquare = "left";
        }
        else if (column <= 6) {
            loCol = 4;
            hiCol = 6;
            colSquare = "center";
        }
        else {
            loCol = 7;
            hiCol = 9;
            colSquare = "right";
        }
        
        for (int i = loRow; i <= hiRow; ++i) {
            for (int j = loCol; j <= hiCol; ++j) {
                if ((storedValue == grid.get(i * 100 + j).getNum()) && storedValue != 0) {
                    System.out.println("Error: " + storedValue + " is already in the " + rowSquare + "-" + colSquare + " square");
                    
                    found = true;
                    break;
                }
            }

            if (found) {
                break;
            }
        }

        return found;
    }

    public static HashMap<Integer, SudokuCell> initialise() throws FileNotFoundException {
        Scanner scnr = new Scanner(new FileInputStream("InitialPosition.txt"));
        HashMap<Integer, SudokuCell> grid = new HashMap<Integer, SudokuCell>();

        for (int i = 1; i <= 9; ++i) { //Let i be the row
            for (int j = 1; j <= 9; ++j) { //Let j be the column
                grid.put(i * 100 + j, new SudokuCell(false));
            }
        }
        
        while (scnr.hasNextInt()) {
            int row = scnr.nextInt();
            int column = scnr.nextInt();
            int newVal = scnr.nextInt();
            
            SudokuCell cell = grid.get(row * 100 + column);

            cell.setNum(newVal);
            cell.setProtection(true);
        }

        return grid;
    }

    public static void setValue(Scanner scnr, HashMap<Integer, SudokuCell> grid) {      
        int row = scnr.nextInt();
        int column = scnr.nextInt();
        int storedValue = scnr.nextInt();
        
        if (wrongNum(row, 1) || wrongNum(column, 1) || wrongNum(storedValue, 0)) {
            System.out.println("Error: Can only process single positive digits.\n");
            return; //Since this method is being called from a while loop, effectively a continue statement
        }
        
        boolean sameRow = sameRow(grid, row, column, storedValue);
        boolean sameColumn = sameColumn(grid, row, column, storedValue);
        boolean sameSquare = sameSquare(grid, row, column, storedValue);

        if (!(sameRow || sameColumn || sameSquare)) {
            if (!grid.get(row * 100 + column).getProtection()) {
                grid.get(row * 100 + column).setNum(storedValue);
            }

            else {
                System.out.println("Error: Cell is protected\n");    
            }
        }
        else if (grid.get(row * 100 + column).getProtection()) {
            System.out.println("Error: Cell is protected\n");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        HashMap<Integer, SudokuCell> grid = initialise();
        Board board = new Board();

        while (true) {
            board.print(grid);

            if (done(grid)) {
                System.out.println("\nCongratulations, you solved the Sudoku! \n");
                break;
            }

            System.out.println("\nPlease enter a row/column and stored value to edit: \n");
            setValue(scnr, grid);
        }
    }
}