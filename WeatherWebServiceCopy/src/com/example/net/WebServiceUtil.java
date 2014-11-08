package com.example.net;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceUtil {
	static final String SERVICE_NS = "http://WebXml.com.cn";
	static final String SERVICE_URL = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
	public static List<String> getProvinceList(){
		String methodName = "getRegionProvice";
		HttpTransportSE httpTranstation = new HttpTransportSE(SERVICE_URL);
		httpTranstation.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		try{
			httpTranstation.call(SERVICE_NS + methodName, envelope);
			if(envelope.getResponse() != null){
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(methodName + "Result");
				return parseProvinceOrCity(detail);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getCityListByProvince(String province){
		String methodName = "getSupportCityString";
		HttpTransportSE httpTranstation = new HttpTransportSE(SERVICE_URL);
		httpTranstation.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("theRegionCode", province);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		try{
			httpTranstation.call(SERVICE_NS + methodName, envelope);
			if(envelope.getResponse() != null){
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(methodName + "Result");
				return parseProvinceOrCity(detail);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	private static List<String> parseProvinceOrCity(SoapObject detail){
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < detail.getPropertyCount(); i++){
			String str = detail.getPropertyAsString(i).toString();
			result.add(str.split(",")[0]);
		}
		return result;
	}
	public static SoapObject getWeatherByCity(String cityName){
		String methodName = "getWeather";
		HttpTransportSE httpTranstation = new HttpTransportSE(SERVICE_URL);
		httpTranstation.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("theCityCode", cityName);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		try{
			httpTranstation.call(SERVICE_NS + methodName, envelope);
			if(envelope.getResponse() != null){
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(methodName + "Result");
				return detail;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
