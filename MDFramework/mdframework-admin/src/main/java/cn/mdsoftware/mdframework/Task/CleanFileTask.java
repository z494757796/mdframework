package cn.mdsoftware.mdframework.Task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class CleanFileTask {

	@Value("${file.op.localFilePath}")
	private String localFilePath;
	
	//@Scheduled(cron="0 0/1 * * * *")
//	@Scheduled(cron = "${clean-file-cron}")
//	public void cleanMission() {
//		File file = new File(localFilePath);
//		deleteAll(file);
//	}
//
//	public static void deleteAll(File file) {
//		if (file.isFile() || file.list().length == 0) {
//			file.delete();
//		} else {
//			File[] files = file.listFiles();
//			for (int i = 0; i < files.length; i++) {
//				deleteAll(files[i]);
//				files[i].delete();
//			}
//		}
//	}
}
