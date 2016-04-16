package obtain_logistics_info;

import java.util.List;

public class Test {
	public static void main(String[] args) {
		List<Integer> list = new java.util.ArrayList<>();
		for(int i=0;i<1001;i++){
			list.add(i);
		}
		for(int i = 0;i<list.size();i+=100){
			List  subList = null;
			if((i+100)>list.size()){
				subList = list.subList(i,list.size());
			}else{
				subList = list.subList(i,i+100);
			}
				System.out.println(i+"---"+list.size()+"--------"+(i+subList.size()));
		}
	}
}
