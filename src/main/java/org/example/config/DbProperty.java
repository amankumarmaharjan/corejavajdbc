package org.example.config;

public class DbProperty {
    String dbURl;
    String dbPassword;
    String dbUserName;

    public DbProperty(dbPropertyBuilder builder) {
        this.dbURl = builder.dbURl;
        this.dbPassword = builder.dbPassword;
        this.dbUserName = builder.dbUserName;
    }

    public String getDbURl() {
        return dbURl;
    }

    public void setDbURl(String dbURl) {
        this.dbURl = dbURl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public  static  class dbPropertyBuilder {
        private String dbURl;
        private String dbPassword;
        private String dbUserName;

        DbProperty build(){
           return new DbProperty(this);
        }

        public dbPropertyBuilder dbURl(String dbURl) {
            this.dbURl = dbURl;
            return this;
        }

        public dbPropertyBuilder dbPassword(String dbPassword) {
            this.dbPassword = dbPassword;
            return this;

        }
        public dbPropertyBuilder dbUserName(String dbUserName) {
            this.dbUserName = dbUserName;
            return this;
        }

    }

    @Override
    public String toString() {
        return "DbProperty{" +
                "dbURl='" + dbURl + '\'' +
                ", dbPassword='" + dbPassword + '\'' +
                ", dbUserName='" + dbUserName + '\'' +
                '}';
    }
}
