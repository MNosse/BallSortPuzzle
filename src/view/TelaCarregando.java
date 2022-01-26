package view;

import javax.swing.*;

public class TelaCarregando extends JFrame {
    private JPanel panPrincipal;
    private JProgressBar pgbCarregando;
    private JLabel lblMensagem;

    public TelaCarregando(){
        setTitle("Ball Sort Puzzle - Carregando");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setContentPane(panPrincipal);
        pgbCarregando = new JProgressBar();
        setVisible(true);
    }
}
