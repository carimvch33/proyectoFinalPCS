package sistemacoil.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.Universidad;
import sistemacoil.utilidades.Constantes;


/**
 *
 * @author jafet
 */
public class UniversidadDAO {
    public static List<Universidad> obtenerUniversidadesPorPais(int idPais) {
        List<Universidad> universidades = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idUniversidad, nombre, abreviatura "
                        + "FROM universidad WHERE idPais = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idPais);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    Universidad universidad = new Universidad();
                    universidad.setIdUniversidad(resultado.getInt("idUniversidad"));
                    universidad.setNombre(resultado.getString("nombre"));
                    universidad.setSiglasUniversidad(resultado.getString("abreviatura"));
                    universidades.add(universidad);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return universidades;
    }

}
