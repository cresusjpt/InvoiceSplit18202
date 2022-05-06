import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Form extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton factureCompactButton;
    private JTextField textField1;
    private JButton buttonRename;

    private JFileChooser fileChooser;

    private File compactedFile;

    public Form() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        buttonRename.addActionListener(e -> onRename());

        factureCompactButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier facture pdf", "pdf");
            fileChooser.setFileFilter(filter);

            fileChooser.setAcceptAllFileFilterUsed(false);

            int returnValue = fileChooser.showOpenDialog(contentPane);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                compactedFile = fileChooser.getSelectedFile();
                textField1.setText(compactedFile.getAbsolutePath());
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void action(boolean renameOnly) throws IOException {
        new App().split(compactedFile, renameOnly);
        JOptionPane.showMessageDialog(contentPane, "Opération effectuée avec succès");
    }

    private void onRename() {
        try {
            action(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //dispose();
    }

    private void onOK() {
        try {
            action(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Form dialog = new Form();
        dialog.setPreferredSize(new Dimension(450, 225));
        dialog.setTitle("Outil Split Facture");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
