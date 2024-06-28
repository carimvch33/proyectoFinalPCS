package sistemacoil.modelo.pojo;

import java.time.LocalDate;

public class Periodo {
    private int idPeriodo;
    private String nombrePeriodo;
    private String anioPeriodo;
    private LocalDate inicioPeriodo;
    private LocalDate finPeriodo;

    public Periodo() {
    }

    public Periodo(int idPeriodo, String nombre, String anioPeriodo, LocalDate inicioPeriodo, LocalDate finPeriodo) {
        this.idPeriodo = idPeriodo;
        this.nombrePeriodo = nombre;
        this.anioPeriodo = anioPeriodo;
        this.inicioPeriodo = inicioPeriodo;
        this.finPeriodo = finPeriodo;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public String getAnioPeriodo() {
        return anioPeriodo;
    }

    public void setAnioPeriodo(String anioPeriodo) {
        this.anioPeriodo = anioPeriodo;
    }

    public LocalDate getInicioPeriodo() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(LocalDate inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public LocalDate getFinPeriodo() {
        return finPeriodo;
    }

    public void setFinPeriodo(LocalDate finPeriodo) {
        this.finPeriodo = finPeriodo;
    }
    
    @Override
    public String toString() {
        return this.nombrePeriodo;
    }
}