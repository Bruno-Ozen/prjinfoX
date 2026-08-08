# X System (prjinfoX)

Aplicação desktop em Java para **gerenciamento de clientes** e **emissão de Ordens de Serviço (OS)**, baseada na playlist de desenvolvimento do sistema InfoX disponível em:

> Playlist original do projeto (curso em vídeo):  
> [https://www.youtube.com/watch?v=rB66EC0VXTA&list=PLbEOwbQR9lqxsTusvu8wfkUECrmcV81MU](https://www.youtube.com/watch?v=rB66EC0VXTA&list=PLbEOwbQR9lqxsTusvu8wfkUECrmcV81MU)

Este repositório adapta o projeto original para usar **SQLite** como banco de dados embarcado, facilitando a execução em qualquer máquina sem precisar instalar MySQL.

---

## Tecnologias e competências utilizadas

- **Java (Swing)**
  - Construção de interface gráfica desktop (JFrame, JInternalFrame, JDesktopPane, JTable, etc.);
  - Organização em camadas (telas em `br.com.infox.telas`, conexão em `br.com.infox.dal`);
  - Tratamento de eventos (ActionListener), controle de perfis de usuário, validação de campos.

- **SQL / Banco de dados**
  - Uso de **SQLite** com JDBC (`sqlite-jdbc`);
  - Criação de tabelas (`tbclientes`, `tbusuarios`, `tbos`) e relacionamentos (FK `idcli` em `tbos`);
  - Comandos de CRUD: `INSERT`, `SELECT`, `UPDATE`, `DELETE`;
  - Scripts de criação e povoamento automático de dados via classe `CriarBanco`.

- **Relatórios com JasperReports**
  - Uso das bibliotecas `jasperreports` para geração de relatórios;
  - Geração de relatórios de **Clientes** e **Serviços** a partir do banco de dados;
  - Exibição dos relatórios com `JasperViewer`.

---

## Pré-requisitos

1. **Java instalado (JDK 21 ou 22+ / Java 26 quando disponível)**
   - Recomenda-se instalar a versão LTS mais recente (ex.: JDK 21) ou superior.
   - Verifique a instalação com:

   ```bash
   java -version
   ```

2. **Sistema operacional suportado**
   - Windows, Linux ou macOS (aplicação Java desktop multiplataforma).

3. **Nenhuma instalação de banco de dados é necessária**
   - O projeto usa **SQLite embarcado**. O arquivo do banco (`database/dbinfox.db`) é criado e populado automaticamente pela classe `CriarBanco`.

---

## Estrutura do projeto

Principais pastas e arquivos:

```text
prjinfoX/
├─ build/                 # Saída de build do NetBeans (contém o .jar executável)
├─ database/
│  ├─ dbinfox.db         # Banco de dados SQLite (gerado em tempo de execução)
│  └─ script.sql         # Opcional: script SQL de referência
├─ lib/
│  ├─ sqlite-jdbc-*.jar  # Driver JDBC do SQLite
│  ├─ rs2xml.jar         # Biblioteca para popular JTable a partir de ResultSet
│  └─ jasperreports-*.jar# Bibliotecas JasperReports e dependências
├─ src/
│  └─ br/com/infox/
│     ├─ dal/            # Camada de acesso a dados (ModuloConexao, CriarBanco)
│     └─ telas/          # Telas Swing (TelaLogin, TelaPrincipal, TelaOS, etc.)
└─ README.md
```

---

## Como executar o projeto (via JAR na pasta build)

1. **Clonar o repositório**

   ```bash
   git clone https://github.com/SEU_USUARIO/prjinfoX.git
   cd prjinfoX
   ```

2. **(Opcional, primeira vez) Gerar/atualizar o banco de dados**

   Se estiver rodando a partir da IDE (NetBeans), a classe `CriarBanco` pode ser executada diretamente:

   - No NetBeans: clique com o botão direito em `CriarBanco.java` → **Run File**.
   - Isso irá:
     - Criar as tabelas `tbclientes`, `tbusuarios`, `tbos` no arquivo `database/dbinfox.db`.
     - Inserir registros de teste (clientes, usuários e ordens de serviço).

   Quando rodar a aplicação pelo JAR, o código de inicialização também pode chamar `CriarBanco` automaticamente (dependendo de como o `main` foi configurado).

3. **Executar o JAR compilado (pasta build)**

   Depois de gerar o build pela IDE (ou usar o build já pronto no repositório), haverá um JAR na pasta `build/` (por exemplo, `prjinfoX.jar`).

   No terminal, a partir da raiz do projeto:

   ```bash
   cd build
   java -jar prjinfoX.jar
   ```

   Se o Java estiver corretamente instalado, a tela de **login** será aberta.

4. **Login padrão**

   Usuários criados automaticamente pela classe `CriarBanco`:

   - Administrador:
     - Usuário: `root`
     - Senha: `root`
     - Perfil: `admin` (habilita Relatórios e Cadastro de Usuários)

   - Usuários comuns de teste (perfil `user`), por exemplo:
     - `maria` / `123456`
     - `joao` / `123456`
     - `ana` / `123456`
     - (há outros usuários fictícios pré-cadastrados)

---

## Funcionalidades principais

- **Tela de Login**
  - Autenticação de usuários via tabela `tbusuarios`.
  - Tratamento de perfis (`admin` / `user`) para habilitar ou bloquear menus.

- **Tela Principal (`TelaPrincipal`)**
  - Menu de **Cadastro**:
    - Clientes
    - Ordens de Serviço (OS)
    - Usuários (somente perfil admin)
  - Menu de **Relatórios** (habilitado para admin):
    - Relatório de Clientes
    - Relatório de Serviços
  - Menu de **Ajuda → Sobre**: janela com descrição da aplicação.
  - Menu de **Opções → Sair**.

- **Cadastro de Clientes (`tbclientes`)**
  - Inclusão, edição, exclusão e pesquisa de clientes.
  - Exibição em tabelas (`JTable` + `rs2xml.jar`).

- **Usuários (`tbusuarios`)**
  - Cadastro de usuários do sistema, com campos:
    - `usuario` (apelido/login amigável)
    - `nome` (nome completo)
    - `fone`
    - `login`
    - `senha`
    - `perfil` (`admin` ou `user`)

- **Ordens de Serviço (`tbos`)**
  - Emissão de OS (tipo: OS ou Orçamento);
  - Situações: Aberta, Em andamento, Fechada, Aguardando aprovação, etc;
  - Associação da OS a um cliente via `idcli`;
  - Edição, cancelamento e impressão da OS.

- **Relatórios (JasperReports)**
  - Relatório de clientes: `clientes.jasper`;
  - Relatório de serviços: `servicos.jasper`;
  - Uso de `JasperFillManager.fillReport` e `JasperViewer.viewReport`.

  > Observação: os arquivos `.jasper` devem estar no caminho configurado no código (por exemplo, `C:/reports/clientes.jasper`). Adapte o caminho conforme o seu ambiente.

---

## Resetar o banco de dados

Se você quiser **resetar** completamente os dados para o estado inicial:

1. Feche a aplicação.
2. Exclua o arquivo de banco:

   ```text
   prjinfoX/database/dbinfox.db
   ```

3. Rode novamente a classe `CriarBanco` (via IDE) ou novamente o JAR se ele já estiver configurado para chamar `CriarBanco` na inicialização.

Um novo arquivo `dbinfox.db` será criado com as tabelas e registros de exemplo.

---

## Créditos e referência didática

Este projeto é baseado na série de aulas do canal **Curso em Vídeo**, adaptado para uso com banco de dados SQLite, mantendo a estrutura geral do sistema de OS (InfoX):

- Playlist original:  
  [https://www.youtube.com/watch?v=rB66EC0VXTA&list=PLbEOwbQR9lqxsTusvu8wfkUECrmcV81MU](https://www.youtube.com/watch?v=rB66EC0VXTA&list=PLbEOwbQR9lqxsTusvu8wfkUECrmcV81MU)

Adaptações realizadas neste repositório:

- Migração de MySQL para **SQLite** embarcado;
- Script de criação e povoamento automático de banco via classe `CriarBanco`;
- Ajustes de conexão (`ModuloConexao`) e compatibilidade com a nova estrutura de banco;
- Inclusão de múltiplos registros de teste (clientes, usuários, ordens de serviço) para facilitar demonstrações e uso acadêmico.
