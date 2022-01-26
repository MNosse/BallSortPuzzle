package model;

//JAVA
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

//BUSCA
import biblioteca.busca.Estado;

public class BallSortPuzzle implements Estado {
    private int quantidadeTubos;
    private String estadoInicialIntegral;
    private String [] estadoInicialSeparado;
    private ArrayList<Tubo> tubos;

    public BallSortPuzzle(String estadoInicialIntegral){
        tubos = new ArrayList<>();
        this.estadoInicialIntegral = estadoInicialIntegral;
        configurarTubos();
    }

    public BallSortPuzzle(ArrayList<Tubo> tubos, String estadoInicialIntegral){
        this.tubos = new ArrayList<>();
        this.estadoInicialIntegral = estadoInicialIntegral;
        for (int i = 0; i < tubos.size(); i++){
            this.tubos.add(tubos.get(i).copiar());
        }
    }

    private void configurarTubos(){
        this.estadoInicialSeparado = estadoInicialIntegral.split("; ");
        this.quantidadeTubos = Integer.parseInt(estadoInicialSeparado[0]);
        for (int i = 1; i <= quantidadeTubos; i++ ){
            if (i <= quantidadeTubos-2){
                String[] cores = estadoInicialSeparado[i].split(", ");
                Bola bola1 = new Bola(cores[0]);
                bola1.dividirValores();
                Bola bola2 = new Bola(cores[1]);
                bola2.dividirValores();
                Bola bola3 = new Bola(cores[2]);
                bola3.dividirValores();
                Bola bola4 = new Bola(cores[3]);
                bola4.dividirValores();
                tubos.add(new Tubo(bola1, bola2, bola3, bola4));
            }else{
                tubos.add(new Tubo());
            }
        }
    }

    @Override
    public String getDescricao() {
        return null;
    }

    @Override
    public boolean ehMeta() {
        boolean resposta = true;
        for (int i = 0; i < quantidadeTubos; i++){
            if (tubos.get(i).todasDaMesmaCor() == false && tubos.get(i).tuboVazio() == false){
                resposta = false;
                break;
            }
        }
        return resposta;
    }

    @Override
    public int custo() {
        return 1;
    }

    @Override
    public List<BallSortPuzzle> sucessores() {
        List<BallSortPuzzle> estadosSucessores = new LinkedList<>();
        for(int i = 0; i < quantidadeTubos; i++){
            for(int j = 0; j < quantidadeTubos; j++){
                if (i != j){
                    if (tubos.get(i).podeMoverParaOutroTubo(tubos.get(j))){
                        BallSortPuzzle novoSucessor = new BallSortPuzzle(this.tubos, this.estadoInicialIntegral);
                        novoSucessor.quantidadeTubos = this.quantidadeTubos;
                        novoSucessor.estadoInicialSeparado = this.estadoInicialSeparado;
                        novoSucessor.tubos.get(i).moverParaOutroTubo(novoSucessor.tubos.get(j));
                        estadosSucessores.add(novoSucessor);
                    }
                }
            }
        }
        return estadosSucessores;
    }

    @Override
    public String toString() {
        String resposta = "\n";
        for (int i = 0; i < quantidadeTubos; i++){
            resposta = resposta+tubos.get(i).toString()+"\n";
        }
        return resposta;
    }

    @Override
    public boolean equals(Object obj) {
        boolean resposta = true;
        BallSortPuzzle outro = (BallSortPuzzle) obj;
        for (int i = 0; i < quantidadeTubos; i++){
            if (this.tubos.get(i).equals(outro.tubos.get(i))){
                resposta = true;
            }else{
                resposta = false;
                break;
            }
        }
        return resposta;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public ArrayList<Tubo> getTubos() {
        return tubos;
    }
}
