package aed;

public class Traslado {
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }
    //devolver destino
    public int destino() {
        return destino;
    }
    //devolver origen
    public int origen(){
        return origen;
    }
    //devolver gananciaNeta
    public int gananciaNeta(){
        return gananciaNeta;
    }
}
