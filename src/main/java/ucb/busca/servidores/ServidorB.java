package ucb.busca.servidores;

import java.io.*;
import java.net.*;

public class ServidorB {
    public static void main(String[] args) {
        int PORTA = 12345;

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