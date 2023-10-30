package com.github.admin.server.service;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.framework.core.Result;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface MenuService {

    Result<TreeMap<Long, Menu>> findMenuByUserId(Long userId);

    Result<List<Menu>> roleMenuAuthList(Long id);

    Result<List<Menu>> findMenuList(MenuRequest menuRequest);

    Result<Map<Integer, String>> findMenuListSort(MenuRequest menuRequest);

    Result<Menu> findMenuById(Long id);

    Result<Integer> saveMenu(MenuRequest menuRequest);

    Result updateMenu(MenuRequest menuRequest);

    Result<Integer> deleteMenuById(Long id);

    Result<Integer> enableMenuStatus(List<Long> ids);

    Result<Integer> disableMenuStatus(List<Long> ids);
}
