import java.util.EmptyStackException;
/*
    Name: William Li
    PID:  A16017109
 */

import java.util.EmptyStackException;

/**
 * An IntStack class that generates stacks of integers. Methods can be applied to add, remove
 * items from the stacks and report properties of the stack
 * @author William Li
 * @since  04/09/2021
 */
public class IntStack {

    /* instance variables, feel free to add more if you need */
    private int[] data;
    private int nElems;
    private double loadFactor;
    private double shrinkFactor;
    private int total_capacity;
    private static final int CAP_THRE = 5;
    private static final int SHRI_LOWER=0;
    private static final double SHRI_UPPER=0.33;
    private static final double LOAD_LOWER = 0.67;
    private static final int LOAD_UPPER = 1;
    private static final double SHR_DEFAULT=0.25;
    private static final double LOADF_Default=0.75;
    private static final int TIMES_TWO=2;
    private static final double FLOAT_CONVERTER=0.0;



    public IntStack(int capacity, double loadF, double shrinkF) {
        /**
         *Constructor that creates an IntStack object
         *@param int integer that indicates the capacity of the StrStack
         *           double a load factor that indicates the proportion of loaded
         *           stack, upon reaching, the stack will be expanded.
         *           double a load factor that indicates the proportion of loaded
         *           stack, upon reaching, the stack will be shrunk.
         *
         * */
        if (capacity < CAP_THRE|| loadF<LOAD_LOWER||loadF>LOAD_UPPER||
                shrinkF <= SHRI_LOWER|| shrinkF> SHRI_UPPER) {
            throw new IllegalArgumentException();}
        data = new int[capacity];
        nElems = 0;
        loadFactor = loadF;
        shrinkFactor = shrinkF;
        total_capacity = capacity;

    }

    public IntStack(int capacity, double loadF) {
        /**
         *Constructor that creates an IntStack object, in which the shrink factor has been
         * set to a default value
         *@param int integer that indicates the capacity of the StrStack
         *           double a load factor that indicates the proportion of loaded
         *           stack, upon reaching, the stack will be expanded.
         *
         *
         * */
        this(capacity, loadF, SHR_DEFAULT);
    }

    public IntStack(int capacity) {
        /**
         *Constructor that creates an IntStack object, in which the shrink factor and load factor
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
        if (this.nElems == 0){
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
        data = new int[total_capacity];
        nElems = 0;
    }

    public int size() {
        /**
         *Method that returns the number of items stored in the stack
         * @return int number of items currently in stack
         * */
        if (this.nElems!=0){
            return nElems;
        }
        return 0;
    }

    public int capacity() {
        /**
         *Method that returns the number of remaining open spaces in the stack
         * @return int number of open spaces in stack
         *
         * */
        if (nElems!=total_capacity){
            int current_max_capa=this.data.length;
            return current_max_capa-nElems;
        }
        return 0;
    }

    public int peek() {
        /**
         *Method that returns the last item to pop in the stack without popping it
         * @return Int the last item in stack
         *
         * */
        if (this.isEmpty()) {
            throw new EmptyStackException();}
        if (nElems!=0){
            return data[nElems-1];
        }

        return 0;
    }

    public void push(int element) {
        /**
         *Method that pushes a given element into the stack
         *
         * */
        int current_max_capa=this.size()+this.capacity();
        double float_current_size=this.size()+FLOAT_CONVERTER;
        double current_loadF=float_current_size/ current_max_capa;
        int[] new_stack;
        if (current_loadF >=loadFactor){
            new_stack=new int [current_max_capa*TIMES_TWO];
            for(int i=0;i<nElems;i++){
                new_stack[i]=data[i];
            }
            this.data=new_stack;
        }
        data[nElems]=element;
        nElems+=1;
    }

    public int pop() {
        /**
         *Method that returns the last item in stack and removes it from stack
         * @returns Integer last item in stack
         *
         * */
        if (this.isEmpty()) {
            throw new EmptyStackException();}
        int current_max_capa=this.size()+this.capacity();
        double decreased_flaot_size=this.size()-1+FLOAT_CONVERTER;
        double current_shrinF=decreased_flaot_size / current_max_capa;
        int res = data[nElems-1];
        if (current_shrinF<=this.shrinkFactor){
            if (current_max_capa>=this.total_capacity){
                if (current_max_capa/TIMES_TWO >=this.total_capacity){
                    int [] new_stack=new int[current_max_capa/TIMES_TWO];
                    for (int i =0; i<nElems;i++){
                        new_stack[i]=this.data[i];
                    }

                    new_stack[this.nElems-1]=0;
                    this.data=new_stack;
                    this.nElems -=1;
                }else{
                    int [] new_stack=new int[total_capacity];
                    for (int i =0; i<nElems;i++){
                        new_stack[i]=data[i];}
                    new_stack[nElems-1]=0;
                    this.data=new_stack;
                    this.nElems -=1;
                }
            }
        }else{
            this.data[nElems-1]=0;
            this.nElems -=1;
        }
        return res;

    }

    public void multiPush(int[] elements) {
        /**
         *Method that push a given array of Integers to the stack
         * @parameter Int[] an array of items to be pushed into the stack
         *
         * */
        if (elements==null) {
            throw new IllegalArgumentException();}
        for (int i=0; i<elements.length; i++){
            this.push(elements[i]);
        }
    }

    public int[] multiPop(int amount) {
        /**
         *Method that pops a indicated number of items from the stack and returns
         * them in an array
         * @parameter int the number of items to be popped
         * @return Int[] an array of items popped from the stack
         *
         * */
        if (amount<=0) {
            throw new IllegalArgumentException();}
        int[] res=new int[amount];
        for (int i=0; i<amount; i++){
            int outcome=0;
            outcome=this.pop();
            res[i]=outcome;
        }
        return res;
    }
}
