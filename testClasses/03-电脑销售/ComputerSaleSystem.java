public class ComputerSaleSystem
{
    private int hostNum;
    private int screenNum;
    private int peripheralNum;

    private int hostPrice = 25;
    private int screenPrice = 30;
    private int peripheralPrice = 45;

    private int maxHostNum = 70;
    private int maxScreenNum = 80;
    private int maxPeripheralNum = 90;

    private double firstLevel = 0.1;
    private double secondLevel = 0.15;
    private double thirdLevel = 0.2;


    private boolean check()
    {
        if (hostNum<1||hostNum>maxHostNum)
        {
            return false;
        }
        if (screenNum<1||screenNum>maxScreenNum)
        {
            return false;
        }
        if (peripheralNum<1||peripheralNum>maxPeripheralNum)
        {
            return false;
        }
        return true;
    }

    public Integer getSaleCount(int hostNum, int screenNum, int peripheralNum)
    {
        this.hostNum = hostNum;
        this.screenNum = screenNum;
        this.peripheralNum = peripheralNum;
        if(!check())
        {
            return -1;
        }
        int saleCount = hostNum * hostPrice + screenNum * screenPrice + peripheralNum * peripheralPrice;

        return saleCount;
    }

    public Double getBrokerage(int hostNum, int screenNum, int peripheralNum)
    {
        var saleCount=getSaleCount(hostNum, screenNum, peripheralNum);
        if(saleCount<0)
        {
            return -1.0;
        }
        else if (saleCount <= 1000 )
        {
            return firstLevel * saleCount;
        }
        else if (saleCount <= 1800)
        {
            return secondLevel * saleCount;
        }
        return thirdLevel * saleCount;
    }
}



