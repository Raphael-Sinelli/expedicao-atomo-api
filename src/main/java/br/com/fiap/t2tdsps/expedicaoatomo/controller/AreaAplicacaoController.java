package br.com.fiap.t2tdsps.expedicaoatomo.controller;

import br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaAplicacaoResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.AreaResumoResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.dto.PontoMapaResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.AreaAplicacaoMapper;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PageResponse;
import br.com.fiap.t2tdsps.expedicaoatomo.mapper.PontoMapaMapper;
import br.com.fiap.t2tdsps.expedicaoatomo.service.AreaAplicacaoService;
import br.com.fiap.t2tdsps.expedicaoatomo.service.PontoMapaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
public class AreaAplicacaoController {

    private final AreaAplicacaoService areaAplicacaoService;
    private final PontoMapaService pontoMapaService;

    public AreaAplicacaoController(AreaAplicacaoService areaAplicacaoService, PontoMapaService pontoMapaService) {
        this.areaAplicacaoService = areaAplicacaoService;
        this.pontoMapaService = pontoMapaService;
    }

    @GetMapping
    public List<AreaAplicacaoResponse> listar() {
        return areaAplicacaoService.listar().stream()
                .map(AreaAplicacaoMapper::toResponse)
                .toList();
    }

    @GetMapping("/resumo")
    public List<AreaResumoResponse> resumir() {
        return areaAplicacaoService.resumir();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaAplicacaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(AreaAplicacaoMapper.toResponse(areaAplicacaoService.buscarPorId(id)));
    }

    @GetMapping("/{id}/pontos")
    public PageResponse<PontoMapaResponse> listarPontos(@PathVariable Long id,
                                                        @RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer sizePage) {
        return PageResponse.from(
                pontoMapaService.listarPorArea(id, page, sizePage).map(PontoMapaMapper::toResponse));
    }
}
