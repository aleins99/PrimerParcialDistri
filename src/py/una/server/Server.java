package py.una.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import py.una.entidad.Persona;
import py.una.entidad.PersonaJSON;
import py.una.entidad.Vehiculo;
import py.una.entidad.VehiculoJSON;

public class Server {


    public static void main(String[] a){

        // Variables
        int puertoServidor = 9876;
        ArrayList persona = new ArrayList();
        ArrayList vehiculo = new ArrayList();
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
            System.out.println("Servidor Sistemas Distribuidos - UDP ");

            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];


            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);

                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);

                System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                datoRecibido = datoRecibido.trim();
                System.out.println("DatoRecibido: " + datoRecibido );
                Persona p = PersonaJSON.stringObjeto(datoRecibido);
                Vehiculo v = VehiculoJSON.stringObjeto(datoRecibido);
                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();
                if (p != null) {
                    System.out.println("De : " + IPAddress + ":" + port);
                    System.out.println("Persona Recibida : " + p.getCedula() + ", " + p.getNombre() + " " + p.getApellido());

                    try {
                        persona.add(p);
                        System.out.println("Persona insertada exitosamente");
                    } catch (Exception e) {
                        System.out.println("Persona NO insertada, razón: " + e.getLocalizedMessage());
                    }
                } else {
                    System.out.println("De : " + IPAddress + ":" + port);
                    System.out.println("Vehiculo Recibido : " + v.getMarca() + ", " + v.getChapa() + " " + v.getMonto());

                    try {
                        vehiculo.add(v);
                        System.out.println("Vehiculo insertada exitosamente");
                    } catch (Exception e) {
                        System.out.println("Vehiculo NO insertada, razón: " + e.getLocalizedMessage());
                    }
                }
                // Enviamos la respuesta inmediatamente a ese mismo cliente
                // Es no bloqueante
                sendData = PersonaJSON.objetoString(p).getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

    }
}

