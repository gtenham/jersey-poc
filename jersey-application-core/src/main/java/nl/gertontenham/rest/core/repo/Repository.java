package nl.gertontenham.rest.core.repo;

import nl.gertontenham.rest.core.entity.Identity;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Repository<T extends Identity> {

    default Optional<T> get(String id) {
        return get()
                .stream()
                .filter(entity -> entity.getId().equals(id))
                .findAny();
    }

    default Set<T> get(Predicate<T> predicate) {
        return get()
                .stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    Set<T> get();

    void persist(T entity);

    void remove(T entity);

}
