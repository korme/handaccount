package xyz.korme.handaccount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.korme.handaccount.common.response.RespCode;
import xyz.korme.handaccount.common.response.ResponseEntity;
import xyz.korme.handaccount.mapper.CategoryMapper;
import xyz.korme.handaccount.mapper.UserMapper;
import xyz.korme.handaccount.model.RequestUser;
import xyz.korme.handaccount.model.UserModel;
import xyz.korme.handaccount.service.timeUtil.TimeUtils;
import xyz.korme.handaccount.service.userIdToUniqueNumber.UserIdToUniqueNumber;
import xyz.korme.handaccount.service.wxapi.WxApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsersController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    TimeUtils timeUtils;
    @Autowired
    UserIdToUniqueNumber userIdToUniqueNumber;

    //微信官方接口处理
    @Autowired
    WxApi wxApi;
//    @RequestMapping(value = "/image")
//    @ResponseBody
//    public BufferedImage getImage() throws IOException {
//        return ImageIO.read(new FileInputStream(new File("C:/img/test.jpg")));
//    }

    /**
     * 查询报销人是否存在
     * @param uniqueNumber
     * @return SUCCESS or WARN_ENPTY
     */
    @RequestMapping(value = "/userIsExist")
    public ResponseEntity userIsExist(int uniqueNumber){
        Integer userId=userMapper.findUserId(uniqueNumber);
        if(userId==null)
            return new ResponseEntity(RespCode.WARN_ENPTY);
//        Map<Object,Object> result=new HashMap<Object, Object>();
//        result.put(),
        String userName=userMapper.findUserName(uniqueNumber);
        List<Map<String,Object>> r=categoryMapper.selectAllCateMap(uniqueNumber);
        Map<String,Object> result=new HashMap<>();
        result.put("userId",userId);
        result.put("userName",userName);
        result.put("payCateList",r);
        return new ResponseEntity(RespCode.SUCCESS,result);
    }

    /**
     * 用户登录
     * @param data
     * @return
     */
    @Transactional
    @RequestMapping(value = "/login")
    public ResponseEntity insertUser(RequestUser data){
        String openid  = wxApi.getOpenid(data.getCode());
        if(openid==null||openid==""){
            return new ResponseEntity(RespCode.WX_ERROR);
        }
        //做查询操作 看是否重复
        Integer uniqueNumber=userMapper.selectUniqueNumberByOpenId(openid);
        if(uniqueNumber!=null)
            return new ResponseEntity(RespCode.SUCCESS,uniqueNumber);
        UserModel user=new UserModel(data.getNickname(),openid,timeUtils.getNowTime());
        userMapper.insertUser(user);
        user.setUniqueNumber(userIdToUniqueNumber.getUniqueNumber(user.getUserId()));
        userMapper.insertUniqueNumber(user.getUniqueNumber(),user.getUserId());
        return new ResponseEntity(RespCode.SUCCESS,user.getUniqueNumber());
    }

}
