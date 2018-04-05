package connect4;

import javax.swing.*;
import java.awt.*;

public class BoardController {
    private Board board;
    private BoardView view;

    public BoardController(Board board) {
        this.board = board;
        this.view = new BoardView(board.getNumOfRows(), board.getNumColumns(),this );
        if (board.hasInitialConfiguration) {
            for (int i = 0; i < board.getConfiguration().length(); i++) {
                int col = Character.getNumericValue(board.getConfiguration().charAt(i));

                if(i % 2 == 0)
                    view.paintButton(Board.NUM_ROWS - board.numberOfItemsInColumn[col - 1], col - 1, Color.RED);
                else
                    view.paintButton(Board.NUM_ROWS - board.numberOfItemsInColumn[col - 1], col - 1, Color.YELLOW);
            }
        }
    }

    public void buttonClicked(int col) throws Exception {
        Chip lastPlayer = board.getCurrentPlayer();
        try {
        board.insertChipAt(col + 1); } catch (Exception ex) { }

        paintChip(col, lastPlayer);

        if (board.hasWon(lastPlayer)) {
            paintWinningFour();

            JOptionPane.showMessageDialog(null, "Game is over, " + getWinningChipName(lastPlayer) + " has won.");
            playAgainDialog();
        }

        else if (board.isGameDraw()) {
            JOptionPane.showMessageDialog(null, "It is a draw !");
            playAgainDialog();
        }
    }

    private void paintChip(int col, Chip lastPlayer) {
        if (lastPlayer == Chip.RED ) {
            view.paintButton(Board.NUM_ROWS - board.numberOfItemsInColumn[col], col, Color.RED);
        }

        else if (lastPlayer == Chip.YELLOW) {
            view.paintButton(Board.NUM_ROWS - board.numberOfItemsInColumn[col], col, Color.YELLOW);
        }
    }

    private void paintWinningFour() {
        int[] winningColIndexes = board.getWinningColIndex();
        int[] winningRowIndexes = board.getWinningRowIndex();

        for (int i = 0; i < 4; i++) {
            int winningChipRow = winningRowIndexes[i];
            int winningChipCol = winningColIndexes[i];

            view.markWinningChip(winningChipRow,winningChipCol);
        }
    }

    private String getWinningChipName(Chip lastPlayer) {
        String winningChipName = "";

        if(lastPlayer == Chip.RED)
            winningChipName = "RED";
        else if(lastPlayer == Chip.YELLOW)
            winningChipName = "YELLOW";

        return winningChipName;
    }

    private void playAgainDialog() {
        int playAgain = JOptionPane.showConfirmDialog(null,"Would you like to play again ?");
        if (playAgain == JOptionPane.YES_OPTION) {
            board = new Board();
            view.resetPanel(board.getNumOfRows(), board.getNumColumns());
        }
        else
            System.exit(0);
    }

    public BoardView getView() {
        return this.view;
    }

}
