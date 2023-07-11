import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class LoginUi extends JFrame{
    private JPanel JPanel1;
    private JTextField IdTextField;
    private JTextField PwTextField;

    public LoginUi(){
///
        setTitle("Login");
        setResizable(false);
        setSize(350,400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon background = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/login_bus.png");
        Image img = background.getImage();
        Image updateImg = img.getScaledInstance(330,400,Image.SCALE_DEFAULT);
        ImageIcon updateIcon = new ImageIcon(updateImg);
        JLabel image2 = new JLabel(updateIcon);
        image2.setBounds(-7,-60,350,450);

        IdTextField.setBounds(93,70,150,30);
        PwTextField.setBounds(93,125,150,30);

        JLabel question = new JLabel("회원이 아니신가요?");
        question.setBounds(124, 205,100,20);
        question.setFont(new Font("맑은 고딕", Font.BOLD, 10));

        JLabel signinPage = new JLabel("<HTML><U>회원가입</U></HTML>");
        signinPage.setBounds(148, 225, 100, 20);
        signinPage.setFont(new Font("맑은 고딕", Font.BOLD, 10));

        ImageIcon checkbutton = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/bus_number1.png");
        Image checkImg = checkbutton.getImage();
        Image checkUpdate = checkImg.getScaledInstance(70,30, Image.SCALE_SMOOTH);
        ImageIcon updateIcon2 = new ImageIcon(checkUpdate);

        ImageIcon checkbutton2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/bus_number2.png");
        Image checkImg2 = checkbutton2.getImage();
        Image checkUpdate2 = checkImg2.getScaledInstance(70,30, Image.SCALE_SMOOTH);
        ImageIcon updateIcon3 = new ImageIcon(checkUpdate2);

        JButton check = new JButton(updateIcon2);
        check.setBounds(141,323,70,30);
        check.setFocusPainted(false);
        check.setContentAreaFilled(false);
        check.setRolloverIcon(updateIcon3); // 버튼에 마우스가 올라갈떄 이미지 변환
        check.setBorderPainted(false); // 버튼 테두리 설정해제
        image2.add(check);

        ImageIcon right1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/light1.png");
        Image right11 = right1.getImage();
        Image right111 = right11.getScaledInstance(70,40, Image.SCALE_SMOOTH);
        ImageIcon right1111 = new ImageIcon(right111);

        ImageIcon right2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/light2.png");
        Image right22 = right2.getImage();
        Image right222= right22.getScaledInstance(70,40, Image.SCALE_SMOOTH);
        ImageIcon right2222 = new ImageIcon(right222);

        JButton right1_btn = new JButton(right1111);
        JButton right2_btn = new JButton(right1111);
        right1_btn.setFocusPainted(false);
        right1_btn.setContentAreaFilled(false);
        right1_btn.setRolloverIcon(right2222); // 버튼에 마우스가 올라갈떄 이미지 변환
        right1_btn.setBorderPainted(false); // 버튼 테두리 설정해제
        image2.add(right1_btn);

        right2_btn.setFocusPainted(false);
        right2_btn.setContentAreaFilled(false);
        right2_btn.setRolloverIcon(right2222); // 버튼에 마우스가 올라갈떄 이미지 변환
        right2_btn.setBorderPainted(false); // 버튼 테두리 설정해제
        image2.add(right2_btn);
        right1_btn.setBounds(230,280,80,35);
        right2_btn.setBounds(40,280,80,35);



        add(IdTextField);
        add(signinPage);
        add(question);
        add(PwTextField);
        add(image2);
        add(JPanel1);
        setVisible(true);
        //
        check.addActionListener(new ActionListener() { //로그인

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String UserId = IdTextField.getText();
                    String UserPw = PwTextField.getText();

                    boolean state = new DB().Login(UserId, UserPw);

                    if(state == true){
                        dispose();
                        new MainUi(UserId);

                    }
                    else {
                        return;
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        signinPage.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // 회원가입
                try {
                    dispose();
                    new SignUpUi();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public static void main(String[] args){
        new LoginUi();
    }
}
