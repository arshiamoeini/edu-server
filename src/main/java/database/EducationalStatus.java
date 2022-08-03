package database;

import MODELS.ContainMessage;

public enum EducationalStatus implements ContainMessage {
    ALLOWED_TO_REGISTER("Allowed to register"),
    PUT_OF("put of");

    private String message;

    EducationalStatus(String message) {
        this.message = message;
    }

    @Override
    public String getMassage() {
        return message;
    }
}
