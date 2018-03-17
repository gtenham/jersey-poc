package nl.gertontenham.rest.resource;

import nl.gertontenham.rest.core.entity.Todo;
import nl.gertontenham.rest.core.repo.Repository;
import nl.gertontenham.rest.resource.data.TodoCreateDTO;
import nl.gertontenham.rest.resource.data.TodoDTO;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/todos")
@Singleton
public class TodoResource {

    private final Repository<Todo> todoRepository;

    @Inject
    public TodoResource(Repository<Todo> todoRepository) {
        this.todoRepository = todoRepository;
        Todo todo = new Todo();
        todo.setName("GraphQL combined with Jersey resource");
        todo.setComplete(false);
        this.todoRepository.persist(todo);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TodoDTO> getAllTodos() {
        return todoRepository.get()
                .stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodoById(@PathParam("id") String id) {
        TodoDTO todo = todoRepository.get(id)
                        .map(TodoDTO::new)
                        .orElseThrow(NotFoundException::new);

        return Response.ok(todo).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTodo(@Valid TodoCreateDTO data) {
        Todo todo = new Todo();
        todo.setName(data.getName());
        todo.setComplete(data.getComplete());

        todoRepository.persist(todo);

        return Response.created(this.getTodoById(todo.getId()).getLocation()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTodo(@PathParam("id") String id, @Valid TodoDTO data) {
        Todo todo = todoRepository.get(id).orElseThrow(NotFoundException::new);
        todo.setName(data.getName());
        todo.setComplete(data.getComplete());
        todoRepository.persist(todo);

        return Response.ok(data).type(MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTodo(@PathParam("id") String id) {
        todoRepository.remove(todoRepository.get(id).orElseThrow(NotFoundException::new));
        return Response.noContent().build();
    }
}
