package sistemacoil.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.Idioma;
import sistemacoil.modelo.pojo.Pais;
import sistemacoil.utilidades.Constantes;

/**
 *
 * @author jafet
 */
public class CatalogoDAO {

    public static List<Pais> obtenerPaises() {
        List<Pais> paises = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idPais, nombre, ladaTelefono "
                        + "FROM pais";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    Pais pais = new Pais();
                    pais.setIdPais(resultado.getInt("idPais"));
                    pais.setNombre(resultado.getString("nombre"));
                    pais.setLadaTelefono(resultado.getString("ladaTelefono"));
                    paises.add(pais);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return paises;
    }
    
    public static List<Idioma> obtenerIdiomas() {
        List<Idioma> idiomas = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idIdioma, nombre "
                        + "FROM idioma";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    Idioma idioma = new Idioma();
                    idioma.setIdIdioma(resultado.getInt("idIdioma"));
                    idioma.setNombre(resultado.getString("nombre"));
                    idiomas.add(idioma);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return idiomas;
    }
}