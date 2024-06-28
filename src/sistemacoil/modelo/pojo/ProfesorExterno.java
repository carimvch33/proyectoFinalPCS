package sistemacoil.modelo.pojo;

/**
 *
 * @author jafet
 */
public class ProfesorExterno {
    private Integer idProfesorExterno;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String departamento;
    private String carrera;
    private String correo;
    private String telefono;
    private String abreviaturaUniversidad;
    private Integer idPais;
    private Integer idUniversidad;
    private Integer idIdioma;

    public ProfesorExterno() {
    }

    public ProfesorExterno(int idProfesorExterno, String nombre, String apellidoPaterno, String apellidoMaterno, String departamento, String carrera, String correo, String telefono, String abreviaturaUniversidad, int idPais, int idUniversidad, int idIdioma) {
        this.idProfesorExterno = idProfesorExterno;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.departamento = departamento;
        this.carrera = carrera;
        this.correo = correo;
        this.telefono = telefono;
        this.abreviaturaUniversidad = abreviaturaUniversidad;
        this.idPais = idPais;
        this.idUniversidad = idUniversidad;
        this.idIdioma = idIdioma;
    }

    public int getIdProfesorExterno() {
        return idProfesorExterno;
    }

    public void setIdProfesorExterno(int idProfesorExterno) {
        this.idProfesorExterno = idProfesorExterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAbreviaturaUniversidad() {
        return abreviaturaUniversidad;
    }

    public void setAbreviaturaUniversidad(String abreviaturaUniversidad) {
        this.abreviaturaUniversidad = abreviaturaUniversidad;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdUniversidad() {
        return idUniversidad;
    }

    public void setIdUniversidad(int idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }
    
    @Override
    public String toString() {
        return this.apellidoPaterno + "-" + this.apellidoMaterno + ", " + this.nombre + " (" + this.abreviaturaUniversidad + ")";
    }
}
