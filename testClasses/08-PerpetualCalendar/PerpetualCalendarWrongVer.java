import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PerpetualCalendar{
    public static String getNextDay(int y,int m,int d){
        //错误：这样判断不完整
        if(y<1800||y>2450||m<1||m>12||d<1||d>311){
            return "非法输入";
        }
        //创建下一天
        SimpleDateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar nextDay=Calendar.getInstance();
        nextDay.set(Calendar.YEAR,y);
        nextDay.set(Calendar.MONTH,m-1);
        nextDay.set(Calendar.DATE,d);
        nextDay.add(Calendar.DATE,1);
        return dFormat.format(nextDay.getTime());
    }
}