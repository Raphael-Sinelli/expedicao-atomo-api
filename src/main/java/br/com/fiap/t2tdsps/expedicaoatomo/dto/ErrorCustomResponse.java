package br.com.fiap.t2tdsps.expedicaoatomo.dto;

import java.time.LocalDateTime;

public record ErrorCustomResponse(LocalDateTime timestamp, int status, String error, String message) {
}
