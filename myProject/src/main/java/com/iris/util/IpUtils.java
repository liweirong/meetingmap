package com.iris.util;
public class IpUtils {
	
	public static String getLongIpToString(Long ipLong){
		if(ipLong == null || ipLong == 0) return null;
        StringBuilder sb = new StringBuilder();
        sb.append(ipLong>>>24);
        sb.append(".");
        sb.append(String.valueOf((ipLong&0x00FFFFFF)>>>16));
        sb.append(".");
        sb.append(String.valueOf((ipLong&0x0000FFFF)>>>8));
        sb.append(".");
        sb.append(String.valueOf(ipLong&0x000000FF));
        return sb.toString();
    }
    
    public static Long getStringIpToLong(String ipString){
    	if(ipString ==null ||"".equals(ipString)) return 0L;
        long result = 0;
        java.util.StringTokenizer token = new java.util.StringTokenizer(ipString,".");
        result += Long.parseLong(token.nextToken())<<24;
        result += Long.parseLong(token.nextToken())<<16;
        result += Long.parseLong(token.nextToken())<<8;
        result += Long.parseLong(token.nextToken());
        return result;
    }
    
    public static byte[] intToByteArray(int value){
        byte[] b = new byte[4];
        byte[] a=new byte[4];
         for (int i = 0; i < 4; i++) {
                int offset = (b.length - 1 - i) * 8;
                 b[i] = (byte) ((value >>> offset) & 0xFF);
          }
         int k=3;
         for(int j=0;j<4;j++)
         {
        	 a[j]=b[k];
        	 k--;
         }
          return a;
       }
    
    public static byte[]  intToByte(int intValue) {
        byte[] result = new byte[4];
        result[0] = (byte) ( (intValue & 0xFF000000) >> 24);
        result[1] = (byte) ( (intValue & 0x00FF0000) >> 16);
        result[2] = (byte) ( (intValue & 0x0000FF00) >> 8);
        result[3] = (byte) ( (intValue & 0x000000FF));
        return result;
      }

	public static String checkIp(String tString) {
		int index = tString.indexOf("...");
		if (index == 0 || "0.0.0".equals(tString)) {
			tString = "0.0.0.0";
		} else {
			do {
				index = tString.indexOf("..");
				StringBuilder temp = new StringBuilder();
				if (index != -1) {
					temp.append(tString.substring(0, index + 1));
					temp.append("0");
					temp.append(tString.substring(index + 1));
					tString = temp.toString();
				}
				if (tString.startsWith(".")) {
					tString = "0" + tString;
				}
				if (tString.endsWith(".")) {
					tString = tString + "0";
				}
			} while (index != -1);

		}
		return tString;
	}
    
}