package servicios;

import modulo.Articulo;
import modulo.Etiqueta;
import modulo.LikeA;
import modulo.Usuario;

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


    public void crearEsp(Long Id, List<Etiqueta> le){

        EntityManager em = getEntityManager();


        em.getTransaction().begin();

        try {

//            em.getTransaction().commit();
            Articulo art = em.find(Articulo.class,Id);

            for (Etiqueta it : le) {
                Etiqueta e = em.find(Etiqueta.class,it.getEtiqueta());
                e.setArticulo(art);
                art.addEtiqueta(e);


                //em.merge(e);
            }
            em.getTransaction().commit();

        }catch (Exception ex){
            em.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }

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
    public void flush(Long Id, Usuario usuario)
    {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            int idLike=0;
            Articulo art = em.find(Articulo.class,Id);
            for(LikeA la : art.getLikes())
            {
                if( la.getUsuario().getUsername().equals(usuario.getUsername()))
                {
                    LikeAQueries.getInstancia().eliminar(la.getId());
                    usuario.getLikesA().remove(la);
                    //likes.remove(la);
                    break;
                }
            }
        em.getTransaction().commit();

        }catch (Exception ex){
            em.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }

    }

}
