package com.gxx.back.bean;

public class Menu {
    private int menuId;
    private String menuName;
    private String menuPicClass;
    private String menuUrl;
    private int parentId;
    private int sort;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPicClass() {
        return menuPicClass;
    }

    public void setMenuPicClass(String menuPicClass) {
        this.menuPicClass = menuPicClass;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
