package zcf.demo.activity;

public class Utils {
	 /**
	 * 
	 * ��������imageResoId 
	 * ���ܣ���ȡͼƬ
	 * ������
	 * @param weather
	 * @return
	 */
	public final static int imageResoId(String weather){
		int resoid=R.drawable.s_2;
		if(weather.indexOf("����")!=-1||weather.indexOf("��")!=-1){//����ת�磬������ͬ indexOf:�����ִ�
            resoid=R.drawable.s_1;}
        else if(weather.indexOf("����")!=-1&&weather.indexOf("��")!=-1){
            resoid=R.drawable.s_2;}
        else if(weather.indexOf("��")!=-1&&weather.indexOf("��")!=-1){
            resoid=R.drawable.s_3;}
        else if(weather.indexOf("��")!=-1&&weather.indexOf("��")!=-1){
            resoid=R.drawable.s_12;}
        else if(weather.indexOf("��")!=-1&&weather.indexOf("��")!=-1){
            resoid=R.drawable.s_12;}
        else if(weather.indexOf("��")!=-1){resoid=R.drawable.s_13;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_2;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_3;}
        else if(weather.indexOf("С��")!=-1){resoid=R.drawable.s_3;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_4;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_5;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_5;}
        else if(weather.indexOf("����")!=-1){resoid=R.drawable.s_6;}
        else if(weather.indexOf("������")!=-1){resoid=R.drawable.s_7;}
        else if(weather.indexOf("Сѩ")!=-1){resoid=R.drawable.s_8;}
        else if(weather.indexOf("��ѩ")!=-1){resoid=R.drawable.s_9;}
        else if(weather.indexOf("��ѩ")!=-1){resoid=R.drawable.s_10;}
        else if(weather.indexOf("��ѩ")!=-1){resoid=R.drawable.s_10;}
        else if(weather.indexOf("��ɳ")!=-1){resoid=R.drawable.s_11;}
        else if(weather.indexOf("ɳ��")!=-1){resoid=R.drawable.s_11;}
        else if(weather.indexOf("��")!=-1){resoid=R.drawable.s_12;}
		return resoid;
	}
}
