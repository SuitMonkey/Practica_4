package modulo;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Ernesto on 04-Jul-16.
 */
@Entity
public class LikeA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Boolean Like;
    @ManyToOne
    private Articulo articulo;
    @ManyToOne
    private Usuario usuario;


    public LikeA(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getLike() {
        return Like;
    }

    public void setLike(Boolean like) {
        Like = like;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
