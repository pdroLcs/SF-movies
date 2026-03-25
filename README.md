# SF Movies API

API REST em **Java 21 + Spring Boot** para consultar filmes gravados em São Francisco com base no dataset público da cidade de San Francisco.

A aplicação consome os dados diretamente do endpoint aberto da prefeitura e expõe rotas simples para:

- listar filmes;
- filtrar por título;
- sugerir títulos por autocomplete.

---

## Visão geral do projeto

- **Stack:** Spring Boot 3, Spring Web, Spring WebFlux, Lombok e Maven.
- **Fonte de dados:** `https://data.sfgov.org/resource/yitu-d5am.json`.
- **Formato de resposta:** JSON.
- **Porta padrão:** `8080` (padrão do Spring Boot, caso não configurado diferente).

> Observação: os dados são externos e carregados sob demanda; a disponibilidade e o tempo de resposta dependem também da API pública de San Francisco.

---

## Endpoints

Base URL local:

```text
http://localhost:8080
```

### 1) Listar filmes (com filtro opcional por título)

```http
GET /movies
```

#### Query params

- `t` (opcional): termo para filtrar por título (case-insensitive, busca por “contém”).

#### Exemplos

Listar todos:

```bash
curl "http://localhost:8080/movies"
```

Filtrar por título:

```bash
curl "http://localhost:8080/movies?t=matrix"
```

---

### 2) Autocomplete de títulos

```http
GET /movies/autocomplete
```

#### Query params

- `q` (obrigatório): prefixo do título.

#### Regras

- busca case-insensitive;
- remove duplicados;
- ordena alfabeticamente;
- limita a 10 sugestões.

#### Exemplo

```bash
curl "http://localhost:8080/movies/autocomplete?q=sta"
```

---

## Estrutura de resposta

Cada item de filme possui os campos abaixo (conforme o modelo atual da API):

- `title`
- `release_year`
- `locations`
- `actor_1`
- `actor_2`
- `actor_3`

Exemplo de objeto:

```json
{
  "title": "Vertigo",
  "release_year": "1958",
  "locations": "Golden Gate Bridge",
  "actor_1": "James Stewart",
  "actor_2": "Kim Novak",
  "actor_3": null
}
```

---

## Como rodar o projeto

### Pré-requisitos

- Java 21+
- Maven 3.9+ (ou usar o Maven Wrapper já incluído)

### 1) Clonar o repositório

```bash
git clone <url-do-repo>
cd SF-movies
```

### 2) Rodar em modo desenvolvimento

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

### 3) Validar que subiu

Abra no navegador ou use curl:

```bash
curl "http://localhost:8080/movies"
```

---

## Build e testes

Executar build:

```bash
./mvnw clean package
```

Executar testes:

```bash
./mvnw test
```

---

## Configurações

Arquivo de configuração principal:

- `src/main/resources/application.properties`

No estado atual, apenas o nome da aplicação está definido:

```properties
spring.application.name=sf-movies
```

Se quiser personalizar porta, logs, timeout ou outras opções, esse é o local recomendado.

---

## Pontos importantes e limitações atuais

- A API consulta o dataset externo a cada requisição (não há cache local).
- Se a fonte externa estiver indisponível, as rotas podem falhar ou ficar lentas.
- O filtro de título (`t`) usa correspondência parcial (`contains`) e não pagina o resultado.
- O autocomplete depende dos títulos retornados pela base externa no momento da chamada.

---

## Licença

MIT