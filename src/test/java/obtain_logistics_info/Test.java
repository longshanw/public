package obtain_logistics_info;

import java.text.SimpleDateFormat;

public class Test {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for(int i=0;i<=1000000;i++){
			i += (i/2);
		}
		long endTime = System.currentTimeMillis();
		System.out.println((endTime-startTime)/1000000);
	}
}
