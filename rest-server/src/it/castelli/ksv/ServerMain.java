package it.castelli.ksv;

import java.sql.SQLException;

public class ServerMain {
    public static void main(String[] args) {
        try {
            new RestService().start();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
