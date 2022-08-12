package shared;

import MODELS.ContainMessage;

public enum Program implements ContainMessage {
    UNDERGRADUATE("undergraduate"),
    MASTER_DEGREE("mater degree"),
    COMMON("common"),
    PHD("PHD");
    private String name;

    Program(String name) {
        this.name = name;
    }

    @Override
    public String getMassage() {
        return name;
    }
}
