package shared;

import MODELS.ContainMessage;

public enum MasterLevel implements ContainMessage {
    ASSISTANT_PROFESSOR("Assistant Professor"),
    ASSOCIATEـPROFESSOR("Associate Professor"),
    FULL_PROFESSOR("full Professor");
    public String message;

    MasterLevel(String message) {
        this.message = message;
    }

    @Override
    public String getMassage() {
        return message;
    }
}
