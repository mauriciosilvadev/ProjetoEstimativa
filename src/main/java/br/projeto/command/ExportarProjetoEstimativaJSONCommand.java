package br.projeto.command;

import br.projeto.model.ProjetoEstimativa;
import br.projeto.repository.ProjetoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.JOptionPane;
import java.io.File;
import java.util.Optional;

public class ExportarProjetoEstimativaJSONCommand implements ProjetoCommand {

    private final ProjetoRepository projetoRepository;
    private final String nomeProjeto;

    public ExportarProjetoEstimativaJSONCommand(ProjetoRepository projetoRepository, String nomeProjeto) {
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

                // Converter o projeto para JSON usando Jackson
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonFormatado = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(projeto);

                // Salvar o JSON em um arquivo
                String nomeArquivo = "projeto_" + nomeProjeto.replaceAll("[^a-zA-Z0-9]", "_") + ".json";
                File arquivo = new File(nomeArquivo);
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, projeto);

                
                JOptionPane.showMessageDialog(
                        null,
                        "Arquivo JSON gerado com sucesso:\n" + arquivo.getAbsolutePath(),
                        "Exportação Concluída",
                        JOptionPane.INFORMATION_MESSAGE
                );

                System.out.println("JSON Formatado:");
                 
            } else {
                
                JOptionPane.showMessageDialog(
                        null,
                        "Projeto não encontrado: " + nomeProjeto,
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
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