package com.example.demo.repository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Coin;

import java.util.List;
import java.util.Map;

@Repository
@ComponentScan
public interface CoinJpaRepository extends JpaRepository<Coin, Integer> {
    List<Coin> findAll();

    Coin findBycId(Integer cId);

//    @Query(value="select CURRENCY_CN_NAME from Coin where CURRENCY_EN_NAME = :enName", nativeQuery = true)
//    String findCnNameByEnName(String enName);


    @Query(value="select CURRENCY_EN_NAME, CURRENCY_CN_NAME from Coin where CURRENCY_EN_NAME in (:enNames)", nativeQuery = true)
    List<Map<String, String>> findCnNamesByEnNames(List<String> enNames);
}
