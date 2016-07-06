package modulo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Francis Cáceres on 12/6/2016.
 */
@Entity
public class Comentario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String comentario;
    @ManyToOne
    private Usuario autor;
    @ManyToOne
    private Articulo articulo;
    @OneToMany(mappedBy = "Coment")
    private List<LikeC> likes;

    public Comentario() {

    }

    public Comentario( String comentario, Usuario autor, Articulo articulo) {
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public List<LikeC> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeC> likes) {
        this.likes = likes;
    }
}
