package br.com.fiap.t2tdsps.expedicaoatomo.controller;

import br.com.fiap.t2tdsps.expedicaoatomo.configurations.ExemplosOpenApi;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaAplicacaoResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaResumoResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.ErrorCustomResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.ValidationErrorResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.AreaAplicacaoMapper;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PageResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PontoMapaMapper;
import br.com.fiap.t2tdsps.expedicaoatomo.service.AreaAplicacaoService;
import br.com.fiap.t2tdsps.expedicaoatomo.service.PontoMapaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@Validated
@Tag(name = "Áreas de aplicação",
        description = "As cinco áreas do enunciado do CTMSP: saúde, indústria, agricultura, pesquisa científica e energia. "
                + "É um catálogo fixo e só de leitura, sem POST, PUT ou DELETE, porque os ids e nomes são os mesmos do "
                + "modelo Oracle e dos dados do app, e os filtros do mapa dependem deles.")
public class AreaAplicacaoController {

    private final AreaAplicacaoService areaAplicacaoService;
    private final PontoMapaService pontoMapaService;

    public AreaAplicacaoController(AreaAplicacaoService areaAplicacaoService, PontoMapaService pontoMapaService) {
        this.areaAplicacaoService = areaAplicacaoService;
        this.pontoMapaService = pontoMapaService;
    }

    @GetMapping
    @Operation(operationId = "listarAreas", summary = "Lista as cinco áreas de aplicação",
            description = "Devolve as áreas em ordem alfabética, com id, nome e descrição. O app usa essa lista para "
                    + "montar os filtros do mapa.")
    @ApiResponse(responseCode = "200", description = "Lista com as cinco áreas")
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public List<AreaAplicacaoResponse> listar() {
        return areaAplicacaoService.listar().stream()
                .map(AreaAplicacaoMapper::toResponse)
                .toList();
    }

    @GetMapping("/resumo")
    @Operation(operationId = "resumirAreas", summary = "Conta os pontos de cada área",
            description = "Devolve cada área com a quantidade de pontos, em ordem alfabética. A contagem sai de uma "
                    + "consulta JPQL com left join e group by, então uma área sem ponto aparece com 0.")
    @ApiResponse(responseCode = "200", description = "Lista de áreas com a quantidade de pontos")
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public List<AreaResumoResponse> resumir() {
        return areaAplicacaoService.resumir();
    }

    @GetMapping("/{id}")
    @Operation(operationId = "buscarAreaPorId", summary = "Busca uma área pelo id",
            description = "Ids dos dados de teste: 1 Saúde, 2 Indústria, 3 Agricultura, 4 Pesquisa Científica e 5 Energia.")
    @ApiResponse(responseCode = "200", description = "Área encontrada")
    @ApiResponse(responseCode = "400", description = "id menor que 1 ou que não é número",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorCustomResponse.class}),
                    examples = {
                            @ExampleObject(name = "id menor que 1", value = ExemplosOpenApi.ID_MENOR_QUE_UM),
                            @ExampleObject(name = "id que não é número", value = ExemplosOpenApi.ID_NAO_NUMERICO)}))
    @ApiResponse(responseCode = "404", description = "Nenhuma área com esse id",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.AREA_NAO_EXISTE)))
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public ResponseEntity<AreaAplicacaoResponse> buscarPorId(
            @Parameter(description = "Id da área, de 1 a 5", example = "1")
            @PathVariable @Positive(message = "id deve ser maior que zero") Long id) {
        return ResponseEntity.ok(AreaAplicacaoMapper.toResponse(areaAplicacaoService.buscarPorId(id)));
    }

    @GetMapping("/{id}/pontos")
    @Operation(operationId = "listarPontosDaArea", summary = "Lista os pontos de uma área",
            description = "Página de pontos da área, ordenada por título, com o Query Method findByAreaId. "
                    + "Devolve 404 se a área não existir.")
    @ApiResponse(responseCode = "200", description = "Página de pontos da área")
    @ApiResponse(responseCode = "400", description = "id ou paginação inválidos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorCustomResponse.class}),
                    examples = {
                            @ExampleObject(name = "id menor que 1", value = ExemplosOpenApi.ID_MENOR_QUE_UM),
                            @ExampleObject(name = "sizePage acima de 50", value = ExemplosOpenApi.PAGINACAO_INVALIDA)}))
    @ApiResponse(responseCode = "404", description = "Nenhuma área com esse id",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.AREA_NAO_EXISTE)))
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public PageResponse<PontoMapaResponse> listarPontos(
            @Parameter(description = "Id da área, de 1 a 5", example = "1")
            @PathVariable @Positive(message = "id deve ser maior que zero") Long id,
            @Parameter(description = "Número da página, começando em 0", example = "0")
            @RequestParam(required = false, defaultValue = "0")
            @PositiveOrZero(message = "page deve ser 0 ou maior")
            Integer page,
            @Parameter(description = "Quantidade de pontos por página, de 1 a 50", example = "10")
            @RequestParam(required = false, defaultValue = "10")
            @Min(value = 1, message = "sizePage deve ficar entre 1 e 50")
            @Max(value = 50, message = "sizePage deve ficar entre 1 e 50")
            Integer sizePage) {
        return PageResponse.from(
                pontoMapaService.listarPorArea(id, page, sizePage).map(PontoMapaMapper::toResponse));
    }
}
