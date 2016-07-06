package modulo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * Created by Francis Cáceres on 12/6/2016.
 */

@Entity
@NamedQueries(
        {@NamedQuery(name = "Articulo.findAllSorted", query = "SELECT a FROM Articulo a order by a.fecha desc"),
                @NamedQuery(name = "Articulo.findAllByTagsSorted", query = "SELECT a FROM Articulo a where :tag member of a.listaEtiqueta order by a.fecha desc")})
public class Articulo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    @Column(length = 1000)
    private String cuerpo;
    @ManyToOne
    private Usuario autor;
    @OrderBy
    private Date fecha;
    @OneToMany(mappedBy = "articulo",fetch=FetchType.EAGER)
    private List<Comentario> listaComentario;
    @OneToMany(fetch=FetchType.EAGER)
    private List<Etiqueta> listaEtiqueta;
    @OneToMany(mappedBy = "articulo")
    private List<LikeA> likes;

    public Articulo(){

    }

    public Articulo(String titulo, String cuerpo, Usuario autor, Date fecha, List<Comentario> listaComentario, List<Etiqueta> listaEtiqueta) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
        this.listaComentario = listaComentario;
        this.listaEtiqueta = listaEtiqueta;

        fecha = new Date();
        java.sql.Date fechas = new java.sql.Date(fecha.getTime());

        this.setFecha(fechas);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Comentario> getListaComentario() {
        return listaComentario;
    }

    public void setListaComentario(List<Comentario> listaComentario) {
        this.listaComentario = listaComentario;
    }

    public List<Etiqueta> getListaEtiqueta() {
        return listaEtiqueta;
    }

    public void setListaEtiqueta(List<Etiqueta> listaEtiqueta) {
        this.listaEtiqueta = listaEtiqueta;
    }
}
