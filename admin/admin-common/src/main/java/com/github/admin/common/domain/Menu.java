package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Menu implements Serializable {

    private Long id;
    private String title;
    private Long pid;
    private String pids;
    private String url;
    private String perms;
    private String icon;
    //1:目录；2：菜单；3：按钮
    private Integer type;
    private Integer sort;
    private String remark;
    private Date createDate;
    private Date updateDate;
    private Long createBy;
    private Long updateBy;
    private Integer status;

//    @JsonIgnore
    private Map<Long, Menu> childMap = new HashMap<Long, Menu>();

    private Menu pMenu;
    private User createUser;
    private User updateUser;
    

}
