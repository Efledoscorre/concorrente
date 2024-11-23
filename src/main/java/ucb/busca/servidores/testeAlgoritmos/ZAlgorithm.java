package ucb.busca.servidores.testeAlgoritmos;

import java.io.PrintWriter;

public class ZAlgorithm{


    public void buscaSubString(String text, String substring, PrintWriter saida){

        String concat = substring + "$" + text;

        int l = concat.length();

        int[] Z = new int[l];

        getZarr(concat, Z);

        for(int i = 0; i < l; ++i){

            if(Z[i] == substring.length()){
                String stringencontrada = "Substring encontrada no index "
                        + (i - substring.length() - 1);
                //saida.println(stringencontrada);
                saida.write(stringencontrada);
            }
        }
    }

    private void getZarr(String str, int[] Z) {

        int n = str.length();

        int L = 0, R = 0;

        for(int i = 1; i < n; ++i) {

            if(i > R){

                L = R = i;

                while(R < n && str.charAt(R - L) == str.charAt(R))
                    R++;

                Z[i] = R - L;
                R--;

            }
            else{

                int k = i - L;

                if(Z[k] < R - i + 1)
                    Z[i] = Z[k];
                else{
                    L = i;
                    while(R < n && str.charAt(R - L) == str.charAt(R))
                        R++;

                    Z[i] = R - L;
                    R--;
                }
            }
        }
    }
}