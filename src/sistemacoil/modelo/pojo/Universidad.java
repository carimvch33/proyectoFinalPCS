package sistemacoil.modelo.pojo;

/**
 *
 * @author jafet
 */
public class Universidad {
    private int idUniversidad;
    private String nombre;
    private String siglasUniversidad;
    private int idPais;

    public Universidad() {
    }

    public Universidad(int idUniversidad, String nombre, String siglasUniversidad, int idPais) {
        this.idUniversidad = idUniversidad;
        this.nombre = nombre;
        this.siglasUniversidad = siglasUniversidad;
        this.idPais = idPais;
    }

    public int getIdUniversidad() {
        return idUniversidad;
    }

    public void setIdUniversidad(int idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSiglasUniversidad() {
        return siglasUniversidad;
    }

    public void setSiglasUniversidad(String siglasUniversidad) {
        this.siglasUniversidad = siglasUniversidad;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }
    
    @Override
    public String toString() {
        return this.nombre + " (" + this.siglasUniversidad + ")";
    }
}
