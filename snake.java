
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javax.swing.*;
import java.awt.*;

public class  snake extends JFrame{
    Board board;
    public snake(){
        board=new Board();
        setVisible(true);
        add(board);
        pack();
        setResizable(false);


    }
    public static void main(String[] args) {
        snake snakegame=new snake();


    }

}