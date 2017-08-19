package pe.dospe.spring.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import pe.dospe.spring.jdbc.model.Employee;

public class EmployeeDAOJDBCTemplateImpl implements EmployeeDAO {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public void save(Employee employee) {
		String query = "insert into employee(id, name, role) values (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		Object[] args = new Object[] {employee.getId(), employee.getName(), employee.getRole()};
		
		int out = jdbcTemplate.update(query, args);
		if (out != 0) {
			System.out.println("Employee saved with id= " + employee.getId());
		} else {
			System.out.println("Employee save failed with id= "+ employee.getId());
		}
	}

	public Employee getById(int id) {
		String query = "select id, name, role from Employee where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		//Using RowMapper anonyomus class, we can create a separate RowMapper for reuse
		Employee employee = jdbcTemplate.queryForObject(query, new Object[]{id}, new RowMapper<Employee>(){
			public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setId(rs.getInt("id"));
				employee.setName(rs.getString("name"));
				employee.setRole(rs.getString("role"));
				return employee;
				
			}
		});
		return employee;
	}

	public void update(Employee employee) {
		String query = "update Employee set name=?, role=? where id=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = new Object[]{employee.getName(), employee.getRole(), employee.getId()};
		
		int out = jdbcTemplate.update(query, args);
		if (out != 0) {
			System.out.println("Employee updated with id= " + employee.getId());
		} else {
			System.out.println("No Employee found with id= "+ employee.getId());
		}
	}

	public void deleteById(int id) {
		String query = "delete from Employee where id = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		int out = jdbcTemplate.update(query, id);
		if (out != 0) {
			System.out.println("Employee saved with id= " + id);
		} else {
			System.out.println("Employee save failed with id= "+ id);
		}
	}

	public List<Employee> getAll() {
		String 	query = "Select * from Employee";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Employee> employeeList = new ArrayList<Employee>();
		
		List<Map<String, Object>> employeeRows = jdbcTemplate.queryForList(query);
		
		for (Map<String, Object> map : employeeRows) {
			Employee employee = new Employee();
			employee.setId(Integer.parseInt(String.valueOf(map.get("id"))));
			employee.setName(String.valueOf(map.get("name")));
			employee.setRole(String.valueOf(map.get("role")));
			employeeList.add(employee);
		}
		
		return employeeList;
	}

}
