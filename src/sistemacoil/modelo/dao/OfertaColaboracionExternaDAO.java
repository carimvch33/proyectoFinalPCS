package sistemacoil.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.OfertaColaboracionExterna;
import sistemacoil.utilidades.Constantes;

public class OfertaColaboracionExternaDAO {
    
    public static HashMap<String, Object> registrarOfertaColaboracionExterna(OfertaColaboracionExterna ofertaColaboracionExterna) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);

        if (ofertaColaboracionExterna == null) {
            respuesta.put(Constantes.KEY_MENSAJE, "Oferta de Colaboración Externa es null.");
            return respuesta;
        }

        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String sentencia = "INSERT INTO ofertacolaboracionexterna "
                    + "(nombreColaboracion, duracion, idProfesorExterno, asignatura, idIdioma, idPais, idUniversidad, "
                    + "estado, correo, carrera, departamento) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, ofertaColaboracionExterna.getNombreColaboracion());
                prepararSentencia.setString(2, ofertaColaboracionExterna.getDuracion());
                prepararSentencia.setInt(3, ofertaColaboracionExterna.getIdProfesorExterno());
                prepararSentencia.setString(4, ofertaColaboracionExterna.getAsignatura());
                prepararSentencia.setInt(5, ofertaColaboracionExterna.getIdIdioma());
                prepararSentencia.setInt(6, ofertaColaboracionExterna.getIdPais());
                prepararSentencia.setInt(7, ofertaColaboracionExterna.getIdUniversidad());
                prepararSentencia.setString(8, Constantes.ESTADO_CREADA);
                prepararSentencia.setString(9, ofertaColaboracionExterna.getCorreo());
                prepararSentencia.setString(10, ofertaColaboracionExterna.getCarrera());
                prepararSentencia.setString(11, ofertaColaboracionExterna.getDepartamento());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "La Oferta de Colaboración Externa ha sido registrada correctamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ha ocurrido un error al registrar la Oferta de Colaboración Externa, favor de verificar la información.");
                }
                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, "Error al ejecutar la consulta SQL: " + ex.getMessage());
            } catch (NullPointerException ex) {
                respuesta.put(Constantes.KEY_MENSAJE, "Error: Se ha encontrado un objeto nulo.");
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}
