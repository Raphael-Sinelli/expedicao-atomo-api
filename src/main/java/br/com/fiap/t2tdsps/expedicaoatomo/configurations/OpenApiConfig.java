package br.com.fiap.t2tdsps.expedicaoatomo.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Expedição Átomo: API do Mapa Nuclear",
                version = "1.0.0 (Sprint 1)",
                description = "API REST que alimenta o Mapa Nuclear do app Expedição Átomo, criado no Challenge FIAP 2026 "
                        + "com o Centro Tecnológico da Marinha em São Paulo (CTMSP). Ela serve as cinco áreas de aplicação "
                        + "do enunciado (saúde, indústria, agricultura, pesquisa científica e energia) e os lugares reais do "
                        + "mapa, como o IPEN, Angra 1 e 2, a Moscamed e o local do acidente com césio-137 em Goiânia, cada "
                        + "um com o link da fonte oficial. As missões Mito ou Verdade e o XP ficam na API .NET.",
                contact = @Contact(name = "Grupo Expedição Átomo, 2TDSPS")
        )
)
public class OpenApiConfig {
}
