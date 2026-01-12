// 1 block..25x25 pixels 
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int brdWidth= 600;
        int brdHeight= brdWidth;

        JFrame frame= new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(brdWidth, brdHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame sg = new SnakeGame(brdWidth, brdHeight);
        frame.add(sg);
        frame.pack();
        sg.requestFocus();
    }
}
