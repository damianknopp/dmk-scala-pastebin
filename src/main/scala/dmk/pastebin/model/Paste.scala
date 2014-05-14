package dmk.pastebin.model

import java.util.Date
import scala.xml.Elem
import scala.xml.Node
import org.slf4j.LoggerFactory

case class Paste(val key: String,
		val title: String,
		val size: Int,
		val pasteDate: Date,
		val pasteExpire: Date,
		val pastePrivate: Int,
		val pasteFormateShort: String,
		val pasteUrl: String,
		val pasteHits: Int){
  
}

object Paste{
  val logger = LoggerFactory.getLogger(classOf[Paste])
  
  def parsePasteXml(paste: String): Paste = {
    val xml: Elem = scala.xml.XML.loadString(paste)
    return parsePaste(xml)
  }

  def parsePaste(paste: Node): Paste = {
    val pasteKey = (paste \ "paste_key").text
    logger.trace("found key = " + pasteKey)
    val date = new Date((paste \ "paste_date").text.toInt)
    val title = (paste \ "paste_title").text
    val size = (paste \ "paste_size").text.toInt
    val expireDate = new Date((paste \ "paste_expire_date").text.toInt)
    val privatePaste = (paste \ "paste_private").text.toInt
    val formatLong = (paste \ "paste_format_long").text
    val formatShort = (paste).text
    var url = (paste \ "paste_url").text
    var hits = (paste \ "paste_hits").text.toInt
    return Paste(pasteKey, title, size, date, expireDate, 
        privatePaste, formatShort, url, hits)
  }

  def parsePastesXml(pastes: String): Seq[Paste] = {
    logger.trace(pastes)
    val pasteWraper = "<pastes>" + pastes + "</pastes>"
    val nodeList = scala.xml.XML.loadString(pasteWraper) \ "paste"
    logger.trace(nodeList.toString)
    val pasteArr: Seq[Paste] = nodeList.map( { (node: Node) =>
      Paste.parsePaste(node)
    })
    return pasteArr
  }

}