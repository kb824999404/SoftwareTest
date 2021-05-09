public class TriangleJudger{
    private static final double MinV=0;
    private static final double MaxV=200;
    private static boolean comp(double a,double b){
        return Math.abs(a-b)<1e-6;
    }
    public static String judgeTriangle(double a,double b,double c){
        double temp;
        //给a,b,c从小到大排序
        if(a>b){
            temp=a;
            a=b;
            b=temp;
        }
        if(b>c){
            temp=b;
            b=c;
            c=temp;
        }
        if(a>b){
            temp=a;
            a=b;
            b=temp;
        }
        //判断
        if(a<=MinV||c>MaxV) return "变量超出取值范围";
        if(a+b<c||comp(a+b,c)) return "无法构成三角形";
        if(comp(a,b)&&comp(b,c)) return "等边三角形";
        if(comp(a,b)||comp(a,c)||comp(b,c)) return "等腰三角形";
        return "一般三角形";
    }
}