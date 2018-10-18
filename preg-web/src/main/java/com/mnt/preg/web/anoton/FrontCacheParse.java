
package com.mnt.preg.web.anoton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import com.mnt.health.utils.files.FileUtils;

/**
 * 解析注解FrontCatch
 * 
 * @author ltb
 * @version 1.0
 * 
 *          变更履历：
 *          v1.0 2015-4-13 ltb 初版
 */
public class FrontCacheParse {

    /**
     * 扫描的包列表
     */
    private static String[] packageList = {"com.mnt.preg"};

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Map<String, String>> parsing() {
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();

        // 循环所有要扫描到的包 获得 这里的所有的类
        List<Class<?>> allclass = new ArrayList<Class<?>>();
        for (String pac : packageList) {
            Set<Class<?>> sets = FileUtils.getClasses(pac);
            allclass.addAll(sets);
        }
        // 循环所有类 如果已经标注上了 FrontCache 做解析
        Map<String, String> _tmpMap = null;
        for (Class clz : allclass) {
            // System.out.println(clz.getName() + ": "+ clz.isAnnotationPresent(FrontCache.class));
            if (clz.isAnnotationPresent(FrontCache.class)) {
                FrontCache fc = (FrontCache) clz.getAnnotation(FrontCache.class);
                String space = fc.space();// 获得空间
                String type = fc.type();// 获得类型
                _tmpMap = getAllParames(clz);// 解析属性和值
                if ("jsp".equals(type)) {
                    for (Map.Entry<String, String> _fmap : _tmpMap.entrySet()) {
                        String key = _fmap.getKey();
                        _tmpMap.put(key, "/page" + _fmap.getValue() + ".jsp");
                    }
                }
                if (_tmpMap == null) {
                    continue;
                }
                if (result.containsKey(space)) {// 如果 结果里已经有该空间的值
                    Map<String, String> old_map = result.get(space);
                    _tmpMap.putAll(old_map);
                }
                result.put(space, _tmpMap);
            }
        }
        return result;
    }

    /**
     * 获得类的所有属性和属性值
     * 
     * @author ltb
     * @param clazz
     */
    @SuppressWarnings("rawtypes")
    private static Map<String, String> getAllParames(Class clazz) {
        Map<String, String> result = new HashMap<String, String>();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field fd : fields) {
                if (fd.isAnnotationPresent(XmlTransient.class)) {
                    continue;
                }
                result.put(fd.getName(), (String) fd.get(clazz));
            }
        } catch (Exception e) {
            System.out.println("解析值失败");
            return null;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        Map<String, Map<String, String>> res = parsing();
        for (Map.Entry<String, Map<String, String>> re : res.entrySet()) {
            System.out.println(re.getKey());
            for (Map.Entry<String, String> con : re.getValue().entrySet()) {
                System.out.println(con.getKey() + ":  " + con.getValue());
            }

            System.out.println("---------------------------------------------");
        }
    }
}
