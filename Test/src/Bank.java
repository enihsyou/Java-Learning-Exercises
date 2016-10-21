import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.showInputDialog;

/*存取款动作接口*/
interface Action {
    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);

}

/*卡片种类选择*/
enum CardType {
    信用卡("信用卡", new DebitCard()), 借记卡("借记卡", new CreditCard()), 白金卡("白金卡", new PlatinumCard());

    public BaseCard getCardType() {
        return cardType;
    }

    public String getName() {
        return name;
    }

    private BaseCard cardType;

    private String name;

    CardType(String name, BaseCard cardType) {
        this.name = name;
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return name;
    }

}

/*卡片基类*/
abstract class BaseCard {
    BigDecimal fee; //手续费
    BigDecimal quota; ////透支限额

    public BigDecimal getFee() {
        return fee;
    }

    public BigDecimal getQuota() {
        return quota;
    }
}

/*借记卡*/
class DebitCard extends BaseCard {
    DebitCard() {
        fee = BigDecimal.ZERO;
        quota = BigDecimal.ZERO;
    }
}

/*信用卡*/
class CreditCard extends BaseCard {
    CreditCard() {
        fee = new BigDecimal(0.01);
        quota = new BigDecimal(1000);
    }
}

/*白金卡*/
class PlatinumCard extends BaseCard {
    PlatinumCard() {
        fee = new BigDecimal(0.05);
        quota = new BigDecimal(10000);
    }
}

/*卡片主体*/
class Card implements Action {
    private String owner;
    private long cardNumber; //TODO: 包装成类
    private BigDecimal balance;
    private Currency currency;
    private CardType cardGrade;

    Card() {
        balance = BigDecimal.ZERO;
        cardNumber = 0;
        currency = Currency.getInstance(Locale.getDefault());
        cardGrade = CardType.借记卡;
    }

    Card(String owner, BigDecimal balance, long cardNumber, CardType cardGrade, Currency currency) {
        this.owner = owner;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.currency = currency;
        this.cardGrade = cardGrade;
    }

    @Override
    public void deposit(BigDecimal amount) {
        BigDecimal fee = cardGrade.getCardType().getFee().multiply(amount);
        BigDecimal total = fee.add(amount);
        if (total.compareTo(balance) < 0) {
            JOptionPane.showMessageDialog(null, "余额不足");
        } else {
            //TODO: add log
            balance = balance.subtract(total);
            JOptionPane.showMessageDialog(null, "操作成功");
        }

    }

    @Override
    public void withdraw(BigDecimal amount) {

    }

    /*获取卡片主人*/
    public String getOwner() {
        return owner;
    }

    /*获取卡号*/
    long getCardNumber() {
        return cardNumber;
    }

    /*获取卡片类型*/
    CardType getCardGrade() {
        return cardGrade;
    }

    /*获取余额*/
    BigDecimal getBalance() {
        return balance;
    }

    void setBalance(BigDecimal money) {
        balance = money;
    }

    /*获取币种*/
    Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return String.format("%s %s", cardGrade.getName(), cardNumber);
    }
}

/*个人账户*/
class Account {
    private String userName;
    private ArrayList<Card> cards;
    private Calendar createTime;

    Account(String userName, ArrayList<Card> cards) {
        this.userName = userName;
        this.cards = cards;
        createTime = Calendar.getInstance();
    }

    Account(String userName) {
        this.userName = userName;
        cards = new ArrayList<>();
        createTime = Calendar.getInstance();
    }

    /*获取卡片组*/
    ArrayList<Card> getCards() {
        return cards;
    }

    /*获取账户用户名*/
    String getUserName() {
        return userName;
    }

    void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        return userName;
    }
}

/*构建GUI*/
class GUI extends JFrame {
    private Logger logger = Logger.getGlobal();
    private JPanel informationPanel;
    private ArrayList<Account> accounts = new ArrayList<>();
    private Account currentAccount;
    private Card currentCard;

    private JLabel userName = new JLabel("name");
    private JLabel cardNumber = new JLabel("number");
    private JLabel cardGrade = new JLabel("grade");
    private JLabel balance = new JLabel(Currency.getInstance(Locale.getDefault()).getSymbol() + "0");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    GUI() {
        setTitle("银行");
        /*设置日志*/
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

    }

    /*新建 菜单*/
    private class CreateMenu extends JMenu {
        JMenuItem createNewAccount = new JMenuItem("创建账户");
        JMenuItem deleteAccount = new JMenuItem("删除账户");
        JMenuItem createNewCard = new JMenuItem("创建新卡");


        CreateMenu() {
            setText("账户");
            createMenu();
        }

        private void createMenu() {
            createNewAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog = createAccountDialog();
                    dialog.setVisible(true);
                }
            });
            deleteAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null, String.format("真的要删除账户%s吗？", currentAccount
                            .getUserName()), "确认删除", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        accounts.remove(currentAccount);
                        currentAccount = null;
                        updateStatusBar();
                    }
                }
            });
            createNewCard.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog = createCardDialog();
                    dialog.setVisible(true);
                }
            });
            /*添加组件*/
            add(createNewAccount);
            add(deleteAccount);
            addSeparator();
            add(createNewCard);

            createNewCard.setEnabled(false);
        }

        private JDialog createAccountDialog() {
            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
            JPanel namePanel = new JPanel(); //卡号一栏
            JPanel buttonPanel = new JPanel(); //按钮

            JLabel userNameLabel = new JLabel("姓名");
            JTextField userName = new JTextField(20);

            userNameLabel.setLabelFor(userName);

            JButton confirm = new JButton("确定");
            JButton cancel = new JButton("取消");
            ActionListener submitUserName = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String name = userName.getText(); //输入的用户名

                        //创建对象
                        Account newAccount = new Account(name);
                        currentAccount = newAccount;
                        accounts.add(newAccount);

                        updateStatusBar();
                        createNewCard.setEnabled(true);
                        logger.fine(newAccount.toString());
                        dialog.dispose();
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(dialog, "输入信息不合标准", "输入错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            confirm.addActionListener(submitUserName);
            userName.addActionListener(submitUserName);
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            namePanel.add(userNameLabel);
            namePanel.add(userName);

            buttonPanel.add(confirm);
            buttonPanel.add(cancel);

            dialog.add(namePanel);
            dialog.add(buttonPanel);

            dialog.pack();
            dialog.setLocationRelativeTo(null);

            return dialog;
        }

        private JDialog createCardDialog() {
            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
            JPanel panel1 = new JPanel(); //卡号一栏
            JPanel panel2 = new JPanel(); //姓名一栏
            JPanel panel3 = new JPanel(); //预设余额一栏
            JPanel panel4 = new JPanel(); //种类选择
            JPanel panel5 = new JPanel(); //按钮

            JLabel cardNumberLabel = new JLabel("卡号");
            JTextField cardNumber = new JTextField(20);
            JLabel userNameLabel = new JLabel("姓名");
            JTextField userName = new JTextField(20);
            JLabel balanceLabel = new JLabel("余额");
            JTextField balance = new JTextField(20);
            JLabel gradeLabel = new JLabel("卡种类");

            panel4.add(gradeLabel);
            ButtonGroup classGroup = new ButtonGroup();
            for (CardType type : CardType.values()) {
                JRadioButton radioButton = new JRadioButton(type.getName());
                classGroup.add(radioButton);
                panel4.add(radioButton);
            }

            cardNumberLabel.setLabelFor(cardNumber);
            userNameLabel.setLabelFor(userName);
            balanceLabel.setLabelFor(balance);

            JButton confirm = new JButton("确定");
            JButton cancel = new JButton("取消");

            ActionListener submitUserName = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String name = userName.getText(); //输入的用户名
                        BigDecimal money = new BigDecimal(balance.getText()); //输入的余额
                        long number = Long.parseLong(cardNumber.getText()); //输入的卡号
                        CardType cardGrade = null;
                        for (Enumeration<AbstractButton> buttons = classGroup.getElements(); buttons.hasMoreElements
                                (); ) {
                            AbstractButton button = buttons.nextElement();
                            if (button.isSelected()) {
                                cardGrade = CardType.valueOf(button.getText());
                                break;
                            }
                        }
                        if (cardGrade == null) {
                            throw new NumberFormatException();
                        }
                        //创建对象
                        Card newCard = new Card(name, money, number, cardGrade, Currency.getInstance(Locale.getDefault()));
                        currentAccount.addCard(newCard);
                        currentCard = newCard;
                        updateStatusBar();
                        logger.fine(newCard.toString());
                        dialog.dispose();
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(dialog, "输入信息不合标准", "输入错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            confirm.addActionListener(submitUserName);
            userName.addActionListener(submitUserName);
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
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            return dialog;
        }

    }

    /*动作 菜单*/
    private class OperateMenu extends JMenu {
        JMenuItem deposit = new JMenuItem("存钱");
        JMenuItem withdraw = new JMenuItem("取钱");

        OperateMenu() {
            setText("操作");
            createMenu();
        }

        private void createMenu() {
            add(deposit);
            add(withdraw);

            deposit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentAccount == null) {
                        JOptionPane.showMessageDialog(null, "还未创建账户，操作失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String input = JOptionPane.showInputDialog(null, "输入存款金额", "存款", JOptionPane.QUESTION_MESSAGE);
                        BigDecimal money = new BigDecimal(input);
                        BigDecimal balance = currentCard.getBalance();
                        currentCard.setBalance(balance.add(money));
                        logger.fine(String.format("%s %s 向 %s 存入%s%s", dateFormat.format(new Date()), currentAccount,
                                                  currentCard, currentCard.getCurrency(), money));
                        updateStatusBar();
                    }
                }
            });
            withdraw.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentAccount == null) {
                        JOptionPane.showMessageDialog(null, "还未创建账户，操作失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String input = showInputDialog(null, "输入取款金额", "存款", JOptionPane.QUESTION_MESSAGE);
                        BigDecimal money = new BigDecimal(input);
                        BigDecimal balance = currentCard.getBalance();
                        BigDecimal newMoney = balance.subtract(money);
                        if (newMoney.compareTo(BigDecimal.ZERO) >= 0) {
                            logger.fine(String.format("%s %s 从 %s 取出%s%s", dateFormat.format(new Date()),
                                                      currentAccount, currentCard, currentCard.getCurrency()
                                                              .getCurrencyCode(), money));
                            currentCard.setBalance(newMoney);
                            updateStatusBar();

                        } else {
                            JOptionPane.showMessageDialog(null, "账户余额不足，操作失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }
    }

    /*菜单栏 创建*/
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new CreateMenu());
        menuBar.add(new OperateMenu());
        menuBar.add(Box.createGlue());
        // menuBar.add(helpMenu());
        // JMenuItem exit = new JMenuItem("退出");
        // exit.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         dispose();
        //     }
        // });
        // menuBar.add(exit);
        JMenuItem about = new JMenu("关于");
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "作者: 黄春翔\n学号: 1517440121", "关于", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menuBar.add(about);
        return menuBar;
    }

    /*操作记录 GUI中部*/
    private JPanel createInformation() {
        JPanel panel = new JPanel();
        JList<Account> accountJList = new JList<>();

        panel.add(accountJList);
        return panel;
    }

    /*状态栏*/
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

    private void updateStatusBar() {
        if (currentAccount != null) {
            userName.setText(currentAccount.getUserName());
            Card card = currentCard;
            if (card != null) {
                cardNumber.setText(Long.toString(card.getCardNumber()));
                cardGrade.setText(card.getCardGrade().getName());
                balance.setText(card.getCurrency().getSymbol() + card.getBalance());
            }
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
        JPanel statusBar = createStatusBar();
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
