package org.morehouse.app.resources;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/todos")
public class TodosResource {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	
	public TodosResource() {
		/* Create EntityManagerFactory */
		 emf = Persistence.createEntityManagerFactory("JPAExamples");
		
		/* Create EntityManager. App will use this to query database. */
		 em = emf.createEntityManager();
	}

    // This is given as bonus. Use this as example to configure the 
    // rest of the endpoints below.
    // To test: http://localhost:9090/morehouse/restapp/todos
	//   HTTP method: GET
    //   Accept:  application/json   or application/xml
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<Todo> getAllTodos() {
		List<Todo>  todos = em.createQuery("SELECT t FROM Todo t", Todo.class).getResultList();
		return todos;
	}
	
	// TODO: Supply the annotation for a GET request
	// TODO: Supply the path annotation so that the todo id 
	//       can be passed as parameter in the URL
	// TODO: Supply the annotation so that the Todo returned by
	//       this method is converted to either JSON or XML, 
	//       depending on client request. 
	// To test: http://localhost:9090/morehouse/restapp/todos/1
	//   HTTP method: GET
	// Headers:
    //      Accept:  application/json   or application/xml
	public Todo getTodo(@PathParam("id") int id) {
		// Retrieve a row from the todos table in the Derby database
		// using the id passed by client via URL
		Todo country = em.find(Todo.class, id);
		return country;
	}
	
	// TODO: Supply the annotation for a POST request
	// TODO: Supply the annotation so that the client passes a JSON
	//       object in the body of the POST request
	// TODO: Supply the annotation so that the Todo returned by
	//       this method is converted to a JSON object
	// To test: http://localhost:9090/morehouse/restapp/todos
	// HTTP method: POST
	// Body:  {"title":"Of Mice and Men", "status":"IN", "dueDate":"2018-02-12", "comment":"checked-in ontime", "assignee":"Jimmy Wong"}
	//         Note that id will be auto-generated 
	//            in the Todos database table
	// Headers:
	//       Content-Type: application/json
	//       Accept: application/json
	public Response createTodo(Todo todo) {
		// Save the todo in the database
		em.getTransaction().begin();
		em.persist(todo);
		em.getTransaction().commit();
		// Retrieve the todo we just created and return it.
		return Response.status(201).entity(em.find(Todo.class, todo.getId())).build();
	}
	
	// TODO: Supply the annotation for a PUT request
	// TODO: Supply the annotation so that the client passes a JSON
	//       object in the body of the PUT request
	// TODO: Supply the annotation so that the Todo returned by
	//       this method is converted to a JSON object
	// To test: http://localhost:9090/morehouse/restapp/todos
	// HTTP method: PUT
	// Body:  {"id":8, "title":"Of Mice and Men", "status":"IN", "dueDate":"2018-03-17", "comment":"checked-in ontime", "assignee":"Dana Robinson"}
	//        (This assumes there is a row in the Todos database table
	//        with id = 8)
	// Headers:
	//     Content-Type: application/json
	//     Accept: application/json
	public Todo updateTodo(Todo todo) {
		em.getTransaction().begin();
		em.merge(todo); 
		em.getTransaction().commit();
		// Retrieve the todo we just updated and return it.
		return em.find(Todo.class, todo.getId());
	}
	
	// TODO: Supply the annotation for a DELETE request
    // TODO: Supply the path annotation so that the todo id 
	//       can be passed as parameter in the URL
	// TODO: Supply the annotation so that the Todo returned by
	//       this method is converted to a JSON object
	// To test: http://localhost:9090/morehouse/restapp/todos/<todo id>
	// HTTP method: DELETE
	// Headers:
	//      Accept: application/json
    public Response deleteTodo(@PathParam("id") int id) {
		em.getTransaction().begin();
        em.remove(em.find(Todo.class, id)); 
        em.getTransaction().commit();
        return Response.ok().entity("row " + id + " deleted").build();
    }
	
}
