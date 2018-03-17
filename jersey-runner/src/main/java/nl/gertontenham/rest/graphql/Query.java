package nl.gertontenham.rest.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import nl.gertontenham.rest.core.entity.Todo;
import nl.gertontenham.rest.core.repo.Repository;
import nl.gertontenham.rest.resource.data.TodoDTO;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Singleton
public class Query implements GraphQLQueryResolver {

    private final Repository<Todo> todoRepository;

    @Inject
    public Query(Repository<Todo> todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDTO> getAllTodos() {
        return todoRepository.get()
                .stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());
    }
}
