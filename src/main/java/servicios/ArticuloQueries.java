package servicios;

import modulo.Articulo;

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

    public List<Articulo> findAllByNombre(String nombre){
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Articulo.findAllByName");
        query.setParameter("titulo", nombre+"%");
        List<Articulo> lista = query.getResultList();
        return lista;
    }

}
