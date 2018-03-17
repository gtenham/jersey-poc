package nl.gertontenham.rest.resource.data;

import nl.gertontenham.rest.core.entity.Todo;
import org.hibernate.validator.constraints.NotBlank;

public class TodoDTO extends TodoCreateDTO {

    @NotBlank(message = "Identifier can not be omitted!")
    private String id;

    public TodoDTO(){}
    public TodoDTO(Todo entity) {
        this.id = entity.getId();
        setName(entity.getName());
        setComplete(entity.getComplete());
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
