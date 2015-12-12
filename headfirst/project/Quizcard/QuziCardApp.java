import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;



class QuizCard{

    private String ques;
    private String answ;

    public QuizCard(String q,String a){
        ques = q;
        answ = a;
    }

    String getQuestion(){
        return ques;
    }

    String getAnswer(){
        return answ;
    }

    void setAnswer(String s){
        answ = s;
    }

}

public class QuziCardApp{
    private JFrame frame;
    private JPanel panel;
    private JLabel question;
    private JLabel answer;   
    private JTextArea area1;
    private JTextArea area2;
    private JScrollPane scroller1;
    private JScrollPane scroller2;
    private JButton nextCard;
    private JButton editCardButton;
    private JButton showAnswerButton;


    private ArrayList quizlist  = new ArrayList<QuizCard>();
    private File f;
    private int currentIndex = -1;
    private boolean answerisShowed = false;
    private File currentFile;

    public static void main(String[] args) {
        QuziCardApp qc = new QuziCardApp();
        qc.go();
    }





    public void go(){
        setupGUI();

    }


    public void setupGUI()
    {
        question = new JLabel("Question:"); 
        answer    = new JLabel("Answer:");   
        area1     = new JTextArea(12,20);
        area2     = new JTextArea(6,20);
        nextCard   = new JButton("Next Card") ;
        showAnswerButton = new JButton("Show Answer") ;
        editCardButton = new JButton("Edit Answer");

        frame = new JFrame("Quiz Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /***
        //面板背景
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        area1.setLineWrap(true); 
        scroller1 = new JScrollPane(area1);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        scroller2 = new JScrollPane(area2);
        scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel1.add(BorderLayout.NORTH,qunestion);
        panel2.add(BorderLayout.NORTH,answer);
        panel1.add(BorderLayout.CENTER,scroller1);
        panel2.add(BorderLayout.CENTER,scroller2);
        panel1.add(BorderLayout.SOUTH,nextCard  );
        panel2.add(BorderLayout.SOUTH,showAnswerButton);
        ***/



        /**字体处理**/
        Font bigFont = new Font("sanserif",Font.BOLD,26);
        Font font = new  Font("sanserif",Font.BOLD,22);
        question.setFont(bigFont);
        answer.setFont(bigFont);
        area1.setFont(font);
        area2.setFont(font);
        /***/


        /***为按钮添加动作**/  
        nextCard.addActionListener(new NextCardListener());
        showAnswerButton.addActionListener(new ShowAnswerListener());
        editCardButton.addActionListener(new EditAsnwerListener());


        /******/


        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        area1.setLineWrap(true); 
        scroller1 = new JScrollPane(area1);
        scroller1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller2 = new JScrollPane(area2);
        scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(question);
        panel.add(scroller1);
        panel.add(nextCard);
        panel.add(answer);
        panel.add(scroller2);
        panel.add(showAnswerButton);
        panel.add(editCardButton);
        

        //添加菜单
        JMenuBar bar = new JMenuBar();
        JMenu  fileMenu = new JMenu("File");
        JMenu  loadMenu = new JMenu("Load");
        JMenu  aboutMienu = new JMenu("About");
        JMenuItem newMenuItem  = new JMenuItem("New");
        JMenuItem saveMenuItem  = new JMenuItem("Save");

        JMenu  aboutMienu = new JMenu("About");
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        bar.add(fileMenu);
        bar.add(aboutMienu);
        // bar.add(loadMenu);

        newMenuItem.addActionListener(new LoadMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());

        // frame.getContentPane().add(BorderLayout.NORTH,panel1);
        // frame.getContentPane().add(BorderLayout.CENTER,panel2);
        frame.getContentPane().add(BorderLayout.CENTER,panel);
        frame.setJMenuBar(bar);
        frame.setSize(400,600);
        frame.setVisible(true);
    }


    public void makeCard(String line)
    {
        String[] result = line.split("/");       //这里使用数组来装String，spilt()方法返回一个数组
        QuizCard card = new QuizCard(result[0],result[1]);
        quizlist.add(card);
    }




    public void loadFile(File f){
        area1.setText("");
        area2.setText("");
        // quizlist = new ArrayList<String>();
        try{
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            // System.out.println("File Successfully loaded!");
            JOptionPane msg = new JOptionPane();
            msg.showMessageDialog(null, "File Successfully loaded!", "打开文件", JOptionPane.INFORMATION_MESSAGE);
            String line = null;
            while((line=br.readLine())!=null)
            {
                makeCard(line);
            }
            br.close();

            if(quizlist.size()!=0){
                showNextCard();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }   

    public void saveFile(File f){
        String line = null ;
        try{
            FileWriter fw = new FileWriter(f);
            for(int i=0;i<quizlist.size();i++){
                QuizCard card = (QuizCard) quizlist.get(i);
                line =  card.getQuestion() + "/" + card.getAnswer()+"\r\n";
                fw.write(line);
            }
            fw.close();
            JOptionPane msg = new JOptionPane();
            msg.showMessageDialog(null,"File saved successfully!","Status",JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){ex.printStackTrace();}
    }

    public void showNextCard(){
        try{
            answerisShowed = false;
            currentIndex=(currentIndex+1)%quizlist.size();    //使之循环
            area1.setText(((QuizCard)quizlist.get(currentIndex)).getQuestion());
            area2.setText("");
        }catch(Exception ex){
            if(currentFile==null){
                JOptionPane msg = new JOptionPane();
                msg.showMessageDialog(null,"请导入文件！","错误",JOptionPane.ERROR_MESSAGE);
            }
            ex.printStackTrace();
        }
    }

    public void showAnswer()
    {
        try{
            if(!answerisShowed){
                area2.setText(((QuizCard)quizlist.get(currentIndex)).getAnswer());
                answerisShowed = true;
            }
            else{}
        }catch(Exception ex){
            if(currentFile==null){
            JOptionPane msg = new JOptionPane();
            msg.showMessageDialog(null,"请导入文件！","错误",JOptionPane.ERROR_MESSAGE);
            }
            ex.printStackTrace();
        }
    }

    public void editAnswer(){
        try{
            String newAnswer = area2.getText();
            QuizCard card = (QuizCard) quizlist.get(currentIndex);
            card.setAnswer(newAnswer);
            JOptionPane msg = new JOptionPane();
            msg.showMessageDialog(null,"The answer's edited!","Status",JOptionPane.INFORMATION_MESSAGE);
            // area2.setText(newAnswer);
        }catch(Exception ex){
            if(currentFile==null){
                JOptionPane msg = new JOptionPane();
                msg.showMessageDialog(null,"请导入文件！","错误",JOptionPane.ERROR_MESSAGE);
            }
            ex.printStackTrace();
        }
    }




    /****各个子类*******/
    class NextCardListener implements ActionListener{
        public void actionPerformed(ActionEvent ae)
        {
            showNextCard();
        }
    }


    class ShowAnswerListener implements ActionListener{
        public void actionPerformed(ActionEvent ae)
        {
            if(!answerisShowed){
                showAnswer();
            }
        }
    }

    class LoadMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent ae)
        {
            //生成文件选择框的代码
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.showOpenDialog(frame);
            currentFile = fileOpen.getSelectedFile();  //返回的是File对象
            loadFile(currentFile);
        }
    }

    class SaveMenuListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            saveFile(currentFile);
        }
    }

    class EditAsnwerListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            editAnswer();
        }
    }

}