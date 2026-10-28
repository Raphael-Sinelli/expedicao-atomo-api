package br.com.fiap.t2tdsps.expedicaoatomo.controller;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaRequest;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PageResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PontoMapaMapper;
import br.com.fiap.t2tdsps.expedicaoatomo.model.PontoMapa;
import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import br.com.fiap.t2tdsps.expedicaoatomo.service.PontoMapaService;
import br.com.fiap.t2tdsps.expedicaoatomo.validation.UfValida;
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
public class PontoMapaController {

    private final PontoMapaService pontoMapaService;

    public PontoMapaController(PontoMapaService pontoMapaService) {
        this.pontoMapaService = pontoMapaService;
    }

    @GetMapping
    public PageResponse<PontoMapaResponse> listar(
            @RequestParam(required = false) @Positive(message = "areaId deve ser maior que zero") Long areaId,
            @RequestParam(required = false) @UfValida String uf,
            @RequestParam(required = false) TipoPonto tipo,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero(message = "page deve ser 0 ou maior") Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "sizePage deve ficar entre 1 e 50") @Max(value = 50, message = "sizePage deve ficar entre 1 e 50") Integer sizePage) {
        return PageResponse.from(
                pontoMapaService.listar(areaId, uf, tipo, page, sizePage).map(PontoMapaMapper::toResponse));
    }

    @GetMapping("/busca")
    public PageResponse<PontoMapaResponse> buscarPorTitulo(
            @RequestParam @NotBlank(message = "termo não pode ficar em branco") @Size(min = 2, max = 100, message = "termo deve ter entre 2 e 100 caracteres") String termo,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero(message = "page deve ser 0 ou maior") Integer page,
            @RequestParam(required = false, defaultValue = "10") @Min(value = 1, message = "sizePage deve ficar entre 1 e 50") @Max(value = 50, message = "sizePage deve ficar entre 1 e 50") Integer sizePage) {
        return PageResponse.from(
                pontoMapaService.buscarPorTitulo(termo, page, sizePage).map(PontoMapaMapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoMapaResponse> buscarPorId(
            @PathVariable @Positive(message = "id deve ser maior que zero") Long id) {
        return ResponseEntity.ok(PontoMapaMapper.toResponse(pontoMapaService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<PontoMapaResponse> criar(@RequestBody @Valid PontoMapaRequest request) {
        PontoMapa criado = pontoMapaService.criar(request);
        return ResponseEntity.created(URI.create("/api/pontos/" + criado.getId()))
                .body(PontoMapaMapper.toResponse(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoMapaResponse> atualizar(
            @PathVariable @Positive(message = "id deve ser maior que zero") Long id,
            @RequestBody @Valid PontoMapaRequest request) {
        return ResponseEntity.ok(PontoMapaMapper.toResponse(pontoMapaService.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive(message = "id deve ser maior que zero") Long id) {
        pontoMapaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
