package br.com.fiap.t2tdsps.expedicaoatomo.exceptions;

public class TituloDuplicadoException extends RuntimeException {

    public TituloDuplicadoException(String titulo) {
        super("Já existe um ponto do mapa com o título '" + titulo + "', sem diferenciar maiúsculas de minúsculas");
    }
}
