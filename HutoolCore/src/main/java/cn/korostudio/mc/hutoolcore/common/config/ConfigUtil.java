package cn.korostudio.mc.hutoolcore.common.config;

import cn.hutool.core.exceptions.NotInitedException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public class ConfigUtil {
    @Getter
    private static final ConcurrentHashMap<String,Object>configObject = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    public static<T> T getInstance(String name,Class<T> config){
        if (configObject.contains(name)){
            try {
                return (T) configObject.get(name);
            }catch (Exception e){
                log.error("转换配置文件出错！",e);
            }
        }
        if(FileUtil.isFile(new  File(System.getProperty("user.dir")+"/config/"+name+".json"))){
            String configJSON = FileReader.create(new File(System.getProperty("user.dir")+"/config/" + name + ".json"), CharsetUtil.CHARSET_UTF_8).readString();
            T obj = JSONUtil.parseObj(configJSON).toBean(config);
            configObject.put(name,obj);
            log.info("已读取配置文件："+System.getProperty("user.dir")+"/config/"+name+".json");
            return obj;
        }
        T obj = ReflectUtil.newInstance(config);
        FileWriter fileWriter = FileWriter.create(FileUtil.touch(System.getProperty("user.dir")+"/config/"+name+".json"), CharsetUtil.CHARSET_UTF_8);
        fileWriter.write(JSONUtil.parseObj(obj).toStringPretty());
        configObject.put(name,obj);
        log.info("已创建配置文件："+System.getProperty("user.dir")+"/config/"+name+".json");
        return obj;
    }

    public static void saveALL(){
        configObject.forEach((name,obj)->{
            FileWriter fileWriter = FileWriter.create(FileUtil.touch(System.getProperty("user.dir")+"/config/"+name+".json"), CharsetUtil.CHARSET_UTF_8);
            fileWriter.write(JSONUtil.parseObj(obj).toStringPretty());
        });
    }
    public static void save(String name){
        log.info("正在保存："+System.getProperty("user.dir")+"/config/"+name+".json");
        Object value = configObject.get(name);
        if (value == null){
            throw new NotInitedException(name+" 的配置文件没有初始化,保存失败");
        }
        FileWriter fileWriter = FileWriter.create(FileUtil.touch(System.getProperty("user.dir")+"/config/"+name+".json"), CharsetUtil.CHARSET_UTF_8);
        fileWriter.write(JSONUtil.parseObj(value).toStringPretty());
    }

    public static<T> void save(Class<T>name){
        save(name.getSimpleName());
    }

    public static<T> T getInstance(Class<T> config){
        return getInstance(config.getSimpleName(),config);
    }
}
