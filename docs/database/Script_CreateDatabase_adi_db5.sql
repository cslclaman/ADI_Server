drop database if exists adi_db5;
create database adi_db5;
use adi_db5;

/* 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
TABELAS
*/

/*
Registro de Imagens.
Representa o arquivo e seus metadados.
*/
create table Imagem (
	 id 				int 			not null	auto_increment
    ,md5 				varchar(40)		not null	/* MD5 */
    ,sha1				varchar(70)		not null	/* SHA-1 */
    ,tag_string 		varchar(8192)	not null	/* String com tags ADI */
    ,classif_etaria		varchar(16) 	not null	/* Classificação etária */
    ,ativa				bit(1)			not null	/* Imagem ativa (no ADI) */
    ,caminho_arquivo	varchar(256)	not null	/* Caminho relativo do arquivo da imagem */
    ,tamanho_arquivo	bigint			not null	/* Tamanho do arquivo armazenado (bytes) */
    ,criada_em			datetime		not null	/* Data de criação do registro */
    ,atualizada_em 		datetime		null		/* Última atualização do registro - NULL caso não tenha sido atualizado ainda*/
    ,arquivo_original	varchar(256)	null		/* Caminho e nome do arquivo original - NULL caso não tenha sido registrado (legado) */
    
    ,constraint PK_Imagem primary key (id)
);

/*
Registro de postagens (ex: Post do Danbooru, gelbooru, Sankaku Complex, Konachan, Yandere).
Compreende apenas imagens provindas de imageboards (Twitter/Facebook não contam).
*/
create table Origem_Imagem (
	 id 				int 			not null	auto_increment
    ,imagem				int				not null	/* ID da Imagem a que se refere */
    ,origem				int				not null	/* ID da Origem */
    ,origem_primaria	bit(1)			not null	/* Se essa origem é a origem primária da imagem */
    ,ident_origem		varchar(96)		not null	/* ID do post (ex: 2824951 ou 2017-06-17-910380) - vazio caso não haja ID*/
	,url_post			varchar(1024)	null	 	/* URL do post da imagem - NULL caso não esteja disponível (ex.: (x)io )*/
    ,url_arquivo		varchar(1024)	null		/* URL do arquivo de imagem - NULL caso não esteja disponível */
    ,upload_em			datetime		null		/* Data de envio na origem - NULL caso não esteja disponível */
    ,md5				varchar(40)		null		/* MD5 da imagem - NULL caso não esteja disponível */ 
    ,tamanho_arquivo	bigint			null		/* Tamanho do arquivo na origem - 0 caso não esteja disponível */ 
    ,tag_string			varchar(8192)	null		/* String com tags (pode acabar truncada). */ 
    ,origem_inativa		bit(1)			not null 	/* (x)io - Endereço de origem não existe mais ou está desativado */
    ,imagem_deletada	bit(1)			not null	/* (x)n  - Imagem foi deletada */
    ,imagem_censurada	bit(1)			not null	/* (x)g  - Imagem censurada, ou acessível apenas para contas premium */
    ,imagem_bannida		bit(1)			not null	/* (x)a  - Imagem banida, por solicitação de remoção */
    ,classif_etaria		varchar(16)		null		/* Classificação etária na origem */
    
    ,constraint PK_OrigemImagem primary key (id)
);

/*
Registro de Origem (Danbooru, Gelbooru, etc).
Registra o nome, sigla e informações de URLs básicas para acesso à API (se houver)
*/
create table Origem (
	id				int				not null	auto_increment
    ,sigla			varchar(2)		not null	/* Sigla da origem */
    ,nome			varchar(128) 	not null	/* Nome da origem */
    ,tipo			varchar(64)		not null	/* Tipo (danbooru, danbooru2, gelbooru, shimmie, etc) */
    ,ativa			bit(1)			not null	/* Se o site está ativo e a API aceita chamadas */
    ,url			varchar(1024)	not null	/* URL para a Homepage do sistema (ex.: http://danbooru.donmai.us ), caso ativo */
    ,url_base_api	varchar(1024)	null		/* URL Base para as chamadas da API. Se for NULL, tenta pela URL da Homepage */
    ,formato_data	varchar(128)	null		/* Formato usado para representar alguma data (de acordo com padrão Java) */
    ,locale			varchar(16)		null		/* Local que deve ser usado como base para tradução de dados como data */
    
    ,constraint PK_Origem primary key (id)
);

/*
Registro de URLs da origem
Registra função de URLs (relacionados à APIs) de sites de origem
*/
create table Url (
	id				int				not null	auto_increment
    ,origem			int				not null	/* ID da origem relacionada */
    ,descricao		varchar(128)	not null	/* "Chave" de identificação. Exemplo: posts_json pode equivaler ao URL para acessar a API que retorna Posts em JSON. */
    ,url			varchar(1024)	not null	/* Endereço em questão */
    ,usa_url_base	bit(1)			not null	/* Indica se essa URL é um complemento à URL base (da Origem) ou se é completa */
    
    ,constraint PK_Url primary key (id)
);

/* 
Tags ADI (V5+), tipos C, P, I, A
Registra nome da tag (igual ao que será incluído no arquivo) e tipo. A combinação tipo-tag deve ser única.
 */
create table Adi_Tag (
	 id				int				not null	auto_increment
    ,tipo			varchar(5)		not null	/* Tipo (c, p, i ou a) */
    ,tag			varchar(256)	not null	/* Nome da tag (abreviado) */
    ,arquivo		bit(1)			not null	/* Se é obrigatória no nome do arquivo ou não. Apenas para tags i */
    
    ,constraint PK_AdiTag primary key (id)
);

/*
Tabela associativa Image <-> Adi_tag
*/
create table Imagem_Tags (
	 imagem			int				not null	
    ,adi_tag		int				not null
    
    ,constraint PK_ImagemTags primary key (imagem, adi_tag)
);

/*
Tags provindas de imageboards. Identificam variedades de coisas, como artista, personagem, itens, copyright, metadados, etc.
O foco de armazenamento aqui é para associação automática entre tags e as AdiTags.
*/
create table Tag (
	 id				int				not null	auto_increment
    ,tag			varchar(512)	not null	/* Nome da tag, conforme indicado no imageboard de origem */
    ,url			varchar(512)	null		/* URL da tag (ou nome sem caracteres especiais) */
    ,tipo			varchar(64)		not null	/* Tipo da tag (copyright, character, general, meta,  */
    ,adi_tag		int				null		/* Adi_Tag mapeada para essa tag (ou NULL se não deve ser mapeada) */
    
    ,constraint PK_Tag primary key (id)
);

/* Tabela associativa Tag <-> Image_Source */
create table Origem_Tags (
	 origem_imagem	int				not null
	,tag			int				not null
    
    ,constraint PK_OrigemTags primary key (origem_imagem, tag)
);

/*
Especialização da Info para tags do tipo (c) - Séries
Inclui campo de nome alternativo.
*/
create table Serie (
	id					int				not null	auto_increment
    ,adi_tag			int				not null
    ,nome				varchar(512)	not null
    ,nome_alternativo	varchar(512)	null		/* Nome alternativo da série/copyright, se houver */
    ,comentario			varchar(4096)	null
    
    ,constraint PK_Serie primary key(id)
);

/*
Especialização da Info para tags do tipo (p) - Personagem
Inclui campos de associação para a série e artista criadores do personagem
*/
create table Personagem (
	id				int				not null	auto_increment
    ,adi_tag		int				not null
    ,nome			varchar(512)	not null
    ,comentario		varchar(4096)	null
    ,serie			int				not null	/* Série do personagem. Pode ser original. */
    ,artista		int				null		/* Artista criador do personagem, caso original */
    
    ,constraint PK_Personagem primary key(id)
);

/*
Especialização da Info para tags do tipo (a) - Artista
Inclui campo de informação para artista (informa se ele está vivo, saudável e não foi banido)
*/
create table Artista (
	id				int				not null	auto_increment
    ,adi_tag		int				not null
    ,nome			varchar(512)	not null
    ,comentario		varchar(4096)	null
    ,ativo			bit(1)			not null	/* Artista ativo/não-banido */ 
    
    ,constraint PK_Artista primary key(id)
);


/*
Especialização da Info para tags do tipo (i) - Item
Inclui campo de informação sobre categoria do item (leia sobre alcance na documentação oficial).
*/
create table Item (
	id				int				not null	auto_increment
    ,adi_tag		int				not null
    ,nome			varchar(512)	not null
    ,comentario		varchar(4096)	null
    ,alcance		int				not null	/* Categoria do item (0 - Personagem, 1 - Ações, 2 - Cenário, 3 - Meta ) */
    
    ,constraint PK_Item primary key(id)
);

/* 
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
CONSTRAINTS 
*/

alter table Imagem
add constraint UN_Imagem_MD5 unique (md5);

alter table Origem
add constraint UN_Origem_Sigla unique (sigla);

alter table Adi_Tag
add constraint UN_AdiTag_Tipo_Tag unique (tipo, tag);

alter table Tag
add constraint UN_Tag_Tag unique (tag);

/*
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
FOREIGN KEYS
*/

alter table Origem_Imagem
add constraint FK_OrigemImagem_Imagem foreign key (imagem) references Imagem (id),
add constraint FK_OrigemImagem_Origem foreign key (origem) references Origem (id);

alter table Url
add constraint FK_Url_Origem foreign key (origem) references Origem (id);

alter table Tag
add constraint FK_Tag_AdiTag foreign key (adi_tag) references Adi_Tag (id);

alter table Imagem_Tags
add constraint FK_ImagemTags_Imagem foreign key (imagem) references Imagem (id),
add constraint FK_ImagemTags_AdiTag foreign key (adi_tag) references Adi_Tag (id);

alter table Origem_Tags
add constraint FK_OrigemTags_Origem foreign key (origem_imagem) references Origem_Imagem (id),
add constraint FK_OrigemTags_Tag foreign key (tag) references Tag (id);

alter table Serie
add constraint FK_Serie_AdiTag foreign key (adi_tag) references Adi_Tag (id);

alter table Personagem
add constraint FK_Personagem_AdiTag foreign key (adi_tag) references Adi_Tag (id),
add constraint FK_Personagem_Serie foreign key (serie) references Serie (id),
add constraint FK_Personagem_Artista foreign key (artista) references Artista (id);

alter table Artista
add constraint FK_Artista_AdiTag foreign key (adi_tag) references Adi_Tag (id);

alter table Item
add constraint FK_Item_AdiTag foreign key (adi_tag) references Adi_Tag (id);



