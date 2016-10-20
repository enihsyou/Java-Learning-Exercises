import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

class Card {
    private String userName;
    private long cardNumber;
    private BigDecimal balance;
    private Currency currency;
    private String cardGrade;

    Card() {
        userName = "";
        balance = BigDecimal.ZERO;
        cardNumber = 0;
        currency = Currency.getInstance(Locale.getDefault());
        cardGrade = "";
    }

    Card(String userName, BigDecimal balance, long cardNumber, String cardGrade, Currency currency) {
        this.userName = userName;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.currency = currency;
        this.cardGrade = cardGrade;
    }

    public String getUserName() {
        return userName;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public String getCardGrade() {
        return cardGrade;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("Card %s %s@%s", cardGrade, cardNumber, userName);
    }
}

class Account {
    private Card card;
    private Calendar createTime;

    Account(Card card) {
        this.card = card;
        createTime = Calendar.getInstance();

    }

    public Card getCard() {
        return card;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("%s created at %s", card, dateFormat.format(createTime.getTime()));
    }
}

class GUI extends JFrame {
    private Logger logger = Logger.getGlobal();
    JPanel statusBar;
    JPanel informationPanel;
    List<Account> accounts = new ArrayList<>();
    private String[] cardGradeList = {"信用卡", "借记卡", "白金卡"};
    private Account current;

    JLabel userName = new JLabel("name");
    JLabel cardNumber = new JLabel("number");
    JLabel cardGrade = new JLabel("grade");
    JLabel balance = new JLabel(Currency.getInstance(Locale.getDefault()).getSymbol() + "0");

    GUI() {
        /*设置日志*/
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createAccountMenu());
        menuBar.add(operateAccountMenu());
        menuBar.add(helpMenu());
        // JMenuItem exit = new JMenuItem("退出");
        // exit.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         dispose();
        //     }
        // });
        // menuBar.add(exit);
        return menuBar;
    }

    private JMenu helpMenu() {
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem about = new JMenuItem("关于");
        helpMenu.add(about);
        return helpMenu;
    }

    private JMenu createAccountMenu() {
        JMenu createAccountMenu = new JMenu("账户");
        JMenuItem create = new JMenuItem("创建账户");
        JMenuItem delete = new JMenuItem("删除账户");

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = createAccountDialog();
                dialog.setVisible(true);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, String.format("真的要删除账户%s吗？", current.getCard()
                        .getUserName()), "确认删除", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    accounts.remove(current);
                    current = null;
                    updateStatusBar();
                }
            }
        });

        createAccountMenu.add(create);
        createAccountMenu.add(delete);

        return createAccountMenu;
    }

    private JDialog createAccountDialog() {
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JLabel cardNumberLabel = new JLabel("卡号");
        cardNumberLabel.setFont(Font.getFont("微软雅黑"));
        JTextField cardNumber = new JTextField(20);
        JLabel userNameLabel = new JLabel("姓名");
        JTextField userName = new JTextField(20);
        JLabel balanceLabel = new JLabel("余额");
        JTextField balance = new JTextField(20);

        JLabel gradeLabel = new JLabel("卡种类");
        panel4.add(gradeLabel);
        ButtonGroup classGroup = new ButtonGroup();
        for (String grade : cardGradeList) {
            JRadioButton radioButton = new JRadioButton(grade);
            classGroup.add(radioButton);
            panel4.add(radioButton);
        }

        cardNumberLabel.setLabelFor(cardNumber);
        userNameLabel.setLabelFor(userName);
        balanceLabel.setLabelFor(balance);

        JButton confirm = new JButton("确定");
        JButton cancel = new JButton("取消");

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = userName.getText();
                    BigDecimal money = new BigDecimal(balance.getText());
                    long number = Long.parseLong(cardNumber.getText());
                    String cardGrade = cardGradeList[0];
                    for (Enumeration<AbstractButton> buttons = classGroup.getElements(); buttons.hasMoreElements(); ) {
                        AbstractButton button = buttons.nextElement();
                        if (button.isSelected()) {
                            cardGrade = button.getText();
                        }
                    }
                    Card newCard = new Card(name, money, number, cardGrade, Currency.getInstance(Locale.getDefault()));
                    Account newAccount = new Account(newCard);
                    accounts.add(newAccount);
                    current = newAccount;
                    updateStatusBar();
                    logger.fine(newAccount.toString());
                    dialog.dispose();
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(dialog, "输入信息不合标准", "输入错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        panel1.add(cardNumberLabel);
        panel1.add(cardNumber);
        panel2.add(userNameLabel);
        panel2.add(userName);
        panel3.add(balanceLabel);
        panel3.add(balance);
        panel5.add(confirm);
        panel5.add(cancel);

        dialog.add(panel1);
        dialog.add(panel2);
        dialog.add(panel3);
        dialog.add(panel4);
        dialog.add(panel5);

        dialog.pack();
        dialog.setLocationRelativeTo(this);

        return dialog;
    }
    // private JDialog deleteAccountDialog() {
    //     JDialog dialog = new JDialog();
    //     dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    //     dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
    //
    //     dialog.pack();
    //     dialog.setLocationRelativeTo(this);
    //
    //     return dialog;
    // }

    private JMenu operateAccountMenu() {
        JMenu createAccountMenu = new JMenu("操作");
        JMenuItem deposit = new JMenuItem("存钱");
        JMenuItem withdraw = new JMenuItem("取钱");
        createAccountMenu.add(deposit);
        createAccountMenu.add(withdraw);

        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (current == null) {
                    return;
                } else {
                    JOptionPane.showInputDialog();
                }
            }
        });

        return createAccountMenu;
    }

    private JPanel createInformation() {
        JPanel panel = new JPanel();
        JList<Account> accountJList = new JList<>();

        panel.add(accountJList);
        return panel;
    }

    private JPanel createStatusBar() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLoweredBevelBorder());
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        panel.add(userName);
        panel.add(cardGrade);
        panel.add(cardNumber);
        JLabel padding = new JLabel();
        panel.add(padding);
        panel.add(balance);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.ipadx = 10;
        layout.setConstraints(userName, c);
        layout.setConstraints(cardGrade, c);
        layout.setConstraints(cardNumber, c);
        c.weightx = 1;
        layout.setConstraints(padding, c);
        c.weightx = 0;
        c.gridwidth = 2;
        layout.setConstraints(balance, c);


        panel.setSize(this.getWidth(), 60);

        updateStatusBar();


        return panel;
    }

    void updateStatusBar() {
        if (current != null) {
            Card card = current.getCard();
            userName.setText(card.getUserName());
            cardNumber.setText(Long.toString(card.getCardNumber()));
            cardGrade.setText(card.getCardGrade());
            balance.setText(card.getCurrency().getSymbol() + card.getBalance());
        } else {
            userName.setText("姓名");
            cardGrade.setText("卡种");
            cardNumber.setText("卡号");
            balance.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + "0");
        }
    }

    void constructGUI() {
        /*按×关闭*/
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        /*设置成系统外观*/
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        /*添加元素*/
        add(createMenuBar(), BorderLayout.NORTH);
        informationPanel = createInformation();
        statusBar = createStatusBar();
        add(informationPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        /*设置窗口位置大小*/
        setSize(new Dimension(480, 270));
        setLocationRelativeTo(null);
        /*显示窗口*/
        setVisible(true);
    }
}


public class Bank {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                /*设置字体*/
                Font font = new Font("微软雅黑", Font.PLAIN, 12);
                FontUIResource globalFont = new FontUIResource(font);
                for (Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
                    Object key = keys.nextElement();
                    Object value = UIManager.get(key);
                    if (value instanceof FontUIResource) UIManager.put(key, globalFont);
                }
                new GUI().constructGUI();
            }
        });
    }
}
