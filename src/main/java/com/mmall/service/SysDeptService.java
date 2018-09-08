package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 部门Service
 *
 * @Author: wb-yxk397023
 * @Date: Created in 2018/9/8
 */
@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 新增部门
     *
     * @param param
     */
    public void save(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        // 构建SysDept
        SysDept sysDept = SysDept.builder().name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();

        // 设置level
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        // todo
        sysDept.setOperator("system");
        // todo
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setOperateTime(new Date());
        // 保存
        sysDeptMapper.insertSelective(sysDept);
    }

    /**
     * 更新部门
     *
     * @param param
     */
    public void update(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        // 获取更新前的部门
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept after = SysDept.builder().id(param.getId()).name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();
        // 设置level
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        // todo
        after.setOperator("system");
        // todo
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());

        updateWithChild(before, after);
    }

    /**
     * 更新部门核心逻辑
     *
     * @param before
     * @param after
     */
    @Transactional
    private void updateWithChild(SysDept before, SysDept after) {
        // 获取新部门level
        String newLevelPrefix = after.getLevel();
        // 获取老部门level
        String oldLevelPrefix = after.getLevel();
        // 判断是否需要更新子部门(如果一致则不需要更新)
        if (!after.getLevel().equals(before.getLevel())) {
            // 取出before的子部门
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            // 如果不为空则进行处理
            if (CollectionUtils.isNotEmpty(deptList)) {
                // 执行遍历
                for (SysDept dept : deptList) {
                    // 获取当前元素的level
                    String level = dept.getLevel();
                    // 如果以oldLevelPrefix为前缀的话则进行处理
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        // 计算新的level
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        // 更新dept中的level
                        dept.setLevel(level);
                    }
                }
                // 批量更新level
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        // 执行更新
        sysDeptMapper.updateByPrimaryKey(after);
    }

    /**
     * 校验同一层级下是否存在相同名称的部门
     *
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    /**
     * 获取部门level
     *
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId) {
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (sysDept == null) {
            return null;
        }
        return sysDept.getLevel();
    }
}
