package controller;

//JAVA
import javax.swing.*;
import java.util.ArrayList;

//VIEW
import view.TelaBusca;
import view.TelaSobre;
import view.TelaPuzzle;


public class ControladorGeral {
    private ControladorTelaSobre controladorTelaSobre;
    private ControladorTelaBusca controladorTelaBusca;
    private ControladorTelaPuzzle controladorTelaPuzzle;

    public ControladorGeral() {
        controladorTelaSobre = new ControladorTelaSobre(new TelaSobre(), this);
        controladorTelaBusca = new ControladorTelaBusca(new TelaBusca(), this);
        controladorTelaPuzzle = new ControladorTelaPuzzle(new TelaPuzzle(), this);
    }

    public void exibirApp() {
        controladorTelaPuzzle.exibir();
    }

    public void exibirTelaSobre() {
        controladorTelaSobre.exibir();
    }

    public void exibirTelaBusca(JFrame other, String profundidade, String tempo) {
        other.setVisible(false);
        controladorTelaBusca.exibir();
        controladorTelaBusca.atualizarProfundidade(profundidade);
        controladorTelaBusca.atualizarTempo(tempo);
    }

    public void exibirTelaPuzzle(JFrame other) {
        other.setVisible(false);
        controladorTelaPuzzle.exibir();
    }

    public void transferirListaDePassos(ArrayList<String> passos){
        controladorTelaBusca.getPassosResolucao().clear();
        for (int i = 0; i < passos.size(); i++){
            controladorTelaBusca.getPassosResolucao().add(passos.get(i));
        }
    }

    public void separarListaDePassos(){
        controladorTelaBusca.separarTubos();
    }

    public void desenharTabelaTelaBusca(){
        controladorTelaBusca.desenharTabela();
    }

    public void desenharTabelaTelaPuzzle(){
        controladorTelaPuzzle.desenharTabela();
    }

    public void ativarListenerVisibilidadeTelaBusca(){
        controladorTelaBusca.ativarListenerVisibilidade();
    }

    public void ativarListenerVisibilidadeTelaPuzzle(){
        controladorTelaPuzzle.ativarListenerVisibilidade();
    }
}
