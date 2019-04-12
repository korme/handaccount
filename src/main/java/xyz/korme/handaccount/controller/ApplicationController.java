package xyz.korme.handaccount.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.korme.handaccount.common.response.RespCode;
import xyz.korme.handaccount.common.response.ResponseEntity;
import xyz.korme.handaccount.mapper.ApplicationMapper;
import xyz.korme.handaccount.mapper.CategoryMapper;
import xyz.korme.handaccount.model.ApplicationModel;
import xyz.korme.handaccount.service.dictionaryMap.CateDictionaryMap;
import xyz.korme.handaccount.service.fileUtil.FileUtil;
import xyz.korme.handaccount.service.priceTransforme.PriceTransform;
import xyz.korme.handaccount.service.timeUtil.TimeUtils;

import java.util.*;


@RestController
public class ApplicationController {
    @Autowired
    TimeUtils timeUtils;
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    PriceTransform priceTransform;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CateDictionaryMap cateDictionaryMap;
    @Autowired
    FileUtil fileUtil;



    @RequestMapping(value = "/allApplication")
    public ResponseEntity allApplication(Integer auditorUniqueNumber){
        List<ApplicationModel> result=applicationMapper.selectMyApplication(auditorUniqueNumber);
        if(result==null)
            return new ResponseEntity(RespCode.WARN_ENPTY);
        Map<Integer,String> cateMap=cateDictionaryMap.getDicMap(auditorUniqueNumber);
        for(ApplicationModel i:result){
            i.setCateName(cateMap.get(i.getCateId()));
            i.setMount(priceTransform.FenToStringYuan(i.getMount()));
        }
        return new ResponseEntity( RespCode.SUCCESS,result);
    }
    @RequestMapping(value = "/initialApplication")
    public ResponseEntity initialApplication(Integer auditorUniqueNumber){
        List<ApplicationModel> result=applicationMapper.selectInitApplication(auditorUniqueNumber);
        if(result==null)
            return new ResponseEntity(RespCode.WARN_ENPTY);
        Map<Integer,Integer> currentBalance =cateDictionaryMap.getBalanceMap(auditorUniqueNumber);
        Map<Integer,String> cateMap=cateDictionaryMap.getDicMap(auditorUniqueNumber);
        List<ApplicationModel> removes=new ArrayList<>();
        for(ApplicationModel i:result){
            try{
                i.setCateName(cateMap.get(i.getCateId()));
                Integer pre=currentBalance.get(i.getCateId())+Integer.parseInt(i.getMount());
                i.setAfterBalance(priceTransform.FenToStringYuan(pre));
                i.setMount(priceTransform.FenToStringYuan(i.getMount()));
            }catch (NullPointerException e){
                removes.add(i);
            }
        }
        for(ApplicationModel i:removes)
            result.remove(i);
        return new ResponseEntity( RespCode.SUCCESS,result);
    }
    @RequestMapping(value = "/addApplicaton")
    public ResponseEntity addApplication(ApplicationModel app){
        if(app.getApplicationUniqueNumber()==null||app.getAudiorUniqueNumber()==null
        ||app.getCateId()==null||app.getMount()==null||
                !categoryMapper.selectUniqueNumberByCateId(app.getCateId()).equals(app.getAudiorUniqueNumber())){
            return new ResponseEntity(RespCode.INPUT_ERROR);
        }
        app.setMountTrans(priceTransform.YuanToIntFen(app.getMount()));
        app.setApplyTime(timeUtils.getNowTime());
        applicationMapper.insertApplication(app);
        return new ResponseEntity(RespCode.SUCCESS);
    }

    @RequestMapping(value = "/myApplicaton")
    public ResponseEntity myApplicaton(ApplicationModel app){
        if(app.getApplicationUniqueNumber()==null
                ||app.getCateId()==null||app.getMount()==null){
            return new ResponseEntity(RespCode.INPUT_ERROR);
        }
        app.setMountTrans(priceTransform.YuanToIntFen(app.getMount()));
        app.setApplyTime(timeUtils.getNowTime());
        applicationMapper.insertMyApplication(app);

        int applicationId=app.getApplicationId();

        Integer cateId=app.getCateId();
        Integer balance=categoryMapper.selectBalanceByCateId(cateId);
        balance=balance+app.getMountTrans();
        categoryMapper.updateBalance(balance,cateId);
        applicationMapper.passMyApplication(applicationId,balance);
        return new ResponseEntity(RespCode.SUCCESS);
    }

    @RequestMapping(value = "/setPassed")
    @Transactional
    public ResponseEntity setPassed(Integer applicationId){
        if(applicationMapper.ApplicationIsExist(applicationId)==0)
            return new ResponseEntity(RespCode.INPUT_ERROR);
        Map<String,Integer> tempMap= applicationMapper.selectApplicationByApplicationId(applicationId);
        for(Integer i:tempMap.values()){
            if(i==null)
                return new ResponseEntity(RespCode.ERROR_DATA);
        }

        Integer cateId=tempMap.get("cateId");
        Integer balance=categoryMapper.selectBalanceByCateId(cateId);
        balance=balance+tempMap.get("mount");
        categoryMapper.updateBalance(balance,tempMap.get("cateId"));
        applicationMapper.passApplication(applicationId,balance);
        return new ResponseEntity(RespCode.SUCCESS);
    }

    @RequestMapping(value = "/setNotPassed")
    public ResponseEntity setNotPassed(Integer applicationId){
        if(applicationMapper.ApplicationIsExist(applicationId)==0)
            return new ResponseEntity(RespCode.INPUT_ERROR);
        applicationMapper.notPassApplication(applicationId);
        return new ResponseEntity(RespCode.SUCCESS);
    }

    @RequestMapping("/fileUpload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file){
        String fileName = fileUtil.upload(file, "C://image", file.getOriginalFilename());
        if (fileName.equals("0"))
            //failed
            return new ResponseEntity(RespCode.ERROR_TYPE);
        else if(fileName==null)
            //不知道发生了啥，网络异常就完事了
            return new ResponseEntity(RespCode.WARN_INTERNET);
        else
            return new ResponseEntity(RespCode.SUCCESS,fileName);


    }
    @RequestMapping("/search")
    public ResponseEntity search(StringBuilder keyWord,Integer uniqueNumber,Integer page){
        if(keyWord==null||uniqueNumber==null||page<1)
            return new ResponseEntity(RespCode.INPUT_ERROR);
        if(page==null)
            page=1;
        keyWord.insert(0,"%");
        keyWord.append("%");
        PageHelper.startPage(page,10);
        List<ApplicationModel> result=applicationMapper.selectSearch(keyWord.toString(),uniqueNumber);
        /*List<ApplicationModel> resultB=categoryMapper.selectSearches(keyWord);
        if(result!=null){
            if(resultB!=null){
                result.addAll(resultB);
                Collections.sort(result);
            }
        }
        else{
            if(resultB!=null)
                return new ResponseEntity(RespCode.SUCCESS,resultB);
        }*/
        for(ApplicationModel i:result)
            i.setMount(priceTransform.FenToStringYuan(i.getMount()));


        return new ResponseEntity(RespCode.SUCCESS,result);
    }

    /**
     * 显示单张图片
     * @return
     */
    /*@RequestMapping("show")
    public ResponseEntity showPhotos(String fileName){

        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/
}
