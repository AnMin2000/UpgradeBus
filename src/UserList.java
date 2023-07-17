import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class UserList extends JFrame {
    private static final long serialVersionUID = 1L;
    private Vector data = null;
    private Vector title = null;
    private JTable table = null;
    private DefaultTableModel model = null;
    private JButton btnAdd = null;
    private JButton btnDel = null;
    private JButton btnUpdate = null;
    private JButton btnClear = null;
    private JTextField tfNum = null;
    private JTextField tfName = null;
    private JTextField tfphone = null;
    private JLabel lblNum = null;
    private JLabel lblName = null;
    private JLabel phone = null;




    public UserList(final String user) {
        super("회원 관리");

        this.setLocation(370,190);  // 프레임을 위치 설정
        this.setDefaultCloseOperation(0);
        this.data = new Vector();
        this.title = new Vector();
        this.title.add("이름");
        this.title.add("아이디");
        this.title.add("전화번호");
        this.model = new DefaultTableModel();
        Vector result = this.selectAll();
        this.model.setDataVector(result, this.title);
        this.table = new JTable(this.model);
        JScrollPane sp = new JScrollPane(this.table);
        this.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = UserList.this.table.getSelectedRow();
                Vector in = (Vector)UserList.this.data.get(index);
                String name = (String)in.get(0);
                String id = (String)in.get(1);
                String phone = (String)in.get(2);
                UserList.this.tfNum.setText(name);
                UserList.this.tfName.setText(id);
                UserList.this.tfphone.setText(phone);
            }
        });
        JPanel panel = new JPanel();
        this.tfNum = new JTextField(8);
        this.tfName = new JTextField(8);
        this.tfphone = new JTextField(6);
        this.lblNum = new JLabel("이름");
        this.lblName = new JLabel("아이디");
        this.phone = new JLabel("전화번호");

        ImageIcon del1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/del1.png");
        Image del11 = del1.getImage();
        Image del111 = del11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon1 = new ImageIcon(del111);
        ImageIcon del2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/del2.png");
        Image del22 = del2.getImage();
        Image del222 = del22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon2 = new ImageIcon(del222);
        this.btnDel = new JButton(delIcon1);
        this.btnDel.setBorderPainted(false); // 버튼 테두리 설정해제
        this.btnDel.setRolloverIcon(delIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        this.btnDel.setFocusPainted(false);
        this.btnDel.setContentAreaFilled(false);
        this.btnDel.setOpaque(false);
        //this.btnDel = new JButton("삭제");

        ImageIcon close1 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/close1.png");
        Image close11 = close1.getImage();
        Image close111 = close11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon closeIcon1 = new ImageIcon(close111);
        ImageIcon close2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/close2.png");
        Image close22 = close2.getImage();
        Image close222 = close22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon closeIcon2 = new ImageIcon(close222);
        this.btnUpdate = new JButton(closeIcon1);
        this.btnUpdate.setBorderPainted(false); // 버튼 테두리 설정해제
        this.btnUpdate.setRolloverIcon(closeIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        this.btnUpdate.setFocusPainted(false);
        this.btnUpdate.setContentAreaFilled(false);
        this.btnUpdate.setOpaque(false);

        //this.btnUpdate = new JButton("닫기");
        this.btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String card = UserList.this.tfName.getText();

                try {
                    new DB().userDelete(card);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                Vector result = UserList.this.selectAll();
                UserList.this.model.setDataVector(result, UserList.this.title);
            }
        });
        this.btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserList.this.dispose();
            }
        });
        panel.add(this.lblNum);
        panel.add(this.tfNum);
        panel.add(this.lblName);
        panel.add(this.tfName);
        panel.add(this.phone);
        panel.add(this.tfphone);
        panel.add(this.btnDel);
        panel.add(this.btnUpdate);
        Container c = this.getContentPane();
        c.add(new JLabel("회원 리스트", 0), "North");
        c.add(sp, "Center");
        c.add(panel, "South");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                try {
                    UserList.this.setVisible(false);
                    UserList.this.dispose();
                } catch (Exception var3) {
                }

            }
        });
        this.setSize(800, 400);
        this.setVisible(true);
    }

    private Vector selectAll() {
        this.data.clear();

        try {

            ResultSet rs = new DB().print("name, userId, number","client","Null","Null","Null","Null");

            while(rs.next()) {
                Vector in = new Vector();
                String name = rs.getString(1);
                String id = rs.getString(2);
                String phone = rs.getString(3);
                in.add(name);
                in.add(id);
                in.add(phone);
                this.data.add(in);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        return this.data;
    }
    public static void main(String[] args){
        new UserList("null");
    }

}
