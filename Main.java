package connect4;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Connect4 Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        Board board = new Board();

        int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to enter an initial configuration?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            String initialConf = JOptionPane.showInputDialog("Enter the configuration");
            if (!board.isValidConfiguration(initialConf)) {
                do {
                    initialConf = JOptionPane.showInputDialog("Enter a valid configuration");
                }
                while (!board.isValidConfiguration(initialConf));

                board = new Board(initialConf);
            } else
                board = new Board(initialConf);
        } else {
            board = new Board();
        }

        BoardController controller = new BoardController(board);
        frame.add(controller.getView());
        frame.setVisible(true);
    }
}