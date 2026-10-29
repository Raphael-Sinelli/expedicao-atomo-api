INSERT INTO AREA_APLICACAO (nm_area, ds_area) VALUES
('Saúde', 'Radiofármacos são remédios com substâncias radioativas que mostram em imagens como um órgão funciona e ajudam a tratar doenças como o câncer. A radioterapia usa radiação para destruir células de tumores. No Brasil, o IPEN, em São Paulo, fornece radiofármacos a 430 clínicas e hospitais.');

INSERT INTO AREA_APLICACAO (nm_area, ds_area) VALUES
('Indústria', 'A indústria nuclear transforma urânio em combustível para usinas, e a radiação também serve para inspecionar soldas e esterilizar material médico. A INB extrai urânio em Caetité (BA), na única mina ativa do país, e fabrica em Resende (RJ) o combustível das usinas de Angra.');

INSERT INTO AREA_APLICACAO (nm_area, ds_area) VALUES
('Agricultura', 'Técnicas nucleares ajudam a controlar pragas, a conservar alimentos e a estudar o caminho dos nutrientes no solo e nas plantas. Em Juazeiro (BA), a Moscamed solta insetos estéreis para combater a mosca-do-mediterrâneo nos pomares do Vale do São Francisco.');

INSERT INTO AREA_APLICACAO (nm_area, ds_area) VALUES
('Pesquisa Científica', 'Reatores de pesquisa e detectores de radiação servem para analisar amostras, estudar materiais e formar especialistas. Em Belo Horizonte (MG), o reator TRIGA IPR-R1 do CDTN, comprado pelo governo de Minas Gerais em 1960, ainda é usado em ensino e pesquisa. A radiação natural também é estudada, como nas areias monazíticas do Espírito Santo.');

INSERT INTO AREA_APLICACAO (nm_area, ds_area) VALUES
('Energia', 'Usinas nucleares geram eletricidade com o calor da fissão do urânio, sem queimar carvão, petróleo ou gás. Em Angra dos Reis (RJ), Angra 1 e Angra 2 somam 1.990 MW e são as duas usinas nucleares em operação no Brasil.');

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Saúde'),
 'Radiofármacos do IPEN',
 'Na Cidade Universitária, em São Paulo, o Centro de Radiofarmácia do IPEN produz radiofármacos, remédios com substâncias radioativas usados para diagnosticar e tratar doenças como o câncer. O IPEN fornece esses produtos a 430 clínicas e hospitais do país, que fazem cerca de 2 milhões de procedimentos por ano.',
 'O IPEN produz e distribui radiofármacos desde a década de 1950 e foi a primeira instituição do Brasil a desenvolver e vender esse tipo de remédio.',
 'INSTALACAO', 'São Paulo', 'SP', -23.5640, -46.7362,
 'https://www.gov.br/ipen/pt-br/centros-de-pesquisa/centro-de-radiofarmacia', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Saúde'),
 'Reator Multipropósito Brasileiro (RMB)',
 'O RMB é um reator de pesquisa em obras desde 2025 em Iperó, ao lado do Centro Experimental Aramar, da Marinha. Com até 30 MW térmicos, vai produzir radioisótopos para mais de 30 tipos de radiofármacos. A criticalidade, primeira reação em cadeia sustentada do reator, está prevista para 2032.',
 'Hoje o Brasil importa o molibdênio-99, que dá origem ao tecnécio-99m, usado em 80% dos procedimentos de medicina nuclear. Com o RMB, a CNEN prevê que o país fique autossuficiente.',
 'INSTALACAO', 'Iperó', 'SP', -23.3903, -47.6218,
 'https://www.gov.br/cnen/pt-br/rmb/os-avancos-para-a-saude-publica-no-brasil', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Energia'),
 'Aramar: ciclo do combustível e LABGENE',
 'No Centro Experimental Aramar, em Iperó, o Centro Tecnológico da Marinha em São Paulo (CTMSP) desenvolve o ciclo do combustível nuclear e constrói o LABGENE, protótipo em terra da planta de propulsão do submarino nuclear brasileiro. No LABGENE, os sistemas são testados antes de irem para o submarino.',
 'O Laboratório de Enriquecimento Isotópico do CTMSP, em Aramar, já produziu urânio enriquecido a 19,75% para os testes do combustível do RMB, o reator que está sendo construído no terreno vizinho.',
 'INSTALACAO', 'Iperó', 'SP', -23.4065, -47.6002,
 'https://www.marinha.mil.br/ctmsp/labgene', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Indústria'),
 'Enriquecimento de urânio da INB',
 'Na Fábrica de Combustível Nuclear da INB, em Resende, o urânio é enriquecido em ultracentrífugas desenvolvidas pelo CTMSP e pelo IPEN, transformado em pó e em pastilhas de dióxido de urânio e montado nos elementos combustíveis que abastecem Angra 1 e Angra 2.',
 'O reator de Angra 1 usa 121 elementos combustíveis, e o de Angra 2, 193. A fábrica de Resende ocupa 600 hectares às margens da Via Dutra.',
 'INSTALACAO', 'Resende', 'RJ', -22.5040, -44.6439,
 'https://www.inb.gov.br/A-INB/Onde-estamos/Resende', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Energia'),
 'Angra 1 e 2, Central Nuclear Almirante Álvaro Alberto',
 'Em Itaorna, Angra dos Reis, ficam as duas usinas nucleares em operação no Brasil. Angra 1, de 640 MW, opera desde 1985; Angra 2, de 1.350 MW, desde 2001. As duas usam reatores de água pressurizada (PWR), em que o calor da fissão do urânio produz o vapor que move as turbinas.',
 'Sozinha, Angra 2 gera energia suficiente para o consumo de uma cidade de 2 milhões de habitantes, como Belo Horizonte.',
 'INSTALACAO', 'Angra dos Reis', 'RJ', -23.0102, -44.4564,
 'https://www.eletronuclear.gov.br/Nossas-Atividades/Paginas/Angra-1.aspx', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Indústria'),
 'Mineração de urânio da INB',
 'Em Caetité, a INB opera a única mina de urânio ativa do Brasil. A unidade extrai o minério e o beneficia até virar concentrado de urânio, o yellowcake, primeira etapa do ciclo que termina no combustível das usinas de Angra.',
 'Entre 2000 e 2015, a mina da Cachoeira, em Caetité, produziu 3.750 toneladas de concentrado de urânio. Hoje a extração acontece na mina do Engenho.',
 'INSTALACAO', 'Caetité', 'BA', -13.8357, -42.2813,
 'https://www.inb.gov.br/A-INB/Onde-estamos/Caetite', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Agricultura'),
 'Moscamed: técnica do inseto estéril',
 'Na biofábrica da Moscamed, em Juazeiro, insetos são criados em grande número, esterilizados com radiação e soltos no campo. Os machos estéreis cruzam com fêmeas selvagens, não nascem descendentes e a população da praga diminui. A técnica protege os pomares do Vale do São Francisco da mosca-do-mediterrâneo.',
 'Inaugurada em 2006, a Moscamed foi a primeira biofábrica do Brasil dedicada a insetos estéreis e a primeira do mundo escolhida para esterilizar machos do mosquito Aedes aegypti com raios X.',
 'INSTALACAO', 'Juazeiro', 'BA', -9.4589, -40.4883,
 'https://moscamed.org.br/moscamed.html', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Agricultura'),
 'CENA/USP: técnicas nucleares na agricultura',
 'O Centro de Energia Nuclear na Agricultura (CENA), da USP, foi criado em 1966 em Piracicaba para aplicar técnicas nucleares na agropecuária e no estudo do ambiente. Seus pesquisadores produzem compostos marcados com isótopos, que funcionam como etiquetas para seguir nutrientes e outras substâncias no solo, nas plantas e nos animais.',
 'Entre 1968 e 1995, o curso CIENA, do próprio centro, formou cerca de 220 técnicos em técnicas nucleares aplicadas à pesquisa e ao melhoramento de produtos agrícolas.',
 'INSTALACAO', 'Piracicaba', 'SP', -22.7066, -47.6452,
 'https://www.cena.usp.br/home/institucional', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Pesquisa Científica'),
 'Reator de pesquisa TRIGA do CDTN',
 'No campus da UFMG, em Belo Horizonte, o CDTN mantém o reator TRIGA IPR-R1, comprado pelo governo de Minas Gerais em 1960. O nome vem de Training, Research, Isotopes, General Atomic, porque ele serve a ensino, pesquisa e produção de radioisótopos. Hoje irradia amostras para análise por ativação neutrônica.',
 'O curso de operadores de reatores de pesquisa do CDTN foi pioneiro na formação dos operadores das usinas nucleares de Angra.',
 'INSTALACAO', 'Belo Horizonte', 'MG', -19.8734, -43.9678,
 'https://www.gov.br/cdtn/pt-br/laboratorios/reator-triga-ipr-r1', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Pesquisa Científica'),
 'Areias monazíticas de Meaípe',
 'As areias da praia de Meaípe, em Guarapari, são monazíticas e contêm tório, um elemento radioativo natural. Uma pesquisa da USP com a UFES mediu a radiação gama na praia por cerca de dois anos e mostrou que ela é mais intensa no primeiro 1,25 m acima da areia e diminui com a altura.',
 'Na mesma pesquisa, a radiação gama subia junto com a temperatura do solo e do ar, e a chuva tinha uma relação pequena e inversa com ela.',
 'NATURAL', 'Guarapari', 'ES', -20.7396, -40.5470,
 'https://teses.usp.br/teses/disponiveis/14/14133/tde-29042022-165703/', CURRENT_TIMESTAMP);

INSERT INTO PONTO_MAPA (id_area, nm_titulo, ds_resumo, ds_curiosidade, tp_ponto, nm_cidade, sg_uf, nr_latitude, nr_longitude, ds_url_fonte, dt_cadastro) VALUES
((SELECT id_area FROM AREA_APLICACAO WHERE nm_area = 'Saúde'),
 'Acidente com césio-137 (1987)',
 'Em 13 de setembro de 1987, um aparelho de radioterapia que o Instituto Goiano de Radioterapia tinha deixado abandonado foi removido e desmontado em Goiânia, e a cápsula de césio-137 foi rompida. No total, 112.800 pessoas foram monitoradas, 249 tiveram contaminação significativa e quatro morreram.',
 'Os rejeitos, 3.500 m³ de lixo radioativo em contêineres concretados, foram levados para Abadia de Goiás, a 23 km de Goiânia, onde a CNEN mantém o Centro Regional de Ciências Nucleares do Centro-Oeste.',
 'HISTORICO', 'Goiânia', 'GO', -16.6689, -49.2641,
 'https://goias.gov.br/saude/historia-do-cesio-137-em-goiania/', CURRENT_TIMESTAMP);
