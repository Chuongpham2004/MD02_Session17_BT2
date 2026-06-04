import java.sql.*;

public class TaskManagement {
    private static final String URL = "jdbc:postgresql://localhost:5432/todo_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "200426";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addTask(String task_name, String status) {
        String sqlAdd = "CALL add_task(?,?)";
        CallableStatement callableStatement = null;
        try (Connection conn = getConnection(); CallableStatement callableStatement1 = conn.prepareCall(sqlAdd)) {
            callableStatement1.setString(1, task_name);
            callableStatement1.setString(2, status);
            callableStatement1.execute();
            System.out.println("=> Them task thanh cong");
        } catch (SQLException e) {
            System.err.println("=> Loi khi them task: " + e.getMessage());
        }
    }

    public void listTasks() {
        String sqlList = "SELECT * FROM list_tasks()";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sqlList);
             ResultSet rs = preparedStatement.executeQuery()) {

            System.out.println("\n=== DANH SÁCH CÔNG VIỆC ===");
            System.out.printf("%-5s | %-30s | %-20s\n", "ID", "Tên công việc", "Trạng thái");
            System.out.println("---------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String taskName = rs.getString("task_name");
                String status = rs.getString("status");
                System.out.printf("%-5d | %-30s | %-20s\n", id, taskName, status);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách: " + e.getMessage());
        }
    }

    public void updateTaskStatus(int id, String status) {
        String sqlUpdate = "CALL update_task_status(?,?)";
        try (Connection conn = getConnection(); CallableStatement callableStatement = conn.prepareCall(sqlUpdate)) {
            callableStatement.setInt(1, id);
            callableStatement.setString(2, status);
            callableStatement.execute();
            System.out.println("=> Cập nhật trạng thái thành công");
        } catch (SQLException e) {
            System.err.println("=> Lỗi khi cập nhật trạng thái: " + e.getMessage());
        }
    }

    public void deleteTask(int id) {
        String sqlDelete = "CALL delete_task(?)";
        try (Connection conn = getConnection(); CallableStatement callableStatement = conn.prepareCall(sqlDelete)) {
            callableStatement.setInt(1, id);
            callableStatement.execute();
            System.out.println("=> Xóa task thành công");
        } catch (SQLException e) {
            System.err.println("=> Lỗi khi xóa task: " + e.getMessage());
        }
    }

    public void searchTaskByName(String taskName){
        String sqlSearch = "SELECT * FROM search_task_by_name(?)";
        try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sqlSearch)){
            preparedStatement.setString(1, taskName);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("\n=== KẾT QUẢ TÌM KIẾM ===");
            System.out.printf("%-5s | %-30s | %-20s\n", "ID", "Tên công việc", "Trạng thái");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("task_name");
                String status = rs.getString("status");
                System.out.printf("%-5d | %-30s | %-20s\n", id, name, status);
            }
        } catch (SQLException e) {
            System.err.println("=> Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }

    public void task_statistics(){
        String sqlList = "SELECT * FROM task_statistics()";
        try(Connection connection = getConnection();PreparedStatement preparedStatement = connection.prepareStatement(sqlList);ResultSet rs = preparedStatement.executeQuery()){
            System.out.println("\n=== THỐNG KÊ CÔNG VIỆC ===");
            System.out.printf("%-20s | %-10s\n", "Trạng thái", "Số lượng");
            System.out.println("-----------------------------------");
            while (rs.next()){
                String status = rs.getString("status");
                int count = rs.getInt("count");
                System.out.printf("%-20s | %-10d\n", status, count);
            }
        } catch (SQLException e) {
            System.err.println("=> Lỗi khi thống kê: " + e.getMessage());
        }
    }

}
