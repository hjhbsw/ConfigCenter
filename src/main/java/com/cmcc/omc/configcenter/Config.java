package com.cmcc.omc.configcenter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "coreconfig")
@Component
@Data
public class Config {

	private String tempPath;
}
