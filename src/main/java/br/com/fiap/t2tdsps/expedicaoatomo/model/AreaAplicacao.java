package br.com.fiap.t2tdsps.expedicaoatomo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AREA_APLICACAO")
@Getter
@Setter
@NoArgsConstructor
public class AreaAplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area")
    private Long id;

    @Column(name = "nm_area", nullable = false, unique = true, length = 60)
    private String nome;

    @Column(name = "ds_area", nullable = false, length = 400)
    private String descricao;

    // Sem cascade: a área é catálogo fixo, então salvar ou apagar uma área nunca mexe nos pontos.
    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<PontoMapa> pontos = new ArrayList<>();
}
