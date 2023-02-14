# ĐỒ ÁN GIỮA KỲ - LẬP TRÌNH JAVA SOCKET - MẠNG MÁY TÍNH - CSC10008
- [Thông tin chung](#i-thông-tin-chung)
- [Mô tả chức năng](#ii-mô-tả-chức-năng)
- [Thư viện, package liên quan](#iii-thư-viện-package-liên-quan)
## I. Thông tin chung
- Tên đồ án: Chương trình điều khiển máy tính từ xa
- Ngôn ngữ lập trình: Java
- Group: 3 members
- Hình thức: Bài tập nhóm
- Hình thức nộp bài: Tìm hiểu + triển khai
- GV phụ trách: Đỗ Hoàng Cường
- Họ và tên thành viên tham gia:
    - Nguyễn Thế Phong 
    - Nguyễn Anh Khôi
    - Nguyễn Văn Trí
## II. Mô tả chức năng
1. Liệt kê các processes đang chạy trong máy tính, start/stop 1 process
2. Liệt kê các application đang chạy trong máy tính, start/stop 1 application
3. Chụp màn hình
4. Keylogger
5. Shutdown
## III. Thư viện, package liên quan
- Java Swing
- Jnativehook: [kwhat/jnativehook: Global keyboard and mouse listeners for Java. (github.com)](https://github.com/kwhat/jnativehook)
- Java io, net, awt, Imageio
- Java net
- Java awt
- Javax Imageio
## IV. Tổ chức file, source code
- Client
    - Chương trình được xây dựng dựa trên 6 file .java:
        - client.java
        - Keylog.java
        - Kill.java
        - listApp.java
        - pic.java
        - process.java
        - Program.java
        - Start.java
    - Cây thư mục

    ```
    Client
    ├───.idea
    │   └───artifacts
    ├───out
    │   └───artifacts
    │       └───client_jar (File .jar sau khi build bằng IntelliJ IDEA)
    ├───src
    │   └───main
    │       └───java
    │           └───org
    │               └───example (Source code)
    └───target (IDE)
        └───classes
            ├───com
            │   └───intellij
            │       └───uiDesigner
            │           └───core
            └───org
                └───example
    ```
- Server
    - Chương trình được xây dựng dựa trên 6 file .java:
        - Keylog.java
        - Program.java
        - server.java
    - Thư viện ngoài đã sử dụng:
        - jnativehook
        - jna
        - slf4j_simple
    - Cây thư mục:
```
    Server
    ├───.idea
    │   ├───artifacts
    │   └───libraries (Thư viện ngoài)
    ├───out
    │   └───artifacts
    │       └───server_jar (File .jar sau khi build bằng IntelliJ IDEA)
    ├───src
    │   └───main
    │       ├───java
    │       │   └───org
    │       │       └───example (Source code)
    │       └───resources
    │           └───META-INF
    └───target
        └───classes
            ├───com
            │   └───intellij
            │       └───uiDesigner
            │           └───core
            ├───META-INF
            └───org
                └───example
    ```
