package com.github.admin.server.service;
import com.github.admin.common.domain.Menu;
import com.github.framework.core.Result;

import java.util.List;
import java.util.TreeMap;

public interface MenuService {

    Result<TreeMap<Long, Menu>> findMenuByUserId(Long userId);

    Result<List<Menu>> roleMenuAuthList(Long id);
}
