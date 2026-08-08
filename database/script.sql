-- ============================================
-- Script de criação do banco dbinfox (SQLite)
-- Sistema InfoX - Banco de Dados Completo
-- ============================================

-- Tabela de clientes
CREATE TABLE IF NOT EXISTS tbclientes (
    idclientes INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    endereco TEXT,
    fone TEXT,
    email TEXT
);

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS tbusuarios (
    idusuario INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario TEXT NOT NULL,
    nome TEXT NOT NULL,
    fone TEXT,
    login TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    perfil TEXT NOT NULL
);

-- Tabela de OS (Ordem de Serviço)
CREATE TABLE IF NOT EXISTS tbos (
    os INTEGER PRIMARY KEY AUTOINCREMENT,
    data_os TEXT DEFAULT CURRENT_TIMESTAMP,
    equipamento TEXT NOT NULL,
    defeito TEXT NOT NULL,
    servico TEXT,
    tecnico TEXT,
    valor TEXT,
    status TEXT,
    idcli INTEGER NOT NULL,
    FOREIGN KEY (idcli) REFERENCES tbclientes(idclientes)
);

-- ============================================
-- Inserindo dados iniciais
-- ============================================

-- Usuário administrador (root/root)
INSERT INTO tbusuarios (usuario, nome, fone, login, senha, perfil) VALUES 
    ('root', 'Administrador Root', '(44) 99999-9999', 'root', 'root', 'admin');

-- Usuário comum para testes
INSERT INTO tbusuarios (usuario, nome, fone, login, senha, perfil) VALUES 
    ('user', 'Usuário Teste', '(44) 88888-8888', 'user', '123456', 'user');

-- Cliente de exemplo
INSERT INTO tbclientes (nome, endereco, fone, email) VALUES 
    ('Cliente Teste', 'Rua Exemplo, 123', '(44) 99999-9999', 'cliente@teste.com');
