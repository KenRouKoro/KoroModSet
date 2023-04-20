package cn.korostudio.mc.hutoolcore.common.webconfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebConfig {
    public static void init(){

        log.info("已关闭在线网页配置器，在HutoolCore配置文件中设置UseWebConfig为true即可开启。");
    }
}
