
# 📊 Matriz de Rastreabilidade de Requisitos

---

## 📚 Livros

### 🔗 Requisitos

| ID  | Requisito Funcional | Descrição                     | Casos de Teste |
|-----|---------------------|-----------------------------|----------------|
| R1  | Criar livro         | Permitir cadastrar um livro | CT01, CT02     |
| R2  | Listar livros       | Retornar todos os livros    | CT03           |
| R3  | Buscar livro        | Retornar livro por ID       | CT14, CT15     |
| R4  | Atualizar livro     | Editar dados do livro       | CT04, CT05     |
| R5  | Deletar livro       | Remover livro do sistema    | CT06, CT07     |

---

### 🧪 Casos de Teste - Livros

- CT01 (shouldCreateBook): Deve cadastrar livro com dados válidos
- CT02 (bookRequestDtoValidation): Deve recusar cadastrar livro com dados inválidos
- CT03 (shouldListBooks): Deve listar livros cadastrados
- CT04 (shouldUpdateBook): Deve atualizar um livro existente
- CT05 (bookRequestDtoValidation): Deve recusar atualizar um livro inexistente
- CT06 (shouldDeleteBook): Deve deletar um livro existente
- CT07 (bookRequestDtoValidation): Deve recusar deletar um livro inexistente
- CT14 (shouldGetBookById): Deve buscar livro por id
- CT15 (bookRequestDtoValidation): Deve recusar busca de livro por id inválido

---

## 👤 Usuários

### 🔗 Requisitos

| ID  | Requisito Funcional | Descrição                     | Casos de Teste |
|-----|---------------------|-----------------------------|----------------|
| R6  | Criar usuário       | Criar novo usuário          | CT08, CT09     |
| R7  | Listar usuários     | Retornar todos os usuários  | CT18           |
| R8  | Buscar usuário      | Retornar usuário por ID     | CT16, CT17     |
| R9  | Atualizar usuário   | Editar dados do usuário     | CT10, CT11     |
| R10 | Deletar usuário     | Remover usuário do sistema  | CT12, CT13     |

---

### 🧪 Casos de Teste - Usuários

- CT08 (shouldRegisterUser): Deve cadastrar usuário válido
- CT09 (UserRequestDtoTest): Deve recusar cadastrar usuário com dados inválidos
- CT10 (shouldUpdateUser): Deve atualizar um usuário existente
- CT11 (UserRequestDtoTest): Deve recusar atualizar usuário inexistente
- CT12 (shouldDeleteUser): Deve deletar um usuário existente
- CT13 (UserRequestDtoTest): Deve recusar deletar usuário inexistente
- CT16 (shouldGetUserById): Deve buscar usuário por id
- CT17 (UserRequestDtoTest): Deve recusar busca de usuário por id inválido
- CT18 (shouldListUsers): Deve listar usuários cadastrados

O I/O Bean Validation realiza a verificação de dados antes que chegem ao service. As classes que testam as entradas de dado são os DtoTest.
