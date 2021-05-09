import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PerpetualCalendar{
    public static String getNextDay(int y,int m,int d){
        if(y<1800||y>2450||m<1||m>12){
            return "非法输入";
        }
        //创建下一天
        SimpleDateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar nextDay=Calendar.getInstance();
        nextDay.set(Calendar.YEAR,y);
        nextDay.set(Calendar.MONTH,m-1);
        //判断这个月的天数
        var i=nextDay.getActualMaximum(Calendar.DATE);
        if(d<1||d>i){
            return "非法输入";
        }
        nextDay.set(Calendar.DATE,d);
        nextDay.add(Calendar.DATE,1);
        return dFormat.format(nextDay.getTime());
    }
}