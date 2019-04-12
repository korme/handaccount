package xyz.korme.handaccount.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.korme.handaccount.model.ApplicationModel;
import xyz.korme.handaccount.model.ExcelForAppModel;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "applicationMapper")
public interface ApplicationMapper {
    @Insert("insert into application(applicationUniqueNumber,audiorUniqueNumber," +
            "applyTime,cateId,remark,mount,currentBalance,userName,image)" +
            "values(#{app.applicationUniqueNumber},#{app.audiorUniqueNumber}," +
            "#{app.applyTime},#{app.cateId},#{app.remark},#{app.mountTrans},#{app.currentBalance},#{app.userName},#{app.image})")
    void insertApplication(@Param("app")ApplicationModel app);

    @Insert("insert into application(applicationUniqueNumber,audiorUniqueNumber," +
            "applyTime,cateId,remark,mount,isPassed,currentBalance,userName,image)" +
            "values(#{app.applicationUniqueNumber},#{app.audiorUniqueNumber}," +
            "#{app.applyTime},#{app.cateId},#{app.remark},#{app.mountTrans},4,#{app.currentBalance},#{app.userName},#{app.image})")
    @Options(useGeneratedKeys = true, keyProperty = "applicationId")
    void insertMyApplication(@Param("app")ApplicationModel app);

    @Select("select * from application where audiorUniqueNumber=#{audiorUniqueNumber} and isPassed=0 order by applyTime desc")
    List<ApplicationModel> selectInitApplication(@Param("audiorUniqueNumber")int audiorUniqueNumber);

    @Select("select * from application where audiorUniqueNumber=#{audiorUniqueNumber} and isPassed=1 order by applyTime desc")
    List<ApplicationModel> selectUnpassedApplication(@Param("uniqueNumber")int uniqueNumber);

    @Select("select * from application where audiorUniqueNumber=#{audiorUniqueNumber} " +
            "and (isPassed=2 or isPassed=4) order by applyTime desc")
    List<ApplicationModel> selectMyApplication(@Param("audiorUniqueNumber")int audiorUniqueNumber);

    @Select("select count(*) from application where applicationId=#{applicationId}")
    int ApplicationIsExist(@Param("applicationId")int applicationId);

    @Update("update application set isPassed=1 where applicationId=#{applicationId}")
    void notPassApplication(@Param("applicationId")int applicationId);

    @Update("update application set isPassed=2,currentBalance=#{currentBalance} " +
            "where applicationId=#{applicationId}")
    void passApplication(@Param("applicationId")int applicationId,@Param("currentBalance") int currentBalance);

    @Update("update application set currentBalance=#{currentBalance} " +
            "where applicationId=#{applicationId}")
    void passMyApplication(@Param("applicationId")int applicationId,@Param("currentBalance") int currentBalance);

    @Select("select mount,audiorUniqueNumber,cateId from application where applicationId=#{applicationId}")
    Map<String,Integer> selectApplicationByApplicationId(@Param("applicationId")int applicationId);

    @Select("select application.*,category.cateName from application left join category on" +
            " application.cateId=category.cateId where (applicationUniqueNumber=#{uniqueNumber} or" +
            " audiorUniqueNumber=#{uniqueNumber}) and (remark like #{keyWord} or" +
            " userName like #{keyWord}) order by applyTime desc")
    List<ApplicationModel> selectSearch(@Param("keyWord")String keyWord,
                                        @Param("uniqueNumber")int uniqueNumber);

    @Select("select a.applicationUniqueNumber,a.audiorUniqueNumber,a.applyTime,a.remark,a.mount,a.isPassed," +
            "a.currentBalance,a.userName,a.image,c.accountId,c.cateName,c.balance,c.cateTotal from application as a " +
            "left JOIN category as c ON a.cateId=c.cateId where a.cateId=#{cateId} and " +
            "(a.isPassed=4 or a.isPassed=3) and c.isDeleted=0 order by a.applyTime desc")
    List<Map<String,Object>> selectApplicationByCateId(@Param("cateId")int cateId);


    @Select("SELECT a.applicationUniqueNumber, a.applyTime, a.mount, a.currentBalance, " +
            "a.userName, a.remark, c.cateName, c.accountId FROM application AS a LEFT JOIN " +
            "category AS c ON a.cateId = c.cateId WHERE a.audiorUniqueNumber = #{audiorUniqueNumber}" +
            " AND ( a.isPassed = 2 OR a.isPassed = 4 ) ORDER BY applyTime DESC")
    List<ExcelForAppModel> selectAllDataByAudiorIdForExcel(@Param("audiorUniqueNumber")int audiorUniqueNumber);



}
