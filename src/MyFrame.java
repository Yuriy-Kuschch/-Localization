import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


public class MyFrame extends JFrame {

    private String currentResource;
    private Map<String, Locale> resourceToLocale = new HashMap<>();

    public JPanel panel;

    public MyFrame() throws IOException {
        resourceToLocale.put("MessageBundle_uk_UA", new Locale("uk", "UA"));
        resourceToLocale.put("MessageBundle_en_US", new Locale("en", "US"));
        resourceToLocale.put("MessageBundle_de_DE", new Locale("de", "DE"));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 156);
        panel = new JPanel();
        setContentPane(panel);
        panel.setLayout(null);
        final JLabel label = new JLabel();
        label.setBounds(16, 21, 204, 17);
        panel.add(label);

        Image uaImage = ImageIO.read(getClass().getResource("ukraine-flag-icon-16.png"));
        JRadioButton radioButtonUk = new JRadioButton("Українська", new ImageIcon(uaImage));
        radioButtonUk.addActionListener(e -> setLanguage(label, "MessageBundle_uk_UA"));
        radioButtonUk.setBounds(42, 59, 109, 23);
        panel.add(radioButtonUk);
        Image usImage = ImageIO.read(getClass().getResource("united-states-of-america-flag-icon-16.png"));
        JRadioButton radioButtonEnglish = new JRadioButton("English", new ImageIcon(usImage));
        radioButtonEnglish.addActionListener(e -> setLanguage(label, "MessageBundle_en_US"));
        radioButtonEnglish.setBounds(153, 59, 109, 23);
        panel.add(radioButtonEnglish);
        Image geImage = ImageIO.read(getClass().getResource("germany-flag-icon-16.png"));
        JRadioButton radioButtonGerman = new JRadioButton("German", new ImageIcon(geImage));
        radioButtonGerman.addActionListener(e -> setLanguage(label, "MessageBundle_de_DE"));
        radioButtonGerman.setBounds(264, 59, 109, 23);
        panel.add(radioButtonGerman);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveToFile());
        saveButton.setBounds(27, 89, 109, 23);
        panel.add(saveButton);
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadFromFile(radioButtonUk, radioButtonEnglish, radioButtonGerman));
        loadButton.setBounds(280, 89, 109, 23);
        panel.add(loadButton);
        ButtonGroup myGroup = new ButtonGroup();
        myGroup.add(radioButtonUk);
        myGroup.add(radioButtonEnglish);
        myGroup.add(radioButtonGerman);

        loadButton.doClick();
    }

    private void loadFromFile(JRadioButton radioButtonUk, JRadioButton radioButtonEnglish, JRadioButton radioButtonGerman) {
        getFromFile();
        switch (currentResource) {
            case "MessageBundle_uk_UA":
                radioButtonUk.doClick();
                break;
            case "MessageBundle_en_US":
                radioButtonEnglish.doClick();
                break;
            case "MessageBundle_de_DE":
                radioButtonGerman.doClick();
                break;
        }
    }     private void getFromFile() {
        File file = new File("text.txt");
        byte[] bytes = new byte[(int)file.length()];
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {
            inputStream.readFully(bytes);
            currentResource = new String(bytes);
            System.out.println("Success");
        }
        catch (FileNotFoundException ex) {
            currentResource = "MessageBundle_uk_UA";
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void saveToFile() {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("text.txt"))) {
            outputStream.writeBytes(currentResource);
            System.out.println("Success");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void setLanguage(JLabel label, String resourceFileName) {
        ResourceBundle message = ResourceBundle.getBundle(resourceFileName, resourceToLocale.get(resourceFileName));
        label.setText(message.getString("Title"));
        currentResource = resourceFileName;
    }

}