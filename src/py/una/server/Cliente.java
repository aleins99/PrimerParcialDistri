package py.una.server;


import java.io.*;
import java.net.*;

import py.una.entidad.Persona;
import py.una.entidad.PersonaJSON;
import py.una.entidad.Vehiculo;
import py.una.entidad.VehiculoJSON;

class Cliente {

    public static void main(String a[]) throws Exception {

        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (a.length > 0) {
            direccionServidor = a[0];
        }

        int puertoServidor = 9876;

        try {

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor +  " via UDP...");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
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
                    System.out.println("Ingresar monto del vehiculo: ");
                    Integer monto = Integer.parseInt(inFromUser.readLine());
                    System.out.print("Ingrese chapa del vehiculo: ");
                    String chapa = inFromUser.readLine();
                    System.out.print("Ingrese marca del vehiculo: ");
                    String marca = inFromUser.readLine();

                    Persona p = new Persona(cedula, nombre, apellido);
                    Vehiculo v = new Vehiculo(chapa, marca, monto);
                    String datoPaqueteVehiculo = VehiculoJSON.objetoString(v);
                    String datoPaquetePersona = PersonaJSON.objetoString(p);
                    sendData = datoPaquetePersona.getBytes();

                    System.out.println("Enviar " + datoPaquetePersona + " al servidor. ("+ sendData.length + " bytes)");
                    DatagramPacket sendPacketPersona =
                            new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
                    sendData = datoPaqueteVehiculo.getBytes();
                    System.out.println("Enviar " + datoPaqueteVehiculo + " al servidor. ("+ sendData.length + " bytes)");
                    DatagramPacket sendPacketVehiculo =
                            new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);
                    clientSocket.send(sendPacketPersona);
                    clientSocket.send(sendPacketVehiculo);
                    break;

            }



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
            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

