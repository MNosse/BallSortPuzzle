package biblioteca.busca;

import java.util.List;

/**
 *   Algoritmos de Busca (geral, qquer problema)
 *   
 *   Busca a solucao por "subida da montanha"
 *                        ------------------
 *
 *   @author Jomi Fred Hubner
 */
public class SubidaMontanha extends BuscaHeuristica {
    
    /** busca sem mostrar status */
    public SubidaMontanha() {
    }
    
    /** busca mostrando status */
    public SubidaMontanha(MostraStatusConsole ms) {
        super(ms);
    }

    public Nodo busca(Estado corrente) {
        status.inicia();
        initFechados();

        Estado melhor = corrente;
        while (!parar && corrente != null) {
            
            List<? extends Estado> filhos = corrente.sucessores();
            if (filhos.size() == 0) {
                corrente = ((Aleatorio)corrente).geraAleatorio(); // comeca em outro lugar aleatorio
                continue;
            }

            // acha o menor h entre os filhos
            Estado melhorFilho = null;
            int melhorH = 0;
            for (Estado e: filhos) {
                if (melhorFilho == null) {
                    melhorFilho = e;
                    melhorH = ((Heuristica)e).h();
                } else if ( ((Heuristica)e).h() < ((Heuristica)melhorFilho).h()) {
                    melhorFilho = e;
                    melhorH = ((Heuristica)e).h();
                }
            }
            
            status.nroVisitados++;
            
            // se nao tem filho melhor que corrente
            if (((Heuristica)melhorFilho).h() >= ((Heuristica)corrente).h()) {
                if (corrente.ehMeta()) {
                    status.termina(true);
                    return new Nodo(corrente, null);
                } else { // maximo local
                    if (((Heuristica)corrente).h() < ((Heuristica)melhor).h()) {
                        melhor = corrente;
                    }
                    corrente = ((Aleatorio)corrente).geraAleatorio(); // comeca em outro lugar aleatorio
                }
            } else { // tem filho melhor que corrente
                corrente = melhorFilho;
            }
        }
        status.termina(false);
        return null;
    }
    
    public String toString() {
    	return "BSM - busca com subida da montanha";
    }
}
