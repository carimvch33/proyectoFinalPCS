package sistemacoil.modelo.pojo;

/**
 *
 * @author jafet
 */
public class ProgramaEducativo {
    private int idProgramaEducativo;
    private String nombre;
    private String anioPlanEstudio;
    private String clave;
    private int idDependencia;

    public ProgramaEducativo() {
    }

    public ProgramaEducativo(int idProgramaEducativo, String nombre, String anioPlanEstudio, String clave, int idDependencia) {
        this.idProgramaEducativo = idProgramaEducativo;
        this.nombre = nombre;
        this.anioPlanEstudio = anioPlanEstudio;
        this.clave = clave;
        this.idDependencia = idDependencia;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAnioPlanEstudio() {
        return anioPlanEstudio;
    }

    public void setAnioPlanEstudio(String anioPlanEstudio) {
        this.anioPlanEstudio = anioPlanEstudio;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }
    
    @Override
    public String toString() {
        return this.nombre + " (" + this.anioPlanEstudio + ")";
    }
}
