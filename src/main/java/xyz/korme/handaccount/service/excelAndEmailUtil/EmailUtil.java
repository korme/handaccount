package xyz.korme.handaccount.service.excelAndEmailUtil;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
public interface EmailUtil {
    int sendEmail(String mailAddress, InputStream file);
}
