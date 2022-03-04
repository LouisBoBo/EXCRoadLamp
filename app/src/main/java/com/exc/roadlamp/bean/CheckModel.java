package com.exc.roadlamp.bean;

public class CheckModel {


    /**
     * code : 0
     * message :
     * data : {"buildBuildVersion":"1","forceUpdateVersion":"","forceUpdateVersionNo":"","needForceUpdate":false,"downloadURL":"https://www.pgyer.com/app/installUpdate/2be30f8278a8e3ca5f0b2667d9956f77?sig=tS6rIUEyHNOHZc8M7CJVhOHo0hgOoj1y2VHpGjbwVcBFIWzeg1P0%2FIIBoelbJCsw&forceHttps=","buildHaveNewVersion":false,"buildVersionNo":"1","buildVersion":"1.0.1","buildUpdateDescription":"1、解决了已知BUG\n2、优化了用户体验","appKey":"bfe8b2bb9748c4baae2adcf2cada248f","buildKey":"2be30f8278a8e3ca5f0b2667d9956f77","buildName":"智慧路灯1","buildIcon":"https://www.pgyer.com/image/view/app_icons/148d8e80160422801add65ad67e50e16/120","buildFileKey":"ad7428e2b460959b8a43e0a1ccbf20c0.apk","buildFileSize":"49065122"}
     */

    private int code;
    private String message;
    /**
     * buildBuildVersion : 1
     * forceUpdateVersion :
     * forceUpdateVersionNo :
     * needForceUpdate : false
     * downloadURL : https://www.pgyer.com/app/installUpdate/2be30f8278a8e3ca5f0b2667d9956f77?sig=tS6rIUEyHNOHZc8M7CJVhOHo0hgOoj1y2VHpGjbwVcBFIWzeg1P0%2FIIBoelbJCsw&forceHttps=
     * buildHaveNewVersion : false
     * buildVersionNo : 1
     * buildVersion : 1.0.1
     * buildUpdateDescription : 1、解决了已知BUG
     2、优化了用户体验
     * appKey : bfe8b2bb9748c4baae2adcf2cada248f
     * buildKey : 2be30f8278a8e3ca5f0b2667d9956f77
     * buildName : 智慧路灯1
     * buildIcon : https://www.pgyer.com/image/view/app_icons/148d8e80160422801add65ad67e50e16/120
     * buildFileKey : ad7428e2b460959b8a43e0a1ccbf20c0.apk
     * buildFileSize : 49065122
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String buildBuildVersion;
        private String forceUpdateVersion;
        private String forceUpdateVersionNo;
        private boolean needForceUpdate;
        private String downloadURL;
        private boolean buildHaveNewVersion;
        private String buildVersionNo;
        private String buildVersion;
        private String buildUpdateDescription;
        private String appKey;
        private String buildKey;
        private String buildName;
        private String buildIcon;
        private String buildFileKey;
        private String buildFileSize;

        public String getBuildBuildVersion() {
            return buildBuildVersion;
        }

        public void setBuildBuildVersion(String buildBuildVersion) {
            this.buildBuildVersion = buildBuildVersion;
        }

        public String getForceUpdateVersion() {
            return forceUpdateVersion;
        }

        public void setForceUpdateVersion(String forceUpdateVersion) {
            this.forceUpdateVersion = forceUpdateVersion;
        }

        public String getForceUpdateVersionNo() {
            return forceUpdateVersionNo;
        }

        public void setForceUpdateVersionNo(String forceUpdateVersionNo) {
            this.forceUpdateVersionNo = forceUpdateVersionNo;
        }

        public boolean isNeedForceUpdate() {
            return needForceUpdate;
        }

        public void setNeedForceUpdate(boolean needForceUpdate) {
            this.needForceUpdate = needForceUpdate;
        }

        public String getDownloadURL() {
            return downloadURL;
        }

        public void setDownloadURL(String downloadURL) {
            this.downloadURL = downloadURL;
        }

        public boolean isBuildHaveNewVersion() {
            return buildHaveNewVersion;
        }

        public void setBuildHaveNewVersion(boolean buildHaveNewVersion) {
            this.buildHaveNewVersion = buildHaveNewVersion;
        }

        public String getBuildVersionNo() {
            return buildVersionNo;
        }

        public void setBuildVersionNo(String buildVersionNo) {
            this.buildVersionNo = buildVersionNo;
        }

        public String getBuildVersion() {
            return buildVersion;
        }

        public void setBuildVersion(String buildVersion) {
            this.buildVersion = buildVersion;
        }

        public String getBuildUpdateDescription() {
            return buildUpdateDescription;
        }

        public void setBuildUpdateDescription(String buildUpdateDescription) {
            this.buildUpdateDescription = buildUpdateDescription;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getBuildKey() {
            return buildKey;
        }

        public void setBuildKey(String buildKey) {
            this.buildKey = buildKey;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public String getBuildIcon() {
            return buildIcon;
        }

        public void setBuildIcon(String buildIcon) {
            this.buildIcon = buildIcon;
        }

        public String getBuildFileKey() {
            return buildFileKey;
        }

        public void setBuildFileKey(String buildFileKey) {
            this.buildFileKey = buildFileKey;
        }

        public String getBuildFileSize() {
            return buildFileSize;
        }

        public void setBuildFileSize(String buildFileSize) {
            this.buildFileSize = buildFileSize;
        }
    }
}
