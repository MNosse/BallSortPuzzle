package biblioteca.busca;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *   Algoritmos de Busca (geral, qquer problema)
 *
 *   @author Jomi Fred Hubner
 *  
 *  Tratamento com Tipos genericos adicionados por Adilson Vahldick.
 *  
 */

public abstract class Busca<E extends Estado> {

	protected boolean parar = false;
    protected boolean podar = true;
    protected boolean usarFechado = true;

    protected Status status = new Status(); // a classe que tem o status (model)
    protected MostraStatusConsole mstatus = null; // a classe que mostra o stauts (view)

    private Map<E,Integer> fechados = null; // mapeia o estado para um custo g
    
    /** busca sem mostrar status */
    public Busca() {
    }

    /** busca mostrando status */
    public Busca(MostraStatusConsole ms) {
        setMostra(ms);
    }
    
    protected void initFechados() {
    	fechados = new HashMap<E,Integer>();
    }
    
    public Status getStatus() {
        return status;
    }
    
    public Status novoStatus() {
        status = new Status();
        if (mstatus != null) {
            mstatus.setStatus(status);
            status.setMostra(mstatus);
        }
        return status;
    }
    
    public void setMostra(MostraStatusConsole ms) {
        mstatus = ms;
        ms.setStatus(status);
        status.setMostra(ms);        
    }
    
    public String toString() {
    	return "Algoritmo de busca geral";
    }
    
    public abstract Nodo busca(E inicial) throws Exception;
    
    public void setParar(boolean b) {
        parar = b;
    }
    public void para() {
    	parar = true;
        status.termina(false);
    }

    public void setPodar(boolean b) {
    	podar = b;
    }
    
    public void usarFechados(boolean b) {
    	usarFechado = b;
    }
    
    /**
     * gera uma lista de sucessores do nodo.
     */
    public List<Nodo> sucessores(Nodo pai) {
        return soNovos(pai.estado.sucessores(),pai); // lista de todos os sucessores
    }

    public List<Nodo> antecessores(Nodo pai) {
        try {
            return soNovos( ((Antecessor)pai.estado).antecessores(),pai);
        } catch (Exception e) {
            System.err.println("O estado "+pai.estado+" nao implementa antecessores!");
            return new LinkedList<Nodo>();
        }
    }
    

    private List<Nodo> soNovos(List<E> estados, Nodo pai) {
        List<Nodo> sucNodo   = new LinkedList<Nodo>(); // a lista de sucessores novos
        for (E e: estados) {
            Nodo filho = new Nodo( e, pai);
            if (podar) {
                if (usarFechado && fechados != null) {
                    Integer custo = fechados.get(e);
                    if (custo == null || filho.g < custo.intValue()) { // nao esta em fechados ou tem custo menor
                        sucNodo.add(filho);
                        fechados.put(e, filho.g);
                    }
                } else if (filho.ehDescendenteNovo(pai)) { // poda os filhos que tem um ascensor igual a ele
                    sucNodo.add(filho);
                }
            } else {
                sucNodo.add(filho);
            }
        }
        return sucNodo;
    }
}
