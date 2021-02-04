package net.uxl21.app.ltrydatacollector;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class LotteryStoreDataRequest {
	
	
	private ConfigSet configSet = ConfigSet.getInstance();
	
	private HashMap<String, String> headers = null;
	
	private HashMap<String, String> params = null;
	
	
	

	public LotteryStoreDataRequest() {
		this.headers = new HashMap<>();
		this.headers.put("Content-Type", this.configSet.getChildString("http", "requestContentType"));
		
		this.params = new HashMap<>();
		this.params.put("method", this.configSet.getChildString("postParams", "method"));
		this.params.put("nowPage", this.configSet.getChildString("postParams", "nowPage"));
		this.params.put("rankNo", this.configSet.getChildString("postParams", "rankNo"));
		this.params.put("gameNo", this.configSet.getChildString("postParams", "gameNo"));
		this.params.put("schKey", this.configSet.getChildString("postParams", "schKey"));
	}
	
	
	
	public Document request(String drwNo, String schVal) throws IOException {
		this.params.put("drwNo", drwNo);
		this.params.put("schVal", schVal);
		
		Connection connection = Jsoup.connect(this.configSet.getChildString("http", "lotteryUrl"));
		connection.headers(this.headers);
		connection.data(this.params);
		connection.postDataCharset(this.configSet.getChildString("http", "postDataCharset"))
				  .timeout(this.configSet.getChildInteger("http", "timeout"))
				  .userAgent(this.configSet.getChildString("http", "userAgent"))
				  .ignoreHttpErrors(this.configSet.getChildBoolean("http", "ignoreHttpError"));
		
		return connection.post();
	}

}
