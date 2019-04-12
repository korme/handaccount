package xyz.korme.handaccount.service.timeUtil;

import org.springframework.stereotype.Service;

@Service
public interface TimeUtils {
    String getNowTime();
    String getYearToDay();
}
