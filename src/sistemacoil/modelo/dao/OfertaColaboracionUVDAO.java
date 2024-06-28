package sistemacoil.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.OfertaColaboracionUV;
import sistemacoil.utilidades.Constantes;

public class OfertaColaboracionUVDAO {
    
    public static List<OfertaColaboracionUV> obtenerOfertasUVProfesorUV(Integer idProfesorUV) {
        List<OfertaColaboracionUV> ofertasDeColaboracionUV = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT o.idOfertaColaboracionUV, o.nombreColaboracion, o.idExperienciaEducativa, o.objetivoCurso, o.perfilEstudiante, o.temaInteres, o.informacionAdicional, o.estado, o.idIdioma, i.nombre AS 'idioma', o.idAreaAcademica, a.nombre AS 'areaAcademica', o.idDependencia, d.nombre AS 'dependencia', o.idPeriodo, p.nombrePeriodo AS 'periodo', ee.idProgramaEducativo, o.idProfesorUV FROM ofertacolaboracionuv o INNER JOIN idioma i ON o.idIdioma = i.idIdioma INNER JOIN areaacademica a ON o.idAreaAcademica = a.idAreaAcademica INNER JOIN dependencia d ON o.idDependencia = d.idDependencia INNER JOIN periodo p ON o.idPeriodo = p.idPeriodo INNER JOIN experienciaeducativa ee ON o.idExperienciaEducativa = ee.idExperienciaEducativa INNER JOIN profesoruv pr ON o.idProfesorUV = pr.idProfesorUV WHERE o.idProfesorUV = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProfesorUV);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    OfertaColaboracionUV ofertaColaboracionUV = new OfertaColaboracionUV();
                    ofertaColaboracionUV.setIdOfertaColaboracionUV(resultado.getInt("idOfertaColaboracionUV"));
                    ofertaColaboracionUV.setNombreColaboracion(resultado.getString("nombreColaboracion"));
                    ofertaColaboracionUV.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    ofertaColaboracionUV.setObjetivoCurso(resultado.getString("objetivoCurso"));
                    ofertaColaboracionUV.setPerfilEstudiante(resultado.getString("perfilEstudiante"));
                    ofertaColaboracionUV.setTemaInteres(resultado.getString("temaInteres"));
                    ofertaColaboracionUV.setInformacionAdicional(resultado.getString("informacionAdicional"));
                    ofertaColaboracionUV.setEstado(resultado.getString("estado"));
                    ofertaColaboracionUV.setIdIdioma(resultado.getInt("idIdioma"));
                    ofertaColaboracionUV.setIdioma(resultado.getString("idioma"));
                    ofertaColaboracionUV.setIdAreaAcademica(resultado.getInt("idAreaAcademica"));
                    ofertaColaboracionUV.setAreaAcademica(resultado.getString("areaAcademica"));
                    ofertaColaboracionUV.setIdDependencia(resultado.getInt("idDependencia"));
                    ofertaColaboracionUV.setDependencia(resultado.getString("dependencia"));
                    ofertaColaboracionUV.setIdPeriodo(resultado.getInt("idPeriodo"));
                    ofertaColaboracionUV.setPeriodo(resultado.getString("periodo"));
                    ofertaColaboracionUV.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                    ofertaColaboracionUV.setIdProfesorUV(resultado.getInt("idProfesorUV"));
                    ofertasDeColaboracionUV.add(ofertaColaboracionUV);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return ofertasDeColaboracionUV;
    }

    
    public static HashMap<String, Object> registrarOfertaColaboracionUV(OfertaColaboracionUV ofertaColaboracionUV){
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            String sentencia = "INSERT INTO ofertacolaboracionuv (nombreColaboracion, idExperienciaEducativa, objetivoCurso, "
                    + "perfilEstudiante, temaInteres, informacionAdicional, idIdioma, "
                    + "idAreaAcademica, idDependencia, idProfesorUV, idPeriodo, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try{
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, ofertaColaboracionUV.getNombreColaboracion());
                prepararSentencia.setInt(2, ofertaColaboracionUV.getIdExperienciaEducativa());
                prepararSentencia.setString(3, ofertaColaboracionUV.getObjetivoCurso());
                prepararSentencia.setString(4, ofertaColaboracionUV.getPerfilEstudiante());
                prepararSentencia.setString(5, ofertaColaboracionUV.getTemaInteres());
                prepararSentencia.setString(6, ofertaColaboracionUV.getInformacionAdicional());
                prepararSentencia.setInt(7, ofertaColaboracionUV.getIdIdioma());
                prepararSentencia.setInt(8, ofertaColaboracionUV.getIdAreaAcademica());
                prepararSentencia.setInt(9, ofertaColaboracionUV.getIdDependencia());
                prepararSentencia.setInt(10, ofertaColaboracionUV.getIdProfesorUV());
                prepararSentencia.setInt(11, ofertaColaboracionUV.getIdPeriodo());
                prepararSentencia.setString(12, Constantes.ESTADO_EN_ESPERA);
                int filasAfectadas = prepararSentencia.executeUpdate();
                if(filasAfectadas > 0){
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Oferta de Colaboración UV registrada correctamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ha ocurrido un error al registrar la Oferta de Colaboración UV, favor de verificar la información.");
                }
                conexionBD.close();
            } catch(SQLException ex){
                ex.printStackTrace();
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.ERROR_CONEXION);            
        }
        return respuesta;
    }
    
    /*public static List<OfertaColaboracionUV> obtenerOfertasColaboracionUV(int idProfesorUV) {
        List<OfertaColaboracionUV> ofertas = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "SELECT ocuv.idOfertaColaboracionUV, ocuv.nombreColaboracion, ee.nombre "
                    + "AS nombreExperienciaEducativa, p.nombrePeriodo AS nombrePeriodo, d.campus "
                    + "FROM ofertacolaboracionuv ocuv INNER JOIN experienciaeducativa ee "
                    + "ON ocuv.idExperienciaEducativa = ee.idExperienciaEducativa "
                    + "INNER JOIN periodo p ON ocuv.idPeriodo = p.idPeriodo "
                    + "INNER JOIN dependencia d ON ocuv.idDependencia = d.idDependencia "
                    + "WHERE ocuv.estado = 'En espera' AND ocuv.idProfesorUV = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProfesorUV);
                ResultSet resultado = prepararSentencia.executeQuery();

                while (resultado.next()) {
                    OfertaColaboracionUV oferta = new OfertaColaboracionUV();
                    oferta.setIdOfertaColaboracionUV(resultado.getInt("idOfertaColaboracionUV"));
                    oferta.setNombreColaboracion(resultado.getString("nombreColaboracion"));
                    oferta.setExperienciaEducativa(resultado.getString("nombreExperienciaEducativa"));
                    oferta.setPeriodo(resultado.getString("nombrePeriodo"));
                    oferta.setCampus(resultado.getString("campus"));
                    System.out.println("Oferta cargada: " + oferta.getIdOfertaColaboracionUV());
                    ofertas.add(oferta);
                }

                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }

        return ofertas;
    }*/
    
    public static HashMap<String, Object> modificarOfertaColaboracionUV(OfertaColaboracionUV ofertaColaboracionUV){
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if(conexionBD != null){
            String sentencia = "UPDATE ofertacolaboracionuv SET nombreColaboracion = ?, idExperienciaEducativa = ?, objetivoCurso = ?, perfilEstudiante = ?, temaInteres = ?, informacionAdicional = ?, idIdioma = ?, idAreaAcademica = ?, idDependencia = ?, idPeriodo = ? WHERE idOfertaColaboracionUV = ?";
            try{
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, ofertaColaboracionUV.getNombreColaboracion());
                prepararSentencia.setInt(2, ofertaColaboracionUV.getIdExperienciaEducativa());
                prepararSentencia.setString(3, ofertaColaboracionUV.getObjetivoCurso());
                prepararSentencia.setString(4, ofertaColaboracionUV.getPerfilEstudiante());
                prepararSentencia.setString(5, ofertaColaboracionUV.getTemaInteres());
                prepararSentencia.setString(6, ofertaColaboracionUV.getInformacionAdicional());
                prepararSentencia.setInt(7, ofertaColaboracionUV.getIdIdioma());
                prepararSentencia.setInt(8, ofertaColaboracionUV.getIdAreaAcademica());
                prepararSentencia.setInt(9, ofertaColaboracionUV.getIdDependencia());
                prepararSentencia.setInt(10, ofertaColaboracionUV.getIdPeriodo());
                prepararSentencia.setInt(11, ofertaColaboracionUV.getIdOfertaColaboracionUV());
                int filasAfectadas = prepararSentencia.executeUpdate();
                if(filasAfectadas > 0){
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Datos de la Oferta Colaboración UV modificados correctamente.");
                }else{
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ha ocurrido un error al modificar los datos de la Oferta de Colaboración UV, favor de verificar la información.");
                }
                conexionBD.close();
            }catch(SQLException ex){
                respuesta.put(Constantes.KEY_MENSAJE, ex.getMessage());
            }
        }else{
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }
    
    /*public static HashMap<String, Object> obtenerOfertaColaboracionUVPorId(int idOfertaColaboracionUV) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT o.idOfertaColaboracionUV, o.nombreColaboracion, o.idExperienciaEducativa, o.objetivoCurso, "
                        + "o.perfilEstudiante, o.temaInteres, o.informacionAdicional, o.estado, o.idIdioma, i.nombre "
                        + "AS 'idioma', o.idAreaAcademica, a.nombre AS 'areaAcademica', o.idDependencia, d.nombre "
                        + "AS 'dependencia', o.idPeriodo, p.nombrePeriodo "
                        + "AS 'periodo', ee.idProgramaEducativo, o.idProfesorUV FROM ofertacolaboracionuv o "
                        + "INNER JOIN idioma i ON o.idIdioma = i.idIdioma "
                        + "INNER JOIN areaacademica a ON o.idAreaAcademica = a.idAreaAcademica "
                        + "INNER JOIN dependencia d ON o.idDependencia = d.idDependencia INNER JOIN periodo p "
                        + "ON o.idPeriodo = p.idPeriodo INNER JOIN experienciaeducativa ee "
                        + "ON o.idExperienciaEducativa = ee.idExperienciaEducativa "
                        + "WHERE o.idOfertaColaboracionUV = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idOfertaColaboracionUV);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    OfertaColaboracionUV ofertaColaboracionUV = new OfertaColaboracionUV();
                    ofertaColaboracionUV.setIdOfertaColaboracionUV(resultado.getInt("idOfertaColaboracionUV"));
                    ofertaColaboracionUV.setNombreColaboracion(resultado.getString("nombreColaboracion"));
                    ofertaColaboracionUV.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    ofertaColaboracionUV.setObjetivoCurso(resultado.getString("objetivoCurso"));
                    ofertaColaboracionUV.setPerfilEstudiante(resultado.getString("perfilEstudiante"));
                    ofertaColaboracionUV.setTemaInteres(resultado.getString("temaInteres"));
                    ofertaColaboracionUV.setInformacionAdicional(resultado.getString("informacionAdicional"));
                    ofertaColaboracionUV.setEstado(resultado.getString("estado"));
                    ofertaColaboracionUV.setIdIdioma(resultado.getInt("idIdioma"));
                    ofertaColaboracionUV.setIdioma(resultado.getString("idioma"));
                    ofertaColaboracionUV.setIdAreaAcademica(resultado.getInt("idAreaAcademica"));
                    ofertaColaboracionUV.setAreaAcademica(resultado.getString("areaAcademica"));
                    ofertaColaboracionUV.setIdDependencia(resultado.getInt("idDependencia"));
                    ofertaColaboracionUV.setDependencia(resultado.getString("dependencia"));
                    ofertaColaboracionUV.setIdPeriodo(resultado.getInt("idPeriodo"));
                    ofertaColaboracionUV.setPeriodo(resultado.getString("periodo"));
                    ofertaColaboracionUV.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                    ofertaColaboracionUV.setIdProfesorUV(resultado.getInt("idProfesorUV"));
                    System.out.println("ID Oferta Colaboracion UV: " + ofertaColaboracionUV.getIdOfertaColaboracionUV()); // Depuración
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put("ofertaColaboracionUV", ofertaColaboracionUV);
                }
                conexionBD.close();
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        } else {
            respuesta.put(Constantes.KEY_MENSAJE, Constantes.ERROR_CONEXION);
        }
        return respuesta;
    }*/
    
    public static HashMap<String, Object> obtenerOfertaColaboracionUVPorId(int idOfertaColaboracionUV) {
    HashMap<String, Object> respuesta = new HashMap<>();
    respuesta.put(Constantes.KEY_ERROR, true);
    Connection conexionBD = ConexionBD.obtenerConexion();
    if (conexionBD != null) {
        try {
            String consulta = "SELECT o.idOfertaColaboracionUV, o.nombreColaboracion, o.idExperienciaEducativa, o.objetivoCurso, "
                    + "o.perfilEstudiante, o.temaInteres, o.informacionAdicional, o.estado, o.idIdioma, i.nombre "
                    + "AS 'idioma', o.idAreaAcademica, a.nombre AS 'areaAcademica', o.idDependencia, d.nombre "
                    + "AS 'dependencia', o.idPeriodo, p.nombrePeriodo "
                    + "AS 'periodo', ee.idProgramaEducativo, o.idProfesorUV, pe.nombre AS 'programaEducativo', "
                    + "ee.nombre AS 'experienciaEducativa', c.nombre AS 'campus' "
                    + "FROM ofertacolaboracionuv o "
                    + "INNER JOIN idioma i ON o.idIdioma = i.idIdioma "
                    + "INNER JOIN areaacademica a ON o.idAreaAcademica = a.idAreaAcademica "
                    + "INNER JOIN dependencia d ON o.idDependencia = d.idDependencia "
                    + "INNER JOIN periodo p ON o.idPeriodo = p.idPeriodo "
                    + "INNER JOIN experienciaeducativa ee ON o.idExperienciaEducativa = ee.idExperienciaEducativa "
                    + "INNER JOIN programaeducativo pe ON ee.idProgramaEducativo = pe.idProgramaEducativo "
                    + "INNER JOIN campus c ON d.idCampus = c.idCampus "
                    + "WHERE o.idOfertaColaboracionUV = ?";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
            prepararSentencia.setInt(1, idOfertaColaboracionUV);
            ResultSet resultado = prepararSentencia.executeQuery();
            if (resultado.next()) {
                OfertaColaboracionUV ofertaColaboracionUV = new OfertaColaboracionUV();
                ofertaColaboracionUV.setIdOfertaColaboracionUV(resultado.getInt("idOfertaColaboracionUV"));
                ofertaColaboracionUV.setNombreColaboracion(resultado.getString("nombreColaboracion"));
                ofertaColaboracionUV.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                ofertaColaboracionUV.setObjetivoCurso(resultado.getString("objetivoCurso"));
                ofertaColaboracionUV.setPerfilEstudiante(resultado.getString("perfilEstudiante"));
                ofertaColaboracionUV.setTemaInteres(resultado.getString("temaInteres"));
                ofertaColaboracionUV.setInformacionAdicional(resultado.getString("informacionAdicional"));
                ofertaColaboracionUV.setEstado(resultado.getString("estado"));
                ofertaColaboracionUV.setIdIdioma(resultado.getInt("idIdioma"));
                ofertaColaboracionUV.setIdioma(resultado.getString("idioma"));
                ofertaColaboracionUV.setIdAreaAcademica(resultado.getInt("idAreaAcademica"));
                ofertaColaboracionUV.setAreaAcademica(resultado.getString("areaAcademica"));
                ofertaColaboracionUV.setIdDependencia(resultado.getInt("idDependencia"));
                ofertaColaboracionUV.setDependencia(resultado.getString("dependencia"));
                ofertaColaboracionUV.setIdPeriodo(resultado.getInt("idPeriodo"));
                ofertaColaboracionUV.setPeriodo(resultado.getString("periodo"));
                ofertaColaboracionUV.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                ofertaColaboracionUV.setProgramaEducativo(resultado.getString("programaEducativo"));
                ofertaColaboracionUV.setExperienciaEducativa(resultado.getString("experienciaEducativa"));
                ofertaColaboracionUV.setCampus(resultado.getString("campus"));
                ofertaColaboracionUV.setIdProfesorUV(resultado.getInt("idProfesorUV"));
                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("ofertaColaboracionUV", ofertaColaboracionUV);
            }
            conexionBD.close();
        } catch (SQLException e) {
            respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
        }
    } else {
        respuesta.put(Constantes.KEY_MENSAJE, Constantes.ERROR_CONEXION);
    }
    return respuesta;
}


    
    public static List<OfertaColaboracionUV> obtenerOfertasColaboracionUVEnEspera() {
        List<OfertaColaboracionUV> ofertasList = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT o.idOfertaColaboracionUV, o.nombreColaboracion, o.idExperienciaEducativa, "
                        + "o.objetivoCurso, o.perfilEstudiante, o.temaInteres, o.informacionAdicional, o.estado, "
                        + "o.idIdioma, i.nombre AS 'idioma', o.idAreaAcademica, a.nombre AS 'areaAcademica', "
                        + "o.idDependencia, d.nombre AS 'dependencia', o.idPeriodo, p.nombrePeriodo AS 'periodo', "
                        + "ee.idProgramaEducativo, o.idProfesorUV "
                        + "FROM ofertacolaboracionuv o "
                        + "INNER JOIN idioma i "
                        + "ON o.idIdioma = i.idIdioma "
                        + "INNER JOIN areaacademica a "
                        + "ON o.idAreaAcademica = a.idAreaAcademica "
                        + "INNER JOIN dependencia d "
                        + "ON o.idDependencia = d.idDependencia "
                        + "INNER JOIN periodo p "
                        + "ON o.idPeriodo = p.idPeriodo "
                        + "INNER JOIN experienciaeducativa ee "
                        + "ON o.idExperienciaEducativa = ee.idExperienciaEducativa "
                        + "WHERE o.estado = 'En espera'";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    OfertaColaboracionUV ofertaColaboracionUV = new OfertaColaboracionUV();
                    ofertaColaboracionUV.setIdOfertaColaboracionUV(resultado.getInt("idOfertaColaboracionUV"));
                    ofertaColaboracionUV.setNombreColaboracion(resultado.getString("nombreColaboracion"));
                    ofertaColaboracionUV.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    ofertaColaboracionUV.setObjetivoCurso(resultado.getString("objetivoCurso"));
                    ofertaColaboracionUV.setPerfilEstudiante(resultado.getString("perfilEstudiante"));
                    ofertaColaboracionUV.setTemaInteres(resultado.getString("temaInteres"));
                    ofertaColaboracionUV.setInformacionAdicional(resultado.getString("informacionAdicional"));
                    ofertaColaboracionUV.setEstado(resultado.getString("estado"));
                    ofertaColaboracionUV.setIdIdioma(resultado.getInt("idIdioma"));
                    ofertaColaboracionUV.setIdioma(resultado.getString("idioma"));
                    ofertaColaboracionUV.setIdAreaAcademica(resultado.getInt("idAreaAcademica"));
                    ofertaColaboracionUV.setAreaAcademica(resultado.getString("areaAcademica"));
                    ofertaColaboracionUV.setIdDependencia(resultado.getInt("idDependencia"));
                    ofertaColaboracionUV.setDependencia(resultado.getString("dependencia"));
                    ofertaColaboracionUV.setIdPeriodo(resultado.getInt("idPeriodo"));
                    ofertaColaboracionUV.setPeriodo(resultado.getString("periodo"));
                    ofertaColaboracionUV.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                    ofertaColaboracionUV.setIdProfesorUV(resultado.getInt("idProfesorUV"));
                    ofertasList.add(ofertaColaboracionUV);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return ofertasList;
    }
    
    public static HashMap<String, Object> aceptarOfertaColaboracionUV(int idOfertaColaboracionUV) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            String sentencia = "UPDATE ofertacolaboracionuv SET estado = ? WHERE idOfertaColaboracionUV = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, Constantes.ESTADO_ACEPTADA);
                prepararSentencia.setInt(2, idOfertaColaboracionUV);
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Oferta de Colaboración UV aceptada correctamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ha ocurrido un error al aceptar la Oferta de Colaboración UV, favor de verificar la información.");
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
    
    public static HashMap<String, Object> rechazarOfertaColaboracionUV(int idOfertaColaboracionUV) {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            String sentencia = "UPDATE ofertacolaboracionuv SET estado = ? WHERE idOfertaColaboracionUV = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, Constantes.ESTADO_RECHAZADA);
                prepararSentencia.setInt(2, idOfertaColaboracionUV);
                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    respuesta.put(Constantes.KEY_ERROR, false);
                    respuesta.put(Constantes.KEY_MENSAJE, "Oferta de Colaboración UV rechazada correctamente.");
                } else {
                    respuesta.put(Constantes.KEY_MENSAJE, "Lo sentimos, ha ocurrido un error al rechazar la Oferta de Colaboración UV, favor de verificar la información.");
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