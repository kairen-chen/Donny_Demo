package com.example.demo.controller;

import com.example.demo.enums.DateTimePattern;
import com.example.demo.utils.http.JavaScriptMessageConverter;
import com.example.demo.vo.CoinDeskAPIVo;
import com.example.demo.enums.ResponseCodeEnum;
import com.example.demo.vo.ResponseVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.CoinService;
import com.example.demo.entity.Coin;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/coin")
public class CoinController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CoinService coinService;


    //查詢
    @GetMapping(value = "/findCoinMapping")
    @ResponseBody
    public List<Coin> findCoinMapping() {
        return coinService.findAllCoins();
    }


    //新增
    @PostMapping(value = "/addCoinMapping")
    public ResponseEntity<ResponseVo> addCoinMapping(@RequestBody Coin coin) {
        //Check parameter
        if(Objects.isNull(coin)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseVo.build().rtnCode(ResponseCodeEnum.fail.getCode()).message("參數缺失"));
        }

        //insert
        Boolean result = coinService.insertCoin(coin);

        //return
        return result ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseVo.build().data(coin).rtnCode(ResponseCodeEnum.success.getCode()))
                :
                ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseVo.build().data(coin).rtnCode(ResponseCodeEnum.fail.getCode()));
    }


    //更新
    @PutMapping(value = "/modifyCoinMapping")
    public ResponseEntity<ResponseVo> modifyCoinMapping(@RequestBody Coin coin) {
        //Check parameter
        if(Objects.isNull(coin) || Objects.isNull(coin.getcId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseVo.build().rtnCode(ResponseCodeEnum.fail.getCode()).message("參數缺失"));
        }

        //update
        Boolean result = coinService.updateCoinMapping(coin);

        //return
        return result
                ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseVo.build().data(coin).rtnCode(ResponseCodeEnum.success.getCode()))
                :
                ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseVo.build().rtnCode(ResponseCodeEnum.fail.getCode()));
    }



    //刪除
    @DeleteMapping(value = "/deleteCoinMapping")
    public ResponseEntity<ResponseVo> deleteCoinMapping(@RequestBody Coin coin) {
        //Check parameter
        if(Objects.isNull(coin) || Objects.isNull(coin.getcId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseVo.build().rtnCode(ResponseCodeEnum.fail.getCode()).message("參數缺失"));
        }

        //delete
        Boolean result = coinService.deleteCoinMapping(coin);

        //return
        return result
                ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseVo.build().rtnCode(ResponseCodeEnum.success.getCode()))
                :
                ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseVo.build().rtnCode(ResponseCodeEnum.fail.getCode()));
    }



    //呼叫 coindesk API
    @GetMapping(value = "/coinDeskAPI")
    @ResponseBody
    public ResponseEntity coinDeskAPI() {
        String coinDeskUrl = "https://api.coindesk.com/v1/bpi/currentprice.json";

        //Add a new messageConverter due to the return content-type: application/javascript
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new JavaScriptMessageConverter());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(coinDeskUrl, String.class);

        try {
            Object rtnData = objectMapper.readValue(responseEntity.getBody(), CoinDeskAPIVo.class);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseVo.build().data(rtnData).rtnCode(ResponseCodeEnum.success.getCode()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseVo.build().rtnCode(ResponseCodeEnum.fail.getCode()));
        }
    }



    //6.資料轉換的API
    @GetMapping(value = "/findConvertInfo")
    @ResponseBody
    public Object findConvertInfo() {
        //request data
        ResponseEntity response = this.coinDeskAPI();
        if(Objects.isNull(response) || !response.getStatusCode().equals(HttpStatus.OK)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseVo.build().rtnCode(ResponseCodeEnum.fail.getCode()).message("查詢coinDeskAPI異常"));
        }


        //extract api data
        ResponseVo responseVo = (ResponseVo) response.getBody();
        CoinDeskAPIVo convertedData = objectMapper.convertValue(responseVo.getData(), CoinDeskAPIVo.class);

        //convert update time, for example -> updated
        Map<String, Object> timeMap = convertedData.getTime();
        String updated = MapUtils.getString(timeMap, "updated", "");
        LocalDateTime dateTime = LocalDateTime.parse(updated,
                DateTimeFormatter.ofPattern(DateTimePattern.COIN_DESK_UPDATED.getPattern(), Locale.ENGLISH));

        //get currencyNameMapping
        Map<String, Object> bpiMap = convertedData.getBpi();
        List<String> currencyEnNames = bpiMap.keySet().stream().collect(Collectors.toList());

        //get rate
        Map<String, Object> rateMap = currencyEnNames.stream().collect(Collectors.toMap(
                String::toString,
                enName -> MapUtils.getMap(bpiMap, enName).get("rate_float")));


        //prepare rtn data
        String convertedDateTime =
                DateTimeFormatter.ofPattern(DateTimePattern.RETURN_DATETIME.getPattern()).format(dateTime);
        Map<String, String> currencyNameMappingMap = coinService.findCurrencyCnName(currencyEnNames);

        List<Map<String, Object>> result = bpiMap.keySet().stream().map(enName -> {
            Map<String, Object> map = new HashMap();
            map.put("updated", convertedDateTime);
            map.put("CurrencyEnName", enName);
            map.put("CurrencyCnName", currencyNameMappingMap.get(enName));
            map.put("rate", rateMap.get(enName));
            return map;
        }).collect(Collectors.toList());

        return result;
    }
}
