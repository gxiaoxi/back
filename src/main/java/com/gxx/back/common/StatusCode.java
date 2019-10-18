package com.gxx.back.common;

/**
 * 返回状态码枚举类
 */
public enum StatusCode {
    Success(200,"成功"),
    Fail(0,"失败"),
    UserHasRole(1,"角色已被用户绑定"),
    UserNameExit(2,"用户名已存在"),
    BrandHasSort(3,"分类已被品牌绑定"),
    GoodsHasBrand(4,"品牌已被商品绑定"),
    BrandNameExit(5,"分类下品牌名已存在"),
    BrandIdExit(6,"该品牌商品已存在"),
    FileIsNone(7,"文件为空"),
    SortNameExit(8,"分类名称已存在");
    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
