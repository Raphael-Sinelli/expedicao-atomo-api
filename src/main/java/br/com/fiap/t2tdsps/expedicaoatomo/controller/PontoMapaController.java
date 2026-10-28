package br.com.fiap.t2tdsps.expedicaoatomo.controller;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaRequest;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PageResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PontoMapaMapper;
import br.com.fiap.t2tdsps.expedicaoatomo.model.PontoMapa;
import br.com.fiap.t2tdsps.expedicaoatomo.model.TipoPonto;
import br.com.fiap.t2tdsps.expedicaoatomo.service.PontoMapaService;
import org.springframework.http.ResponseEntity;
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
public class PontoMapaController {

    private final PontoMapaService pontoMapaService;

    public PontoMapaController(PontoMapaService pontoMapaService) {
        this.pontoMapaService = pontoMapaService;
    }

    @GetMapping
    public PageResponse<PontoMapaResponse> listar(@RequestParam(required = false) Long areaId,
                                                  @RequestParam(required = false) String uf,
                                                  @RequestParam(required = false) TipoPonto tipo,
                                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer sizePage) {
        return PageResponse.from(
                pontoMapaService.listar(areaId, uf, tipo, page, sizePage).map(PontoMapaMapper::toResponse));
    }

    @GetMapping("/busca")
    public PageResponse<PontoMapaResponse> buscarPorTitulo(@RequestParam String termo,
                                                           @RequestParam(required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(required = false, defaultValue = "10") Integer sizePage) {
        return PageResponse.from(
                pontoMapaService.buscarPorTitulo(termo, page, sizePage).map(PontoMapaMapper::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoMapaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(PontoMapaMapper.toResponse(pontoMapaService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<PontoMapaResponse> criar(@RequestBody PontoMapaRequest request) {
        PontoMapa criado = pontoMapaService.criar(request);
        return ResponseEntity.created(URI.create("/api/pontos/" + criado.getId()))
                .body(PontoMapaMapper.toResponse(criado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoMapaResponse> atualizar(@PathVariable Long id,
                                                       @RequestBody PontoMapaRequest request) {
        return ResponseEntity.ok(PontoMapaMapper.toResponse(pontoMapaService.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pontoMapaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
