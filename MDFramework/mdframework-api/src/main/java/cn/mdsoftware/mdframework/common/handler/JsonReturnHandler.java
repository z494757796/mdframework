package cn.mdsoftware.mdframework.common.handler;

import cn.mdsoftware.mdframework.common.annotation.JSON;
import cn.mdsoftware.mdframework.common.annotation.JSONS;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.*;

public class JsonReturnHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        // 如果有我们自定义的 JSON 注解 就用我们这个Handler 来处理
        boolean hasJsonAnno= returnType.getMethodAnnotation(JSON.class) != null;
        boolean hasJsonSAnno= returnType.getMethodAnnotation(JSONS.class) != null;
        return hasJsonAnno || hasJsonSAnno;
    }


    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        mavContainer.setRequestHandled(true);

        // 获得注解并执行filter方法 最后返回
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annos = returnType.getMethodAnnotations();
        CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();

        JSONS jsons= null;
        for (Object a :Arrays.asList(annos)){
            if (a instanceof JSON) {
                JSON json = (JSON) a;
                jsonSerializer.filter(json.type(), json.include(), json.filter());
            }
            if (a instanceof JSONS) {
                jsons = (JSONS) a;
            }
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        String result=null;
        if (jsons != null ) {
            result = test(returnValue, jsons);
        }
        else
            result = jsonSerializer.toJson(returnValue);
        response.getWriter().write(result);
    }

    public String test(Object object,JSONS jsons){
        PropertyFilter profilter = new PropertyFilter(){
            private Map<Class<?>, Set<String>> includeMap = new HashMap<Class<?>, Set<String>>();
            @Override
            public boolean apply(Object object1, String name, Object value) {
                Set<String> staffsSet = null;
                for (int i = 0;i<jsons.value().length;i++) {

                    if (jsons.value()[i].include() != null && jsons.value()[i].include().length() > 0) {
                        List jsonInclude = Arrays.asList(jsons.value()[i].include().split(","));
                        staffsSet =  new HashSet<>(jsonInclude);
                        staffsSet.add("USE_JSON_INCLUDE");
                    } else if (jsons.value()[i].filter() != null && jsons.value()[i].filter().length() > 0) {
                        List jsonFilter = Arrays.asList(jsons.value()[i].filter().split(","));
                        staffsSet =  new HashSet<>(jsonFilter);
                        staffsSet.add("USE_JSON_FILTER");
                    }


                    includeMap.put(jsons.value()[i].type(),staffsSet);
                }
                for(Map.Entry<Class<?>, Set<String>> entry : includeMap.entrySet()) {
                    Class<?> class1 = entry.getKey();
                    if(object1.getClass() == class1){
                        Set<String> fields = entry.getValue();
                        for(String field : fields) {
                            if(field.equals(name)){
                                if (fields.contains("USE_JSON_INCLUDE"))
                                    return true;
                                if (fields.contains("USE_JSON_FILTER"))
                                    return false;
                            }
                        }
                        if (fields.contains("USE_JSON_INCLUDE"))
                            return false;
                        if (fields.contains("USE_JSON_FILTER"))
                            return true;
                    }
                }
                return true;
            }

        };

        String json = com.alibaba.fastjson.JSON.toJSONString(object, profilter,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
        return json;
    }
}
