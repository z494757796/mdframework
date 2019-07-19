package cn.mdsoftware.mdframework.dao;

import java.util.List;
import java.util.Map;

public interface GeneratorDao {

	List<Map<String, Object>> list();

	int count(Map<String, Object> map);

	Map<String, String> get(String tableName);

	List<Map<String, String>> listColumns(String tableName);
}
