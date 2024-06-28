package sistemacoil.utilidades;

import java.time.format.DateTimeFormatter;

/**
 *
 * @author jafet
 */
public class Constantes {
    public static final String ES_COORDINADOR = "coil";
    
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String NOMBRE_BD = "coil_db";
    public static final String HOSTNAME = "localhost";
    public static final String PUERTO = "3306";
    public static final String USUARIO = "carim";
    public static final String PASSWORD = "usuario0001";
    
    public static final DateTimeFormatter FORMATO_FECHAS = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static final int MAX_LONG_MATRICULA = 9;
    public static final int MAX_LONG_NOMBRES = 20;
    public static final int MAX_LONG_CORREO = 40;
    public static final int MAX_LONG_INSTITUCION = 40;
    public static final int MAX_LONG_TELEFONO = 10;
    public static final int MAX_LONG_NOMBRE_COLAB = 70;
    public static final int MAX_LONG_TEXTAREA = 500;
    public static final int MAX_LONG_LOGIN_USER = 12;
    public static final int MAX_LONG_LOGIN_PASSWORD = 20;
    public static final int MAX_LONG_MOTIVO = 250;
    
    public static final String ESTADO_EN_ESPERA = "En espera";
    public static final String ESTADO_ACEPTADA = "Aceptada";
    public static final String ESTADO_RECHAZADA = "Rechazada";
    
    public static final String ESTADO_CREADA = "Creada";
    public static final String ESTADO_VIGENTE = "Vigente";
    public static final String ESTADO_CONCLUIDA = "Concluida";
    public static final String ESTADO_CANCELADA = "Cancelada";
    
    public static final String ERROR_CONEXION = "Por el momento el servidor no se encuentra disponible, intentelo más tarde.";
    public static final String ERROR_CAMPOS_VACIOS = "Campo obligatorio.*";
    public static final String ERROR_CB_VACIO = "Selecciona una opción.*";
    public static final String ERROR_FORMATO_CORREO = "Dirección de correo inválida.*";
    public static final String ERROR_FORMATO_MATRICULA = "Matrícula inválida.*";
    public static final String ERROR_SELECCIONAR_PERIODO = "Selecciona un periodo.*";
    public static final String ERROR_FECHA_FUERA_RANGO = "La fecha excede el periodo*.";
    public static final String ERROR_FECHA_CONCLUSION = "La fecha debe ser posterior al inicio*.";
    
    public static final String TITULO_SALIR = "Cancelar operación";
    public static final String INDICACION_SALIR = "¿Está seguro de que desea cancelar la operación actual? "
            + "\nCualquier progreso no guardado se perderá.";
    public static final String CANCELAR_OPERACION = "Cancelar";
    public static final String CONTINUAR_OPERACION = "Continuar aquí";
    
    public static final int MIN_ESTUDIANTES_COLAB = 1;
    public static final long MAX_PESO_ARCHIVOS = 20 * 1024 * 1024;
    
    public static final String KEY_MENSAJE = "mensaje";
    public static final String KEY_ERROR = "error";
}
