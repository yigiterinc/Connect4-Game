package connect4;

enum Chip {
    NONE, RED, YELLOW;

    public String toString() {
        if (this == RED)
            return "R";
        else if (this == YELLOW)
            return "Y";
        else
            return "_";
    }
}

public class Board {
    public static final int NUM_COLUMNS = 7;
    public static final int NUM_ROWS = 6;
    int[] numberOfItemsInColumn = new int[NUM_COLUMNS]; // holds the number of items in the columns from 0 to 6
    Chip[][] gameBoard;
    Chip currentPlayer;
    String boardConfiguration = "";
    Chip winner;
    int[] winningRowIndex, winningColIndex; //holds the locations of winning 4 chips in the format: (winningRowIndex[i],winningColIndex[i])
    boolean hasInitialConfiguration = false;

    public Board() {
        initBoard();
    }

    public Board(String configuration) throws Exception {
        initBoard();
        hasInitialConfiguration = true;

        if (isValidConfiguration(configuration)) {
            applyConfiguration(configuration);
            currentPlayer = configuration.length() % 2 == 1 ? Chip.YELLOW : Chip.RED;
        } else
            throw new IllegalArgumentException("Invalid configuration!");
    }

    private void initBoard() {
        gameBoard = new Chip[NUM_ROWS][NUM_COLUMNS];

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                gameBoard[i][j] = Chip.NONE;
            }
        }
        currentPlayer = Chip.RED;
        winner = Chip.NONE;
    }

    void applyConfiguration(String configuration) throws Exception {
        for (int i = 0; i < configuration.length(); i++) {
            int columnForAddition = Character.getNumericValue(configuration.charAt(i));
            insertChipAt(columnForAddition);
        }
    }

    // it is  considered a valid configuration if it doesn't try to add after winning cond. is satisfied
    // or to a column that does not exist, or to a column that is full
    protected boolean isValidConfiguration(String configuration) {
        for (int i = 0; i < configuration.length(); i++) {
            int add = Character.getNumericValue(configuration.charAt(i));
            boolean isNotValid = !isValidInsertion(add);
            if (isNotValid)
                return false;
        }
        return true;
    }

    /**
     * (indexing starts at 1). After the chip is inserted, it will be the other player's turn.
     *
     * @param column The index of the column into which to put a chip.
     * @throws IllegalArgumentException if no column at the specified index exists.
     * @throws RuntimeException         if inserting a chip at the specified column is not possible (e.g. when the column is full)
     */
    public void insertChipAt(int column) throws Exception {
        if (isValidInsertion(column)) {
            insertChip(column);
            boardConfiguration += column;
            currentPlayer = currentPlayer == Chip.RED ? Chip.YELLOW : Chip.RED;
        } else {
            if (!columnExistsAt(column))
                throw new IllegalArgumentException();
            else if (isColumnFull(column - 1) || getWinner() != Chip.NONE)
                throw new RuntimeException();
        }
    }

    private void insertChip(int column) {
        int rowIndexForInsertion = NUM_ROWS - 1 - numberOfItemsInColumn[column - 1]; // NUM_ROWS-1 is the index of last row, you have n items,insert to n cells above
        gameBoard[rowIndexForInsertion][column - 1] = currentPlayer;
        numberOfItemsInColumn[column - 1] += 1;
    }

    private boolean columnExistsAt(int column) {
        return !(column > NUM_COLUMNS || column < 1);
    }

    private boolean isColumnFull(int column) {
        return numberOfItemsInColumn[column - 1] == NUM_ROWS;
    }

    private boolean isValidInsertion(int column) {
        boolean columnIsAvailable = column <= NUM_COLUMNS && column >= 1 && numberOfItemsInColumn[column - 1] < NUM_ROWS;
        boolean gameIsOver = getWinner() != Chip.NONE;
        return columnIsAvailable && !gameIsOver;
    }

    /**
     * @return the winner Chip (RED or YELLOW) if there exists a 4-connection;
     * NONE, otherwise.
     */
    public Chip getWinner() {
        checkWin();
        return winner;
    }

    void checkWin() {
        if (hasWon(Chip.YELLOW) == true) {
            currentPlayer = Chip.NONE;
            winner = Chip.YELLOW;
        } else if (hasWon(Chip.RED) == true) {
            currentPlayer = Chip.NONE;
            winner = Chip.RED;
        }
    }

    boolean hasWon(Chip chip) {
        return verticalWinConditionSatisfied(chip) || horizontalWinConditionSatisfied(chip)
                || diagonalWinConditionSatisfied(chip) || reverseDiagonalWinConditionSatisfied(chip);
    }

    private boolean verticalWinConditionSatisfied(Chip chip) {
        int count = 1;
        winningRowIndex = new int[4];
        winningColIndex = new int[4];
        int row, col;
        for (row = NUM_ROWS - 1; row >= 3; row--) {
            for (col = 0; col < NUM_COLUMNS; col++) {
                if (gameBoard[row][col] == chip) {
                    count = 1;
                    for (int i = 1; i < 4; i++) {
                        if (gameBoard[row - i][col] == chip)
                            count++;
                        else
                            break;
                    }
                }
                if (count == 4) {
                    winningRowIndex[0] = row;
                    winningColIndex[0] = col;
                    for (int i = 1; i < 4; i++) {
                        winningColIndex[i] = col;
                        winningRowIndex[i] = row - i;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalWinConditionSatisfied(Chip chip) {
        int count = 1;
        int row, col;
        winningRowIndex = new int[4];
        winningColIndex = new int[4];

        for (row = NUM_ROWS - 1; row >= 0; row--) {
            for (col = 0; col < NUM_COLUMNS - 3; col++) {
                if (gameBoard[row][col] == chip) {
                    count = 1;
                    for (int i = 1; i < 4; i++) {
                        if (gameBoard[row][col + i] == chip)
                            count++;
                        else
                            break;
                    }
                }
                if (count == 4) {
                    winningRowIndex[0] = row;
                    winningColIndex[0] = col;
                    for (int i = 1; i < 4; i++) {
                        winningColIndex[i] = col + i;
                        winningRowIndex[i] = row;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    //test3 -diagonal from left to right-
    private boolean diagonalWinConditionSatisfied(Chip chip) {
        int count, row, col;
        winningRowIndex = new int[4];
        winningColIndex = new int[4];

        for (row = NUM_ROWS - 1; row >= 3; row--) {
            for (col = 0; col < NUM_COLUMNS - 3; col++) {
                if (gameBoard[row][col] == chip) {
                    count = 1;
                    for (int i = 1; i < 4; i++) {
                        if (gameBoard[row - i][col + i] == chip)
                            count++;
                    }
                    if (count == 4) {
                        winningRowIndex[0] = row;
                        winningColIndex[0] = col;
                        for (int i = 1; i < 4; i++) {
                            winningRowIndex[i] = row - i;
                            winningColIndex[i] = col + i;
                        }
                        return true;
                    }
                }

            }
        }
        return false;
    }

    //diagonal from right to left
    private boolean reverseDiagonalWinConditionSatisfied(Chip chip) {
        int count, row, col;
        winningRowIndex = new int[4];
        winningColIndex = new int[4];

        for (row = NUM_ROWS - 1; row >= 3; row--) {
            for (col = NUM_COLUMNS - 1; col >= 3; col--) {
                if (gameBoard[row][col] == chip) {
                    count = 1;
                    for (int i = 1; i < 4; i++) {
                        if (gameBoard[row - i][col - i] == chip)
                            count++;
                    }
                    if (count == 4) {
                        winningRowIndex[0] = row;
                        winningColIndex[0] = col;
                        for (int i = 1; i < 4; i++) {
                            winningRowIndex[i] = row - i;
                            winningColIndex[i] = col - i;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getConfiguration() {
        return this.boardConfiguration;
    }

    public Chip getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return this board's string representation.
     * E.g. here is the output for the configuration 3432554:
     * |_ _ _ _ _ _ _|
     * |_ _ _ _ _ _ _|
     * |_ _ _ _ _ _ _|
     * |_ _ _ _ _ _ _|
     * |_ _ R R Y _ _|
     * |_ Y R Y R _ _|
     */
    public String toString() {
        String stringRepresentation = new String("");

        for (int row = 0; row < NUM_ROWS; row++) {
            stringRepresentation += "|";
            for (int col = 0; col < NUM_COLUMNS; col++) {
                stringRepresentation += gameBoard[row][col];
                if (col != NUM_COLUMNS - 1)
                    stringRepresentation += " ";
            }
            stringRepresentation += "|";

            if (row < NUM_ROWS - 1)
                stringRepresentation += "\n";
        }

        return stringRepresentation;
    }

    boolean isGameDraw() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                if (gameBoard[i][j] == Chip.NONE)
                    return false;
            }
        }
        return true;
    }

    int[] getWinningRowIndex() {
        return winningRowIndex;
    }

    int[] getWinningColIndex() {
        return winningColIndex;
    }

    public int getNumOfRows() {
        return NUM_ROWS;
    }

    public int getNumColumns() {
        return NUM_COLUMNS;
    }
}
