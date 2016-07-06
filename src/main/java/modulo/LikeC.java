package modulo;

import javax.persistence.*;

/**
 * Created by Ernesto on 04-Jul-16.
 */
@Entity
public class LikeC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Boolean Like;
    @ManyToOne
    private Comentario Coment;
    @ManyToOne
    private Usuario usuario;

    public LikeC(){}

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

    public Comentario getComent() {
        return Coment;
    }

    public void setComent(Comentario coment) {
        Coment = coment;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
