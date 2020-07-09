package com.cicoding.fastdfs;

import com.cicoding.fastdfs.config.FastdfsConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties(FastdfsConfig.class)
@ComponentScan(basePackages="com.cicoding.fastdfs")
public class FastdfsStarter {
}
