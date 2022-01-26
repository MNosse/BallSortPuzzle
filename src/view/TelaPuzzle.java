package view;

//JAVA
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class TelaPuzzle extends JFrame {
    private JPanel panPrincipal;
    private JButton btnCarregar;
    private JButton btnLargura;
    private JButton btnProfundidade;
    private JButton btnSobre;
    private JPanel panBotoes;
    private JPanel panTubos;
    private JLabel lblPuzzle;

    public TelaPuzzle(){
        setTitle("Ball Sort Puzzle");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panPrincipal);
    }

    public void addComponentListenerPanTubos(ComponentAdapter componentAdapter) {
        panTubos.addComponentListener(componentAdapter);
    }

    public void addActionListenerBtnCarregar(ActionListener acao){
        btnCarregar.addActionListener(acao);
    }

    public void addActionListenerBtnLargura(ActionListener acao){
        btnLargura.addActionListener(acao);
    }

    public void addActionListenerBtnProfundidade(ActionListener acao){
        btnProfundidade.addActionListener(acao);
    }

    public void addActionListenerBtnSobre(ActionListener acao){
        btnSobre.addActionListener(acao);
    }

    public JPanel getPanTubos(){
        return panTubos;
    }
}
