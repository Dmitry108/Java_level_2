package server;

import java.sql.*;

public class DataBaseAuthService implements AuthService {
    private Connection cn;
    private Statement stmt;
    private PreparedStatement ppstmt;

    public DataBaseAuthService() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            cn = DriverManager.getConnection("jdbc:sqlite:chatDB.db");
            stmt = cn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void finalize(){
        try {
            stmt.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {
            ppstmt = cn.prepareStatement("SELECT nickName FROM users WHERE login = ? AND password = ?;");
            ppstmt.setString(1, login);
            ppstmt.setString(2, password);
            ResultSet rs = ppstmt.executeQuery();
        //    ResultSet rs = stmt.executeQuery("SELECT nickName FROM users WHERE login = '"+login+"' AND password = '"+password+"';");
            return rs.next()?rs.getString(1):null;
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        try {
            ppstmt = cn.prepareStatement("SELECT login FROM users WHERE login = ?;");
            ppstmt.setString(1, login);
            ResultSet rs = ppstmt.executeQuery();
            //ResultSet rs = stmt.executeQuery("SELECT login FROM users WHERE login = '"+login+"';");
            if (!rs.next()){
                System.out.println(login+password+nickname);
                stmt.executeUpdate("INSERT INTO users (login, password, nickName) VALUES ('"+login+"', '"+password+"', '"+nickname+"');");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void changeNick(String login, String newNick){
        //метод изменения ника добавлен в интерфейс AuthService, вызывается,
        //когда пользователь вводит команду /change,
        //где далее через пробел пишет новый ник
        try {
            ppstmt = cn.prepareStatement("UPDATE users SET nickName = ? WHERE login =?");
            ppstmt.setString(1, newNick);
            ppstmt.setString(2, login);
            ppstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}