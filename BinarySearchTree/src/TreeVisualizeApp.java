import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TreeVisualizeApp extends JFrame {
    private BinarySearchTree<Integer> binarySearchTree;
    private JTextField valueField;
    private JButton insertButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton printButton;
    //private JTextField travsevField;

    public TreeVisualizeApp() {
        binarySearchTree = new BinarySearchTree<Integer>();

        // Thêm các giá trị mẫu vào cây nhị phân
        /* binarySearchTree.insert(50);
        binarySearchTree.insert(30);
        binarySearchTree.insert(70);
        binarySearchTree.insert(20);
        binarySearchTree.insert(40);
        binarySearchTree.insert(60);
        binarySearchTree.insert(80); */

        valueField = new JTextField(10);
        insertButton = new JButton("Insert");
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        printButton = new JButton("Print");
        //travsevField = new JTextField(100);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertValue();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteValue();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchValue();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printValue();;
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Value:"));
        inputPanel.add(valueField);
        inputPanel.add(insertButton);
        inputPanel.add(deleteButton);
        inputPanel.add(searchButton);
        inputPanel.add(printButton);
        //inputPanel.add(travsevField);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Binary Search Tree Visualization");
        setSize(800, 600);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Vẽ cây nhị phân
        int startX = getWidth() / 2;
        int startY = 50;
        int levelGap = 70;
        int nodeRadius = 15;

        drawNode(g, binarySearchTree.getRoot(), startX, startY, levelGap, nodeRadius);
    }

    public void drawNode(Graphics g, LinkedBinaryTree.Node<Integer> node, int x, int y, int levelGap, int nodeRadius) {
        if (node == null) {
            return;
        }
    
        // Vẽ node
        g.setColor(Color.WHITE);
        g.fillOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
    
        g.setColor(Color.BLACK);
        g.drawOval(x - nodeRadius, y - nodeRadius, 2 * nodeRadius, 2 * nodeRadius);
        g.drawString(node.getElement().toString(), x - 5, y + 5);
    
        // Vẽ liên kết đến node con bên trái
        if (node.getLeft() != null) {
            int leftX = x - levelGap;
            int leftY = y + 2 * nodeRadius;
            g.setColor(Color.BLACK);
            g.drawLine(x, y + nodeRadius, leftX, leftY - nodeRadius);
    
            // Đệ quy vẽ cây con bên trái
            drawNode(g, node.getLeft(), leftX, leftY, levelGap, nodeRadius);
        }
    
        // Vẽ liên kết đến node con bên phải
        if (node.getRight() != null) {
            int rightX = x + levelGap;
            int rightY = y + 2 * nodeRadius;
            g.setColor(Color.BLACK);
            g.drawLine(x, y + nodeRadius, rightX, rightY - nodeRadius);
    
            // Đệ quy vẽ cây con bên phải
            drawNode(g, node.getRight(), rightX, rightY, levelGap, nodeRadius);
        }
    }

    private void insertValue() {
        try {
            int value = Integer.parseInt(valueField.getText());
            binarySearchTree.insert(value);
            repaint();
            valueField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter an integer.");
        }
    }

    private void deleteValue() {
        try {
            int value = Integer.parseInt(valueField.getText());
            binarySearchTree.delete(value);
            repaint();
            valueField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter an integer.");
        }
    }

    private void searchValue() {
        try {
            int value = Integer.parseInt(valueField.getText());
            boolean found = binarySearchTree.search(value);
            String message = found ? "Value found in the tree." : "Value not found in the tree.";
            JOptionPane.showMessageDialog(this, message);
            valueField.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter an integer.");
        }
    }

    private void printValue() {
        try {
            binarySearchTree.printInOrderToFile("in_order.txt");
            binarySearchTree.printPreOrderToFile("pre_order.txt");
            binarySearchTree.printPostOrderToFile("post_order.txt");
            JOptionPane.showMessageDialog(this, "Print successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid value. Please enter an integer.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TreeVisualizeApp();
            }
        });
    }
}
