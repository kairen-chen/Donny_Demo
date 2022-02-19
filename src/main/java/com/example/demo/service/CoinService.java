package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CoinJpaRepository;
import com.example.demo.entity.Coin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CoinService{
    @Autowired
    private CoinJpaRepository coinJpaRepository;

    /**
     * 查詢所有幣別
     * @return
     */
    public List<Coin> findAllCoins() {
        return coinJpaRepository.findAll();
    }

    /**
     * 新增幣別
     * @param coin
     * @return
     */
    public Boolean insertCoin(Coin coin) {
        try {
            coinJpaRepository.save(coin);
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * 更新幣別
     * @param coin
     * @return
     */
    public Boolean updateCoinMapping(Coin coin) {
        if(Objects.isNull(coin) || Objects.isNull(coin.getcId())){
            return Boolean.FALSE;
        }

        try {
            //Check if target existed in database
            Coin dbCoin = coinJpaRepository.findBycId(coin.getcId());
            if(Objects.isNull(dbCoin)){
                return Boolean.FALSE;
            }

            coinJpaRepository.save(coin);
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * 刪除幣種
     * @param coin
     * @return
     */
    public Boolean deleteCoinMapping(Coin coin) {
        if(Objects.isNull(coin) || Objects.isNull(coin.getcId())){
            return Boolean.FALSE;
        }

        try {
            coinJpaRepository.delete(coin);
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    /**
     * 依據幣別英文名查找幣別中文名
     * @param currencyEnNames
     * @return
     */
    public Map<String, String> findCurrencyCnName(List<String> currencyEnNames) {
        if(Objects.isNull(currencyEnNames)){
            return new HashMap();
        }

        List<Map<String, String>> result = coinJpaRepository.findCnNamesByEnNames(currencyEnNames);

        return result.stream().filter(Objects::nonNull).collect(Collectors.toMap(
                map -> MapUtils.getString(map, "CURRENCY_EN_NAME", ""),
                map -> MapUtils.getString(map, "CURRENCY_CN_NAME", "")
        ));
    }
}
