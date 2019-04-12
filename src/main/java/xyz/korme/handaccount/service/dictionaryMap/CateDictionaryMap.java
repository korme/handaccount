package xyz.korme.handaccount.service.dictionaryMap;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CateDictionaryMap {
    Map<Integer,String> getDicMap(int uniqueNumber);
    Map<Integer,Integer> getBalanceMap(int uniqueNumber);

}
