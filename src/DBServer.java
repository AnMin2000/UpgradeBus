import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DBServer {
    private DB db;
    private List<ClientHandler> clientHandlers; // 클라이언트 핸들러들을 저장하는 리스트

    public DBServer() throws ClassNotFoundException, SQLException {
        db = new DB();
        clientHandlers = new ArrayList<>();
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
                    System.out.println(operation);
                    switch (operation) {
                        case "CHAT":
                            String message = requestData[1];
                            System.out.println("메시지 수신: " + message);
                            // 채팅 메시지를 다른 클라이언트로 전송하는 로직을 추가합니다.
                            // 여기서는 수신한 메시지를 해당 클라이언트에 연결된 다른 클라이언트에게 보내는 방식으로 구현합니다.
                            sendChatMessageToOtherClients(message);
                            break;
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
                           // System.out.println(userId + password);
                            boolean loginResult = db.Login(userId, password);
                            System.out.println(loginResult);
                            out.writeObject(loginResult ? "LOGIN_SUCCESS" : "LOGIN_FAILURE");
                            break;
                        case "CHECK_OVERLAP":
                            String checkUserId = requestData[1];
                            boolean overlapResult = db.Overlap(checkUserId);
                         //   System.out.println(overlapResult);
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
        private void sendChatMessageToOtherClients(String message) throws IOException {
            // 모든 클라이언트 핸들러를 순회하며 수신한 메시지를 전달합니다.
            for (ClientHandler clientHandler : clientHandlers) {
                if (clientHandler != this) {
                    clientHandler.out.writeObject("CHAT:" + message);
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
