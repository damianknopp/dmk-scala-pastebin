package dmk.pastebin.service

import java.util.ArrayList
import org.apache.commons.io.IOUtils
import org.apache.http.Consts
import org.apache.http.NameValuePair
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import dmk.pastebin.model.Paste
import java.io.IOException
import org.apache.http.client.methods.CloseableHttpResponse


trait PastebinService{
	def fetchTrends: Array[Paste]
	def getTrends: String
}


class PastebinServiceImpl(val user: String, val pass: String, 
			val host: String, val develKey: String) extends PastebinService{

    override def fetchTrends: Array[Paste] = {
		  Array()
  	}
  
	def getTrends(): String = {
		val httpPost = new HttpPost(this.host + "/api/api_post.php");
		val nvps = new ArrayList[NameValuePair]();
		nvps.add(new BasicNameValuePair("api_option", "trends"));
		nvps.add(new BasicNameValuePair("api_dev_key", this.develKey));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		var httpClient: CloseableHttpClient = null
		var response: CloseableHttpResponse = null
		try {
			httpClient = this.createClient();
			response = httpClient.execute(httpPost);
			val entity = response.getEntity();
			val respData = IOUtils.toString(entity.getContent());
			EntityUtils.consume(entity);
			respData
		} catch {
		  case e: IOException => throw new RuntimeException(e);
		} finally{
			IOUtils.closeQuietly(response);
			IOUtils.closeQuietly(httpClient);
		}
	}

	def createClient(): CloseableHttpClient = {
		val credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(this.user, this.pass));
		val httpClient = HttpClients.custom()
				.setDefaultCredentialsProvider(credsProvider).build();
		return httpClient;
	}
	
}