package br.com.fiap.t2tdsps.expedicaoatomo.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String recurso, Long id) {
        super(recurso + " com id " + id + " não existe");
    }
}
