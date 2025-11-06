package backend;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.Arrays;

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
