package prototype;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    public Main() {
        
        super.setTitle("Facharbeit: Messung von Entfernung mittels Ultraschal-S.");
        setSize(new Dimension(1200, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new GUI());
        setResizable(false);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
           new Main().setVisible(true);
        });
       new Thread(new Serial()).start();
    }

}
