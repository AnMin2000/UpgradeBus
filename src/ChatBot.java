import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class ChatBot extends JFrame implements ActionListener{
    JTextArea txtA = new JTextArea(7,0);
    JTextField txtF = new JTextField(30);


    JButton btnTransfer;
    JButton btnExit;
    boolean isFirst=true;
    JPanel p1 = new JPanel();

    public ChatBot(String id) {
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
        String hello = "안녕하세요 "+ id+"님 버스안내 챗봇입니다!\n" +
                "궁금한게 있으신가요??";
        txtA.append("[챗봇]"+ hello+"\n");
        btnTransfer.addActionListener(this);
        btnExit.addActionListener(this);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                ChatBot.this.dispose();
              //  new MainUi("dksals");
            }
        });

        setSize(580,500);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e){
        String id = "나";
        String input = txtF.getText();
        if(e.getSource()==btnTransfer){//전송버튼 눌렀을 경우
            //메세지 입력없이 전송버튼만 눌렀을 경우
            if(input.equals("")){
                return;
            }
            txtA.append("["+id+"] "+ input+"\n");
            txtF.setText("");

            //String arr[] = new String[10]; //답변 배열
            ArrayList<String> arrs = new ArrayList<String>(); //답변 배열

                StringTokenizer tk = new StringTokenizer(input," ");
                int n = tk.countTokens(); // input 토큰 갯수
                int ck = 0; // 체크포인트
                for (int k=0; k<n; k++){
                    if (ck==1){
                        break;
                    }
                    input = tk.nextToken();
                }

                String answer="";

                System.out.println("<"+answer+">");
                //txtA.append("[챗봇] "+ new String(rs.getString("answer"))+"\n");
                if (answer.equals("")){
                    txtA.append("[챗봇] "+ "이해하지못했습니다. 죄송합니다 ㅠㅠ " +"\n");
                    txtF.setText("");
                }
                else {
                    txtA.append("[챗봇] "+ answer +"\n");
                    txtF.setText("");
                }

        }else{
            this.dispose();
        }

    }
    public static void main(String[] args){
        new ChatBot("dksals");
    }
}
