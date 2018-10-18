
package com.mnt.preg.master.service.basic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.health.core.exception.ServiceException;
import com.mnt.health.core.utils.HQLConditionParam;
import com.mnt.preg.main.results.ResultCode;
import com.mnt.preg.main.service.BaseService;
import com.mnt.preg.main.utils.SQLSymbol;
import com.mnt.preg.master.dao.basic.TreeDAO;
import com.mnt.preg.master.entity.basic.FoodTree;
import com.mnt.preg.master.pojo.basic.TreePojo;

/**
 * 商品类别事务
 * 
 * @author gss
 * @version 1.0
 * 
 *          变更履历： v1.0 2015-12-18 gss 初版
 */
@Service
public class TreeServiceImpl extends BaseService implements TreeService {

	@Resource
	private TreeDAO treeDAO;

	@Override
	@Transactional(readOnly = true)
	public List<TreePojo> queryTreeByCondition(FoodTree condition) {
		return treeDAO.queryTreeByCondition(condition);
	}

	@Override
	@Transactional(readOnly = true)
	public TreePojo getTree(String catalogId) {
		return treeDAO.getTransformerBean(catalogId, FoodTree.class, TreePojo.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer checkTreeIdValid(String treeId, String treeKind) {
		return treeDAO.countField("treeId", treeId, FoodTree.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer checkTreeNameValid(String catalogName, String treeKind) {
		return treeDAO.checkTreeName(catalogName, treeKind);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer checkTreeNameValidFromBrother(String catalogName, String treeKind) {
		return treeDAO.checkTreeNameFromBrother(catalogName, treeKind);
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public TreePojo addTree(FoodTree tree) {
		String catalogId = addTree_help(tree);
		return getTree(catalogId);
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public String addTree_help(FoodTree tree) {
		// 主键：三位一级，父级编码+排序（补充到三位），例如 一级=001，二级=001001
		tree.setTreeId(createTreeId(tree.getParentTreeId(), tree.getTreeKind()));
		if (checkTreeNameValid(tree.getTreeId(), tree.getTreeKind()) > 0) {
			throw new ServiceException(ResultCode.ERROR_90002);
		}
		return (String) treeDAO.save(tree);
	}

	/**
	 * 生成功能菜单主键
	 * 
	 * @author zcq
	 * @param catalogParent
	 * @return
	 */
	private String createTreeId(String catalogParent, String treeKind) {
		String maxTreeId = (String) treeDAO.getSonMaxTreeId(catalogParent, treeKind);
		String code = StringUtils.isEmpty(maxTreeId) ? "001"
				: String.valueOf(Integer.valueOf(maxTreeId.substring(maxTreeId.length() - 3, maxTreeId.length())) + 1);
		int length = code.length();
		if (length < 3) {
			for (int i = 0; i < 3 - length; i++) {
				code = "0" + code;
			}
		}
		return "000".equals(catalogParent) ? code : (catalogParent + code);
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public void updateTree(FoodTree tree) {
		String treeId = tree.getTreeId();
		if (StringUtils.isEmpty(treeId)) {
			throw new ServiceException(ResultCode.ERROR_90013);
		}
		// 设置检索条件
		List<HQLConditionParam> conditionParams = new ArrayList<HQLConditionParam>();
		conditionParams.add(new HQLConditionParam("tree_id", SQLSymbol.EQ.toString(), treeId));
		conditionParams.add(new HQLConditionParam("tree_kind", SQLSymbol.EQ.toString(), tree.getTreeKind()));
		treeDAO.updateHQL(tree, conditionParams);
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public void deleteTree(String treeId, String treeKind) {
		treeDAO.deleteTreeByTreeId(treeId, treeKind);
	}

	@Override
	@Transactional(rollbackFor = HibernateException.class)
	public void editTreeOrder(List<String> catalogIdList) {
		if (CollectionUtils.isNotEmpty(catalogIdList)) {
			for (int i = 1; i <= catalogIdList.size(); i++) {
				treeDAO.updateTreeOrder(catalogIdList.get(i - 1), i);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<TreePojo> getTree_returnlist(FoodTree condition) {
		return getTreeTreeList(treeDAO.queryTreeByCondition(condition));
	}

	/**
	 * 把所有菜单按父子级分类
	 * 
	 * @author zcq
	 * @return
	 */
	private List<TreePojo> getTreeTreeList(List<TreePojo> treeList) {
		// 把所有菜单按父子级分类
		Map<String, List<TreePojo>> treeMap = new LinkedHashMap<String, List<TreePojo>>();
		if (CollectionUtils.isNotEmpty(treeList)) {
			for (TreePojo tree : treeList) {
				String parentCode = tree.getParentTreeId();
				if (!treeMap.containsKey(parentCode)) {
					treeMap.put(parentCode, new ArrayList<TreePojo>());
				}
				treeMap.get(parentCode).add(tree);
			}
		}
		// 获取一级目录
		List<TreePojo> oneTreeList = treeMap.get("000");
		// 逐级填充完善菜单信息
		if (CollectionUtils.isNotEmpty(oneTreeList)) {
			// 递归填充childList
			for (TreePojo oneTree : oneTreeList) {
				treeRecursion(oneTree, treeMap);
			}
			// 去除没有子级的目录
			// checkTree(oneTreeList);
		}
		return oneTreeList;
	}

	/**
	 * 递归填充childList
	 * 
	 * @author zcq
	 * @param catalog
	 * @param catalogMap
	 */
	private void treeRecursion(TreePojo tree, Map<String, List<TreePojo>> catalogMap) {
		List<TreePojo> treeList = catalogMap.get(tree.getTreeId());
		if (CollectionUtils.isNotEmpty(treeList)) {
			for (TreePojo nextGradeTree : treeList) {
				treeRecursion(nextGradeTree, catalogMap);
			}
			tree.setChildList(treeList);
		}
	}

	@Override
	public Integer checkTreeHaveSub(String treeId, String treeKind) {
		return treeDAO.checkTreeHaveSub(treeId, treeKind);
	}

}
