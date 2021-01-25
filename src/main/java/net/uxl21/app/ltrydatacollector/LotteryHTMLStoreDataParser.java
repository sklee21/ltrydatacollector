package net.uxl21.app.ltrydatacollector;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LotteryHTMLStoreDataParser {
	
	private Document rootDoc = null;
	
	private ConfigSet configSet = null;
	

	public LotteryHTMLStoreDataParser(Document rootDoc) {
		this.rootDoc = rootDoc;
		this.configSet = ConfigSet.getInstance();
	}
	
	
	public ArrayList[] parse() {
		ArrayList[] storeLists = {
			new ArrayList(), new ArrayList(), new ArrayList()
		};

		
		Elements groupContentDivs = this.rootDoc.select(configSet.getChildString("html", "contentDiv"));
		
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
		
		return storeLists;
	}
	
}
