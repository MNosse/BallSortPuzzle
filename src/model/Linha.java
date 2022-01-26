package model;

public class Linha {
    private int xInicio;
    private int yInicio;
    private int xFinal;
    private int yFinal;

    public Linha(int xInicio, int yInicio, int xFinal, int yFinal) {
        this.xInicio = xInicio;
        this.yInicio = yInicio;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
    }

    public int getxInicio() {
        return xInicio;
    }

    public int getyInicio() {
        return yInicio;
    }

    public int getxFinal() {
        return xFinal;
    }

    public int getyFinal() {
        return yFinal;
    }
}
