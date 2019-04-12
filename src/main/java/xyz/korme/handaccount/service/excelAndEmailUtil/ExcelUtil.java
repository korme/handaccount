package xyz.korme.handaccount.service.excelAndEmailUtil;

import org.springframework.stereotype.Service;
import xyz.korme.handaccount.model.ExcelForAppModel;

import java.io.File;
import java.util.List;

@Service
public interface ExcelUtil {
    int createExcel(String email,List<ExcelForAppModel> userList);

}
