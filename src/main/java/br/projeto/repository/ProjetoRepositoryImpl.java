package br.projeto.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.projeto.dbConnection.connections.IDatabaseConnection;
import br.projeto.model.Funcionalidade;
import br.projeto.model.Perfil;
import br.projeto.model.Plataforma;
import br.projeto.model.ProjetoEstimativa;
import br.projeto.presenter.Observer;
import br.projeto.repository.traits.PegarUltimoIdInserido;
import br.projeto.session.UsuarioSession;

public class ProjetoRepositoryImpl extends PegarUltimoIdInserido implements ProjetoRepository {

    private final List<Observer> observers;
    private static ProjetoRepository instance;
    private final IDatabaseConnection connection;

    private ProjetoRepositoryImpl(IDatabaseConnection connection) {
        observers = new ArrayList<>();
        this.connection = connection;
    }

    public static ProjetoRepository getInstance(IDatabaseConnection connection) {
        if (instance == null) {
            return new ProjetoRepositoryImpl(connection);
        }
        return instance;
    }

    @Override
    public List<ProjetoEstimativa> getProjetosCompartilhados() {
        List<ProjetoEstimativa> projetos = new ArrayList<>();
        int userId = UsuarioSession.getInstance().getUsuarioLogado().getId();

        String sql = "SELECT p.* FROM projetos p "
                + "INNER JOIN projetos_compartilhados pc ON p.id = pc.projeto_id "
                + "WHERE pc.usuario_id = " + userId;

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ProjetoEstimativa projeto = new ProjetoEstimativa(
                        rs.getDouble("total_dias"),
                        rs.getDouble("valor_total"),
                        rs.getDouble("percentual_imposto"),
                        rs.getDouble("percentual_lucro"),
                        rs.getDouble("custo_hardware"),
                        rs.getDouble("custo_software"),
                        rs.getDouble("custo_riscos"),
                        rs.getDouble("custo_garantia"),
                        rs.getDouble("fundo_reserva"),
                        rs.getDouble("outros_custos"),
                        rs.getInt("id"),
                        rs.getInt("perfil_id"),
                        rs.getInt("usuario_id"),
                        rs.getString("nome")
                );

                projeto.setDataCriacao(rs.getString("data_criacao"));

                projeto = populaProjetoEstimativa(projeto);

                projetos.add(projeto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível obter os projetos de estimativa compartilhados", e);
        }

        return projetos;
    }

    @Override
    public List<ProjetoEstimativa> getProjetos() {

        List<ProjetoEstimativa> projetos = new ArrayList<>();
        int userId = UsuarioSession.getInstance().getUsuarioLogado().getId();

        String sql = "SELECT * FROM projetos WHERE usuario_id = " + userId;

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ProjetoEstimativa projeto = new ProjetoEstimativa(
                        rs.getDouble("total_dias"),
                        rs.getDouble("valor_total"),
                        rs.getDouble("percentual_imposto"),
                        rs.getDouble("percentual_lucro"),
                        rs.getDouble("custo_hardware"),
                        rs.getDouble("custo_software"),
                        rs.getDouble("custo_riscos"),
                        rs.getDouble("custo_garantia"),
                        rs.getDouble("fundo_reserva"),
                        rs.getDouble("outros_custos"),
                        rs.getInt("id"),
                        rs.getInt("perfil_id"),
                        rs.getInt("usuario_id"),
                        rs.getString("nome")
                );

                projeto.setDataCriacao(rs.getString("data_criacao"));

                projeto = populaProjetoEstimativa(projeto);

                projetos.add(projeto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível obter os projetos de estimativa", e);
        }

        return projetos;
    }

    public boolean hasProjeto(String nome) {
        String sql = "SELECT * FROM projetos WHERE nome = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível verificar se o projeto de estimativa existe", e);
        }
    }

    @Override
    public ProjetoEstimativa getProjetoPorNome(String nome) {
        String sql = "SELECT * FROM projetos WHERE nome = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProjetoEstimativa projeto = new ProjetoEstimativa(
                            rs.getDouble("total_dias"),
                            rs.getDouble("valor_total"),
                            rs.getDouble("percentual_imposto"),
                            rs.getDouble("percentual_lucro"),
                            rs.getDouble("custo_hardware"),
                            rs.getDouble("custo_software"),
                            rs.getDouble("custo_riscos"),
                            rs.getDouble("custo_garantia"),
                            rs.getDouble("fundo_reserva"),
                            rs.getDouble("outros_custos"),
                            rs.getInt("id"),
                            rs.getInt("perfil_id"),
                            rs.getInt("usuario_id"),
                            rs.getString("nome")
                    );

                    projeto.setDataCriacao(rs.getString("data_criacao"));

                    projeto = populaProjetoEstimativa(projeto);

                    return projeto;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível obter o projeto de estimativa", e);
        }

        return null;
    }

    @Override
    public void adicionarProjeto(ProjetoEstimativa projetoEstimativa) {
        projetoEstimativa.setDataCriacao((new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss"))
                .format(new java.util.Date()));

        String projetoSql = "INSERT INTO projetos "
                + "(usuario_id, perfil_id, nome, custo_hardware, custo_software, "
                + "custo_riscos, custo_garantia, fundo_reserva, outros_custos, "
                + "percentual_imposto, percentual_lucro, total_dias, valor_total, data_criacao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String projetoPlataformaSql = "INSERT INTO projetos_plataformas (projeto_id, plataforma_id) VALUES (?, ?)";
        String projetoFuncionalidadeSql = "INSERT INTO funcionalidade_projeto_plataforma (projeto_plataforma_id, funcionalidade_id, valor) VALUES (?, ?, ?)";

        try {
            try (PreparedStatement pstmt = connection.prepareStatement(projetoSql)) {
                pstmt.setInt(1, projetoEstimativa.getUserId());
                pstmt.setInt(2, projetoEstimativa.getPerfilId());
                pstmt.setString(3, projetoEstimativa.getNome());
                pstmt.setDouble(4, projetoEstimativa.getCustoHardware());
                pstmt.setDouble(5, projetoEstimativa.getCustoSoftware());
                pstmt.setDouble(6, projetoEstimativa.getCustosRiscos());
                pstmt.setDouble(7, projetoEstimativa.getCustoGarantia());
                pstmt.setDouble(8, projetoEstimativa.getFundoReserva());
                pstmt.setDouble(9, projetoEstimativa.getOutrosCustos());
                pstmt.setDouble(10, projetoEstimativa.getPercentualImposto());
                pstmt.setDouble(11, projetoEstimativa.getPercentualLucro());
                pstmt.setDouble(12, projetoEstimativa.getTotalDias());
                pstmt.setDouble(13, projetoEstimativa.getValorTotal());
                pstmt.setString(14, projetoEstimativa.getDataCriacao());

                pstmt.executeUpdate();

                int projetoId = obterUltimoIdInserido(connection, pstmt);

                projetoEstimativa.setId(projetoId);
            }

            try (PreparedStatement pstmtPlataforma = connection.prepareStatement(projetoPlataformaSql)) {
                for (Plataforma plataforma : projetoEstimativa.getPlatafomasSelecionadas()) {
                    pstmtPlataforma.setInt(1, projetoEstimativa.getId());
                    pstmtPlataforma.setInt(2, plataforma.getId());
                    pstmtPlataforma.executeUpdate();

                    int idProjetoPlataforma = obterUltimoIdInserido(connection, pstmtPlataforma);

                    for (Funcionalidade funcionalidade : projetoEstimativa.getFuncionalidadesSelecionadas()) {
                        try (PreparedStatement pstmtFuncionalidade = connection.prepareStatement(projetoFuncionalidadeSql)) {
                            pstmtFuncionalidade.setInt(1, idProjetoPlataforma);
                            pstmtFuncionalidade.setInt(2, funcionalidade.getId());
                            pstmtFuncionalidade.setDouble(3, funcionalidade.getValorPorPlataforma(plataforma));
                            pstmtFuncionalidade.executeUpdate();
                        }
                    }
                }
            }

            notifyObservers(getProjetos());
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível inserir o projeto de estimativa", e);
        }
    }

    @Override
    public void atualizarProjeto(ProjetoEstimativa projetoEstimativa) {
        String projetoSql = "UPDATE projetos SET "
                + "usuario_id = ?, perfil_id = ?, nome = ?, custo_hardware = ?, custo_software = ?, "
                + "custo_riscos = ?, custo_garantia = ?, fundo_reserva = ?, outros_custos = ?, "
                + "percentual_imposto = ?, percentual_lucro = ?, total_dias = ?, valor_total = ? "
                + "WHERE id = ?";

        // Remove as associações antigas
        String deleteProjetoFuncionalidadeSql = "DELETE FROM funcionalidade_projeto_plataforma "
                + "WHERE projeto_plataforma_id IN (SELECT id FROM projetos_plataformas WHERE projeto_id = ?)";
        String deleteProjetoPlataformaSql = "DELETE FROM projetos_plataformas WHERE projeto_id = ?";

        // Insere as novas associações
        String projetoPlataformaSql = "INSERT INTO projetos_plataformas (projeto_id, plataforma_id) VALUES (?, ?)";
        String projetoFuncionalidadeSql = "INSERT INTO funcionalidade_projeto_plataforma (projeto_plataforma_id, funcionalidade_id, valor) VALUES (?, ?, ?)";

        try {
            try (PreparedStatement pstmt = connection.prepareStatement(projetoSql)) {
                pstmt.setInt(1, projetoEstimativa.getUserId());
                pstmt.setInt(2, projetoEstimativa.getPerfilId());
                pstmt.setString(3, projetoEstimativa.getNome());
                pstmt.setDouble(4, projetoEstimativa.getCustoHardware());
                pstmt.setDouble(5, projetoEstimativa.getCustoSoftware());
                pstmt.setDouble(6, projetoEstimativa.getCustosRiscos());
                pstmt.setDouble(7, projetoEstimativa.getCustoGarantia());
                pstmt.setDouble(8, projetoEstimativa.getFundoReserva());
                pstmt.setDouble(9, projetoEstimativa.getOutrosCustos());
                pstmt.setDouble(10, projetoEstimativa.getPercentualImposto());
                pstmt.setDouble(11, projetoEstimativa.getPercentualLucro());
                pstmt.setDouble(12, projetoEstimativa.getTotalDias());
                pstmt.setDouble(13, projetoEstimativa.getValorTotal());
                pstmt.setInt(14, projetoEstimativa.getId());

                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = connection.prepareStatement(deleteProjetoFuncionalidadeSql)) {
                pstmt.setInt(1, projetoEstimativa.getId());
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmt = connection.prepareStatement(deleteProjetoPlataformaSql)) {
                pstmt.setInt(1, projetoEstimativa.getId());
                pstmt.executeUpdate();
            }

            try (PreparedStatement pstmtPlataforma = connection.prepareStatement(projetoPlataformaSql)) {
                for (Plataforma plataforma : projetoEstimativa.getPlatafomasSelecionadas()) {
                    pstmtPlataforma.setInt(1, projetoEstimativa.getId());
                    pstmtPlataforma.setInt(2, plataforma.getId());
                    pstmtPlataforma.executeUpdate();

                    int idProjetoPlataforma = obterUltimoIdInserido(connection, pstmtPlataforma);
                    for (Funcionalidade funcionalidade : projetoEstimativa.getFuncionalidadesSelecionadas()) {
                        try (PreparedStatement pstmtFuncionalidade = connection.prepareStatement(projetoFuncionalidadeSql)) {
                            pstmtFuncionalidade.setInt(1, idProjetoPlataforma);
                            pstmtFuncionalidade.setInt(2, funcionalidade.getId());
                            pstmtFuncionalidade.setDouble(3, funcionalidade.getValorPorPlataforma(plataforma));
                            pstmtFuncionalidade.executeUpdate();
                        }
                    }
                }
            }

            notifyObservers(getProjetos());
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível atualizar o projeto de estimativa", e);
        }
    }

    @Override
    public boolean removerProjetoPorNome(String nome) {
        String sql = "DELETE FROM projetos WHERE nome = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.executeUpdate();

            notifyObservers(getProjetos());

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível remover o projeto de estimativa", e);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(List<ProjetoEstimativa> projetos) {
        for (Observer observer : observers) {
            observer.update(projetos);
        }
    }

    private ProjetoEstimativa populaProjetoEstimativa(ProjetoEstimativa projeto) {
        Perfil perfil = PerfilRepositoryImpl.getInstance(connection).buscarPorId(projeto.getPerfilId());

        projeto.setPerfil(perfil);

        String projetosPlataformasSql = "SELECT * FROM projetos_plataformas WHERE projeto_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(projetosPlataformasSql)) {
            pstmt.setInt(1, projeto.getId());
            try (ResultSet rsProjetoPlataformas = pstmt.executeQuery()) {
                List<Plataforma> plataformas = new ArrayList<>();
                List<Funcionalidade> funcionalidades = new ArrayList<>();
                while (rsProjetoPlataformas.next()) {

                    // Pegar plataformas
                    int plataformaId = rsProjetoPlataformas.getInt("plataforma_id");
                    Plataforma plataforma = PlataformaRepositoryImpl.getInstance(connection).buscarPorId(plataformaId);
                    plataformas.add(plataforma);

                    // Pegar funcionalidades com seus valores
                    int projetoPlataformaId = rsProjetoPlataformas.getInt("id");
                    String funcionalidadesProjetoPlataformaSql = "SELECT * FROM funcionalidade_projeto_plataforma WHERE projeto_plataforma_id = ?";

                    try (PreparedStatement pstmtFuncionalidades = connection.prepareStatement(funcionalidadesProjetoPlataformaSql)) {
                        pstmtFuncionalidades.setInt(1, projetoPlataformaId);
                        try (ResultSet rsFuncionalidades = pstmtFuncionalidades.executeQuery()) {
                            while (rsFuncionalidades.next()) {
                                boolean funcionalidadeJaAdicionada = false;
                                for (Funcionalidade funcionalidade : funcionalidades) {
                                    if (funcionalidade.getId() == rsFuncionalidades.getInt("funcionalidade_id")) {
                                        funcionalidade.addValorPlataforma(plataforma, rsFuncionalidades.getDouble("valor"));
                                        funcionalidadeJaAdicionada = true;
                                        break;
                                    }
                                }

                                if (funcionalidadeJaAdicionada) {
                                    continue;
                                }

                                int funcionalidadeId = rsFuncionalidades.getInt("funcionalidade_id");
                                double valor = rsFuncionalidades.getDouble("valor");

                                Funcionalidade funcionalidade = FuncionalidadeRepositoryImpl.getInstance(connection).buscarPorId(funcionalidadeId);
                                funcionalidade.addValorPlataforma(plataforma, valor);
                                funcionalidade.setPerfil(perfil);
                                funcionalidade.setFoiSelecionada(true);

                                funcionalidades.add(funcionalidade);
                            }
                        }
                    }
                }

                projeto.setPlatafomasSelecionadas(plataformas);
                projeto.setFuncionalidadesSelecionadas(funcionalidades);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível obter as plataformas e funcionalidades do projeto de estimativa", e);
        }

        return projeto;
    }

    @Override
    public boolean compartilharProjeto(int idProjeto, int idUsuario) {
        String checkSql = "SELECT * FROM projetos_compartilhados WHERE projeto_id = ? AND usuario_id = ?";

        // Verifica se o projeto já foi compartilhado com o mesmo usuário
        try (PreparedStatement checkPstmt = connection.prepareStatement(checkSql)) {
            checkPstmt.setInt(1, idProjeto);
            checkPstmt.setInt(2, idUsuario);

            try (ResultSet rs = checkPstmt.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível verificar se o projeto já foi compartilhado", e);
        }

        String sql = "INSERT INTO projetos_compartilhados (projeto_id, usuario_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idProjeto);
            pstmt.setInt(2, idUsuario);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível compartilhar o projeto de estimativa", e);
        }
    }

}
