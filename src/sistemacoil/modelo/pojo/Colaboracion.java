package sistemacoil.modelo.pojo;

import java.time.LocalDate;

public class Colaboracion {
    private int idColaboracion;
    private int idProfesorUV;
    private int idProfesorExterno;
    private String nombreProfesorExterno;
    private String nombreColaboracion;
    private LocalDate fechaInicio;
    private LocalDate fechaConclusion;
    private int idIdioma;
    private String idioma;
    private int numEstudiantes;
    private String objetivoCurso;
    private String perfilEstudiante;
    private String temaInteres;
    private String informacionAdicional;
    private int idExperienciaEducativa;
    private String experienciaEducativa;
    private int idProgramaEducativo;
    private String programaEducativo;
    private int idDependencia;
    private String dependencia;
    private int idAreaAcademica;
    private String areaAcademica;
    private int idPeriodo;
    private String periodo;
    private int idOfertaColaboracionUV;
    private int idOfertaColaboracionExterna;
    private byte[] syllabus;
    private String estado;
    private String profesorUV;
    private String motivoCancelacion;

    // Getters and Setters
    public int getIdColaboracion() {
        return idColaboracion;
    }

    public void setIdColaboracion(int idColaboracion) {
        this.idColaboracion = idColaboracion;
    }

    public int getIdProfesorUV() {
        return idProfesorUV;
    }

    public void setIdProfesorUV(int idProfesorUV) {
        this.idProfesorUV = idProfesorUV;
    }

    public int getIdProfesorExterno() {
        return idProfesorExterno;
    }

    public void setIdProfesorExterno(int idProfesorExterno) {
        this.idProfesorExterno = idProfesorExterno;
    }

    public String getNombreProfesorExterno() {
        return nombreProfesorExterno;
    }

    public void setNombreProfesorExterno(String nombreProfesorExterno) {
        this.nombreProfesorExterno = nombreProfesorExterno;
    }

    public String getNombreColaboracion() {
        return nombreColaboracion;
    }

    public void setNombreColaboracion(String nombreColaboracion) {
        this.nombreColaboracion = nombreColaboracion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaConclusion() {
        return fechaConclusion;
    }

    public void setFechaConclusion(LocalDate fechaConclusion) {
        this.fechaConclusion = fechaConclusion;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumEstudiantes() {
        return numEstudiantes;
    }

    public void setNumEstudiantes(int numEstudiantes) {
        this.numEstudiantes = numEstudiantes;
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

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getExperienciaEducativa() {
        return experienciaEducativa;
    }

    public void setExperienciaEducativa(String experienciaEducativa) {
        this.experienciaEducativa = experienciaEducativa;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public String getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(String programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public int getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(int idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public int getIdAreaAcademica() {
        return idAreaAcademica;
    }

    public void setIdAreaAcademica(int idAreaAcademica) {
        this.idAreaAcademica = idAreaAcademica;
    }

    public String getAreaAcademica() {
        return areaAcademica;
    }

    public void setAreaAcademica(String areaAcademica) {
        this.areaAcademica = areaAcademica;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getIdOfertaColaboracionUV() {
        return idOfertaColaboracionUV;
    }

    public void setIdOfertaColaboracionUV(int idOfertaColaboracionUV) {
        this.idOfertaColaboracionUV = idOfertaColaboracionUV;
    }

    public int getIdOfertaColaboracionExterna() {
        return idOfertaColaboracionExterna;
    }

    public void setIdOfertaColaboracionExterna(int idOfertaColaboracionExterna) {
        this.idOfertaColaboracionExterna = idOfertaColaboracionExterna;
    }

    public byte[] getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(byte[] syllabus) {
        this.syllabus = syllabus;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getProfesorUV() {
        return profesorUV;
    }

    public void setProfesorUV(String profesorUV) {
        this.profesorUV = profesorUV;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }
}