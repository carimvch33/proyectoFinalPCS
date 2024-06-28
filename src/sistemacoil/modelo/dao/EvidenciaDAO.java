package sistemacoil.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.Evidencia;
import sistemacoil.utilidades.Constantes;

/**
 *
 * @author jafet
 */
public class EvidenciaDAO {
    public static boolean subirEvidencia(Evidencia evidencia) {
        boolean exito = false;
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "INSERT INTO evidencia (archivoEvidencia, idColaboracion) VALUES (?, ?)";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setBytes(1, evidencia.getArchivoEvidencia());
                prepararSentencia.setInt(2, evidencia.getIdColaboracion());

                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    exito = true;
                }

                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return exito;
    }
    
    public static boolean tieneEvidenciaAsociada(int idColaboracion) {
        boolean tieneEvidencia = false;
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "SELECT COUNT(*) FROM evidencia WHERE idColaboracion = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultado = prepararSentencia.executeQuery();

                if (resultado.next()) {
                    tieneEvidencia = resultado.getInt(1) > 0;
                }

                resultado.close();
                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return tieneEvidencia;
    }
}
