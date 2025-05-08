package com.integration.nasajon.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegrationConfig {
    private static final Logger logger = Logger.getLogger(IntegrationConfig.class.getName());
    
    private String integrationType;
    private String apiUrl;
    private String apiToken;
    private String outputDirectory;
    private String fileFormat;
    private String ftpHost;
    private String ftpUser;
    private String ftpPassword;
    private boolean useFtp;
    private int batchSize;
    private int retryAttempts;
    
    public static IntegrationConfig load(String configFile) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            props.load(fis);
        }
        
        IntegrationConfig config = new IntegrationConfig();
        config.setIntegrationType(props.getProperty("integration.type", "api"));
        config.setApiUrl(props.getProperty("api.url", ""));
        config.setApiToken(props.getProperty("api.token", ""));
        config.setOutputDirectory(props.getProperty("file.output.directory", "./output"));
        config.setFileFormat(props.getProperty("file.format", "csv"));
        config.setFtpHost(props.getProperty("ftp.host", ""));
        config.setFtpUser(props.getProperty("ftp.user", ""));
        config.setFtpPassword(props.getProperty("ftp.password", ""));
        config.setUseFtp(Boolean.parseBoolean(props.getProperty("ftp.use", "false")));
        config.setBatchSize(Integer.parseInt(props.getProperty("batch.size", "100")));
        config.setRetryAttempts(Integer.parseInt(props.getProperty("retry.attempts", "3")));
        
        logger.log(Level.INFO, "Configuração carregada: tipo={0}", config.getIntegrationType());
        return config;
    }

    // Getters e Setters
    public String getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(String integrationType) {
        this.integrationType = integrationType;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public boolean isUseFtp() {
        return useFtp;
    }

    public void setUseFtp(boolean useFtp) {
        this.useFtp = useFtp;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(int retryAttempts) {
        this.retryAttempts = retryAttempts;
    }
}
