# ADI - Anime Downloaded Images

## O que é:
Sistema de categorização de imagens de animes baixadas de imageboards como Danbooru.

## Requisitos (ordem de prioridade):
* O sistema deve permitir pesquisa por tags (sejam elas da origem ou tags ADI) e retornar dados de imagens correspondentes com agilidade (menos que 2 segundos). Deve também cadastrar imagens, tags e informações relativas, bem como organizar os arquivos de imagem por suas séries e classificação etária.
	_Implementação: Módulo de API que retorna JSON/XML e interage com banco de dados e sistema de arquivos._
* O sistema deve permitir a exibição de imagens/vídeos e busca interativa de tags, independente dos outros sistemas (não precisa acessar a base de dados diretamente, pode usar a API).
	_Implementação: Sistema WEB que consome módulo de API._
  
Esse projeto prevê ainda um cliente instalado no computador do usuário que mapeia pastas e, ao encontrar novas imagens baixadas, automaticamente (via API) envia o arquivo para ser cadastrado por este módulo.

## O que ele tem a ver com o [ADI-API](https://github.com/cslclaman/adi-api) ou com o [ADI-Client](https://github.com/cslclaman/adi-client)?
Trata-se do mesmo projeto com o mesmo conceito e as mesmas funcionalidades e requisitos. Porém, agora as linguagens e ferramentas usadas nesse projeto são idênticas às que eu estou usando em meu estágio, para fins de aprendizado e aperfeiçoamento (exceto o banco de dados, que é MySQL por questão de gosto pessoal). Além disso, as funcionalidades foram remapeadas (por exemplo, o módilo cliente não é mais responsável por armazenar os arquivos).
 
## Dependências (deste módulo):
* Java 8
* Spring Boot
* Gradle
* Angular 6 (em breve!)
