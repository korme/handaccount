package xyz.korme.handaccount.service.dictionaryMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.korme.handaccount.mapper.CategoryMapper;
import xyz.korme.handaccount.model.CateIdAndBalanceModel;
import xyz.korme.handaccount.model.CategoryModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CateDictionaryMapMPL implements CateDictionaryMap {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Map<Integer, String> getDicMap(int uniqueNumber) {
        List<CategoryModel> temp=categoryMapper.selectDicMap(uniqueNumber);
        Map<Integer,String> result = temp.stream().collect(Collectors.toMap(CategoryModel::getCateId,CategoryModel::getCateName));
        return result;
    }

    @Override
    public Map<Integer, Integer> getBalanceMap(int uniqueNumber) {
        List<CateIdAndBalanceModel> temp=categoryMapper.selectBalanceMap(uniqueNumber);
        Map<Integer,Integer> result = temp.stream().collect(Collectors.toMap(CateIdAndBalanceModel::getCateId,CateIdAndBalanceModel::getBalance));
        return result;
    }
}
