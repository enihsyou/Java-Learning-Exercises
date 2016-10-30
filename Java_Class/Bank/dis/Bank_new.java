/*
* ID: 1517440121
* */

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.*;

//TODO:连接数据库
/*卡片种类选择*/
enum CardType {
    借记卡("借记卡", new DebitCard()), 信用卡("信用卡", new CreditCard()), 白金卡("白金卡", new PlatinumCard());

    private String name;
    private BaseCardType cls;

    CardType(String name, BaseCardType cls) {
        this.name = name;
        this.cls = cls;
    }

    public String getName() {
        return name;
    }

    public BaseCardType getGrade() {
        return cls;
    }

    @Override
    public String toString() {
        return name;
    }

}

/*分期*/
enum Times {
    一期(1), 二期(2), 三期(3), 六期(6), 十二期(12), 二十四期(24);
    private int i;

    Times(int i) {
        this.i = i;
    }

    public int getTimes() {
        return i;
    }

}

/*存取款动作接口*/
interface Action {
    /*存钱*/
    boolean deposit(BigDecimal amount);

    /*取钱*/
    boolean withdraw(BigDecimal amount);

    /*取钱 分期*/
    boolean withdraw(BigDecimal amount, int times);
}


/*卡片基类*/
abstract class BaseCardType {
    BigDecimal fee; //手续费
    BigDecimal quota; //透支限额
    String name;
    DecimalFormat decimalFormatter = new DecimalFormat("+#.00");

    BigDecimal getFee() {
        return fee;
    }

    BigDecimal getQuota() {
        return quota;
    }

    abstract BigDecimal getTotal(BigDecimal amount);

    @Override
    public String toString() {
        return name;
    }
}

/*借记卡*/
class DebitCard extends BaseCardType {
    DebitCard() {
        fee = BigDecimal.ZERO;
        quota = BigDecimal.ZERO;
        name = "借记卡";
    }

    BigDecimal getTotal(BigDecimal amount) {
        return amount;
    }
}

/*信用卡*/
class CreditCard extends BaseCardType {
    CreditCard() {
        fee = new BigDecimal(0.01);
        quota = new BigDecimal(1000);
        name = "信用卡";
    }

    BigDecimal getTotal(BigDecimal amount) {
        return amount.add(amount.multiply(fee));
    }
}

/*白金卡*/
class PlatinumCard extends BaseCardType {
    PlatinumCard() {
        fee = new BigDecimal(0.05);
        quota = new BigDecimal(10000);
        name = "白金卡";
    }

    BigDecimal getTotal(BigDecimal amount) {
        return amount.add(amount.multiply(fee));
    }
}

/*卡片主体*/
class Card implements Action {
    private long cardNumber; //TODO: 包装成类
    private BigDecimal balance; //余额
    private BigDecimal remain; //剩余可透支
    private NumberFormat numberFormatter; //格式化用//TODO:币种
    private BaseCardType cardGrade;
    private DefaultTableModel tableModel = new DefaultTableModel(new String[]{"No.", "时间", "操作", "变动", "余额"}, 0);
    private int rowNumber = 1; //第几次操作
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private JTable table;

    Card(BigDecimal balance, long cardNumber, BaseCardType cardGrade, Locale locale) {
        this.balance = balance;
        this.cardNumber = cardNumber;
        numberFormatter = NumberFormat.getCurrencyInstance(locale);
        this.cardGrade = cardGrade;
        remain = cardGrade.getQuota();
        /*每张卡给一个独立的操作记录*/
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
    }

    @Override
    public boolean deposit(BigDecimal amount) {
        BigDecimal quota = cardGrade.getQuota(); //可透支额度
        BigDecimal last = amount.subtract(quota.subtract(remain)); //存入减去还款剩下的
        if (last.compareTo(BigDecimal.ZERO) >= 0) { //还款过多
            balance = balance.add(last);
            remain = quota;
        } else {
            remain = quota.add(last);
        }
        addLog("存款", amount);
        return true;
    }

    @Override
    public boolean withdraw(BigDecimal amount, int times) {
        String operate;
        BigDecimal fee;
        //数量
        BigDecimal total = cardGrade.getTotal(amount); //总计
        if (times > 1) {
            total = cardGrade.getTotal(amount.divide(new BigDecimal(times), BigDecimal.ROUND_HALF_UP));
            fee = cardGrade.getFee().multiply(amount.divide(new BigDecimal(times), BigDecimal.ROUND_HALF_UP));
            if (cardGrade instanceof DebitCard) operate = "付款";
            else operate = String.format("付款(分%d期)", times);
        } else if (times == 1) {
            operate = "付款";
            fee = cardGrade.getFee().multiply(amount); //手续费
        } else {
            operate = "取款";
            fee = cardGrade.getFee().multiply(amount);
        } //手续费
        boolean status = withdraw(total);
        if (status) addLog(operate, total, fee);
        return status;
    }

    @Override
    public boolean withdraw(BigDecimal amount) {
        BigDecimal newMoney = balance.subtract(amount);
        if (newMoney.compareTo(BigDecimal.ZERO) >= 0) { //余额足够
            balance = newMoney;
        } else if (newMoney.add(remain).compareTo(BigDecimal.ZERO) >= 0) { //加上透支 足够
            remain = remain.subtract(amount.subtract(balance));
            balance = BigDecimal.ZERO;
        } else {
            return false;
        }
        return true;
    }

    /*获取卡号*/
    long getCardNumber() {
        return cardNumber;
    }

    /*获取卡片类型*/
    BaseCardType getCardGrade() {
        return cardGrade;
    }

    /*获取余额*/
    BigDecimal getBalance() {
        return balance;
    }

    /*获取剩余额度*/
    BigDecimal getRemain() {
        return remain;
    }

    /*添加记录到自身表格 标准记录*/
    void addLog(String operate, BigDecimal money) {
        addLog(operate, format(money));
    }

    /*添加记录到自身表格 带有手续费的记录*/
    private void addLog(String operate, BigDecimal money, BigDecimal fee) {
        if (fee.compareTo(BigDecimal.ZERO) == 0) addLog(operate, format(money));
        else addLog(operate, format(money) + "(" + format(fee) + ")");
    }

    private void addLog(String... strings) {
        String dataNow = dateFormat.format(new Date());
        String balanceString = format(balance.subtract(cardGrade.getQuota().subtract(remain)));
        tableModel.addRow(new String[]{"" + rowNumber++, dataNow, strings[0], strings[1], balanceString});
    }

    JTable getTable() {
        return table;
    }

    String formatBalance() {
        return numberFormatter.format(balance);
    }

    String formatRemain() {
        return numberFormatter.format(remain);
    }

    private String format(BigDecimal money) {
        return numberFormatter.format(money);

    }

    @Override
    public String toString() {
        return String.format("%s %s", cardGrade, cardNumber);
    }
}

/*个人账户*/
class Account {
    private String userName; //账户名
    private ArrayList<Card> cards; //名下卡
    private Card currentCard; //当前选择的卡

    Account(String userName) {
        this.userName = userName;
        cards = new ArrayList<>();
        currentCard = null;
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
        currentCard = card;
    }

    /**
     * @param card 打算删除的卡片，需要在卡片集里
     *
     * @return Card 返回集合中的可用卡片，不可用时返回null
     */
    Card removeCard(Card card) {
        cards.remove(card);
        if (cards.isEmpty()) {
            currentCard = null;
        } else {
            currentCard = cards.get(0);
        }
        return currentCard;
    }

    Card getCurrentCard() {
        return currentCard;
    }

    void setCurrentCard(Card card) {
        currentCard = card;
    }

    @Override
    public String toString() {
        return userName;
    }
}

/*构建GUI*/
class GUI extends JFrame {
    private Logger logger = Logger.getGlobal();
    private ArrayList<Account> accounts = new ArrayList<>();
    private Account currentAccount;
    private Card currentCard;
    /*状态栏标签*/
    private JLabel userNameStatusLabel = new JLabel(); //用户名
    private JLabel cardGradeStatusLabel = new JLabel(); //卡种
    private JLabel cardNumberStatusLabel = new JLabel(); //卡号
    private JLabel overFlowStatusLabel = new JLabel(); //可透支
    private JLabel balanceStatusLabel = new JLabel(); //余额

    private JScrollPane informationScrollPane; //中部信息面板
    private JTable defaultTable; //默认信息模板 初始化用

    GUI() {
        setTitle("银行");
        /*设置日志*/
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
    }

    /*菜单 新建*/
    private class CreateMenu extends JMenu {
        JMenuItem createAccount = new JMenuItem("创建账户");
        JMenuItem selectAccount = new JMenuItem("选择账户");
        JMenuItem deleteAccount = new JMenuItem("删除账户");
        JMenuItem createCard = new JMenuItem("创建新卡");
        JMenuItem selectCard = new JMenuItem("选择卡片");
        JMenuItem deleteCard = new JMenuItem("删除卡片");

        CreateMenu() {
            setText("账户");
            createMenu();
            updateMenu();
        }

        private void createMenu() {
            createAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String optionPane = ((String) JOptionPane.showInputDialog(
                            GUI.super.getRootPane(), "姓名", "创建账号", JOptionPane.PLAIN_MESSAGE, null, null, null)).trim();
                    if (!optionPane.isEmpty()) {
                        try {
                            Account newAccount = new Account(optionPane);//创建对象
                            currentAccount = newAccount;
                            accounts.add(newAccount);
                            currentCard = null;
                            informationScrollPane.setViewportView(defaultTable);
                            logger.fine("创建新账户" + newAccount);
                        } catch (NumberFormatException e1) {
                            showMessageDialog(GUI.super.getRootPane(), "输入信息不合标准", "输入错误", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    updateMenu();
                    updateStatus();
                }
            });
            selectAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox<Object> list = new JComboBox<>(accounts.toArray());
                    if (currentAccount != null) {
                        list.setSelectedItem(currentAccount);
                    } else {
                        list.setSelectedIndex(0);
                    }
                    int result = JOptionPane.showConfirmDialog(GUI.super.getRootPane(), list);
                    if (result == JOptionPane.OK_OPTION) {
                        currentAccount = (Account) list.getSelectedItem();
                        currentCard = currentAccount.getCurrentCard();
                        if (currentCard != null) {
                            informationScrollPane.setViewportView(currentAccount.getCards().get(0).getTable());
                            logger.fine(String.format("切换到%s@%s", currentAccount, currentCard));
                        } else logger.fine(String.format("切换到账户%s", currentAccount));
                        createCard.setEnabled(true);
                        updateMenu();
                        updateStatus();
                    }
                }
            });
            deleteAccount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int result = JOptionPane.showConfirmDialog(GUI.super.getRootPane(),
                                String.format("真的要删除账户%s吗？", currentAccount.getUserName()), "确认删除",
                                JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            accounts.remove(currentAccount);
                            logger.fine(String.format("删除账户%s", currentAccount));
                            currentCard = null;
                            currentAccount = null;
                            informationScrollPane.setViewportView(defaultTable);
                        }
                    } catch (NullPointerException ignore) {
                    }
                    updateMenu();
                    updateStatus();
                }
            });
            createCard.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog dialog = createCardDialog();
                    dialog.setVisible(true);
                    deleteCard.setEnabled(true);
                    selectCard.setEnabled(true);
                    updateMenu();
                    updateStatus();
                }
            });
            selectCard.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentAccount == null) {
                        return;
                    }
                    JComboBox<Object> list = new JComboBox<>(currentAccount.getCards().toArray());
                    list.setSelectedItem(currentCard);
                    int result = JOptionPane.showConfirmDialog(GUI.super.getRootPane(), list);
                    if (result == JOptionPane.OK_OPTION) {
                        currentCard = (Card) list.getSelectedItem();
                        currentAccount.setCurrentCard(currentCard);
                        informationScrollPane.setViewportView(currentCard.getTable());
                        logger.fine(String.format("切换到%s@%s", currentAccount, currentCard));
                    }
                    updateMenu();
                    updateStatus();
                }
            });
            deleteCard.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int result = JOptionPane.showConfirmDialog(GUI.super.getRootPane(),
                                String.format("真的要删除卡片%s吗？", currentCard.getCardNumber()), "确认删除",
                                JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            logger.fine(String.format("删除卡片%s@%s", currentAccount, currentCard));
                            currentCard = currentAccount.removeCard(currentCard);
                        }
                    } catch (NullPointerException ignore) {
                    }
                    updateMenu();
                    updateStatus();
                }
            });
            /*添加组件*/
            add(createAccount);
            add(selectAccount);
            add(deleteAccount);
            addSeparator();
            add(createCard);
            add(selectCard);
            add(deleteCard);
        }

        private JDialog createCardDialog() {
            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
            JPanel panel1 = new JPanel(); //卡号一栏
            JPanel panel3 = new JPanel(); //预设余额一栏
            JPanel panel4 = new JPanel(); //种类选择
            JPanel panel5 = new JPanel(); //按钮

            JLabel cardNumberLabel = new JLabel("卡号");
            JTextField cardNumber = new JTextField(20);
            JLabel balanceLabel = new JLabel("余额");
            JTextField balanceField = new JTextField(20);
            JLabel gradeLabel = new JLabel("卡种类");

            panel4.add(gradeLabel);
            ButtonGroup classGroup = new ButtonGroup();
            for (CardType type : CardType.values()) {
                JRadioButton radioButton = new JRadioButton(type.getName());
                classGroup.add(radioButton);
                panel4.add(radioButton);
            }

            cardNumberLabel.setLabelFor(cardNumber);
            balanceLabel.setLabelFor(balanceField);

            JButton confirm = new JButton("确定");
            JButton cancel = new JButton("取消");

            ActionListener submitUserName = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        long number = Long.parseLong(cardNumber.getText()); //输入的卡号
                        BigDecimal money = new BigDecimal(balanceField.getText()); //输入的余额
                        BaseCardType cardGrade = null;
                        for (Enumeration<AbstractButton> buttons = classGroup.getElements(); buttons.hasMoreElements
                                (); ) {
                            AbstractButton button = buttons.nextElement();
                            if (button.isSelected()) {
                                cardGrade = CardType.valueOf(button.getText()).getGrade();
                                break;
                            }
                        }
                        if (cardGrade == null) throw new NumberFormatException();
                        //创建对象
                        Card newCard = new Card(money, number, cardGrade, Locale.getDefault());
                        currentAccount.addCard(newCard);
                        currentCard = newCard;
                        logger.fine("创建" + newCard);
                        informationScrollPane.setViewportView(currentCard.getTable());
                        currentCard.addLog("开卡", money);
                        updateStatus();
                        dialog.dispose();
                    } catch (NumberFormatException e1) {
                        showMessageDialog(dialog, "输入有误，请修正", "输入错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            confirm.addActionListener(submitUserName);
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            panel1.add(cardNumberLabel);
            panel1.add(cardNumber);
            panel3.add(balanceLabel);
            panel3.add(balanceField);

            panel5.add(confirm);
            panel5.add(cancel);

            dialog.add(panel1);
            dialog.add(panel3);
            dialog.add(panel4);
            dialog.add(panel5);

            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);
            return dialog;
        }

        private void updateMenu() {
            if (currentAccount == null) { //没有创建账号
                selectAccount.setEnabled(false);
                deleteAccount.setEnabled(false);
                createCard.setEnabled(false);
                selectCard.setEnabled(false);
                deleteCard.setEnabled(false);
            } else if (currentCard == null) { //创建了账号但是没有卡片
                if (!accounts.isEmpty()) selectAccount.setEnabled(true);
                deleteAccount.setEnabled(true);
                createCard.setEnabled(true);
                if (currentAccount.getCards().isEmpty()) {
                    informationScrollPane.setViewportView(defaultTable);
                    deleteCard.setEnabled(false);
                    selectCard.setEnabled(false);
                } else {
                    informationScrollPane.setViewportView(currentAccount.getCurrentCard().getTable());
                    deleteCard.setEnabled(true);
                    selectCard.setEnabled(true);
                }
            } else { //有账号也有卡片
                selectCard.setEnabled(true);
                deleteCard.setEnabled(true);
            }
        }
    }

    /*菜单 动作*/
    private class OperateMenu extends JMenu {
        JMenuItem deposit = new JMenuItem("存款");
        JMenuItem withdraw = new JMenuItem("取款");
        JMenuItem pay = new JMenuItem("付款");

        OperateMenu() {
            setText("操作");
            createMenu();
        }

        private void createMenu() {
            add(deposit);
            add(withdraw);
            add(pay);

            deposit.addActionListener(new ActionListener() {
                String operate = "存款";

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentCard == null) {
                        showMessageDialog(GUI.super.getRootPane(), "还未创建账户或卡，操作失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String input = JOptionPane.showInputDialog(GUI.super.getRootPane(), "输入存款金额", operate,
                                JOptionPane.QUESTION_MESSAGE);
                        if (input == null) return;
                        try {
                            BigDecimal money = new BigDecimal(input);
                            currentCard.deposit(money);
                            logger.fine(String.format("%s@%s %s %s", currentAccount, currentCard, operate, money));
                        } catch (NullPointerException | NumberFormatException ignored) {
                            showMessageDialog(GUI.super.getRootPane(), "操作失败", "失败", JOptionPane.ERROR_MESSAGE);
                        }
                        updateStatus();
                    }
                }
            });
            withdraw.addActionListener(new ActionListener() {
                String operate = "取款";

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentCard == null) {
                        showMessageDialog(GUI.super.getRootPane(), "还未创建账户或卡，操作失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String input = showInputDialog(
                                GUI.super.getRootPane(), "输入取款金额", operate, JOptionPane.QUESTION_MESSAGE);
                        if (input == null) return;
                        try {
                            BigDecimal money = new BigDecimal(input);
                            boolean status = currentCard.withdraw(money, 0);
                            if (status) {
                                logger.fine(String.format("%s@%s %s %s", currentAccount, currentCard, operate, money));
                            } else showMessageDialog(null, "账户余额不足，操作失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                        } catch (NullPointerException | NumberFormatException ignored) {
                            showMessageDialog(GUI.super.getRootPane(), "操作失败", "失败", JOptionPane.ERROR_MESSAGE);
                        }
                        updateStatus();
                    }
                }
            });
            pay.addActionListener(new ActionListener() {
                String operate = "付款";

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentCard == null) {
                        showMessageDialog(GUI.super.getRootPane(), "还未创建账户或卡，操作失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JPanel panel = new JPanel(new GridLayout(2, 1));
                        JLabel label1 = new JLabel("输入支付金额");
                        JLabel label2 = new JLabel("预计支付期数");
                        JTextField textField = new JTextField(7);
                        JComboBox<Times> list = new JComboBox<>(Times.values());
                        if (currentCard.getCardGrade().equals(CardType.借记卡.getGrade())) list.setEnabled(false);
                        list.setSelectedIndex(0); //默认选上第一个
                        label1.setLabelFor(textField);
                        JPanel panel1 = new JPanel();
                        JPanel panel2 = new JPanel();
                        panel1.add(label1);
                        panel1.add(textField);
                        panel2.add(label2);
                        panel2.add(list);
                        panel.add(panel1);
                        panel.add(panel2);
                        if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(GUI.super.getRootPane(), panel,
                                operate, OK_CANCEL_OPTION)) {
                            try {
                                String input = textField.getText();
                                int times = ((Times) list.getSelectedItem()).getTimes();
                                BigDecimal money = new BigDecimal(input);
                                boolean status = currentCard.withdraw(money, times);
                                if (times > 1) operate = String.format("付款(分%d期)", times);
                                else operate = "付款";
                                if (status) {
                                    logger.fine(String.format("%s@%s %s %s", currentAccount, currentCard, operate,
                                            money.divide(new BigDecimal(times), BigDecimal.ROUND_HALF_UP)));
                                } else {
                                    showMessageDialog(null, "账户余额不足，操作失败", "操作失败", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (NullPointerException | NumberFormatException ignored) {
                                showMessageDialog(GUI.super.getRootPane(), "操作失败", "失败", JOptionPane.ERROR_MESSAGE);
                            }
                            updateStatus();
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
        JMenuItem about = new JMenu("关于");
        about.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showMessageDialog(GUI.super.getRootPane(), "作者: 黄春翔\n学号: 1517440121", "关于", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menuBar.add(about);
        return menuBar;
    }

    /*操作记录 GUI中部*/
    private JScrollPane createInformation() {
        defaultTable = new JTable(new DefaultTableModel(new String[]{"No.", "时间", "操作", "变动", "余额"}, 0)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        defaultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        defaultTable.getColumnModel().getColumn(0).setPreferredWidth(15);
        defaultTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        defaultTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        defaultTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        defaultTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        informationScrollPane = new JScrollPane(defaultTable);
        informationScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return informationScrollPane;
    }

    /*状态栏 创建*/
    private JPanel createStatusBar() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLoweredBevelBorder()); //边框
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        panel.add(userNameStatusLabel);
        panel.add(cardGradeStatusLabel);
        panel.add(cardNumberStatusLabel);
        JLabel padding = new JLabel();
        panel.add(padding);
        panel.add(overFlowStatusLabel);
        panel.add(balanceStatusLabel);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.ipadx = 10;
        layout.setConstraints(userNameStatusLabel, c);
        layout.setConstraints(cardGradeStatusLabel, c);
        layout.setConstraints(cardNumberStatusLabel, c);
        c.weightx = 1;
        layout.setConstraints(padding, c);
        c.weightx = 0;
        layout.setConstraints(overFlowStatusLabel, c);
        layout.setConstraints(balanceStatusLabel, c);

        panel.setSize(getWidth(), 60);

        updateStatus();
        return panel;
    }

    /*更新状态*/
    private void updateStatus() {
        if (currentCard != null) {
            informationScrollPane.setViewportView(currentCard.getTable());
        }
        userNameStatusLabel.setText("姓名");
        cardGradeStatusLabel.setText("卡种");
        cardNumberStatusLabel.setText("卡号");
        overFlowStatusLabel.setText("");
        balanceStatusLabel.setText(Currency.getInstance(Locale.getDefault()).getSymbol() + "0");
        //创建了账号
        if (currentAccount != null) {
            userNameStatusLabel.setText(currentAccount.getUserName());
            Card card = currentCard;
            //创建了卡
            if (card != null) {
                cardNumberStatusLabel.setText(Long.toString(card.getCardNumber()));
                cardGradeStatusLabel.setText(card.getCardGrade().toString());
                if (!CardType.借记卡.getName().equals(card.getCardGrade().toString())) { //存在可透支
                    overFlowStatusLabel.setText(card.formatRemain());
                }
                balanceStatusLabel.setText(card.formatBalance());
            }
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
        add(createInformation(), BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
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
