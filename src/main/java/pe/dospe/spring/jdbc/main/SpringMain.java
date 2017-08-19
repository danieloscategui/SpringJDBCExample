package pe.dospe.spring.jdbc.main;

import java.util.List;
import java.util.Random;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import pe.dospe.spring.jdbc.dao.EmployeeDAO;
import pe.dospe.spring.jdbc.model.Employee;

public class SpringMain {

	public static void main(String[] args) {
		//Get the Spring Context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		
		//Get the EmployeeDAO Bean
		//EmployeeDAO employeeDAO = ctx.getBean("employeeDAO", EmployeeDAO.class);
		//To use JdbcTemplate
		EmployeeDAO employeeDAO = ctx.getBean("employeeDAOJDBCTemplate", EmployeeDAO.class);
		
		System.out.println("employeeDAO got!");
		
		//Run some tests for JDBC CRUD operations
		Employee employee = new Employee();
		int rand = new Random().nextInt(1000);
		employee.setId(rand);
		employee.setName("Daniel");
		employee.setRole("Java Developer");
		
		//Create 
		employeeDAO.save(employee);
		
		//Read
		Employee employee2 = employeeDAO.getById(rand);
		System.out.println("Employee retrieved: " + employee2);
		
		//Update
		employee.setRole("CEO");
		employeeDAO.update(employee);
		
		//GetAll
		List<Employee> employeeList = employeeDAO.getAll();
		System.out.println(employeeList);
		
		//Delete
		employeeDAO.deleteById(rand);
		
		//Close Spring Context
		ctx.close();
		
		System.out.println("Done!");
		
	}

}