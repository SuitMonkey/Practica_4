package servicios;

import modulo.Articulo;
import modulo.Etiqueta;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Francis Cáceres on 15/6/2016.
 */
public class ArticuloQueries extends GestionDB<Articulo> {

    private static ArticuloQueries instancia;

    public ArticuloQueries() {
        super(Articulo.class);
    }

    public static ArticuloQueries getInstancia() {
        if(instancia==null){
            instancia = new ArticuloQueries();
        }
        return instancia;
    }

    public List<Articulo> findAllSorted(){
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Articulo.findAllSorted");
        List<Articulo> lista = query.getResultList();
        return lista;
    }

    public List<Articulo> findAllByTagsSorted(Etiqueta tag){
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Articulo.findAllByTagsSorted");
        List<Articulo> la = query.setParameter("tag", tag).getResultList();
        return la;
    }

}
