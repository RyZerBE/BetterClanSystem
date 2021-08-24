package baubolp.clans.util;

import baubolp.clans.Clans;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;

public class MySQL {

    private static final List<MySQL> OPEN = new ArrayList<>();
    private static final ThreadLocal<MySQL> THREAD_LOCAL = new ThreadLocal<>();
    private Connection connection;
    private Map<ResultSet, Statement> statementMap;

    private MySQL(String url) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "sql.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            this.connection = DriverManager.getConnection(url, prop.getProperty("user"), prop.getProperty("password"));
            this.statementMap = new HashMap<>();
            OPEN.add(this);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
    }


    public static void createAsync(Consumer<MySQL> mySQLConsumer, Consumer<IOException> exceptionHandler, String url, boolean useSSL) {
        Clans.getInstance().runAsync(() -> {
            try {
                MySQL mySQL = new MySQL(url + "?useSSL=" + useSSL);
                mySQLConsumer.accept(mySQL);
                mySQL.close();
            } catch (IOException e) {
                exceptionHandler.accept(e);
            }
        });
    }
    public static void createAsync(Consumer<MySQL> mySQLConsumer, Consumer<IOException> exceptionHandler, String url) {
        Clans.getInstance().runAsync(() -> {
            try {
                if(url == null) {
                    MySQL mySQL = new MySQL("jdbc:mysql://localhost/BetterClans?autoReconnect=true&useSSL=false");
                    mySQLConsumer.accept(mySQL);
                    mySQL.close();
                }else {
                    MySQL mySQL = new MySQL(url + "?autoReconnect=true&useSSL=false");
                    mySQLConsumer.accept(mySQL);
                    mySQL.close();
                }
            } catch (IOException e) {
                exceptionHandler.accept(e);
            }
        });
    }

    public static MySQL current(String url) {
        try {
            return new MySQL(url);
        } catch (IOException e) {
            return null;
        }
    }

    public static void closeAll() {
        OPEN.forEach(MySQL::close);
    }

    public ResultSet executeQuery(String sql) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            this.statementMap.put(resultSet, statement);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void execute(String sql) {
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeSet(ResultSet resultSet) {
        try {
            this.statementMap.remove(resultSet);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            for (ResultSet resultSet : this.statementMap.keySet()) {
                this.closeSet(resultSet);
            }
            this.connection.close();
            OPEN.remove(this);
            THREAD_LOCAL.set(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
