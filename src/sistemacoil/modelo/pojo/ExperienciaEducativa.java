package sistemacoil.modelo.pojo;

/**
 *
 * @author jafet
 */
public class ExperienciaEducativa {
    private Integer idExperienciaEducativa;
    private String nrc;
    private String nombre;
    private int numCreditos;
    private int idProgramaEducativo;

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(int idExperienciaEducativa, String nrc, String nombre, int numCreditos, int idProgramaEducativo) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.nrc = nrc;
        this.nombre = nombre;
        this.numCreditos = numCreditos;
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumCreditos() {
        return numCreditos;
    }

    public void setNumCreditos(int numCreditos) {
        this.numCreditos = numCreditos;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }
    
    @Override
    public String toString() {
        return this.nombre + " (" + this.nrc + ")";
    }
}
