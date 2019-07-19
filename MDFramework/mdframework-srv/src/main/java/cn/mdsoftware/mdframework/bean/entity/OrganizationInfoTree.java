package cn.mdsoftware.mdframework.bean.entity;

import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-04-03 17:45:28
 */
public class OrganizationInfoTree implements Serializable {
	private static final long serialVersionUID = 1L;

	private String text;
	private String href;
	private String[] tags = new String[]{"0"};
	private List<OrganizationInfoTree> nodes;


	public static List<OrganizationInfoTree> buildTree(List<OrganizationInfoDO> columnInfos, int parentId){
		if (CollectionUtils.isEmpty(columnInfos)) return new ArrayList<>();
		List<OrganizationInfoTree> result = new LinkedList<>();
		for (OrganizationInfoDO columnInfoDO : columnInfos) {
			if (null != columnInfoDO.getParentId() && parentId == columnInfoDO.getParentId().intValue()) {
				OrganizationInfoTree columnInfoTree = new OrganizationInfoTree();
				columnInfoTree.setText(columnInfoDO.getName());
				columnInfoTree.setHref(columnInfoDO.getId().toString());
				columnInfoTree.setNodes(buildTree(columnInfos,columnInfoDO.getId()));
				columnInfoTree.setTags(new String[]{String.valueOf(columnInfoTree.getNodes().size())});
				result.add(columnInfoTree);
			}
		}
		return result;
	}



	public static List<OrganizationInfoTree> buildTree(List<OrganizationInfoDO> columnInfos){
		if (CollectionUtils.isEmpty(columnInfos)) return new ArrayList<>();
		List<OrganizationInfoTree> result = new LinkedList<>();
		for (OrganizationInfoDO columnInfoDO : columnInfos) {
			if (null != columnInfoDO.getParentId() && 0 == columnInfoDO.getParentId().intValue()) {
				OrganizationInfoTree columnInfoTree = new OrganizationInfoTree();
				columnInfoTree.setNodes(columnInfoTree.build(columnInfoTree.getNodes(),columnInfos,columnInfoDO.getId()));
				result.add(columnInfoTree);
			}

		}
		return result;
	}

	public  List<OrganizationInfoTree> build(List<OrganizationInfoTree> nodes, List<OrganizationInfoDO> columnInfos, int parentId){
		if (CollectionUtils.isEmpty(nodes))
			nodes = new LinkedList<>();
		List<OrganizationInfoTree> result = new LinkedList<>();
		for (OrganizationInfoDO columnInfoDO : columnInfos) {
			if (null != columnInfoDO.getParentId() && parentId == columnInfoDO.getParentId().intValue()) {
				OrganizationInfoTree columnInfoTree = new OrganizationInfoTree();
				columnInfoTree.setNodes(columnInfoTree.build(columnInfoTree.getNodes(),columnInfos,columnInfoDO.getId()));
				nodes.add(columnInfoTree);
			}

		}
		return nodes;
	}



	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public List<OrganizationInfoTree> getNodes() {
		return nodes;
	}

	public void setNodes(List<OrganizationInfoTree> nodes) {
		this.nodes = nodes;
	}
}
