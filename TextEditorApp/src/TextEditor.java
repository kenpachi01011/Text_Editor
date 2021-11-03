import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JLabel fontLabel;
    JButton fontColorButton;
    JComboBox fontBox;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;


    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text Modifier");
        this.setSize(680,680); 
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.darkGray);
        this.setLayout(new FlowLayout());
        this.setResizable(false);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial",Font.PLAIN,21));
        textArea.setBackground(Color.LIGHT_GRAY);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(640,640));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        fontLabel = new JLabel("Font:");
        fontLabel.setForeground(Color.white);

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50,25));
        fontSizeSpinner.setValue(21);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN ,
                        (Integer) fontSizeSpinner.getValue()));
            }
        });

        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        //-------------- MENU BAR  ---------------------------------


            menuBar = new JMenuBar();
            fileMenu = new JMenu("File");
            openItem = new JMenuItem("Open");
            saveItem = new JMenuItem("save");
            exitItem = new JMenuItem("Exit");

            openItem.addActionListener(this);
            saveItem.addActionListener(this);
            exitItem.addActionListener(this);

            fileMenu.add(openItem);
            fileMenu.add(saveItem);
            fileMenu.add(exitItem);
            menuBar.add(fileMenu);


        //-------------- \MENU BAR  --------------------------------


        this.setJMenuBar(menuBar);
        this.add(fontColorButton);
        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == fontColorButton){
            JColorChooser colorChooser = new JColorChooser();

            Color color = colorChooser.showDialog(null,"Chose a color"
                    ,Color.black);
            textArea.setForeground(color);
        }
        if (e.getSource() == fontBox){
            textArea.setFont(new Font((String) fontBox.getSelectedItem(),
                    Font.PLAIN,textArea.getFont().getSize()));
        }
        if (e.getSource() == openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files"
                    ,"txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()){
                        while (fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } finally {
                    fileIn.close();
                }
            }

        }
        if (e.getSource() == saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                finally {
                    fileOut.close();
                }
            }
        }
        if (e.getSource() == exitItem){
            System.exit(0);
        }

    }
}
