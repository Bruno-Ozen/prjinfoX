package br.com.infox.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bruno
 */
public class ModuloConexao {
    // Método responsável por estabelecer a conexão com o banco
    public static Connection conector() {
        Connection conexao = null;

        // Driver do SQLite
        String driver = "org.sqlite.JDBC";

        // IMPORTANTE:
        // Caminho RELATIVO ao diretório de execução.
        // Sem a barra inicial, vai criar/usar "database/dbinfox.db"
        // dentro da pasta do projeto / dist.
        String url = "jdbc:sqlite:database/dbinfox.db";

        try {
            // Carrega o driver
            Class.forName(driver);

            // Estabelece a conexão
            conexao = DriverManager.getConnection(url);

            // Log para debug
            if (conexao != null) {
                System.out.println("Conexão estabelecida com sucesso! URL = " + url);
            }

            return conexao;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao conectar: " + e);
            return null;
        }
    }
}