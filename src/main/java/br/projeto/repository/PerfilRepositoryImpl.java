/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.model.Categoria;
import br.projeto.model.Funcionalidade;
import br.projeto.model.Perfil;
import br.projeto.model.Plataforma;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class PerfilRepositoryImpl implements PerfilRepository {

    private final IDatabaseConnection connection;
    private static PerfilRepository instance;

    private PerfilRepositoryImpl(IDatabaseConnection connection) {
        this.connection = connection;
    }

    public static PerfilRepository getInstance(IDatabaseConnection connection) {
        if (instance == null) {
            return new PerfilRepositoryImpl(connection);
        }
        return instance;
    }

    @Override
    public void inserir(Perfil perfil) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(Perfil perfil, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Perfil> getPerfis() {
        // Query para buscar os perfis
        String queryPerfil = "SELECT id, nome FROM perfis";

        try {
            PreparedStatement stmtPerfil = connection.prepareStatement(queryPerfil);
            ResultSet rsPerfil = stmtPerfil.executeQuery();

            List<Perfil> perfis = new ArrayList<>();

            while (rsPerfil.next()) {
                Perfil perfil = new Perfil(rsPerfil.getInt("id"), rsPerfil.getString("nome"));
                perfis.add(perfil);
            }

            return perfis;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public Perfil buscarPorId(int id) {
        Perfil perfil = null;

        // Query para buscar os dados básicos do perfil
        String queryPerfil = "SELECT id, nome FROM perfis WHERE id = ?";

        // Query para buscar categorias associadas ao perfil
        String queryCategorias = "SELECT c.id, c.nome, c.tipo FROM perfis_categorias pc JOIN categorias c ON c.id = pc.categoria_id WHERE pc.perfil_id = ?";

        // Query para buscar plataformas associadas ao perfil
        String queryPlataformas = "SELECT p.id, p.nome FROM perfis_plataformas pp JOIN plataformas p ON p.id = pp.plataforma_id WHERE pp.perfil_id = ?";

        // Query para buscar funcionalidades associadas ao perfil por meio das plataformas
        String queryFuncionalidades = "SELECT f.id, f.nome, f.categoria_id, fp.valor, pp.plataforma_id "
                + "FROM funcionalidade_perfil_plataforma fp "
                + "JOIN funcionalidades f ON f.id = fp.funcionalidade_id "
                + "JOIN perfis_plataformas pp ON pp.id = fp.perfil_plataforma_id "
                + "WHERE pp.perfil_id = ?";

        try {
            PreparedStatement stmtPerfil = connection.prepareStatement(queryPerfil);
            PreparedStatement stmtCategorias = connection.prepareStatement(queryCategorias);
            PreparedStatement stmtPlataformas = connection.prepareStatement(queryPlataformas);
            PreparedStatement stmtFuncionalidades = connection.prepareStatement(queryFuncionalidades);

            stmtPerfil.setInt(1, id);
            ResultSet rsPerfil = stmtPerfil.executeQuery();

            if (rsPerfil.next()) {
                perfil = new Perfil(rsPerfil.getInt("id"), rsPerfil.getString("nome"));

                // Buscar categorias associadas
                stmtCategorias.setInt(1, id);
                ResultSet rsCategorias = stmtCategorias.executeQuery();
                while (rsCategorias.next()) {
                    Categoria categoria = new Categoria(rsCategorias.getInt("id"), rsCategorias.getString("nome"), rsCategorias.getString("tipo"));
                    perfil.addCategoria(categoria);
                }

                // Buscar plataformas associadas
                stmtPlataformas.setInt(1, id);
                ResultSet rsPlataformas = stmtPlataformas.executeQuery();
                while (rsPlataformas.next()) {
                    Plataforma plataforma = new Plataforma(rsPlataformas.getInt("id"), rsPlataformas.getString("nome"));
                    perfil.addPlataforma(plataforma);
                }

                // Buscar funcionalidades associadas ao perfil
                stmtFuncionalidades.setInt(1, id);
                ResultSet rsFuncionalidades = stmtFuncionalidades.executeQuery();

                List<Funcionalidade> funcionalidades = new ArrayList<>();

                while (rsFuncionalidades.next()) {
                    int funcId = rsFuncionalidades.getInt("id");
                    int categoriaId = rsFuncionalidades.getInt("categoria_id");
                    String nomeFunc = rsFuncionalidades.getString("nome");
                    int plataformaId = rsFuncionalidades.getInt("plataforma_id");
                    double valor = rsFuncionalidades.getDouble("valor");

                    // Verifica se já existe uma funcionalidade com esse ID na lista
                    Funcionalidade funcionalidadeExistente = null;
                    for (Funcionalidade func : funcionalidades) {
                        if (func.getId() == funcId) {
                            funcionalidadeExistente = func;
                            break;
                        }
                    }

                    if (funcionalidadeExistente != null) {
                        // Se já existe, adiciona o valor da plataforma a essa funcionalidade
                        funcionalidadeExistente.addValorPlataforma(perfil.getPlataformaById(plataformaId), valor);
                    } else {
                        // Se não existe, cria a funcionalidade e adiciona o valor da plataforma
                        Funcionalidade novaFunc = new Funcionalidade(funcId, perfil.getCategoriaById(categoriaId), perfil, nomeFunc);
                        novaFunc.addValorPlataforma(perfil.getPlataformaById(plataformaId), valor);
                        funcionalidades.add(novaFunc);
                    }
                }

                perfil.setFuncionalidades(funcionalidades);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return perfil;
    }
}
