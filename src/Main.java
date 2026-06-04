import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManagement taskManagement = new TaskManagement();
        int choice = -1;
        while (true) {
            System.out.println("\n===== HE THONG QUAN LY TO-DO LIST =====");
            System.out.println("1. Them cong viec");
            System.out.println("2. Liet ke cong viec");
            System.out.println("3. Cap nhat trang thai");
            System.out.println("4. Xoa cong viec");
            System.out.println("5. Tim kiem cong viec theo ten");
            System.out.println("6. Thong ke cong viec");
            System.out.println("0. Thoat");
            System.out.println("=========================================");
            System.out.println("Nhap lua chon cua ban: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("=> Vui long nhap so nguyen!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- Them cong viec ---");
                    System.out.println("Nhap ten cong viec: ");
                    String taskName = scanner.nextLine().trim();

                    if (taskName.isEmpty()) {
                        System.out.println("=> Ten cong viec khong duoc de trong!");
                        break;
                    }
                    System.out.println("Nhap trang thai (chua hoan thanh/ da hoan thanh): ");
                    String status = scanner.nextLine().trim();

                    if (status.isEmpty()) {
                        System.out.println("=> Trang thai khong duoc de trong!");
                        break;
                    }

                    taskManagement.addTask(taskName, status);
                    break;

                case 2:
                    taskManagement.listTasks();
                    break;
                case 3:
                    System.out.println("\n--- Cap nhat trang thai ---");
                    try {
                        System.out.println("Nhap ID can cap nhat: ");
                        int updateId = Integer.parseInt(scanner.nextLine().trim());

                        System.out.println("Nhap trang thai moi (chua hoan thanh/ da hoan thanh): ");
                        String newStatus = scanner.nextLine().trim();

                        if (newStatus.isEmpty()) {
                            System.out.println("=> Trang thai khong duoc de trong!");
                            break;
                        }
                        taskManagement.updateTaskStatus(updateId, newStatus);
                    } catch (NumberFormatException e) {
                        System.out.println("=> Vui long nhap so nguyen cho ID!");
                    }
                    break;
                case 4:
                    System.out.println("\n--- Xoa cong viec ---");
                    try {
                        System.out.println("Nhap ID can xoa: ");
                        int deleteId = Integer.parseInt(scanner.nextLine().trim());
                        taskManagement.deleteTask(deleteId);
                    } catch (NumberFormatException e) {
                        System.out.println("=> Vui long nhap so nguyen cho ID!");
                    }
                    break;
                case 5:
                    System.out.println("\n--- Tim kiem cong viec theo ten ---");
                    System.out.println("Nhap ten cong viec can tim: ");
                    String searchName = scanner.nextLine().trim();
                    if (searchName.isEmpty()) {
                        System.out.println("=> Ten cong viec khong duoc de trong!");
                        break;
                    }
                    taskManagement.searchTaskByName(searchName);
                    break;
                case 6:
                    System.out.println("\n--- Thong ke cong viec ---");
                    taskManagement.task_statistics();
                    break;
                case 0:
                    System.out.println("\nCam on ban da su dung he thong! Dang thoat...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("=> Lua chon khong hop le! Vui long chon lai.");
            }
        }
    }
}