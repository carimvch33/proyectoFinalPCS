package sistemacoil.modelo.pojo;

public class OfertaColaboracionExterna {
    
    private Integer idOfertaColaboracionExterna;
    private String nombreColaboracion;
    private String correo;
    private String carrera;
    private String profesorExterno;
    private String asignatura;
    private String departamento;
    private String duracion;
    private String estado;
    private Integer idIdioma;
    private Integer idPais;
    private Integer idUniversidad;
    private String idioma;
    private String pais;
    private String universidad;
    private Integer idProfesorExterno;

    public OfertaColaboracionExterna() {
    }

    public OfertaColaboracionExterna(Integer idOfertaColaboracionExterna, String nombreColaboracion, String correo, String carrera, String profesorExterno, String asignatura, String departamento, String duracion, String estado, Integer idIdioma, Integer idPais, Integer idUniversidad, String idioma, String pais, String universidad, Integer idProfesorExterno) {
        this.idOfertaColaboracionExterna = idOfertaColaboracionExterna;
        this.nombreColaboracion = nombreColaboracion;
        this.correo = correo;
        this.carrera = carrera;
        this.profesorExterno = profesorExterno;
        this.asignatura = asignatura;
        this.departamento = departamento;
        this.duracion = duracion;
        this.estado = estado;
        this.idIdioma = idIdioma;
        this.idPais = idPais;
        this.idUniversidad = idUniversidad;
        this.idioma = idioma;
        this.pais = pais;
        this.universidad = universidad;
        this.idProfesorExterno = idProfesorExterno;
    }

    public Integer getIdOfertaColaboracionExterna() {
        return idOfertaColaboracionExterna;
    }

    public void setIdOfertaColaboracionExterna(Integer idOfertaColaboracionExterna) {
        this.idOfertaColaboracionExterna = idOfertaColaboracionExterna;
    }

    public String getNombreColaboracion() {
        return nombreColaboracion;
    }

    public void setNombreColaboracion(String nombreColaboracion) {
        this.nombreColaboracion = nombreColaboracion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getProfesorExterno() {
        return profesorExterno;
    }

    public void setProfesorExterno(String profesorExterno) {
        this.profesorExterno = profesorExterno;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
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

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public Integer getIdUniversidad() {
        return idUniversidad;
    }

    public void setIdUniversidad(Integer idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public Integer getIdProfesorExterno() {
        return idProfesorExterno;
    }

    public void setIdProfesorExterno(Integer idProfesorExterno) {
        this.idProfesorExterno = idProfesorExterno;
    }
}
