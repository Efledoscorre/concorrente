package ucb.busca.servidores.testeAlgoritmos;

import org.json.JSONObject;
import ucb.busca.servidores.util.ArtigoCientifico;

import java.util.*;
import java.util.regex.Matcher;

public class AhoCorasickAlgorithm implements SearchAlgorithm {
	private static final int MAXS = 500;
    private static final int MAXC = 256;
    private int[] out;
    private int[] f;
    private int[][] g;

    public AhoCorasickAlgorithm() {
        out = new int[MAXS];
        f = new int[MAXS];
        g = new int[MAXS][MAXC];
    }

    @Override
    public List<ArtigoCientifico> buscaSubString(String text, String substring) {
        Set<Integer> chavesArtigosEncontrados = new HashSet<>();
        Matcher matcher;

        String[] arr = new String[] {substring};
        int k = arr.length;
        buildMatchingMachine(arr, k);

        int currentState = 0;
        for (int i = 0; i < text.length(); i++) {
            currentState = findNextState(currentState, text.charAt(i));

            if (out[currentState] == 0) continue;

            for (int j = 0; j < k; j++) {
                if ((out[currentState] & (1 << j)) > 0) {
                    StringBuilder substringBuilder = new StringBuilder(substring);
                    int indexSubstringNoTexto = i - substring.length() - 1;

                    while(true) {
                        matcher = PATTERN.matcher(substringBuilder);

                        if (matcher.find())
                            break;

                        substringBuilder.insert(0, text.charAt(--indexSubstringNoTexto));
                    }
                    String chaveSubstring = matcher.group();
                    int chaveComoNumero = Integer.parseInt(chaveSubstring.replaceAll("\"", "").replaceAll(":", ""));
                    chavesArtigosEncontrados.add(chaveComoNumero + 1);
                }
            }
        }
        return retornaArtigosCientificos(chavesArtigosEncontrados, text);
    }

    private List<ArtigoCientifico> retornaArtigosCientificos(Set<Integer> chavesSubstrings, String text){

        List<ArtigoCientifico> artigosCientificos = new LinkedList<>();

        final JSONObject artigosJson = new JSONObject(text);

        final JSONObject titulosJson = artigosJson.getJSONObject("title");
        final JSONObject resumoJson = artigosJson.getJSONObject("abstract");
        final JSONObject labelJson = artigosJson.getJSONObject("label");

        chavesSubstrings.forEach(chave -> {
            String titulo = titulosJson.getString(String.valueOf(chave));
            String resumo = resumoJson.getString(String.valueOf(chave));
            String rotulo = labelJson.getString(String.valueOf(chave));

            artigosCientificos.add(new ArtigoCientifico(titulo, resumo, rotulo));
        });

        return artigosCientificos;
    }

    private int buildMatchingMachine(String[] arr, int k) {
        Arrays.fill(out, 0);
        for (int[] row : g) Arrays.fill(row, -1);
        int states = 1;

        for (String word : arr) {
            int currentState = 0;
            for (char ch : word.toCharArray()) {
                int c = ch;
                if (g[currentState][c] == -1)
                    g[currentState][c] = states++;
                currentState = g[currentState][c];
            }
            out[currentState] |= (1 << Arrays.asList(arr).indexOf(word));
        }

        for (int c = 0; c < MAXC; c++)
            if (g[0][c] == -1)
                g[0][c] = 0;

        Arrays.fill(f, -1);
        Queue<Integer> queue = new LinkedList<>();
        for (int c = 0; c < MAXC; c++) {
            if (g[0][c] != 0) {
                f[g[0][c]] = 0;
                queue.add(g[0][c]);
            }
        }

        while (!queue.isEmpty()) {
            int state = queue.poll();

            for (int c = 0; c < MAXC; c++) {
                if (g[state][c] != -1) {
                    int failure = f[state];
                    while (g[failure][c] == -1)
                        failure = f[failure];
                    f[g[state][c]] = g[failure][c];
                    out[g[state][c]] |= out[f[g[state][c]]];
                    queue.add(g[state][c]);
                }
            }
        }
        return states;
    }

    private int findNextState(int currentState, char nextInput) {
        int answer = currentState;
        int c = nextInput;
        while (g[answer][c] == -1)
            answer = f[answer];
        return g[answer][c];
    }
}
