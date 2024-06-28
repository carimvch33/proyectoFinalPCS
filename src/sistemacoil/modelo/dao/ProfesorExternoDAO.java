package sistemacoil.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.ProfesorExterno;
import sistemacoil.utilidades.Constantes;
/**
 *
 * @author jafet
 */

public class ProfesorExternoDAO {
    
    public static int registrarProfesorExterno(ProfesorExterno profesorExterno) {
        int idProfesorExterno = -1;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            String consulta = "INSERT INTO profesorexterno (nombre, apellidoPaterno, apellidoMaterno, departamento, carrera, correo, telefono, idUniversidad, idIdioma, idPais) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
                prepararSentencia.setString(1, profesorExterno.getNombre());
                prepararSentencia.setString(2, profesorExterno.getApellidoPaterno());
                prepararSentencia.setString(3, profesorExterno.getApellidoMaterno());
                prepararSentencia.setString(4, profesorExterno.getDepartamento());
                prepararSentencia.setString(5, profesorExterno.getCarrera());
                prepararSentencia.setString(6, profesorExterno.getCorreo());
                prepararSentencia.setString(7, profesorExterno.getTelefono());
                prepararSentencia.setInt(8, profesorExterno.getIdUniversidad());
                prepararSentencia.setInt(9, profesorExterno.getIdIdioma());
                prepararSentencia.setInt(10, profesorExterno.getIdPais());

                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    ResultSet rs = prepararSentencia.getGeneratedKeys();
                    if (rs.next()) {
                        idProfesorExterno = rs.getInt(1);
                    }
                }
                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return idProfesorExterno;
    }
    
    public static boolean consultarExistenciaProfesorExterno(String nombre, String apellidoPaterno, String apellidoMaterno) {
        boolean esDuplicado = false;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT COUNT(*) "
                        + "FROM profesorExterno "
                        + "WHERE nombre = ? AND apellidoPaterno = ? AND apellidoMaterno = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, nombre);
                prepararSentencia.setString(2, apellidoPaterno);
                prepararSentencia.setString(3, apellidoMaterno);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next() && resultado.getInt(1) > 0)
                    esDuplicado = true;
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return esDuplicado;
    }

    public static String obtenerAbreviaturaUniversidad(int idUniversidad) {
        String abreviatura = null;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT abreviatura FROM universidad WHERE idUniversidad = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idUniversidad);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    abreviatura = resultado.getString("abreviatura");
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return abreviatura;
    }
}