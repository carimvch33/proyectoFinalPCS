package sistemacoil.modelo.pojo;

/**
 *
 * @author jafet
 */
public class AreaAcademica {
    private int idAreaAcademica;
    private String nombre;
    private String clave;

    public AreaAcademica() {
    }

    public AreaAcademica(int idAreaAcademica, String nombre, String clave) {
        this.idAreaAcademica = idAreaAcademica;
        this.nombre = nombre;
        this.clave = clave;
    }

    public int getIdAreaAcademica() {
        return idAreaAcademica;
    }

    public void setIdAreaAcademica(int idAreaAcademica) {
        this.idAreaAcademica = idAreaAcademica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}
