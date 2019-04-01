/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcliente;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author carlo
 */
public class Chat {
    
    public Chat(String ip, int port, VentanaChat ventana) throws UnknownHostException, IOException, AWTException, ClassNotFoundException{
        this.ventana = ventana;
        EstablecerConexion(ip, port);
    }
    
    public Chat(){}
    
    private Socket socket;
    private DataOutputStream streamToServer;
    private InputStreamReader streamFromServer;
    private BufferedReader bufferedReader;
    private Thread hilo;
    private VentanaChat ventana;
    public Notification notificacion = new Notification();
    
    private ObjectOutputStream objectStreamToServer;
    private ObjectInputStream objectInputStream;
    
    public void EstablecerConexion(String ip, int port) throws UnknownHostException, IOException, AWTException, ClassNotFoundException{       
        socket = new Socket(ip, port);
        //streamToServer = new DataOutputStream(socket.getOutputStream());
        //streamFromServer = new InputStreamReader(socket.getInputStream());
        
        objectStreamToServer = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        
        //bufferedReader = new BufferedReader(streamFromServer);
        ventana.ActualizarConversacion("Estableciendo conexión con " + ip +"...");
        EnviarMensaje("confirm");
        String mensajeRecibido = DecodificarLista((ArrayList < Integer >)objectInputStream.readObject());
        if(mensajeRecibido.equals("confirm")){
            notificacion.displayTray("Conexión establecida", socket.getInetAddress().toString());
            ventana.conectado = true;
            ventana.ActualizarConversacion("Conectado correctamente.");
        }
        
    }
    
    public void EnviarMensaje(String msg) throws IOException{  
        //streamToServer.writeBytes(msg + '\n');

        ArrayList<Integer> list = CodificarString(msg);
        objectStreamToServer.writeObject(list);
    }
    
    public void Escuchar(){
        try {
            String mensajeRecibido;
            //mensajeRecibido = bufferedReader.readLine();
            mensajeRecibido = DecodificarLista((ArrayList< Integer>) objectInputStream.readObject());
            if ((!mensajeRecibido.equals(""))) {
                ventana.EscribirMensajeDelServer(mensajeRecibido);
                if(ventana.minimizado){
                    notificacion.displayTray("Mensaje nuevo", mensajeRecibido);
                    System.out.println("Notificacion");
                }
            }
        } catch (IOException e) {
        } catch(AWTException e){
        } catch(ClassNotFoundException e){}

    }
    
    public void CerrarConexion() throws IOException{
        socket.close();
    }
    
    public ArrayList<Integer> CodificarString(String cadena){
        ArrayList<Integer> lista = new ArrayList<>();
        for(int i = 0; i<cadena.length(); i++){
            int ascii = cadena.codePointAt(i);
            lista.add(CodificarInt(ascii));
            System.out.println(CodificarInt(ascii));
        }
        return lista;
    }
    
    public int CodificarInt(int entero){
        
        int n1 = entero * 84 * 24;
        int n2  = (n1 + 58) * 54;
        int n3 = (n2 * 34) / 44;
        
        return (int) n2;
    }
    
    public int DecodificarInt(int entero){
        
        int n1 = (entero * 44) / 34;
        int n2 = (entero / 54) - 58;
        int n3 = n2 / (84*24);

        return n3;
    }
    
    public String DecodificarLista(ArrayList<Integer> lista){
        StringBuilder resultado = new StringBuilder();
        for(Integer ent : lista){
            int decodificado = DecodificarInt(ent);
            resultado.appendCodePoint(decodificado);
        }
        return resultado.toString();
    }
    
    public static void main(String[] args) {
        Chat c = new Chat();
        ArrayList<Integer> lista = c.CodificarString("hola");
        System.out.println("");
        System.out.println(c.DecodificarLista(lista));
    }
    
}
