package pl.artelaguna.backend.contest.model;

public enum ContestStatus {

    PLANNED,
    STARTED,
    FIRST_PHASE,
    SECOND_PHASE,
    FINISHED,
    ARCHIVED;

    public boolean isActive() {
        return this != PLANNED && this != ARCHIVED;
    }
}
