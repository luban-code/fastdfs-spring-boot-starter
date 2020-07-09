package com.cicoding.fastdfs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("fastdfs.config")
public class FastdfsConfig {
    private String configFile="fdfs_client.conf";

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }
}
