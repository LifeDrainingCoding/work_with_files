import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


public class Main extends JFrame implements ActionListener, WindowListener {

    JTextArea jta=new JTextArea();
    File fnameContainer;
    public Main(){

        Font fnt=new Font("Arial",Font.BOLD,20);
        Container con=getContentPane();

        JMenuBar jmb=new JMenuBar();
        JMenu jmfile = new JMenu("File");
        JMenu jmedit = new JMenu("Edit");

        con.setLayout(new BorderLayout());
        JScrollPane sbrText = new JScrollPane(jta);
        sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sbrText.setVisible(true);
        jta.setFont(fnt);
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        jta.setBackground(Color.PINK);

        con.add(sbrText);

        createMenuItem(jmfile,"New");
        createMenuItem(jmfile,"Open");
        createMenuItem(jmfile,"Save");
        jmfile.addSeparator();
        createMenuItem(jmfile,"Exit");

        createMenuItem(jmedit,"Cut");
        createMenuItem(jmedit,"Copy");
        createMenuItem(jmedit,"Paste");
        jmfile.setForeground(Color.GREEN);


        jmb.add(jmfile);
        jmb.add(jmedit);


        setJMenuBar(jmb);
        addWindowListener(this);
        setSize(1920,1080);
        setTitle("нотепад");
        setBackground(Color.PINK);
        setVisible(true);

    }

    public void createMenuItem(JMenu jm,String txt){
        JMenuItem jmi=new JMenuItem(txt);
        jmi.addActionListener(this);
        jmi.setBackground(Color.gray);
        jm.add(jmi);
    }

    public void actionPerformed(ActionEvent e){
        JFileChooser jfc=new JFileChooser();

        if(e.getActionCommand().equals("New")){
            //new
            this.setTitle("Untitled.txt - Notepad");
            jta.setText("");
            fnameContainer=null;
        }else if(e.getActionCommand().equals("Open")){
            //open
            int ret=jfc.showDialog(null,"Open");

            if(ret == JFileChooser.APPROVE_OPTION)
            {
                try{
                    File fyl=jfc.getSelectedFile();
                    OpenFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName()+ " - Notepad");
                    fnameContainer=fyl;
                }catch(IOException ers){}
            }

        }else if(e.getActionCommand().equals("Save")){
            //save
            if(fnameContainer != null){
                jfc.setCurrentDirectory(fnameContainer);
                jfc.setSelectedFile(fnameContainer);
            }
            else {
                //jfc.setCurrentDirectory(new File("."));
                jfc.setSelectedFile(new File("Untitled.txt"));
            }

            int ret=jfc.showSaveDialog(null);

            if(ret == JFileChooser.APPROVE_OPTION){
                try{

                    File fyl=jfc.getSelectedFile();
                    SaveFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName()+ " - Notepad");
                    fnameContainer=fyl;

                }catch(Exception ers2){}
            }

        }else if(e.getActionCommand().equals("Exit")){
            //exit
            Exiting();
        }else if(e.getActionCommand().equals("Copy")){
            //copy
            jta.copy();
        }else if(e.getActionCommand().equals("Paste")){
            //paste
            jta.paste();
        }else if(e.getActionCommand().equals("Cut")){
            jta.cut();
        }
    }

    public void OpenFile(String fname) throws IOException {
        BufferedReader d=new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
        String l;
        jta.setText("");

        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        while((l=d.readLine())!= null) {
            jta.setText(jta.getText()+l+"\r\n");
        }

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        d.close();
    }

    public void SaveFile(String fname) throws IOException {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream o=new DataOutputStream(new FileOutputStream(fname));
        o.writeBytes(jta.getText());
        o.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public void windowDeactivated(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowClosed(WindowEvent e){}

    public void windowClosing(WindowEvent e) {
        Exiting();
    }

    public void windowOpened(WindowEvent e){}

    public void Exiting() {
        System.exit(0);
    }

    public static void main (String[] args) {
        Main notp=new Main();
    }

}