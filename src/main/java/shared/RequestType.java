package shared;

public enum RequestType {
    CHECK_CONNECTION(true),
    GET_WEEKLY_CLASS_DATA(true),
    GET_COURSE_VIEWS(true),
    GET_NOTIFICATIONS(true),
    ADD_RECOMMENDATION(false),
    ADD_CERTIFICATE(false),
    ADD_WITHDRAWAL_REQUEST(false),
    ADD_MAJOR_REQUEST(false),
    ADD_ACCOMMODATION_REQUEST(false),
    GET_PROFESSORS_OF_SELECTED_FACULTY(true),
    GET_MAJORS_STATUS(true),
    ADD_REQUEST_TURN_TO_DEFEND_THE_DISSERTATION(false);

    RequestType(boolean needReply) {
        this.needReply = needReply;
    }

    private boolean needReply;

    public boolean isNeedReply() {
        return needReply;
    }
}
