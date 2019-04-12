package xyz.korme.handaccount.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.korme.handaccount.common.response.RespCode;
import xyz.korme.handaccount.common.response.ResponseEntity;
import xyz.korme.handaccount.mapper.ApplicationMapper;
import xyz.korme.handaccount.mapper.CategoryMapper;
import xyz.korme.handaccount.model.ApplicationModel;
import xyz.korme.handaccount.model.CategoryModel;
import xyz.korme.handaccount.model.ExcelForAppModel;
import xyz.korme.handaccount.service.excelAndEmailUtil.ExcelUtil;
import xyz.korme.handaccount.service.priceTransforme.PriceTransform;
import xyz.korme.handaccount.service.timeUtil.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    PriceTransform priceTransform;
    @Autowired
    TimeUtils timeUtils;
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    ExcelUtil excelUtil;
    @RequestMapping(value = "/allCategory")
    public ResponseEntity allCategory(int uniqueNumber){
        categoryMapper.selectAllCate(uniqueNumber);
        List<CategoryModel> result=categoryMapper.selectAllCate(uniqueNumber);
        if(result.isEmpty())
            return new ResponseEntity(RespCode.WARN_ENPTY);
        for(CategoryModel i:result){
            i.setBalance(priceTransform.FenToStringYuan(i.getBalance()));
            i.setCateTotal(priceTransform.FenToStringYuan(i.getCateTotal()));
        }
        return new ResponseEntity(RespCode.SUCCESS,result);
    }

    @RequestMapping(value = "/addCategory")
    public ResponseEntity addCategory(CategoryModel cate){
        if(cate.getBalance()==null||cate.getCateName()==null
                ||cate.getUniqueNumber()==null||cate.getCateTotal()==null)
            return new ResponseEntity(RespCode.INPUT_ERROR);
        cate.setCeateTime(timeUtils.getNowTime());
        cate.setBalanceTrans(priceTransform.YuanToIntFen(cate.getBalance()));
        cate.setCateTotalTrans(priceTransform.YuanToIntFen(cate.getCateTotal()));
        categoryMapper.insertcategory(cate.getCateName(),cate.getUniqueNumber(),
                cate.getBalanceTrans(),cate.getCeateTime(),cate.getCateTotalTrans(),cate.getAccountId());
        return new ResponseEntity(RespCode.SUCCESS);
    }


    @RequestMapping(value = "/delCategory")
    public ResponseEntity delCategory(@Param("cateId") Integer cateId){
        if(cateId==null)
            return new ResponseEntity(RespCode.INPUT_ERROR);
        categoryMapper.delCateByUniqueNumber(cateId);
        return new ResponseEntity(RespCode.SUCCESS);
    }
    @Transactional
    @RequestMapping(value = "/changeCateTotal")
    public ResponseEntity changeCateTotal(Integer cateId,Integer uniqueNumber,String change){
        if(uniqueNumber==null||change==null||cateId==null)
            return new ResponseEntity(RespCode.INPUT_ERROR);
        if(!categoryMapper.selectUniqueNumberByCateId(cateId).equals(uniqueNumber))
            return new ResponseEntity(RespCode.INPUT_ERROR);
        Integer balance=categoryMapper.selectBalanceByCateId(cateId);
        Integer cateTotal=categoryMapper.selectCateTotalByCateId(cateId);
        if(balance==null||cateTotal==null)
            return new ResponseEntity(RespCode.INPUT_ERROR);
        int plus=priceTransform.YuanToIntFen(change);
        balance=balance+plus;
        cateTotal=cateTotal+plus;
        categoryMapper.updateBalanceAndCateTotal(balance,cateTotal,cateId);
        ApplicationModel app=new ApplicationModel(uniqueNumber,uniqueNumber,timeUtils.getNowTime(),
                cateId,plus,4,balance);
        app.setUserName("自我提额");
        applicationMapper.insertMyApplication(app);
        return new ResponseEntity(RespCode.SUCCESS);
    }

    @RequestMapping(value = "/email")
    public ResponseEntity email(String email,Integer audiorUniqueNumber){
        String regex="[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)" +
                "*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        if(audiorUniqueNumber==null||!email.matches(regex))
            return new ResponseEntity(RespCode.WRONG);
        List<ExcelForAppModel> list=applicationMapper.selectAllDataByAudiorIdForExcel(audiorUniqueNumber);
        excelUtil.createExcel(email,list);

        return new ResponseEntity(RespCode.SUCCESS);
    }

    @RequestMapping(value = "/getCateList")
    public ResponseEntity cateContent(Integer cateId){
        if(cateId!=null){
            List<Map<String,Object>> result=applicationMapper.selectApplicationByCateId(cateId);
            for(Map<String,Object> i:result){
                i.put("mount",priceTransform.FenToStringYuan((Integer) i.get("mount")));
                i.put("applyTime",i.get("applyTime").toString());
            }


            return new ResponseEntity(RespCode.SUCCESS,result);
        }

        return new ResponseEntity(RespCode.INPUT_ERROR);
    }

    @RequestMapping(value = "/test")
    public ResponseEntity test(String email,Integer audiorUniqueNumber){
        /*String regex="[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)" +
                "*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        if(audiorUniqueNumber==null||!email.matches(regex))
            return new ResponseEntity(RespCode.WRONG);*/
        List<ExcelForAppModel> list=applicationMapper.selectAllDataByAudiorIdForExcel(10018);
        excelUtil.createExcel("454334698@qq.com",list);

        return new ResponseEntity(RespCode.SUCCESS);
    }


}
