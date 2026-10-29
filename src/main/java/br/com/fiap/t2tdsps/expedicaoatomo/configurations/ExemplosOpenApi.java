package br.com.fiap.t2tdsps.expedicaoatomo.configurations;

// Corpos de erro reais da API, usados como @ExampleObject no Swagger. Ficam aqui para não repetir o mesmo JSON
// em cada endpoint.
public final class ExemplosOpenApi {

    public static final String ID_MENOR_QUE_UM = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "errors": [{"field": "id", "message": "id deve ser maior que zero"}]}""";

    public static final String ID_NAO_NUMERICO = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "message": "Parâmetro 'id' inválido: abc"}""";

    public static final String PAGINACAO_INVALIDA = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "errors": [{"field": "sizePage", "message": "sizePage deve ficar entre 1 e 50"}]}""";

    public static final String FILTRO_UF_INVALIDA = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "errors": [{"field": "uf", "message": "UF deve ser uma das 27 siglas oficiais em maiúsculas, como SP"}]}""";

    public static final String FILTRO_TIPO_INVALIDO = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "message": "Parâmetro 'tipo' inválido: BOMBA. Valores aceitos: [INSTALACAO, NATURAL, HISTORICO]"}""";

    public static final String TERMO_CURTO = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "errors": [{"field": "termo", "message": "termo deve ter entre 2 e 100 caracteres"}]}""";

    public static final String TERMO_AUSENTE = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "message": "Parâmetro obrigatório 'termo' não foi informado"}""";

    public static final String CAMPOS_INVALIDOS = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "errors": [
               {"field": "uf", "message": "UF deve ser uma das 27 siglas oficiais em maiúsculas, como SP"},
               {"field": "latitude", "message": "Latitude deve estar entre -33.75 e 5.27, do Chuí (RS) ao Monte Caburaí (RR)"},
               {"field": "urlFonte", "message": "URL da fonte deve começar com https:// e não pode ter espaços"}
             ]}""";

    public static final String JSON_INVALIDO = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 400, "error": "Bad Request",
             "message": "JSON inválido: confira vírgulas, aspas e o tipo de cada campo. tipo aceita INSTALACAO, NATURAL ou HISTORICO"}""";

    public static final String PONTO_NAO_EXISTE = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 404, "error": "Not Found",
             "message": "Ponto do mapa com id 9999 não existe"}""";

    public static final String AREA_NAO_EXISTE = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 404, "error": "Not Found",
             "message": "Área de aplicação com id 99 não existe"}""";

    public static final String TITULO_DUPLICADO = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 409, "error": "Conflict",
             "message": "Já existe um ponto do mapa com o título 'Radiofármacos do IPEN', sem diferenciar maiúsculas de minúsculas"}""";

    public static final String ERRO_INTERNO = """
            {"timestamp": "2026-10-29T10:15:30.123", "status": 500, "error": "Internal Server Error",
             "message": "Erro interno inesperado"}""";

    private ExemplosOpenApi() {
    }
}
