/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.projeto.view.components;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Maurício Silva <mauricio.s.dev@gmail.com>
 */
public class CustomCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        EstimativaTableModel model = (EstimativaTableModel) table.getModel();
        RowData rowData = model.getRowDataAt(row);
        RowType rowType = rowData.getRowType();

        // Se for cabeçalho de categoria, exibimos em bold, centralizado
        if (rowType == RowType.CATEGORY_HEADER) {
            JLabel label = new JLabel(value == null ? "" : value.toString());
            label.setFont(label.getFont().deriveFont(Font.BOLD));
            label.setHorizontalAlignment(SwingConstants.CENTER);

            if (isSelected) {
                label.setOpaque(true);
                label.setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionForeground());
            }
            return label;
        }

        // Se o valor for Boolean, retornamos um JCheckBox
        if (value instanceof Boolean) {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected((Boolean) value);
            checkBox.setHorizontalAlignment(SwingConstants.CENTER);

            if (isSelected) {
                checkBox.setBackground(table.getSelectionBackground());
                checkBox.setForeground(table.getSelectionForeground());
            } else {
                checkBox.setBackground(table.getBackground());
                checkBox.setForeground(table.getForeground());
            }
            return checkBox;
        }

        // Caso contrário, deixamos o comportamento padrão do JLabel
        Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
        );

        ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);

        // Exemplo: linha 0 (PLATFORM_SELECTION) em itálico
        if (rowType == RowType.PLATFORM_SELECTION) {
            c.setFont(c.getFont().deriveFont(Font.ITALIC));
        }

        return c;
    }
}
