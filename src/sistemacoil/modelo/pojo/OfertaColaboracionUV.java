package sistemacoil.modelo.pojo;

public class OfertaColaboracionUV {

    private Integer idOfertaColaboracionUV;
    private String nombreColaboracion;
    private String objetivoCurso;
    private String perfilEstudiante;
    private String temaInteres;
    private String informacionAdicional;
    private String estado;
    private Integer idIdioma;
    private Integer idAreaAcademica;
    private Integer idDependencia;
    private Integer idPeriodo;
    private Integer idExperienciaEducativa;
    private Integer idProgramaEducativo;
    private String idioma;
    private String areaAcademica;
    private String dependencia;
    private String programaEducativo;
    private String periodo;
    private String experienciaEducativa;
    private String campus;
    private Integer idProfesorUV;

    public OfertaColaboracionUV() {
    }

    public OfertaColaboracionUV(Integer idOfertaColaboracionUV, String nombreColaboracion, String objetivoCurso, String perfilEstudiante, String temaInteres, String informacionAdicional, String estado, Integer idIdioma, Integer idAreaAcademica, Integer idDependencia, Integer idPeriodo, Integer idExperienciaEducativa, Integer idProgramaEducativo, String idioma, String areaAcademica, String dependencia, String periodo, String experienciaEducativa, String campus, Integer idProfesorUV) {
        this.idOfertaColaboracionUV = idOfertaColaboracionUV;
        this.nombreColaboracion = nombreColaboracion;
        this.objetivoCurso = objetivoCurso;
        this.perfilEstudiante = perfilEstudiante;
        this.temaInteres = temaInteres;
        this.informacionAdicional = informacionAdicional;
        this.estado = estado;
        this.idIdioma = idIdioma;
        this.idAreaAcademica = idAreaAcademica;
        this.idDependencia = idDependencia;
        this.idPeriodo = idPeriodo;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.idProgramaEducativo = idProgramaEducativo; // Nuevo campo
        this.idioma = idioma;
        this.areaAcademica = areaAcademica;
        this.dependencia = dependencia;
        this.periodo = periodo;
        this.experienciaEducativa = experienciaEducativa;
        this.campus = campus;
        this.idProfesorUV = idProfesorUV;
    }

    // Getters y Setters

    public Integer getIdOfertaColaboracionUV() {
        return idOfertaColaboracionUV;
    }

    public void setIdOfertaColaboracionUV(Integer idOfertaColaboracionUV) {
        this.idOfertaColaboracionUV = idOfertaColaboracionUV;
    }

    public String getNombreColaboracion() {
        return nombreColaboracion;
    }

    public void setNombreColaboracion(String nombreColaboracion) {
        this.nombreColaboracion = nombreColaboracion;
    }

    public String getObjetivoCurso() {
        return objetivoCurso;
    }

    public void setObjetivoCurso(String objetivoCurso) {
        this.objetivoCurso = objetivoCurso;
    }

    public String getPerfilEstudiante() {
        return perfilEstudiante;
    }

    public void setPerfilEstudiante(String perfilEstudiante) {
        this.perfilEstudiante = perfilEstudiante;
    }

    public String getTemaInteres() {
        return temaInteres;
    }

    public void setTemaInteres(String temaInteres) {
        this.temaInteres = temaInteres;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(Integer idIdioma) {
        this.idIdioma = idIdioma;
    }

    public Integer getIdAreaAcademica() {
        return idAreaAcademica;
    }

    public void setIdAreaAcademica(Integer idAreaAcadematica) {
        this.idAreaAcademica = idAreaAcadematica;
    }

    public Integer getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(Integer idDependencia) {
        this.idDependencia = idDependencia;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(Integer idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public Integer getIdProgramaEducativo() {
        return idProgramaEducativo; // Nuevo getter
    }

    public void setIdProgramaEducativo(Integer idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo; // Nuevo setter
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getAreaAcademica() {
        return areaAcademica;
    }

    public void setAreaAcademica(String areaAcadematica) {
        this.areaAcademica = areaAcadematica;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(String experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Integer getIdProfesorUV() {
        return idProfesorUV;
    }

    public void setIdProfesorUV(Integer idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
    }
}