package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StringUtils {

	public static void main(String[] args) {
		System.out.println(charOrder("123abcefgeac212", "DESC"));
		System.out.println(wordOrder("i am fine think you  fine think you", "ASC"," "));
	}
	
	/**
	 * 
	 * @param sourceStr  需要排序的字符串
	 * @param order排序方式;DESC降序；ASC升序
	 * @return
	 */
	public static String charOrder(String sourceStr,String order){
		if(null==order||order.trim().length()==0){
			order="ASC";
		}
		Map<Character, Integer> map=new HashMap<Character, Integer>();
		char [] c1=sourceStr.toCharArray();
		//计算相同字符出现的次数 并把他们put到map里边去
		for (char cv1 : c1) {
			for (char cv2 : c1) {
				if(cv1==cv2){
					Integer val= map.get(cv1)==null?1: map.get(cv1)+1;
					map.put(cv1, val);
					break;
				}
			}
		}
		//把map转换成list
		List<Map.Entry<Character, Integer>> entryList = new ArrayList<Map.Entry<Character, Integer>>(map.entrySet());
		//根据出现次数的多少排序
		Collections.sort(entryList, new Comparator<Map.Entry<Character, Integer>>() {
			@Override
			public int compare(Entry<Character, Integer> o1,
					Entry<Character, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		StringBuffer finalString=new StringBuffer();
		if("DESC".equals(order.trim())){
			//降序组装
			for (int i = entryList.size(); i >0; i--) {
				Character key= entryList.get(i-1).getKey();
				Integer value=entryList.get(i-1).getValue();
				for (int j = 0; j < value; j++) {
					finalString.append(key);
				}
			}
		}else{
			//升序组装
			for (int i = 0; i < entryList.size(); i++) {
				Character key= entryList.get(i).getKey();
				Integer value=entryList.get(i).getValue();
				for (int j = 0; j < value; j++) {
					finalString.append(key);
				}
			}
		}
		return finalString.toString();
	}
	
	/**
	 * 
	 * @param sourceStr 需要排序的字符串
	 * @param order		order排序方式;DESC降序；ASC升序
	 * @param split		分隔符
	 * @return
	 */
	public static String wordOrder(String sourceStr,String order,String split){
		if(null==order||order.trim().length()==0){
			order="ASC";
		}
		if(null==split||split.trim().length()==0){
			split=" ";
		}
		Map<String, Integer> map=new HashMap<String, Integer>();
		String [] strArray=sourceStr.split(split);
		//计算相同字符出现的次数 并把他们put到map里边去
		for (String cv1 : strArray) {
			for (String cv2 : strArray) {
				if(cv1.equals(cv2)){
					Integer val= map.get(cv1)==null?1: map.get(cv1)+1;
					map.put(cv1, val);
					break;
				}
			}
		}
		//把map转换成list
		List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		//根据出现次数的多少排序
		Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		StringBuffer finalString=new StringBuffer();
		if("DESC".equals(order.trim())){
			//降序组装
			for (int i = entryList.size(); i >0; i--) {
				String key= entryList.get(i-1).getKey();
				Integer value=entryList.get(i-1).getValue();
				for (int j = 0; j < value; j++) {
					finalString.append(key).append(split);
				}
			}
		}else{
			//升序组装
			for (int i = 0; i < entryList.size(); i++) {
				String key= entryList.get(i).getKey();
				Integer value=entryList.get(i).getValue();
				for (int j = 0; j < value; j++) {
					finalString.append(key).append(split);
				}
			}
		}
		return finalString.toString();
	}
}
