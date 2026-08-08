package br.com.infox.dal;

import java.sql.Connection;
import java.sql.Statement;

public class CriarBanco {
    public static void main(String[] args) {
        try {
            Connection conexao = ModuloConexao.conector();
            if (conexao == null) {
                System.out.println("Não foi possível conectar ao banco!");
                return;
            }

            Statement stmt = conexao.createStatement();

            // ==============================
            // Tabela de clientes
            // ==============================
            String sqlClientes =
                "CREATE TABLE IF NOT EXISTS tbclientes (" +
                "idclientes INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "fone TEXT, " +
                "email TEXT)";
            stmt.execute(sqlClientes);
            System.out.println("Tabela tbclientes criada com sucesso!");

            // ==============================
            // Tabela de usuários
            // ==============================
            String sqlUsuarios =
                "CREATE TABLE IF NOT EXISTS tbusuarios (" +
                "idusuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario TEXT NOT NULL, " +  // username curto (login amigável)
                "nome TEXT NOT NULL, " +     // nome completo
                "fone TEXT, " +
                "login TEXT NOT NULL UNIQUE, " +
                "senha TEXT NOT NULL, " +
                "perfil TEXT NOT NULL)";     // admin / user
            stmt.execute(sqlUsuarios);
            System.out.println("Tabela tbusuarios criada com sucesso!");

            // ==============================
            // Tabela de OS
            // ==============================
            // Campos usados na TelaOS:
            // os, data_os, tipo, situacao, equipamento, defeito,
            // servico, tecnico, valor, idcli
            String sqlOS =
                "CREATE TABLE IF NOT EXISTS tbos (" +
                "os INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data_os TEXT DEFAULT CURRENT_TIMESTAMP, " +
                "tipo TEXT NOT NULL, " +              // 'OS' ou 'Orçamento'
                "situacao TEXT NOT NULL, " +          // Aberta / Em andamento / Fechada / etc.
                "equipamento TEXT NOT NULL, " +
                "defeito TEXT NOT NULL, " +
                "servico TEXT, " +
                "tecnico TEXT, " +
                "valor TEXT, " +                      // armazenando como texto, como no seu código
                "idcli INTEGER NOT NULL, " +
                "FOREIGN KEY (idcli) REFERENCES tbclientes(idclientes))";
            stmt.execute(sqlOS);
            System.out.println("Tabela tbos criada com sucesso!");

            // ==============================
            // Dados iniciais - Usuários
            // ==============================

            // Usuário root/root - administrador
            String insertRoot =
                "INSERT OR IGNORE INTO tbusuarios " +
                "(usuario, nome, fone, login, senha, perfil) VALUES " +
                "('root', 'Administrador Root', '(44) 99999-9999', 'root', 'root', 'admin')";
            stmt.execute(insertRoot);

            // Outros usuários fictícios (técnicos, atendentes, gerentes)
            String insertUsuariosFicticios =
                "INSERT OR IGNORE INTO tbusuarios (usuario, nome, fone, login, senha, perfil) VALUES " +
                "('maria', 'Maria Oliveira', '(44) 98888-1111', 'maria', '123456', 'user')," +
                "('joao', 'João Souza', '(44) 97777-2222', 'joao', '123456', 'user')," +
                "('ana', 'Ana Lima', '(44) 96666-3333', 'ana', '123456', 'user')," +
                "('carlos', 'Carlos Técnico', '(44) 95555-4444', 'carlos', '123456', 'user')," +
                "('paula', 'Paula Atendimento', '(44) 94444-5555', 'paula', '123456', 'user')," +
                "('roberto', 'Roberto Gerente', '(44) 93333-6666', 'roberto', 'admin123', 'admin')," +
                "('lucas', 'Lucas Silva', '(44) 92222-7777', 'lucas', '123456', 'user')," +
                "('fernanda', 'Fernanda Dias', '(44) 91111-8888', 'fernanda', '123456', 'user')," +
                "('rafael', 'Rafael Martins', '(44) 90000-9999', 'rafael', '123456', 'user')," +
                "('julia', 'Júlia Nascimento', '(44) 98888-1212', 'julia', '123456', 'user')," +
                "('tiago', 'Tiago Ribeiro', '(44) 97777-3434', 'tiago', '123456', 'user')," +
                "('bruna', 'Bruna Costa', '(44) 96666-5656', 'bruna', '123456', 'user')," +
                "('eduardo', 'Eduardo Farias', '(44) 95555-7878', 'eduardo', '123456', 'user')," +
                "('patricia', 'Patrícia Menezes', '(44) 94444-9090', 'patricia', '123456', 'user')";
            stmt.execute(insertUsuariosFicticios);

            System.out.println("Usuários iniciais criados (ou já existentes).");

            // ==============================
            // Dados iniciais - Clientes
            // ==============================
            String insertClientes =
                "INSERT OR IGNORE INTO tbclientes (idclientes, nome, endereco, fone, email) VALUES " +
                "(1, 'Cliente Teste', 'Rua Exemplo, 123', '(44) 99999-0000', 'cliente@teste.com')," +
                "(2, 'Empresa Alpha Ltda', 'Av. Central, 1000', '(44) 98888-0001', 'contato@alpha.com')," +
                "(3, 'Oficina Beta', 'Rua das Oficinas, 45', '(44) 97777-0002', 'beta@oficina.com')," +
                "(4, 'Comércio Gamma', 'Rua Principal, 303', '(44) 96666-0003', 'gamma@comercio.com')," +
                "(5, 'Auto Center Delta', 'Av. Industrial, 150', '(44) 95555-0004', 'contato@deltaauto.com')," +
                "(6, 'Clínica Tecnológica Epsilon', 'Rua Tech, 200', '(44) 94444-0005', 'suporte@epsilon.com')," +
                "(7, 'Padaria Pão Quente', 'Rua do Pão, 77', '(44) 93333-0006', 'contato@paoquente.com')," +
                "(8, 'Mercado BomPreço', 'Av. Brasil, 500', '(44) 92222-0007', 'contato@bompreco.com')," +
                "(9, 'Escritório Zeta', 'Rua das Flores, 89', '(44) 91111-0008', 'contato@zeta.com')," +
                "(10, 'Farmácia Vida', 'Av. Saúde, 321', '(44) 90000-0009', 'contato@farmaciavida.com')," +
                "(11, 'Lanchonete Sabor & Arte', 'Rua Gourmet, 12', '(44) 98888-1010', 'contato@saborearte.com')," +
                "(12, 'Academia Forma Perfeita', 'Av. Esporte, 45', '(44) 97777-1111', 'contato@formaperfeita.com')," +
                "(13, 'Loja de Roupas Fashion', 'Rua da Moda, 99', '(44) 96666-1212', 'contato@fashion.com')," +
                "(14, 'Papelaria Criativa', 'Rua das Letras, 8', '(44) 95555-1313', 'contato@papelcriativa.com')," +
                "(15, 'Livraria Saber', 'Av. Conhecimento, 404', '(44) 94444-1414', 'contato@livrariasaber.com')";
            stmt.execute(insertClientes);
            System.out.println("Clientes iniciais criados (ou já existentes).");

            // ==============================
            // Dados iniciais - OS
            // ==============================
            String insertOS =
                "INSERT OR IGNORE INTO tbos (os, tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli) VALUES " +
                "(1,  'OS',        'Aberta',               'Notebook Dell',         'Não liga',                         'Troca de fonte e limpeza interna',      'Carlos Técnico', '350.00', 1)," +
                "(2,  'OS',        'Em andamento',         'Impressora HP',         'Papel atolando',                   'Limpeza de roletes',                    'Carlos Técnico', '180.00', 2)," +
                "(3,  'Orçamento', 'Aguardando aprovação', 'PC Desktop',            'Muito lento',                      'Formatação + upgrade SSD',              'Carlos Técnico', '520.00', 3)," +
                "(4,  'OS',        'Fechada',              'Notebook Lenovo',       'Tela quebrada',                    'Troca de tela',                         'Carlos Técnico', '750.00', 4)," +
                "(5,  'OS',        'Aberta',               'Servidor Dell',         'Reiniciando sozinho',              'Diagnóstico de hardware',               'Rafael Martins', '430.00', 5)," +
                "(6,  'OS',        'Em andamento',         'PC Recepção',           'Barulho estranho',                 'Limpeza completa e troca de cooler',    'Lucas Silva',    '260.00', 6)," +
                "(7,  'Orçamento', 'Aguardando aprovação', 'Notebook Acer',         'Superaquecendo',                   'Troca de pasta térmica + limpeza',      'Ana Lima',       '300.00', 7)," +
                "(8,  'OS',        'Fechada',              'Impressora Epson',      'Imprime borrado',                  'Alinhamento e limpeza de cabeçotes',    'Maria Oliveira', '190.00', 8)," +
                "(9,  'OS',        'Aberta',               'Notebook Samsung',      'Sem vídeo',                         'Verificação de cabo flat e GPU',        'Tiago Ribeiro',  '480.00', 9)," +
                "(10, 'OS',        'Em andamento',         'PC Escritório',         'Sem acesso à rede',                'Configuração de placa de rede',         'Fernanda Dias',  '210.00', 10)," +
                "(11, 'Orçamento', 'Aguardando aprovação', 'All-in-One HP',        'Ligando muito lento',              'Upgrade de memória e SSD',              'Bruna Costa',    '640.00', 11)," +
                "(12, 'OS',        'Fechada',              'Notebook ASUS',         'Teclado não funciona',             'Troca de teclado',                      'Eduardo Farias', '350.00', 12)," +
                "(13, 'OS',        'Aberta',               'PC Gamer',              'FPS muito baixo',                  'Limpeza + atualização de drivers',      'Rafael Martins', '290.00', 13)," +
                "(14, 'OS',        'Em andamento',         'Notebook Positivo',     'Travando constantemente',          'Formatação e reinstalação do sistema',  'Lucas Silva',    '380.00', 14)," +
                "(15, 'OS',        'Fechada',              'Impressora Brother',    'Não puxa papel',                   'Troca de roletes de tração',            'Carlos Técnico', '230.00', 15)";
            stmt.execute(insertOS);
            System.out.println("OS iniciais criadas (ou já existentes).");

            // ==============================
            // Finalização
            // ==============================
            stmt.close();
            conexao.close();

            System.out.println("Banco criado/configurado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao criar banco: " + e);
        }
    }
}