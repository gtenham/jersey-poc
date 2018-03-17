package nl.gertontenham.rest.resource;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import nl.gertontenham.rest.resource.data.GraphQLInputDTO;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/graphql")
@Singleton
@Produces(MediaType.APPLICATION_JSON)
public class GraphQLResource {

    private final GraphQL graphQL;

    @Inject
    public GraphQLResource(GraphQLQueryResolver queryResolver) {
        GraphQLSchema schema = SchemaParser.newParser()
                .file("schema.graphqls") //parse the schema file created under resources
                .resolvers(queryResolver)
                .build()
                .makeExecutableSchema();

        this.graphQL = GraphQL.newGraphQL(schema).build();

    }

    @GET
    public Response handleGetGraphQL(@QueryParam("query") String query) {
        GraphQLInputDTO graphQLInputDTO = new GraphQLInputDTO();
        graphQLInputDTO.setQuery(query);

        ExecutionResult er = graphQL.execute(
                getExecutionInput(graphQLInputDTO)
        );

        return Response.ok(er).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handlePostGraphQL(GraphQLInputDTO graphQLInputDTO) {

        ExecutionResult er = graphQL.execute(
                getExecutionInput(graphQLInputDTO)
        );

        return Response.ok(er).type(MediaType.APPLICATION_JSON).build();
    }

    private static ExecutionInput getExecutionInput(GraphQLInputDTO graphQLInputDTO) {
        return ExecutionInput.newExecutionInput()
                .query(graphQLInputDTO.getQuery())
                .operationName(graphQLInputDTO.getOperationName())
                .variables(graphQLInputDTO.getVariables())
                .build();
    }
}
