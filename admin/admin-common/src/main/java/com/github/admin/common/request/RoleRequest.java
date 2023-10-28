package com.github.admin.common.request;

import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleRequest extends BaseRequest {

    @NotNull(message = "角色id不能为空",groups = {UpdateGroup.class})
    private Long id;
    @NotEmpty(message = "角色编号不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private String title;
    @NotEmpty(message = "角色名称不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private String name;
    @NotNull(message = "角色状态不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    private Integer status;
    private String remark;
    private Long createBy;
    private Long updateBy;
    private List<Long> menuIds;

}
