package py.una.entidad;



public class Vehiculo {

    String chapa;
    String marca;
    Integer monto;


    public Vehiculo(){
    }

    public Vehiculo(String chapa, String marca, Integer monto){
        this.chapa = chapa;
        this.marca = marca;
        this.monto = monto;

    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }


}

