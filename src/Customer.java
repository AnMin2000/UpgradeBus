import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Customer extends JFrame implements ActionListener{
    JTextArea txtA = new JTextArea(7,0);
    JTextField txtF = new JTextField(30);
    JButton btnTransfer;
    JButton btnExit;
    boolean isFirst=true;
    JPanel p1 = new JPanel();
    private DBClient client;


    public Customer(String id) {
        super("챗봇 문의상담");


        ImageIcon del1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/send1.png");
        Image del11 = del1.getImage();
        Image del111 = del11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon1 = new ImageIcon(del111);
        ImageIcon del2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/send2.png");
        Image del22 = del2.getImage();
        Image del222 = del22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon2 = new ImageIcon(del222);
        btnTransfer = new JButton(delIcon1);
        btnTransfer.setBorderPainted(false); // 버튼 테두리 설정해제
        btnTransfer.setRolloverIcon(delIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        btnTransfer.setFocusPainted(false);
        btnTransfer.setContentAreaFilled(false);
        btnTransfer.setOpaque(false);

        ImageIcon close1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/close1.png");
        Image close11 = close1.getImage();
        Image close111 = close11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon closeIcon1 = new ImageIcon(close111);
        ImageIcon close2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/close2.png");
        Image close22 = close2.getImage();
        Image close222 = close22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon closeIcon2 = new ImageIcon(close222);
        btnExit = new JButton(closeIcon1);
        btnExit.setBorderPainted(false); // 버튼 테두리 설정해제
        btnExit.setRolloverIcon(closeIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        btnExit.setFocusPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.setOpaque(false);


        String Id = id;
        txtA.setLineWrap(true); // 자동 줄바꿈
        add("Center", txtA);
        setLocation(520,160);  // 프레임을 위치 설정
        p1.add(txtF);
        String idt = txtA.getText();
        p1.add(btnTransfer);
        p1.add(btnExit);
        add("South", p1);
        String hello = "상담 서비스"+ id + "님 버스안내 챗봇입니다!\n" +
                "궁금한게 있으신가요??";
        txtA.append("[챗봇]"+ hello+"\n");
        btnTransfer.addActionListener(this);
        btnExit.addActionListener(this);

//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent w) {
//                ChatBot.this.dispose();
//                // 서버와 연결을 종료해야 하므로 DBClient 인스턴스를 사용하여 연결 종료 로직을 추가합니다.
//                try {
//                    client.closeConnection();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        setSize(580,500);
        setVisible(true);
        try {
            client = new DBClient("localhost", 12345);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "서버에 연결할 수 없습니다.", "에러", JOptionPane.ERROR_MESSAGE);
            this.dispose(); // 연결 실패 시 프로그램 종료
        }
    }
    public void actionPerformed(ActionEvent e){
        String id = "나";
        String input = txtF.getText();
        if (e.getSource() == btnTransfer) { // 전송 버튼 눌렀을 경우
            // 메시지 입력 없이 전송 버튼만 눌렀을 경우
            if (input.equals("")) {
                return;
            }
            txtA.append("[" + id + "] " + input + "\n");
            txtF.setText("");

            // 채팅 메시지를 서버로 전송합니다.
            try {
                client.sendChatMessage(input);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            this.dispose();
        }
    }
    public static void main(String[] args){
        new Customer("dksals");
    }
}
