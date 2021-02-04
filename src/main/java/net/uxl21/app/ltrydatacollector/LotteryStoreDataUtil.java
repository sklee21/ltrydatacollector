package net.uxl21.app.ltrydatacollector;

import java.util.ArrayList;

public class LotteryStoreDataUtil {
	
	private static ConfigSet configSet = ConfigSet.getInstance();
	
	

	public static LotteryStoreData toLotteryStoreData(String strData, boolean isFirstPlace) {
		String cleaned = strData;
		String[] dataToClean = configSet.getList("dataToClean");
		
		for( String data : dataToClean ) {
			cleaned = cleaned.replaceAll(data, "");
		}
		
    	int firstWs = cleaned.indexOf(" ");
    	int secondWs = cleaned.indexOf(" ", firstWs + 1);
    	int thirdWs = cleaned.indexOf(" ", secondWs + 1);
    	
    	String no = cleaned.substring(0, firstWs);
    	String storeName = cleaned.substring(firstWs + 1, secondWs);
    	String winType = null;
    	String address = null;
    	
    	if( isFirstPlace ) {
        	winType = cleaned.substring(secondWs + 1, thirdWs);
        	address = cleaned.substring(thirdWs + 1);
    	
    	} else {
    		winType = "";
    		address = cleaned.substring(secondWs + 1);
    	}
    	
    	LotteryStoreData storeData = new LotteryStoreData();
    	storeData.setNo(no);
    	storeData.setStoreName(storeName);
    	storeData.setWinType(winType);
    	storeData.setAddress(address);
    	
		return storeData;
	}
	
	
	
	public static void distinct(ArrayList<LotteryStoreData> list) {
		int size = list.size();
		LotteryStoreData element;
		
		for( LotteryStoreData storeData : list ) {
			for( int i=0; i<size; i++ ) {
				element = list.get(i);
				
				if( storeData.equals(element) ) {
					element.setChecked(storeData != element);
				}
			}
		}
		
		for( int i=size-1; i>=0; i-- ) {
			if( list.get(i).isChecked() ) {
				list.remove(i);
			}
		}
	}

}
