package view;

//JAVA
import javax.swing.*;

public class TelaSobre extends JFrame {
    private JPanel panPrincipal;
    private JLabel lblAluno1;
    private JLabel lblAluno2;
    private JLabel lblContato1;
    private JLabel lblContato2;

    public TelaSobre(){
        setTitle("Ball Sort Puzzle - Sobre");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(panPrincipal);
    }
}
