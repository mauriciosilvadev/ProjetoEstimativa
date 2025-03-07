/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.view.components;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class EstimativaTable extends JTable {

    public EstimativaTable(EstimativaTableModel model) {
        super(model);

        for (int i = 0; i < model.getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
        }
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        EstimativaTableModel model = (EstimativaTableModel) getModel();
        RowData rowData = model.getRowDataAt(row);
        RowType rowType = rowData.getRowType();

        // Se é a 1ª linha (PLATFORM_SELECTION) e col > 1 => checkbox
        if (rowType == RowType.PLATFORM_SELECTION && column > 1) {
            return new DefaultCellEditor(new JCheckBox());
        }

        // Se é FUNCTIONALITY e col == 0 => checkbox (selecionar funcionalidade)
        if (rowType == RowType.FUNCTIONALITY && column == 0) {
            return new DefaultCellEditor(new JCheckBox());
        }

        // Se é FUNCTIONALITY e col > 1 => campo de texto (para números)
        if (rowType == RowType.FUNCTIONALITY && column > 1) {
            return new DefaultCellEditor(new JTextField());
        }

        return super.getCellEditor(row, column);
    }
}
