import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    Connection connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/skypro",
            "postgres",
            "MASA858chu");

    public EmployeeDAOImpl(Connection connection) throws SQLException {
    }

    @Override
    public void addEmployee(Employee employee) {
        try(PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO employees (first_name, last_name, gender, age, city_id) VALUES ((?), (?), (?), (?), (?))")) {
            statement.setString(1, employee.getFirst_name());
            statement.setString(2, employee.getLast_name());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getCity_id());
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = new Employee();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM employees INNER JOIN city ON employees.city_id = city.city_id AND employees_id=(?)")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                employee.setId(Integer.parseInt(resultSet.getString("id")));
                employee.setFirst_name(resultSet.getString("first_name"));
                employee.setLast_name(resultSet.getString("last_name"));
                employee.setGender(resultSet.getString("gender"));
                employee.setAge(Integer.parseInt(resultSet.getString("age")));
                employee.setCity_id(new City(resultSet.getInt("city_id"),
                        resultSet.getString("name_city")).getCity_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployee() {
        List<Employee> employeesList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM employees INNER JOIN city ON employees.city_id = city.city_id")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = Integer.parseInt(resultSet.getString("id"));
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                int age = Integer.parseInt(resultSet.getString("age"));
                City city = new City(resultSet.getInt("city_id"),
                        resultSet.getString("name_city"));

                employeesList.add(new Employee(id, first_name, last_name, gender, age, city.getCity_id()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeesList;
    }

    @Override
    public void updateEmployeeAgeById(int id, int age) {
        try(PreparedStatement statement = connection.prepareStatement(
                "UPDATE employees SET age=(?) WHERE id=(?)")) {
            statement.setInt(1, age);
            statement.setInt(2, id);

            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployeeById(int id) {
        try(PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM employees WHERE id=(?)")) {
            statement.setInt(1, id);
            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
