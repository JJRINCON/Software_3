package exceptions;

public class RepeatedNameException extends Exception{

    public RepeatedNameException(String name){
        super("El nombre " + name + ", ya pertenece a un proceso");
    }
}
