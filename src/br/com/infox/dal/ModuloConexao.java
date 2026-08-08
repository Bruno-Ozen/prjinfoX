/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.infox.dal;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author bruno
 */
public class ModuloConexao {
    // Método responsável por estabelecer a conexão com o banco
    public static Connection conector(){
        java.sql.Connection conexao = null;
        
        // Driver do SQLite
        String driver = "org.sqlite.JDBC";
        
        // URL do banco de dados SQLite (arquivo na pasta database)
        // O "./" indica que o arquivo está na raiz do projeto
        String url = "jdbc:sqlite:./database/dbinfox.db";
        
        // SQLite não precisa de usuário e senha por padrão
        // String user = "";
        // String password = "";
        
        try {
            // Carrega o driver
            Class.forName(driver);
            
            // Estabelece a conexão
            conexao = DriverManager.getConnection(url);
            
            // Testa a conexão (opcional)
            if (conexao != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            }
            
            return conexao;
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e);
            return null;
        }
    }
}