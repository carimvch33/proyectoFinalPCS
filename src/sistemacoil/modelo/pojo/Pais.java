package sistemacoil.modelo.pojo;

/**
 *
 * @author jafet
 */
public class Pais {
    private int idPais;
    private String nombre;
    private String ladaTelefono;

    public Pais() {
    }

    public Pais(int idPais, String nombre, String ladaTelefono) {
        this.idPais = idPais;
        this.nombre = nombre;
        this.ladaTelefono = ladaTelefono;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLadaTelefono() {
        return ladaTelefono;
    }

    public void setLadaTelefono(String ladaTelefono) {
        this.ladaTelefono = ladaTelefono;
    }
    
    @Override
    public String toString() {
        return this.nombre;
    }
}
