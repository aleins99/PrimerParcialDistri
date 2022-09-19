package py.una.server;
import py.una.entidad.Persona;
import py.una.entidad.PersonaJSON;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class ClienteHilo {
    public static void main(String[] args) {
        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (args.length > 0) {
            direccionServidor = args[0];
        }

        int puertoServidor = 9876;
        try {
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));
            System.out.println("MENÚ");
            System.out.println("1- Ingresar datos ");
            System.out.println("2- Vehiculo más caro");
            System.out.print("Elegir opcion: ");
            int opcion = Integer.parseInt(inFromUser.readLine());
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el número de cédula (debe ser numérico): ");
                    String strcedula = inFromUser.readLine();
                    Long cedula = 0L;
                    try {
                        cedula = Long.parseLong(strcedula);
                    }catch(Exception e1) {

                    }

                    System.out.print("Ingrese el nombre: ");
                    String nombre = inFromUser.readLine();
                    System.out.print("Ingrese el apellido: ");
                    String apellido = inFromUser.readLine();

                    Persona p = new Persona(cedula, nombre, apellido);
                    String datoPaquete = PersonaJSON.objetoString(p);
                    sendData = datoPaquete.getBytes();

                    System.out.println("Enviar " + datoPaquete + " al servidor. ("+ sendData.length + " bytes)");
                    DatagramPacket sendPacket =
                            new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);

                    clientSocket.send(sendPacket);

                    DatagramPacket receivePacket =
                            new DatagramPacket(receiveData, receiveData.length);

                    System.out.println("Esperamos si viene la respuesta.");

                    //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
                    clientSocket.setSoTimeout(10000);

                    try {
                        // ESPERAMOS LA RESPUESTA, BLOQUENTE
                        clientSocket.receive(receivePacket);

                        String respuesta = new String(receivePacket.getData());
                        Persona presp = PersonaJSON.stringObjeto(respuesta.trim());

                        InetAddress returnIPAddress = receivePacket.getAddress();
                        int port = receivePacket.getPort();

                        System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);


                    } catch (SocketTimeoutException ste) {
                        System.out.println("TimeOut: El paquete udp se asume perdido.");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    clientSocket.close();
                    break;
                case 2:


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
