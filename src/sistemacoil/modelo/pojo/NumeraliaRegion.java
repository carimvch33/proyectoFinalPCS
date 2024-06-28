/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemacoil.modelo.pojo;

/**
 *
 * @author lalom
 */

public class NumeraliaRegion {

    private String region;
    private Integer numeroDeColaboraciones;
    private Integer numeroDeProfesores;
    private Integer numeroDeEstudiantes;

    public NumeraliaRegion() {
    }

    public NumeraliaRegion(String region, Integer numeroDeColaboraciones, Integer numeroDeProfesores, Integer numeroDeEstudiantes) {
        this.region = region;
        this.numeroDeColaboraciones = numeroDeColaboraciones;
        this.numeroDeProfesores = numeroDeProfesores;
        this.numeroDeEstudiantes = numeroDeEstudiantes;
    }

    
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getNumeroDeColaboraciones() {
        return numeroDeColaboraciones;
    }

    public void setNumeroDeColaboraciones(Integer numeroDeColaboraciones) {
        this.numeroDeColaboraciones = numeroDeColaboraciones;
    }

    public int getNumeroDeProfesores() {
        return numeroDeProfesores;
    }

    public void setNumeroDeProfesores(Integer numeroDeProfesores) {
        this.numeroDeProfesores = numeroDeProfesores;
    }

    public int getNumeroDeEstudiantes() {
        return numeroDeEstudiantes;
    }

    public void setNumeroDeEstudiantes(Integer numeroDeEstudiantes) {
        this.numeroDeEstudiantes = numeroDeEstudiantes;
    }
}
