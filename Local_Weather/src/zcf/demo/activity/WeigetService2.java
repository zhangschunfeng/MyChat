package zcf.demo.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import android.widget.RemoteViews;


public class WeigetService2  extends Service{
	private LocationClient mLocationClient=null;
	private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy��MM��dd ,HH:mm");
	private BDLocationListener myListener;
	private String cityName;
	private String wd="";
	private String tq="";
	private String fx="";
	private Timer timer;
	private RemoteViews view;
	@Override
	public IBinder onBind(Intent intent) {
	
		return null;
	}
	@Override
	public void onCreate() {
			super.onCreate();
			mLocationClient = new LocationClient(this);     //����LocationClient��
			initListener();
			mLocationClient.registerLocationListener(myListener );    //ע���������
			initLocation();
			mLocationClient.start();
			timer=new Timer();
			timer.schedule(new TimerTask()
			{
				@Override
				public void run() {	
					
					updateView();
				}
				
			}, 0,1000);
	}
	protected void updateView() {
		String str=dateFormat.format(new Date());
		String dateInfor[]=str.split(",");
		view=new RemoteViews(getPackageName(),R.layout.weiget);
		view.setTextViewText(R.id.city, cityName);
		view.setTextViewText(R.id.showtime, dateInfor[1]);
		view.setTextViewText(R.id.showyear, dateInfor[0]);
		view.setTextViewText(R.id.weather, wd);
		view.setTextViewText(R.id.weather_detail,tq);
	  //  setIamgeResource(tq);
		AppWidgetManager manager=AppWidgetManager.getInstance(this);
		ComponentName cn=new ComponentName(this,MyWeigetProviderInfo.class);
		manager.updateAppWidget(cn,view);
		
		
	}
	private void initListener() {
		 myListener=new BDLocationListener()
	        {
				@Override
				public void onReceiveLocation(BDLocation location) {
					String citys=location.getCity();
					cityName=citys.substring(0,citys.length()-1);
					if(cityName!=null)
					{ 
						QueryAsyncTask asyncTask = new QueryAsyncTask();	
						asyncTask.execute();
					}
				}
	        };
     	
	}
	private void initLocation() {
	  	LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
        option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
        int span=1000;
        option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
        option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
        option.setIgnoreKillProcess(false);//��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
        option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
        option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
        mLocationClient.setLocOption(option);
}
//ʹ���첽ͨ��AsynTask���������в�ѯ
private class QueryAsyncTask extends AsyncTask
{	
	@Override
	protected void onPostExecute(Object result) {

		if(result!=null)
		{		
			String weatherResult = (String)result;
			if(weatherResult.split(";").length>1){
			   String a  = weatherResult.split(";")[1];
				if(a.split("=").length>1){
					String b = a.split("=")[1];
					String c = b.substring(1,b.length()-1);
					String[] resultArr = c.split("\\}");
					if(resultArr.length>0)
					{
						todayParse(resultArr[0]);
				
						
					}
			 	 }
			}		
		}
	
	}
	@Override
	protected Object doInBackground(Object... params) {
		//��ѯ����
		return WeatherService.getWeather(cityName);
	}
	
}

public void todayParse(String weather) {
	String temp = weather.replace("'", "");
	String[] tempArr = temp.split(",");
	
	if(tempArr.length>0){
		for(int i=0;i<tempArr.length;i++){
			if(tempArr[i].indexOf("t1:")!=-1){
				wd=tempArr[i].substring(3,tempArr[i].length())+"��";
			}else if(tempArr[i].indexOf("t2:")!=-1){
				wd=wd+"~"+tempArr[i].substring(3,tempArr[i].length())+"��";
			}else if(tempArr[i].indexOf("d1:")!=-1){
				fx=tempArr[i].substring(3,tempArr[i].length());
			}else if(tempArr[i].indexOf("s1:")!=-1){
				tq=tempArr[i].substring(4,tempArr[i].length());
			}
		}
		
		
		
	}
	
}
}
