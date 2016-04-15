package com.ehaoyao.logistics.jd.utils;

import java.util.List;

import org.apache.log4j.Logger;

import com.ehaoyao.logistics.common.utils.ReadConfigs;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.etms.EtmsTraceGetRequest;
import com.jd.open.api.sdk.response.etms.EtmsTraceGetResponse;
import com.jd.open.api.sdk.response.etms.TraceApiDto;


public class JDExpressUtils {
	public static ReadConfigs configs = new ReadConfigs("jdconfig");
	public static final String serverUrl =configs.getString("SERVERURL");
	public static final String appKey = configs.getString("APPKEY");
	public static final String appSecret =configs.getString("APPSECRET");
	public static final String token = configs.getString("TOKEN");
    public static Logger logger=Logger.getLogger(JDExpressUtils.class);
	public static List<TraceApiDto> getDetails(String num) {
		
		JdClient client = new DefaultJdClient(serverUrl, token, appKey,
				appSecret);
		EtmsTraceGetRequest request = new EtmsTraceGetRequest();
		request.setWaybillCode(num);
		EtmsTraceGetResponse response;
		//StringBuffer stb=new StringBuffer();
		try {
			response = client.execute(request);
			//String str=response.getMsg();
			//logger.info("★★★★★★★★★★★"+str+"★★★★★★★★★★★");
			List<TraceApiDto> apiDtos = response.getTraceApiDtos();
			
			return apiDtos;
		} catch (JdException e) {
			e.printStackTrace();
		}

		return null;
	}
}
