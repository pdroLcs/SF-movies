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
- **Deploy:** Docker Hub + Render.
- **URL pública (produção):** `https://sf-movies-xbea.onrender.com`.
- **Porta local padrão:** `8080`.

> Observação: os dados são externos e carregados sob demanda; a disponibilidade e o tempo de resposta dependem também da API pública de San Francisco.

---

## Endpoints

As rotas abaixo funcionam tanto localmente quanto em produção.

### Base URLs

- **Produção:** `https://sf-movies-xbea.onrender.com/movies`
- **Local:** `http://localhost:8080`

### 1) Listar filmes (com filtro opcional por título)

```http
GET /movies
```

#### Query params

- `t` (opcional): termo para filtrar por título (case-insensitive, busca por “contém”).

#### Exemplos

Produção (todos os filmes):

```bash
curl "https://sf-movies-xbea.onrender.com/movies"
```

Produção (filtrando por título):

```bash
curl "https://sf-movies-xbea.onrender.com/movies?t=matrix"
```

Local (todos os filmes):

```bash
curl "http://localhost:8080/movies"
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

#### Exemplos

Produção:

```bash
curl "https://sf-movies-xbea.onrender.com/movies/autocomplete?q=sta"
```

Local:

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

## Como rodar o projeto localmente

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

```bash
curl "http://localhost:8080/movies"
```

---

## Build

Executar build:

```bash
./mvnw clean package
```

---

## Configurações

Arquivo de configuração principal:

- `src/main/resources/application.properties`

---

## Pontos importantes e limitações atuais

- A API consulta o dataset externo a cada requisição (não há cache local).
- Se a fonte externa estiver indisponível, as rotas podem falhar ou ficar lentas.
- O filtro de título (`t`) usa correspondência parcial (`contains`) e não pagina o resultado.
- O autocomplete depende dos títulos retornados pela base externa no momento da chamada.
- Em produção (Render), pode haver latência inicial em acessos após inatividade (cold start).

---

## Licença

MIT
