package notepadclone;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.io.*;


public class Notepad extends JFrame implements ActionListener
{
    JPanel panel;
    JLabel imglb;
    JMenuBar menubar;
    JMenu file, edit, helpmenu;
    JMenuItem newdoc, open, save, print, exit, copy, paste, cut, selectall, help;
    JTextArea ta;
    JScrollPane jsp;
    JFileChooser fileChooser;
    String text;


    ImageIcon iconimg;

    public Notepad()
    {
        setTitle("Notepad");
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); //full display er size nibe


        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);
        //____________________________________File Menu_____________________________________

        file = new JMenu("File");
        file.setFont(new Font("Arial", Font.PLAIN, 16));
        menubar.add(file);

        newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));  //CTRL+N...Keyevent => jeta pore press hobe, Actionevent => jei button press kore rakhbo
        newdoc.addActionListener(this);
        file.add(newdoc);

        open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);
        file.add(open);

        save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        file.add(save);

        print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);
        file.add(print);

        exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);
        file.add(exit);

        //__________________________________Edit Menu_______________________________________

        edit = new JMenu("Edit");
        edit.setFont(new Font("Arial", Font.PLAIN, 16));
        menubar.add(edit);
        

        copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);
        edit.add(copy);

        paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);
        edit.add(paste);

        cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);
        edit.add(cut);

        selectall = new JMenuItem("Select All");
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectall.addActionListener(this);
        edit.add(selectall);

        //_____________________________Help Menu____________________________________________

        helpmenu = new JMenu("Help");
        helpmenu.setFont(new Font("Arial", Font.PLAIN, 16));
        menubar.add(helpmenu);
        

        help = new JMenuItem("Help");
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        helpmenu.add(help);

        

        
        
        setJMenuBar(menubar);


        //______________________________TextArea___________________________________________

        ta = new JTextArea();
        ta.setFont(new Font("Cascadia Code SemiBold", Font.PLAIN, 22));
        ta.setLineWrap(true); // new line e chole jabe automatic
        ta.setWrapStyleWord(true); //puro word niche niye jabe

        jsp = new JScrollPane(ta);
        //jsp.setBounds(100, 100, 500, 500);
        jsp.setBorder(BorderFactory.createEmptyBorder());
        panel.add(jsp, BorderLayout.CENTER);
        
        iconimg = new ImageIcon(getClass().getClassLoader().getSystemResource("icons/notepad.png"));
        
        Image icon = iconimg.getImage();
        setIconImage(icon);


        this.add(panel);
        this.setVisible(true);
    }


    public void actionPerformed(ActionEvent ae)
    {
       if(ae.getSource() == newdoc)
       {
            ta.setText("");
       }

       if(ae.getSource() == open)
       {
            fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false); //shb file type accept korbe na
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt"); // shudu txt file accept korbe
            fileChooser.addChoosableFileFilter(restrict);

            int action = fileChooser.showOpenDialog(this); // dialog box open korbe

            if(action != JFileChooser.APPROVE_OPTION)
            {
                return;
            }

            File file = fileChooser.getSelectedFile();

            try 
            {
                BufferedReader reader = new BufferedReader(new FileReader(file)); // File ke read korbe
                ta.read(reader, null);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
       }

       if(ae.getSource() == save)
       {
            JFileChooser saveas = new JFileChooser();
            saveas.setApproveButtonText("Save");

            int action = saveas.showOpenDialog(this);

            if(action != JFileChooser.APPROVE_OPTION)
            {
                return;
            }

            File filename = new File(saveas.getSelectedFile() + ".txt");
            BufferedWriter writer = null;
            try 
            {
                writer = new BufferedWriter(new FileWriter(filename));
                ta.write(writer);
                JOptionPane.showMessageDialog(this, "File Saved");
            }
            catch(Exception e)
            {
                System.out.println(e);
            }

       }

       if(ae.getSource() == print)
       {
            try 
            {
                ta.print();
            }

            catch(Exception e)
            {
                System.out.println(e);
            }

       }

       if(ae.getSource() == exit)
       {
           int choice = JOptionPane.showConfirmDialog(this, "Do you want to Exit?");

           if(choice == JOptionPane.YES_OPTION)
           {
                System.exit(0);
           }
           
       }

       if(ae.getSource() == copy)
       {
            text = ta.getSelectedText();

       }

       if(ae.getSource() == paste)
       {
            ta.insert(text, ta.getCaretPosition()); // taxt variable e ja ase insert korbe, jei position e cursor ache shekhane
       }

       if(ae.getSource() == cut)
       {
            text = ta.getSelectedText();
            ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
       }

       if(ae.getSource() == selectall)
       {
         ta.selectAll();
       }
    }
    
    
    public static void main(String[] args)
    {
        new Notepad();
    }
}

    

