package com.caiwo.caiwoapp.login.bean;

public class LoginBean extends BaseBean {

    private LoginData data;

    public void setData(LoginData data) {
        this.data = data;
    }

    public LoginData getData() {
        return data;
    }

    public class LoginData {

        private String user_id;
        private String username;
        private String mobile;
        private String token;

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUsername() {
            return username;
        }

        public String getMobile() {
            return mobile;
        }

        public String getToken() {
            return token;
        }
    }
}
