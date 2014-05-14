package dmk.pastebin.conf

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import dmk.pastebin.service.PastebinServiceImpl
import dmk.pastebin.service.PastebinService


@Configuration
@Import(
    Array(classOf[PropertyPlaceholderConfig])
    )
class BootstrapConfig(var pasteUser: String, var pastePass: String,
    var pasteDevelKey: String, var pasteHost: String){
	val logger: Logger = LoggerFactory.getLogger(classOf[BootstrapConfig])

	def this() = { 
	  this("", "", "", "")
	}
	
//	var pasteUser: String = null
//	var pastePass: String = null
//	var pasteDevelKey: String = null
//	var pasteHost: String = null
	
	@Value("${dmk.pastebin.user}")
	def setPasteUser(tmp: String){
	  this.pasteUser = tmp
	}
	
	@Value("${dmk.pastebin.pass}")
	def setPastePass(tmp: String){
	  this.pastePass = tmp
	}
	
	@Value("${dmk.pastebin.develkey}")
	def setPasteDevelKey(tmp: String){
	  this.pasteDevelKey= tmp
	}

	@Value("${dmk.pastebin.host}")
	def setPasteHost(tmp: String){
	  this.pasteHost = tmp
	}
	@Bean def pastebinUser(): String = {
		this.pasteUser
	}
	
	@Bean def pastebinHost(): String = {
		this.pasteHost
	}
	
	@Bean def pastebinPass(): String = {
	  this.pastePass
	}
	
	@Bean def pastebinDevelKey(): String = {
	  this.pasteDevelKey
	}
	
	@Bean def pastebinService: PastebinService = {
	  val pastebinService = new PastebinServiceImpl(
	      this.pastebinUser(),
	      this.pastebinPass(),
	      this.pastebinHost(),
	      this.pastebinDevelKey())
	  return pastebinService
	}
}