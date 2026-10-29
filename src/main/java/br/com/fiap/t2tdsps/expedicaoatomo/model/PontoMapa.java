package br.com.fiap.t2tdsps.expedicaoatomo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "PONTO_MAPA")
@Getter
@Setter
@NoArgsConstructor
public class PontoMapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ponto")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_area", nullable = false)
    private AreaAplicacao area;

    @Column(name = "nm_titulo", nullable = false, unique = true, length = 150)
    private String titulo;

    @Column(name = "ds_resumo", nullable = false, length = 400)
    private String resumo;

    @Column(name = "ds_curiosidade", length = 400)
    private String curiosidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_ponto", nullable = false, length = 20)
    private TipoPonto tipo;

    @Column(name = "nm_cidade", nullable = false, length = 80)
    private String cidade;

    @Column(name = "sg_uf", nullable = false, length = 2)
    private String uf;

    @Column(name = "nr_latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "nr_longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "ds_url_fonte", nullable = false, length = 500)
    private String urlFonte;

    @Column(name = "dt_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    // Sem fração de segundo: o relógio do sistema tem mais casas do que a coluna guarda, e o POST devolveria
    // um valor diferente do que o GET lê depois.
    @PrePersist
    void preencherDataCadastro() {
        this.dataCadastro = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
