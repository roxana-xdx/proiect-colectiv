package backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class ProiectColectivApplication {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/login_schema", "echipanr1", "secretdiscret");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from parent");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }
}
