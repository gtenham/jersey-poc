package nl.gertontenham.rest.app.config;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import nl.gertontenham.rest.core.entity.Todo;
import nl.gertontenham.rest.core.repo.Repository;
import nl.gertontenham.rest.graphql.Query;
import nl.gertontenham.rest.repo.TodoInMemoryRepository;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // Bind Repositories for DI
        bind(TodoInMemoryRepository.class)
                .to(new TypeLiteral<Repository<Todo>>(){}.getType()).in(Singleton.class);

        bind(Query.class).to(GraphQLQueryResolver.class).in(Singleton.class);
    }
}
