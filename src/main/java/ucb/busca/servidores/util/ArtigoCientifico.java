package ucb.busca.servidores.util;

import java.io.Serializable;

public class  ArtigoCientifico implements Serializable {
    private String titulo;
    private String resumo;
    private String rotulo;


    public ArtigoCientifico(String titulo, String resumo, String rotulo) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.rotulo = rotulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getResumo() {
        return resumo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getRotulo() {
        return rotulo;
    }




}
