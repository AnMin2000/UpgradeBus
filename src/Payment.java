import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// 카드 정보 클래스
class Card {
    ArrayList<String> bank = new ArrayList<String>();
    ArrayList<String> cardNum = new ArrayList<String>();
    ArrayList<String> pw = new ArrayList<String>();

    public void insertCard(String bank, String cardNum, String pw) {
        this.bank.add(bank);
        this.cardNum.add(cardNum);
        this.pw.add(pw);
    }
}

// 결재 페이지 뒤로가기 버튼
class PaymentBack extends JPanel {
    public PaymentBack(Payment frame, SeatsSelect frame2) {
        Color mycor=new Color(189,215,238);
        setBackground(mycor);

        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        // 뒤로가기 버튼 이미지
        ImageIcon backIcon = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/back.png");
        Image backImg = backIcon.getImage();
        Image backUpdate = backImg.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon backUpdateIcon = new ImageIcon(backUpdate);

        ImageIcon backIcon2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/back2.png");
        Image backImg2 = backIcon2.getImage();
        Image backUpdate2 = backImg2.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon backUpdateIcon2 = new ImageIcon(backUpdate2);

        JButton back = new JButton(backUpdateIcon);
        back.setPreferredSize(new Dimension(100,50));
        back.setRolloverIcon(backUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        back.setBorderPainted(false); // 버튼 테두리 설정해제
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.setOpaque(false);
        add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame2.setVisible(true);
                frame.setVisible(false);
            }
        });
    }
}

// 결제 페이지 상단
class PaymentNorth extends JPanel {
    public PaymentNorth(Payment frame, SeatsSelect frame2) {
        Color mycor=new Color(189,215,238);
        setBackground(mycor);

        setLayout(new BorderLayout());
        add(new Title(), BorderLayout.WEST);
        add(new PaymentBack(frame, frame2), BorderLayout.EAST);
    }
}

// 결제 페이지 중심
class PaymentCenter extends JPanel implements ItemListener {
    JTable ticketTable;
    DefaultTableModel model;
    JScrollPane scroll;
    String start;
    String end;
    String date;
    String[] info;
    int number;
    int totalPrice;
//    int seatNum;
    JComboBox<String> myCard = new JComboBox<String>();  // 카드 선택 콤보박스
    Card ca;
    //DB_connect DB = new DB_connect();  // DB 연결
    JTextField cardNumber1;
    JTextField cardNumber2;
    JTextField cardNumber3;
    JTextField cardNumber4;
    JPasswordField password;
    String pw;

    public PaymentCenter(SeatsSelect frame, Payment frame2, String id, String start, String end, String date, int price, int seatNum) {
        setLayout(null);
        Color bgmycor=new Color(166,222,249);
        setBackground(bgmycor);
        Color mycor=new Color(189,215,238);

        // '예매하기' 글자
        JLabel title = new JLabel("예매하기");
        title.setBounds(60, 3, 150, 100);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        add(title);

        this.start = start; // 출발 터미널 정보 저장
        this.end = end;     // 도착 터미널 정보 저장
        this.date = date;   // 날짜 정보 저장
        this.totalPrice = price;    // 가격 정보 저장
//        this.seatNum = seatNum;


        // 날짜 정보 표시
        JLabel dateInfo = new JLabel("2023-08-01");
        dateInfo.setBounds(90, 170, 200, 100);
        dateInfo.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        add(dateInfo);

        // 출발 터미널 정보 표시
        JLabel startInfo = new JLabel(start);
        startInfo.setBounds(90, 220, 100, 100);
        startInfo.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        add(startInfo);

        // 화살표 이미지 표시
        ImageIcon profile = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/arrow2.png");
        Image img = profile.getImage();
        Image updateImg = img.getScaledInstance(70,35,Image.SCALE_SMOOTH);
        ImageIcon updateIcon = new ImageIcon(updateImg);

        JLabel image = new JLabel(updateIcon);
        image.setBounds(210,255,70,35);
        image.setHorizontalAlignment(JLabel.CENTER);
        add(image);

        // 도착 터미널 정보 표시
        JLabel endInfo = new JLabel(end);
        endInfo.setBounds(340,220,100,100);
        endInfo.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        add(endInfo);

        // 테이블 기본 틀 생성
        JPanel tT = new JPanel();
        tT.setBounds(40,290,500,100);

        // 테이블 기본 값 생성
        String[] colName = {"출발시간","회사","등급","요금"}; // 컬럼 네임 설정
        String[][] row = new String[0][4];
        model = new DefaultTableModel(row, colName);

        // 테이블 생성
        ticketTable = new JTable(model);
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 스크롤 팬 추가
        scroll = new JScrollPane(ticketTable);
        scroll.setBorder(BorderFactory.createEmptyBorder(10,10, 10, 10));

        tT.add("North", scroll);
        add(tT);

        // 테이블에 넣을 값 생성
        String[] data = {"09:00","우진관광","일반","18000원"};
        model.addRow(data);

        // 총 결재금액 글자
        JLabel totalText = new JLabel("총 결제금액");
        totalText.setBounds(90,380,200,100);
        totalText.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        add(totalText);

        // 총 결재금액 액수
        JLabel total = new JLabel("" + "18000" + " 원");
        total.setBounds(300,380,200,100);
        total.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        total.setForeground(Color.RED);
        add(total);


        JPanel square1 = new JPanel();
        square1.setBackground(mycor);
        square1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 16));
        square1.setBounds(30,100,520,450);
        add(square1);


        // 결제완료 버튼 이미지
        ImageIcon paymentClearIcon = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/paymentClear.png");
        Image paymentClearImg = paymentClearIcon.getImage();
        Image paymentClearUpdate = paymentClearImg.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon paymentClearUpdateIcon = new ImageIcon(paymentClearUpdate);

        ImageIcon paymentClearIcon2 = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/paymentClear2.png");
        Image paymentClearImg2 = paymentClearIcon2.getImage();
        Image paymentClearUpdate2 = paymentClearImg2.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon paymentClearUpdateIcon2 = new ImageIcon(paymentClearUpdate2);

        JButton clear = new JButton(paymentClearUpdateIcon);
        clear.setBounds(260,600,100,50);
        clear.setRolloverIcon(paymentClearUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        clear.setBorderPainted(false); // 버튼 테두리 설정해제
        clear.setFocusPainted(false);
        clear.setContentAreaFilled(false);
        clear.setOpaque(false);
        add(clear);


        // 결재완료 버튼 클릭 시
        clear.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, "예매에 성공하였습니다.");

                    new MainUi(id);
                    frame2.dispose();
            }
        });

        ImageIcon background = new ImageIcon("C:/Users/user/IdeaProjects/Hello-World--main/Hello-World--main/project__java/buspj/image/test.jpg");
        JLabel image2 = new JLabel(background);
        image2.setBounds(-1800,-200,4500,1200);
        add(image2);

    }

    // 내 카드 콤보박스에서 이벤트 처리
    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            String item = (String) event.getItem();
//            String bank1 = myCard.getSelectedItem().toString();   // 해당 카드 은행
            if (item.equals("없음")) {
                cardNumber1.setText("");
                cardNumber2.setText("");
                cardNumber3.setText("");
                cardNumber4.setText("");
                password.setText("");
            } else {
                int index = ca.bank.indexOf(item);             // 해당 카드 은행의 인덱스 추출
                String num = ca.cardNum.get(index);             // 해당 카드 은행의 카드 번호 추출
                String[] numList = new String[4];
                numList[0] = num.substring(0,4);                  // 카드 번호 나눠 배열에 저장
                numList[1] = num.substring(4,8);
                numList[2] = num.substring(8,12);
                numList[3] = num.substring(12);

                cardNumber1.setText(numList[0]);
                cardNumber2.setText(numList[1]);
                cardNumber3.setText(numList[2]);
                cardNumber4.setText(numList[3]);

                pw = ca.pw.get(index);     // 해당 카드의 비밀번호 추출
            }
        }
    }
}

// 결재 페이지 메인
public class Payment extends JFrame {
    public Payment(SeatsSelect frame, String id, String start, String end, String date, int price, int seatNum) {
        setTitle("최종결재");
        setSize(600,800);
      //  setResizable(false);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(new PaymentNorth(this, frame), BorderLayout.NORTH);
        mainContainer.add(new PaymentCenter(frame, this, id, start, end, date, price, seatNum), BorderLayout.CENTER);


        setVisible(true);
    }
}
