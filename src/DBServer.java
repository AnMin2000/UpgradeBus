import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class DBServer {
    private DB db;

    public DBServer() throws ClassNotFoundException, SQLException {
        db = new DB();
    }

    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("서버 시작. 포트 " + port + "에서 대기 중");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트 연결됨: " + clientSocket.getInetAddress().getHostAddress());

                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket) throws IOException {
            clientSocket = socket;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String request = (String) in.readObject();
                    String[] requestData = request.split(":");
                    String operation = requestData[0];

                    switch (operation) {
                        case "INSERT":
                            String name = requestData[1];
                            int number = Integer.parseInt(requestData[2]);
                            String[] values = requestData[3].split(",");
                            db.insert(name, number, values);
                            out.writeObject("INSERT_SUCCESS");
                            break;
                        case "PRINT":
                            String selectName = requestData[1];
                            String tableName = requestData[2];
                            String sqlName = requestData[3];
                            String data = requestData[4];
                            String sqlName2 = requestData[5];
                            String data2 = requestData[6];
                            ResultSet resultSet = db.print(selectName, tableName, sqlName, data, sqlName2, data2);
                            // 여기서 resultSet을 처리하여 클라이언트로 보내는 로직을 구현해야 합니다.
                            // out.writeObject(response);
                            break;
                        case "LOGIN":
                            String userId = requestData[1];
                            String password = requestData[2];
                            boolean loginResult = db.Login(userId, password);
                            out.writeObject(loginResult ? "LOGIN_SUCCESS" : "LOGIN_FAILURE");
                            break;
                        case "CHECK_OVERLAP":
                            String checkUserId = requestData[1];
                            boolean overlapResult = db.Overlap(checkUserId);
                            out.writeObject(overlapResult ? "ID_NOT_EXISTS" : "ID_EXISTS");
                            break;
                        // UPDATE, DELETE 등 다른 작업에 대한 case도 추가 가능
                        default:
                            out.writeObject("INVALID_OPERATION");
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException | SQLException | ParseException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int port = 12345; // 사용할 포트 번호로 변경
        DBServer server = new DBServer();
        server.startServer(port);
    }
}
