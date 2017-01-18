package com.li.inspection.util;

import android.util.Xml;

import com.li.inspection.entity.RequestDTO;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * 封装及解析XML的工具类
 * 项目名称:  [Traffic]<br/>
 * 包:        [com.satisfy.traffic.httputils]<br/>
 * 类名称:    [PullUtils]<br/>
 * 创建人:    [FanHang]<br/>
 * 创建时间:  [2015年4月29日 上午12:37:17]<br/>
 * 修改人:    [FanHang]<br/>
 * 修改时间:  [2015年4月29日 上午12:37:17]<br/>
 * 修改备注:  [说明本次修改内容]<br/>
 * 版本:      [v1.0]<br/>
 */
public class PullUtils {
	
	public static String buildXML(){
		StringWriter str = new StringWriter();
		XmlSerializer xmlSerializer=new KXmlSerializer();
			try {
				xmlSerializer.setOutput(str);
				xmlSerializer.startDocument("UTF-8", true);
				xmlSerializer.startTag(null, "soap:Envelope")
					.attribute(null, "xmlns:soap", "http://schemas.xmlsoap.org/soap/envelope/")
					.attribute(null, "xmlns:soapenc", "http://schemas.xmlsoap.org/soap/encoding/")
					.attribute(null, "xmlns:tns", "http://bjhms.eicp.net:9080/IntelligentTraffic/services/TpiWebService")
					.attribute(null, "xmlns:types", "http://bjhms.eicp.net:9080/IntelligentTraffic/services/TpiWebService/encodedTypes")
					.attribute(null, "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance")
					.attribute(null, "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
				
				xmlSerializer.startTag(null, "soap:Body")
					.attribute(null, "soap:encodingStyle", "http://schemas.xmlsoap.org/soap/encoding/");
				
				xmlSerializer.startTag(null, "q1:iTpiService")
					.attribute(null, "xmlns:q1", "http://webservice.tpi.jroo.com");
				
				xmlSerializer.startTag(null, "xtlb").attribute(null, "xsi:type", "xsd:string");
				xmlSerializer.text("01");
				xmlSerializer.endTag(null, "xtlb");
				
				xmlSerializer.startTag(null, "jkxlh").attribute(null, "xsi:type", "xsd:string");
				xmlSerializer.text("123456");
				xmlSerializer.endTag(null, "jkxlh");
				
				xmlSerializer.startTag(null, "jkid").attribute(null, "xsi:type", "xsd:string");
				xmlSerializer.text("02");
				xmlSerializer.endTag(null, "jkid");
				
				xmlSerializer.startTag(null, "json").attribute(null, "xsi:type", "xsd:string");
				xmlSerializer.text("{'phone':'13816388665','password':'123456'}");
				xmlSerializer.endTag(null, "json");
				
				xmlSerializer.endTag(null, "q1:iTpiService");
				xmlSerializer.endTag(null, "soap:Body");
				xmlSerializer.endTag(null, "soap:Envelope");
				xmlSerializer.endDocument();
				xmlSerializer.flush();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} 
		return str.toString();
	}
	
	/**
	 * 生成请求的
	 * @param dto
	 * @return
	 */
	public static String buildXML(RequestDTO dto){
		if(dto==null || Utils.isBlank(dto.getJkid()) || Utils.isBlank(dto.getJkxlh())
				|| Utils.isBlank(dto.getXtlb())){
			return null;
		}
		StringWriter str = new StringWriter();
		XmlSerializer xmlSerializer=new KXmlSerializer();
			try {
				xmlSerializer.setOutput(str);
				xmlSerializer.startDocument("UTF-8", true);
				
				xmlSerializer.setPrefix("web", "http://webservice.tpi.jroo.com");
				xmlSerializer.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
				xmlSerializer.setPrefix("xsd", "http://www.w3.org/2001/XMLSchema");
				xmlSerializer.setPrefix("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
				xmlSerializer.startTag("http://schemas.xmlsoap.org/soap/envelope/", "Envelope");
				
				xmlSerializer.startTag("http://schemas.xmlsoap.org/soap/envelope/", "Header");
				xmlSerializer.endTag("http://schemas.xmlsoap.org/soap/envelope/", "Header");
				
	            xmlSerializer.startTag("http://schemas.xmlsoap.org/soap/envelope/", "Body");
	            
	            xmlSerializer.startTag("http://webservice.tpi.jroo.com", "iTpiService");
	            xmlSerializer.attribute("http://schemas.xmlsoap.org/soap/envelope/", "encodingStyle", "http://schemas.xmlsoap.org/soap/encoding/");
	            
	            xmlSerializer.startTag(null, "xtlb");
	            xmlSerializer.attribute("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:string");
	            xmlSerializer.text(dto.getXtlb());
	            xmlSerializer.endTag(null, "xtlb");
	            
	            xmlSerializer.startTag(null, "jkxlh");
	            xmlSerializer.attribute("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:string");
	            xmlSerializer.text(dto.getJkxlh());
	            xmlSerializer.endTag(null, "jkxlh");
	            
	            xmlSerializer.startTag(null, "jkid");
	            xmlSerializer.attribute("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:string");
	            xmlSerializer.text(dto.getJkid());
	            xmlSerializer.endTag(null, "jkid");
	            
	            xmlSerializer.startTag(null, "json");
	            xmlSerializer.attribute("http://www.w3.org/2001/XMLSchema-instance", "type", "xsd:string");
	            xmlSerializer.text(dto.getJson()!=null&&dto.getJson().size()>0?new Gson().toJson(dto.getJson()):"");
	            xmlSerializer.endTag(null, "json");
	            
	            xmlSerializer.endTag("http://webservice.tpi.jroo.com", "iTpiService");
	            xmlSerializer.endTag("http://schemas.xmlsoap.org/soap/envelope/", "Body");  
				xmlSerializer.endTag("http://schemas.xmlsoap.org/soap/envelope/", "Envelope");
				xmlSerializer.endDocument();
				xmlSerializer.flush();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalStateException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} 
		return str.toString();
	}
	
	public static String parseXMLToJSON(String xml){
		if(xml==null||Utils.isBlank(xml)){
			return null;
		}
		String json = "";
		try {
			XmlPullParser parser = Xml.newPullParser();
			InputStream inStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			parser.setInput(inStream, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
			    switch (eventType) {
			        case XmlPullParser.START_TAG:// 开始元素事件
			        	String name = "";
			        	//得到命名空间，如：http://xxx.xxx.xx
			            //name = parser.getNamespace();
			        	//得到<soapenv:Envelope>
			            //name = parser.getPositionDescription();
			            //得到前缀，如<soapenv:Envelope>的soapenv
			            //name = parser.getPrefix();
			        	//得到TagName，如<soapenv:Envelope>的Envelope
			            name = parser.getName();
			            if (name.equalsIgnoreCase("iTpiServiceReturn")) {
			            	json = parser.nextText();//得到文本内容
			            }
			            break;
			    }
			    eventType = parser.next();
			}
			inStream.close();
			return json;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return json;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return json;
		} catch (IOException e) {
			e.printStackTrace();
			return json;
		}
	}
}
