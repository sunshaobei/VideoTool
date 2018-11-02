package com.ccee.videotool.model.entities.response;

public class VideoSTSBean {
    /**
     * result : true
     * message : 调用成功
     * data : {"AccessKeyId":"STS.NK1adeBxGynVCGpPPC3G9g7E6","AccessKeySecret":"EroWyeEePEhXyosuGNi88qBKJsxHJHfpSDkoi9BXbDGs","SecurityToken":"CAISkwJ1q6Ft5B2yfSjIr4iEKt7Rr6dmzqy9QWHBtFAWP8gViPLu1Dz2IHhFdHNvBuwZtP4/nWBV6vYflol4EsNIS0LNYZYs5Z8L+lmsataY5JXss+wK1MP+F2OfW0ZBJUpu4aGrIunGc9KBNnrm9EYqs5aYGBymW1u6S+7r7bdsctUQWCShcDNCH604DwB+qcgcRxCzXLTXRXyMuGfLC1dysQdRkH527b/FoveR8R3Dllb3uIR3zsbTWsH/P5E9Zs0hC4ntg7IuKfr7vXQOu0QQxsBfl7dZ/DrLhNaZDmRK7g+OW+iuqYU+fFUmOfRlQfQU9aihya0loJDak4Xm1hBWIfpZXirWWYS82szAFfNv3ymajCAqURqAATpUDOq+zsaZAidak9BcsHedm9oMJdn95urGwrvRYoErtohDUfmfZgYlcQadJ4ukKqL7H9u9AUTgp7Qet9VpCT4SBx2++0mmcgqOFK8UcWVByOLgtdf4jFaNTsoHy8xcoeUlOV+jU4HcS77OEPin7kMunc1QzMaOivh3DOHnQw8+","Expiration":"2018-10-25 17:43:15"}
     */

    private boolean result;
    private String message;
    private DataBean data;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
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
        /**
         * AccessKeyId : STS.NK1adeBxGynVCGpPPC3G9g7E6
         * AccessKeySecret : EroWyeEePEhXyosuGNi88qBKJsxHJHfpSDkoi9BXbDGs
         * SecurityToken : CAISkwJ1q6Ft5B2yfSjIr4iEKt7Rr6dmzqy9QWHBtFAWP8gViPLu1Dz2IHhFdHNvBuwZtP4/nWBV6vYflol4EsNIS0LNYZYs5Z8L+lmsataY5JXss+wK1MP+F2OfW0ZBJUpu4aGrIunGc9KBNnrm9EYqs5aYGBymW1u6S+7r7bdsctUQWCShcDNCH604DwB+qcgcRxCzXLTXRXyMuGfLC1dysQdRkH527b/FoveR8R3Dllb3uIR3zsbTWsH/P5E9Zs0hC4ntg7IuKfr7vXQOu0QQxsBfl7dZ/DrLhNaZDmRK7g+OW+iuqYU+fFUmOfRlQfQU9aihya0loJDak4Xm1hBWIfpZXirWWYS82szAFfNv3ymajCAqURqAATpUDOq+zsaZAidak9BcsHedm9oMJdn95urGwrvRYoErtohDUfmfZgYlcQadJ4ukKqL7H9u9AUTgp7Qet9VpCT4SBx2++0mmcgqOFK8UcWVByOLgtdf4jFaNTsoHy8xcoeUlOV+jU4HcS77OEPin7kMunc1QzMaOivh3DOHnQw8+
         * Expiration : 2018-10-25 17:43:15
         */

        private String AccessKeyId;
        private String AccessKeySecret;
        private String SecurityToken;
        private String Expiration;

        public String getAccessKeyId() {
            return AccessKeyId;
        }

        public void setAccessKeyId(String AccessKeyId) {
            this.AccessKeyId = AccessKeyId;
        }

        public String getAccessKeySecret() {
            return AccessKeySecret;
        }

        public void setAccessKeySecret(String AccessKeySecret) {
            this.AccessKeySecret = AccessKeySecret;
        }

        public String getSecurityToken() {
            return SecurityToken;
        }

        public void setSecurityToken(String SecurityToken) {
            this.SecurityToken = SecurityToken;
        }

        public String getExpiration() {
            return Expiration;
        }

        public void setExpiration(String Expiration) {
            this.Expiration = Expiration;
        }
    }
}
