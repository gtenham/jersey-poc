package nl.gertontenham.rest.resource.data;

import org.hibernate.validator.constraints.NotBlank;

public class TodoCreateDTO {

    @NotBlank(message = "There must be a value for name!")
    private String name;
    private Boolean isComplete;

    public TodoCreateDTO(){}

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
}
