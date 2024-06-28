package sistemacoil.modelo.dao;

import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.ProfesorUV;
import sistemacoil.modelo.pojo.RespuestaLoginProfesorUV;
import sistemacoil.utilidades.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistemacoil.modelo.pojo.Coordinador;
import sistemacoil.modelo.pojo.RespuestaLoginCoordinador;

public class AutenticacionDAO {
    
    public static RespuestaLoginProfesorUV iniciarSesionProfesorUV(String numPersonal, String password) {
        RespuestaLoginProfesorUV respuesta = new RespuestaLoginProfesorUV();
        respuesta.setError(true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        
        if (conexionBD != null) {
            PreparedStatement prepararSentencia = null;
            ResultSet resultadoSentencia = null;
            try {
                System.out.println("Conexión a la base de datos exitosa");
                String consulta = "SELECT idProfesorUV, numPersonal, nombre, apellidoPaterno, apellidoMaterno, correo, telefono "
                        + "FROM profesoruv "
                        + "WHERE numPersonal = ? "
                        + "AND password = ?";
                prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, numPersonal);
                prepararSentencia.setString(2, password);
                resultadoSentencia = prepararSentencia.executeQuery();
                
                if (resultadoSentencia.next()) {
                    System.out.println("Usuario encontrado en la base de datos");
                    respuesta.setError(false);
                    respuesta.setMensaje("Usuario identificado correctamente");
                    ProfesorUV profesorUV = new ProfesorUV();
                    profesorUV.setIdProfesorUV(resultadoSentencia.getInt("idProfesorUV"));
                    profesorUV.setNumPersonal(resultadoSentencia.getString("numPersonal"));
                    profesorUV.setNombre(resultadoSentencia.getString("nombre"));
                    profesorUV.setApellidoPaterno(resultadoSentencia.getString("apellidoPaterno"));
                    profesorUV.setApellidoMaterno(resultadoSentencia.getString("apellidoMaterno"));
                    profesorUV.setCorreo(resultadoSentencia.getString("correo"));
                    profesorUV.setTelefono(resultadoSentencia.getString("telefono"));
                    respuesta.setProfesorUV(profesorUV);
                } else {
                    System.out.println("Número de personal y/o contraseña incorrectos");
                    respuesta.setMensaje("Número de personal y/o contraseña incorrectos, favor de verificarlos.");
                }
            } catch (SQLException e) {
                System.out.println("Error al ejecutar la consulta: " + e.getMessage());
                respuesta.setMensaje(e.getMessage());
            } finally {
                try {
                    if (resultadoSentencia != null) {
                        resultadoSentencia.close();
                    }
                    if (prepararSentencia != null) {
                        prepararSentencia.close();
                    }
                    if (conexionBD != null) {
                        conexionBD.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Error de conexión a la base de datos");
            respuesta.setMensaje(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    public static RespuestaLoginCoordinador iniciarSesionCoordinador(String numPersonal, String password){
        RespuestaLoginCoordinador respuesta = new RespuestaLoginCoordinador();
        respuesta.setError(true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            try{
                String consulta = "SELECT idCoordinador, numPersonal, nombre, apellidoPaterno, apellidoMaterno "
                        + "FROM coordinador "
                        + "WHERE numPersonal = ? "
                        + "AND password = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, numPersonal);
                prepararSentencia.setString(2, password);
                prepararSentencia.executeQuery();
                ResultSet resultadoSentencia = prepararSentencia.executeQuery();
                if(resultadoSentencia.next()){
                    respuesta.setError(false);
                    respuesta.setMensaje("Usuario identificado correctamente");
                    Coordinador coordinador = new Coordinador();
                    coordinador.setIdCoordinador(resultadoSentencia.getInt("idCoordinador"));
                    coordinador.setNumPersonal(resultadoSentencia.getString("numPersonal"));
                    coordinador.setNombre(resultadoSentencia.getString("nombre"));
                    coordinador.setApellidoPaterno(resultadoSentencia.getString("apellidoPaterno"));
                    coordinador.setApellidoMaterno(resultadoSentencia.getString("apellidoMaterno"));
                    respuesta.setCoordinador(coordinador);
                }else{
                    respuesta.setMensaje("Número de personal y/o contraseña incorrectos, favor de verificarlos.");
                }
                conexionBD.close();
            }catch(SQLException e){
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            respuesta.setMensaje(Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
}