import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ToDoListGui extends JFrame implements ActionListener {
    // taskPanel will act as the container for the taskComponentPanel
    // taskComponentPanel will store all of the taskComponents
    private JPanel taskPanel, taskComponentPanel;

    public ToDoListGui() {
        super("To Do List Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(CommontConstants.GUI_SIZE);
        setLocationRelativeTo(null);
        setResizable(true); // Memungkinkan jendela untuk diubah ukurannya
        setLayout(new BorderLayout()); // Menggunakan BorderLayout untuk pengelolaan tata letak

        addGuiComponents();
        pack(); // Mengatur ukuran jendela agar sesuai dengan komponen
    }

    private void addGuiComponents() {
        // Banner text
        JLabel bannerLabel = new JLabel("TO DO LIST");
        bannerLabel.setFont(createFont("resource/LEMONMILK-Light.otf", 36f));
        bannerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Tengah secara horizontal
        bannerLabel.setPreferredSize(new Dimension(CommontConstants.GUI_SIZE.width, 50));
        add(bannerLabel, BorderLayout.NORTH);

        // TaskPanel
        taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout());

        // TaskComponentPanel
        taskComponentPanel = new JPanel();
        taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS));
        taskPanel.add(taskComponentPanel, BorderLayout.CENTER);

        // Tambahkan scrolling ke taskPanel
        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER); // Scroll pane ada di tengah

        // Tombol Add Task
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setFont(createFont("resource/LEMONMILK-Light.otf", 18f));
        addTaskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addTaskButton.addActionListener(this);
        add(addTaskButton, BorderLayout.SOUTH); // Tombol di bagian bawah
    }

    private Font createFont(String resource, float size) {
        try {
            String filePath = getClass().getClassLoader().getResource(resource).getPath();

            // Ganti %20 dengan spasi jika ada
            filePath = filePath.replaceAll("%20", " ");

            File customFontFile = new File(filePath);
            return Font.createFont(Font.TRUETYPE_FONT, customFontFile).deriveFont(size);
        } catch (Exception e) {
            System.out.println("Error loading font: " + e);
        }
        return new Font("SansSerif", Font.PLAIN, (int) size); // Default font jika font khusus gagal dimuat
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("Add Task")) {
            // Buat task component baru
            TaskComponent taskComponent = new TaskComponent(taskComponentPanel);
            taskComponentPanel.add(taskComponent);

            // Set task sebelumnya menjadi tidak aktif
            if (taskComponentPanel.getComponentCount() > 1) {
                TaskComponent previousTask = (TaskComponent) taskComponentPanel.getComponent(
                        taskComponentPanel.getComponentCount() - 2);
                previousTask.getTaskField().setBackground(null);
            }

            // Fokuskan field task yang baru dibuat
            taskComponent.getTaskField().requestFocus();
            taskComponentPanel.revalidate();
            taskComponentPanel.repaint();
        }
    }

    // Main Method untuk menjalankan aplikasi
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoListGui().setVisible(true);
            }
        });
    }
}
