package sistemacoil.modelo.pojo;

import java.sql.Timestamp;

/**
 *
 * @author jafet
 */
public class Evidencia {
    private int idEvidencia;
    private byte[] archivoEvidencia;
    private Timestamp fechaSubida;
    private int idColaboracion;

    public Evidencia() {
    }

    public Evidencia(int idEvidencia, byte[] archivoEvidencia, Timestamp fechaSubida, int idColaboracion) {
        this.idEvidencia = idEvidencia;
        this.archivoEvidencia = archivoEvidencia;
        this.fechaSubida = fechaSubida;
        this.idColaboracion = idColaboracion;
    }

    public int getIdEvidencia() {
        return idEvidencia;
    }

    public void setIdEvidencia(int idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public byte[] getArchivoEvidencia() {
        return archivoEvidencia;
    }

    public void setArchivoEvidencia(byte[] archivoEvidencia) {
        this.archivoEvidencia = archivoEvidencia;
    }

    public Timestamp getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Timestamp fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public int getIdColaboracion() {
        return idColaboracion;
    }

    public void setIdColaboracion(int idColaboracion) {
        this.idColaboracion = idColaboracion;
    }
}