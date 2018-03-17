package nl.gertontenham.rest.repo;

import nl.gertontenham.rest.core.entity.Todo;
import nl.gertontenham.rest.core.repo.InMemoryRepository;
import org.jvnet.hk2.annotations.Service;

@Service
public class TodoInMemoryRepository extends InMemoryRepository<Todo> {
}
