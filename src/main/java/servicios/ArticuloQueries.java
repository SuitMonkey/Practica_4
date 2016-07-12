package servicios;

import modulo.*;

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
                Etiqueta e = em.find(Etiqueta.class,it.getId());
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
                   // usuario.getLikesA().remove(la);//NO SE ESTA REMOVIENDO!!!!

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

    public void delete(Long id){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Articulo entidad = em.find(Articulo.class, id);
            em.remove(entidad);
            //em.clear();
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            em.close();
        }

    }

    public void unlinkComent(Long idA, int idC)
    {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        try {
            Articulo articulo = em.find(Articulo.class, idA);
            List<Comentario> lc = articulo.getListaComentario();
            for(Comentario c: lc)
            {
                if(c.getId() == idC)
                {
                    System.out.println(lc.remove(c));
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
