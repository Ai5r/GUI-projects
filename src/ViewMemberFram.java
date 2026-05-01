import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

public class ViewMemberFram extends javax.swing.JFrame {

    public ViewMemberFram() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        rootPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        showButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        memberTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        rootPanel.setBackground(new java.awt.Color(0, 153, 204));

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("View Members");

        showButton.setText("Show");
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        memberTextArea.setColumns(20);
        memberTextArea.setRows(8);
        memberTextArea.setEditable(false);
        jScrollPane1.setViewportView(memberTextArea);

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(showButton)
                        .addGap(18, 18, 18)
                        .addComponent(backButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        rootPanelLayout.setVerticalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showButton)
                    .addComponent(backButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            AppDataStore store = new AppDataStore();
            List<UserRecord> users = store.getAllUsers();
            if (users.isEmpty()) {
                memberTextArea.setText("No registered members found");
                return;
            }
            StringBuilder text = new StringBuilder();
            int i = 1;
            for (UserRecord user : users) {
                text.append(i++)
                    .append(". ")
                    .append(user.getName())
                    .append(" | ")
                    .append(user.getPhone())
                    .append(" | ")
                    .append(user.getEmail())
                    .append("\n");
            }
            memberTextArea.setText(text.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load members");
        }
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        HomeFram homeFram = new HomeFram();
        homeFram.setVisible(true);
        dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewMemberFram().setVisible(true);
            }
        });
    }

    private javax.swing.JButton backButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea memberTextArea;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JButton showButton;
    private javax.swing.JLabel titleLabel;
}
