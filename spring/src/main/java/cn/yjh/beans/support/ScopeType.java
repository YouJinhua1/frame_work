package cn.yjh.beans.support;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-08 19:49
 */
public enum ScopeType {
    SINGLETON("singleton"),PROTOTYPE("prototype");
    ScopeType(String value){
        this.value = value;
    }
    private String value;

    public ScopeType getType(String value){
        switch (value){
            case "singleton":return SINGLETON;
            case "prototype":return PROTOTYPE;
            default:return null;
        }
    }
}
