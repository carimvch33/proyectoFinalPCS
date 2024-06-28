package sistemacoil.modelo.dao;

import sistemacoil.modelo.pojo.Colaboracion;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.utilidades.Constantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sistemacoil.modelo.pojo.Periodo;

/**
 *
 * @author jafet
 */
public class ColaboracionDAO {
    
    public static int registrarColaboracion(Colaboracion colaboracion) {
        int idColaboracion = -1;
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            String consulta = "INSERT INTO colaboracion (idProfesorUV, idProfesorExterno, nombreColaboracion,"
                    + " fechaInicio, fechaConclusion, idIdioma, numEstudiantes, objetivoCurso, perfilEstudiante, "
                    + "temaInteres, informacionAdicional, idExperienciaEducativa, idPeriodo, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta, PreparedStatement.RETURN_GENERATED_KEYS);
                prepararSentencia.setInt(1, colaboracion.getIdProfesorUV());
                prepararSentencia.setInt(2, colaboracion.getIdProfesorExterno());
                prepararSentencia.setString(3, colaboracion.getNombreColaboracion());
                prepararSentencia.setDate(4, java.sql.Date.valueOf(colaboracion.getFechaInicio()));
                prepararSentencia.setDate(5, java.sql.Date.valueOf(colaboracion.getFechaConclusion()));
                prepararSentencia.setInt(6, colaboracion.getIdIdioma());
                prepararSentencia.setInt(7, colaboracion.getNumEstudiantes());
                prepararSentencia.setString(8, colaboracion.getObjetivoCurso());
                prepararSentencia.setString(9, colaboracion.getPerfilEstudiante());
                prepararSentencia.setString(10, colaboracion.getTemaInteres());
                prepararSentencia.setString(11, colaboracion.getInformacionAdicional());
                prepararSentencia.setInt(12, colaboracion.getIdExperienciaEducativa());
                prepararSentencia.setInt(13, colaboracion.getIdPeriodo());
                prepararSentencia.setString(14, colaboracion.getEstado());

                int filasAfectadas = prepararSentencia.executeUpdate();
                if (filasAfectadas > 0) {
                    ResultSet generatedKeys = prepararSentencia.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        idColaboracion = generatedKeys.getInt(1);
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
        return idColaboracion;
    }
    
    public static String obtenerNombreColaboracion(int idColaboracion) {
        String nombreColaboracion = null;
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "SELECT nombreColaboracion " + 
                    "FROM colaboracion " + 
                    "WHERE idColaboracion = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultado = prepararSentencia.executeQuery();
                if (resultado.next()) {
                    nombreColaboracion = resultado.getString("nombreColaboracion");
                }
                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return nombreColaboracion;
    }

    public static List<Colaboracion> obtenerColaboracionesVigentes(int idProfesorUV) {
        List<Colaboracion> colaboraciones = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "SELECT c.idColaboracion, c.nombreColaboracion, " 
                    + "CONCAT(pe.apellidoPaterno, '-', pe.apellidoMaterno, ', ', pe.nombre) AS nombreProfesorExterno, " +
                    "ee.nombre AS experienciaEducativa, c.fechaInicio, c.fechaConclusion, i.nombre AS idioma " +
                    "FROM colaboracion c " +
                    "JOIN profesorexterno pe ON c.idProfesorExterno = pe.idProfesorExterno " +
                    "JOIN experienciaeducativa ee ON c.idExperienciaEducativa = ee.idExperienciaEducativa " +
                    "JOIN idioma i ON c.idIdioma = i.idIdioma " +
                    "WHERE c.idProfesorUV = ? AND c.estado = 'Vigente'";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProfesorUV);
                ResultSet resultadoSentencia = prepararSentencia.executeQuery();

                while (resultadoSentencia.next()) {
                    Colaboracion colaboracion = new Colaboracion();
                    colaboracion.setIdColaboracion(resultadoSentencia.getInt("idColaboracion"));
                    colaboracion.setNombreColaboracion(resultadoSentencia.getString("nombreColaboracion"));
                    colaboracion.setNombreProfesorExterno(resultadoSentencia.getString("nombreProfesorExterno"));
                    colaboracion.setExperienciaEducativa(resultadoSentencia.getString("experienciaEducativa"));
                    colaboracion.setFechaInicio(resultadoSentencia.getDate("fechaInicio").toLocalDate());
                    colaboracion.setFechaConclusion(resultadoSentencia.getDate("fechaConclusion").toLocalDate());
                    colaboracion.setIdioma(resultadoSentencia.getString("idioma"));

                    colaboraciones.add(colaboracion);
                }

                resultadoSentencia.close();
                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }

        return colaboraciones;
    }
    
    public static Colaboracion obtenerDetallesColaboracion(int idColaboracion) {
        Colaboracion colaboracion = null;
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "SELECT c.nombreColaboracion, i.nombre AS idioma, p.nombrePeriodo AS periodo, c.fechaInicio, c.fechaConclusion, " + 
                    "ee.nombre AS experienciaEducativa, pe.nombre AS programaEducativo, d.nombre AS dependencia, " +
                    "aa.nombre AS areaAcademica, CONCAT(puv.apellidoPaterno, '-', puv.apellidoMaterno, ', ', puv.nombre) AS profesorUV, " + 
                    "CONCAT(pext.apellidoPaterno, '-', pext.apellidoMaterno, ', ', pext.nombre) AS profesorExterno, " +
                    "c.objetivoCurso, c.perfilEstudiante, c.temaInteres, c.informacionAdicional " +
                    "FROM colaboracion c " +
                    "JOIN idioma i ON c.idIdioma = i.idIdioma " +
                    "JOIN periodo p ON c.idPeriodo = p.idPeriodo " +
                    "JOIN experienciaeducativa ee ON c.idExperienciaEducativa = ee.idExperienciaEducativa " +
                    "JOIN programaeducativo pe ON ee.idProgramaEducativo = pe.idProgramaEducativo " +
                    "JOIN dependencia d ON pe.idDependencia = d.idDependencia " +
                    "JOIN areaacademica aa ON d.idAreaAcademica = aa.idAreaAcademica " +
                    "JOIN profesoruv puv ON c.idProfesorUV = puv.idProfesorUV " +
                    "JOIN profesorexterno pext ON c.idProfesorExterno = pext.idProfesorExterno " +
                    "WHERE c.idColaboracion = ?";

            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idColaboracion);
                ResultSet resultadoSentencia = prepararSentencia.executeQuery();

                if (resultadoSentencia.next()) {
                    colaboracion = new Colaboracion();
                    colaboracion.setNombreColaboracion(resultadoSentencia.getString("nombreColaboracion"));
                    colaboracion.setIdioma(resultadoSentencia.getString("idioma"));
                    colaboracion.setPeriodo(resultadoSentencia.getString("periodo"));
                    colaboracion.setFechaInicio(resultadoSentencia.getDate("fechaInicio").toLocalDate());
                    colaboracion.setFechaConclusion(resultadoSentencia.getDate("fechaConclusion").toLocalDate());
                    colaboracion.setExperienciaEducativa(resultadoSentencia.getString("experienciaEducativa"));
                    colaboracion.setProgramaEducativo(resultadoSentencia.getString("programaEducativo"));
                    colaboracion.setDependencia(resultadoSentencia.getString("dependencia"));
                    colaboracion.setAreaAcademica(resultadoSentencia.getString("areaAcademica"));
                    colaboracion.setProfesorUV(resultadoSentencia.getString("profesorUV"));
                    colaboracion.setNombreProfesorExterno(resultadoSentencia.getString("profesorExterno"));
                    colaboracion.setObjetivoCurso(resultadoSentencia.getString("objetivoCurso"));
                    colaboracion.setPerfilEstudiante(resultadoSentencia.getString("perfilEstudiante"));
                    colaboracion.setTemaInteres(resultadoSentencia.getString("temaInteres"));
                    colaboracion.setInformacionAdicional(resultadoSentencia.getString("informacionAdicional"));
                }

                resultadoSentencia.close();
                prepararSentencia.close();
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }

        return colaboracion;
    }
    
    public static boolean actualizarEstadoColaboracion(int idColaboracion, String nuevoEstado) {
        boolean exito = false;
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "UPDATE colaboracion " + 
                    "SET estado = ? " +
                    "WHERE idColaboracion = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, nuevoEstado);
                prepararSentencia.setInt(2, idColaboracion);

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
    
    public static boolean cancelarColaboracion(int idColaboracion, String motivoCancelacion) {
        boolean exito = false;
        Connection conexionBD = ConexionBD.obtenerConexion();

        if (conexionBD != null) {
            String consulta = "UPDATE colaboracion " + 
                    "SET estado = 'Cancelada', motivoCancelacion = ? " + 
                    "WHERE idColaboracion = ?";
            try {
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setString(1, motivoCancelacion);
                prepararSentencia.setInt(2, idColaboracion);

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
    public ArrayList<Colaboracion> obtenerActividadesConEvidencia() {
        ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        String consulta = "SELECT * FROM colaboracion WHERE idColaboracion IN (SELECT idColaboracion FROM evidencia)";
        try (PreparedStatement stmt = conexionBD.prepareStatement(consulta)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Colaboracion colaboracion = new Colaboracion();
                colaboracion.setIdColaboracion(rs.getInt("idColaboracion"));
                colaboracion.setNombreColaboracion(rs.getString("nombreColaboracion"));
                colaboraciones.add(colaboracion);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return colaboraciones;
    }
    public byte[] obtenerContenidoEvidencia(int idColaboracion) {
        byte[] contenidoEvidencia = null;
        Connection conexionBD = ConexionBD.obtenerConexion();
        String consulta = "SELECT archivoEvidencia FROM evidencia WHERE idColaboracion = ?";
        try (PreparedStatement stmt = conexionBD.prepareStatement(consulta)) {
            stmt.setInt(1, idColaboracion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contenidoEvidencia = rs.getBytes("archivoEvidencia");
            }
        } catch (SQLException ex) {
        ex.printStackTrace();
        }
        return contenidoEvidencia;
    }
    
    public ArrayList<Periodo> obtenerPeriodosConColaboraciones() {
        ArrayList<Periodo> periodos = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        String consulta = "SELECT DISTINCT p.* FROM periodo p JOIN colaboracion c ON p.idPeriodo = c.idPeriodo";
        try (PreparedStatement stmt = conexionBD.prepareStatement(consulta)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Periodo periodo = new Periodo();
                periodo.setIdPeriodo(rs.getInt("idPeriodo"));
                periodo.setNombrePeriodo(rs.getString("nombrePeriodo"));
                periodo.setAnioPeriodo(rs.getString("anioPeriodo"));
                periodo.setInicioPeriodo(rs.getObject("inicioPeriodo", LocalDate.class));
                periodo.setFinPeriodo(rs.getObject("finPeriodo", LocalDate.class));
                periodos.add(periodo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return periodos;
    }
    
    public ArrayList<Colaboracion> obtenerColaboracionesPorPeriodo(int idPeriodo) {
    Connection conexionBD = ConexionBD.obtenerConexion();
    ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
    String consulta = "SELECT c.nombreColaboracion, i.nombre AS idioma, p.nombrePeriodo AS periodo, c.fechaInicio, c.fechaConclusion, " + 
                    "ee.nombre AS experienciaEducativa, pe.nombre AS programaEducativo, d.nombre AS dependencia, " +
                    "aa.nombre AS areaAcademica, CONCAT(puv.apellidoPaterno, '-', puv.apellidoMaterno, ', ', puv.nombre) AS profesorUV, " + 
                    "CONCAT(pext.apellidoPaterno, '-', pext.apellidoMaterno, ', ', pext.nombre) AS profesorExterno, " +
                    "c.objetivoCurso, c.perfilEstudiante, c.temaInteres, c.informacionAdicional " +
                    "FROM colaboracion c " +
                    "JOIN idioma i ON c.idIdioma = i.idIdioma " +
                    "JOIN periodo p ON c.idPeriodo = p.idPeriodo " +
                    "JOIN experienciaeducativa ee ON c.idExperienciaEducativa = ee.idExperienciaEducativa " +
                    "JOIN programaeducativo pe ON ee.idProgramaEducativo = pe.idProgramaEducativo " +
                    "JOIN dependencia d ON pe.idDependencia = d.idDependencia " +
                    "JOIN areaacademica aa ON d.idAreaAcademica = aa.idAreaAcademica " +
                    "JOIN profesoruv puv ON c.idProfesorUV = puv.idProfesorUV " +
                    "JOIN profesorexterno pext ON c.idProfesorExterno = pext.idProfesorExterno " +
                    "WHERE c.idPeriodo = ?";
    try (PreparedStatement stmt = conexionBD.prepareStatement(consulta)) {
        stmt.setInt(1, idPeriodo);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Colaboracion colaboracion = new Colaboracion();
            colaboracion.setNombreColaboracion(rs.getString("nombreColaboracion"));
                    colaboracion.setIdioma(rs.getString("idioma"));
                    colaboracion.setPeriodo(rs.getString("periodo"));
                    colaboracion.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());
                    colaboracion.setFechaConclusion(rs.getDate("fechaConclusion").toLocalDate());
                    colaboracion.setExperienciaEducativa(rs.getString("experienciaEducativa"));
                    colaboracion.setProgramaEducativo(rs.getString("programaEducativo"));
                    colaboracion.setDependencia(rs.getString("dependencia"));
                    colaboracion.setAreaAcademica(rs.getString("areaAcademica"));
                    colaboracion.setProfesorUV(rs.getString("profesorUV"));
                    colaboracion.setNombreProfesorExterno(rs.getString("profesorExterno"));
                    colaboracion.setObjetivoCurso(rs.getString("objetivoCurso"));
                    colaboracion.setPerfilEstudiante(rs.getString("perfilEstudiante"));
                    colaboracion.setTemaInteres(rs.getString("temaInteres"));
                    colaboracion.setInformacionAdicional(rs.getString("informacionAdicional"));
            colaboraciones.add(colaboracion);
            
            colaboraciones.add(colaboracion);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (conexionBD != null) {
                conexionBD.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return colaboraciones;
}
    
    public ArrayList<Periodo> obtenerPeriodosConColaboracionesPorProfesorUV(int idProfesorUV) {
        ArrayList<Periodo> periodos = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        String consulta = "SELECT DISTINCT p.* FROM periodo p " +
                "JOIN colaboracion c ON p.idPeriodo = c.idPeriodo " +
                "WHERE c.idProfesorUV = ?";
        try (PreparedStatement stmt = conexionBD.prepareStatement(consulta)) {
            stmt.setInt(1, idProfesorUV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Periodo periodo = new Periodo();
                periodo.setIdPeriodo(rs.getInt("idPeriodo"));
                periodo.setNombrePeriodo(rs.getString("nombrePeriodo"));
                periodo.setAnioPeriodo(rs.getString("anioPeriodo"));
                periodo.setInicioPeriodo(rs.getObject("inicioPeriodo", LocalDate.class));
                periodo.setFinPeriodo(rs.getObject("finPeriodo", LocalDate.class));
                periodos.add(periodo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conexionBD != null) {
                    conexionBD.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return periodos;
    }
    
    public static boolean subirSyllabus(int idColaboracion, byte[] syllabus) {
        String sql = "UPDATE colaboracion SET syllabus = ? WHERE idColaboracion = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBytes(1, syllabus);
            stmt.setInt(2, idColaboracion);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<Colaboracion> obtenerColaboracionesPorPeriodoConIdProfUV(int idPeriodo, int idProfesorUV) {
        Connection conexionBD = ConexionBD.obtenerConexion();
        ArrayList<Colaboracion> colaboraciones = new ArrayList<>();
        String consulta = "SELECT c.nombreColaboracion, i.nombre AS idioma, p.nombrePeriodo AS periodo, c.fechaInicio, c.fechaConclusion, " + 
                        "ee.nombre AS experienciaEducativa, pe.nombre AS programaEducativo, d.nombre AS dependencia, " +
                        "aa.nombre AS areaAcademica, CONCAT(puv.apellidoPaterno, '-', puv.apellidoMaterno, ', ', puv.nombre) AS profesorUV, " + 
                        "CONCAT(pext.apellidoPaterno, '-', pext.apellidoMaterno, ', ', pext.nombre) AS profesorExterno, " +
                        "c.objetivoCurso, c.perfilEstudiante, c.temaInteres, c.informacionAdicional " +
                        "FROM colaboracion c " +
                        "JOIN idioma i ON c.idIdioma = i.idIdioma " +
                        "JOIN periodo p ON c.idPeriodo = p.idPeriodo " +
                        "JOIN experienciaeducativa ee ON c.idExperienciaEducativa = ee.idExperienciaEducativa " +
                        "JOIN programaeducativo pe ON ee.idProgramaEducativo = pe.idProgramaEducativo " +
                        "JOIN dependencia d ON pe.idDependencia = d.idDependencia " +
                        "JOIN areaacademica aa ON d.idAreaAcademica = aa.idAreaAcademica " +
                        "JOIN profesoruv puv ON c.idProfesorUV = puv.idProfesorUV " +
                        "JOIN profesorexterno pext ON c.idProfesorExterno = pext.idProfesorExterno " +
                        "WHERE c.idPeriodo = ? AND c.idProfesorUV = ?";
        try (PreparedStatement stmt = conexionBD.prepareStatement(consulta)) {
            stmt.setInt(1, idPeriodo);
            stmt.setInt(2, idProfesorUV);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Colaboracion colaboracion = new Colaboracion();
                colaboracion.setNombreColaboracion(rs.getString("nombreColaboracion"));
                colaboracion.setIdioma(rs.getString("idioma"));
                colaboracion.setPeriodo(rs.getString("periodo"));
                colaboracion.setFechaInicio(rs.getDate("fechaInicio").toLocalDate());
                colaboracion.setFechaConclusion(rs.getDate("fechaConclusion").toLocalDate());
                colaboracion.setExperienciaEducativa(rs.getString("experienciaEducativa"));
                colaboracion.setProgramaEducativo(rs.getString("programaEducativo"));
                colaboracion.setDependencia(rs.getString("dependencia"));
                colaboracion.setAreaAcademica(rs.getString("areaAcademica"));
                colaboracion.setProfesorUV(rs.getString("profesorUV"));
                colaboracion.setNombreProfesorExterno(rs.getString("profesorExterno"));
                colaboracion.setObjetivoCurso(rs.getString("objetivoCurso"));
                colaboracion.setPerfilEstudiante(rs.getString("perfilEstudiante"));
                colaboracion.setTemaInteres(rs.getString("temaInteres"));
                colaboracion.setInformacionAdicional(rs.getString("informacionAdicional"));
                colaboraciones.add(colaboracion);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return colaboraciones;
    }
}