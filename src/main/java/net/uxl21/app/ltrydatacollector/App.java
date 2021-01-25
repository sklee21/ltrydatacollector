package net.uxl21.app.ltrydatacollector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	String drwNo = "", schVal = "";
    	
    	if( args.length == 2 ) {
    		drwNo = args[0];
    		schVal = args[1];
    		
    	} else if( args.length == 1 ) {
    		drwNo = args[0];
    	}
    	
    	ConfigSet configSet = ConfigSet.getInstance();
    	
		Connection connection = Jsoup.connect(configSet.getChildString("http", "lotteryUrl"));
		Document doc = null;
		
		try {
			HashMap<String, String> headers = new HashMap<>();
			headers.put("Content-Type", configSet.getChildString("http", "requestContentType"));
			
			connection.headers(headers);
			
			HashMap<String, String> params = new HashMap<>();
			params.put("method", configSet.getChildString("postParams", "method"));
			params.put("nowPage", configSet.getChildString("postParams", "nowPage"));
			params.put("rankNo", configSet.getChildString("postParams", "rankNo"));
			params.put("gameNo", configSet.getChildString("postParams", "gameNo"));
			params.put("drwNo", drwNo);
			params.put("schKey", configSet.getChildString("postParams", "schKey"));
			//params.put("schVal", "%C1%BE%B7%CE%B1%B8");
			params.put("schVal", schVal);
			
			connection.data(params);
			
			connection.postDataCharset(configSet.getChildString("http", "postDataCharset"))
					  .timeout(10 * 1000)
					  .userAgent(configSet.getChildString("http", "userAgent"))
					  .ignoreHttpErrors(true);
			doc = connection.post();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if( doc != null ) {
			ArrayList[] storeLists = {
				new ArrayList(), new ArrayList(), new ArrayList()
			};

			
			Elements groupContentDivs = doc.select(configSet.getChildString("html", "contentDiv"));
			//System.out.println(groupContentDivs);
			
			for( Element contentDiv : groupContentDivs ) {
				Elements titles = contentDiv.select(configSet.getChildString("html", "titleh"));
				int rankIndex = -1;
				
				if( titles.size() > 0 ) {
					//
					// 등수
					String titleText = titles.get(0).text();
					
					if( titleText.indexOf(configSet.getChildString("html", "1stLabel")) > -1 ) {
						rankIndex = 0;
						
					} else if( titleText.indexOf(configSet.getChildString("html", "2ndLabel")) > -1 ) {
						rankIndex = 1;
					
					} else {
						rankIndex = 2;
					}
				}
				
				
				//
				// 당첨점 내역
				Elements tables = contentDiv.select(configSet.getChildString("html", "storeTable"));
				
				for(Element table : tables) {
					Elements tbody = table.getElementsByTag("tbody");
					
					if( tbody.size() > 0 ) {
						Elements trs = tbody.get(0).children();
						
						for(Element tr : trs) {
							storeLists[rankIndex].add(tr.text());
						}
					}
				}
			}
			
			System.out.println(storeLists[0].toString());
			System.out.println(storeLists[1].toString());
		}
    }
    
}
