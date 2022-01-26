package controller;

//JAVA
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//MODEL
import model.Linha;

//VIEW
import view.TelaBusca;

public class ControladorTelaBusca {
    private int contagem = 0;
    private TelaBusca telaBusca;
    private ControladorGeral controladorGeral;
    private ArrayList<Linha> linhasHorizontais;
    private ArrayList<Linha> linhasVerticais;
    private ArrayList<String> passosResolucao;
    private ArrayList<ArrayList<String>> tubosResolucao;

    public ControladorTelaBusca(TelaBusca telaBusca, ControladorGeral controladorGeral){
        this.telaBusca = telaBusca;
        this.controladorGeral = controladorGeral;
        linhasHorizontais = new ArrayList<>();
        linhasVerticais = new ArrayList<>();
        passosResolucao = new ArrayList<>();
        tubosResolucao = new ArrayList<>();
        definirAcoes();
    }

    public void definirAcoes(){
        telaBusca.addComponentListenerPanTubos(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                desenharTabela();
                mudarEstadoBotoesVoltar(false);
            }
        });

        telaBusca.addActionListenerBtnVoltarTudo(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                contagem = 0;
                desenharTabela();
                mudarEstadoBotoesVoltar(false);
                mudarEstadoBotoesAvancar(true);
            }
        });

        telaBusca.addActionListenerBtnVoltarUm(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (contagem > 0){
                    contagem--;
                    desenharTabela();
                }
                if(contagem == 0){
                    mudarEstadoBotoesVoltar(false);
                }
                if(contagem < passosResolucao.size()-1){
                    mudarEstadoBotoesAvancar(true);
                }
            }
        });

        telaBusca.addActionListenerBtnAvancarUm(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (contagem < passosResolucao.size()-1){
                    contagem++;
                    desenharTabela();
                }
                if(contagem == passosResolucao.size()-1){
                    mudarEstadoBotoesAvancar(false);
                }
                if(contagem > 0){
                    mudarEstadoBotoesVoltar(true);
                }
            }
        });

        telaBusca.addActionListenerBtnAvancarTudo(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                contagem = passosResolucao.size()-1;
                desenharTabela();
                mudarEstadoBotoesAvancar(false);
                mudarEstadoBotoesVoltar(true);
            }
        });

        telaBusca.addActionListenerBtnVoltar(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                limparControlador();
                controladorGeral.exibirTelaPuzzle(telaBusca);
                controladorGeral.desenharTabelaTelaPuzzle();
                controladorGeral.ativarListenerVisibilidadeTelaPuzzle();
            }
        });
    }

    private void mudarEstadoBotoesVoltar(boolean estado){
        telaBusca.getBtnVoltarTudo().setEnabled(estado);
        telaBusca.getBtnVoltarUm().setEnabled(estado);
    }

    private void mudarEstadoBotoesAvancar(boolean estado){
        telaBusca.getBtnAvancarTudo().setEnabled(estado);
        telaBusca.getBtnAvancarUm().setEnabled(estado);
    }

    public void desenharTabela(){
        linhasVerticais.clear();
        linhasHorizontais.clear();
        int altura = telaBusca.getPanTubos().getHeight();
        int largura = telaBusca.getPanTubos().getWidth();
        int quantidadeTubos = tubosResolucao.get(contagem).size();
        int tamanhoBola = 0;
        if (altura/4 >= largura/quantidadeTubos){
            tamanhoBola = largura/quantidadeTubos;
        }else{
            tamanhoBola = altura/4;
        }
        Graphics2D graphics2D = (Graphics2D) telaBusca.getPanTubos().getGraphics();
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
        for (int i = 0; i < tubosResolucao.get(contagem).size(); i++) {
            if (!tubosResolucao.get(contagem).get(i).equals("[]")) {
                String conteudoTubo = tubosResolucao.get(contagem).get(i);
                conteudoTubo = conteudoTubo.substring(1, conteudoTubo.length()-1);
                String[] bolas = conteudoTubo.split(", ");
                for (int j = 0; j < bolas.length; j++) {
                    String bola = bolas[j];
                    bola = bola.replace("Bola ", "");
                    String[] cores = bola.split("-");
                    int r = Integer.parseInt(cores[0]);
                    int g = Integer.parseInt(cores[1]);
                    int b = Integer.parseInt(cores[2]);
                    graphics2D.setColor(new Color(r, g, b));
                    graphics2D.fillOval(linhasVerticais.get(i).getxInicio(), linhasHorizontaisInvertidas.get(j+1).getyInicio(), tamanhoBola, tamanhoBola);
                }
            }
        }
        telaBusca.getPanTubos().paintComponents(graphics2D);
    }

    public void ativarListenerVisibilidade(){
        telaBusca.getPanTubos().setVisible(false);
        telaBusca.getPanTubos().setVisible(true);
    }

    public ArrayList<Linha> inverterListaLinhasHorizontais(){
        ArrayList<Linha> resposta = new ArrayList<>();
        for (int i = linhasHorizontais.size()-1; i >= 0; i--){
            resposta.add(linhasHorizontais.get(i));
        }
        return resposta;
    }

    public ArrayList<String> getPassosResolucao(){
        return passosResolucao;
    }

    public void separarTubos(){
        for (int i = 0; i < passosResolucao.size(); i++){
            ArrayList<String> tubo = new ArrayList<>();
            String[] tubos = passosResolucao.get(i).split("\n");
            for (int j = 0; j < tubos.length; j++){
                tubo.add(tubos[j]);
            }
            tubosResolucao.add(tubo);
        }
    }

    public ArrayList<String> separarBolas(String tubo){
        ArrayList<String> resposta = new ArrayList<>();
        String[] bolas = tubo.split("\n");
        for (int i = 0; i < bolas.length; i++) {
            resposta.add(bolas[i]);
        }
        return resposta;
    }

    public void limparControlador(){
        contagem = 0;
        linhasHorizontais.clear();
        linhasVerticais.clear();
        passosResolucao.clear();
        tubosResolucao.clear();
        mudarEstadoBotoesAvancar(true);
        mudarEstadoBotoesVoltar(false);

    }

    public void atualizarProfundidade(String profundidade){
        telaBusca.getLblVariavelProfundidade().setText(profundidade);
    }

    public void atualizarTempo(String tempo){
        telaBusca.getLblVariavelTempo().setText(tempo);
    }

    public void exibir(){
        telaBusca.setVisible(true);
    }
}
