package br.projeto.command;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.repository.ProjetoRepository;
import com.opencsv.CSVWriter;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.StringJoiner;

public class ExportarProjetoEstimativaCSVCommand implements ProjetoCommand {

    private final ProjetoRepository projetoRepository;
    private final String nomeProjeto;

    public ExportarProjetoEstimativaCSVCommand(ProjetoRepository projetoRepository, String nomeProjeto) {
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

                // Gerar o nome do arquivo CSV
                String nomeArquivo = "projeto_" + nomeProjeto.replaceAll("[^a-zA-Z0-9]", "_") + ".csv";
                File arquivo = new File(nomeArquivo);

                // Escrever o CSV usando OpenCSV
                try (CSVWriter writer = new CSVWriter(
                        new FileWriter(arquivo),
                        ';', // Separador: ponto-e-vírgula
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END
                )) {
                    // Cabeçalho do CSV
                    String[] header = {
                            "Nome", "ID", "Perfil ID", "User ID", "Total Dias", "Valor Total", "Percentual Imposto",
                            "Percentual Lucro", "Custo Hardware", "Custo Software", "Custos Riscos", "Custo Garantia",
                            "Fundo Reserva", "Outros Custos", "Total Custo", "Valor Imposto", "Valor Com Impostos",
                            "Valor Lucro", "Valor Com Lucro", "Valor Final Cliente", "Meses", "Média Por Mês",
                            "Plataformas Selecionadas", "Funcionalidades Selecionadas"
                    };
                    writer.writeNext(header);

                    // Dados do projeto
                    StringJoiner plataformas = new StringJoiner(", ");
                    projeto.getPlatafomasSelecionadas().forEach(plataforma ->
                            plataformas.add(plataforma.getNome()));

                    StringJoiner funcionalidades = new StringJoiner(", ");
                    projeto.getFuncionalidadesSelecionadas().forEach(funcionalidade ->
                            funcionalidades.add(funcionalidade.getNome()));

                    String[] rowData = {
                            projeto.getNome(),
                            String.valueOf(projeto.getId()),
                            String.valueOf(projeto.getPerfilId()),
                            String.valueOf(projeto.getUserId()),
                            String.valueOf(projeto.getTotalDias()),
                            String.valueOf(projeto.getValorTotal()),
                            String.valueOf(projeto.getPercentualImposto()),
                            String.valueOf(projeto.getPercentualLucro()),
                            String.valueOf(projeto.getCustoHardware()),
                            String.valueOf(projeto.getCustoSoftware()),
                            String.valueOf(projeto.getCustosRiscos()),
                            String.valueOf(projeto.getCustoGarantia()),
                            String.valueOf(projeto.getFundoReserva()),
                            String.valueOf(projeto.getOutrosCustos()),
                            String.valueOf(projeto.getTotalCusto()),
                            String.valueOf(projeto.getValorImposto()),
                            String.valueOf(projeto.getValorComImpost()),
                            String.valueOf(projeto.getValorLucro()),
                            String.valueOf(projeto.getValorComLucro()),
                            String.valueOf(projeto.getValorFinalDoCliente()),
                            String.valueOf(projeto.getMeses()),
                            String.valueOf(projeto.getMediaPorMes()),
                            plataformas.toString(),
                            funcionalidades.toString()
                    };
                    writer.writeNext(rowData);
                }

               
                JOptionPane.showMessageDialog(
                        null,
                        "Arquivo CSV gerado com sucesso:\n" + arquivo.getAbsolutePath(),
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