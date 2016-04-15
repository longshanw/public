package com.ehaoyao.logistics.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;



public class ReadConfigs {
	/**
	 * 存储读取出来的配置文件信息
	 * 
	 * @author bj_gxj
	 */
	public static HashMap<String, String> configs;
	private Logger logger =Logger.getLogger(ReadConfigs.class);

	/**
	 * 获取相对路径下的配置文件信息
	 * 
	 * @param fileName
	 *            properties配置文件名称 eg：test.properties fileName=test
	 */
	public ReadConfigs(String fileName) {
		super();
		ResourceBundle bundle = ResourceBundle.getBundle(fileName);
		configs = new HashMap<String, String>();
		Set<String> names = bundle.keySet();
		for (String name : names) {
			configs.put(name, bundle.getString(name));
		}
	}

	/**
	 * 获取绝对路径下的配置文件信息
	 * 
	 * @param filePath
	 *            配置文件所在的当前工程下的文件夹名称 eg：工程下config文件夹 filePath=config
	 * @param fileName
	 *            properties配置文件名称 eg：test.properties fileName=test
	 * 
	 */
	public ReadConfigs(String filePath, String fileName) {
		super();
		String proFilePath = System.getProperty("user.dir") + "/" + filePath
				+ "/" + fileName + ".properties";
		try {
			File file = new File(proFilePath);
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			ResourceBundle bundle = new PropertyResourceBundle(in);// 读取配置文件
			configs = new HashMap<String, String>();
			Set<String> names = bundle.keySet();
			for (String name : names) {
				configs.put(name, bundle.getString(name));
			}
		} catch (FileNotFoundException e) {
			logger.error("没有找到配置文件，请检查配置文件是否存在！！！\n配置wenjian路径为：" + proFilePath);
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("配置文件读取失败！！！！");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("获取配置文件信息错误，异常信息为：" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public Short getShort(String key) {
		if (null != configs && null != configs.get(key)) {
			return Short.valueOf(configs.get(key).trim());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public Integer getInteger(String key) {
		if (null != configs && null != configs.get(key)) {
			return Integer.valueOf(configs.get(key).trim());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public Float getFloat(String key) {
		if (null != configs && null != configs.get(key)) {
			return Float.valueOf(configs.get(key));
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public Double getDouble(String key) {
		if (null != configs && null != configs.get(key)) {
			return Double.valueOf(configs.get(key));
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public Long getLong(String key) {
		if (null != configs && null != configs.get(key)) {
			return Long.valueOf(configs.get(key).trim());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public Boolean getBoolean(String key) {
		if (null != configs && null != configs.get(key)) {
			return Boolean.valueOf(configs.get(key));
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public Byte getByte(String key) {
		if (null != configs && null != configs.get(key)) {
			return Byte.valueOf(configs.get(key).trim());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public Character getCharacter(String key) {
		if (null != configs && null != configs.get(key)) {
			return configs.get(key).charAt(0);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 *            properties配置文件的key值
	 * @return
	 * 
	 */
	public String getString(String key) {
		if (null != configs && null != configs.get(key)) {
			return configs.get(key).trim();
		} else {
			return null;
		}
	}

}
