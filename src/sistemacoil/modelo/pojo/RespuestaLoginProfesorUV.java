package sistemacoil.modelo.pojo;

public class RespuestaLoginProfesorUV {
    
    private Boolean error;
    private String mensaje;
    private ProfesorUV profesorUV;

    public RespuestaLoginProfesorUV() {
    }

    public RespuestaLoginProfesorUV(Boolean error, String mensaje, ProfesorUV profesorUV) {
        this.error = error;
        this.mensaje = mensaje;
        this.profesorUV = profesorUV;
    }

    public Boolean getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public ProfesorUV getProfesorUV() {
        return profesorUV;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setProfesorUV(ProfesorUV profesorUV) {
        this.profesorUV = profesorUV;
    }
}