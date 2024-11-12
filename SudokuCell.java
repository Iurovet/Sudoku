public class SudokuCell {
    private int storedValue;
    private boolean isprotected;

    public SudokuCell(boolean isprotected) {
        storedValue = 0;
        this.isprotected = isprotected;
    }
    
    public void setNum(int storedValue) {
        this.storedValue = storedValue;
    }

    public int getNum() {
        return storedValue;
    }

    public void setProtection(boolean isprotected) {
        this.isprotected = isprotected;
    }

    public boolean getProtection() {
        return isprotected;
    }
}