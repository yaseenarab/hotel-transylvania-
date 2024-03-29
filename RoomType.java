package edu.baylor.hoteltransylvania;

// Family, Double, Single, Deluxe, Suite, Standard
public enum RoomType {
    FM, DB, SN, DL, SU, ST;
    
    public static RoomType getEnum(String str) {
    	str = str.toLowerCase();
    	if (str.contains("fam")) {
    		return RoomType.FM;
    	} else if (str.contains("dou")) {
    		return RoomType.DB;
    	} else if (str.contains("sin")) {
    		return RoomType.SN;
    	} else if (str.contains("del")) {
    		return RoomType.DL;
    	} else if (str.contains("su")) {
    		return RoomType.SU;
    	} else if (str.contains("st")){
    		return RoomType.ST;
    	} else {
    		throw new IllegalArgumentException("Invalid RoomType");
    	}
    }
}
