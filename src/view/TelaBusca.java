package view;

//JAVA
import javax.swing.*;
import java.awt.event.*;

public class TelaBusca extends JFrame {
    private JPanel panPrincipal;
    private JPanel panBotoes;
    private JPanel panTubos;
    private JButton btnVoltarTudo;
    private JButton btnVoltarUm;
    private JButton btnAvancarUm;
    private JButton btnAvancarTudo;
    private JButton btnVoltar;
    private JPanel panProfundidadeTempo;
    private JLabel lblProfundidade;
    private JLabel lblVariavelProfundidade;
    private JLabel lblTempo;
    private JLabel lblVariavelTempo;

    public TelaBusca(){
        setTitle("Ball Sort Puzzle");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(panPrincipal);
    }

    public void addComponentListenerPanTubos(ComponentAdapter componentAdapter) {
        panTubos.addComponentListener(componentAdapter);
    }

    public void addActionListenerBtnVoltarTudo(ActionListener acao){
        btnVoltarTudo.addActionListener(acao);
    }

    public void addActionListenerBtnVoltarUm(ActionListener acao){
        btnVoltarUm.addActionListener(acao);
    }

    public void addActionListenerBtnAvancarUm(ActionListener acao){
        btnAvancarUm.addActionListener(acao);
    }

    public void addActionListenerBtnAvancarTudo(ActionListener acao){
        btnAvancarTudo.addActionListener(acao);
    }

    public void addActionListenerBtnVoltar(ActionListener acao){
        btnVoltar.addActionListener(acao);
    }

    public JPanel getPanTubos(){
        return panTubos;
    }

    public JLabel getLblVariavelProfundidade(){
        return lblVariavelProfundidade;
    }

    public JLabel getLblVariavelTempo(){
        return lblVariavelTempo;
    }

    public JButton getBtnVoltarTudo(){
        return btnVoltarTudo;
    }

    public JButton getBtnVoltarUm(){
        return btnVoltarUm;
    }

    public JButton getBtnAvancarUm(){
        return btnAvancarUm;
    }
    public JButton getBtnAvancarTudo(){
        return btnAvancarTudo;
    }
}
