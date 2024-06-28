package sistemacoil.modelo.pojo;

public class Coordinador {

    private Integer idCoordinador;
    private String numPersonal;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Coordinador() {
    }

    public Coordinador(Integer idCoordinador, String numPersonal, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.idCoordinador = idCoordinador;
        this.numPersonal = numPersonal;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    public Integer getIdCoordinador() {
        return idCoordinador;
    }

    public void setIdCoordinador(Integer idCoordinador) {
        this.idCoordinador = idCoordinador;
    }

    public String getNumPersonal() {
        return numPersonal;
    }

    public void setNumPersonal(String numPersonal) {
        this.numPersonal = numPersonal;
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
}