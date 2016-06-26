package modulo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Francis Cáceres on 12/6/2016.
 */

@Entity
public class Etiqueta implements Serializable{
    @Id
    private String etiqueta;

    public Etiqueta(){

    }

    public Etiqueta( String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
