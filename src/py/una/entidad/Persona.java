package py.una.entidad;


public class Persona {

    Long cedula;
    String nombre;
    String apellido;


    public Persona(){
    }

    public Persona(Long pcedula, String pnombre, String papellido){
        this.cedula = pcedula;
        this.nombre = pnombre;
        this.apellido = papellido;

    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


}
