package cn.mdsoftware.mdframework;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("cn.mdsoftware.mdframework.dao")
@SpringBootApplication
public class MDFrameworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MDFrameworkApiApplication.class, args);
	}
}
