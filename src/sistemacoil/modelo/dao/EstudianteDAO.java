package sistemacoil.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.Estudiante;
import sistemacoil.utilidades.Constantes;

/**
 * DAO para la gestión de los estudiantes en el sistema COIL.
 */
public class EstudianteDAO {

    public static boolean registrarEstudiante(Estudiante estudiante) {
        boolean exito = false;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            String consulta = "INSERT INTO estudiante (nombre, apellidoPaterno, apellidoMaterno, matricula, correo) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
                prepararSentencia.setString(1, estudiante.getNombre());
                prepararSentencia.setString(2, estudiante.getApellidoPaterno());
                prepararSentencia.setString(3, estudiante.getApellidoMaterno());
                prepararSentencia.setString(4, estudiante.getMatricula());
                prepararSentencia.setString(5, estudiante.getCorreo());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    ResultSet rs = prepararSentencia.getGeneratedKeys();
                    if (rs.next()) {
                        estudiante.setIdEstudiante(rs.getInt(1));
                    }
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

    public static boolean relacionarEstudianteConColaboracion(int idColaboracion, int idEstudiante) {
        boolean exito = false;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            String consulta = "INSERT INTO colaboracion_estudiante (idColaboracion, idEstudiante) VALUES (?, ?)";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                prepararSentencia.setInt(2, idEstudiante);
                int filasAfectadas = prepararSentencia.executeUpdate();
                exito = filasAfectadas > 0;
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

    public static int obtenerIdEstudiante(String matricula) {
        int idEstudiante = -1;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            String consulta = "SELECT idEstudiante FROM estudiante WHERE matricula = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, matricula);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    idEstudiante = resultado.getInt("idEstudiante");
                }
                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return idEstudiante;
    }

    public static List<Estudiante> obtenerEstudiantesPorColaboracion(int idColaboracion) {
        List<Estudiante> estudiantes = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "SELECT e.idEstudiante, e.nombre, e.apellidoPaterno, e.apellidoMaterno, e.matricula, e.correo " +
                              "FROM colaboracion_estudiante ce " +
                              "INNER JOIN estudiante e ON ce.idEstudiante = e.idEstudiante " +
                              "WHERE ce.idColaboracion = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultado = prepararSentencia.executeQuery();

                while (resultado.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setIdEstudiante(resultado.getInt("idEstudiante"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setCorreo(resultado.getString("correo"));
                    estudiantes.add(estudiante);
                }

                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }

        return estudiantes;
    }

    public static boolean eliminarEstudianteDeColaboracion(int idColaboracion, int idEstudiante) {
        boolean exito = false;
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "DELETE FROM colaboracion_estudiante " + 
                    "WHERE idColaboracion = ? AND idEstudiante = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                prepararSentencia.setInt(2, idEstudiante);

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
}
