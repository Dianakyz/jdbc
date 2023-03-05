import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws SQLException {

        try(Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/skypro",
                "postgres",
                "MASA858chu")) {
            Statement statement = connection.createStatement();
            statement.execute("SELECT first_name, last_name, gender, age, city_id FROM \"employees\" WHERE id = 1");
            statement.getResultSet().next();
            System.out.println(statement.getResultSet().getString(1) +
                    " " +
                    statement.getResultSet().getString(2) +
                            " " +
                            statement.getResultSet().getString(3) +
                            " " +
                            statement.getResultSet().getInt(4) +
                            " " +
                            statement.getResultSet().getString(5)
                    );

            EmployeeDAO employeeDAO = new EmployeeDAOImpl(connection);

            City mos = new City(1, "Moscow");
            Employee employee1 = new Employee(1, "Max", "Popov", "male", 44, mos.getCity_id());

            employeeDAO.addEmployee(employee1);

            List<Employee> list = new ArrayList<>(employeeDAO.getAllEmployee());
            for(Employee employee: list) {
                System.out.println(employee);
            }
        }
    }
}
