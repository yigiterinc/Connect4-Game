package connect4;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

public class BoardView extends JPanel {
    private JButton[][] buttons;

    public BoardView(int numRows, int numCols, final BoardController controller) {
        setLayout(new GridLayout(numRows, numCols));
        buttons = new JButton[numRows][numCols];

        addButtons(numRows, numCols, controller);
    }

    private void addButtons(int numRows, int numCols, BoardController controller) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                JButton button = createConnect4Button(controller, col);
                add(button);
                buttons[row][col] = button;
            }
        }
    }

    private JButton createConnect4Button(BoardController controller, int col) {
        JButton button = new JButton();
        button.setBorder(new RoundedBorder(10));
        button.setForeground(Color.BLUE);

        button.addActionListener(new ButtonListener(controller, col));
        return button;
    }

    void markWinningChip(int winningChipRow, int winningChipCol) {
        setButtonIcon(buttons[winningChipRow][winningChipCol], "resources/veryGood.png");
    }

    void paintButton(int row, int col, Color color) {
        if (color == Color.RED) {
            setButtonIcon(buttons[row][col], "resources/redChip.jpg");
        } else if (color == Color.YELLOW) {
            setButtonIcon(buttons[row][col], "resources/yellowChip.jpg");
        }
    }

    private void setButtonIcon(JButton button, String iconAddress) {
        try {
            Image img = ImageIO.read(new FileInputStream(iconAddress));
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    void resetPanel(int row, int col) {
        revalidate();
        repaint();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                buttons[i][j].setIcon(null);
            }
        }
    }

    private static class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius + 1, this.radius+2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    class ButtonListener implements ActionListener {
        private final BoardController controller;
        private final int col;

        public ButtonListener(BoardController controller, int col) {
            this.controller = controller;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            try {
                controller.buttonClicked(col);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}
