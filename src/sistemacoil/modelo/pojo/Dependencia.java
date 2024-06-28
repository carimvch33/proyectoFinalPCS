package sistemacoil.modelo.pojo;

/**
 *
 * @author jafet
 */
public class Dependencia {
    private int idDependencia;
    private String nombre;
    private String campus;
    private String municipio;
    private String clave;
    private int idAreaAcademica;

    public Dependencia() {
    }

    public Dependencia(int idDependencia, String nombre, String campus, String municipio, String clave, int idAreaAcademica) {
        this.idDependencia = idDependencia;
        this.nombre = nombre;
        this.campus = campus;
        this.municipio = municipio;
        this.clave = clave;
        this.idAreaAcademica = idAreaAcademica;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdAreaAcademica() {
        return idAreaAcademica;
    }

    public void setIdAreaAcademica(int idAreaAcademica) {
        this.idAreaAcademica = idAreaAcademica;
    }
    
    @Override
    public String toString(){
        return this.nombre;
    }
}
