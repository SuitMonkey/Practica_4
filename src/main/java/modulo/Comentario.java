package modulo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Francis Cáceres on 12/6/2016.
 */
@Entity
public class Comentario implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private  String comentario;
    @Transient
    private Usuario autor;
//    @Transient
    @ManyToOne
    private Articulo articulo;

    public Comentario() {

    }

    public Comentario(int id, String comentario, Usuario autor, Articulo articulo) {
        //this.id = id;
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
}
