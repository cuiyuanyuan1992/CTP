package org.example.constant;

public enum JobTypeEnum {
    ITEST("接口自动化", "iTest");

    // 成员变量
    private String name;
    private String code;

    // 构造方法
    private JobTypeEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // 普通方法
    public static String getName(String code) {
        for (JobTypeEnum type : JobTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type.name;
            }
        }
        return null;
    }

    public static String getCode(String name) {
        for (JobTypeEnum type : JobTypeEnum.values()) {
            if (type.getName().equals(name)) {
                return type.code;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
