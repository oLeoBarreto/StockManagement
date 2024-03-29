
# Stock Management

Projeto spring boot para gerenciamento de estoque, que esta sendo desenvolvido com o intuito educacional de conceitos logisticos, além de melhorar o conhecimento de desenvolvimento Java, aplicando conhecimentos como SOLID e Clean architecture.

## Contribuir

### O que devo ter em mente antes de abrir um PullRequest?
- Esse é um projeto opensource onde toda a comunidade e contribuições são bem vindas.
- Para aumentar a chance de a sua contribuição ser aprovada, escolha um título legal, simples e explicativo para os itens, e siga a formatação correta dos arquivos.
- Quer contribuir com o projeto? [Confira o passo a passo](./CONTRIBUTING.md)

## Variáveis de Ambiente

Para rodar esse projeto, é necessário indicar um secret para os tokens JWT na variável:

`JWT_SECRET`

Também é possível informar a porta que a aplicação irá rodar através da variável:

`PORT`

Para definir o caminho que irá armazenar as imagens basta informar a variável:

`UPLOAD_DIR`

## Documentação da API

### Autenticação

- Rotas para autenticação na aplicação, sendo possivel registrar um novo usuario e logar na aplicação, resultando em um token que deverá ser usado em todos os request.

##

```http
  POST /auth/register
```

| Corpo   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `login` | `string` | **Obrigatório**. E-mail para login na aplicação.  |
| `password` | `string` | **Obrigatório**. Senha para login na aplicação. |
| `role` | `string` | Tipo de usuário. *Admin* ou *User* |

##
```http
  POST /auth/login
```

| Corpo   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `login` | `string` | **Obrigatório**. E-mail para login na aplicação.  |
| `password` | `string` | **Obrigatório**. Senha para login na aplicação. |

### Produtos

- Rotas para criação, alteração e deleção de produtos na aplicação.

##

```http
  GET /products
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `size`      | `integer` | Tamanho de itens na pagina. |
| `page`      | `integer` | Número da pagina. |

##
```http
  GET /products/findById
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório** Id do produto. |

##
```http
  GET /products/findBySupplier
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `supplier`      | `string` | **Obrigatório** fornecedor do produto. |

##
```http
  GET /products/findByCategory
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `category`      | `string` | **Obrigatório** categoria do produto. |

##
```http
  POST /products
```

| Corpo         | Tipo       | Descrição                                   |
| :----------   | :--------- | :------------------------------------------ |
| `name`        | `string` | **Obrigatório** nome do produto. |
| `description` | `string` | **Obrigatório** Descrição do produto. |
| `unitPrice`   | `number` | **Obrigatório** preço unitário do produto. |
| `supplier`    | `string` | **Obrigatório** fornecedor do produto. |
| `category`    | `string` | **Obrigatório** categoria do produto.  |

##
```http
  PUT /products
```

| Corpo         | Tipo       | Descrição                                   |
| :----------   | :--------- | :------------------------------------------ |
| `id`          | `string` | **Obrigatório** Id do produto. |
| `name`        | `string` | **Obrigatório** nome do produto. |
| `description` | `string` | **Obrigatório** Descrição do produto. |
| `unitPrice`   | `number` | **Obrigatório** preço unitário do produto. |
| `supplier`    | `string` | **Obrigatório** fornecedor do produto. |
| `category`    | `string` | **Obrigatório** categoria do produto.  |

##
```http
  DEL /products
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório** Id do produto. |

##
#### Imagem do Produto
```http
  GET /products/image/download
```

| Parâmetro   | Tipo       | Descrição                      |
| :---------- | :--------- |:-------------------------------|
| `productId`      | `string` | **Obrigatório** ID do produto. |

##
```http
  POST /products/image/upload
```

| Parâmetro   | Tipo        | Descrição                                     |
| :---------- |:------------|:----------------------------------------------|
| `productId`      | `string`    | **Obrigatório** ID do produto.                |
| `imageFile`      | `multipart` | **Obrigatório** Arquivo de imagem do produto. |
**Atenção:** apenas arquivos .jpeg  e .png são aceitos. 

##
```http
  DEL /products/image
```

| Parâmetro   | Tipo       | Descrição                      |
| :---------- | :--------- |:-------------------------------|
| `productId`      | `string` | **Obrigatório** ID do produto. |


### Documentos de entrada

- Rotas para criação de documentos de entrada, que posibilitaram a entrada de produtos no estoque.

##

```http
  GET /inbounds
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `size`      | `integer` | Tamanho de itens na pagina. |
| `page`      | `integer` | Número da pagina. |

##
```http
  GET /inbounds/findById
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório** Id do produto. |

##
```http
  POST /inbounds
```

| Corpo         | Tipo       | Descrição                                   |
| :----------   | :--------- | :------------------------------------------ |
| `quantity`    | `integer`  | **Obrigatório** Quantidade de entrada do produto. |
| `productId`   | `string`   | **Obrigatório** Id do produto. |

##
```http
  DEL /inbounds
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório** Id do documento de entrada. |

### Documentos de saída

- Rotas para criação de documentos de saída, que posibilitaram a saída de produtos no estoque.

##

```http
  GET /outbounds
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `size`      | `integer` | Tamanho de itens na pagina. |
| `page`      | `integer` | Número da pagina. |

##
```http
  GET /outbounds/findById
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório** Id do produto. |

##
```http
  POST /outbounds
```

| Corpo         | Tipo       | Descrição                                   |
| :----------   | :--------- | :------------------------------------------ |
| `quantity`    | `integer`  | **Obrigatório** Quantidade de saída do produto. |
| `productId`   | `string`   | **Obrigatório** Id do produto. |

##
```http
  DEL /inbooutboundsunds
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório** Id do documento de saída. |

## Autores

- [@oLeoBarreto](https://github.com/oLeoBarreto)

