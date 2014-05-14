package dmk.pastebin.conf

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig
import org.jasypt.spring31.properties.EncryptablePropertyPlaceholderConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.slf4j.LoggerFactory
import org.slf4j.Logger


@Configuration
class PropertyPlaceholderConfig {} 

object PropertyPlaceholderConfig {
//	val logger: Logger = LoggerFactory.getLogger(classOf[PropertyPlaceholderConfig]);

	val locations: Array[ClassPathResource] = Array(new ClassPathResource("dmk-pastebin.properties"))
	
	@Bean
	def environmentStringPBEConfig(): EnvironmentStringPBEConfig = {
		val tmp =  new EnvironmentStringPBEConfig()
		tmp.setAlgorithm("PBEWithMD5AndDES")
		val pass = "dmk-salt"
		tmp.setPasswordCharArray(pass.toCharArray())
		return tmp
	}
	
	@Bean
	def configurationEncryptor(): StandardPBEStringEncryptor ={
		val standardPBEStringEncryptor: StandardPBEStringEncryptor = new StandardPBEStringEncryptor();
		standardPBEStringEncryptor.setConfig(environmentStringPBEConfig());
		return standardPBEStringEncryptor;
	}
	
	@Bean
	def encryptablePropertyPlaceholderConfigurer(): EncryptablePropertyPlaceholderConfigurer = {
		val configurer =  new EncryptablePropertyPlaceholderConfigurer(configurationEncryptor());
		configurer.setLocations(locations.toArray);
		return configurer;
	}
	
}