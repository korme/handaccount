package xyz.korme.handaccount.common.response;

public enum RespCode {
    SUCCESS(0, "请求成功"),
    WRONG(1, "请求错误"),
    ERROR_DATABASE(2, "数据库错误"),
    WARN_INTERNET(3, "网络异常，请稍后重试"),
    WARN_ENPTY(4,"所选内容为空"),
    INPUT_ERROR(5,"输入内容有误"),
    WX_ERROR(6,"微信服务器错误"),
    ERROR_TYPE(7,"文件类型错误"),
    ERROR_DATA(8,"该申请单错误，请重新提交");

    private Integer code;
    private String msg;

    RespCode(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
