package dmk.pastebin.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.Test
import dmk.pastebin.conf.BootstrapConfig
import org.junit.Ignore

@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(classes=Array(classOf[BootstrapConfig]))
class PastebinServiceTest{
  var logger : Logger = LoggerFactory.getLogger(getClass())
  
  @Autowired
  var pastebinService: PastebinService = null


  @Test
  @Ignore
  def canary() = {
    logger.debug("canary test")
	val resp = pastebinService.getTrends
	logger.debug(resp.size.toString)
	logger.debug(resp)
//	resp.foreach { (el) => logger.debug(el) }
  }
  
}