/*
    Name: William Li
    PID:  A16017109
 */

/**
 * A class named PrefixNotation that evaluates the results of prefix notation expressions.
 * @author William Li
 * @since  04/12/2021
 */
public class PrefixNotation {
    private static double FLOAT_CONVERTER=0.0;
    protected class StrStack {
        //String Stacks that helps to perform later task of prefix notation conversion

        /* instance variables, feel free to add more if you need */
        private String [] data;
        private int nElems;
        private double loadFactor;
        private double shrinkFactor;
        private int total_capacity;
        private static final double SHR_DEFAULT=0.25;
        private static final double LOADF_Default=0.75;
        private static final int TIMES_TWO=2;
        private static final double FLOAT_CONVERTER=0.0;


        public StrStack(int capacity, double loadF, double shrinkF) {
            /**
             *Constructor that creates an StrStack object
             *@param int integer that indicates the capacity of the StrStack
             *           double a load factor that indicates the proportion of loaded
             *           stack, upon reaching, the stack will be expanded.
             *           double a load factor that indicates the proportion of loaded
             *           stack, upon reaching, the stack will be shrunk.
             *
             * */
            data = new String[capacity];
            nElems = 0;
            loadFactor = loadF;
            shrinkFactor = shrinkF;
            total_capacity = capacity;


        }

        public StrStack(int capacity, double loadF) {
            /**
             *Constructor that creates an StrStack object, in which the shrink factor has been
             * set to a default value
             *@param int integer that indicates the capacity of the StrStack
             *           double a load factor that indicates the proportion of loaded
             *           stack, upon reaching, the stack will be expanded.
             *
             *
             * */
            this(capacity, loadF, SHR_DEFAULT);
        }

        public StrStack(int capacity) {
            /**
             *Constructor that creates an StrStack object, in which the shrink factor and load factor
             * have both been set to a default value
             *@param int integer that indicates the capacity of the StrStack
             *
             *
             * */
            this(capacity, LOADF_Default, SHR_DEFAULT);
        }

        public boolean isEmpty() {
            /**
             *Method that returns true if the StrStack is empty, vice versa.
             * @return boolean whether the stack is empty
             *
             * */
            if (nElems == 0){
                return true;
            }
            return false;
        }

        public void clear() {
            /**
             *Method that clears the data of the stack, resets the capacity of stack,
             * and resets the count for items in stack
             *
             *
             * */
            data = new String[total_capacity];
            nElems = 0;
        }

        public int size() {
            /**
             *Method that returns the number of items stored in the stack
             * @return int number of items currently in stack
             * */
            if (nElems!=0){
                return nElems;
            }
            return 0;
        }

        public int capacity() {
            /**
             *Method that returns the number of remaining open spaces in the stack
             * @return number of open spaces in stack
             *
             * */
            if (nElems!=total_capacity){
                return total_capacity-nElems;
            }
            return 0;
        }

        public String peek() {
            /**
             *Method that returns the last item to pop in the stack without popping it
             * @return String the last item in stack
             *
             * */
            return data[nElems-1];


        }

        public void push(String element) {
            /**
             *Method that pushes a given element into the stack
             *
             * */
            int current_max_capa=this.size()+this.capacity();
            double float_current_size=this.size()+FLOAT_CONVERTER;
            double current_loadF=float_current_size/ current_max_capa;
            if (current_loadF >=loadFactor){
                String[] new_stack=new String [current_max_capa*TIMES_TWO];
                for(int i=0;i<nElems;i++){
                    new_stack[i]=data[i];
                }
                data=new_stack;
            }
            data[nElems]=element;
            nElems+=1;
        }

        public String pop() {
            /**
             *Method that returns the last item in stack and removes it from stack
             * @returns String last item in stack
             *
             * */
            int current_max_capa=this.size()+this.capacity();
            double decreased_flaot_size=this.size()-1+FLOAT_CONVERTER;
            double current_shrinF=decreased_flaot_size / current_max_capa;
            String res = data[nElems-1];
            if (current_shrinF<=shrinkFactor){
                if (current_max_capa>=total_capacity){
                    if (current_max_capa/TIMES_TWO >=total_capacity){
                        String [] new_stack=new String[current_max_capa/TIMES_TWO];
                        for (int i =0; i<nElems;i++){
                            new_stack[i]=data[i];
                        }

                        new_stack[nElems-1]="";
                        data=new_stack;
                        nElems -=1;
                    }else{
                        String [] new_stack=new String[total_capacity];
                        for (int i =0; i<nElems;i++){
                            new_stack[i]=data[i];}
                        new_stack[nElems-1]="";
                        data=new_stack;
                        nElems -=1;
                    }
                }
            }else{
                data[nElems-1]="";
                nElems -=1;
            }
            return res;

        }

        public void multiPush(String[] elements) {
            /**
             *Method that push a given array of Strings to the stack
             * @parameter String[] an array of items to be pushed into the stack
             *
             * */
            for (int i=0; i<elements.length; i++){
                this.push(elements[i]);
            }
        }

        public String[] multiPop(int amount) {
            /**
             *Method that pops a indicated number of items from the stack and returns
             * them in an array
             * @parameter int the number of items to be popped
             * @return String[] an array of items popped from the stack
             *
             * */
            String[] res=new String[amount];
            for (int i=0; i<amount; i++){
                String outcome="";
                outcome=this.pop();
                res[i]=outcome;
            }
            return res;
        }
    }

    public int evaluate(String notation) {
        /**
        *Method that evaluates the prefix notation expressions to integer outputs
         *@param String form prefix notation
         * @return int form result of computing the expression
         * */
        StrStack stack=new StrStack(notation.length());
        for (int i = notation.length()-1;i>-1;i-- ){
            if (notation.charAt(i) !=' '){
                if (notation.charAt(i)=='+'||notation.charAt(i)=='-'||
                        notation.charAt(i)=='*'||notation.charAt(i)=='/'){
                    if (stack.size()==1||stack.size()==0){
                        stack.push(String.valueOf(notation.charAt(i)));
                    }else{
                        String last_item= stack.pop();
                        String second_last_item= stack.pop();
                        int item = calculator(last_item, second_last_item,notation.charAt(i));
                        stack.push(String.valueOf(item));
                    }
                }else{
                    stack.push(String.valueOf(notation.charAt(i)));
                }
            }
        }
        return Integer.parseInt(stack.pop());
    }
    private int calculator(String a,String b, char operator){
        /**
         *Helper function that recognizes string form operators and computes
         *@param String, String, and char two string forms of numbers and one char
         * form operator
         * @return int form result of computing the expression
         * */
        int res=0;
        if(operator =='+'){
            res= Integer.parseInt(a)+Integer.parseInt(b);
        }else if (operator =='-'){
            res= Integer.parseInt(a)-Integer.parseInt(b);
        }else if (operator == '*'){
            res= Integer.parseInt(a)*Integer.parseInt(b);
        }else if (operator =='/'){
            res= Integer.parseInt(a)/Integer.parseInt(b);
        }
        return res;
    }


}
