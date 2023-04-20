package cn.korostudio.mc.wac.common;

import cn.korostudio.mc.hutoolcore.common.config.ConfigUtil;
import cn.korostudio.mc.wac.common.config.WACConfig;
import cn.korostudio.mc.wac.common.config.WorldConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class WAC {
    @Getter
    private static WACConfig config ;

    public static void init(){
        try {
            Class<?> hutoolcoreClass = Class.forName("cn.korostudio.mc.hutoolcore.common.HutoolCore");
        } catch (ClassNotFoundException e) {
            log.error("警告，未检测到HutoolCore！",e);
            throw new RuntimeException(e);
        }
        config = ConfigUtil.getInstance("WACConfig",WACConfig.class);
    }

    public static WorldConfig getInstance(String id){
        if (config.getWorldConfigs().containsKey(id)){
            return config.getWorldConfigs().get(id);
        }else{
            WorldConfig worldConfig = new WorldConfig();
            worldConfig.setId(id);
            config.getWorldConfigs().put(id,worldConfig);
            ConfigUtil.save("WACConfig");
            return worldConfig;
        }
    }

}
