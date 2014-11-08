package com.example.weatherwebservicecopy;

import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.example.net.WebServiceUtil;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button bt_city;
	private TextView tv_city;
	private String city_name;
	private Spinner province_spinner;
	private Spinner city_spinner;
	private List<String> provinces;
	private List<String> citys;
	private SharedPreferences preference;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		preference = getSharedPreferences("weather", MODE_PRIVATE);
		city_name = preference.getString("city", "无锡");
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_city.setText(city_name);
		bt_city = (Button) findViewById(R.id.bt_city);
		bt_city.setOnClickListener(new MyListener());
		
		refresh(city_name);
		
		
		
		
		
		
		
	}
	 
	class MyListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			showDialog();
		}
		 
	 }
	public void showDialog(){
		View view = LayoutInflater.from(this).inflate(R.layout.city_layout, null);
		province_spinner = (Spinner) view.findViewById(R.id.province_spinner);
		city_spinner = (Spinner) findViewById(R.id.city_spinner);
		provinces = WebServiceUtil.getProvinceList();
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces);
		province_spinner.setAdapter(adapter);
		province_spinner.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				citys = WebServiceUtil.getCityListByProvince(provinces.get(position));
				ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, citys);
//				adapter.set
				city_spinner.setAdapter(adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		city_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				city_name = citys.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("请选择城市");
		dialog.setView(view);
		dialog.setPositiveButton("确定", new android.content.DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				tv_city.setText(city_name);
				addToSharedPreferences(city_name);
				refresh(city_name);
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
		
		
		
		
	}
	protected void refresh(String city_name2) {
		// TODO Auto-generated method stub
		SoapObject detail = WebServiceUtil.getWeatherByCity(city_name2);
		String date = detail.get
		
		
		
		
		
		
	}
	protected void addToSharedPreferences(String city_name2) {
		// TODO Auto-generated method stub
		SharedPreferences.Editor editor = preference.edit();
		editor.putString("city", city_name2);
		editor.commit();
	}
	
	
	
	
}

