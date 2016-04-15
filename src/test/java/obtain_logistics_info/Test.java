package obtain_logistics_info;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<Integer> list = new java.util.ArrayList<>();
		for(int i=0;i<1000;i++){
			list.add(i);
		}
		for(int i = 0;i<list.size();i+=100){
		    List  newlist = list.subList(i,i+99);
		   System.out.println(list.size()+"---"+i+"--"+(i+newlist.size()));
		}
	}
}
