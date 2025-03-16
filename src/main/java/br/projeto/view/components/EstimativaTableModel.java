/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.view.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import br.projeto.model.Categoria;
import br.projeto.model.Funcionalidade;
import br.projeto.model.Perfil;
import br.projeto.model.Plataforma;
import br.projeto.presenter.ManterProjetoPresenter;
import br.projeto.service.ValidaFuncionalidadeService;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class EstimativaTableModel extends AbstractTableModel {

    private List<Plataforma> plataformas;
    private Map<Plataforma, Boolean> plataformasSelecionadas;
    private List<RowData> rows;

    private final List<String> colunasPlataformas = new ArrayList<>();
    private Perfil perfil;

    private final ManterProjetoPresenter presenter;

    public EstimativaTableModel(Perfil perfil, ManterProjetoPresenter presenter) {
        this.perfil = perfil;
        this.presenter = presenter;
        this.plataformas = perfil.getPlataformas();

        colunasPlataformas.add("Selecionar"); // col 0
        colunasPlataformas.add("Nome");       // col 1

        for (Plataforma p : plataformas) {
            colunasPlataformas.add(p.getNome());  // col >=2
        }

        this.plataformasSelecionadas = new HashMap<>();
        for (Plataforma p : plataformas) {
            this.plataformasSelecionadas.put(p, false);
        }

        buildRowData();
    }

    private void buildRowData() {
        rows = new ArrayList<>();

        // Linha 0: Seleção de plataformas
        rows.add(new RowData(RowType.PLATFORM_SELECTION, null, null));

        // Agrupar funcionalidades por categoria
        Map<Categoria, List<Funcionalidade>> mapaCat = new LinkedHashMap<>();
        for (Funcionalidade f : perfil.getFuncionalidades()) {
            Categoria c = f.getCategoria();
            mapaCat.computeIfAbsent(c, k -> new ArrayList<>()).add(f);
        }

        // Para cada categoria, cria-se um header e depois as funcionalidades
        for (Map.Entry<Categoria, List<Funcionalidade>> entry : mapaCat.entrySet()) {
            Categoria cat = entry.getKey();
            rows.add(new RowData(RowType.CATEGORY_HEADER, cat, null));

            for (Funcionalidade f : entry.getValue()) {
                rows.add(new RowData(RowType.FUNCTIONALITY, cat, f));
            }
        }
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return colunasPlataformas.size();
    }

    @Override
    public String getColumnName(int column) {
        return colunasPlataformas.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            // Checkbox para funcionalidades e row 0
            return Boolean.class;
        } else if (columnIndex == 1) {
            return String.class;
        } else {
            return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        RowData rowData = rows.get(rowIndex);
        RowType rowType = rowData.getRowType();

        switch (rowType) {
            case PLATFORM_SELECTION:
                // Editável só nas colunas de plataforma (col >=2)
                return columnIndex > 1;

            case CATEGORY_HEADER:
                return false;

            case FUNCTIONALITY:
                if (columnIndex == 0) {
                    return true; // checkbox de seleção da funcionalidade
                }
                if (columnIndex >= 2) {
                    return true; // valor numérico
                }
                return false;

            default:
                return false;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RowData rowData = rows.get(rowIndex);
        RowType rowType = rowData.getRowType();

        switch (rowType) {
            case PLATFORM_SELECTION:
                if (columnIndex == 0) {
                    return "Selecionar Plataformas";
                } else if (columnIndex == 1) {
                    return "";
                } else {
                    // col >= 2 => retorna se a plataforma está selecionada
                    Plataforma p = plataformas.get(columnIndex - 2);
                    return plataformasSelecionadas.get(p);
                }

            case CATEGORY_HEADER:
                if (columnIndex == 0) {
                    return "";
                } else if (columnIndex == 1) {
                    return "[ " + rowData.getCategoria().getNome() + " ]";
                } else {
                    return "";
                }

            case FUNCTIONALITY:
                Funcionalidade f = rowData.getFuncionalidade();

                if (columnIndex == 0) {
                    return f.isFoiSelecionada();
                } else if (columnIndex == 1) {
                    return f.getNome();
                } else {
                    Plataforma p = plataformas.get(columnIndex - 2);
                    double val = f.getValorPorPlataforma(p);

                    // Ajuste de exibição de acordo com a categoria
                    String tipo = f.getCategoria().getTipo();
                    if ("percentual".equals(tipo)) {
                        return val + "%";
                    } else if ("monetario".equals(tipo)) {
                        return "R$" + val;
                    } else {
                        return val + " dias";
                    }
                }

            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        RowData rowData = rows.get(rowIndex);
        RowType rowType = rowData.getRowType();

        switch (rowType) {
            case PLATFORM_SELECTION:
                if (columnIndex >= 2) {
                    Plataforma p = plataformas.get(columnIndex - 2);
                    plataformasSelecionadas.put(p, (Boolean) value);
                }
                break;

            case FUNCTIONALITY:
                Funcionalidade f = rowData.getFuncionalidade();

                if (columnIndex == 0) {
                    boolean novoValor = (Boolean) value;

                    if (novoValor) {
                        // Tentando marcar a funcionalidade e montando lista temporária (jáSelecionadas + esta)
                        List<Funcionalidade> jaSelecionadas = getFuncionalidadesSelecionadas();
                        jaSelecionadas.add(f);

                        try {
                            ValidaFuncionalidadeService.getInstance()
                                    .validaFuncionalidades(jaSelecionadas);

                            f.setFoiSelecionada(true);
                        } catch (IllegalArgumentException ex) {
                            // Exibe popup
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(
                                        null,
                                        ex.getMessage(),
                                        "Erro de Validação",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            });
                        }
                    } else {
                        // Desmarcou
                        f.setFoiSelecionada(false);
                    }

                } else if (columnIndex >= 2) {
                    // Editando valor numérico
                    Plataforma p = plataformas.get(columnIndex - 2);
                    try {
                        double val = Double.parseDouble(value.toString()
                                .replace("R$", "")
                                .replace("%", "")
                                .replace("dias", "")
                                .trim());
                        f.addValorPlataforma(p, val);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Valor inválido: " + value,
                                "Erro",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
                break;

            default:
                break;
        }

        // Informa ao TableModel que a célula mudou
        fireTableCellUpdated(rowIndex, columnIndex);

        // Notifica o Presenter para que ele atualize a view com a estimativa
        presenter.onMudancaNaTabela();
    }

    public RowData getRowDataAt(int row) {
        return rows.get(row);
    }

    /**
     * Retorna as funcionalidades selecionadas atualmente.
     */
    public List<Funcionalidade> getFuncionalidadesSelecionadas() {
        List<Funcionalidade> selecionadas = new ArrayList<>();
        for (RowData r : rows) {
            if (r.getRowType() == RowType.FUNCTIONALITY) {
                Funcionalidade f = r.getFuncionalidade();
                if (f.isFoiSelecionada()) {
                    selecionadas.add(f);
                }
            }
        }
        return selecionadas;
    }

    /**
     * Retorna as plataformas marcadas.
     */
    public List<Plataforma> getPlataformasSelecionadas() {
        List<Plataforma> lista = new ArrayList<>();
        for (Map.Entry<Plataforma, Boolean> entry : plataformasSelecionadas.entrySet()) {
            if (Boolean.TRUE.equals(entry.getValue())) {
                lista.add(entry.getKey());
            }
        }
        return lista;
    }

    public void setPlataformaSelecionada(Plataforma plataformaSelecionada) {
        for (Map.Entry<Plataforma, Boolean> entry : plataformasSelecionadas.entrySet()) {
            if (entry.getKey().getId() == plataformaSelecionada.getId()) {
                entry.setValue(true);
            }
        }
    }
}
