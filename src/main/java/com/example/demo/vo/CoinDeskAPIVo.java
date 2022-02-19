package com.example.demo.vo;

import java.util.Map;

public class CoinDeskAPIVo {
    private Map<String, Object> time;
    private String disclaimer;
    private String chartName;
    private Map<String, Object> bpi;

    public Map<String, Object> getTime() {
        return time;
    }

    public void setTime(Map<String, Object> time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public Map<String, Object> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, Object> bpi) {
        this.bpi = bpi;
    }
}
