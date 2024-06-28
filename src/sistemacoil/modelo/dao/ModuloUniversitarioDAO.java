package sistemacoil.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.AreaAcademica;
import sistemacoil.modelo.pojo.Dependencia;
import sistemacoil.modelo.pojo.ExperienciaEducativa;
import sistemacoil.modelo.pojo.Periodo;
import sistemacoil.modelo.pojo.ProgramaEducativo;
import sistemacoil.utilidades.Constantes;

/**
 *
 * @author jafet
 */
public class ModuloUniversitarioDAO {
    public static List<AreaAcademica> obtenerAreasAcademicas() {
        List<AreaAcademica> areasAcademicas = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idAreaAcademica, nombre, clave "
                        + "FROM areaAcademica";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    AreaAcademica areaAcademica = new AreaAcademica();
                    areaAcademica.setIdAreaAcademica(resultado.getInt("idAreaAcademica"));
                    areaAcademica.setNombre(resultado.getString("nombre"));
                    areaAcademica.setClave(resultado.getString("clave"));
                    areasAcademicas.add(areaAcademica);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return areasAcademicas;
    }
    
    public static List<Periodo> obtenerPeriodos() {
        List<Periodo> periodos = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idPeriodo, nombrePeriodo, anioPeriodo, inicioPeriodo, finPeriodo FROM periodo";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    Periodo periodo = new Periodo();
                    periodo.setIdPeriodo(resultado.getInt("idPeriodo"));
                    periodo.setNombrePeriodo(resultado.getString("nombrePeriodo"));
                    periodo.setAnioPeriodo(resultado.getString("anioPeriodo"));
                    periodo.setInicioPeriodo(resultado.getDate("inicioPeriodo").toLocalDate());
                    periodo.setFinPeriodo(resultado.getDate("finPeriodo").toLocalDate());
                    periodos.add(periodo);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return periodos;
    }
    
    public static List<Dependencia> obtenerDependenciasPorAreaAcademica(int idAreaAcademica) {
        List<Dependencia> dependencias = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idDependencia, nombre "
                                + "FROM dependencia "
                                + "WHERE idAreaAcademica = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idAreaAcademica);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    Dependencia dependencia = new Dependencia();
                    dependencia.setIdDependencia(resultado.getInt("idDependencia"));
                    dependencia.setNombre(resultado.getString("nombre"));
                    dependencias.add(dependencia);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return dependencias;
    }
    
    public static List<ProgramaEducativo> obtenerProgramasEducativosPorDependencia(int idDependencia) {
        List<ProgramaEducativo> programasEducativos = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idProgramaEducativo, nombre, anioPlanEstudio, clave "
                                + "FROM programaEducativo "
                                + "WHERE idDependencia = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idDependencia);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    ProgramaEducativo programaEducativo = new ProgramaEducativo();
                    programaEducativo.setIdProgramaEducativo(resultado.getInt("idProgramaEducativo"));
                    programaEducativo.setNombre(resultado.getString("nombre"));
                    programaEducativo.setAnioPlanEstudio(resultado.getString("anioPlanEstudio"));
                    programaEducativo.setClave(resultado.getString("clave"));
                    programasEducativos.add(programaEducativo);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return programasEducativos;
    }
    
    public static List<ExperienciaEducativa> obtenerExperienciasPorProgramaEducativo(int idProgramaEducativo) {
        List<ExperienciaEducativa> experienciasEducativas = new ArrayList<>();
        Connection conexionBD = ConexionBD.obtenerConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idExperienciaEducativa, nrc, nombre, numCreditos "
                                + "FROM experienciaEducativa "
                                + "WHERE idProgramaEducativo = ?";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(consulta);
                prepararSentencia.setInt(1, idProgramaEducativo);
                ResultSet resultado = prepararSentencia.executeQuery();
                while (resultado.next()) {
                    ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                    experienciaEducativa.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    experienciaEducativa.setNrc(resultado.getString("nrc"));
                    experienciaEducativa.setNombre(resultado.getString("nombre"));
                    experienciaEducativa.setNumCreditos(resultado.getInt("numCreditos"));
                    experienciasEducativas.add(experienciaEducativa);
                }
                conexionBD.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Constantes.ERROR_CONEXION);
        }
        return experienciasEducativas;
    }

    
}
