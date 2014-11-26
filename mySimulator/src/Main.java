/**
 * Created by oky on 14/11/26.
 */
public class Main {

    //フィールドの範囲
    static final int fieldx = 100;
    static final int fieldy = 100;

    //シミュレーション時間
    static final int SimuTime = 60*60*12;
    static final int refresh_rate = 1;
    //MNの数
    static final int nodeNum = 1;


    public static void main(String[] args) {
        Simulator simu = new Simulator();
        simu.startSimulation();
    }
}
