package xyz.korme.handaccount.model;

public class ApplicationModel implements Comparable<ApplicationModel>{
    private Integer applicationId;
    private Integer applicationUniqueNumber;
    private Integer audiorUniqueNumber;
    private String applyTime;
    private Integer cateId;
    private String remark;
    private String mount;
    private Integer isPassed;
    private Integer currentBalance;
    private String image;
    private String userName;
    private Integer mountTrans;
    private String cateName;
    private String afterBalance;

    public ApplicationModel() {
    }

    public ApplicationModel(Integer applicationUniqueNumber, Integer audiorUniqueNumber,
                            String applyTime, Integer cateId, Integer mountTrans, Integer isPassed,
                            Integer currentBalance) {
        this.applicationUniqueNumber = applicationUniqueNumber;
        this.audiorUniqueNumber = audiorUniqueNumber;
        this.applyTime = applyTime;
        this.cateId = cateId;
        this.mountTrans = mountTrans;
        this.isPassed = isPassed;
        this.currentBalance = currentBalance;
    }

    @Override
    public int compareTo(ApplicationModel o) {
        return o.applyTime.compareTo(this.applyTime);
    }

    public String getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(String afterBalance) {
        this.afterBalance = afterBalance;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getMountTrans() {
        return mountTrans;
    }

    public void setMountTrans(Integer mountTrans) {
        this.mountTrans = mountTrans;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getApplicationUniqueNumber() {
        return applicationUniqueNumber;
    }

    public void setApplicationUniqueNumber(Integer applicationUniqueNumber) {
        this.applicationUniqueNumber = applicationUniqueNumber;
    }

    public Integer getAudiorUniqueNumber() {
        return audiorUniqueNumber;
    }

    public void setAudiorUniqueNumber(Integer audiorUniqueNumber) {
        this.audiorUniqueNumber = audiorUniqueNumber;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public Integer getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Integer isPassed) {
        this.isPassed = isPassed;
    }

    public Integer getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Integer currentBalance) {
        this.currentBalance = currentBalance;
    }
}
