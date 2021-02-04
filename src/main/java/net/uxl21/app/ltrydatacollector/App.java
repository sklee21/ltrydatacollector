package net.uxl21.app.ltrydatacollector;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.nodes.Document;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) throws IOException {
    	String drwNo = "", schVal = "";
    	
    	if( args.length == 2 ) {
    		schVal = args[0];
    		drwNo = args[1];
    		
    	} else if( args.length == 1 ) {
    		schVal = args[0];
    	}
    	
    	ArrayList<LotteryStoreData> firstPlaces = new ArrayList<>();
    	ArrayList<LotteryStoreData> secondPlaces = new ArrayList<>();
    	
		Document doc = null;
		
		for( int i=1; i<949; i++ ) {
			System.out.println("---------------------");
			
			try {
				drwNo = Integer.toString(i);
				
				System.out.printf("Request: drwNo=%s, schVal=%s\n", drwNo, schVal);
				LotteryStoreDataRequest request = new LotteryStoreDataRequest();
				doc = request.request(drwNo, schVal);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			if( doc != null ) {
				LotteryStoreHTMLDataParser parser = new LotteryStoreHTMLDataParser(doc, true);
				ArrayList<LotteryStoreData>[] storeLists = parser.parse();
				
				firstPlaces.addAll(storeLists[0]);
				secondPlaces.addAll(storeLists[1]);
				
				System.out.printf("Result: 1st=%d, 2nd=%d\n", storeLists[0].size(), storeLists[1].size());
			}
			System.out.println("---------------------\n");
		}
		
		
		System.out.println("======= [First Places] =======");
		for( LotteryStoreData storeData : firstPlaces ) {
	    	System.out.println(storeData.toString());
		}
		System.out.println("=============================\n");
		
		System.out.println("======= [Second Places] =======");
		for( LotteryStoreData storeData : secondPlaces ) {
	    	System.out.println(storeData.toString());
		}
		System.out.println("===============================");
    }
    
}
