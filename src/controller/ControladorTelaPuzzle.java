package controller;

//JAVA
import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.nio.file.Files;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

//BUSCA
import biblioteca.busca.Nodo;
import biblioteca.busca.BuscaLargura;
import biblioteca.busca.BuscaProfundidade;

//MODEL
import model.Bola;
import model.Linha;
import model.BallSortPuzzle;

//VIEW
import view.TelaCarregando;
import view.TelaPuzzle;

public class ControladorTelaPuzzle {
    private long tempoInicio;
    private long tempoFinal;
    private boolean sucessoNaBusca = false;
    private File file;
    private Path path;
    private String desafio;
    private JFileChooser jfc;
    private int profundidade = 0;
    private TelaPuzzle telaPuzzle;
    private BallSortPuzzle ballSortPuzzle;
    private ControladorGeral controladorGeral;
    private ArrayList<Linha> linhasHorizontais;
    private ArrayList<Linha> linhasVerticais;
    private ArrayList<String> passosResolucao;

    public ControladorTelaPuzzle(TelaPuzzle telaPuzzle, ControladorGeral controladorGeral){
        this.telaPuzzle = telaPuzzle;
        this.controladorGeral = controladorGeral;
        linhasHorizontais = new ArrayList<>();
        linhasVerticais = new ArrayList<>();
        passosResolucao = new ArrayList<>();
        definirAcoes();
    }

    public void definirAcoes(){
        telaPuzzle.addComponentListenerPanTubos(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                desenharTabela();
            }
        });

        telaPuzzle.addActionListenerBtnCarregar(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.showOpenDialog(telaPuzzle);
                try {
                    if(jfc.getSelectedFile() != null){
                        file = jfc.getSelectedFile();
                        path = file.toPath();
                        desafio = Files.readString(path);
                        ballSortPuzzle = new BallSortPuzzle(desafio);
                        desenharTabela();
                    }
                }catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        telaPuzzle.addActionListenerBtnLargura(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TelaCarregando telaCarregando = new TelaCarregando();
                new Thread(() -> {
                    try {
                        if (ballSortPuzzle != null){
                            tempoInicio = System.currentTimeMillis();
                            buscaLargura();
                            tempoFinal = System.currentTimeMillis();
                            if (sucessoNaBusca == true) {
                                controladorGeral.transferirListaDePassos(passosResolucao);
                                controladorGeral.separarListaDePassos();
                                controladorGeral.exibirTelaBusca(telaPuzzle, profundidade+"", (tempoFinal-tempoInicio)+"ms");
                                controladorGeral.desenharTabelaTelaBusca();
                                controladorGeral.ativarListenerVisibilidadeTelaBusca();
                            }else{
                                JOptionPane.showMessageDialog(telaPuzzle, "O desafio nao possui solucao", "Sem solucao", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(telaPuzzle, "O desafio possui uma complexidade muito alta", "Complexidade alta", JOptionPane.WARNING_MESSAGE);
                    } finally {
                        telaCarregando.dispose();
                    }
                }).start();
            }
        });

        telaPuzzle.addActionListenerBtnProfundidade(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TelaCarregando telaCarregando = new TelaCarregando();
                new Thread(() -> {
                    try {
                        if (ballSortPuzzle != null) {
                            tempoInicio = System.currentTimeMillis();
                            buscaProfundidade();
                            tempoFinal = System.currentTimeMillis();
                            if (sucessoNaBusca == true) {
                                controladorGeral.transferirListaDePassos(passosResolucao);
                                controladorGeral.separarListaDePassos();
                                controladorGeral.exibirTelaBusca(telaPuzzle, profundidade + "", (tempoFinal - tempoInicio) + "ms");
                                controladorGeral.desenharTabelaTelaBusca();
                                controladorGeral.ativarListenerVisibilidadeTelaBusca();
                            } else {
                                System.out.println("Sem solucao");
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        telaCarregando.dispose();
                    }
                }).start();
            }
        });

        telaPuzzle.addActionListenerBtnSobre(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                controladorGeral.exibirTelaSobre();
            }
        });
    }

    public void desenharTabela(){
        linhasVerticais.clear();
        linhasHorizontais.clear();
        int altura = telaPuzzle.getPanTubos().getHeight();
        int largura = telaPuzzle.getPanTubos().getWidth();
        int quantidadeTubos = ballSortPuzzle.getTubos().size();
        int tamanhoBola = 0;
        if (altura/4 >= largura/quantidadeTubos){
            tamanhoBola = largura/quantidadeTubos;
        }else{
            tamanhoBola = altura/4;
        }
        Graphics2D graphics2D = (Graphics2D) telaPuzzle.getPanTubos().getGraphics();
        graphics2D.clearRect(0, 0, largura, altura);
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(0, 0, largura, altura);
        graphics2D.setColor(Color.black);
        for (int i = 0; i <= 4; i++){
            int valor = ((altura/4)*i);
            linhasHorizontais.add(new Linha(0, valor, largura, valor));
        }
        for (int i = 0; i <= quantidadeTubos; i++){
            int valor = ((largura/quantidadeTubos)*i);
            linhasVerticais.add(new Linha(valor, 0, valor, altura));
        }
        for (int i = 0; i < linhasHorizontais.size(); i++){
            Linha linha = linhasHorizontais.get(i);
            graphics2D.drawLine(linha.getxInicio(), linha.getyInicio(), linha.getxFinal(), linha.getyFinal());
        }
        for (int i = 0; i < linhasVerticais.size(); i++){
            Linha linha = linhasVerticais.get(i);
            graphics2D.drawLine(linha.getxInicio(), linha.getyInicio(), linha.getxFinal(), linha.getyFinal());
        }

        ArrayList<Linha> linhasHorizontaisInvertidas = inverterListaLinhasHorizontais();
        for (int i = 0; i < quantidadeTubos; i++) {
            if (ballSortPuzzle.getTubos().get(i).tuboVazio() != true) {
                for (int j = 0; j < 4; j++) {
                    if (ballSortPuzzle.getTubos().get(i).getTubo().get(j) != null) {
                        Bola bola = ballSortPuzzle.getTubos().get(i).getTubo().get(j);
                        graphics2D.setColor(new Color(bola.getR(), bola.getG(), bola.getB()));
                        graphics2D.fillOval(linhasVerticais.get(i).getxInicio(), linhasHorizontaisInvertidas.get(j+1).getyInicio(), tamanhoBola, tamanhoBola);
                    }
                }
            }
        }
        telaPuzzle.getPanTubos().paintComponents(graphics2D);
    }

    public ArrayList<Linha> inverterListaLinhasHorizontais(){
        ArrayList<Linha> resposta = new ArrayList<>();
        for (int i = linhasHorizontais.size()-1; i >= 0; i--){
            resposta.add(linhasHorizontais.get(i));
        }
        return resposta;
    }

    public void exibir(){
        telaPuzzle.setVisible(true);
    }

    public void buscaLargura() throws Exception{
        passosResolucao.clear();
        BuscaLargura<BallSortPuzzle> buscaLargura = new BuscaLargura<>();
        Nodo n = null;
        n = buscaLargura.busca(ballSortPuzzle);
        if (n == null) {
            sucessoNaBusca = false;
        }else{
            String[] passos = n.montaCaminho().split(";");
            for(int i = 0; i <= n.getProfundidade(); i++){
                if(i == 0){
                    passos[i] = passos[i].substring(1, passos[i].length()-1);
                }else{
                    passos[i] = passos[i].substring(2, passos[i].length()-1);
                }
                passosResolucao.add(passos[i]);
            }
            sucessoNaBusca = true;
            profundidade = n.getProfundidade();
        }
    }

    public void buscaProfundidade() throws Exception{
        passosResolucao.clear();
        BuscaProfundidade<BallSortPuzzle> buscaProfundidade = new BuscaProfundidade<>();
        Nodo n = null;
        n = buscaProfundidade.busca(ballSortPuzzle);
        if (n == null) {
            sucessoNaBusca = false;
        }else{
            String[] passos = n.montaCaminho().split(";");
            for(int i = 0; i < n.getProfundidade()+1; i++){
                if(i == 0){
                    passos[i] = passos[i].substring(1, passos[i].length()-1);
                }else{
                    passos[i] = passos[i].substring(2, passos[i].length()-1);
                }
                passosResolucao.add(passos[i]);
            }
            sucessoNaBusca = true;
            profundidade = n.getProfundidade();
        }
    }

    public void ativarListenerVisibilidade(){
        telaPuzzle.getPanTubos().setVisible(false);
        telaPuzzle.getPanTubos().setVisible(true);
    }

    public int getProfundidade(){
        return profundidade;
    }
}
