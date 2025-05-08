package com.integration.nasajon.service;

import com.integration.nasajon.config.IntegrationConfig;
import com.integration.nasajon.model.LancamentoContabil;
import com.integration.nasajon.util.FtpUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIntegrationService implements IntegrationService {
    private static final Logger logger = Logger.getLogger(FileIntegrationService.class.getName());
    
    private final IntegrationConfig config;
    
    public FileIntegrationService(IntegrationConfig config) {
        this.config = config;
        
        // Garantir que o diretório de saída existe
        try {
            Files.createDirectories(Paths.get(config.getOutputDirectory()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao criar diretório de saída", e);
        }
    }
    
    @Override
    public boolean enviarLote(List<LancamentoContabil> lancamentos) {
        try {
            logger.info("Iniciando geração de arquivo para lote. Total de lançamentos: " + lancamentos.size());
            
            String fileName = gerarNomeArquivo();
            String filePath = config.getOutputDirectory() + File.separator + fileName;
            
            // Gerar arquivo de acordo com o formato configurado
            switch (config.getFileFormat().toLowerCase()) {
                case "csv":
                    gerarArquivoCsv(lancamentos, filePath);
                    break;
                case "xml":
                    gerarArquivoXml(lancamentos, filePath);
                    break;
                case "json":
                    gerarArquivoJson(lancamentos, filePath);
                    break;
                default:
                    logger.warning("Formato de arquivo não suportado: " + config.getFileFormat());
                    return false;
            }
            
            // Enviar arquivo via FTP se configurado
            if (config.isUseFtp()) {
                return FtpUtil.enviarArquivo(
                        config.getFtpHost(),
                        config.getFtpUser(),
                        config.getFtpPassword(),
                        filePath,
                        fileName
                );
            }
            
            logger.info("Arquivo gerado com sucesso: " + filePath);
            return true;
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao gerar arquivo para lote", e);
            return false;
        }
    }
    
    private String gerarNomeArquivo() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return "lancamentos_contabeis_" + timestamp + "." + config.getFileFormat().toLowerCase();
    }
    
    private void gerarArquivoCsv(List<LancamentoContabil> lancamentos, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Cabeçalho
            writer.append("Conta;Historico;Valor;Data;Natureza\n");
            
            // Dados
            for (LancamentoContabil lancamento : lancamentos) {
                writer.append(lancamento.getConta()).append(";")
                      .append(lancamento.getHistorico()).append(";")
                      .append(String.valueOf(lancamento.getValor())).append(";")
                      .append(lancamento.getData()).append(";")
                      .append(lancamento.getNatureza()).append("\n");
            }
        }
    }
    
    private void gerarArquivoXml(List<LancamentoContabil> lancamentos, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.append("<lancamentos>\n");
            
            for (LancamentoContabil lancamento : lancamentos) {
                writer.append("  <lancamento>\n");
                writer.append("    <conta>").append(lancamento.getConta()).append("</conta>\n");
                writer.append("    <historico>").append(lancamento.getHistorico()).append("</historico>\n");
                writer.append("    <valor>").append(String.valueOf(lancamento.getValor())).append("</valor>\n");
                writer.append("    <data>").append(lancamento.getData()).append("</data>\n");
                writer.append("    <natureza>").append(lancamento.getNatureza()).append("</natureza>\n");
                writer.append("  </lancamento>\n");
            }
            
            writer.append("</lancamentos>");
        }
    }
    
    private void gerarArquivoJson(List<LancamentoContabil> lancamentos, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("{\n");
            writer.append("  \"lancamentos\": [\n");
            
            for (int i = 0; i < lancamentos.size(); i++) {
                LancamentoContabil lancamento = lancamentos.get(i);
                writer.append("    {\n");
                writer.append("      \"conta\": \"").append(lancamento.getConta()).append("\",\n");
                writer.append("      \"historico\": \"").append(lancamento.getHistorico()).append("\",\n");
                writer.append("      \"valor\": ").append(String.valueOf(lancamento.getValor())).append(",\n");
                writer.append("      \"data\": \"").append(lancamento.getData()).append("\",\n");
                writer.append("      \"natureza\": \"").append(lancamento.getNatureza()).append("\"\n");
                writer.append("    }").append(i < lancamentos.size() - 1 ? "," : "").append("\n");
            }
            
            writer.append("  ]\n");
            writer.append("}");
        }
    }
    
    @Override
    public String verificarStatusLote(String loteId) {
        // Para integração baseada em arquivos, o status pode ser verificado
        // através de um arquivo de resposta ou outro mecanismo
        logger.info("Verificação de status não implementada para integração baseada em arquivos");
        return "DESCONHECIDO";
    }
}
