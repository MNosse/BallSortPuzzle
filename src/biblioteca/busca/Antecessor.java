package biblioteca.busca;

/**
 * Interface para estados que tem a funcao antecessores.
 *
 * @author  jomi
 * 
 */
public interface Antecessor {
    public <E extends Estado> java.util.List<E> antecessores();
}
