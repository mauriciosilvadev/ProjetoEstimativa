package br.projeto.command;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.repository.ProjetoRepository;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.StringJoiner;

public class ExportarProjetoEstimativaPDFCommand implements ProjetoCommand {

    private final ProjetoRepository projetoRepository;
    private final String nomeProjeto;

    public ExportarProjetoEstimativaPDFCommand(ProjetoRepository projetoRepository, String nomeProjeto) {
        this.projetoRepository = projetoRepository;
        this.nomeProjeto = nomeProjeto;
    }

    @Override
    public void execute() {
        try {
            // Buscar o projeto pelo nome
            Optional<ProjetoEstimativa> projetoOptional = projetoRepository.getProjetos().stream()
                    .filter(projeto -> projeto.getNome().equals(nomeProjeto))
                    .findFirst();

            if (projetoOptional.isPresent()) {
                ProjetoEstimativa projeto = projetoOptional.get();

                // Gerar o nome do arquivo PDF
                String nomeArquivo = "projeto_" + nomeProjeto.replaceAll("[^a-zA-Z0-9]", "_") + ".pdf";
                File arquivo = new File(nomeArquivo);

                // Criar o documento PDF
                try (PdfWriter writer = new PdfWriter(arquivo)) {
                    PdfDocument pdfDoc = new PdfDocument(writer);
                    Document document = new Document(pdfDoc);

                    // Título
                    Paragraph titulo = new Paragraph("Detalhes do Projeto: " + projeto.getNome())
                            .setBold()
                            .setFontSize(16)
                            .setTextAlignment(TextAlignment.CENTER);
                    document.add(titulo);

                    // Tabela de Dados Principais
                    float[] columnWidths = {2, 3};
                    Table table = new Table(columnWidths);
                    table.addCell("Campo").setBold();
                    table.addCell("Valor").setBold();

                    table.addCell("Nome");
                    table.addCell(projeto.getNome());
                    table.addCell("ID");
                    table.addCell(String.valueOf(projeto.getId()));
                    table.addCell("Perfil ID");
                    table.addCell(String.valueOf(projeto.getPerfilId()));
                    table.addCell("User ID");
                    table.addCell(String.valueOf(projeto.getUserId()));
                    table.addCell("Total Dias");
                    table.addCell(String.valueOf(projeto.getTotalDias()));
                    table.addCell("Valor Total");
                    table.addCell(String.valueOf(projeto.getValorTotal()));
                    table.addCell("Percentual Imposto");
                    table.addCell(String.valueOf(projeto.getPercentualImposto()));
                    table.addCell("Percentual Lucro");
                    table.addCell(String.valueOf(projeto.getPercentualLucro()));
                    table.addCell("Custo Hardware");
                    table.addCell(String.valueOf(projeto.getCustoHardware()));
                    table.addCell("Custo Software");
                    table.addCell(String.valueOf(projeto.getCustoSoftware()));
                    table.addCell("Custos Riscos");
                    table.addCell(String.valueOf(projeto.getCustosRiscos()));
                    table.addCell("Custo Garantia");
                    table.addCell(String.valueOf(projeto.getCustoGarantia()));
                    table.addCell("Fundo Reserva");
                    table.addCell(String.valueOf(projeto.getFundoReserva()));
                    table.addCell("Outros Custos");
                    table.addCell(String.valueOf(projeto.getOutrosCustos()));
                    table.addCell("Total Custo");
                    table.addCell(String.valueOf(projeto.getTotalCusto()));
                    table.addCell("Valor Imposto");
                    table.addCell(String.valueOf(projeto.getValorImposto()));
                    table.addCell("Valor Com Impostos");
                    table.addCell(String.valueOf(projeto.getValorComImpost()));
                    table.addCell("Valor Lucro");
                    table.addCell(String.valueOf(projeto.getValorLucro()));
                    table.addCell("Valor Com Lucro");
                    table.addCell(String.valueOf(projeto.getValorComLucro()));
                    table.addCell("Valor Final Cliente");
                    table.addCell(String.valueOf(projeto.getValorFinalDoCliente()));
                    table.addCell("Meses");
                    table.addCell(String.valueOf(projeto.getMeses()));
                    table.addCell("Média Por Mês");
                    table.addCell(String.valueOf(projeto.getMediaPorMes()));

                    document.add(table);

                    // Plataformas Selecionadas
                    StringJoiner plataformas = new StringJoiner(", ");
                    projeto.getPlatafomasSelecionadas().forEach(plataforma -> plataformas.add(plataforma.getNome()));
                    document.add(new Paragraph("\nPlataformas Selecionadas:").setBold());
                    document.add(new Paragraph(plataformas.toString()));

                    // Funcionalidades Selecionadas
                    StringJoiner funcionalidades = new StringJoiner(", ");
                    projeto.getFuncionalidadesSelecionadas().forEach(funcionalidade -> funcionalidades.add(funcionalidade.getNome()));
                    document.add(new Paragraph("\nFuncionalidades Selecionadas:").setBold());
                    document.add(new Paragraph(funcionalidades.toString()));

                    // Fechar o documento
                    document.close();
                }

                JOptionPane.showMessageDialog(
                        null,
                        "Arquivo PDF gerado com sucesso:\n" + arquivo.getAbsolutePath(),
                        "Exportação Concluída",
                        JOptionPane.INFORMATION_MESSAGE
                );

                System.out.println("Projeto exportado com sucesso para: " + arquivo.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Projeto não encontrado: " + nomeProjeto,
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (IOException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao exportar o projeto: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
