package br.com.dio.model;

public class Space {

    private Integer real;
    private  int expected;
    private boolean fixed;

    public Space( int expected, boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if(this.fixed == true){this.real = this.expected;}
    }

    public Integer getReal() {
        return real;
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setReal(Integer real) {
        this.real = real;
    }

    public void clearSpace(){
        setReal(null);
    }

    @Override
    public String toString() {
        return "{" +
                "real=" + real +
                ", expected=" + expected +
                ", fixed=" + fixed +
                '}';
    }
}
