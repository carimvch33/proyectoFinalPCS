package sistemacoil.modelo.pojo;

public class RespuestaLoginCoordinador {
    
    private Boolean error;
    private String mensaje;
    private Coordinador coordinador;

    public RespuestaLoginCoordinador() {
    }

    public RespuestaLoginCoordinador(Boolean error, String mensaje, Coordinador coordinador) {
        this.error = error;
        this.mensaje = mensaje;
        this.coordinador = coordinador;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
}