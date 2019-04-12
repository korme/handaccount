package xyz.korme.handaccount.service.priceTransforme;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class PriceTransformMPL implements PriceTransform{
    @Override
    public String FenToStringYuan(Integer cals){
        int sign=0;
        if(cals<0)
            sign=1;
        cals=Math.abs(cals);
        StringBuffer price=new StringBuffer(cals.toString());
        if(cals>=100){
            int t=price.length();
            price.insert(price.length()-2,".");
        }
        if(cals<100&&cals>=10){
            price.insert(0,"0.");
        }
        if(cals<10){
            price.insert(0,"0.0");
        }
        if(sign==1)
            price.insert(0,"-");
        return price.toString();
    }

    /*@Override
    public String FenToStringYuan(int pre) {
        Integer cals =new Integer(pre);
        int sign=0;
        if(cals<0)
            sign=1;
        cals=Math.abs(cals);
        StringBuffer price=new StringBuffer(cals.toString());
        if(cals>=100){
            int t=price.length();
            price.insert(price.length()-2,".");
        }
        if(cals<100&&cals>=10){
            price.insert(0,"0.");
        }
        if(cals<10){
            price.insert(0,"0.0");
        }
        if(sign==1)
            price.insert(0,"-");
        return price.toString();
    }*/

    @Override
    public String FenToStringYuan(String pre) {
        Integer cals=Integer.parseInt(pre);
        int sign=0;
        if(cals<0)
            sign=1;
        cals=Math.abs(cals);
        StringBuffer price=new StringBuffer(cals.toString());
        if(cals>=100){
            int t=price.length();
            price.insert(price.length()-2,".");
        }
        if(cals<100&&cals>=10){
            price.insert(0,"0.");
        }
        if(cals<10){
            price.insert(0,"0.0");
        }
        if(sign==1)
            price.insert(0,"-");
        return price.toString();
    }

    @Override
    public int YuanToIntFen(String pre) {
        float f;
        try{
            f=Float.parseFloat(pre);
        }catch (Exception e){
            return 0;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        f = Float.valueOf(df.format(f));
        int money = (int)(f * 100);
        return money;
    }
}
