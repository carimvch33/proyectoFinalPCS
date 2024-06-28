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
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            String sentencia = "INSERT INTO ofertacolaboracionexterna "
                    + "(titulo, periodo, idProfesorExterno, asignatura, idIdioma, idPais, idUniversidad, "
                    + "estado, correo, carrera, departamento) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, ofertaColaboracionExterna.getTitulo());
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
                conexionBD.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}