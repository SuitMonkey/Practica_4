package servicios;

import modulo.Articulo;
import modulo.Comentario;

/**
 * Created by Ernesto on 19-Jun-16.
 */
public class ComentarioQueries extends GestionDB<Comentario> {
    private static ComentarioQueries instancia;

    public ComentarioQueries() {
        super(Comentario.class);
    }

    public static ComentarioQueries getInstancia() {
        if(instancia==null){
            instancia = new ComentarioQueries();
        }
        return instancia;
    }


}
