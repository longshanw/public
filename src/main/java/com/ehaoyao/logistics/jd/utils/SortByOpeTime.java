package com.ehaoyao.logistics.jd.utils;

import java.util.Comparator;

import com.jd.open.api.sdk.response.etms.TraceApiDto;



public class SortByOpeTime implements Comparator<Object> {
	 public int compare(Object o1, Object o2) {
		 TraceApiDto s1 = (TraceApiDto) o1;
		 TraceApiDto s2 = (TraceApiDto) o2;
		 return s1.getOpeTime().compareTo(s2.getOpeTime());
		 }
	}
