package ucb.busca.servidores;

import java.io.*;
import java.net.*;

public class ServidorA{
    public static void main(String[] args) {
    	criandoCliente();
    	criandoServidor();
   
    }
    
    private static void criandoCliente() {
        String SERVIDOR = "localhost";
        int PORTA = 12345;
        try {
            Socket servidor = new Socket(SERVIDOR, PORTA);
            System.out.println("Conectado ao servidor!");


            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));


            PrintWriter saida = new PrintWriter(servidor.getOutputStream(), true);

            String mensagem = entrada.readLine();
            System.out.println("Mensagem do servidor: " + mensagem);

            saida.println("Obrigado, servidor! Estou conectado.");

            entrada.close();
            saida.close();
            servidor.close();

        } catch(IOException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }
    
    
    private static void criandoServidor(){
        int PORTA = 54321;

        try {
            ServerSocket servidor = new ServerSocket(PORTA);
            System.out.println("Servidor escutando na porta: " + PORTA + " ...");


            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());


            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));


            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);

            saida.println("Bem-vindo ao servidor!");
            saida.flush();
            String mensagem = entrada.readLine();
            System.out.println("Mensagem do cliente: " + mensagem);

            entrada.close();
            saida.close();
            cliente.close();
            servidor.close();
        }catch(Exception e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}