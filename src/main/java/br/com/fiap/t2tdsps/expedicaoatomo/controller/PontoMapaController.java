package br.com.fiap.t2tdsps.expedicaoatomo.controller;

import br.com.fiap.t2tdsps.expedicaoatomo.configurations.ExemplosOpenApi;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.ErrorCustomResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaRequest;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.ValidationErrorResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PageResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PontoMapaMapper;
import br.com.fiap.t2tdsps.expedicaoatomo.model.PontoMapa;
import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import br.com.fiap.t2tdsps.expedicaoatomo.service.PontoMapaService;
import br.com.fiap.t2tdsps.expedicaoatomo.validation.UfValida;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/pontos")
@Validated
@Tag(name = "Pontos do mapa",
        description = "CRUD dos lugares reais que aparecem no mapa do app, de hospitais e usinas a praias com radiação "
                + "natural e ao acidente de Goiânia. Todo ponto traz resumo, curiosidade e o link da fonte oficial.")
public class PontoMapaController {

    private final PontoMapaService pontoMapaService;

    public PontoMapaController(PontoMapaService pontoMapaService) {
        this.pontoMapaService = pontoMapaService;
    }

    @GetMapping
    @Operation(summary = "Lista os pontos do mapa com filtros opcionais",
            description = "Página de pontos ordenada por título. Os filtros areaId, uf e tipo são opcionais e podem ser "
                    + "combinados, como em ?uf=SP&tipo=INSTALACAO. Filtro ausente não restringe nada.")
    @ApiResponse(responseCode = "200", description = "Página de pontos, que pode vir vazia")
    @ApiResponse(responseCode = "400", description = "Filtro ou paginação inválidos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorCustomResponse.class}),
                    examples = {
                            @ExampleObject(name = "UF fora da lista oficial", value = ExemplosOpenApi.FILTRO_UF_INVALIDA),
                            @ExampleObject(name = "tipo inexistente", value = ExemplosOpenApi.FILTRO_TIPO_INVALIDO),
                            @ExampleObject(name = "sizePage acima de 50", value = ExemplosOpenApi.PAGINACAO_INVALIDA)}))
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public PageResponse<PontoMapaResponse> listar(
            @Parameter(description = "Id da área de aplicação, de 1 a 5", example = "1")
            @RequestParam(required = false) @Positive(message = "areaId deve ser maior que zero") Long areaId,
            @Parameter(description = "Sigla da UF em maiúsculas", example = "SP")
            @RequestParam(required = false) @UfValida String uf,
            @Parameter(description = "Tipo do ponto: INSTALACAO, NATURAL ou HISTORICO", example = "INSTALACAO")
            @RequestParam(required = false) TipoPonto tipo,
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
                pontoMapaService.listar(areaId, uf, tipo, page, sizePage).map(PontoMapaMapper::toResponse));
    }

    @GetMapping("/busca")
    @Operation(summary = "Busca pontos por parte do título",
            description = "Requisito \"busca por texto usando Query Method\": o repositório usa "
                    + "findByTituloContainingIgnoreCase, que acha o termo em qualquer parte do título sem diferenciar "
                    + "maiúsculas. Com termo=reator, volta o RMB e o TRIGA do CDTN. Sem resultado, devolve 200 com página vazia.")
    @ApiResponse(responseCode = "200", description = "Página com os pontos cujo título contém o termo")
    @ApiResponse(responseCode = "400", description = "Termo ausente, em branco, com menos de 2 ou mais de 100 caracteres",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorCustomResponse.class}),
                    examples = {
                            @ExampleObject(name = "termo com 1 caractere", value = ExemplosOpenApi.TERMO_CURTO),
                            @ExampleObject(name = "termo não informado", value = ExemplosOpenApi.TERMO_AUSENTE)}))
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public PageResponse<PontoMapaResponse> buscarPorTitulo(
            @Parameter(description = "Parte do título, de 2 a 100 caracteres", example = "reator")
            @RequestParam
            @NotBlank(message = "termo não pode ficar em branco")
            @Size(min = 2, max = 100, message = "termo deve ter entre 2 e 100 caracteres")
            String termo,
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
                pontoMapaService.buscarPorTitulo(termo, page, sizePage).map(PontoMapaMapper::toResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um ponto pelo id",
            description = "Devolve o ponto com o id e o nome da área. Os pontos de teste têm ids de 1 a 11.")
    @ApiResponse(responseCode = "200", description = "Ponto encontrado")
    @ApiResponse(responseCode = "400", description = "id menor que 1 ou que não é número",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorCustomResponse.class}),
                    examples = {
                            @ExampleObject(name = "id menor que 1", value = ExemplosOpenApi.ID_MENOR_QUE_UM),
                            @ExampleObject(name = "id que não é número", value = ExemplosOpenApi.ID_NAO_NUMERICO)}))
    @ApiResponse(responseCode = "404", description = "Nenhum ponto com esse id",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.PONTO_NAO_EXISTE)))
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public ResponseEntity<PontoMapaResponse> buscarPorId(
            @Parameter(description = "Id do ponto", example = "2")
            @PathVariable @Positive(message = "id deve ser maior que zero") Long id) {
        return ResponseEntity.ok(PontoMapaMapper.toResponse(pontoMapaService.buscarPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Cadastra um ponto no mapa",
            description = "O servidor gera o id e a data de cadastro e busca a área pelo idArea. O título não pode repetir "
                    + "o de outro ponto, sem diferenciar maiúsculas. O exemplo cadastra o reator Argonauta, do IEN, "
                    + "que não está nos dados de teste.")
    @ApiResponse(responseCode = "201", description = "Ponto criado; o header Location aponta para ele",
            headers = @Header(name = "Location", description = "Caminho do ponto criado",
                    schema = @Schema(type = "string", example = "/api/pontos/12")))
    @ApiResponse(responseCode = "400", description = "Campos inválidos ou JSON malformado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorCustomResponse.class}),
                    examples = {
                            @ExampleObject(name = "UF, latitude e URL inválidas", value = ExemplosOpenApi.CAMPOS_INVALIDOS),
                            @ExampleObject(name = "JSON malformado ou tipo inexistente", value = ExemplosOpenApi.JSON_INVALIDO)}))
    @ApiResponse(responseCode = "404", description = "idArea não corresponde a nenhuma área",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.AREA_NAO_EXISTE)))
    @ApiResponse(responseCode = "409", description = "Já existe ponto com esse título",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.TITULO_DUPLICADO)))
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public ResponseEntity<PontoMapaResponse> criar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do novo ponto. O id e a data de cadastro não vão no corpo, porque o servidor gera os dois.",
                    required = true)
            @RequestBody @Valid PontoMapaRequest request) {
        PontoMapa criado = pontoMapaService.criar(request);
        return ResponseEntity.created(URI.create("/api/pontos/" + criado.getId()))
                .body(PontoMapaMapper.toResponse(criado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza todos os campos de um ponto",
            description = "Substitui todos os campos editáveis do ponto. O id e a data de cadastro continuam os mesmos. "
                    + "O título não pode ser igual ao de outro ponto.")
    @ApiResponse(responseCode = "200", description = "Ponto atualizado")
    @ApiResponse(responseCode = "400", description = "id, campos ou JSON inválidos",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorCustomResponse.class}),
                    examples = {
                            @ExampleObject(name = "UF, latitude e URL inválidas", value = ExemplosOpenApi.CAMPOS_INVALIDOS),
                            @ExampleObject(name = "id menor que 1", value = ExemplosOpenApi.ID_MENOR_QUE_UM),
                            @ExampleObject(name = "JSON malformado ou tipo inexistente", value = ExemplosOpenApi.JSON_INVALIDO)}))
    @ApiResponse(responseCode = "404", description = "Ponto ou área inexistente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = {
                            @ExampleObject(name = "ponto inexistente", value = ExemplosOpenApi.PONTO_NAO_EXISTE),
                            @ExampleObject(name = "área inexistente", value = ExemplosOpenApi.AREA_NAO_EXISTE)}))
    @ApiResponse(responseCode = "409", description = "Outro ponto já usa esse título",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.TITULO_DUPLICADO)))
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public ResponseEntity<PontoMapaResponse> atualizar(
            @Parameter(description = "Id do ponto a atualizar; o POST de exemplo cria o id 12", example = "12")
            @PathVariable @Positive(message = "id deve ser maior que zero") Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Todos os campos editáveis do ponto, com as mesmas regras do POST. "
                            + "Uma curiosidade ausente apaga a que estava gravada.",
                    required = true)
            @RequestBody @Valid PontoMapaRequest request) {
        return ResponseEntity.ok(PontoMapaMapper.toResponse(pontoMapaService.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um ponto do mapa",
            description = "Remove o ponto e devolve 204 sem corpo. Depois disso, um GET do mesmo id devolve 404.")
    @ApiResponse(responseCode = "204", description = "Ponto excluído", content = @Content)
    @ApiResponse(responseCode = "400", description = "id menor que 1 ou que não é número",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(oneOf = {ValidationErrorResponse.class, ErrorCustomResponse.class}),
                    examples = {
                            @ExampleObject(name = "id menor que 1", value = ExemplosOpenApi.ID_MENOR_QUE_UM),
                            @ExampleObject(name = "id que não é número", value = ExemplosOpenApi.ID_NAO_NUMERICO)}))
    @ApiResponse(responseCode = "404", description = "Nenhum ponto com esse id",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.PONTO_NAO_EXISTE)))
    @ApiResponse(responseCode = "500", description = "Erro inesperado no servidor",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCustomResponse.class),
                    examples = @ExampleObject(value = ExemplosOpenApi.ERRO_INTERNO)))
    public ResponseEntity<Void> excluir(
            @Parameter(description = "Id do ponto a excluir", example = "12")
            @PathVariable @Positive(message = "id deve ser maior que zero") Long id) {
        pontoMapaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
