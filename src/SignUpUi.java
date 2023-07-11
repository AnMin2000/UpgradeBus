import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class SignUpUi extends JFrame{
    private JPanel panel1;
    private JTextField NameTextField;
    private JTextField NumberTextField;
    private JTextField IDTextField;
    private JTextField PassWordTextField;
    private JTextField EmailTestField;
    String UserID, UserPassWd, Username, Usernumber;
    public boolean check;
    public SignUpUi() throws SQLException, ClassNotFoundException {

        ///////////
        setTitle("회원가입");
        //setResizable(false);

        setSize(350,400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setLayout(null);

        JLabel title = new JLabel("회원 가입");
        Font a = new Font("휴먼엑스포", Font.PLAIN, 20);
        title.setFont(a);
        title.setBounds(120, 0, 100, 30);
        add(title);

        JLabel name = new JLabel("이름");
        name.setBounds(50, 50, 100, 30);
        add(name);
        NameTextField.setBounds(200, 50, 100, 30);
        add(NameTextField);

        JLabel phone = new JLabel("전화번호");
        phone.setBounds(50, 100, 100, 30);
        add(phone);
        NumberTextField.setBounds(200, 100, 100, 30);
        add(NumberTextField);

        JLabel email = new JLabel("이메일");
        email.setBounds(50, 150, 100, 30);
        add(email);
        EmailTestField.setBounds(200, 150, 100, 30);
        add(EmailTestField);

        JLabel id = new JLabel("아이디");
        id.setBounds(50, 200, 100, 30);
        add(id);
        IDTextField.setBounds(200, 200, 100, 30);
        add(IDTextField);

        JLabel pw = new JLabel("비밀 번호");
        pw.setBounds(50, 250, 100, 30);
        add(pw);
        PassWordTextField.setBounds(200, 250, 100, 30);
        add(PassWordTextField);

        ImageIcon overlap = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/overlap1.png");
        Image overlap11 = overlap.getImage();
        Image reoverlap = overlap11.getScaledInstance(80, 30, Image.SCALE_SMOOTH);
        ImageIcon overlapIcon1 = new ImageIcon(reoverlap);
        ImageIcon overlap2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/overlap2.png");
        Image overlap22 = overlap2.getImage();
        Image reoverlap2 = overlap22.getScaledInstance(80, 30, Image.SCALE_SMOOTH);
        ImageIcon overlapIcon2 = new ImageIcon(reoverlap2);
        JButton idck = new JButton(overlapIcon1);
        idck.setBorderPainted(false); // 버튼 테두리 설정해제
        idck.setRolloverIcon(overlapIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        idck.setFocusPainted(false);
        idck.setContentAreaFilled(false);
        idck.setOpaque(false);
        idck.setBounds(100, 200, 80, 30);
        add(idck);

        ImageIcon my = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/registered1.png");
        Image my1 = my.getImage();
        Image remy = my1.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ImageIcon reUpdateIcon1 = new ImageIcon(remy);
        ImageIcon my2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/registered2.png");
        Image my11 = my2.getImage();
        Image remy1 = my11.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ImageIcon reUpdateIcon2 = new ImageIcon(remy1);
        JButton btn1 = new JButton(reUpdateIcon1);
        btn1.setBorderPainted(false); // 버튼 테두리 설정해제
        btn1.setRolloverIcon(reUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        btn1.setFocusPainted(false);
        btn1.setContentAreaFilled(false);
        btn1.setOpaque(false);
        btn1.setBounds(50, 300, 80, 40);
        add(btn1);


        ImageIcon close1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/close1.png");
        Image close = close1.getImage();
        Image reclose = close.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ImageIcon Iconclose = new ImageIcon(reclose);
        ImageIcon close2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/close2.png");
        Image acclose2 = close2.getImage();
        Image reclose2 = acclose2.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ImageIcon Iconclose2 = new ImageIcon(reclose2);
        JButton btn2 = new JButton(Iconclose);
        btn2.setBorderPainted(false); // 버튼 테두리 설정해제
        btn2.setRolloverIcon(Iconclose2); // 버튼에 마우스가 올라갈떄 이미지 변환
        btn2.setFocusPainted(false);
        btn2.setContentAreaFilled(false);
        btn2.setOpaque(false);
        btn2.setBounds(200, 300, 80, 40);
        add(btn2);

        add(panel1);
        setVisible(true);
        DB connect = new DB();
        idck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    UserID = IDTextField.getText();
                    try {

                        check = connect.Overlap(UserID);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });


        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(check == false) return;

                UserID = IDTextField.getText();
                UserPassWd = PassWordTextField.getText();
                Username = NameTextField.getText();
                Usernumber = NumberTextField.getText();

                String[] PrArr = new String[]{UserID,UserPassWd,Username,Usernumber};
                try {
                    connect.insert("client",4,PrArr);
                    dispose();
                    new MainUi("NULL");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginUi();  // 회원가입 창 닫고 로그인 창 띄우기
            }
        });
    }
    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        new SignUpUi();
    }
}
