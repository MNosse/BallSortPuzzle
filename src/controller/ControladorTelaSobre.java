package controller;

//VIEW
import view.TelaSobre;

public class ControladorTelaSobre {
    private TelaSobre telaSobre;
    private ControladorGeral controladorGeral;

    public ControladorTelaSobre(TelaSobre telaSobre, ControladorGeral controladorGeral){
        this.telaSobre = telaSobre;
        this.controladorGeral = controladorGeral;
    }

    public void exibir(){
        telaSobre.setVisible(true);
    }
}
