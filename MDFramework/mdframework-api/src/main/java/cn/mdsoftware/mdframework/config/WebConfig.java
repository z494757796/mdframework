package cn.mdsoftware.mdframework.config;

import cn.mdsoftware.mdframework.common.converter.DateConverter;
import cn.mdsoftware.mdframework.common.handler.JsonReturnHandler;
import cn.mdsoftware.mdframework.interceptor.AuthInterceptor;
import cn.mdsoftware.mdframework.properties.AuthProperties;
import cn.mdsoftware.mdframework.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * web配置
 * @author Jax
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthProperties authProperties;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private MemberService memberService;

	@Bean
	public JsonReturnHandler jsonReturnHandler(){
		return new JsonReturnHandler();//初始化json过滤器
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		MdAccessTokenHolder.redisTemplate =redisTemplate;
		registry.addInterceptor(new AuthInterceptor(authProperties, redisTemplate,memberService))
				.addPathPatterns("/**")
				.excludePathPatterns("/outer/**")
				.excludePathPatterns("/wechat/**")
				.excludePathPatterns("/banner/**")
				.excludePathPatterns("/news/**")
				.excludePathPatterns("/file/**")
				.excludePathPatterns("/baseInfo/**");
//		registry.addInterceptor(new)
		super.addInterceptors(registry);
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).maxAge(3600).allowedMethods("*");
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		returnValueHandlers.add(jsonReturnHandler());
	}

    @Bean
    public DateConverter dateConverter(){
        return new DateConverter();
    }

//	@Override
	/*public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//1.需要定义一个convert转换消息的对象;
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		//2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);


		//3处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		//4.在convert中添加配置信息.
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
		fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		//5.将convert添加到converters当中.
		converters.add(fastJsonHttpMessageConverter);
	}*/
}
