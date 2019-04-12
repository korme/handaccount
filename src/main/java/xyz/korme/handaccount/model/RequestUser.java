package xyz.korme.handaccount.model;

public class RequestUser {
    private String code;
    private String nickname;
    private String avatar;

    public RequestUser(String code, String nickname, String avatar, String mobile, String gender) {

        this.code = code;
        this.nickname = nickname;
        this.avatar = avatar;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}