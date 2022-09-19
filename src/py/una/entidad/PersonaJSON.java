package py.una.entidad;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PersonaJSON {
    public static void main(String[] args) throws Exception {
        PersonaJSON representacion = new PersonaJSON();

        System.out.println("Ejemplo de uso 1: pasar de objeto a string");
        Persona p = new Persona();
        p.setNombre("Maria");
        p.setApellido("Gomez");
        p.setCedula(123456L);

        String r1 = representacion.objetoString(p);
        System.out.println(r1);


        System.out.println("\n*************************************************************************");
        System.out.println("\nEjemplo de uso 2: pasar de string a objeto");
        String un_string = "{\"cedula\":123123,\"nombre\":\"Ana\",\"apellido\":\"Perez\",\"asignaturas\":[\"Sistemas Distribuidos\",\"Fisica\",\"Inteligencia Artificial\"]}";

        Persona r2 = representacion.stringObjeto(un_string);
        System.out.println(r2.nombre + " " + r2.apellido + " " +r2.cedula );

    }

    public static String objetoString(Persona p) {

        JSONObject obj = new JSONObject();
        obj.put("cedula", p.getCedula());
        obj.put("nombre", p.getNombre());
        obj.put("apellido", p.getApellido());


        return obj.toJSONString();
    }


    public static Persona stringObjeto(String str) throws Exception {
        Persona p = new Persona();
        JSONParser parser = new JSONParser();

        Object obj = parser.parse(str.trim());
        JSONObject jsonObject = (JSONObject) obj;

        Long cedula = (Long) jsonObject.get("cedula");
        p.setCedula(cedula);
        p.setNombre((String)jsonObject.get("nombre"));
        p.setApellido((String)jsonObject.get("apellido"));

        return p;
    }

}
