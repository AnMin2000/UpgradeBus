import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class DBClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public DBClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
    public void sendChatMessage(String message) throws IOException, ClassNotFoundException {
        String request = "CHAT:" + message;
        out.writeObject(request);

        // 서버로부터 받은 응답을 처리하는 로직을 추가할 수 있습니다.
    }
    public void sendConsultMessage(String message) throws IOException, ClassNotFoundException {
        String request = "Consult:" + message;
        out.writeObject(request);

        // 서버로부터 받은 응답을 처리하는 로직을 추가할 수 있습니다.
    }

    public boolean checkOverlap(String ID) throws IOException, ClassNotFoundException {

        String request = "CHECK_OVERLAP:" + ID;
        out.writeObject(request);

        String response = (String) in.readObject();

        System.out.println(response);

        return response.equals("ID_NOT_EXISTS");

    }
    public void insert(String tableName, int number, String[] values) throws IOException, ClassNotFoundException {
        String request = "INSERT:" + tableName + ":" + number + ":" + String.join(",", values);
        out.writeObject(request);

        String response = (String) in.readObject();
        System.out.println(response); // 필요에 따라 응답 처리
    }
    public boolean LogIn(String ID, String PW) throws IOException, ClassNotFoundException {

        String request = "LOGIN:" + ID + ":" + PW;
        out.writeObject(request);

        String response = (String) in.readObject();

        System.out.println(response);

        return response.equals("LOGIN_SUCCESS");

    }
}
