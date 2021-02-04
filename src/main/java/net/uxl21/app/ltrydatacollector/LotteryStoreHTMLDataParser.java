package net.uxl21.app.ltrydatacollector;

import java.util.ArrayList;
import java.util.Objects;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LotteryStoreHTMLDataParser {
	
	private Document rootDoc = null;
	
	private ConfigSet configSet = null;
	
	
	private boolean distinct = false;
	
	
	private String noDataText = null;
	
	
	

	public LotteryStoreHTMLDataParser(Document rootDoc, boolean distinct) {
		this.rootDoc = rootDoc;
		this.configSet = ConfigSet.getInstance();
		this.distinct = distinct;
		this.noDataText = this.configSet.getString("noDataText");
	}
	
	
	public LotteryStoreHTMLDataParser(Document rootDoc) {
		this(rootDoc, false);
	}
	
	
	
	public ArrayList<LotteryStoreData>[] parse() {
		@SuppressWarnings("unchecked")
		ArrayList<LotteryStoreData>[] storeLists = new ArrayList[3];
		storeLists[0] = new ArrayList<LotteryStoreData>();
		storeLists[1] = new ArrayList<LotteryStoreData>();
		storeLists[2] = new ArrayList<LotteryStoreData>();

		
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
			String trText;
			
			for(Element table : tables) {
				Elements tbody = table.getElementsByTag("tbody");
				
				if( tbody.size() > 0 ) {
					Elements trs = tbody.get(0).children();
					
					for(Element tr : trs) {
						trText = tr.text();
						
						if( !Objects.equals(trText, this.noDataText) ) {
							storeLists[rankIndex].add( LotteryStoreDataUtil.toLotteryStoreData(trText, (rankIndex == 0)) );
						}
					}
				}
			}
			
			
			//
			// distinct
			if( this.distinct ) {
				LotteryStoreDataUtil.distinct(storeLists[0]);
				LotteryStoreDataUtil.distinct(storeLists[1]);
				LotteryStoreDataUtil.distinct(storeLists[2]);
			}
		}
		
		return storeLists;
	}
	
}
