package GUI;

import shared.UserType;

public class UserConstantInformation {
    private static UserConstantInformation instance;

    static {
        instance = new UserConstantInformation();
    }

    UserType userType;

    public static UserConstantInformation getInstance() {
        return instance;
    }
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
