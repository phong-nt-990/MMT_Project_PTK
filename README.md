# ĐỒ ÁN GIỮA KỲ - LẬP TRÌNH JAVA SOCKET - MẠNG MÁY TÍNH
- [Thông tin chung](#i-thông-tin-chung)
- [Mô tả chức năng](#ii-mô-tả-chức-năng)
- [Thư viện, package liên quan](#iii-thư-viện-package-liên-quan)
## I. Thông tin chung
---
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
---
1. Liệt kê các processes đang chạy trong máy tính, start/stop 1 process
2. Liệt kê các application đang chạy trong máy tính, start/stop 1 application
3. Chụp màn hình
4. Keylogger
5. Shutdown
## III. Thư viện, package liên quan
---
- Java Swing
- Jnativehook: [kwhat/jnativehook: Global keyboard and mouse listeners for Java. (github.com)](https://github.com/kwhat/jnativehook)
- Java io, net, awt, Imageio
- Java net
- Java awt
- Javax Imageio
## IV. Tổ chức file, source code
- Client
```
Client
├───.idea
│   └───artifacts
├───out
│   └───artifacts
│       └───client_jar
├───src
│   └───main
│       └───java
│           └───org
│               └───example
└───target
    └───classes
        ├───com
        │   └───intellij
        │       └───uiDesigner
        │           └───core
        └───org
            └───example
```
- Server
```
Server
├───.idea
│   ├───artifacts
│   └───libraries
├───out
│   └───artifacts
│       └───server_jar
├───src
│   └───main
│       ├───java
│       │   └───org
│       │       └───example
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
