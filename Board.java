import java.util.HashMap;

public class Board{
    public void print(HashMap<Integer, SudokuCell> grid) {
        for (int i = 0; i <= 9; ++i) {
            System.out.print(i == 0 ? "" : i);
            System.out.print(i == 0 ? "  | " : " | ");
        }

        System.out.println();

        for (int i = 1; i <= 9; ++i) {
            System.out.print(i + " | ");

            for (int j = 1; j <= 9; ++j) {
                int currCell = grid.get((i * 100) + j).getNum();
                
                String output = currCell != 0 ? Integer.toString(currCell) : "-";
                System.out.print(output);

                System.out.print(i > 0 ? " | " : "  ");
            }

            System.out.println();
        }
    }
}
