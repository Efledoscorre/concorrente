package ucb.busca;

import ucb.busca.servidores.util.ArtigoCientifico;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Main{

	private static final Scanner SCANNER = new Scanner(System.in);
    private static final String SERVIDOR = "localhost";
    private static final int PORTA = 54321;

    public static void main(String[] args){

        try {
            Socket servidor = new Socket(SERVIDOR, PORTA);
            System.out.println("Conectado ao servidor!");

            System.out.print("Digite o que deseja pesquisar: ");
            String subString = SCANNER.nextLine();

            PrintWriter dataToServidor = new PrintWriter(servidor.getOutputStream(), true);
            dataToServidor.println(subString);

            long inicio = System.currentTimeMillis();
            ObjectInputStream dataFromServidor = new ObjectInputStream(servidor.getInputStream());

            List<ArtigoCientifico> artigosTotal = (List<ArtigoCientifico>) dataFromServidor.readObject();
            long fim = System.currentTimeMillis();

            for(int i = 0 ; i < artigosTotal.size(); i++){
            	System.out.println("----------------------------------------");
                System.out.println("Rótulo: " + artigosTotal.get(i).getRotulo());
            	System.out.println("Título: ");
                System.out.println(artigosTotal.get(i).getTitulo());
                System.out.println("Resumo: ");
                System.out.println(artigosTotal.get(i).getResumo());
            }

            System.out.printf("""
                    
                    %d artigos científicos encontrados.
                    Levou %dms
                    """, artigosTotal.size(), Math.abs(inicio - fim));

            dataFromServidor.close();
            dataToServidor.close();
            servidor.close();

        } catch(Exception e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }

}
