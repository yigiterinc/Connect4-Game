package connect4;

import org.junit.Test;

import static connect4.Board.NUM_ROWS;
import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void noArgConstTest() {
        Board gameBoard = new Board();
        String expected = "";

        for (int i = 0; i < NUM_ROWS; i++) {
            expected += "|_ _ _ _ _ _ _|";
            if(i != NUM_ROWS - 1)
                expected += "\n";
        }

        assertEquals(expected, gameBoard.toString());
    }

    @Test
    public void constWithInitialConfTest() throws Exception {
        Board gameBoard = new Board("3432554");
        String expected = "";

        for (int i = 0; i < 4; i++)
            expected += "|_ _ _ _ _ _ _|\n";

        expected += "|_ _ R R Y _ _|\n";
        expected += "|_ Y R Y R _ _|";

        assertEquals(expected, gameBoard.toString());
    }

    //with valid conf
    @Test
    public void isValidConfigurationTest() throws Exception {
        Board gameBoard = new Board("121356");

        assertEquals(true, gameBoard.isValidConfiguration("121356"));
    }

    //with unvalid config, trying to add chips after winning condition is satisfied
    @Test (expected = RuntimeException.class)
    public void isValidConfigurationTest2() throws Exception {
        Board gameBoard = new Board("12131417");

        assertEquals(false, gameBoard.isValidConfiguration("12131417"));
    }

    //with unvalid config, impossible combination, col is full
    @Test (expected = RuntimeException.class)
    public void isValidConfigurationTest3() throws Exception {
        Board gameBoard = new Board("11111111");

        assertEquals(false, gameBoard.isValidConfiguration("11111111"));
    }

    //with unvalid config, impossible combination: col does not exist
    @Test (expected = IllegalArgumentException.class)
    public void isValidConfigurationTest4() throws Exception {
        Board gameBoard = new Board("1238325");

        assertEquals(false, gameBoard.isValidConfiguration("1238325"));
    }

    //asserts to insert successfully
    @Test
    public void insertChipAtTest() throws Exception {
        Board gameBoard = new Board();

        gameBoard.insertChipAt(1);
        gameBoard.insertChipAt(2);
        gameBoard.insertChipAt(1);
        gameBoard.insertChipAt(6);

        assertEquals("1216", gameBoard.getConfiguration());
    }

    // if there is no column at the specified index exists, has to throw IllegalArgumentException, test it.
    @Test (expected =  IllegalArgumentException.class)
    public void insertChipAtTest2() throws Exception {
        Board gameBoard = new Board();

        gameBoard.insertChipAt(8);
    }

    //if inserting a chip at the specified column is not possible throws a RunTimeException. test it.
    @Test (expected = RuntimeException.class)
    public void insertChipAtTest3() throws Exception {
        Board gameBoard = new Board("1111111");

        gameBoard.insertChipAt(1);
    }

    //tests vertically
    @Test
    public void getWinnerTest() throws Exception {
        Board gameBoard = new Board("1213141");

        assertEquals(Chip.RED, gameBoard.getWinner());
    }

    //tests horizontally
    @Test
    public void getWinnerTest2() throws Exception {
        Board gameBoard = new Board("1121314");

        assertEquals(Chip.RED, gameBoard.getWinner());
    }

    //test diagonally
    @Test
    public void getWinnerTest3() throws Exception {
        Board gameBoard = new Board("1313252134444");

        assertEquals(Chip.RED, gameBoard.getWinner());
    }
    //tests reverse diagonal
    @Test
    public void getWinnerTest4() throws Exception {
        Board gameBoard = new Board("1314212335444");

        assertEquals(Chip.RED, gameBoard.getWinner());
    }
    //there is no winner
    @Test
    public void getWinnerTest5() throws Exception {
        Board gameBoard = new Board("13274");

        assertEquals(Chip.NONE, gameBoard.getWinner());
    }

    @Test
    public void getConfigurationTest() throws Exception {
        Board gameBoard = new Board();

        gameBoard.insertChipAt(3);
        gameBoard.insertChipAt(2);
        gameBoard.insertChipAt(3);
        gameBoard.insertChipAt(4);

        assertEquals("3234", gameBoard.getConfiguration());
    }
}
