package xyz.korme.handaccount.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import xyz.korme.handaccount.model.ApplicationModel;
import xyz.korme.handaccount.model.CateIdAndBalanceModel;
import xyz.korme.handaccount.model.CategoryModel;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "categoryMapper")
public interface CategoryMapper {
    @Insert("insert into category(cateName,uniqueNumber,balance,createTime,cateTotal,accountId) " +
            "values(#{cateName},#{uniqueNumber},#{balanceTrans},#{createTime},#{cateTotalTrans},#{accountId})")
    void insertcategory(@Param("cateName")String cateName,@Param("uniqueNumber")int uniqueNumber,
                        @Param("balanceTrans")int balanceTrans,@Param("createTime")String createTime,
                        @Param("cateTotalTrans")int cateTotalTrans,@Param("accountId")String accountId);

    @Update("update category set balance=#{balance} where cateId=#{cateId}")
    void updateBalance(@Param("balance")int balance, @Param("cateId")int cateId);

    @Update("update category set balance=#{balance},cateTotal=#{cateTotal} where cateId=#{cateId}")
    void updateBalanceAndCateTotal(@Param("balance")int balance,
                               @Param("cateTotal") int cateTotal,@Param("cateId")int cateId);

    @Select("select cateId,accountId,cateName,uniqueNumber,balance,createTime,cateTotal from category " +
            "where uniqueNumber=#{uniqueNumber} and isDeleted=0")
    List<CategoryModel> selectAllCate(@Param("uniqueNumber")int uniqueNumber);

    @Select("select cateId,cateName,uniqueNumber,balance,createTime from category " +
            "where uniqueNumber=#{uniqueNumber} and isDeleted=0")
    List<Map<String,Object>> selectAllCateMap(@Param("uniqueNumber")int uniqueNumber);

    @Select("select cateId,balance from category where uniqueNumber=#{uniqueNumber}")
    List<CateIdAndBalanceModel> selectBalanceMap(int uniqueNumber);

    @Select("select balance from category where cateId=#{cateId}")
    Integer selectBalanceByCateId(@Param("cateId") int cateId);

    @Select("select cateTotal from category where cateId=#{cateId}")
    Integer selectCateTotalByCateId(@Param("cateId")int cateId);

    @Update("Update category set isDeleted=1 where cateId=#{cateId}")
    void delCateByUniqueNumber(@Param("cateId")int cateId);

    @Select("select cateId,cateName from category where uniqueNumber=#{uniqueNumber} ")
    List<CategoryModel> selectDicMap(@Param("uniqueNumber")int uniqueNumber);

    @Select("select uniqueNumber from category where cateId=#{cateId}")
    Integer selectUniqueNumberByCateId(@Param("cateId")int cateId);

    @Select("select * from (select application.*,category.cateName  from application" +
            " LEFT JOIN category on application.cateId= category.cateId)" +
            " as n where n.cateName like '%' || 'keyWord' || '%' order by n.applyTime desc")
    List<ApplicationModel> selectSearches(String keyWord);



}
