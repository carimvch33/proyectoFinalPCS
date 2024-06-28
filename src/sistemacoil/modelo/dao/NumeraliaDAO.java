/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacoil.modelo.dao;

import sistemacoil.modelo.ConexionBD;
import sistemacoil.modelo.pojo.NumeraliaAreaAcademica;
import sistemacoil.modelo.pojo.NumeraliaRegion;
import sistemacoil.utilidades.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author lalom
 */
public class NumeraliaDAO {
    
      public static HashMap<String, Object> consultarNumeraliaAreaAcademica() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("error", true);
        Connection conexionBD = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.obtenerConexion();
            if (conexionBD != null) {
                String consulta = 
                        "SELECT aa.nombre AS area_academica, " +
                            "COUNT(DISTINCT c.idColaboracion) AS numero_de_colaboraciones, " +
                            "COUNT(DISTINCT c.idProfesorUV) AS numero_de_profesores, " +
                            "SUM(c.numEstudiantes) AS numero_de_estudiantes " +
                            "FROM colaboracion c " +
                            "JOIN experienciaeducativa ee ON c.idExperienciaEducativa = ee.idExperienciaEducativa " +
                            "JOIN programaeducativo pe ON ee.idProgramaEducativo = pe.idProgramaEducativo " +
                            "JOIN dependencia d ON pe.idDependencia = d.idDependencia " +
                            "JOIN areaacademica aa ON d.idAreaAcademica = aa.idAreaAcademica " +
                            "WHERE YEAR(c.fechaInicio) = YEAR(CURDATE()) - 1 " +
                            "AND YEAR(c.fechaConclusion) = YEAR(CURDATE()) - 1 " +
                            "GROUP BY aa.idAreaAcademica, aa.nombre";


                prepararSentencia = conexionBD.prepareStatement(consulta);
                resultado = prepararSentencia.executeQuery();

                ArrayList<NumeraliaAreaAcademica> numeraliaAreaAcademica = new ArrayList<>();
                while (resultado.next()) {
                    NumeraliaAreaAcademica areaAcademica = new NumeraliaAreaAcademica();
                    areaAcademica.setAreaAcademica(resultado.getString("area_academica"));
                    areaAcademica.setNumeroDeColaboraciones(resultado.getInt("numero_de_colaboraciones"));
                    areaAcademica.setNumeroDeProfesores(resultado.getInt("numero_de_profesores"));
                    areaAcademica.setNumeroDeEstudiantes(resultado.getInt("numero_de_estudiantes"));
                    numeraliaAreaAcademica.add(areaAcademica);
                }

                respuesta.put("error", false);
                respuesta.put("colaboraciones", numeraliaAreaAcademica);
            } else {
                //respuesta.put("mensaje", Constantes.MSJ_ERROR_CONEXION);
            }
        } catch (SQLException e) {
            respuesta.put("mensaje", e.getMessage());
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (prepararSentencia != null) {
                    prepararSentencia.close();
                }
                if (conexionBD != null) {
                    conexionBD.close();
                }
            } catch (SQLException e) {
                respuesta.put("mensaje", e.getMessage());
            }
        }

        return respuesta;
    }
      
    public static HashMap<String, Object> consultarNumeraliaRegion() {
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put(Constantes.KEY_ERROR, true);
        Connection conexionBD = null;
        PreparedStatement prepararSentencia = null;
        ResultSet resultado = null;

        try {
            conexionBD = ConexionBD.obtenerConexion();
            if (conexionBD != null) {
                String consulta =
                        "SELECT d.municipio AS region, " +
                                "COUNT(DISTINCT c.idColaboracion) AS numero_de_colaboraciones, " +
                                "COUNT(DISTINCT c.idProfesorUV) AS numero_de_profesores, " +
                                "SUM(c.numEstudiantes) AS numero_de_estudiantes " +
                                "FROM colaboracion c " +
                                "JOIN experienciaeducativa ee ON c.idExperienciaEducativa = ee.idExperienciaEducativa " +
                                "JOIN programaeducativo pe ON ee.idProgramaEducativo = pe.idProgramaEducativo " +
                                "JOIN dependencia d ON pe.idDependencia = d.idDependencia " +
                                "WHERE YEAR(c.fechaInicio) = YEAR(CURDATE()) - 1 " +
                                "AND YEAR(c.fechaConclusion) = YEAR(CURDATE()) - 1 " +
                                "GROUP BY d.municipio";

                prepararSentencia = conexionBD.prepareStatement(consulta);
                resultado = prepararSentencia.executeQuery();

                ArrayList<NumeraliaRegion> numeraliaRegion = new ArrayList<>();
                while (resultado.next()) {
                    NumeraliaRegion region= new NumeraliaRegion();
                    region.setRegion(resultado.getString("region"));
                    region.setNumeroDeColaboraciones(resultado.getInt("numero_de_colaboraciones"));
                    region.setNumeroDeProfesores(resultado.getInt("numero_de_profesores"));
                    region.setNumeroDeEstudiantes(resultado.getInt("numero_de_estudiantes"));
                    numeraliaRegion.add(region);
                }

                respuesta.put(Constantes.KEY_ERROR, false);
                respuesta.put("colaboraciones", numeraliaRegion);
            } else {
               // respuesta.put(Constantes.KEY_MENSAJE, Constantes.MSJ_ERROR_CONEXION);
            }
        } catch (SQLException e) {
            respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (prepararSentencia != null) {
                    prepararSentencia.close();
                }
                if (conexionBD != null) {
                    conexionBD.close();
                }
            } catch (SQLException e) {
                respuesta.put(Constantes.KEY_MENSAJE, e.getMessage());
            }
        }

        return respuesta;
    }
}

