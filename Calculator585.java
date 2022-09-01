import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedList;

public class Calculator585 implements ActionListener{
    JLabel label;
    JFrame frame;
    JTextField textField,currentOp;
    JTextArea history;
    JPanel panel;
    JButton[] numbers = new JButton[10];
    JButton[] functions = new JButton[15];
    JButton addButton,subButton,mulButton,divButton,modButton,sqrtButton,squareButton,inverseButton,signButton;
    JButton clearButton,ceButton,deleteButton,decimalButton,equalButton,clearHistory;
    Font font = new Font("Times New Roman",Font.BOLD,30);
    double n1=0,n2=0,result=0;
    String operator;
    boolean equalsFlag = false;
    LimitedQueue<String> opHistory = new LimitedQueue<String>(3);
    public Calculator585(){

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(460,600);
        frame.setLayout(null);

        textField = new JTextField();
        textField.setBounds(50,50,300,45);
        textField.setFont(font);
        textField.setEditable(false);

        currentOp = new JTextField();
        currentOp.setBounds(50,10,300,35);
        currentOp.setFont(new Font("Times New Roman",Font.BOLD,25));
        currentOp.setEditable(false);

        history = new JTextArea();
        history.setBounds(50,450,300,100);
        history.setFont(font);
        history.setRows(3);
        history.setEditable(false);



        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("*");
        divButton = new JButton("/");
        modButton = new JButton("%");
        sqrtButton = new JButton("\u221A");
        squareButton = new JButton("x\u00B2");
        inverseButton = new JButton("1/x");
        signButton = new JButton("\u00B1");

        clearButton = new JButton("C");
        ceButton = new JButton("CE");
        deleteButton = new JButton("\u2190");
        decimalButton = new JButton(".");
        equalButton = new JButton("=");
        clearHistory = new JButton("CH");

        functions[0] = addButton;
        functions[1] = subButton;
        functions[2] = mulButton;
        functions[3] = divButton;
        functions[4] = modButton;
        functions[5] = sqrtButton;
        functions[6] = squareButton;
        functions[7] = inverseButton;
        

        functions[8] = clearButton;
        functions[9] = ceButton;
        functions[10] = deleteButton;
        functions[11] = decimalButton;
        functions[12] = equalButton;
        functions[13] = signButton;
        functions[14] = clearHistory;

        for(int i=0;i<15;i++){
            functions[i].addActionListener(this);
            functions[i].setFont(font);
            functions[i].setFocusable(false);
        }
        for(int i=0;i<10;i++){
            numbers[i] = new JButton(String.valueOf(i));
            numbers[i].addActionListener(this);
            numbers[i].setFont(font);
            numbers[i].setFocusable(false);
        }
        ceButton.setFont(new Font("Times New Roman",Font.BOLD,15));

        panel = new JPanel();
        panel.setBounds(50,100,300,330);
        panel.setLayout(new GridLayout(6,4,5,5));
        
        
        
        panel.add(modButton);
        panel.add(sqrtButton);
        panel.add(squareButton);
        panel.add(inverseButton);

        panel.add(clearButton);
        panel.add(ceButton);
        panel.add(deleteButton);
        panel.add(divButton);

        panel.add(numbers[7]);
        panel.add(numbers[8]);
        panel.add(numbers[9]);
        panel.add(mulButton);

        panel.add(numbers[4]);
        panel.add(numbers[5]);
        panel.add(numbers[6]);
        panel.add(subButton);

        panel.add(numbers[1]);
        panel.add(numbers[2]);
        panel.add(numbers[3]);
        panel.add(addButton);

        panel.add(signButton);
        panel.add(numbers[0]);
        panel.add(decimalButton);
        panel.add(equalButton);

        clearHistory.setBounds(365,475,65,50);
        clearHistory.setFont(new Font("Times New Roman",Font.BOLD,15));


        
        frame.add(panel);
        frame.add(textField);
        frame.add(currentOp);
        frame.add(history);
        frame.add(clearHistory);
        frame.setVisible(true);
    }
    public static void main(String[] args){
        new Calculator585();

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        DecimalFormat df = new DecimalFormat("#.######");
        df.setRoundingMode(RoundingMode.HALF_UP);
        for(int i=0;i<10;i++){
            if(e.getSource() == numbers[i])
                textField.setText(textField.getText().concat(String.valueOf(i)));
        }
        if(e.getSource()==signButton){
            if(textField.getText().length()>0)
                if(!textField.getText().contains("-"))
                    textField.setText("-".concat(textField.getText()));
                else{
                    textField.setText(textField.getText().substring(1,textField.getText().length()));
                }
        }
        if(e.getSource()==decimalButton){
            if(!textField.getText().contains(".")){
                textField.setText(textField.getText().concat("."));
                equalsFlag=false;
            }

        }
        if(e.getSource()==addButton){
            n1 = Double.parseDouble(textField.getText());
            operator="+";
            if(!currentOp.getText().contains("+")){
                currentOp.setText(textField.getText().concat("+"));
                equalsFlag=false;
            }
            textField.setText("");
        }
        if(e.getSource()==subButton){
            n1 = Double.parseDouble(textField.getText());
            operator="-";
            if(!currentOp.getText().contains("-")){
                currentOp.setText(textField.getText().concat("-"));
                equalsFlag=false;
            }
            textField.setText("");
        }
        if(e.getSource()==mulButton){
            n1 = Double.parseDouble(textField.getText());
            operator="*";
            if(!currentOp.getText().contains("*")){
                currentOp.setText(textField.getText().concat("*"));
                equalsFlag=false;
            }
            textField.setText("");
        }
        if(e.getSource()==divButton){
            n1 = Double.parseDouble(textField.getText());
            operator="/";
            if(!currentOp.getText().contains("/")){
                currentOp.setText(textField.getText().concat("/"));
                equalsFlag=false;
            }
            textField.setText("");
        }
        if(e.getSource()==modButton){
            n1 = Double.parseDouble(textField.getText());
            operator="%";
            if(!currentOp.getText().contains("%")){
                currentOp.setText(textField.getText().concat("%"));
                equalsFlag=false;
            }

            textField.setText("");
        }
        if(e.getSource()==sqrtButton){
            n1 = Double.parseDouble(textField.getText());
            String ans = df.format(Math.sqrt(Double.parseDouble(textField.getText())));
            textField.setText(ans);
            opHistory.add("\u221A"+df.format(n1)+"="+ans);
            PrintHistory();
            operator="";
            currentOp.setText("");
        }
        if(e.getSource()==squareButton){
            n1 = Double.parseDouble(textField.getText());
            String ans = df.format(Math.pow(Double.parseDouble(textField.getText()),2));
            textField.setText(ans);
            opHistory.add(df.format(n1)+"\u00B2"+"="+ans);
            PrintHistory();
            operator="";
            currentOp.setText("");
        }
        if(e.getSource()==inverseButton){
            if(Double.parseDouble(textField.getText())!=0){
                n1 = Double.parseDouble(textField.getText());
                String ans = df.format(1/Double.parseDouble(textField.getText()));
                textField.setText(ans);
                opHistory.add("1/"+df.format(n1)+"="+ans);
                PrintHistory();
                operator="";
                currentOp.setText("");
            }
            else{
                textField.setText("Cannot divide by 0");
            }
        }
        if(e.getSource()==ceButton){
            textField.setText("");
            if(currentOp.getText().length()==0)
                operator="";
        }
        if(e.getSource()==clearButton){
            textField.setText("");
            currentOp.setText("");
            operator="";
            equalsFlag=false;
        }
        if(e.getSource()==deleteButton){
            textField.setText(textField.getText().substring(0,textField.getText().length()-1));
        }
        if(e.getSource()==clearHistory){
            history.setText("");
            opHistory.clear();

        }
        if(e.getSource()==equalButton){
            switch(operator){
                case "+":
                    if(equalsFlag==false)
                        n2 = Double.parseDouble(textField.getText());
                    else
                        n1 = Double.parseDouble(textField.getText());
                    textField.setText(df.format(n1+n2));
                    currentOp.setText("");
                    opHistory.add(df.format(n1)+operator+df.format(n2)+"="+textField.getText());
                    equalsFlag=true;
                    PrintHistory();
                    break;
                case "-":
                    if(equalsFlag==false)
                        n2 = Double.parseDouble(textField.getText());
                    else
                        n1 = Double.parseDouble(textField.getText());
                    textField.setText(df.format(n1-n2));
                    currentOp.setText("");
                    opHistory.add(df.format(n1)+operator+df.format(n2)+"="+textField.getText());
                    equalsFlag=true;   
                    PrintHistory();
                    break;
                case "*":
                    if(equalsFlag==false)
                        n2 = Double.parseDouble(textField.getText());
                    else
                        n1 = Double.parseDouble(textField.getText());
                    textField.setText(df.format(n1*n2));
                    currentOp.setText("");
                    opHistory.add(df.format(n1)+operator+df.format(n2)+"="+textField.getText());
                    equalsFlag=true;
                    PrintHistory();
                    break;
                case "/":
                    if(equalsFlag==false)
                        n2 = Double.parseDouble(textField.getText());
                    else
                        n1 = Double.parseDouble(textField.getText());
                    textField.setText(df.format(n1/n2));
                    currentOp.setText("");
                    opHistory.add(df.format(n1)+operator+df.format(n2)+"="+textField.getText());
                    equalsFlag=true;
                    PrintHistory();
                    break;
                case "%":
                    if(equalsFlag==false)
                        n2 = Double.parseDouble(textField.getText());
                    else
                        n1 = Double.parseDouble(textField.getText());
                    textField.setText(df.format(n1%n2));
                    currentOp.setText("");
                    opHistory.add(df.format(n1)+operator+df.format(n2)+"="+textField.getText());
                    equalsFlag=true;
                    PrintHistory();
                    break;

            }
        }
        
    }
    void PrintHistory(){
        history.setText("");
        Iterator<String> it = opHistory.iterator();
        while(it.hasNext())
            history.setText(history.getText()+it.next()+"\n");
    }
    


}
class LimitedQueue<E> extends LinkedList<E> {

    private int max;

    public LimitedQueue(int max) {
        this.max = max;
    }

    @Override
    public boolean add(E o) {
        boolean add = super.add(o);
        while (add && size() > max) {
           super.remove();
        }
        return add;
    }
}