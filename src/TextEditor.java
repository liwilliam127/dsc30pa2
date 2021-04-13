import java.util.EmptyStackException;
/*
    Name: William Li
    PID:  A16017109
 */

/**
 * A Text Editor class that edit a piece of text it holds in a variety of ways
 * @author William Li
 * @since  04/12/2021
 */
public class TextEditor {

    /* instance variables */
    private String text;
    private IntStack undo;
    private StringStack deletedText;
    private IntStack redo;
    private StringStack insertedText;
    private static final int INICAPA=200;

    public TextEditor() {
        /**
         *Constructor that creates a TextEditor object
         *
         * */
        text="";
        undo = new IntStack(INICAPA);
        deletedText=new StringStack(INICAPA);
        redo= new IntStack(INICAPA);
        insertedText=new StringStack(INICAPA);
    }

    public String getText() {
        /**
         *Method that returns the text within the TextEditor object
         * */
        return this.text;
    }

    public int length() {
        /**
         *Method that returns the length of text within the TextEditor object
         * */
        return this.text.length();
    }

    public void caseConvert(int i, int j) {
        /**
         *Method that converts cases of all characters between index i to index j of the text
         *@param int two indexes that marks the start and end of converting
         *@throws IllegalArgumentException the exception will be thrown if indexes are out of
         * bound or the second argument int is equal to or smaller than the first argument int
         * */
        if (j<= i|| i>= this.text.length()|| j>= this.text.length()||
        i<0||j<0) {
            throw new IllegalArgumentException();}
        String txt = this.getText();
        char [] resarr = new char [j-i];
        // pushing to the undo stack
        undo.push(i);
        undo.push(j);
        undo.push(0);
        String res="";
        for (int x=0;x<i;x++){
            res+= txt.charAt(x);
        }
        for(int z=i; z<j;z++){
            char character=txt.charAt(z);
            //convert based on current case of character
            if (Character.isLetter(character)){
                if(Character.isLowerCase(character)){
                    character=Character.toUpperCase(character);
                    res+=character;
                }else if (Character.isUpperCase(character)){
                    character=Character.toLowerCase(character);
                    res+=character;
                }

            }

        }
        for (int y=j;j<txt.length();j++){
            res+= txt.charAt(y);
        }
        this.text=res;

    }

    public void insert(int i, String input) {
        /**
         *Method that inserts a piece of text at indicated index
         *@param int, String first argument of index indicates the location of insertion, while the second
         *string was the text to insert
         *@throws NullPointerException if the input text is null, IllegalArgumentException if the
         * index is out of bound
         *
         * */
        if (input==null) {
            throw new NullPointerException();}
        if (i>=this.text.length()|| i<0) {
            throw new IllegalArgumentException ();}
        int insert_len=input.length();
        int ending_position=i+input.length();
        undo.push(i);
        undo.push(ending_position);
        undo.push(1);
        String res_str="";
        String txt=this.text;
        for (int j=0;j<i;j++){
            res_str+= txt.charAt(j);
            if (j==i-1){
                res_str+= input;
            }
        }
        for (int z=i; z<txt.length();z++){
            res_str+= txt.charAt(z);
        }
        this.text=res_str;

    }

    public void delete(int i, int j) {
        /**
         *Method that deletes a piece of text at indicated index
         *@param int first and second argument marks the index of the location of deletion
         *@throws IllegalArgumentException if the index is out of bound
         *
         * */
        if (j<= i|| i>= this.text.length()|| j>= this.text.length()||
                i<0||j<0) {
            throw new IllegalArgumentException();}
        String res_str="";
        String res_txt="";
        String txt=this.text;
        undo.push(i);
        undo.push(j);
        undo.push(2);
        for (int z=i;z<j;z++){
            res_str+= txt.charAt(z);
        }
        deletedText.push(res_str);
        for (int x=0;x<i;x++){
            res_txt+= txt.charAt(x);
        }
        for (int y=j;y<txt.length();y++){
            res_txt+= txt.charAt(y);
        }
        this.text=res_txt;



    }

    public boolean undo() {
        /**
         *Method that undos the last operation recorded in undo stack
         *
         * */
        //handling empty stack
        if (undo.size()==0){
            return false;
        }else{
            //extracts information about last operation
            int type_indicator=undo.pop();
            int ending=undo.pop();
            int starting =undo.pop();
            redo.push(starting);
            redo.push(ending);
            redo.push(type_indicator);
            String txt = this.text;
            if (type_indicator==0){
                this.caseConvert(starting,ending);
            }else if (type_indicator==1){
                String removed_txt="";
                for (int x=starting; x< ending;x++) {
                    removed_txt += txt.charAt(x);
                }
                insertedText.push(removed_txt);
                this.delete(starting,ending);

            }else if (type_indicator==2){
                String deleted_txt=deletedText.pop();
                this.insert(starting,deleted_txt);
            }
        }
        return true;
    }

    public boolean redo() {
        /**
         *Method that redos the last operation that has been undone
         *
         * */
        //handles empty stacks
        if (redo.size()==0){
            return false;
        }else{
            //extracting information about last undo
            int type_indicator=redo.pop();
            int ending=redo.pop();
            int starting =redo.pop();
            undo.push(starting);
            undo.push(ending);
            undo.push(type_indicator);
            String txt=this.text;
            if (type_indicator==0){
                this.caseConvert(starting,ending);
            }else if (type_indicator==1){
                String to_insert=insertedText.pop();
                this.insert(starting,to_insert);
            }else if (type_indicator==2){
                this.delete(starting,ending);
            }
        }
        return true;
    }
}