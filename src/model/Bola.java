package model;

public class Bola {
    private String cor;
    private int r = 0;
    private int g = 0;
    private int b = 0;

    public Bola(String cor){
        this.cor = cor;
    }

    public void dividirValores(){
        String[] cores = cor.split("-");
        r = Integer.parseInt(cores[0]);
        g = Integer.parseInt(cores[1]);
        b = Integer.parseInt(cores[2]);
    }

    public String getCor() {
        return cor;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    @Override
    public String toString() {
        return "Bola "+cor;
    }
}
