# X System (prjinfoX)

Projeto de **engenharia reversa e readaptação** de um sistema legado de Ordens de Serviço (InfoX), originalmente desenvolvido com **MySQL**, para um ambiente mais portátil usando **SQLite embarcado** e empacotado em um **JAR executável**.

Neste repositório eu:

- Migrei o backend de **MySQL para SQLite**, removendo a necessidade de instalar servidor de banco de dados.
- Reescrevi a camada de conexão (`ModuloConexao`) para funcionar tanto na IDE quanto na versão compilada (`dist/prjinfoX.jar`), resolvendo problemas de caminho relativo/absoluto.
- Criei um módulo de **inicialização automática do banco** (`CriarBanco`), que gera o schema completo e popula com dados realistas (clientes, usuários e ordens de serviço).
- Ajustei o projeto Ant/NetBeans (build com `dist/`, `lib/`, `database/`) para permitir distribuição simples: baixar, ter Java instalado e rodar o `.jar`.
- Integrei manualmente bibliotecas externas (`sqlite-jdbc`, `rs2xml`, `jasperreports`, `commons-logging`), resolvendo conflitos de classpath e compatibilidade com versões recentes do Java.

Aplicação desktop em Java para **gerenciamento de clientes** e **emissão de Ordens de Serviço (OS)**, baseada na playlist de desenvolvimento do sistema InfoX disponível em:

> Playlist original do projeto (curso em vídeo):  
> [https://www.youtube.com/watch?v=rB66EC0VXTA&list=PLbEOwbQR9lqxsTusvu8wfkUECrmcV81MU](https://www.youtube.com/watch?v=rB66EC0VXTA&list=PLbEOwbQR9lqxsTusvu8wfkUECrmcV81MU)

---

## Tecnologias e competências utilizadas

- **Java (Swing)**
  - Construção de interface gráfica desktop (JFrame, JInternalFrame, JDesktopPane, JTable, etc.).
  - Organização em camadas (telas em `br.com.infox.telas`, conexão em `br.com.infox.dal`).
  - Tratamento de eventos (ActionListener), controle de perfis de usuário, validação de campos.
  - Empacotamento em JAR executável com dependências em `dist/lib`.

- **SQL / Banco de dados**
  - Uso de **SQLite** com JDBC (`sqlite-jdbc`) em modo embarcado (sem servidor externo).
  - Criação de tabelas (`tbclientes`, `tbusuarios`, `tbos`) e relacionamentos (FK `idcli` em `tbos`).
  - Comandos de CRUD: `INSERT`, `SELECT`, `UPDATE`, `DELETE`.
  - Scripts de criação e povoamento automático de dados via classe `CriarBanco` (incluindo cerca de 15 registros em cada tabela para testes/demonstração).
  - Adaptação de código originalmente pensado para MySQL (tipos, auto incremento, data/hora) para o dialeto do SQLite.

- **Integração com bibliotecas externas**
  - `sqlite-jdbc` para acesso ao SQLite.
  - `rs2xml.jar` para popular `JTable` diretamente a partir de `ResultSet`.
  - `jasperreports` para geração de relatórios (Clientes/Serviços) e `commons-logging` como dependência.
  - Configuração manual de dependências em projeto Ant/NetBeans, incluindo correções de `NoClassDefFoundError` e empacotamento em `dist/lib`.

- **Engenharia de build e distribuição**
  - Ajustes em `nbproject/` para corrigir erros de build relacionados a `nblibraries.properties` e Ant.
  - Configuração de diretórios `dist/`, `lib/` e `database/` para que o sistema rode a partir do JAR, mantendo o banco ao lado do executável.
  - Tratamento de caminhos relativos/absolutos para que o banco SQLite seja criado e utilizado corretamente em ambiente de desenvolvimento e em produção.

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
   - O projeto usa **SQLite embarcado**. O arquivo do banco (`database/dbinfox.db` ou `dist/database/dbinfox.db`) é criado e populado automaticamente pela classe `CriarBanco`.

---

## Estrutura do projeto

Principais pastas e arquivos:

```text
prjinfoX/
├─ build/                 # Saída de build do NetBeans (pode conter um .jar executável)
├─ dist/
│  ├─ prjinfoX.jar        # JAR executável gerado pelo NetBeans
│  ├─ lib/                # Dependências (sqlite-jdbc, rs2xml, jasperreports, commons-logging, etc.)
│  └─ database/
│     └─ dbinfox.db      # Banco de dados SQLite utilizado em runtime
├─ database/
│  ├─ dbinfox.db         # Banco de dados SQLite (ambiente de desenvolvimento)
│  └─ script.sql         # Opcional: script SQL de referência
├─ lib/
│  ├─ sqlite-jdbc-*.jar  # Driver JDBC do SQLite
│  ├─ rs2xml.jar         # Biblioteca para popular JTable a partir de ResultSet
│  ├─ jasperreports-*.jar# Bibliotecas JasperReports
│  └─ commons-*.jar      # Dependências auxiliares (commons-logging, beanutils, collections, etc.)
├─ src/
│  └─ br/com/infox/
│     ├─ dal/            # Camada de acesso a dados (ModuloConexao, CriarBanco)
│     └─ telas/          # Telas Swing (TelaLogin, TelaPrincipal, TelaOS, etc.)
└─ README.md
```

---

## Como executar o projeto (via JAR na pasta dist)

1. **Clonar o repositório**

   ```bash
   git clone https://github.com/SEU_USUARIO/prjinfoX.git
   cd prjinfoX
   ```

2. **(Opcional, primeira vez) Gerar/atualizar o banco de dados**

   Se estiver rodando a partir da IDE (NetBeans), a classe `CriarBanco` pode ser executada diretamente:

   - No NetBeans: clique com o botão direito em `CriarBanco.java` → **Run File**.
   - Isso irá:
     - Criar (ou atualizar) as tabelas `tbclientes`, `tbusuarios`, `tbos` no arquivo `database/dbinfox.db`.
     - Inserir registros de teste (clientes, usuários e ordens de serviço).

   Para rodar via JAR, recomenda-se copiar a pasta `database/` para dentro de `dist/` ao final do build:

   ```bash
   cp -r database dist/
   ```

3. **Executar o JAR compilado (pasta dist)**

   Depois de gerar o build pela IDE (NetBeans), haverá um JAR na pasta `dist/` (por exemplo, `prjinfoX.jar`).

   No terminal, a partir da raiz do projeto:

   ```bash
   cd dist
   java -jar prjinfoX.jar
   ```

   Se o Java estiver corretamente instalado, a tela de **login** será aberta e o sistema usará o banco em `dist/database/dbinfox.db`.

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
    - `perfil` (`admin` ou `user`).

- **Ordens de Serviço (`tbos`)**
  - Emissão de OS (tipo: OS ou Orçamento).
  - Situações: Aberta, Em andamento, Fechada, Aguardando aprovação, etc.
  - Associação da OS a um cliente via `idcli`.
  - Edição, cancelamento e (quando configurados os arquivos `.jasper`) impressão da OS.

- **Relatórios (JasperReports)**
  - Relatório de clientes: `clientes.jasper`.
  - Relatório de serviços: `servicos.jasper`.
  - Uso de `JasperFillManager.fillReport` e `JasperViewer.viewReport`.

  > Observação: atualmente os arquivos `.jasper` originais do curso não estão incluídos neste repositório. A funcionalidade de relatório pode ser adaptada criando novos arquivos `.jasper` (via Jaspersoft Studio) na pasta `reports/` e ajustando os caminhos no código.

---

## Resetar o banco de dados

Se você quiser **resetar** completamente os dados para o estado inicial:

1. Feche a aplicação.
2. Exclua o arquivo de banco:

   ```text
   prjinfoX/database/dbinfox.db
   ```

   ou, se estiver usando o banco dentro de `dist/`:

   ```text
   prjinfoX/dist/database/dbinfox.db
   ```

3. Rode novamente a classe `CriarBanco` (via IDE) ou execute o fluxo de inicialização configurado no JAR.

Um novo arquivo `dbinfox.db` será criado com as tabelas e registros de exemplo.

---

## Créditos e referência didática

Este projeto é baseado na série de aulas do canal **Curso em Vídeo**, adaptado para uso com banco de dados SQLite, mantendo a estrutura geral do sistema de OS (InfoX):

- Playlist original:  
  [https://www.youtube.com/watch?v=rB66EC0VXTA&list=PLbEOwbQR9lqxsTusvu8wfkUECrmcV81MU](https://www.youtube.com/watch?v=rB66EC0VXTA&list=PLbEOwbQR9lqxsTusvu8wfkUECrmcV81MU)

Adaptações e extensões realizadas neste repositório:

- Migração de MySQL para **SQLite** embarcado.
- Script de criação e povoamento automático de banco via classe `CriarBanco`.
- Ajustes de conexão (`ModuloConexao`) e compatibilidade com a nova estrutura de banco e com ambiente compilado (`dist/`).
- Inclusão de múltiplos registros de teste (clientes, usuários, ordens de serviço) para facilitar demonstrações e uso acadêmico.
- Correção de problemas de build Ant/NetBeans (como referência a `nblibraries.properties`) e configuração de dependências externas.
- Integração manual de bibliotecas JDBC, JasperReports e utilitários, demonstrando capacidade de lidar com projetos legados e resolver conflitos de dependências.
