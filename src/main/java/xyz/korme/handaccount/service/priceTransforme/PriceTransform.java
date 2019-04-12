package xyz.korme.handaccount.service.priceTransforme;

import org.springframework.stereotype.Service;

@Service
public interface PriceTransform {
    String FenToStringYuan(Integer pre);
    int YuanToIntFen(String pre);
    String FenToStringYuan(String pre);
    //String FenToStringYuan(int pre);

}
