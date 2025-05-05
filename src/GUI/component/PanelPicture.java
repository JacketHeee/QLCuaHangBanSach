package GUI.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatLineBorder;

import net.miginfocom.swing.MigLayout;
import utils.ImageUtil;

public class PanelPicture extends JPanel{
    private JFileChooser fileChooser;
    private JLabel labelAnh;
    private CustomButton btnThem;
    private String pathAnh;
    private ImageUtil imageUtil = new ImageUtil();

    public PanelPicture(){
        this.fileChooser = new JFileChooser();
        this.labelAnh = new JLabel();
        this.btnThem = new CustomButton("Thêm Ảnh");
        this.init();
    }

    public void init(){
        this.setLayout(new MigLayout("wrap 1"));
        this.setBackground(Color.decode("#FFFFFF"));
        JPanel sachPanel = new JPanel();
        sachPanel.setLayout(new MigLayout("insets 0"));
        sachPanel.setPreferredSize(new Dimension(160, 210));
        sachPanel.setBackground(Color.decode("#FFFFFF"));
        sachPanel.setBorder(new FlatLineBorder(new Insets(5, 5, 5, 5), java.awt.Color.lightGray));
        sachPanel.add(labelAnh);

        this.add(sachPanel, "push, center");
        this.add(btnThem, "push, center");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Ảnh JPG & PNG & SVG", "jpg", "png", "svg");
        fileChooser.setFileFilter(filter);
        addBtnListener();
    }

    public void addBtnListener(){
        this.btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // int result = fileChooser.showOpenDialog(null);
                // if(result == JFileChooser.APPROVE_OPTION){
                //     File selectedFile = fileChooser.getSelectedFile();
                //     String path = selectedFile.getAbsolutePath();

                //     if(!path.toLowerCase().endsWith(".svg")){
                //         ImageIcon originalIcon = new ImageIcon(selectedFile.getAbsolutePath());
                //         Image image = originalIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                //         ImageIcon icon = new ImageIcon(image);
                //         labelAnh.setIcon(icon);
                //     }
                //     else{
                //         try {
                //             URL url = new File(path).toURI().toURL();
                //             FlatSVGIcon svgIcon = new FlatSVGIcon(url).derive(150, 200);
                //             labelAnh.setIcon(svgIcon);
                //         } catch (MalformedURLException e1) {
                //             e1.printStackTrace();
                //         }
                //     }
                //     pathAnh = path;
                //     btnThem.setText("Đổi ảnh");
                // }
                if (imageUtil.chooseFile()) {
                    String fileName = imageUtil.getSelectedFileName();
                    // JOptionPane.showMessageDialog(null, "Đã chọn file: " + fileName,
                    //         "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    // Hiển thị ảnh xem trước
                    BufferedImage preview = imageUtil.getPreviewImage(150,200);
                    if (preview != null) {
                        labelAnh.setIcon(new ImageIcon(preview));
                        labelAnh.setText("");
                    }
                    pathAnh = fileName;
                    btnThem.setText("Đổi ảnh");
                } else {
                    JOptionPane.showMessageDialog(null, "Không chọn được file!",
                            "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            }  
        });
    }

    public void setAnh(String path){  
        // if(!path.toLowerCase().endsWith(".svg")){
        //     ImageIcon originalIcon = new ImageIcon(path);
        //     Image image = originalIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        //     ImageIcon icon = new ImageIcon(image);
        //     labelAnh.setIcon(icon);
        // }
        // else{
        //     labelAnh.setForeground(java.awt.Color.black);
        //     try {
        //         URL url = new File(path).toURI().toURL();
        //         FlatSVGIcon svgIcon = new FlatSVGIcon(url).derive(150, 200);
        //         labelAnh.setIcon(svgIcon);
        //     } catch (MalformedURLException e1) {
        //         e1.printStackTrace();
        //     }
        // }

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/resources/img/img_product/"+path));
        Image image = originalIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        labelAnh.setIcon(new ImageIcon(image));
        pathAnh = path;
        btnThem.setText("Đổi ảnh");
    }

    public String getPath(){
        return(this.pathAnh);
    }

    public void setEdit(boolean i){
        this.btnThem.setEnabled(false);
    }

    public ImageUtil getImageUtil() {
        return imageUtil;
    }

    public void setImageUtil(ImageUtil imageUtil) {
        this.imageUtil = imageUtil;
    }

    // public static void main(String[] args) {
    //     FlatIntelliJLaf.setup();
    //     JFrame frame = new JFrame();
    //     PanelPicture picture = new PanelPicture();

    //     frame.add(picture);

    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(300, 300);
    //     frame.setLocationRelativeTo(null);
    //     frame.setVisible(true);

    // }


    
    
}
