package br.dev.nicolas.dukemarket.graphic.DAO;

import br.dev.nicolas.dukemarket.graphic.model.Clientes;
import connection.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientesDAO {

    // 4.6.1 Strings DML (Data Manipulation Language)
    private static final String SQL_INSERT = "INSERT INTO clientes(Endereco, "
            + "NomeCompleto, CPF, Cidade, UF) "
            + "VALUES (?, ?, ?, ?, ?)";

    
    private static final String SQL_SELECT_ALL = "SELECT * FROM clientes";
    private static final String SQL_SELECT_ID = "SELECT * FROM clientes "
            + "WHERE id = ?";

    private static final String SQL_UPDATE = "UPDATE clientes SET Endereco = ?,"
            + "NomeCompleto = ?, CPF = ?, Cidade = ?, UF = ? "
            + "WHERE id = ?";

    private static final String SQL_DELETE = "DELETE FROM clientes WHERE id = ?";

    /**
     * Insere um usuario na base de dados Produto
     * 
     * @param c
     */
    public void create(Clientes c) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, c.getEndereco());
            stmt.setString(2, c.getNomeCompleto());
            stmt.setString(3, c.getCPF());
            stmt.setString(4, c.getCidade());
            stmt.setString(5, c.getUF());

            // Executa a query
            int auxRetorno = stmt.executeUpdate();

            Logger.getLogger(ClientesDAO.class.getName()).log(Level.INFO, null,
                    "Inclusao: " + auxRetorno);

        } catch (SQLException sQLException) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }
    }

    /**
     * Retorna todos os registros da tabela produto
     * 
     * @return
     */
    public List<Clientes> getResults() {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Clientes c = null;
        List<Clientes> listaClientes = null;

        try {
            // Prepara a string de SELECT e executa a query.
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery();

            // Carrega os dados do ResultSet rs, converte em Produto e
            // adiciona na lista de retorno.
            listaClientes = new ArrayList<>();

            while (rs.next()) {
                c = new Clientes();
                c.setId(rs.getInt("id"));
                c.setEndereco(rs.getString("Endereco"));
                c.setNomeCompleto(rs.getString("NomeCompleto"));
                c.setCPF(rs.getString("CPF"));
                c.setCidade(rs.getString("Cidade"));
                c.setUF(rs.getString("UF"));
                c.setDataCadastro(rs.getString("dataCadastro"));
                listaClientes.add(c);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Encerra a conexão com o banco, o statement e o resultset
            MySQLConnection.closeConnection(conn, stmt, rs);
        }

        return listaClientes;
    }

    /**
     * Retorna um produto da tabela produto.
     * 
     * @param id Identificação do Produto
     * @return
     */
    public Clientes getResultById(int id) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Clientes c = null;

        try {
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                c = new Clientes();
                c.setId(rs.getInt("id"));
                c.setEndereco(rs.getString("Endereco"));
                c.setNomeCompleto(rs.getString("NomeCompleto"));
                c.setCPF(rs.getString("CPF"));
                c.setCidade(rs.getString("Cidade"));
                c.setUF(rs.getString("UF"));
                c.setDataCadastro(rs.getString("dataCadastro"));
            }

        } catch (SQLException sQLException) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt, rs);
        }

        return c;
    }

    /**
     * Atualiza um registro na tabela produto.
     * 
     * @param c Produto a ser atualizado.
     */
    public void update(Clientes c) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, c.getEndereco());
            stmt.setString(2, c.getNomeCompleto());
            stmt.setString(3, c.getCPF());
            stmt.setString(4, c.getCidade());
            stmt.setString(5, c.getUF());
            stmt.setInt(6, c.getId());

            // Executa a query
            int auxRetorno = stmt.executeUpdate();

            Logger.getLogger(ClientesDAO.class.getName()).log(Level.INFO, null,
                    "Update: " + auxRetorno);

        } catch (SQLException sQLException) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }
    }

    /**
     * Exclui um produto com base do ID fornecido.
     * 
     * @param id Identificação do produto.
     */
    public void delete(int id) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);

            // Executa a query
            int auxRetorno = stmt.executeUpdate();

            Logger.getLogger(ClientesDAO.class.getName()).log(Level.INFO, null,
                    "Delete: " + auxRetorno);

        } catch (SQLException sQLException) {
            Logger.getLogger(ClientesDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }
    }


}