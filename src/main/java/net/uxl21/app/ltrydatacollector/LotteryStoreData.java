package net.uxl21.app.ltrydatacollector;

import java.util.Objects;

public class LotteryStoreData {
	
	private boolean isChecked = false;
	
	
	private String drwNo = null;
	
	private String no = null;

	private String storeName = null;
	
	private String winType = null;
	
	private String address = null;
	
	
	
	public LotteryStoreData() {
		
	}


	
	
	

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}


	
	public String getDrwNo() {
		return drwNo;
	}

	public void setDrwNo(String drwNo) {
		this.drwNo = drwNo;
	}


	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}


	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	

	public String getWinType() {
		return winType;
	}

	public void setWinType(String winType) {
		this.winType = winType;
	}

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
	
	@Override
	public boolean equals(Object obj) {
		if( obj instanceof LotteryStoreData ) {
			LotteryStoreData storeData = this.getClass().cast(obj);
			
			return Objects.equals(this.storeName, storeData.getStoreName()) && Objects.equals(this.address, storeData.getAddress());

		} else {
			return false;
		}
	}


	@Override
	public String toString() {
		return String.format("{ drwNo=%s, no=%s, storeName=%s, winType=%s, address=%s }", this.drwNo, this.no, this.storeName, this.winType, this.address);
	}
	
}
