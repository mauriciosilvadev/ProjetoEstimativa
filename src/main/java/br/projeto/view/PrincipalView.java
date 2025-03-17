package br.projeto.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

public final class PrincipalView extends JFrame {

    private JDesktopPane desktop;
    private JTree tree;
    private JScrollPane treeScrollPane;
    private JLabel footerLabel;

    public PrincipalView() {
        setTitle("Sistema de Estimativa de Projetos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        treeScrollPane = new JScrollPane();
        desktop = new JDesktopPane();

        JSplitPane divisoriaPainel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getTreeScrollPane(), getDesktop());
        divisoriaPainel.setDividerLocation(300);
        divisoriaPainel.setResizeWeight(0.0);
        divisoriaPainel.setContinuousLayout(true);
        divisoriaPainel.setOneTouchExpandable(true);

        add(divisoriaPainel, BorderLayout.CENTER);

        footerLabel = new JLabel();
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public void setMainComponents(JPanel painelSuperior) {
        add(painelSuperior, BorderLayout.NORTH);
    }

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public JTree getTree() {
        return tree;
    }

    public void setTree(JTree tree) {
        this.tree = tree;
        treeScrollPane.setViewportView(tree);
    }

    public JScrollPane getTreeScrollPane() {
        return treeScrollPane;
    }

    public JLabel getFooterLabel() {
        return footerLabel;
    }
}
