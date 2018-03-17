package nl.gertontenham.rest.core.entity;

public class Todo extends Identity {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    private String name;
    private Boolean isComplete;

}
