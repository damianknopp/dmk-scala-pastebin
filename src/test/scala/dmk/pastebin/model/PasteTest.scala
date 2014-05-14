package dmk.pastebin.model

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

class PasteTest{
  val logger = LoggerFactory.getLogger(classOf[PasteTest])
  
  @Before
  def setup(): Unit = { }
  
  @Test 
  def parseSingle() = {
    val singlePaste = PasteTestData.paste
    val el = Paste.parsePasteXml(singlePaste)
    logger.debug(el.toString)
    
    pasteOneAssertions(el)
  }
  
  @Test
  def parseArr() = {
    val pastes = PasteTestData.pastes
    
    val seq: Seq[Paste] = Paste.parsePastesXml(pastes)
    for(paste <- seq; i <- 1 to seq.size){
      assertNotNull(paste.key)
    }
    
    pasteOneAssertions(seq.head)
  }
  
  def pasteOneAssertions(paste: Paste): Unit = {
    assertEquals("vKz5ruMX", paste.key)
    assertEquals("http://pastebin.com/vKz5ruMX", paste.pasteUrl)
    assertEquals(7874, paste.pasteHits)
    assertEquals("Isabelita", paste.title)
  }
}