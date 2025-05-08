package com.integration.nasajon;

import com.integration.nasajon.config.IntegrationConfig;
import com.integration.nasajon.model.LancamentoContabil;
import com.integration.nasajon.service.ApiIntegrationService;
import com.integration.nasajon.service.FileIntegrationService;
import com.integration.nasajon.service.IntegrationService;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NasajonIntegrationApp {
    private static final Logger logger = Logger.getLogger(NasajonIntegrationApp.class.getName());

    public static void main(String[] args) {
        try {
            // Carregar configurações
            IntegrationConfig config = IntegrationConfig.load("config.properties");
            
            // Criar serviço de integração baseado na configuração
            IntegrationService service = createIntegrationService(config);
            
            // Dados de exemplo (em produção, estes viriam do seu sistema)
            List<LancamentoContabil> lancamentos = criarLancamentosExemplo();
            
            // Executar integração
            boolean success = service.enviarLote(lancamentos);
            
            if (success) {
                logger.info("Integração concluída com sucesso!");
            } else {
                logger.warning("Integração concluída com avisos. Verifique os logs.");
            }
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro durante a integração", e);
            System.exit(1);
        }
    }
    
    private static IntegrationService createIntegrationService(IntegrationConfig config) {
        switch (config.getIntegrationType().toLowerCase()) {
            case "api":
                return new ApiIntegrationService(config);
            case "file":
                return new FileIntegrationService(config);
            default:
                throw new IllegalArgumentException("Tipo de integração não suportado: " + config.getIntegrationType());
        }
    }
    
    private static List<LancamentoContabil> criarLancamentosExemplo() {
        return Arrays.asList(
            new LancamentoContabil("1.1.1.01", "Lançamento de Abertura", 1000.00, "2023-05-08", "D"),
            new LancamentoContabil("2.1.1.01", "Pagamento de Fornecedor", 500.00, "2023-05-08", "C"),
            new LancamentoContabil("3.1.1.01", "Receita de Vendas", 1500.00, "2023-05-08", "C")
        );
    }
}
