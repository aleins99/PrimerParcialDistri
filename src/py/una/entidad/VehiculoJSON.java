package py.una.entidad;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class VehiculoJSON {
    public static void main(String[] args) throws Exception {
        VehiculoJSON representacion = new VehiculoJSON();

        System.out.println("Ejemplo de uso 1: pasar de objeto a string");
        Vehiculo p = new Vehiculo();
        p.setMarca("Nissan");
        p.setChapa("23e21");
        p.setMonto(123456);

        String r1 = representacion.objetoString(p);
        System.out.println(r1);


        System.out.println("\n*************************************************************************");
        System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
        String un_string = "{\"monto\":15000,\"marca\":\"Nissan\",\"chapa\":\"e221ev\"}";

        Vehiculo r2 = representacion.stringObjeto(un_string);
        System.out.println(r2.chapa + " " + r2.marca + " " +r2.monto );

    }

    public static String objetoString(Vehiculo p) {

        JSONObject obj = new JSONObject();
        obj.put("chapa", p.getChapa());
        obj.put("marca", p.getMarca());
        obj.put("monto", p.getMonto());


        return obj.toJSONString();
    }


    public static Vehiculo stringObjeto(String str) throws Exception {
        Vehiculo p = new Vehiculo();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Integer monto = (Integer) jsonObject.get("monto");
        p.setMonto(monto);
        p.setMarca((String)jsonObject.get("marca"));
        p.setChapa((String)jsonObject.get("chapa"));

        return p;
    }

}
