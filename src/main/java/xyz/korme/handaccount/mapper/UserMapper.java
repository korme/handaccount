package xyz.korme.handaccount.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.korme.handaccount.model.UserModel;

@Mapper
@Component(value = "userMapper")
public interface UserMapper {
    @Insert("insert into user(nickName,openid,createTime) values(#{user.nickName}," +
            "#{user.openid},#{user.createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(@Param("user") UserModel user);

    @Update("update user set uniqueNumber=#{uniqueNumber} where userId=#{userId}")
    void insertUniqueNumber(@Param("uniqueNumber")int uniqueNumber,@Param("userId")int userId);

    @Select("select userId from user where uniqueNumber=#{uniqueNumber}")
    Integer findUserId(@Param("uniqueNumber")int uniqueNumber);

    @Select("select userName from user where uniqueNumber=#{uniqueNumber}")
    String findUserName(@Param("uniqueNumber")int uniqueNumber);

    @Select("SELECT uniqueNumber FROM user WHERE openid=#{openid}")
    Integer selectUniqueNumberByOpenId(@Param("openid")String openid);


}
