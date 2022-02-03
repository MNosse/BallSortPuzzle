package model;

//JAVA
import java.util.Stack;
import java.util.ArrayList;

public class Tubo {
    private Stack<Bola> tubo;

    public Tubo(){
        tubo = new Stack<>();
    }

    public Tubo(ArrayList<Bola> bolas){
        tubo = new Stack<>();
        for (int i = 0; i < bolas.size(); i++){
            tubo.push(bolas.get(i));
        }
    }

    public Tubo(Bola bola1, Bola bola2, Bola bola3, Bola bola4){
        tubo = new Stack<>();
        tubo.push(bola1);
        tubo.push(bola2);
        tubo.push(bola3);
        tubo.push(bola4);
    }

    private ArrayList<Bola> conteudoTubo(){
        ArrayList<Bola> resposta = new ArrayList<>();
        resposta.addAll(tubo);
        return resposta;
    }

    public boolean todasDaMesmaCor(){
        boolean resposta = false;
        ArrayList<Bola> verificar = conteudoTubo();
        if (verificar.size() == 4) {
            if (verificar.get(0).getCor().equals(verificar.get(1).getCor()) &&
                    verificar.get(0).getCor().equals(verificar.get(2).getCor()) &&
                    verificar.get(0).getCor().equals(verificar.get(3).getCor())) {
                resposta = true;
            }
        }
        return resposta;
    }

    public boolean moverParaOutroTubo(Tubo outroTubo){
        if (podeMoverParaOutroTubo(outroTubo) == true){
            outroTubo.getTubo().push(this.getTubo().pop());
            return true;
        }else{
            return false;
        }
    }

    public boolean podeMoverParaOutroTubo(Tubo outroTubo){
        if (this.tuboVazio() != true){
            if (outroTubo.tuboCheio() != true){
                if (outroTubo.tuboVazio() || outroTubo.getTubo().peek().getCor().equals(this.tubo.peek().getCor())){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean tuboCheio(){
        if (conteudoTubo().size() >= 4){
            return true;
        }else{
            return false;
        }
    }

    public boolean tuboVazio(){
        if (conteudoTubo().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public Tubo copiar() {
        if (tuboVazio()){
            return new Tubo();
        }else{
            return new Tubo(conteudoTubo());
        }
    }

    public Stack<Bola> getTubo() {
        return tubo;
    }

    @Override
    public String toString() {
        ArrayList<Bola> conteudo = conteudoTubo();
        String resposta = "[";
        for (int i = 0; i < conteudo.size(); i++){
            if (i == conteudo.size() - 1){
                resposta = resposta + conteudo.get(i);
            }else{
                resposta = resposta + conteudo.get(i) + ", ";
            }
        }
        resposta = resposta + "]";
        return resposta;
    }

    @Override
    public boolean equals(Object obj) {
        boolean resposta = true;
        int tamanho = conteudoTubo().size();
        Tubo outro = (Tubo)obj;
        if (tubo.size() != outro.tubo.size()){
            return false;
        }
        for (int i = 0; i < tamanho; i++){
            if (this.conteudoTubo().get(i).getCor().equals(outro.conteudoTubo().get(i).getCor())){
                resposta = true;
            }else{
                resposta = false;
                break;
            }
        }
        return resposta;
    }
}
