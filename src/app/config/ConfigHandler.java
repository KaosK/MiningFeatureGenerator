package config;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigHandler {
	
	private Configurations configs;
	private FileBasedConfigurationBuilder<PropertiesConfiguration> builder;
	private FileBasedConfiguration config;

	public ConfigHandler(Path configFile) {
		if (Files.notExists(configFile)) {
			try {
				Files.createFile(configFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.configs = new Configurations();
		this.builder = configs.propertiesBuilder(configFile.toFile());
		
	}
	
	public void load() throws ConfigurationException {
		this.config = builder.getConfiguration();
	}

	public void save() throws ConfigurationException {
		builder.save();
	}

	public String get(String key, String defaultValue) {
		String value = config.getString(key, defaultValue);
		config.setProperty(key, value);
		return value;
	}
	
	

}
