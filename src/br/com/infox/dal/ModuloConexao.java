package br.com.infox.dal;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ModuloConexao {
    // Método responsável por estabelecer a conexão com o banco
    public static Connection conector() {
        Connection conexao = null;

        String driver = "org.sqlite.JDBC";

        try {
            Class.forName(driver);

            // Descobre o diretório de execução da aplicação (onde está o JAR)
            String basePath = new File(".").getCanonicalPath();

            // Banco na pasta "database" relativa ao diretório de execução
            String dbPath = basePath + File.separator + "database" + File.separator + "dbinfox.db";

            // Garante que a pasta database existe
            File dbDir = new File(basePath + File.separator + "database");
            if (!dbDir.exists()) {
                dbDir.mkdirs();
            }

            String url = "jdbc:sqlite:" + dbPath;

            conexao = DriverManager.getConnection(url);
            System.out.println("Conexão estabelecida com sucesso! DB = " + dbPath);
            return conexao;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao conectar: " + e);
            return null;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao resolver caminho do banco: " + e);
            return null;
        }
    }
}