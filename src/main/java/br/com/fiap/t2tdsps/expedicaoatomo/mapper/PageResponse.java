package br.com.fiap.t2tdsps.expedicaoatomo.mapper;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Página de resultados, sem os campos internos do Page do Spring")
public record PageResponse<T>(
        @Schema(description = "Itens da página atual")
        List<T> content,

        @Schema(description = "Número da página, começando em 0", example = "0")
        int page,

        @Schema(description = "Tamanho da página pedido em sizePage", example = "10")
        int size,

        @Schema(description = "Total de itens encontrados em todas as páginas", example = "11")
        long totalElements,

        @Schema(description = "Total de páginas", example = "2")
        int totalPages,

        @Schema(description = "true quando esta é a última página", example = "false")
        boolean last
) {

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
